package ana.ioWindow;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Table;

/**
 * 负责调配所有绘图参数
 * @author 刘通
 *
 */
public class Bus {

	private static double paintCenterLon; 
	private static double paintCenterLat;
	private static double paintRmax;
	private static double[][] radars; // 雷达站
	private static double[] watchHeights; // 观察层高度
	
	private static int[][] rgbColorArray = new int[][] {
		  {255,0,0,50},{255,165,0,50},
		  {255,255,0,50},{0,255,0,50},
		  {0,255,255,50},{0,0,255,50},
		  {238,130,238,50},{0,0,0,50}
	};
	
	/**
	 * 设置所有成员变量
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
	 * 设置高度表格颜色
	 * @param table
	 */
	public static void setWatchHeightTableColor(Table table) {
		int heightCount = table.getItemCount(); // 高度层数量
		for(int i=0; i<heightCount; i++) {
			table.getItem(i).setBackground( 1 , new Color(null, rgbColorArray[i][0], rgbColorArray[i][1], rgbColorArray[i][2]) );
		}
	}
	
	/**
	 * 设置雷达站，包括其纬度、经度、高度、安装角、威力范围
	 * @param table 
	 */
	public static void setRadars(Table table) {
		int radarCount = table.getItemCount(); // 雷达数量
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
	 * 返回雷达站
	 * @return
	 */
	public static double[][] getRadars() {
		return radars;
	}
	
	/**
	 * 设置观察层高度
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
	 * 返回观察层
	 * @return
	 */
	public static double[] getWatchHeights() {
		return watchHeights;
	}
	
	/**
	 * 返回绘图中心经度
	 * @return
	 */
	public static double getPaintCenterLon() {
		return paintCenterLon;
	}
	
	/**
	 * 返回绘图中心纬度
	 * @return
	 */
	public static double getPaintCenterLat() {
		return paintCenterLat;
	}
	
	/**
	 * 返回绘图范围
	 * @return
	 */
	public static double getPaintRmax() {
		return paintRmax;
	}
}
