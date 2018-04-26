package ana.ImgDispose;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class SaveImage {
	
	/**
	 * �÷���ͨ���ƶ��ļ�������·�������ƺõ�ͼƬ�ļ��洢Ϊָ����ʽ��ͼƬ
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
