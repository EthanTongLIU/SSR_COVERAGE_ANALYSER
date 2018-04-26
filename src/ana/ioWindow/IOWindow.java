package ana.ioWindow;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.IOException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import ana.ImgDispose.DrawFinalRange;
import ana.ImgDispose.DrawPowerRange;
import ana.ImgDispose.DrawVisionRange;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class IOWindow {
	public static Table table; // 记录雷达站
	public static Table table_1; // 记录观察层高度
	public static Table table_2; // 记录导入的srtm文件
	private static Text text; // 绘图中心的经度
	private static Text text_1; // 绘图中心的纬度
	private static Text text_2; // 绘图的范围
	private static String lat0; // 绘图中心的经度
	private static String lon0; // 绘图中心的纬度
	private static String rMax; // 绘图的范围
	private static Button button_4; // 绘图中心与雷达站重合
	public static FileDialog fileDialog; // 文件选择目录
	public static String str; //导入文件字符串
	public static List list; // 系统信息框
	
	public static Label lblNewLabel; // 经度
	public static Label lblNewLabel_1; // 纬度
	
	/**
	 * 该方法设置绘图中心的纬度、经度、范围
	 */
	public static void setPaintLat() {
		lat0 = text.getText();
		lon0 = text_1.getText();	
		rMax = text_2.getText();
	}
	
	/**
	 * 返回绘图中心的纬度 
	 * @return
	 */
	public static String getPaintLat() {
		return lat0;
	}

	/**
	 * 返回绘图中心的经度
	 * @return
	 */
	public static String getPaintLon() {
		return lon0;
	}
	
	/**
	 * 返回绘图范围
	 * @return
	 */
	public static String getPaintRmax() {
		return rMax;
	}
	
	/**
	 * Launch the application.
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.CLOSE|SWT.MIN);
		String path = System.getProperty("user.dir") + "\\src\\Img\\radar.jpg";
		shell.setImage(SWTResourceManager.getImage(path));
		shell.setSize(1368, 983);
		shell.setText("\u96F7\u8FBE\u8986\u76D6\u8303\u56F4\u5206\u6790\u7CFB\u7EDF");
		shell.setLayout(null);
		
		Group group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(10, 638, 224, 290);
		group_1.setText("\u5730\u5F62\u6587\u4EF6");
		group_1.setLayout(null);
		
		Button button = new Button(group_1, SWT.NONE);
		button.setBounds(27, 33, 76, 30);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fileDialog = new FileDialog(shell,SWT.OPEN|SWT.MULTI);
				fileDialog.setFilterPath("C:/");
				fileDialog.setFilterNames(new String[] {"TIF文件(*.tif)"});
				fileDialog.setFilterExtensions(new String[] {"*.tif"});
				fileDialog.setText("选择地形文件");
				String fileName = fileDialog.open();
				String[] fileNames = fileDialog.getFileNames();
				str = new String();
				for(int i=0; i<fileNames.length;i++) {
					str += fileDialog.getFilterPath() + "\\" + fileNames[i] + "\n";
					TableItem tableItem = new TableItem(table_2, SWT.NONE);
					tableItem.setText(new String[] { String.valueOf( table_2.getItemCount() ) , fileNames[i] });
				}
				MessageDialog.openInformation(null, null, 
						"导入的文件：" + "\n" + str);
			}
		});
		button.setText("\u5BFC\u5165");
		
		Button button_10 = new Button(group_1, SWT.NONE);
		button_10.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				table_2.removeAll();
			}
		});
		button_10.setBounds(116, 33, 76, 30);
		button_10.setText("\u6E05\u7A7A");
		
		table_2 = new Table(group_1, SWT.BORDER | SWT.FULL_SELECTION);
		table_2.setBounds(10, 69, 206, 210);
		table_2.setHeaderVisible(true);
		
		TableColumn tableColumn_6 = new TableColumn(table_2, SWT.NONE);
		tableColumn_6.setWidth(46);
		tableColumn_6.setText("\u5E8F\u53F7");
		
		TableColumn tableColumn_7 = new TableColumn(table_2, SWT.NONE);
		tableColumn_7.setWidth(141);
		tableColumn_7.setText("\u6587\u4EF6\u540D");
		
		Group group_2 = new Group(shell, SWT.NONE);
		group_2.setBounds(12, 32, 222, 290);
		group_2.setText("\u96F7\u8FBE\u7AD9\u5740");
		
		Button button_6 = new Button(group_2, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputRadar.launch();
			}
		});
		button_6.setBounds(29, 33, 76, 30);
		button_6.setText("\u6DFB\u52A0");
		
		Button button_7 = new Button(group_2, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selindex = table.getSelectionIndices();
				table.remove(selindex);
			}
		});
		button_7.setBounds(120, 33, 76, 30);
		button_7.setText("\u5220\u9664");
		
		table = new Table(group_2, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.setBounds(10, 69, 202, 210);
		table.setHeaderVisible(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				int selIndex = table.getSelectionIndex();
				TableItem selItem = table.getItem(selIndex);
				String str = "雷达站" + selItem.getText(0) + "：" +
						"\n\n经度：" + selItem.getText(1) + "度" +
						"\n\n纬度：" + selItem.getText(2) + "度" +
						"\n\n高度：" + selItem.getText(3) + "米" +
						"\n\n天线安装角：" + selItem.getText(4) + "度" +
						"\n\n威力范围：" + selItem.getText(5) + "公里";
				MessageDialog.openInformation(null, null, str);
			}
		});
		
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(48);
		tableColumn.setText("\u5E8F\u53F7");
		
		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(50);
		tableColumn_1.setText("\u7ECF\u5EA6");
		
		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(50);
		tableColumn_2.setText("\u7EAC\u5EA6");
		
		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(50);
		tableColumn_3.setText("\u9AD8\u5EA6");
		
		TableColumn tableColumn_8 = new TableColumn(table, SWT.NONE);
		tableColumn_8.setWidth(65);
		tableColumn_8.setText("\u5B89\u88C5\u89D2");
		
		TableColumn tableColumn_9 = new TableColumn(table, SWT.NONE);
		tableColumn_9.setWidth(73);
		tableColumn_9.setText("\u6700\u5927\u5A01\u529B");
		
		Group group_3 = new Group(shell, SWT.NONE);
		group_3.setBounds(12, 334, 222, 290);
		group_3.setText("\u89C2\u5BDF\u5C42\u9AD8\u5EA6");
		
		table_1 = new Table(group_3, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table_1.setBounds(10, 69, 205, 210);
		table_1.setHeaderVisible(true);
		table_1.addMouseListener(new MouseAdapter() {
			public void mouseDoubleClick(MouseEvent e) {
				int selIndex = table_1.getSelectionIndex();
				TableItem selItem = table_1.getItem(selIndex);
				String str = "观察层高度：" + selItem.getText(0) + "米";
				MessageDialog.openInformation(null, null, str);
			}
		});
		
		TableColumn tableColumn_4 = new TableColumn(table_1, SWT.NONE);
		tableColumn_4.setWidth(89);
		tableColumn_4.setText("\u9AD8\u5EA6");
		
		TableColumn tableColumn_5 = new TableColumn(table_1, SWT.NONE);
		tableColumn_5.setWidth(105);
		tableColumn_5.setText("\u989C\u8272");
		
		Button button_8 = new Button(group_3, SWT.NONE);
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				InputAlt.launch();
			}
		});
		button_8.setBounds(28, 33, 76, 30);
		button_8.setText("\u6DFB\u52A0");
		
		Button button_9 = new Button(group_3, SWT.NONE);
		button_9.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] selindex1 = table_1.getSelectionIndices();
				table_1.remove(selindex1);
			}
		});
		button_9.setBounds(118, 33, 76, 30);
		button_9.setText("\u5220\u9664");
		
		// 创建画布
		Canvas canvas = new Canvas(shell, SWT.BORDER);
		canvas.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		canvas.setBounds(240, 32, 870, 870);

		Group group_4 = new Group(shell, SWT.NONE);
		group_4.setBounds(1116, 312, 222, 86);
		group_4.setText("\u8986\u76D6\u8303\u56F4");
		
		Button button_1 = new Button(group_4, SWT.CHECK);
		button_1.setBounds(24, 36, 54, 20);
		button_1.setText("\u5A01\u529B");
		
		Button button_2 = new Button(group_4, SWT.CHECK);
		button_2.setBounds(84, 36, 54, 20);
		button_2.setText("\u89C6\u57DF");
		
		Button button_3 = new Button(group_4, SWT.CHECK);
		button_3.setBounds(146, 36, 54, 20);
		button_3.setText("\u53E0\u5408");
		
		ViewForm viewForm = new ViewForm(shell, SWT.NONE);
		viewForm.setBounds(10, 10, 138, 0);
		
		Group group = new Group(shell, SWT.NONE);
		group.setText("\u7ED8\u56FE\u4E2D\u5FC3");
		group.setBounds(1116, 32, 222, 274);
		
		button_4 = new Button(group, SWT.CHECK);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 计算多雷达的几何中心
				Bus.setRadars(table);
				double radars[][] = Bus.getRadars();
				int radarNum = radars.length;
				double lonCenter = 0; // 经度几何中心
				double latCenter = 0; // 纬度几何中心
				// 计算经度和纬度的几何中心
				for(int i=0; i<radarNum; i++) {
					lonCenter += radars[i][0];
					latCenter += radars[i][1];
				}
				lonCenter = lonCenter/radarNum;
				latCenter = latCenter/radarNum;
				// 将计算好的几何中心加载到text和text_1
				text.setText(String.valueOf(lonCenter));
				text_1.setText(String.valueOf(latCenter));
			}
		});
		
		button_4.setBounds(23, 47, 179, 20);
		button_4.setText("\u8BBE\u7F6E\u4E3A\u591A\u96F7\u8FBE\u51E0\u4F55\u4E2D\u5FC3");
		
		text = new Text(group, SWT.BORDER);
		text.setBounds(62, 96, 95, 26);
		
		text_1 = new Text(group, SWT.BORDER);
		text_1.setBounds(62, 145, 95, 26);
		
		text_2 = new Text(group, SWT.BORDER);
		text_2.setBounds(62, 195, 95, 26);
		
		Label label = new Label(group, SWT.NONE);
		label.setBounds(20, 99, 36, 20);
		label.setText("\u7ECF\u5EA6");
		
		Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(20, 148, 36, 20);
		label_1.setText("\u7EAC\u5EA6");
		
		Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(20, 198, 36, 20);
		label_2.setText("\u8303\u56F4");
		
		Label label_3 = new Label(group, SWT.NONE);
		label_3.setBounds(175, 99, 27, 20);
		label_3.setText("\u5EA6");
		
		Label label_4 = new Label(group, SWT.NONE);
		label_4.setBounds(175, 148, 27, 20);
		label_4.setText("\u5EA6");
		
		Label label_5 = new Label(group, SWT.NONE);
		label_5.setBounds(166, 198, 36, 20);
		label_5.setText("\u516C\u91CC");
		
		Group group_5 = new Group(shell, SWT.NONE);
		group_5.setText("\u7ED8\u56FE\u9009\u9879");
		group_5.setBounds(1116, 404, 224, 101);
		
		Button button_5 = new Button(group_5, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				list.removeAll(); // 清空列表框
				Bus.setAll(); // 设置所有参数
				
				long startTime = System.currentTimeMillis();   //获取开始时间
				try {
					if(button_3.getSelection()) {
						DrawFinalRange.createMapByBufferedImage(Bus.getPaintCenterLat(), Bus.getPaintCenterLon(), 
								Bus.getPaintRmax()*1000, Bus.getRadars(), Bus.getWatchHeights());
					}
					if(button_1.getSelection()) {
						DrawPowerRange.createMapByBufferedImage(Bus.getPaintCenterLat(), Bus.getPaintCenterLon(), 
								Bus.getPaintRmax()*1000, Bus.getRadars(), Bus.getWatchHeights());
					}
					if(button_2.getSelection()) {
						DrawVisionRange.createMapByBufferedImage(Bus.getPaintCenterLat(), Bus.getPaintCenterLon(), 
								Bus.getPaintRmax()*1000, Bus.getRadars(), Bus.getWatchHeights());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				long endTime = System.currentTimeMillis(); //获取结束时间
				list.add("运行总时间 " + Math.floor( (endTime-startTime)/1000.0 ) + "s" );
				
				// 将图像加载到屏幕上
				Image img = new Image(display, "D:/dem.png");
				canvas.addPaintListener( new PaintListener() {
					public void paintControl(PaintEvent e) {
						e.gc.drawImage(img, 0, 0, 800, 800, 0, 0, 870, 870);
					}
				});
				// 加载结束
				
			}
		});
		button_5.setBounds(20, 43, 84, 30);
		button_5.setText("\u5F00\u59CB\u7ED8\u56FE");
		
		Button button_11 = new Button(group_5, SWT.NONE);
		button_11.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
			}
		});
		button_11.setBounds(115, 43, 84, 30);
		button_11.setText("\u4FDD\u5B58\u56FE\u50CF");
		

		
		Group group_6 = new Group(shell, SWT.NONE);
		group_6.setText("\u7CFB\u7EDF\u4FE1\u606F");
		group_6.setBounds(1116, 511, 224, 308);
		
		list = new List(group_6, SWT.BORDER);
		list.setBounds(10, 23, 204, 273);
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		toolBar.setBounds(0, 0, 1431, 28);
		
		ToolItem toolItem = new ToolItem(toolBar, SWT.NONE);
		toolItem.setText("\u5173\u4E8E");
		
		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
		toolItem_1.setText("\u5E2E\u52A9");
		
		Canvas canvas_1 = new Canvas(shell, SWT.NONE);
		canvas_1.setBounds(1195, 825, 77, 77);
		
		Label lblCopyrightFly = new Label(shell, SWT.NONE);
		lblCopyrightFly.setBounds(1151, 908, 179, 20);
		lblCopyrightFly.setText("Copyright \u00A9 FLY 2018");
		
		canvas_1.addPaintListener( new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(new Image(display, path), 0, 0, 260, 260, 0, 0, 77, 77);
			}
		});
		
//		Composite composite = new Composite(shell, SWT.BORDER | SWT.EMBEDDED);
//		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
//		composite.setBounds(238, 32, 800, 800);
//		
//		// 设置一个frame
//		Frame frame = SWT_AWT.new_Frame(composite);
//		frame.setLayout(null); // 设置其布局，frame的尺寸无需指定，没卵用，其完全跟随composite
//		frame.setBackground(Color.GRAY);
//		
//		//PanelTest jpanel = new PanelTest();
//		ZoomPanel jpanel = new ZoomPanel("D://dem.png" );
//	    
//		frame.add(jpanel, BorderLayout.CENTER);
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(240, 908, 870, 30);
		
		Label label_6 = new Label(composite_1, SWT.NONE);
		label_6.setBounds(10, 0, 34, 20);
		label_6.setText("\u7ECF\u5EA6:");
		
		lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(50, 0, 147, 20);
		
		Label label_7 = new Label(composite_1, SWT.NONE);
		label_7.setBounds(203, 0, 34, 20);
		label_7.setText("\u7EAC\u5EA6:");
		
		lblNewLabel_1 = new Label(composite_1, SWT.NONE);
		lblNewLabel_1.setBounds(243, 0, 147, 20);

		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
