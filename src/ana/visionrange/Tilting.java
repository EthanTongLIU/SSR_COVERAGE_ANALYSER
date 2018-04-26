package ana.visionrange;

/**
 * 
 * @author 刘通
 * 该类用于计算遮蔽角
 */
public class Tilting {
	
	static double a = 6378137; // 长半轴
	static double aq = 4*a/3.0; // 等效长半轴
	static double e = 0.0818191910428; // 地球第一偏心率
	static double R;
	
	/**
	 * 计算R
	 * @param B 弧度制
	 * @return
	 */
	public static double getR(double B) {
		R = aq / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(B), 2));
		// System.out.println("R=" + R);
		return R;
	}
	
	/**
	 * WGS-84椭球模型计算单点遮蔽角
	 * @param lat 角度制 
	 * @param lon 角度制
	 * @param h 单位为m
	 * @param latRef 雷达站心的lat 角度制
	 * @param lonRef 雷达站心的lon 角度制
	 * @param hRef 雷达站心的h 单位为m
	 * @return 返回遮蔽角，角度制
	 */
	public static double getTilting(double lat, double lon, double h, 
			double latRef, double lonRef, double hRef) {
		
		lat = Math.toRadians(lat);
		lon = Math.toRadians(lon);
		latRef = Math.toRadians(latRef);
		lonRef = Math.toRadians(lonRef);
		
		double[][] n = new double[3][1];
		double[][] a = new double[3][1];
		double[][] c = new double[3][1];
		
		n[0][0] = Math.cos(latRef) * Math.cos(lonRef);
		n[1][0] = Math.cos(latRef) * Math.sin(lonRef);
		n[2][0] = Math.sin(latRef);
		
		a[0][0] = (getR(latRef)+hRef) * Math.cos(latRef) * Math.cos(lonRef);
		a[1][0] = (getR(latRef)+hRef) * Math.cos(latRef) * Math.sin(lonRef);
		a[2][0] = (getR(latRef)*(1-Math.pow(e, 2)) + hRef) * Math.sin(latRef);
		
		c[0][0] = (getR(lat)+h) * Math.cos(lat) * Math.cos(lon);
		c[1][0] = (getR(lat)+h) * Math.cos(lat) * Math.sin(lon);
		c[2][0] = (getR(lat)*(1-Math.pow(e, 2)) + h) * Math.sin(lat);
		
		double theta = 0;
		
		theta = Math.PI/2 - Math.acos( getInnerPro(getArrayMinus(c,a),n)/getArrayMod(getArrayMinus(c,a)) );
		
		return Math.toDegrees(theta);
	}
	
	/**
	 * 求两个向量相减的方法
	 * @param a
	 * @param b
	 * @return
	 */
	public static double[][] getArrayMinus(double[][] a, double[][] b) {
		double[][] c = new double[a.length][1];
		for(int i=0; i<a.length; i++) {
			c[i][0] = a[i][0] - b[i][0];
		}
		return c;
	}
	
	/**
	 * 求内积方法
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getInnerPro(double[][] a, double[][] b) {
		double c = 0;
		// 求内积的两个向量需要相同的长度
		if(a.length != b.length) {
			System.out.println("Wrong parameters.");
			return c;
		}
		for(int i=0; i<a.length; i++) {
			c += a[i][0] * b[i][0];
		}
		return c;	
	}
	
	/**
	 * 求一个向量的模
	 * @param a
	 * @return
	 */
	public static double getArrayMod(double[][] a) {
		double mod = 0;
		for(int i=0; i<a.length; i++) {
			mod += Math.pow(a[i][0], 2);
		}
		return Math.sqrt(mod);
	}
	
}
