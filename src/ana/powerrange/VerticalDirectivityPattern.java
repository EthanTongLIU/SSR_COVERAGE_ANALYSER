package ana.powerrange;

import java.awt.geom.Point2D;

import ana.geotools.ECEF2ENU;
import ana.geotools.ECEF2JWH;
import ana.geotools.ENU2ECEF;
import ana.geotools.JWH2ECEF;
import ana.geotools.Lambert;
import ana.visionrange.Tilting;

/**
 * 雷达的垂直方向性图分析
 * @author 刘通
 *
 */
public class VerticalDirectivityPattern {

	// 最大的G
	private static double GMax = 2.700097898186677;
	
	/**
	 * 找某一个方向上的最大作用距离
	 * @param range 为雷达最大威力距离，单位为米
	 * @param theta 单位为弧度
	 * @return 返回该方向上的最大作用距离
	 */
	public static double getActionDistanceTheta(double theta, double range) {
		
		// 定义辐射柱的振幅，单位是分贝
		double[] amp = {
				-10.64 , -5.78 , -6.01 , -10.98 , -9.91 , 
				-8.89 , -13.48 , -15.08 , -17.95 , -20.60 , -20.11	
		}; 
		// 定义辐射柱的初始相位，单位是度
		double[] phi0 = {
				0 , 66.8 , 126.3 , 153.8 , 151.4 , -174.1 ,
				-155.6 , -144.9 , -130.9 , -112.2 , -124.2
		}; 
		// 初始振幅及相位向量实部
		double[] ReOrigin = new double[11];
		// 初始振幅及相位向量虚部
		double[] ImOrigin = new double[11];
		
		for(int i=0; i<11; i++) {
			phi0[i] = Math.toRadians( phi0[i] );
			amp[i] = Math.pow( 10 , amp[i]/20 );
			ReOrigin[i] = amp[i]*Math.cos(phi0[i]);
			ImOrigin[i] = amp[i]*Math.sin(phi0[i]);
		}
		
		double phi = Math.PI * Math.sin( theta ); // 定义相位差
		double[] RePhiDelay = new double[11]; // 相位延迟向量实部
		double[] ImPhiDelay = new double[11]; // 相位延迟向量虚部
		for(int i=0; i<11; i++) {
			RePhiDelay[i] = Math.cos( phi * i );
			ImPhiDelay[i] = -Math.sin( phi * i );
		}

		// 做乘法
		double re = 0;
		double im = 0;
		for(int i=0; i<11; i++) {
			re += (RePhiDelay[i]*ReOrigin[i] - ImPhiDelay[i]*ImOrigin[i]);
			im += (RePhiDelay[i]*ImOrigin[i] + ImPhiDelay[i]*ReOrigin[i]);
		}
		double G = Math.sqrt( re*re + im*im );
		return G * range / GMax;
	}
	
	
	public static double[] inne = new double[2]; // 内圆经纬度
	public static double[] extern = new double[2]; // 外圆经纬度
	
