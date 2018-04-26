package ana.geotools;

/**
 * һЩ���õ������ļ���
 * @author ��ͨ
 *
 */
public class CoordinateDisposer {
	
	// ����֮��ķ�λ��
	private static double Azimuth;
	
	// ����֮��ľ���
	private static double Distance;
	
	// һ���γ��
	private static double Lat;
	
	// һ��ľ���
	private static double Lon;
	
	/**
	 * ���Ƕ���ת��Ϊ������
	 * @param angleOnDeg
	 * @return angleOnRad
	 */
	public static double degToRad(double angleOnDeg) {
		return angleOnDeg/180*Math.PI;
	}
	
	/**
	 * ��������ת��Ϊ�Ƕ���
	 * @param angleOnRad
	 * @return angleOnDeg
	 */
	public static double radToDeg(double angleOnRad) {
		return angleOnRad/Math.PI*180;
	}
	
	/**
	 * ��÷�λ��
	 * @return Azimuth
	 */
	public static double getAzimuth() {
		return Azimuth;
	}
	
	/**
	 * ��֪����֮��ľ�γ�ȣ�������֮��ķ�λ��
	 * �����ľ�γ��һ���ýǶ���
	 * ��λ�ǵ�λҲΪ�Ƕ���
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
	 * �������֮��ľ���
	 * @return Distance
	 */
	public static double getDistance() {
		return Distance;
	}
	
	/**
	 * ��֪����֮��ľ�γ�ȣ�������֮����������
	 * �����ľ�γ��һ���ýǶ���
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
		double R = 6371000; // ����뾶����λ��m
		distance = R*alphaC;
		Distance = distance;
	}
	
	/**
	 * ���һ���γ��
	 * @return this.Lat
	 */
	public static double getLat() {
		return Lat;
	}
	
	/**
	 * ���һ��ľ���
	 * @return this.Lon
	 */
	public static double getLon() {
		return Lon;
	}
	
	/**
	 * ��֪һ��ľ�γ�Ⱥ�����һ��ľ���ͷ�λ�ǣ�����һ���γ�Ⱥ;���
	 * �����ľ�γ��һ���ýǶ��ƣ����뵥λΪ�ף���λ�ǵ�λΪ�Ƕ���
	 * ��õ���һ��ľ�γ��ҲΪ�Ƕ���
	 * @param latRef
	 * @param lonRef
	 * @param distance
	 * @param azimuth
	 */
	public static void setLatAndLon(double latRef, double lonRef, double distance, double azimuth) {
		latRef = degToRad(latRef);
		lonRef = degToRad(lonRef);
		azimuth = degToRad(azimuth);
		double R = 6371000; // ����뾶����λ��m
		double alphaC = distance/R;
		double alphaA = Math.acos( Math.sin(latRef)*Math.cos(alphaC) +
				Math.cos(latRef)*Math.sin(alphaC)*Math.cos(azimuth) );
		double C = Math.asin(Math.sin(alphaC)*Math.sin(azimuth)/Math.sin(alphaA));
		Lat = radToDeg(Math.PI/2 - alphaA);
		Lon = radToDeg(lonRef + C);
	}
	
	/**
	 * �ȷ��뾭γ��(���뺬��'��')�����־�γ��ת��
	 * @param degrees �ȷ��뾭γ��
	 * @return ���־�γ��
	 */
	public static double ConvertDegreesToDigital(String degrees) {
        final double num = 60;
        double digitalDegree = 0.0;
        int d = degrees.indexOf('��');           //�ȵķ��Ŷ�Ӧ�� Unicode ����Ϊ��00B0[1]����ʮ���ƣ�����ʾΪ�㡣
        if (d < 0) {
            return digitalDegree;
        }
        String degree = degrees.substring(0, d);
        digitalDegree += Double.parseDouble(degree);

        int m = degrees.indexOf('\'');           //�ֵķ��Ŷ�Ӧ�� Unicode ����Ϊ��2032[1]����ʮ���ƣ�����ʾΪ�䡣
        if (m < 0) {
            return digitalDegree;
        }
        String minute = degrees.substring(d + 1, m);
        digitalDegree += ((Double.parseDouble(minute)) / num);

        int s = degrees.indexOf('\"');           //��ķ��Ŷ�Ӧ�� Unicode ����Ϊ��2033[1]����ʮ���ƣ�����ʾΪ�塣
        if (s < 0) {
            return digitalDegree;
        }
        String second = degrees.substring(m + 1, s);
        digitalDegree += (Double.parseDouble(second) / (num * num));

        return digitalDegree;
    }
   
	/**
	 * �ȷ��뾭γ��(���뺬��'��')�����־�γ��ת��
	 * @param degrees �ȷ��뾭γ��
	 * @return ���־�γ��
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
