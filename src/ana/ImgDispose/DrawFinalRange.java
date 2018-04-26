package ana.ImgDispose;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import ana.ioWindow.IOWindow;
import ana.geotools.Lambert;
import ana.powerrange.VerticalDirectivityPattern;
import ana.visionrange.ActionDistance;
import ana.visionrange.TiffMethod;
import mil.nga.tiff.Rasters;

/**
 * ���Ƶ��Ϸ�Χ
 * @author ��ͨ
 */
public class DrawFinalRange {

	// Ԥ��8���߶Ȳ����ɫ
	private static int[][] rgbColorArray = new int[][] {
		  {255,0,0,50},{255,165,0,50},
		  {255,255,0,50},{0,255,0,50},
		  {0,255,255,50},{0,0,255,50},
		  {238,130,238,50},{0,0,0,50}
	};
	
	
	/**
	 * �÷���ʹ��BufferedImage���л�ͼ
	 * @param lat0 ��ͼ���ĵ��γ��
	 * @param lon0 ��ͼ���ĵ�ľ���
	 * @param rMax ��ͼ���뾶��Χ
	 * @param radars �״�վ
	 * @param alt �۲��
	 * @throws IOException 
	 */
	public static void createMapByBufferedImage(double lat0, double lon0, double rMax,
			double[][] radars, double[] alt) throws IOException {
		
		// ��һ��hMax���ⲿ����
		double hMax = 8848;
				
		// ��������
		double d = rMax/400; // ��800x800���ش�С�����ֶ��ߵ����������������ò����޸�
				
		// ����ѹ������
		double compressCoef = rMax/400;  // ��800x800���ش�С�µľ���ѹ�����ӣ��������޸�		

		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// 1. �򿪵����ļ�
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		long startTime = System.currentTimeMillis();   //��ȡ��ʼʱ��
		// ��9��srtm�ļ�
		// ����
		String srtmName0 = TiffMethod.getTiffMom(lat0, lon0);
		Rasters rasters0 = null;
		if(IOWindow.str.contains(srtmName0)) {
			File srtmFile0 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName0);
			rasters0 = TiffMethod.getRasters(srtmFile0);
			IOWindow.list.add("�� " + srtmName0);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName0);
		}
		
		// ���Ͻ�
		String srtmName1 = TiffMethod.getTiffMom(lat0+5, lon0-5);
		Rasters rasters1 = null;
		if(IOWindow.str.contains(srtmName1)) {
			File srtmFile1 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName1);
			rasters1 = TiffMethod.getRasters(srtmFile1);
			IOWindow.list.add("�� " + srtmName1);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName1);
		}
		
		// ����
		String srtmName2 = TiffMethod.getTiffMom(lat0+5, lon0);
		Rasters rasters2 = null;
		if(IOWindow.str.contains(srtmName2)) {
			File srtmFile2 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName2);
			rasters2 = TiffMethod.getRasters(srtmFile2);
			IOWindow.list.add("�� " + srtmName2);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName2);
		}
		
		// ���Ͻ�
		String srtmName3 = TiffMethod.getTiffMom(lat0+5, lon0+5);
		Rasters rasters3 = null;
		if(IOWindow.str.contains(srtmName3)) {
			File srtmFile3 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName3);
			rasters3 = TiffMethod.getRasters(srtmFile3);
			IOWindow.list.add("�� " + srtmName3);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName3);
		}
		
		// ����
		String srtmName5 = TiffMethod.getTiffMom(lat0, lon0+5);
		Rasters rasters5 = null;
		if(IOWindow.str.contains(srtmName5)) {
			File srtmFile5 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName5);
			rasters5 = TiffMethod.getRasters(srtmFile5);
			IOWindow.list.add("�� " + srtmName5);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName5);
		}
		
		// ���½�
		String srtmName7 = TiffMethod.getTiffMom(lat0-5, lon0+5);
		Rasters rasters7 = null;
		if(IOWindow.str.contains(srtmName7)) {
			File srtmFile7 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName7);
			rasters7 = TiffMethod.getRasters(srtmFile7);
			IOWindow.list.add("�� " + srtmName7);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName7);
		}
		
		// ����
		String srtmName4 = TiffMethod.getTiffMom(lat0-5, lon0);
		Rasters rasters4 = null;
		if(IOWindow.str.contains(srtmName4)) {
			File srtmFile4 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName4);
			rasters4 = TiffMethod.getRasters(srtmFile4);
			IOWindow.list.add("�� " + srtmName4);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName4);
		}
		
		// ���½�
		String srtmName6 = TiffMethod.getTiffMom(lat0-5, lon0-5);
		Rasters rasters6 = null;
		if(IOWindow.str.contains(srtmName6)) {
			File srtmFile6 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName6);
			rasters6 = TiffMethod.getRasters(srtmFile6);
			IOWindow.list.add("�� " + srtmName6);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName6);
		}
		
		// ����
		String srtmName8 = TiffMethod.getTiffMom(lat0, lon0-5);
		Rasters rasters8 = null;
		if(IOWindow.str.contains(srtmName8)) {
			File srtmFile8 = TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName8);
			rasters8 = TiffMethod.getRasters(srtmFile8);
			IOWindow.list.add("�� " + srtmName8);
		}
		else {
			IOWindow.list.add("δ�ҵ� " + srtmName8);
		}
		
		long endTime = System.currentTimeMillis(); //��ȡ����ʱ��
		
		IOWindow.list.add("�ļ���ʱ�� " + Math.floor( (endTime-startTime) /1000.0 ) + "s" );
		
		
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// 2. ���Ƶ���ͼ����
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		long startTime1 = System.currentTimeMillis();   //��ȡ��ʼʱ��
		// ���ڴ��д���һ��ͼ�����
		BufferedImage img = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		// ����һ������
		Graphics g = img.getGraphics(); 
		
		// x �� y �������ͼ�����ĵ����Ծ���
		double x = 0;
		double y = 0;
		
		// ʵʱsrtm�ļ�
		String srtmName = srtmName0;
		Rasters rasters = rasters0;
		
		// �����Ͻǲ���
		for(x = 0; Math.abs(x)<=rMax; x-=d) {
			for(y = 0; Math.abs(y)<=rMax; y+=d) {
				// ���������ط��任�����ʵ�ʾ�γ��
				Point2D.Double LatAndLon = Lambert.arclambert(y, x, lat0, lon0);
				double lat = LatAndLon.y;
				double lon = LatAndLon.x;
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
						rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName));
						System.out.println("�����ˣ�����9������ " + srtmName);
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
				// ���ݸ߶�ֵ��ɫ����Ϊ������ɫ
				int[] rgb = setColor(h, hMax);
				Color color = new Color(rgb[0], rgb[1], rgb[2]);
				g.setColor(color);
				// �ҵ��滭��ʵ����������
				int posX = 400 + (int) Math.round( -(-x/compressCoef) );
				int posY = 400 + (int) Math.round( -(y/compressCoef) );
				// ���Ƹõ�
				g.drawLine(posX, posY, posX, posY+1);
				
			}
		}
		
		// �����Ͻǲ���
		for(x = 0; Math.abs(x)<=rMax; x+=d) {
			for(y = 0; Math.abs(y)<=rMax; y+=d) {
				// ���������ط��任�����ʵ�ʾ�γ��
				Point2D.Double LatAndLon = Lambert.arclambert(y, x, lat0, lon0);
				double lat = LatAndLon.y;
				double lon = LatAndLon.x;
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
						rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName));
						System.out.println("�����ˣ�����9������ " + srtmName);
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
				// ���ݸ߶�ֵ��ɫ����Ϊ������ɫ
				int[] rgb = setColor(h, hMax);
				Color color = new Color(rgb[0], rgb[1], rgb[2]);
				g.setColor(color);
				// �ҵ��滭��ʵ����������
				int posX = 400 + (int) Math.round( (x/compressCoef) );
				int posY = 400 + (int) Math.round( (-y/compressCoef) );
				// ���Ƹõ�
				g.drawLine(posX, posY, posX, posY+1);
			}
		}
		
		// �����½ǲ���
		for(x = 0; Math.abs(x)<=rMax; x+=d) {
			for(y = 0; Math.abs(y)<=rMax; y-=d) {
				// ���������ط��任�����ʵ�ʾ�γ��
				Point2D.Double LatAndLon = Lambert.arclambert(y, x, lat0, lon0);
				double lat = LatAndLon.y;
				double lon = LatAndLon.x;
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
						rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName));
						System.out.println("�����ˣ�����9������ " + srtmName);
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
				// ���ݸ߶�ֵ��ɫ����Ϊ������ɫ
				int[] rgb = setColor(h, hMax);
				Color color = new Color(rgb[0], rgb[1], rgb[2]);
				g.setColor(color);
				// �ҵ��滭��ʵ����������
				int posX = 400 + (int) Math.round( (x/compressCoef) );
				int posY = 400 + (int) Math.round( (-y/compressCoef) );
				// ���Ƹõ�
				g.drawLine(posX, posY, posX, posY+1);
			}
		}
		
		// �����Ͻǲ���
		for(x = 0; Math.abs(x)<=rMax; x-=d) {
			for(y = 0; Math.abs(y)<=rMax; y-=d) {
				// ���������ط��任�����ʵ�ʾ�γ��
				Point2D.Double LatAndLon = Lambert.arclambert(y, x, lat0, lon0);
				double lat = LatAndLon.y;
				double lon = LatAndLon.x;
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
						rasters = TiffMethod.getRasters(TiffMethod.getTiffFile2(IOWindow.fileDialog.getFilterPath() + "\\" + srtmName));
						System.out.println("�����ˣ�����9������ " + srtmName);
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
				// ���ݸ߶�ֵ��ɫ����Ϊ������ɫ
				int[] rgb = setColor(h, hMax);
				Color color = new Color(rgb[0], rgb[1], rgb[2]);
				g.setColor(color);
				// �ҵ��滭��ʵ����������
				int posX = 400 + (int) Math.round( (x/compressCoef) );
				int posY = 400 + (int) Math.round( (-y/compressCoef) );
				// ���Ƹõ�
				g.drawLine(posX, posY, posX, posY+1);
			}
		}
		long endTime1 = System.currentTimeMillis(); //��ȡ����ʱ��
		IOWindow.list.add("����ͼ����ʱ�� " + Math.floor( (endTime1-startTime1) /1000.0 ) + "s" );
		
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// 3. ���Ƶ��Ϸ�Χ
		// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		long startTime2 = System.currentTimeMillis();   //��ȡ��ʼʱ��

		// ����Χ���飬1��1��
		int[] polyX = new int[361];
		int[] polyY = new int[361];
		
		// ��ʼ���������״�ĸ��Ƿ�Χ
		// �״�վѭ��
		for(int k=0; k<radars.length; k++) {
			
			// ���״�վ������ƽ�Ƶ���������������ĵĻ�ͼλ�ã�ƽ����ͨ�������ر任����
			Point2D.Double pos = new Point2D.Double();
			pos = Lambert.lambert(radars[k][1], radars[k][0], lat0, lon0);
			int xTrans = (int)(pos.x/compressCoef);
			int yTrans = (int)(-pos.y/compressCoef);
			// �״�վ��ͼվַ
			int radarX = 400 + xTrans;
			int radarY = 400 + yTrans;
			// �����״�վ
			Color colorSat = new Color(255, 255, 0);
			g.setColor(colorSat);
			g.fillRect(radarX-5, radarY-5, 10, 10);
			
			// �߶Ȳ�ѭ��
			for(int n=alt.length-1; n>=0; n--) {					
				// ��ʼ���Ƹ��Ƿ�Χͼ��

				// ���Ƶ��Ϸ�Χ
				// %% %% %% %% %% %% %% %% %% %% %% %% 
				VerticalDirectivityPattern.findActionDistanceInfo(radars[k][1], radars[k][0], radars[k][2], 30, radars[k][4]*1000, alt[n], 90, radars[k][3]);
				// ��Ȧ
				double posx1 = VerticalDirectivityPattern.extern[0];
				double posy1 = VerticalDirectivityPattern.extern[1];
				double externRealR = Math.sqrt( Math.pow(posx1, 2) + Math.pow(posy1, 2) );
				// %% %% %% %% %% %% %% %% %% %% %% %% 
				
				// ����Χ����Ƕȿ���
				for(int i=0; i<=360; i++) {
					// ����Χ����Ƕȿ���
					ActionDistance.findActionDistanceInfo(radars[k][1], radars[k][0], radars[k][2], i, radars[k][4]*1000, alt[n], 90, 
							srtmName0, rasters0,
							srtmName1, srtmName2, srtmName3, srtmName4, srtmName5, srtmName6, srtmName7, srtmName8,
							rasters1, rasters2, rasters3, rasters4, rasters5, rasters6, rasters7, rasters8);
					double posx = ActionDistance.actionLambertX;
					double posy = ActionDistance.actionLambertY;	
					
					// ���Ƶ��Ϸ�Χ
					// %% %% %% %% %% %% %% %% %% %% %% %% 
					if( Math.pow(externRealR, 2) < ( Math.pow(posx, 2) + Math.pow(posy, 2) ) ) {
						posx = externRealR * Math.sin(Math.toRadians(i));
						posy = externRealR * Math.cos(Math.toRadians(i));
					}
					// %% %% %% %% %% %% %% %% %% %% %% %% 
					
					// �����ÿһ���״�վΪͼƬ���ģ�xR��yR���������ͼƬ���ĵ�ʵ��λ��
					int posX = (int) Math.round( (posx/compressCoef) );
					int posY = (int) Math.round( (-posy/compressCoef) );
					int xR = 0;
					int yR = 0;
					if(posX<0) {
						xR = 400 + posX;
					}
					else {
						xR = 400 + posX;
					}
					
					if(posY<0) {
						yR = 400 + posY;
					}
					else {
						yR = 400 + posY;
					}
					// ƽ���״�վ
					xR += xTrans;
					yR += yTrans;
					polyX[i] = xR;
					polyY[i] = yR;
				}
				
				// ���Ƶ��Ϸ�Χ͸��ͼ�����״ﵥ�߶Ȳ�
				Color colorPoly = new Color(rgbColorArray[n][0], rgbColorArray[n][1], rgbColorArray[n][2] , 50);
				g.setColor(colorPoly);
				g.fillPolygon(polyX, polyY, 361);
				Color colorPolyLine = new Color(rgbColorArray[n][0], rgbColorArray[n][1], rgbColorArray[n][2]);
				g.setColor(colorPolyLine);
				g.drawPolyline(polyX, polyY, 361);
				
				// ����������ΧԲ��ͼ�ߣ����״ﵥ�߶Ȳ�
				// ����ֻɨ��һ������ֻɨ��30�ȣ�Ȼ��Բ����
//				VerticalDirectivityPattern.findActionDistanceInfo(radars[k][1], radars[k][0], radars[k][2], 30, range, alt[n], 90);
//				// ��Ȧ
//				double posx1 = VerticalDirectivityPattern.extern[0];
//				double posy1 = VerticalDirectivityPattern.extern[1];
//				int posX1 = (int) Math.round( (posx1/compressCoef) );
//				int posY1 = (int) Math.round( (-posy1/compressCoef) );
//				int externR = (int) Math.sqrt( Math.pow(posX1, 2) + Math.pow(posY1, 2) );
//				Color colorExternR = new Color(0, 0, 255, 30);
//				g.setColor(colorExternR);
//				g.fillOval(radarX-externR, radarY-externR, externR*2, externR*2);
//				// ��Ȧ
//				double posx2 = VerticalDirectivityPattern.inne[0];
//				double posy2 = VerticalDirectivityPattern.inne[1];
//				int posX2 = (int) Math.round( (posx2/compressCoef) );
//				int posY2 = (int) Math.round( (-posy2/compressCoef) );
//				int inneR = (int) Math.sqrt( Math.pow(posX2, 2) + Math.pow(posY2, 2) );
//				Color colorInneR = new Color(255, 0, 0, 100);
//				g.setColor(colorInneR);
//				g.fillOval(radarX-inneR, radarY-inneR, inneR*2, inneR*2);
				
					
			}// �߶Ȳ�ѭ������
			
			// ���״����������Χ�͵���뾶�������Ʒ�Χ����
			// ������Χ��һ��Բ�ܣ�
			int powerRangeR = (int) (radars[k][4]*1000.0/compressCoef);
			Color colorExternR = new Color(0, 0, 255, 255);
			g.setColor(colorExternR);
			g.drawOval(radarX-powerRangeR, radarY-powerRangeR, powerRangeR*2, powerRangeR*2);
			// �������ƣ�һ��Բ�ܣ�
			
		}// �״�վѭ������
		// �����״︲�Ƿ�Χ���ƽ���
		
		// ���Ʊ�����
		Color strColor = new Color(255, 255, 0, 255);
		g.setColor(strColor);
		g.drawLine(10, 770, 410, 770);
		
		int dR = (int)(rMax/1000.0/4.0);
		
		g.drawLine(10, 770, 10, 755);
		g.drawString("0", 7, 785);
		g.drawLine(110, 770, 110, 755);
		g.drawString(String.valueOf(dR), 107, 785);
		g.drawLine(210, 770, 210, 755);
		g.drawString(String.valueOf(2*dR), 207, 785);
		g.drawLine(310, 770, 310, 755);
		g.drawString(String.valueOf(3*dR), 307, 785);
		g.drawLine(410, 770, 410, 755);
		g.drawString(String.valueOf(4*dR), 407, 785);
		
		g.drawString("km", 432, 785);
		
		long endTime2 = System.currentTimeMillis(); //��ȡ����ʱ��
		IOWindow.list.add("���Ƿ�Χ��ͼʱ�� " + Math.floor( (endTime2-startTime2)/1000.0 ) + "s" );
		
		//��ͼƬ�������ļ��ķ�ʽ���  
	    File f = new File("D:dem.png");  
	    ImageIO.write(img, "png", f); 
	    
	}
	
	
	/**
	 * �÷���ʹ��awt��ɫ
	 */
	public static int[] setColor(double h, double hMax) {
		int[] color = new int[3];
		// ����64��RGB��ɫ
		int[][] rgbColorArray = new int[][] {
			  { 0,102,51},
			  { 2,106,51},
			  { 5,111,51},
			  { 8,116,52},
			  {12,121,53},
			  {16,126,53},
			  {19,131,54},
			  {24,136,55},
			  {28,140,56},
			  {33,145,57},
			  {38,150,58},
			  {43,155,60},
			  {48,160,62},
			  {54,165,64},
			  { 60,170, 66},
			  { 66,174, 69},
			  { 73,179, 73},
			  { 83,184, 79},
			  { 93,189, 86},
			  {104,194, 93},
			  {114,199,101},
			  {124,203,108},
			  {134,208,116},
			  {145,213,124},
			  {155,218,133},
			  {165,223,141},
			  {175,228,150},
			  {186,233,159},
			  {195,238,169},
			  {205,242,178},
			  {215,247,188},
			  {224,252,198},
			  {226,252,198},
			  {221,247,188},
			  {216,242,178},
			  {211,238,169},
			  {207,233,159},
			  {204,228,150},
			  {200,223,141},
			  {197,218,133},
			  {195,213,124},
			  {192,208,116},
			  {190,204,108},
			  {188,199,101},
			  {187,194, 93},
			  {185,189, 86},
			  {184,184, 79},
			  {179,176,73},
			  {174,167,66},
			  {170,158,60},
			  {165,150,54},
			  {160,141,48},
			  {155,132,43},
			  {150,123,38},
			  {145,114,33},
			  {140,106,28},
			  {136, 97,24},
			  {131, 89,19},
			  {126, 80,16},
			  {121, 72,12},
			  {116, 64, 8},
			  {111, 57, 5},
			  {106, 49, 2},
			  {102, 42, 0}
		};
		
		if(h>0) {
			double dh = hMax/64;
			int RGBIndex = (int) Math.floor(h/dh);
			color[0] = rgbColorArray[RGBIndex][0];
			color[1] = rgbColorArray[RGBIndex][1];
			color[2] = rgbColorArray[RGBIndex][2];
		}
		else {
			// ���Ǹ߶�ֵС��0�ĵ�ȫ����ɫΪ����ɫ
			color[0] = 0;
			color[1] = 69;
			color[2] = 107;
		}
		return color;
	}
	
}
