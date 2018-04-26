package ana.geotools;

public class ECEF2ENU {
	static double X;
	static double Y;
	static double Z;
	static double X0;
	static double Y0;
	static double Z0;
	static double B0; //�Ƕȣ�����45
	static double L0;
	static double[][] enu = new double[3][1]; // ���������꣬neu[0][0]:east, neu[1][0]:north, neu[2][0]:up

	/**
	 * ����˷�����������Ԫ��
	 * �����״�վ������λ����Ҫ�õ�ECEF��JWH��ת��
	 * @param X ����ֱ������ϵ��Ŀ��X����λ��m
	 * @param Y ����ֱ������ϵ��Ŀ��Y����λ��m
	 * @param Z ����ֱ������ϵ��Ŀ��Z����λ��m
	 * @param X0  ����ֱ������ϵ���״�վX����λ��m
	 * @param Y0  ����ֱ������ϵ���״�վY����λ��m
	 * @param Z0  ����ֱ������ϵ���״�վZ����λ��m
	 * @param B0  ���Ĵ������ϵ���״�վγ��B���Ƕ���
	 * @param L0  ���Ĵ������ϵ���״�վ����L���Ƕ���
	 * @return
	 */
	public static double[][] getEnu(double X, double Y, double Z, double X0, double Y0, double Z0, double B0, double L0) {
		double a[][] = new double[3][3];
		double b[][] = new double[3][1];
		a[0][0] = -Math.sin(Math.toRadians(L0));
		a[0][1] = Math.cos(Math.toRadians(L0));
		a[0][2] = 0;
		
		a[1][0] = -Math.sin(Math.toRadians(B0)) * Math.cos(Math.toRadians(L0));
		a[1][1] = -Math.sin(Math.toRadians(B0)) * Math.sin(Math.toRadians(L0));
		a[1][2] = Math.cos(Math.toRadians(B0));
		
		a[2][0] = Math.cos(Math.toRadians(B0)) * Math.cos(Math.toRadians(L0));
		a[2][1] = Math.cos(Math.toRadians(B0)) * Math.sin(Math.toRadians(L0));
		a[2][2] = Math.sin(Math.toRadians(B0));

		b[0][0] = X - X0;
		b[1][0] = Y - Y0;
		b[2][0] = Z - Z0;
				
		enu = mulMatrix(a, b);
		return enu;
	}

	// �������
	public static double[][] mulMatrix(double[][] a, double[][] b) {
		double[][] c = new double[a.length][b[0].length];
		//��˵���������ʽ��Ҫ���㣬��һ������ʽ�����ڵڶ�������ʽ�������  
		if (a[0].length != b.length) {
			System.out.println("Wrong parameters.");
			return c;
		}
		//��AΪmXn����BΪnXp���󣬷���һ��mXp�ľ���  
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				for (int j2 = 0; j2 < a[0].length; j2++) {
					c[i][j] = c[i][j] + a[i][j2] * b[j2][j];
				}
			}
		}
		return c;
	}
}
