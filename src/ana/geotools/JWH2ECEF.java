package ana.geotools;

public class JWH2ECEF {
	
    static double X;
	static double Y;
	static double Z;
	static double a = 6378137; // ���򳤰��ᣬ��λ��m 
	static double b = 6356752.31414; // ����̰��ᣬ��λ��m
	static double e = 0.0818191910428; // �����һƫ����
	static double B; //�Ƕȣ�����45
	static double R; //= a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
	
	/**
	 * ����R����λ��m
	 * @param B �Ƕ���
	 * @return
	 */
	public static double getR(double B) {
		R = a / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(Math.toRadians(B)), 2));
		// System.out.println("R=" + R);
		return R;
	}
	
	/**
	 * ����X����λ��m
	 * @param h �߶ȣ���λ��m
	 * @param B γ�ȣ��Ƕ���
	 * @param L ���ȣ��Ƕ���
	 * @return
	 */
	public static double getX(double h, double B, double L) {
		X = (getR(B) + h) * Math.cos(Math.toRadians(B)) * Math.cos(Math.toRadians(L));
		//System.out.println("X=" + X);
		return X;
	}
	
	/**
	 * ����Y����λ��m
	 * @param h ��λ��m
	 * @param B �Ƕ���
	 * @param L �Ƕ���
	 * @return
	 */
	public static double getY(double h, double B, double L) {
		Y = (getR(B) + h) * Math.cos(Math.toRadians(B)) * Math.sin(Math.toRadians(L));
		//System.out.println("Y=" + Y);
		return Y;
	}
	
	/**
	 * ����Z����λ��m
	 * @param h ��λ��m
	 * @param B �Ƕ���
	 * @return
	 */
	public static double getZ(double h, double B) {
		Z = (getR(B) * (1 - Math.pow(e, 2)) + h) * Math.sin(Math.toRadians(B));
		//System.out.println("Z=" + Z);
		return Z;
	}

}
