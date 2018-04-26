package ana.visionrange;

import java.awt.geom.Point2D;
import java.io.File;

import ana.geotools.ECEF2ENU;
import ana.geotools.ECEF2JWH;
import ana.geotools.ENU2ECEF;
import ana.geotools.JWH2ECEF;
import ana.geotools.Lambert;
import ana.powerrange.VerticalDirectivityPattern;
import mil.nga.tiff.Rasters;

/**
 * 该类用于计算单方向雷达最大作用距离
 */
public class ActionDistance {
	
	public static double actionDistance; // 某一射线方向的最大作用距离
	public static double actionLat; // 最大作用距离所对应的纬度
	public static double actionLon; // 最大作用距离所对应的经度
	public static double actionLambertX; // 最大作用距离点经过兰伯特变换后的x方向距离
	public static double actionLambertY; // 最大作用距离点经过兰伯特变换后的y方向距离
	
	/**
	 * 计算一个射线方向上的最大作用距离
	 * @param lat0 雷达站心纬度，角度制
	 * @param lon0 雷达站心经度，角度制
	 * @param h0 雷达站心的高度
	 * @param a0 射线方位角，角度制
	 * @param dmax 雷达威力最大作用距离，单位米
	 * @param alt 高度层
	 * @param d 搜索方向的单位增量
	 * @param srtmName 文件名 
	 * @param srtmFile 文件
	 * @param rasters 栅格数据
	 */
	public static void findActionDistanceInfo(double lat0, double lon0, double h0, 
			double a0, double dmax, double alt, double d,
			String srtmName0, Rasters rasters0,
			String srtmName1, String srtmName2, String srtmName3, String srtmName4, 
			String srtmName5, String srtmName6, String srtmName7, String srtmName8,
			Rasters rasters1, Rasters rasters2, Rasters rasters3, Rasters rasters4, 
			Rasters rasters5, Rasters rasters6, Rasters rasters7, Rasters rasters8) {
		
		actionDistance = 0;
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
		
		// 定义初始遮蔽角
		double tilmax = -90;
		
		// 用于记录每次循环完后的经纬度，目的是最后如果作用距离就是雷达的威力范围，则直接输出最后一个经纬度信息
		double latFinal = 0;
		double lonFinal = 0;
		
		// 实时srtm文件
		String srtmName = srtmName0;
		Rasters rasters = rasters0;
		
		while(actionDistance<dmax) {
			enu[0] += dE; // 搜索前进
			enu[1] += dN; // 搜索前进
			
			// 将ENU转化为ECEF
			double[][] Xyz = ENU2ECEF.getXyz(enu[0], enu[1], enu[2], X0, Y0, Z0, lat0, lon0);
			// 将ECEF转化为JWH
			double lat = ECEF2JWH.getB(Xyz[0][0], Xyz[1][0], Xyz[2][0]);
			double lon = ECEF2JWH.getL(Xyz[0][0], Xyz[1][0]);
			// 根据lat,lon及其所属文件求出索引经纬度
			double latR = TiffMethod.getIndex(srtmName.substring(8, 10));
			double lonR = TiffMethod.getIndexLon(srtmName.substring(5, 7));
			// 计算该点的实际位置
			int i = TiffMethod.getX(lon, lonR);
			int j = TiffMethod.getY(lat, latR);
			// 如果该点超出了[0,6000]的取值范围，则根据lat,lon判断应该打开的新的srtm文件，替换原来的srtm文件
			if(i<0||i>6000||j<0||j>6000) {
				srtmName = TiffMethod.getTiffMom(lat, lon);
				// 如果能从传入的文件中找到，则直接使用，不用再次打开；如果找不到，则再次打开新文件
				if(srtmName.equals(srtmName0)) {
					rasters = rasters0;
				}
				else if(srtmName.equals(srtmName1)) {
					rasters = rasters1;
				}
				else if(srtmName.equals(srtmName2)) {
					rasters = rasters2;
				}
				else if(srtmName.equals(srtmName3)) {
					rasters = rasters3;
				}
				else if(srtmName.equals(srtmName4)) {
					rasters = rasters4;
				}
				else if(srtmName.equals(srtmName5)) {
					rasters = rasters5;
				}
				else if(srtmName.equals(srtmName6)) {
					rasters = rasters6;
				}
				else if(srtmName.equals(srtmName7)) {
					rasters = rasters7;
				}
				else if(srtmName.equals(srtmName8)) {
					rasters = rasters8;
				}
				else {
					rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(srtmName));
				}
				// 为了确保顺利向下执行，再次执行求i，j操作
				latR = TiffMethod.getIndex(srtmName.substring(8, 10));
				lonR = TiffMethod.getIndexLon(srtmName.substring(5, 7));
				i = TiffMethod.getX(lon, lonR);
				j = TiffMethod.getY(lat, latR);
				if(j==6001) {
					j=6000;
				}
				if(i==6001) {
					i=6000;
				}
			}
			// 读出高度值
			double h = rasters.getPixel(i, j)[0].doubleValue();
			// 求遮蔽角
			double til = Tilting.getTilting(lat, lon, h, lat0, lon0, h0);
			// 求仰角
			double yang = Tilting.getTilting(lat, lon, alt, lat0, lon0, h0);
			// 比较tilmax和til
			if(tilmax<til) {
				tilmax = til;
			}
			// 比较仰角和遮蔽角
			if(tilmax>=yang) {
				// 输出当前点的经纬度和作用距离，以及经兰伯特变换后的距离信息
				// 当前只测试一下作用距离
				double xCurrent = JWH2ECEF.getX(alt, lat, lon);
				double yCurrent = JWH2ECEF.getY(alt, lat, lon);
				double zCurrent = JWH2ECEF.getZ(alt, lat);
				double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
				actionDistance = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
				actionLat = lat;
				actionLon = lon;
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(actionLat, actionLon, lat0, lon0);
				actionLambertX = pos.x;
				actionLambertY = pos.y;
				break;
			}
			
			// 计算当前作用距离
			// 当前(lat,lon,alt)转为ENU
			double xCurrent = JWH2ECEF.getX(alt, lat, lon);
			double yCurrent = JWH2ECEF.getY(alt, lat, lon);
			double zCurrent = JWH2ECEF.getZ(alt, lat);
			double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
			actionDistance = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
			latFinal = lat;
			lonFinal = lon;
		}
		
		if(actionDistance>=dmax) {
			actionLat = latFinal;
			actionLon = lonFinal;
			Point2D.Double pos = new Point2D.Double();
			pos = Lambert.lambert(latFinal, lonFinal, lat0, lon0);
			actionLambertX = pos.x;
			actionLambertY = pos.y;
		}
	}
}
