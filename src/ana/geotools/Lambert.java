package ana.geotools;

import java.awt.geom.Point2D;

public class Lambert {
			
	////=========================================����================================================================================
    ////========================��Ļ�ϵĵ��Ӧ�ľ�γ�Ⱥ͸̵߳�ת��==========================

    //==================================================================================
    // ����Lambert���任
    //==================================================================================
    public static Point2D.Double arclambert(double Y, double X, double latitude0, double longitude0)
    {
        //Y��γ�ȷ��򣬡�X�����ȷ���
        double R_earth = 6378137.0;                 //����뾶
        double latitude1 = 25.0, latitude2 = 47.0;  //��׼γ��(��γ��)
        Point2D.Double GroundPosition=new Point2D.Double() ;
        //latitude0 = latitude0 / 3600.0;
        //longitude0 = longitude0 / 3600.0;

        //Բ׶����: alpha = frac{ln(cos{latitude1})-ln(cos{latitude2})}{ln(tan{45+latitude2/2})-ln(tan{45+latitude1/2})}
        double alpha = (Math.log(Math.cos(latitude1 * Math.PI / 180.0)) - Math.log(Math.cos(latitude2 * Math.PI / 180.0)))
            / (Math.log(Math.tan((45.0 + latitude2 / 2.0) * Math.PI / 180.0)) - Math.log(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)));
        //����ԭ���ӦγȦͶӰԲ���뾶
        double rho_original = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude0 / 2.0) * Math.PI / 180.0), alpha);

        //��任���ӦγȦͶӰԲ���뾶
        double rho = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(rho_original, 2) - 2.0 * rho_original * Y);

        double latitude = (2 * Math.atan(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)
            / Math.pow(alpha * rho / (R_earth * Math.cos(latitude1 * Math.PI / 180.0)), 1 / alpha)) - Math.PI / 2.0) / Math.PI * 180.0;
        double longitude = longitude0 + Math.asin(X / rho) / alpha / Math.PI * 180.0;
        GroundPosition.x = longitude;// *3600.0;
        GroundPosition.y = latitude;// *3600.0;
        return GroundPosition;

    }


    //==================================================================================
    // ����Lambert�任
    //   - ��������Զ�Ϊ��λ
    //   - �����������Ϊ��λ
    //==================================================================================
    public static  Point2D.Double lambert(double latitude, double longitude, double latitude0, double longitude0)
    {
        double R_earth = 6378137.0;                 //����뾶
        double latitude1 = 25.0, latitude2 = 47.0;  //��׼γ��(��γ��)
        Point2D.Double DispPosition=new Point2D.Double();
        //latitude = latitude / 3600.0;
        //longitude = longitude / 3600.0;
        //latitude0 = latitude0 / 3600.0;
        //longitude0 = longitude0 / 3600.0;

        //Բ׶����: alpha = frac{ln(cos{latitude1})-ln(cos{latitude2})}{ln(tan{45+latitude2/2})-ln(tan{45+latitude1/2})}
        double alpha = (Math.log(Math.cos(latitude1 * Math.PI / 180.0)) - Math.log(Math.cos(latitude2 * Math.PI / 180.0)))
            / (Math.log(Math.tan((45.0 + latitude2 / 2.0) * Math.PI / 180.0)) - Math.log(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)));
        //��Ծ��Ȳ�
        double delta = alpha * (longitude - longitude0);
        //����ԭ���ӦγȦͶӰԲ���뾶
        double rho_original = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude0 / 2.0) * Math.PI / 180.0), alpha);
        //��任���ӦγȦͶӰԲ���뾶
        double rho = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude / 2.0) * Math.PI / 180.0), alpha);

        DispPosition.x = (int)((rho * Math.sin(delta * Math.PI / 180.0)));//���ȷ���
        DispPosition.y = (int)(((rho_original - rho * Math.cos(delta * Math.PI / 180.0))));//γ�ȷ���
        return DispPosition;
    }
   
}



