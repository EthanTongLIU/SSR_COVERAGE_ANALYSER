package ana.ioWindow;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;

/**
 * ����������л�ͼ����
 * @author ��ͨ
 *
 */
public class Bus {

	private static double paintCenterLon; 
	private static double paintCenterLat;
	private static double paintRmax;
	private static double[][] radars; // �״�վ
	private static double[] watchHeights; // �۲��߶�
	
	private static int[][] rgbColorArray = new int[][] {
		  {255,0,0,50},{255,165,0,50},
		  {255,255,0,50},{0,255,0,50},
		  {0,255,255,50},{0,0,255,50},
		  {238,130,238,50},{0,0,0,50}
	};
	
	/**
	 * �������г�Ա����
	 */
	public static void setAll() {
		setRadars(IOWindow.table);
		setWatchHeights(IOWindow.table_1);
		IOWindow.setPaintLat();
		paintCenterLon = Double.parseDouble( IOWindow.getPaintLat() );
		paintCenterLat = Double.parseDouble( IOWindow.getPaintLon() );
		paintRmax = Double.parseDouble( IOWindow.getPaintRmax() );
		setWatchHeightTableColor(IOWindow.table_1);
	}
	
	/**
	 * ���ø߶ȱ����ɫ
	 * @param table
	 */
	public static void setWatchHeightTableColor(Table table) {
		int heightCount = table.getItemCount(); // �߶Ȳ�����
		for(int i=0; i<heightCount; i++) {
			table.getItem(i).setBackground( 1 , new Color(null, rgbColorArray[i][0], rgbColorArray[i][1], rgbColorArray[i][2]) );
		}
	}
	
	/**
	 * �����״�վ��������γ�ȡ����ȡ��߶ȡ���װ�ǡ�������Χ
	 * @param table 
	 */
	public static void setRadars(Table table) {
		int radarCount = table.getItemCount(); // �״�����
		double[][] radars0 = new double[radarCount][5];
		
		for(int i=0; i<radarCount; i++) {
			radars0[i][0] = Double.parseDouble(table.getItem(i).getText(1));
			radars0[i][1] = Double.parseDouble(table.getItem(i).getText(2));
			radars0[i][2] = Double.parseDouble(table.getItem(i).getText(3));
			radars0[i][3] = Double.parseDouble(table.getItem(i).getText(4));
			radars0[i][4] = Double.parseDouble(table.getItem(i).getText(5));
		}
		
		radars = radars0;
	}
	
	/**
	 * �����״�վ
	 * @return
	 */
	public static double[][] getRadars() {
		return radars;
	}
	
	/**
	 * ���ù۲��߶�
	 */
	public static void setWatchHeights(Table table) {
		int hCount = table.getItemCount();
		double[] watchHeights0 = new double[hCount];
		for(int i=0; i<hCount; i++) {
			watchHeights0[i] = Double.parseDouble(table.getItem(i).getText(0));
		}
		watchHeights = watchHeights0;
	}
	
	/**
	 * ���ع۲��
	 * @return
	 */
	public static double[] getWatchHeights() {
		return watchHeights;
	}
	
	/**
	 * ���ػ�ͼ���ľ���
	 * @return
	 */
	public static double getPaintCenterLon() {
		return paintCenterLon;
	}
	
	/**
	 * ���ػ�ͼ����γ��
	 * @return
	 */
	public static double getPaintCenterLat() {
		return paintCenterLat;
	}
	
	/**
	 * ���ػ�ͼ��Χ
	 * @return
	 */
	public static double getPaintRmax() {
		return paintRmax;
	}
}
