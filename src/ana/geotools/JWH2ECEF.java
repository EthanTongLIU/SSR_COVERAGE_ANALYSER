package ana.geotools;

public class JWH2ECEF {
	
    static double X;
	static double Y;
	static double Z;
	static double a = 6378137; // 地球长半轴，单位：m 
	static double b = 6356752.31414; // 地球短半轴，单位：m
	static double e = 0.0818191910428; // 地球第一偏心率
	static double B; //角度，例如45
	static double R; //= a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
	
	/**
	 * 计算R，单位：m
	 * @param B 角度制
	 * @return
	 */
	public static double getR(double B) {
		R = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
		// System.out.println("R=" + R);
		return R;
	}
	
	/**
	 * 计算X，单位：m
	 * @param h 高度，单位：m
	 * @param B 纬度，角度制
	 * @param L 经度，角度制
	 * @return
	 */
	public static double getX(double h, double B, double L) {
		X = (getR(B) + h) * Math.cos(Math.toRadians(B)) * Math.cos(Math.toRadians(L));
		//System.out.println("X=" + X);
		return X;
	}
	
	/**
	 * 计算Y，单位：m
	 * @param h 单位：m
	 * @param B 角度制
	 * @param L 角度制
	 * @return
	 */
	public static double getY(double h, double B, double L) {
		Y = (getR(B) + h) * Math.cos(Math.toRadians(B)) * Math.sin(Math.toRadians(L));
		//System.out.println("Y=" + Y);
		return Y;
	}
	
	/**
	 * 计算Z，单位：m
	 * @param h 单位：m
	 * @param B 角度制
	 * @return
	 */
	public static double getZ(double h, double B) {
		Z = (getR(B) * (1 - Math.pow(e, 2)) + h) * Math.sin(Math.toRadians(B));
		//System.out.println("Z=" + Z);
		return Z;
	}

}
