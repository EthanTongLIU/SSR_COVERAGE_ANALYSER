package ana.powerrange;

import java.awt.geom.Point2D;

import ana.geotools.ECEF2ENU;
import ana.geotools.ECEF2JWH;
import ana.geotools.ENU2ECEF;
import ana.geotools.JWH2ECEF;
import ana.geotools.Lambert;
import ana.visionrange.Tilting;

/**
 * �״�Ĵ�ֱ������ͼ����
 * @author ��ͨ
 *
 */
public class VerticalDirectivityPattern {

	// ����G
	private static double GMax = 2.700097898186677;
	
	/**
	 * ��ĳһ�������ϵ�������þ���
	 * @param range Ϊ�״�����������룬��λΪ��
	 * @param theta ��λΪ����
	 * @return ���ظ÷����ϵ�������þ���
	 */
	public static double getActionDistanceTheta(double theta, double range) {
		
		// ������������������λ�Ƿֱ�
		double[] amp = {
				-10.64 , -5.78 , -6.01 , -10.98 , -9.91 , 
				-8.89 , -13.48 , -15.08 , -17.95 , -20.60 , -20.11	
		}; 
		// ����������ĳ�ʼ��λ����λ�Ƕ�
		double[] phi0 = {
				0 , 66.8 , 126.3 , 153.8 , 151.4 , -174.1 ,
				-155.6 , -144.9 , -130.9 , -112.2 , -124.2
		}; 
		// ��ʼ�������λ����ʵ��
		double[] ReOrigin = new double[11];
		// ��ʼ�������λ�����鲿
		double[] ImOrigin = new double[11];
		
		for(int i=0; i<11; i++) {
			phi0[i] = Math.toRadians( phi0[i] );
			amp[i] = Math.pow( 10 , amp[i]/20 );
			ReOrigin[i] = amp[i]*Math.cos(phi0[i]);
			ImOrigin[i] = amp[i]*Math.sin(phi0[i]);
		}
		
		double phi = Math.PI * Math.sin( theta ); // ������λ��
		double[] RePhiDelay = new double[11]; // ��λ�ӳ�����ʵ��
		double[] ImPhiDelay = new double[11]; // ��λ�ӳ������鲿
		for(int i=0; i<11; i++) {
			RePhiDelay[i] = Math.cos( phi * i );
			ImPhiDelay[i] = -Math.sin( phi * i );
		}

		// ���˷�
		double re = 0;
		double im = 0;
		for(int i=0; i<11; i++) {
			re += (RePhiDelay[i]*ReOrigin[i] - ImPhiDelay[i]*ImOrigin[i]);
			im += (RePhiDelay[i]*ImOrigin[i] + ImPhiDelay[i]*ReOrigin[i]);
		}
		double G = Math.sqrt( re*re + im*im );
		return G * range / GMax;
	}
	
	
	public static double[] inne = new double[2]; // ��Բ��γ��
	public static double[] extern = new double[2]; // ��Բ��γ��
	
