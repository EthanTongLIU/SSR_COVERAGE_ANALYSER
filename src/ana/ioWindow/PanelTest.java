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
    static int frame_width = 800; // panel的宽度
    static int frame_height = 800; // panel的高度
        
    static PicPanel pic = null; // 图片
    
    private int pic_x; // 图片的位置
    private int pic_y; // 图片的位置
    
    //前一个位置
    int begin_x = 0; 
    int begin_y = 0;
    
    boolean inThePic = false;
    
    private int width; // 图片的宽度 
    private int height; // 图片的高度

    public PanelTest()
    {
        pic = new PicPanel("D:\\dem.png");
        
        setLayout(null);
        add(pic);
        
        width = pic.getWidth(); // 图像的宽度
        height = pic.getHeight(); // 图像的高度
        
        
        
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);
        
        System.out.println(pic_x + " " + pic_y);
        
        pic.setLocation(pic_x, pic_y);  //定位
        
        //鼠标动作 监听器 注册
        addMouseListener(
            new MouseAdapter()
            {   
                public void mousePressed(MouseEvent e)
                {
                    //检测 落点 是否在图片上,只有落点在图片上时 才起作用
                    if(inPicBounds(e.getX(), e.getY()))
                    {
                        begin_x = e.getX();
                        begin_y = e.getY();
                        inThePic = true;
                    }
                    //记录当前坐标
                }
                //释放
                public void mouseReleased(MouseEvent e)
                {
                    inThePic = false;
                }
            }
        );
        
        //鼠标移动 监听器 注册
        addMouseMotionListener(
            new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    if(inThePic && checkPoint(e.getX(),e.getY()))
                    {
                        //边界 检查
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
    //-------------帮助方法-----------------//
    //检测 点(px,py) 是否在图片上
    private boolean inPicBounds(int px,int py)
    {
        if(px >= pic_x && px <= pic_x + pic.getWidth() &&
                            py >= pic_y && py <= pic_y + pic.getHeight())
            return true;
        else
            return false;
    }
    
    //越界 检查
    private boolean checkPoint(int px, int py)
    {
        if(px <0 || py <0)
            return false;
        if(px >getWidth() || py > getHeight())
            return false;
        return true;
    }
        
    //放大  
    public void enlargeImg(){  
        if(width >= pic.getWidth() * 2){  
            return;  
        }  
        if(height >= pic.getHeight() * 2){  
            return;  
        }  
          
        width += width / 9; //这里为什么是除以9呢  这个是数学问题  留给大家自己想吧  要是不明白,可以留言  
        height += height / 9;  
        
        pic_x = (int)((frame_width - pic.getWidth())/2);
        pic_y = (int)((frame_height - pic.getHeight())/2);
          
        repaint();  
    }     
      
    //缩小  
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
//        JFrame jframe = new JFrame("图片拖动");
//        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jframe.getContentPane().add(jpanel);
//        jframe.setSize(frame_width, frame_height);
//        jframe.setVisible(true);
//    }
    
}


//图片面板,只是用来放置图片
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

//自定义缩放面板
//class PicPanel extends JPanel {  
//	
//  private ImageIcon ii;  
//  private Dimension frameSize;  
//  private int x, y, width, height;//图片的左上角坐标、缩放的后的size  
//
//  public PicPanel(String imgPath) {  
//      setBackground(Color.BLACK);  
//        
//      this.frameSize = new Dimension(800, 800);  
//
//      ii = new ImageIcon(imgPath);  
//
//      width = ii.getIconWidth(); // 图像的宽度  
//      height = ii.getIconHeight();  // 图像的高度
//        
//      setImgPos();  
//      
//      //添加鼠标滚轮消息响应  
//      addMouseWheelListener(new MouseWheelListener(){  
//          public void mouseWheelMoved(MouseWheelEvent e){  
//                
//              //滚轮向前滑动  放大  
//              if(e.getWheelRotation() < 0){  
//                  enlargeImg();  
//              }  
//                
//              //滚轮向后滑动  缩小  
//              else{  
//                  reduceImg();  
//              }  
//          }  
//      });  
//      
//  }  
//    
//  //设定x、y坐标  
//  public void setImgPos() {  
//      x = (frameSize.width - width) / 2;  
//      y = (frameSize.height - height) / 2;  
//      System.out.println(x + " " + y);
//  }  
//    
//  //放大  
//  public void enlargeImg(){  
//      if(width >= ii.getIconWidth() * 3){  
//          return;  
//      }  
//      if(height >= ii.getIconHeight() * 3){  
//          return;  
//      }  
//        
//      width += width / 9; //这里为什么是除以9呢  这个是数学问题  留给大家自己想吧  要是不明白,可以留言  
//      height += height / 9;  
//      setImgPos();  
//        
//      repaint();  
//  }     
//    
//  //缩小  
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
//  //重绘  
//  public void paintComponent(Graphics g) {  
//      super.paintComponent(g);  
//        
//      g.drawImage(ii.getImage(), x, y, width, height, null);  
//  }  
//}  