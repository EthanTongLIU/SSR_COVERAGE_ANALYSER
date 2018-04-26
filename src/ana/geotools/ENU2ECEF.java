package ana.geotools;

public class ENU2ECEF {
	static double North;
	static double East;
	static double Up;
	static double X0;
	static double Y0;
	static double Z0;
	static double B0; //角度，例如45
	static double L0;
	static double[][] xyz = new double[3][1];
	
	/**
	 * 
	 * @param East 单位：m
	 * @param North 单位：m
	 * @param Up 单位：m
	 * @param X0  地心直角坐标系，雷达站X，单位：m
	 * @param Y0  地心直角坐标系，雷达站Y，单位：m
	 * @param Z0  地心直角坐标系，雷达站Z，单位：m
	 * @param B0 地心大地坐标系，雷达站纬度B，角度制
	 * @param L0 地心大地坐标系，雷达站经度L，角度制
	 * @return
	 */
	public static double[][] getXyz(double East, double North, double Up, double X0, double Y0, double Z0, double B0, double L0) {
		double a[][] = new double[3][3];
		double b[][] = new double[3][1];
		double c[][] = new double[3][1];
		
		a[0][0] = -Math.sin(Math.toRadians(L0));
		a[0][1] = -Math.sin(Math.toRadians(B0)) * Math.cos(Math.toRadians(L0));
		a[0][2] = Math.cos(Math.toRadians(B0)) * Math.cos(Math.toRadians(L0));

		a[1][0] = Math.cos(Math.toRadians(L0));
		a[1][1] = -Math.sin(Math.toRadians(B0)) * Math.sin(Math.toRadians(L0));
		a[1][2] = Math.cos(Math.toRadians(B0)) * Math.sin(Math.toRadians(L0));

		a[2][0] = 0;
		a[2][1] = Math.cos(Math.toRadians(B0));
		a[2][2] = Math.sin(Math.toRadians(B0));

		b[0][0] = East;
		b[1][0] = North;
		b[2][0] = Up;

		c[0][0] = X0;
		c[1][0] = Y0;
		c[2][0] = Z0;

		xyz = matrixSum(c, mulMatrix(a, b));
		return xyz;
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

	// 求和方法 
	public static double[][] matrixSum(double[][] c, double[][] ds) {

		double[][] arrSum = new double[c.length][c[0].length];

		if ((c.length != ds.length) || (c[0].length != ds[0].length)) {
			System.err.println("Matrices are of incompatible size.");
			System.exit(1);
		}

		for (int row = 0; row < c.length; row++) {
			for (int col = 0; col < c[0].length; col++) {
				arrSum[row][col] = c[row][col] + ds[row][col];
			}
		}

		return arrSum;

	}
}
