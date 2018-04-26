package ana.visionrange;

import java.awt.geom.Point2D;
import java.io.File;

import ana.geotools.ECEF2ENU;
import ana.geotools.ECEF2JWH;
import ana.geotools.ENU2ECEF;
import ana.geotools.JWH2ECEF;
import ana.geotools.Lambert;
import ana.powerrange.VerticalDirectivityPattern;
import mil.nga.tiff.Rasters;

/**
 * �������ڼ��㵥�����״�������þ���
 */
public class ActionDistance {
	
	public static double actionDistance; // ĳһ���߷����������þ���
	public static double actionLat; // ������þ�������Ӧ��γ��
	public static double actionLon; // ������þ�������Ӧ�ľ���
	public static double actionLambertX; // ������þ���㾭�������ر任���x�������
	public static double actionLambertY; // ������þ���㾭�������ر任���y�������
	
	/**
	 * ����һ�����߷����ϵ�������þ���
	 * @param lat0 �״�վ��γ�ȣ��Ƕ���
	 * @param lon0 �״�վ�ľ��ȣ��Ƕ���
	 * @param h0 �״�վ�ĵĸ߶�
	 * @param a0 ���߷�λ�ǣ��Ƕ���
	 * @param dmax �״�����������þ��룬��λ��
	 * @param alt �߶Ȳ�
	 * @param d ��������ĵ�λ����
	 * @param srtmName �ļ��� 
	 * @param srtmFile �ļ�
	 * @param rasters դ������
	 */
	public static void findActionDistanceInfo(double lat0, double lon0, double h0, 
			double a0, double dmax, double alt, double d,
			String srtmName0, Rasters rasters0,
			String srtmName1, String srtmName2, String srtmName3, String srtmName4, 
			String srtmName5, String srtmName6, String srtmName7, String srtmName8,
			Rasters rasters1, Rasters rasters2, Rasters rasters3, Rasters rasters4, 
			Rasters rasters5, Rasters rasters6, Rasters rasters7, Rasters rasters8) {
		
		actionDistance = 0;
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
		
		// �����ʼ�ڱν�
		double tilmax = -90;
		
		// ���ڼ�¼ÿ��ѭ�����ľ�γ�ȣ�Ŀ�������������þ�������״��������Χ����ֱ��������һ����γ����Ϣ
		double latFinal = 0;
		double lonFinal = 0;
		
		// ʵʱsrtm�ļ�
		String srtmName = srtmName0;
		Rasters rasters = rasters0;
		
		while(actionDistance<dmax) {
			enu[0] += dE; // ����ǰ��
			enu[1] += dN; // ����ǰ��
			
			// ��ENUת��ΪECEF
			double[][] Xyz = ENU2ECEF.getXyz(enu[0], enu[1], enu[2], X0, Y0, Z0, lat0, lon0);
			// ��ECEFת��ΪJWH
			double lat = ECEF2JWH.getB(Xyz[0][0], Xyz[1][0], Xyz[2][0]);
			double lon = ECEF2JWH.getL(Xyz[0][0], Xyz[1][0]);
			// ����lat,lon���������ļ����������γ��
			double latR = TiffMethod.getIndex(srtmName.substring(8, 10));
			double lonR = TiffMethod.getIndexLon(srtmName.substring(5, 7));
			// ����õ��ʵ��λ��
			int i = TiffMethod.getX(lon, lonR);
			int j = TiffMethod.getY(lat, latR);
			// ����õ㳬����[0,6000]��ȡֵ��Χ�������lat,lon�ж�Ӧ�ô򿪵��µ�srtm�ļ����滻ԭ����srtm�ļ�
			if(i<0||i>6000||j<0||j>6000) {
				srtmName = TiffMethod.getTiffMom(lat, lon);
				// ����ܴӴ�����ļ����ҵ�����ֱ��ʹ�ã������ٴδ򿪣�����Ҳ��������ٴδ����ļ�
				if(srtmName.equals(srtmName0)) {
					rasters = rasters0;
				}
				else if(srtmName.equals(srtmName1)) {
					rasters = rasters1;
				}
				else if(srtmName.equals(srtmName2)) {
					rasters = rasters2;
				}
				else if(srtmName.equals(srtmName3)) {
					rasters = rasters3;
				}
				else if(srtmName.equals(srtmName4)) {
					rasters = rasters4;
				}
				else if(srtmName.equals(srtmName5)) {
					rasters = rasters5;
				}
				else if(srtmName.equals(srtmName6)) {
					rasters = rasters6;
				}
				else if(srtmName.equals(srtmName7)) {
					rasters = rasters7;
				}
				else if(srtmName.equals(srtmName8)) {
					rasters = rasters8;
				}
				else {
					rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(srtmName));
				}
				// Ϊ��ȷ��˳������ִ�У��ٴ�ִ����i��j����
				latR = TiffMethod.getIndex(srtmName.substring(8, 10));
				lonR = TiffMethod.getIndexLon(srtmName.substring(5, 7));
				i = TiffMethod.getX(lon, lonR);
				j = TiffMethod.getY(lat, latR);
				if(j==6001) {
					j=6000;
				}
				if(i==6001) {
					i=6000;
				}
			}
			// �����߶�ֵ
			double h = rasters.getPixel(i, j)[0].doubleValue();
			// ���ڱν�
			double til = Tilting.getTilting(lat, lon, h, lat0, lon0, h0);
			// ������
			double yang = Tilting.getTilting(lat, lon, alt, lat0, lon0, h0);
			// �Ƚ�tilmax��til
			if(tilmax<til) {
				tilmax = til;
			}
			// �Ƚ����Ǻ��ڱν�
			if(tilmax>=yang) {
				// �����ǰ��ľ�γ�Ⱥ����þ��룬�Լ��������ر任��ľ�����Ϣ
				// ��ǰֻ����һ�����þ���
				double xCurrent = JWH2ECEF.getX(alt, lat, lon);
				double yCurrent = JWH2ECEF.getY(alt, lat, lon);
				double zCurrent = JWH2ECEF.getZ(alt, lat);
				double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
				actionDistance = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
				actionLat = lat;
				actionLon = lon;
				Point2D.Double pos = new Point2D.Double();
				pos = Lambert.lambert(actionLat, actionLon, lat0, lon0);
				actionLambertX = pos.x;
				actionLambertY = pos.y;
				break;
			}
			
			// ���㵱ǰ���þ���
			// ��ǰ(lat,lon,alt)תΪENU
			double xCurrent = JWH2ECEF.getX(alt, lat, lon);
			double yCurrent = JWH2ECEF.getY(alt, lat, lon);
			double zCurrent = JWH2ECEF.getZ(alt, lat);
			double[][] enuCurrent = ECEF2ENU.getEnu(xCurrent, yCurrent, zCurrent, X0, Y0, Z0, lat0, lon0);
			actionDistance = Math.sqrt(Math.pow(enuCurrent[0][0],2)+Math.pow(enuCurrent[1][0],2)+Math.pow(enuCurrent[2][0],2));
			latFinal = lat;
			lonFinal = lon;
		}
		
		if(actionDistance>=dmax) {
			actionLat = latFinal;
			actionLon = lonFinal;
			Point2D.Double pos = new Point2D.Double();
			pos = Lambert.lambert(latFinal, lonFinal, lat0, lon0);
			actionLambertX = pos.x;
			actionLambertY = pos.y;
		}
	}
}
