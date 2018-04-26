package ana.geotools;

public class ECEF2ENU {
	static double X;
	static double Y;
	static double Z;
	static double X0;
	static double Y0;
	static double Z0;
	static double B0; //角度，例如45
	static double L0;
	static double[][] enu = new double[3][1]; // 东北天坐标，neu[0][0]:east, neu[1][0]:north, neu[2][0]:up

	/**
	 * 矩阵乘法，计算矩阵的元素
	 * 这里雷达站的坐标位置需要用到ECEF和JWH的转换
	 * @param X 地心直角坐标系，目标X，单位：m
	 * @param Y 地心直角坐标系，目标Y，单位：m
	 * @param Z 地心直角坐标系，目标Z，单位：m
	 * @param X0  地心直角坐标系，雷达站X，单位：m
	 * @param Y0  地心直角坐标系，雷达站Y，单位：m
	 * @param Z0  地心直角坐标系，雷达站Z，单位：m
	 * @param B0  地心大地坐标系，雷达站纬度B，角度制
	 * @param L0  地心大地坐标系，雷达站经度L，角度制
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

	// 求积方法
	public static double[][] mulMatrix(double[][] a, double[][] b) {
		double[][] c = new double[a.length][b[0].length];
		//相乘的两个行列式需要满足，第一个行列式的列于第二个行列式的行相等  
		if (a[0].length != b.length) {
			System.out.println("Wrong parameters.");
			return c;
		}
		//若A为mXn矩阵，B为nXp矩阵，返回一个mXp的矩阵  
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
