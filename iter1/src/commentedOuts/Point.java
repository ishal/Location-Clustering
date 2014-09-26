package commentedOuts;

import java.util.Scanner;

public class Point {
	
	private double x;
	private double y;

	public Point(double x,double y){
		this.x=x;
		this.y=y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
        
        public double getMyDistanceTo(Point another){
//            double res;
//            System.out.prdoubleln("I am here.");
//            res=new Scanner(System.in).nextInt();
//            System.out.prdoubleln("Entered="+res);
            double d=12742;
            double Xa= this.x;
            double Xb= another.getX();
            double dX=Xa-Xb;
            double dY= this.y- another.getY();
            double d2r=Math.PI/180;            
            double a=Math.pow(Math.sin((dX/2)*d2r), 2)+Math.pow(Math.sin((dY/2)*d2r), 2)
                    *Math.cos(Xa*d2r)*Math.cos(Xb*d2r);
//            System.out.prdoubleln("Distance="+((d*1.0)*Math.atan(Math.pow(a, 0.5)/Math.pow(1-a, 0.5)))+"km");
            return (d*1.0)*Math.atan(Math.pow(a, 0.5)/Math.pow(1-a, 0.5));
        }
                
        public boolean equals(Point p){
            return (this.x==p.getX() && this.y==p.getY());
        }
        
        @Override
        public String toString(){
            return x+","+y+"\n";            
        }
}
