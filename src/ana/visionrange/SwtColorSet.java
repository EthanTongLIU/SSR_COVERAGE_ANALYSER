package ana.visionrange;

import org.eclipse.swt.graphics.RGB;

/**
 * SWT配色类
 */

public class SwtColorSet {	
	/**
	 * 该方法为指定的高度配色
	 * @param height,heighestHeight
	 * @return Color
	 */
	public static RGB setRGBColor(double height, double heighestHeight) {
		
		// 定义64组RGB配色
		int[][] rgbColorArray = new int[][] {
			  { 0,102,51},
			  { 2,106,51},
			  { 5,111,51},
			  { 8,116,52},
			  {12,121,53},
			  {16,126,53},
			  {19,131,54},
			  {24,136,55},
			  {28,140,56},
			  {33,145,57},
			  {38,150,58},
			  {43,155,60},
			  {48,160,62},
			  {54,165,64},
			  { 60,170, 66},
			  { 66,174, 69},
			  { 73,179, 73},
			  { 83,184, 79},
			  { 93,189, 86},
			  {104,194, 93},
			  {114,199,101},
			  {124,203,108},
			  {134,208,116},
			  {145,213,124},
			  {155,218,133},
			  {165,223,141},
			  {175,228,150},
			  {186,233,159},
			  {195,238,169},
			  {205,242,178},
			  {215,247,188},
			  {224,252,198},
			  {226,252,198},
			  {221,247,188},
			  {216,242,178},
			  {211,238,169},
			  {207,233,159},
			  {204,228,150},
			  {200,223,141},
			  {197,218,133},
			  {195,213,124},
			  {192,208,116},
			  {190,204,108},
			  {188,199,101},
			  {187,194, 93},
			  {185,189, 86},
			  {184,184, 79},
			  {179,176,73},
			  {174,167,66},
			  {170,158,60},
			  {165,150,54},
			  {160,141,48},
			  {155,132,43},
			  {150,123,38},
			  {145,114,33},
			  {140,106,28},
			  {136, 97,24},
			  {131, 89,19},
			  {126, 80,16},
			  {121, 72,12},
			  {116, 64, 8},
			  {111, 57, 5},
			  {106, 49, 2},
			  {102, 42, 0}
		};
		
		// 目前只为0高度以上的高度值配色，如果高度值全部为0以下，则不为其配色
		if(height>0) {
			double dh = heighestHeight/64;
			int RGBIndex = (int) Math.floor(height/dh);
			RGB mycolor = new RGB(rgbColorArray[RGBIndex][0],
					rgbColorArray[RGBIndex][1],
					rgbColorArray[RGBIndex][2]);
			return mycolor;
		}
		else {
			// 凡是高度值小于0的点全部上色为海蓝色
			RGB seaColor = new RGB(0,69,107);
			return seaColor;
		}
	}
	
	/**
	 * 该方法为指定的遮蔽角配色
	 * @param a
	 * @param maxA
	 * @return Color
	 */
	public static RGB setAngleRGB(double a, double maxA) {
		
		RGB color1 = null;
		double d = 0.01;
		double d2 = 0.02;
		// 定义8组RGB配色
		int[][] rgbColorArray = new int[][] {
			  {255,0,0},{255,165,0},{255,255,0},
			  {0,255,0},{0,255,255},{0,0,255},
			  {238,130,238},{0,0,0}
		};
		
		int[][] rgbColorArray1 = new int[][] {
			  {54,54,54},{79,79,79},{105,105,105},
			  {130,130,130},{156,156,156},{	181,181,181},
			  {207,207,207},{232,232,232}
		};
		
		// 角度大于0
		if(a>=0 && a<d) {
			RGB color = new RGB(
					rgbColorArray[7][0],
					rgbColorArray[7][1],
					rgbColorArray[7][2]
							);
			color1 = color;
		}
		if(a>=d && a<2*d) {
			RGB color = new RGB(
					rgbColorArray[6][0],
					rgbColorArray[6][1],
					rgbColorArray[6][2]
							);
			color1 = color;
		}
		if(a>=2*d && a<3*d) {
			RGB color = new RGB(
					rgbColorArray[5][0],
					rgbColorArray[5][1],
					rgbColorArray[5][2]
							);
			color1 = color;
		}
		if(a>=3*d && a<4*d) {
			RGB color = new RGB(
					rgbColorArray[4][0],
					rgbColorArray[4][1],
					rgbColorArray[4][2]
							);
			color1 = color;
		}
		if(a>=4*d && a<5*d) {
			RGB color = new RGB(
					rgbColorArray[3][0],
					rgbColorArray[3][1],
					rgbColorArray[3][2]
							);
			color1 = color;
		}
		if(a>=5*d && a<6*d) {
			RGB color = new RGB(
					rgbColorArray[2][0],
					rgbColorArray[2][1],
					rgbColorArray[2][2]
							);
			color1 = color;
		}
		if(a>=6*d && a<7*d) {
			RGB color = new RGB(
					rgbColorArray[1][0],
					rgbColorArray[1][1],
					rgbColorArray[1][2]
							);
			color1 = color;
		}
		if(a>=7*d) {
			RGB color = new RGB(
					rgbColorArray[0][0],
					rgbColorArray[0][1],
					rgbColorArray[0][2]
							);
			color1 = color;
		}
		if(a<0 && a>=-d2) {
			RGB color = new RGB(
					rgbColorArray1[0][0],
					rgbColorArray1[0][1],
					rgbColorArray1[0][2]
							);
			color1 = color;
		}
		if(a<-d2 && a>=-2*d2) {
			RGB color = new RGB(
					rgbColorArray1[1][0],
					rgbColorArray1[1][1],
					rgbColorArray1[1][2]
							);
			color1 = color;
		}
		if(a<-2*d2 && a>=-3*d2) {
			RGB color = new RGB(
					rgbColorArray1[2][0],
					rgbColorArray1[2][1],
					rgbColorArray1[2][2]
							);
			color1 = color;
		}
		if(a<-3*d2 && a>=-4*d2) {
			RGB color = new RGB(
					rgbColorArray1[3][0],
					rgbColorArray1[3][1],
					rgbColorArray1[3][2]
							);
			color1 = color;
		}
		if(a<-4*d2 && a>=-5*d2) {
			RGB color = new RGB(
					rgbColorArray1[4][0],
					rgbColorArray1[4][1],
					rgbColorArray1[4][2]
							);
			color1 = color;
		}
		if(a<-5*d2 && a>=-6*d2) {
			RGB color = new RGB(
					rgbColorArray1[5][0],
					rgbColorArray1[5][1],
					rgbColorArray1[5][2]
							);
			color1 = color;
		}
		if(a<-6*d2 && a>=-7*d2) {
			RGB color = new RGB(
					rgbColorArray1[6][0],
					rgbColorArray1[6][1],
					rgbColorArray1[6][2]
							);
			color1 = color;
		}
		if(a<-7*d2) {
			RGB color = new RGB(
					rgbColorArray1[7][0],
					rgbColorArray1[7][1],
					rgbColorArray1[7][2]
							);
			color1 = color;
		}
		return color1;
	}

}
