package ana.visionrange;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mil.nga.tiff.FileDirectory;
import mil.nga.tiff.Rasters;
import mil.nga.tiff.TIFFImage;
import mil.nga.tiff.TiffReader;

/**
 * 该类是一系列处理Tiff文件的方法集合
 * @author 刘通
 *
 */
public class TiffMethod {
	
	/**
	 * 该方法根据给出的索引得到地形文件
	 * @param X
	 * @param Y
	 * @return 返回相应的地形文件
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
	 * 该方法根据给出的文件名得到地形文件
	 * @param tiffName 字符串
	 * @return 返回相应的地形文件
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
	 * 该方法用于从文件中生成Rasters并给出
	 * @param 地形文件
	 * @return 返回地形文件
	 */
	public static Rasters getRasters(File file) {
		
		TIFFImage tiffImage = null;
		try {
			tiffImage = TiffReader.readTiff(file);
		} catch (IOException e2) {
			// TODO 自动生成的 catch 块
			e2.printStackTrace();
		}
		
		List<FileDirectory> fileDirectories = tiffImage.getFileDirectories();
		FileDirectory fileDirectory = fileDirectories.get(0);
		Rasters rasters = fileDirectory.readRasters();
		
		return rasters;
	}
	
	/**
	 * 通过srtm文件名取得左上角的索引经纬度
	 * @param XX 经度方向
	 * @param YY 纬度方向
	 * @return 返回的数组第一个是左上角纬度，第二个是左上角经度
	 */
	public static double getIndex(String YY) {
		return 60-(Integer.valueOf(YY)-1)*5;
	}
	
	/**
	 * 通过srtm文件名取得左上角的索引经纬度
	 * @param XX 经度方向
	 * @param YY 纬度方向
	 * @return 返回的数组第一个是左上角纬度，第二个是左上角经度
	 */
	public static double getIndexLon(String XX) {
		return (Integer.valueOf(XX)-1)*5-180;
	}
	
	/**
	 * 在一幅tiff文件中将经纬度转换成对应的像素点位置
	 * @param lat 不使用度分秒，使用小数
	 * @param lon 不使用度分秒，使用小数
	 * @param latRef 是该区块的左上角参考纬度，不使用度分秒，使用小数
	 * @param lonRef 是该区块的左上角参考精度，不使用度分秒，使用小数
	 * @return
	 */
	public static int getX(double lon, double lonRef) {
		return (int) Math.round(6001*(lon-lonRef)/5.0);
	}
	
	/**
	 * 在一幅tiff文件中将经纬度转换成对应的像素点位置
	 * @param lat 不使用度分秒，使用小数
	 * @param lon 不使用度分秒，使用小数
	 * @param latRef 是该区块的左上角参考纬度，不使用度分秒，使用小数
	 * @param lonRef 是该区块的左上角参考精度，不使用度分秒，使用小数
	 * @return
	 */
	public static int getY(double lat, double latRef) {
		return (int) Math.round(6001*(latRef-lat)/5.0);
	}
	
	/**
	 * 在一幅tiff文件中将像素点位置转换成经纬度
	 * @param X
	 * @param Y
	 * @param latRef 是该区块的左上角参考纬度，不使用度分秒，使用小数
	 * @param lonRef 是该区块的左上角参考精度，不使用度分秒，使用小数
	 * @return 返回的经纬度是小数形式
	 */
	public static double getLat(int Y, double latRef) {
		double d = 5.0/6001.0; 
		return latRef - d*Y; 
	}
	
	/**
	 * 在一幅tiff文件中将像素点位置转换成经纬度
	 * @param X
	 * @param Y
	 * @param latRef 是该区块的左上角参考纬度，不使用度分秒，使用小数
	 * @param lonRef 是该区块的左上角参考精度，不使用度分秒，使用小数
	 * @return 返回的经纬度是小数形式
	 */
	public static double getLon(int X, double lonRef) {
		double d = 5.0/6001.0; 
		return lonRef + d*X;
	}
	
	/**
	 * 根据经纬度判断该点所在的Tiff文件，返回完整的Tiff文件字符串，比如"srtm_59_05.tif"
	 * @param 纬度，角度制
	 * @param 经度，角度制
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
