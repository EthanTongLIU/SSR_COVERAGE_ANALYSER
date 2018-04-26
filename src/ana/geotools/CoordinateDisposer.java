package ana.geotools;

/**
 * 一些常用地理方法的集合
 * @author 刘通
 *
 */
public class CoordinateDisposer {
	
	// 两点之间的方位角
	private static double Azimuth;
	
	// 两点之间的距离
	private static double Distance;
	
	// 一点的纬度
	private static double Lat;
	
	// 一点的经度
	private static double Lon;
	
	/**
	 * 将角度制转换为弧度制
	 * @param angleOnDeg
	 * @return angleOnRad
	 */
	public static double degToRad(double angleOnDeg) {
		return angleOnDeg/180*Math.PI;
	}
	
	/**
	 * 将弧度制转换为角度制
	 * @param angleOnRad
	 * @return angleOnDeg
	 */
	public static double radToDeg(double angleOnRad) {
		return angleOnRad/Math.PI*180;
	}
	
	/**
	 * 获得方位角
	 * @return Azimuth
	 */
	public static double getAzimuth() {
		return Azimuth;
	}
	
	/**
	 * 已知两点之间的经纬度，求它们之间的方位角
	 * 参数的经纬度一律用角度制
	 * 方位角单位也为角度制
	 * @param latGoal
	 * @param lonGoal
	 * @param latLocal
	 * @param latLocal
	 */
	public static void setAzimuth(double latGoal, double lonGoal, double latLocal, double lonLocal) {
		latGoal = degToRad(latGoal);
		lonGoal = degToRad(lonGoal);
		latLocal = degToRad(latLocal);
		lonLocal = degToRad(lonLocal);
		double alphaA = Math.PI/2 - latGoal;
		// double alphaB = Math.PI/2 - latGoal;
		double C = lonGoal - lonLocal;
		double cosAlphaC = Math.sin(latGoal)*Math.sin(latLocal) +
				Math.cos(latLocal)*Math.cos(latGoal)*Math.cos(C);
		double sinAlphaC = Math.sqrt( 1 - Math.pow(cosAlphaC, 2) );
		double A = Math.asin( Math.sin(alphaA)/sinAlphaC*Math.sin(C) );
		if(radToDeg(A)>=0) {
			Azimuth = 180 - radToDeg(A);
		}
		if(radToDeg(A)<0) {
			Azimuth = 360 + radToDeg(A);
		}
	}
	
	/**
	 * 获得两点之间的距离
	 * @return Distance
	 */
	public static double getDistance() {
		return Distance;
	}
	
	/**
	 * 已知两点之间的经纬度，求两点之间的球面距离
	 * 参数的经纬度一律用角度制
	 * @param latGoal
	 * @param lonGoal
	 * @param latLocal
	 * @param latLocal
	 */
	public static void setDistance(double latGoal, double lonGoal, double latLocal, double lonLocal) {
		latGoal = degToRad(latGoal);
		lonGoal = degToRad(lonGoal);
		latLocal = degToRad(latLocal);
		lonLocal = degToRad(lonLocal);
		double distance = 0;
		double C = lonGoal - lonLocal;
		double alphaC = Math.acos( Math.sin(latGoal)*Math.sin(latLocal) +
				Math.cos(latLocal)*Math.cos(latGoal)*Math.cos(C) );
		double R = 6371000; // 地球半径，单位：m
		distance = R*alphaC;
		Distance = distance;
	}
	
	/**
	 * 获得一点的纬度
	 * @return this.Lat
	 */
	public static double getLat() {
		return Lat;
	}
	
	/**
	 * 获得一点的经度
	 * @return this.Lon
	 */
	public static double getLon() {
		return Lon;
	}
	
	/**
	 * 已知一点的经纬度和与另一点的距离和方位角，求另一点的纬度和经度
	 * 参数的经纬度一律用角度制，距离单位为米，方位角单位为角度制
	 * 求得的另一点的经纬度也为角度制
	 * @param latRef
	 * @param lonRef
	 * @param distance
	 * @param azimuth
	 */
	public static void setLatAndLon(double latRef, double lonRef, double distance, double azimuth) {
		latRef = degToRad(latRef);
		lonRef = degToRad(lonRef);
		azimuth = degToRad(azimuth);
		double R = 6371000; // 地球半径，单位：m
		double alphaC = distance/R;
		double alphaA = Math.acos( Math.sin(latRef)*Math.cos(alphaC) +
				Math.cos(latRef)*Math.sin(alphaC)*Math.cos(azimuth) );
		double C = Math.asin(Math.sin(alphaC)*Math.sin(azimuth)/Math.sin(alphaA));
		Lat = radToDeg(Math.PI/2 - alphaA);
		Lon = radToDeg(lonRef + C);
	}
	
	/**
	 * 度分秒经纬度(必须含有'°')和数字经纬度转换
	 * @param degrees 度分秒经纬度
	 * @return 数字经纬度
	 */
	public static double ConvertDegreesToDigital(String degrees) {
        final double num = 60;
        double digitalDegree = 0.0;
        int d = degrees.indexOf('°');           //度的符号对应的 Unicode 代码为：00B0[1]（六十进制），显示为°。
        if (d < 0) {
            return digitalDegree;
        }
        String degree = degrees.substring(0, d);
        digitalDegree += Double.parseDouble(degree);

        int m = degrees.indexOf('\'');           //分的符号对应的 Unicode 代码为：2032[1]（六十进制），显示为′。
        if (m < 0) {
            return digitalDegree;
        }
        String minute = degrees.substring(d + 1, m);
        digitalDegree += ((Double.parseDouble(minute)) / num);

        int s = degrees.indexOf('\"');           //秒的符号对应的 Unicode 代码为：2033[1]（六十进制），显示为″。
        if (s < 0) {
            return digitalDegree;
        }
        String second = degrees.substring(m + 1, s);
        digitalDegree += (Double.parseDouble(second) / (num * num));

        return digitalDegree;
    }
   
	/**
	 * 度分秒经纬度(必须含有'°')和数字经纬度转换
	 * @param degrees 度分秒经纬度
	 * @return 数字经纬度
	 */
	public static double ConvertDMSToDigital(String degrees) {
		double digitalDegree = 0.0;
	    String []array= degrees.substring(1).split("\\.");
	    double D=Double.parseDouble(array[0]);
	    double M2D=Double.parseDouble(array[1])/60;
	    double S2D=0.0;
        if(array.length>3) {
        	S2D=Double.parseDouble(array[2]+"."+array[3])/3600;
	    }
	    else {
	    	S2D=Double.parseDouble(array[2])/3600;
	    }
	    digitalDegree=D+M2D+S2D;
	    return digitalDegree;    
    }  

}
