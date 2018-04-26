package ana.ioWindow;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;  
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;  
import java.awt.event.MouseWheelListener;  

  
//�Զ����������
public class ZoomPanel extends JPanel {  
	
    private ImageIcon ii;  
    private int x, y, width, height;//ͼƬ�����Ͻ����ꡢ���ŵĺ��size  
    private int cX, cY; // ��ȡ���λ��
  
    public ZoomPanel(String imgPath) {  
    	setLayout(null);
    	setBounds(0, 0, 637, 637);
        setBackground(Color.BLACK); 
        ii = new ImageIcon(imgPath);  
        width = ii.getIconWidth();  
        height = ii.getIconHeight(); 
        System.out.println(width+" "+height);
        
    	addMouseListener( new MouseAdapter() {   
    		public void mousePressed(MouseEvent e) {
                cX = e.getX();
                cY = e.getY();
                System.out.println(cX+" "+cY);
                IOWindow.list.add("xing");
                IOWindow.lblNewLabel.setText("bbb");
                IOWindow.lblNewLabel_1.setText(String.valueOf(cY));
    		}                    
    	});
    } 
    
    //�ػ�  
    public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        g.drawImage(ii.getImage(), x, y, width, height, null);  
    }  

}   
 
      
 
          
 