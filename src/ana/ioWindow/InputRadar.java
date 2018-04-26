package ana.ioWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class InputRadar extends Shell {
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3; // 安装角
	private Text text_4; // 最大威力
	public static String lon0; 
	public static String lat0;
	public static String h0;
	public static String alpha;
	public static String rMax;


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void launch() {
		try {
			Display display = Display.getDefault();
			InputRadar shell = new InputRadar(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public InputRadar(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(171, 20, 149, 26);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(63, 23, 90, 20);
		label.setText("\u7ECF\u5EA6\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(63, 63, 90, 20);
		label_1.setText("\u7EAC\u5EA6\uFF1A");
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(171, 60, 149, 26);
		
		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(63, 103, 90, 20);
		label_2.setText("\u9AD8\u5EA6\uFF1A");
		
		text_2 = new Text(this, SWT.BORDER);
		text_2.setBounds(171, 100, 149, 26);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(getShell(),SWT.ICON_WARNING|SWT.OK);
				if(text.getText().equals("")||text_1.getText().equals("")||text_2.getText().equals("")) {
					box.setText("提醒");
					box.setMessage("雷达站址信息不完整，请补全信息！");
					box.open();
				}
				else {
					lon0 = text.getText();
					lat0 = text_1.getText();
					h0 = text_2.getText();
					alpha = text_3.getText();
					rMax = text_4.getText();
					TableItem tableItem = new TableItem(IOWindow.table, SWT.NONE);
					tableItem.setText(new String[] { String.valueOf( IOWindow.table.getItemCount() ) , lon0 , lat0 , h0 , alpha, rMax});
				}
			}
		});
		button.setBounds(171, 240, 98, 30);
		button.setText("\u6DFB\u52A0\u81F3\u5217\u8868");
		
		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(329, 23, 76, 20);
		label_3.setText("\u5EA6");
		
		Label label_4 = new Label(this, SWT.NONE);
		label_4.setBounds(329, 63, 76, 20);
		label_4.setText("\u5EA6");
		
		Label label_5 = new Label(this, SWT.NONE);
		label_5.setBounds(329, 103, 76, 20);
		label_5.setText("\u7C73");
		
		Label label_6 = new Label(this, SWT.NONE);
		label_6.setBounds(63, 143, 90, 20);
		label_6.setText("\u5929\u7EBF\u5B89\u88C5\u89D2\uFF1A");
		
		text_3 = new Text(this, SWT.BORDER);
		text_3.setBounds(171, 140, 149, 26);
		
		Label label_7 = new Label(this, SWT.NONE);
		label_7.setBounds(329, 143, 76, 20);
		label_7.setText("\u5EA6");
		
		text_4 = new Text(this, SWT.BORDER);
		text_4.setBounds(171, 181, 149, 26);
		
		Label label_8 = new Label(this, SWT.NONE);
		label_8.setBounds(63, 184, 76, 20);
		label_8.setText("\u6700\u5927\u5A01\u529B\uFF1A");
		
		Label label_9 = new Label(this, SWT.NONE);
		label_9.setBounds(329, 183, 76, 20);
		label_9.setText("\u516C\u91CC");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u6DFB\u52A0\u96F7\u8FBE\u7AD9");
		setSize(477, 343);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