	/**
	 * 计算一个射线方向上的最大作用距离
	 * @param lat0 雷达站心纬度，角度制
	 * @param lon0 雷达站心经度，角度制
	 * @param h0 雷达站心的高度
	 * @param a0 射线方位角，角度制
	 * @param dmax 雷达威力最大作用距离，单位米
	 * @param alt 高度层
	 * @param d 搜索方向的单位增量
	 * @param alphaPos 天线安装角，单位度
	 */
	public static void findActionDistanceInfo(double lat0, double lon0, double h0, 
			double a0, double dmax, double alt, double d, double alphaPos) {
		
		// 用于记录上一组距离
		double actionDistanceLast = 0;
		double actionDistanceThetaLast = 0;
		
		// 用于记录当前组距离
		double actionDistanceCurrent = 0;
		double actionDistanceThetaCurrent = 0;
		
		// 计算正北方向和正东方向的单位增量
		double dN = 0;
		double dE = 0;
		if(a0==0||a0==360) {
			dN = d;
			dE = 0;
		}
		if(0<a0&&a0<90) {
			dN = d*Math.cos(Math.toRadians(a0)); // 正北方向的单位增量 
			dE = d*Math.sin(Math.toRadians(a0)); // 正东方向的单位增量
		}
		if(a0==90) {
			dN = 0;
			dE = d;
		}
		if(90<a0&&a0<180) {
			dN = -d*Math.sin(Math.toRadians(a0-90));
			dE = d*Math.cos(Math.toRadians(a0-90));
		}
		if(a0==180) {
			dN = -d;
			dE = 0;
		}
		if(180<a0&&a0<270) {
			dN = -d*Math.cos(Math.toRadians(a0-180));
			dE = -d*Math.sin(Math.toRadians(a0-180));
		}
		if(a0==270) {
			dN = 0;
			dE = -d;
		}
		if(270<a0&&a0<360) {
			dN = d*Math.sin(Math.toRadians(a0-270));
			dE = -d*Math.cos(Math.toRadians(a0-270));
		}

		
		// 建立射线上的点的站心坐标并初始化坐标
		double[] enu = new double[3];
		enu[0] = 0;
		enu[1] = 0;
		enu[2] = 0;
		
		// 将雷达站的JWH转化为ECEF
		double X0 = JWH2ECEF.getX(h0, lat0, lon0);
		double Y0 = JWH2ECEF.getY(h0, lat0, lon0);
		double Z0 = JWH2ECEF.getZ(h0, lat0);
		
		while(actionDistanceCurrent<dmax) {
			enu[0] += dE; // 搜索前进
			enu[1] += dN; // 搜索前进
			
			// 将ENU转化为ECEF
			double[][] Xyz = ENU2ECEF.getXyz(enu[0], enu[1], enu[2], X0, Y0, Z0, lat0, lon0);
			// 将ECEF转化为JWH
			double lat = ECEF2JWH.getB(Xyz[0][0], Xyz[1][0], Xyz[2][0]);
			double lon = ECEF2JWH.getL(Xyz[0][0], Xyz[1][0]);
			// 求仰角
			double yang = Tilting.getTilting(lat, lon, alt, lat0, lon0, h0);
			// 若仰角小于0，停止搜索，因为小于0的地方雷达看不见
			if(yang<0) {
				System.out.println("角度已经小于0了");
				// 记录该地的距离
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				extern[0] = pos.x;
				extern[1] = pos.y;
				break;
			}
			
			// 计算当前仰角方向上的最大作用距离 
			// 考虑安装角的问题 yang-alphaPos
			// double alpha = 20;
			actionDistanceThetaCurrent = getActionDistanceTheta(Math.toRadians(yang-alphaPos),dmax);
			
			// 计算高度层对应的最大作用距离
			double xCurrent = JWH2ECEF.getX(alt, lat, lon);
			double yCurrent = JWH2ECEF.getY(alt, lat, lon);
			double zCurrent = JWH2ECEF.getZ(alt, lat);
			double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
			actionDistanceCurrent = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
			// 比较这两个距离，如果变号，则记录当前经纬度
			if( ( (actionDistanceThetaCurrent>actionDistanceCurrent) && (actionDistanceThetaLast<actionDistanceLast) ) ) {
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				inne[0] = pos.x;
				inne[1] = pos.y;
				System.out.println("找到内圈");
			}
			
			if( ( (actionDistanceThetaCurrent<actionDistanceCurrent) && (actionDistanceThetaLast>actionDistanceLast) ) ) {
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				extern[0] = pos.x;
				extern[1] = pos.y;
				System.out.println("找到外圈");
			}
			
			actionDistanceThetaLast = actionDistanceThetaCurrent;
			actionDistanceLast = actionDistanceCurrent;
			
		}
	}
	
	
}
