package ana.ioWindow;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelTest extends JPanel
{
    static int frame_width = 800; // panel�Ŀ��
    static int frame_height = 800; // panel�ĸ߶�
        
    static PicPanel pic = null; // ͼƬ
    
    private int pic_x; // ͼƬ��λ��
    private int pic_y; // ͼƬ��λ��
    
    //ǰһ��λ��
    int begin_x = 0; 
    int begin_y = 0;
    
    boolean inThePic = false;
    
    private int width; // ͼƬ�Ŀ�� 
    private int height; // ͼƬ�ĸ߶�

    public PanelTest()
    {
        pic = new PicPanel("D:\\dem.png");
        
        setLayout(null);
        add(pic);
        
        width = pic.getWidth(); // ͼ��Ŀ��
        height = pic.getHeight(); // ͼ��ĸ߶�
        
        
        
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);
        
        System.out.println(pic_x + " " + pic_y);
        
        pic.setLocation(pic_x, pic_y);  //��λ
        
        //��궯�� ������ ע��
        addMouseListener(
            new MouseAdapter()
            {   
                public void mousePressed(MouseEvent e)
                {
                    //��� ��� �Ƿ���ͼƬ��,ֻ�������ͼƬ��ʱ ��������
                    if(inPicBounds(e.getX(), e.getY()))
                    {
                        begin_x = e.getX();
                        begin_y = e.getY();
                        inThePic = true;
                    }
                    //��¼��ǰ����
                }
                //�ͷ�
                public void mouseReleased(MouseEvent e)
                {
                    inThePic = false;
                }
            }
        );
        
        //����ƶ� ������ ע��
        addMouseMotionListener(
            new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    if(inThePic && checkPoint(e.getX(),e.getY()))
                    {
                        //�߽� ���
                        pic_x =pic_x - (begin_x - e.getX());
                        pic_y =pic_y - (begin_y - e.getY());
                        //System.out.println("pic_x=" + pic_x);
                        begin_x = e.getX();
                        begin_y = e.getY();
                        pic.setLocation(pic_x, pic_y);
                    }
                }
            }
        );
    }
    //-------------��������-----------------//
    //��� ��(px,py) �Ƿ���ͼƬ��
    private boolean inPicBounds(int px,int py)
    {
        if(px >= pic_x && px <= pic_x + pic.getWidth() &&
                            py >= pic_y && py <= pic_y + pic.getHeight())
            return true;
        else
            return false;
    }
    
    //Խ�� ���
    private boolean checkPoint(int px, int py)
    {
        if(px <0 || py <0)
            return false;
        if(px >getWidth() || py > getHeight())
            return false;
        return true;
    }
        
    //�Ŵ�  
    public void enlargeImg(){  
        if(width >= pic.getWidth() * 2){  
            return;  
        }  
        if(height >= pic.getHeight() * 2){  
            return;  
        }  
          
        width += width / 9; //����Ϊʲô�ǳ���9��  �������ѧ����  ��������Լ����  Ҫ�ǲ�����,��������  
        height += height / 9;  
        
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);
          
        repaint();  
    }     
      
    //��С  
    public void reduceImg(){  
        if(width <= pic.getWidth() / 5){  
            return;  
        }  
        if(height <= pic.getHeight() / 5){  
            return;  
        }  
          
        width -= width / 10;  
        height -= height / 10;  
        
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);  
          
        repaint();  
    }  
    
//    public static void main(String[] args)
//    {
//        JPanel jpanel = new PanelTest();
//        JFrame jframe = new JFrame("ͼƬ�϶�");
//        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jframe.getContentPane().add(jpanel);
//        jframe.setSize(frame_width, frame_height);
//        jframe.setVisible(true);
//    }
    
}


//ͼƬ���,ֻ����������ͼƬ
class PicPanel extends JPanel
{
    int p_width = 0;
    int p_height = 0;
    Image im = null;
    
    int i = 0; //temp var
    public PicPanel(String picName)
    {
        ImageIcon imageIcon = new ImageIcon(picName);
        im = imageIcon.getImage();
        
        p_width = imageIcon.getIconWidth();
        p_height = imageIcon.getIconHeight();
        setBounds(0,0,p_width, p_height);
    }
    
    public void paint(Graphics g)
    {
        g.drawImage(im,0,0,p_width,p_height,null);
    }
    
}

//�Զ����������
//class PicPanel extends JPanel {  
//	
//  private ImageIcon ii;  
//  private Dimension frameSize;  
//  private int x, y, width, height;//ͼƬ�����Ͻ����ꡢ���ŵĺ��size  
//
//  public PicPanel(String imgPath) {  
//      setBackground(Color.BLACK);  
//        
//      this.frameSize = new Dimension(800, 800);  
//
//      ii = new ImageIcon(imgPath);  
//
//      width = ii.getIconWidth(); // ͼ��Ŀ��  
//      height = ii.getIconHeight();  // ͼ��ĸ߶�
//        
//      setImgPos();  
//      
//      //�����������Ϣ��Ӧ  
//      addMouseWheelListener(new MouseWheelListener(){  
//          public void mouseWheelMoved(MouseWheelEvent e){  
//                
//              //������ǰ����  �Ŵ�  
//              if(e.getWheelRotation() < 0){  
//                  enlargeImg();  
//              }  
//                
//              //������󻬶�  ��С  
//              else{  
//                  reduceImg();  
//              }  
//          }  
//      });  
//      
//  }  
//    
//  //�趨x��y����  
//  public void setImgPos() {  
//      x = (frameSize.width - width) / 2;  
//      y = (frameSize.height - height) / 2;  
//      System.out.println(x + " " + y);
//  }  
//    
//  //�Ŵ�  
//  public void enlargeImg(){  
//      if(width >= ii.getIconWidth() * 3){  
//          return;  
//      }  
//      if(height >= ii.getIconHeight() * 3){  
//          return;  
//      }  
//        
//      width += width / 9; //����Ϊʲô�ǳ���9��  �������ѧ����  ��������Լ����  Ҫ�ǲ�����,��������  
//      height += height / 9;  
//      setImgPos();  
//        
//      repaint();  
//  }     
//    
//  //��С  
//  public void reduceImg(){  
//      if(width <= ii.getIconWidth() / 5){  
//          return;  
//      }  
//      if(height <= ii.getIconHeight() / 5){  
//          return;  
//      }  
//        
//      width -= width / 10;  
//      height -= height / 10;  
//      setImgPos();  
//        
//      repaint();  
//  }  
//    
//  //�ػ�  
//  public void paintComponent(Graphics g) {  
//      super.paintComponent(g);  
//        
//      g.drawImage(ii.getImage(), x, y, width, height, null);  
//  }  
//}  