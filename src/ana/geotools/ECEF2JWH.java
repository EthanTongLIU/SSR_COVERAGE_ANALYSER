package ana.geotools;

public class ECEF2JWH {
	static double X;
	static double Y;
	static double Z;
	static double a = 6378137;
	static double b = 6356752.31414;
	static double e = 0.0818191910428; // 地球第一偏心率
	static double e2 = 0.0820944381526; // 地球第二偏心率
	static double B; //角度，例如45
	static double R; //= a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
	static double H;
	
	/**
	 * 计算R
	 * @param B
	 * @return
	 */
	public static double getR(double B) {
		R = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
		// System.out.println("R=" + R);
		return R;
	}
	
	/**
	 * 计算经度L，角度制
	 * @param X
	 * @param Y
	 * @return
	 */
	public static double getL(double X, double Y) {
		return Math.toDegrees( Math.PI/2 - Math.atan(X/Y) );
	}
	
	/**
	 * 计算纬度B，角度制
	 * @param X
	 * @param Y
	 * @param Z
	 * @return
	 */
	public static double getB(double X, double Y, double Z) {
		double U = Math.atan( Z / ( 
				Math.sqrt( Math.pow(X, 2) + Math.pow(Y, 2) ) *
				Math.sqrt( 1 - Math.pow(e, 2) )
				) );
		return Math.toDegrees(
				Math.atan( ( Z + b * Math.pow(e2, 2) * Math.pow( Math.sin(U) , 3) ) /
				( Math.sqrt( Math.pow(X, 2) + Math.pow(Y, 2) ) - 
					a * Math.pow(e, 2) * Math.pow( Math.cos(U) , 3)	)
				));
	}
	
	/**
	 * 在普通地区计算H
	 * @param X
	 * @param Y
	 * @param B
	 * @return
	 */
	public static double getH(double X, double Y, double Z) {
		b = getB(X,Y,Z);
		H = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2)) / Math.cos(Math.toRadians(b)) - getR(b);
		// System.out.println("H=" + H);
		return H;
	}
	
	/**
	 * 在极区附近附近计算H
	 * @param Z
	 * @param B
	 * @return 
	 */
	public static double getH0(double X, double Y, double Z) {
		b = getB(X, Y, Z);
		H = Z / Math.sin(Math.toRadians(b)) - getR(b) * (1 - Math.pow(e, 2));
		// System.out.println("H0=" + H);
		return H;
	}
}
