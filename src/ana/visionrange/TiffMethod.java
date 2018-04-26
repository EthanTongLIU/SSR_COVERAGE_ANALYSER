package ana.visionrange;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mil.nga.tiff.FileDirectory;
import mil.nga.tiff.Rasters;
import mil.nga.tiff.TIFFImage;
import mil.nga.tiff.TiffReader;

/**
 * ������һϵ�д���Tiff�ļ��ķ�������
 * @author ��ͨ
 *
 */
public class TiffMethod {
	
	/**
	 * �÷������ݸ����������õ������ļ�
	 * @param X
	 * @param Y
	 * @return ������Ӧ�ĵ����ļ�
	 */
	public static File getTiffFile(String X, String Y) {
		
		String fileName = "srtm_"+X+"_"+Y+".tif";
		File file = new File(fileName);
		if (!file.exists()) {
			throw new IllegalArgumentException("TIFF file does not exist: "
					+ file.getAbsolutePath());
		}
		return file;
	}
	
	/**
	 * �÷������ݸ������ļ����õ������ļ�
	 * @param tiffName �ַ���
	 * @return ������Ӧ�ĵ����ļ�
	 */
	public static File getTiffFile2(String tiffName) {
		
		File file = new File(tiffName);
		if (!file.exists()) {
			throw new IllegalArgumentException("TIFF file does not exist: "
					+ file.getAbsolutePath());
		}
		return file;
	}
	
	
	/**
	 * �÷������ڴ��ļ�������Rasters������
	 * @param �����ļ�
	 * @return ���ص����ļ�
	 */
	public static Rasters getRasters(File file) {
		
		TIFFImage tiffImage = null;
		try {
			tiffImage = TiffReader.readTiff(file);
		} catch (IOException e2) {
			// TODO �Զ����ɵ� catch ��
			e2.printStackTrace();
		}
		
		List<FileDirectory> fileDirectories = tiffImage.getFileDirectories();
		FileDirectory fileDirectory = fileDirectories.get(0);
		Rasters rasters = fileDirectory.readRasters();
		
		return rasters;
	}
	
	/**
	 * ͨ��srtm�ļ���ȡ�����Ͻǵ�������γ��
	 * @param XX ���ȷ���
	 * @param YY γ�ȷ���
	 * @return ���ص������һ�������Ͻ�γ�ȣ��ڶ��������ϽǾ���
	 */
	public static double getIndex(String YY) {
		return 60-(Integer.valueOf(YY)-1)*5;
	}
	
	/**
	 * ͨ��srtm�ļ���ȡ�����Ͻǵ�������γ��
	 * @param XX ���ȷ���
	 * @param YY γ�ȷ���
	 * @return ���ص������һ�������Ͻ�γ�ȣ��ڶ��������ϽǾ���
	 */
	public static double getIndexLon(String XX) {
		return (Integer.valueOf(XX)-1)*5-180;
	}
	
	/**
	 * ��һ��tiff�ļ��н���γ��ת���ɶ�Ӧ�����ص�λ��
	 * @param lat ��ʹ�öȷ��룬ʹ��С��
	 * @param lon ��ʹ�öȷ��룬ʹ��С��
	 * @param latRef �Ǹ���������Ͻǲο�γ�ȣ���ʹ�öȷ��룬ʹ��С��
	 * @param lonRef �Ǹ���������Ͻǲο����ȣ���ʹ�öȷ��룬ʹ��С��
	 * @return
	 */
	public static int getX(double lon, double lonRef) {
		return (int) Math.round(6001*(lon-lonRef)/5.0);
	}
	
	/**
	 * ��һ��tiff�ļ��н���γ��ת���ɶ�Ӧ�����ص�λ��
	 * @param lat ��ʹ�öȷ��룬ʹ��С��
	 * @param lon ��ʹ�öȷ��룬ʹ��С��
	 * @param latRef �Ǹ���������Ͻǲο�γ�ȣ���ʹ�öȷ��룬ʹ��С��
	 * @param lonRef �Ǹ���������Ͻǲο����ȣ���ʹ�öȷ��룬ʹ��С��
	 * @return
	 */
	public static int getY(double lat, double latRef) {
		return (int) Math.round(6001*(latRef-lat)/5.0);
	}
	
	/**
	 * ��һ��tiff�ļ��н����ص�λ��ת���ɾ�γ��
	 * @param X
	 * @param Y
	 * @param latRef �Ǹ���������Ͻǲο�γ�ȣ���ʹ�öȷ��룬ʹ��С��
	 * @param lonRef �Ǹ���������Ͻǲο����ȣ���ʹ�öȷ��룬ʹ��С��
	 * @return ���صľ�γ����С����ʽ
	 */
	public static double getLat(int Y, double latRef) {
		double d = 5.0/6001.0; 
		return latRef - d*Y; 
	}
	
	/**
	 * ��һ��tiff�ļ��н����ص�λ��ת���ɾ�γ��
	 * @param X
	 * @param Y
	 * @param latRef �Ǹ���������Ͻǲο�γ�ȣ���ʹ�öȷ��룬ʹ��С��
	 * @param lonRef �Ǹ���������Ͻǲο����ȣ���ʹ�öȷ��룬ʹ��С��
	 * @return ���صľ�γ����С����ʽ
	 */
	public static double getLon(int X, double lonRef) {
		double d = 5.0/6001.0; 
		return lonRef + d*X;
	}
	
	/**
	 * ���ݾ�γ���жϸõ����ڵ�Tiff�ļ�������������Tiff�ļ��ַ���������"srtm_59_05.tif"
	 * @param γ�ȣ��Ƕ���
	 * @param ���ȣ��Ƕ���
	 * @return
	 */
	public static String getTiffMom(double lat, double lon) {
		int XX = (int) Math.floor( (lon+180.0)/5.0 ) + 1;
		int YY = (int) Math.floor( (60.0-lat)/5.0)+1;
		String TiffName = null;
		if(XX<10) {
			TiffName = "srtm_" + "0" + String.valueOf(XX) + "_" + String.valueOf(YY) + ".tif";
		}
		if(YY>=10) {
			TiffName = "srtm_" + String.valueOf(XX) + "_" + String.valueOf(YY) + ".tif";
		}
		else {
			TiffName = "srtm_" + String.valueOf(XX) + "_" + "0" + String.valueOf(YY) + ".tif";
		}
		return TiffName;
	}
	
}