	/**
	 * ����һ�����߷����ϵ�������þ���
	 * @param lat0 �״�վ��γ�ȣ��Ƕ���
	 * @param lon0 �״�վ�ľ��ȣ��Ƕ���
	 * @param h0 �״�վ�ĵĸ߶�
	 * @param a0 ���߷�λ�ǣ��Ƕ���
	 * @param dmax �״�����������þ��룬��λ��
	 * @param alt �߶Ȳ�
	 * @param d ��������ĵ�λ����
	 * @param alphaPos ���߰�װ�ǣ���λ��
	 */
	public static void findActionDistanceInfo(double lat0, double lon0, double h0, 
			double a0, double dmax, double alt, double d, double alphaPos) {
		
		// ���ڼ�¼��һ�����
		double actionDistanceLast = 0;
		double actionDistanceThetaLast = 0;
		
		// ���ڼ�¼��ǰ�����
		double actionDistanceCurrent = 0;
		double actionDistanceThetaCurrent = 0;
		
		// ���������������������ĵ�λ����
		double dN = 0;
		double dE = 0;
		if(a0==0||a0==360) {
			dN = d;
			dE = 0;
		}
		if(0<a0&&a0<90) {
			dN = d*Math.cos(Math.toRadians(a0)); // ��������ĵ�λ���� 
			dE = d*Math.sin(Math.toRadians(a0)); // ��������ĵ�λ����
		}
		if(a0==90) {
			dN = 0;
			dE = d;
		}
		if(90<a0&&a0<180) {
			dN = -d*Math.sin(Math.toRadians(a0-90));
			dE = d*Math.cos(Math.toRadians(a0-90));
		}
		if(a0==180) {
			dN = -d;
			dE = 0;
		}
		if(180<a0&&a0<270) {
			dN = -d*Math.cos(Math.toRadians(a0-180));
			dE = -d*Math.sin(Math.toRadians(a0-180));
		}
		if(a0==270) {
			dN = 0;
			dE = -d;
		}
		if(270<a0&&a0<360) {
			dN = d*Math.sin(Math.toRadians(a0-270));
			dE = -d*Math.cos(Math.toRadians(a0-270));
		}

		
		// ���������ϵĵ��վ�����겢��ʼ������
		double[] enu = new double[3];
		enu[0] = 0;
		enu[1] = 0;
		enu[2] = 0;
		
		// ���״�վ��JWHת��ΪECEF
		double X0 = JWH2ECEF.getX(h0, lat0, lon0);
		double Y0 = JWH2ECEF.getY(h0, lat0, lon0);
		double Z0 = JWH2ECEF.getZ(h0, lat0);
		
		while(actionDistanceCurrent<dmax) {
			enu[0] += dE; // ����ǰ��
			enu[1] += dN; // ����ǰ��
			
			// ��ENUת��ΪECEF
			double[][] Xyz = ENU2ECEF.getXyz(enu[0], enu[1], enu[2], X0, Y0, Z0, lat0, lon0);
			// ��ECEFת��ΪJWH
			double lat = ECEF2JWH.getB(Xyz[0][0], Xyz[1][0], Xyz[2][0]);
			double lon = ECEF2JWH.getL(Xyz[0][0], Xyz[1][0]);
			// ������
			double yang = Tilting.getTilting(lat, lon, alt, lat0, lon0, h0);
			// ������С��0��ֹͣ��������ΪС��0�ĵط��״￴����
			if(yang<0) {
				System.out.println("�Ƕ��Ѿ�С��0��");
				// ��¼�õصľ���
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				extern[0] = pos.x;
				extern[1] = pos.y;
				break;
			}
			
			// ���㵱ǰ���Ƿ����ϵ�������þ��� 
			// ���ǰ�װ�ǵ����� yang-alphaPos
			// double alpha = 20;
			actionDistanceThetaCurrent = getActionDistanceTheta(Math.toRadians(yang-alphaPos),dmax);
			
			// ����߶Ȳ��Ӧ��������þ���
			double xCurrent = JWH2ECEF.getX(alt, lat, lon);
			double yCurrent = JWH2ECEF.getY(alt, lat, lon);
			double zCurrent = JWH2ECEF.getZ(alt, lat);
			double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
			actionDistanceCurrent = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
			// �Ƚ����������룬�����ţ����¼��ǰ��γ��
			if( ( (actionDistanceThetaCurrent>actionDistanceCurrent) && (actionDistanceThetaLast<actionDistanceLast) ) ) {
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				inne[0] = pos.x;
				inne[1] = pos.y;
				System.out.println("�ҵ���Ȧ");
			}
			
			if( ( (actionDistanceThetaCurrent<actionDistanceCurrent) && (actionDistanceThetaLast>actionDistanceLast) ) ) {
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(lat, lon, lat0, lon0);
				extern[0] = pos.x;
				extern[1] = pos.y;
				System.out.println("�ҵ���Ȧ");
			}
			
			actionDistanceThetaLast = actionDistanceThetaCurrent;
			actionDistanceLast = actionDistanceCurrent;
			
		}
	}
	
	
}
