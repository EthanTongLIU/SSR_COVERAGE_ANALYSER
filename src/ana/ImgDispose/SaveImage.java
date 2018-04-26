package ana.ImgDispose;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class SaveImage {
	
	/**
	 * 该方法通过制定文件名及其路径将绘制好的图片文件存储为指定格式的图片
	 * @param fileName
	 */
	public static void saveImage(String fileName, Image image, String format) {
		
		ImageData data = image.getImageData();
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[]{data};
		if(format.equals("png")) {
			loader.save(fileName, SWT.IMAGE_PNG);
		}
		if(format.equals("jpg")) {
			loader.save(fileName, SWT.IMAGE_JPEG);
		}
		if(format.equals("bmp")) {
			loader.save(fileName, SWT.IMAGE_BMP);
		}
	}
	
}
