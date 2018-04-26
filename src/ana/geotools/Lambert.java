package ana.geotools;

import java.awt.geom.Point2D;

public class Lambert {
			
	////=========================================函数================================================================================
    ////========================屏幕上的点对应的经纬度和高程的转换==========================

    //==================================================================================
    // 单点Lambert反变换
    //==================================================================================
    public static Point2D.Double arclambert(double Y, double X, double latitude0, double longitude0)
    {
        //Y：纬度方向，　X：经度方向
        double R_earth = 6378137.0;                 //地球半径
        double latitude1 = 25.0, latitude2 = 47.0;  //标准纬度(割纬度)
        Point2D.Double GroundPosition=new Point2D.Double() ;
        //latitude0 = latitude0 / 3600.0;
        //longitude0 = longitude0 / 3600.0;

        //圆锥常数: alpha = frac{ln(cos{latitude1})-ln(cos{latitude2})}{ln(tan{45+latitude2/2})-ln(tan{45+latitude1/2})}
        double alpha = (Math.log(Math.cos(latitude1 * Math.PI / 180.0)) - Math.log(Math.cos(latitude2 * Math.PI / 180.0)))
            / (Math.log(Math.tan((45.0 + latitude2 / 2.0) * Math.PI / 180.0)) - Math.log(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)));
        //坐标原点对应纬圈投影圆弧半径
        double rho_original = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude0 / 2.0) * Math.PI / 180.0), alpha);

        //需变换点对应纬圈投影圆弧半径
        double rho = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(rho_original, 2) - 2.0 * rho_original * Y);

        double latitude = (2 * Math.atan(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)
            / Math.pow(alpha * rho / (R_earth * Math.cos(latitude1 * Math.PI / 180.0)), 1 / alpha)) - Math.PI / 2.0) / Math.PI * 180.0;
        double longitude = longitude0 + Math.asin(X / rho) / alpha / Math.PI * 180.0;
        GroundPosition.x = longitude;// *3600.0;
        GroundPosition.y = latitude;// *3600.0;
        return GroundPosition;

    }


    //==================================================================================
    // 单点Lambert变换
    //   - 输入参数以度为单位
    //   - 输出参数以米为单位
    //==================================================================================
    public static  Point2D.Double lambert(double latitude, double longitude, double latitude0, double longitude0)
    {
        double R_earth = 6378137.0;                 //地球半径
        double latitude1 = 25.0, latitude2 = 47.0;  //标准纬度(割纬度)
        Point2D.Double DispPosition=new Point2D.Double();
        //latitude = latitude / 3600.0;
        //longitude = longitude / 3600.0;
        //latitude0 = latitude0 / 3600.0;
        //longitude0 = longitude0 / 3600.0;

        //圆锥常数: alpha = frac{ln(cos{latitude1})-ln(cos{latitude2})}{ln(tan{45+latitude2/2})-ln(tan{45+latitude1/2})}
        double alpha = (Math.log(Math.cos(latitude1 * Math.PI / 180.0)) - Math.log(Math.cos(latitude2 * Math.PI / 180.0)))
            / (Math.log(Math.tan((45.0 + latitude2 / 2.0) * Math.PI / 180.0)) - Math.log(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0)));
        //相对经度差
        double delta = alpha * (longitude - longitude0);
        //坐标原点对应纬圈投影圆弧半径
        double rho_original = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude0 / 2.0) * Math.PI / 180.0), alpha);
        //需变换点对应纬圈投影圆弧半径
        double rho = (R_earth / alpha) * Math.cos(latitude1 * Math.PI / 180.0)
            * Math.pow(Math.tan((45.0 + latitude1 / 2.0) * Math.PI / 180.0) / Math.tan((45.0 + latitude / 2.0) * Math.PI / 180.0), alpha);

        DispPosition.x = (int)((rho * Math.sin(delta * Math.PI / 180.0)));//经度方向
        DispPosition.y = (int)(((rho_original - rho * Math.cos(delta * Math.PI / 180.0))));//纬度方向
        return DispPosition;
    }
   
}



