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

public class InputAlt extends Shell {
	private Text text;
	public static String alt0; 


	/**
	 * Launch the application.
	 * @param args
	 */
	public static void launch() {
		try {
			Display display = Display.getDefault();
			InputAlt shell = new InputAlt(display);
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
	public InputAlt(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(135, 73, 168, 26);
		
		Label label = new Label(this, SWT.NONE);
		label.setBounds(53, 76, 76, 20);
		label.setText("\u9AD8\u5EA6\uFF1A");
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(313, 76, 76, 20);
		label_1.setText("\u7C73");
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(getShell(),SWT.ICON_WARNING|SWT.OK);
				if(text.getText().equals("")) {
					box.setText("提醒");
					box.setMessage("观察层高度为空，请重新输入！");
					box.open();
				}
				else {
					alt0 = text.getText();
					TableItem tableItem = new TableItem(IOWindow.table_1, SWT.NONE);
					tableItem.setText(new String[] { alt0 });
				}
			}
		});
		button.setBounds(168, 166, 98, 30);
		button.setText("\u6DFB\u52A0\u81F3\u5217\u8868");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("\u6DFB\u52A0\u89C2\u5BDF\u5C42");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
