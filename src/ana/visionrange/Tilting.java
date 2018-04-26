package ana.visionrange;

/**
 * 
 * @author ��ͨ
 * �������ڼ����ڱν�
 */
public class Tilting {
	
	static double a = 6378137; // ������
	static double aq = 4*a/3.0; // ��Ч������
	static double e = 0.0818191910428; // �����һƫ����
	static double R;
	
	/**
	 * ����R
	 * @param B ������
	 * @return
	 */
	public static double getR(double B) {
		R = aq / Math.sqrt(1 - Math.pow(e, 2) * Math.pow(Math.sin(B), 2));
		// System.out.println("R=" + R);
		return R;
	}
	
	/**
	 * WGS-84����ģ�ͼ��㵥���ڱν�
	 * @param lat �Ƕ��� 
	 * @param lon �Ƕ���
	 * @param h ��λΪm
	 * @param latRef �״�վ�ĵ�lat �Ƕ���
	 * @param lonRef �״�վ�ĵ�lon �Ƕ���
	 * @param hRef �״�վ�ĵ�h ��λΪm
	 * @return �����ڱνǣ��Ƕ���
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
	 * ��������������ķ���
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
	 * ���ڻ�����
	 * @param a
	 * @param b
	 * @return
	 */
	public static double getInnerPro(double[][] a, double[][] b) {
		double c = 0;
		// ���ڻ�������������Ҫ��ͬ�ĳ���
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
	 * ��һ��������ģ
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
