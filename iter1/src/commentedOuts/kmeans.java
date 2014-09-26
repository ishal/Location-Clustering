package commentedOuts;

import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class kmeans {
    
    public static final int maxIters=8;
    public static final int maxClusts=10;
    public static double sse=0;
    public static List<Point> points;
    public static List<List<Point>> clustNpts;
    public static List<Point> clustNcenters;
    
    public static List<List<Point>> CclustNpts;
    public static List<Point> CclustNcenters;        
    public static double sse2=0;    
         
    public static String Main(int num, Date begin, Date end, List<Integer> mans) throws Exception{
        int k=num,mono=-1;
        String retStr="";
                        
        Connection cnn=getConnection();
        Statement st=(Statement)cnn.createStatement(); 
        String persons = null;
        switch(mans.size()){
            case 1:
                persons=((" pid = "+mans.get(0)));
                break;
            case 2:
                persons=("( pid = "+mans.get(0)+" OR "+
                        " pid = "+mans.get(1)+")");
                break;
            case 3:
                persons=" pid<4 ";
                break;
        }
        
        if(persons!=null) System.out.println("Persons not null\n"+persons);
        st.executeQuery("Select lat,lon from tocluster where dat BETWEEN '"+
                (new SimpleDateFormat("yyyy-MM-dd").format(begin))+"' AND '"+
                    (new SimpleDateFormat("yyyy-MM-dd").format(end))
                         +"' AND "+ persons);								     
        ResultSet rs=st.getResultSet();
        points=new ArrayList<Point>();      
        while(rs.next()){   
            System.out.println("Here");
            double lat=rs.getDouble(1);
            double lon =rs.getDouble(2);
            points.add(new Point(lat, lon));
            System.out.println((++mono)+")  "+lat+"  "+lon);
        }
        
        
        
        if(k<=maxClusts){  
            clustNcenters=new ArrayList<Point>();
            CclustNcenters=new ArrayList<Point>();
            clustNpts=new ArrayList<List<Point>>();
            CclustNpts=new ArrayList<List<Point>>();
            
            for(int h=0;h<k;h++){
                clustNpts.add(new ArrayList<Point>());
                CclustNpts.add(new ArrayList<Point>());
            }
            
            formCluster(k);
            int ctr=0;
            retStr+=("\n"+"Cluster Set#1   SSE:"+sse2);
            retStr+=("\n"+"==========================================================");
            for(List<Point> lp:CclustNpts){
                retStr+=("\n"+"Cluster#"+(++ctr)+"  Center:"+CclustNcenters.get(ctr-1));
                retStr+=("\n"+"Members:"+"  (Count:"+lp.size()+")");
                String finalString="";
                for(Point p:lp){
                    if(!finalString.contains(p.toString()))
                        finalString+=p;
                }
                retStr+=("\n"+finalString);                
//                System.out.println();
//                System.out.println();
            }
            
            retStr+=("\n");
            
            ctr=0;            
            retStr+=("\n"+"Cluster Set#2   SSE:"+sse);
            retStr+=("\n"+"==========================================================");
            for(List<Point> lp:clustNpts){
                retStr+=("\n"+"Cluster#"+(++ctr)+"  Center:"+clustNcenters.get(ctr-1));
                retStr+=("\n"+"Members:"+"  (Count:"+lp.size()+")");
                String finalString="";
                for(Point p:lp){
                    if(!finalString.contains(p.toString()))
                        finalString+=p;
                }
                retStr+=("\n"+finalString); 
//                System.out.println();
//                System.out.println();
            }
            
        }
        
        
        
//        return k+" "+begin+" "+end+" "+mans;
        
//        points=;
        
        // --- version arff
//        points=parseMe("F:\\Imp\\Project\\iter1\\latlong.arff");
        
//        if(k<=maxClusts){  
//            clustNcenters=new ArrayList<Point>();
//            CclustNcenters=new ArrayList<Point>();
//            clustNpts=new ArrayList<List<Point>>();
//            CclustNpts=new ArrayList<List<Point>>();
//            
//            for(int h=0;h<k;h++){
//                clustNpts.add(new ArrayList<Point>());
//                CclustNpts.add(new ArrayList<Point>());
//            }
//            
//            formCluster(k);
//            int ctr=0;
//            System.out.println("Cluster Set#1   SSE:"+sse2);
//            System.out.println("==========================================================");
//            for(List<Point> lp:CclustNpts){
//                System.out.println("Cluster#"+(++ctr)+"  Center:"+CclustNcenters.get(ctr-1));
//                System.out.println("Members:"+"  (Count:"+lp.size()+")");
//                String finalString="";
//                for(Point p:lp){
//                    if(!finalString.contains(p.toString()))
//                        finalString+=p;
//                }
//                System.out.println(finalString);                
////                System.out.println();
////                System.out.println();
//            }
//            
//            System.out.println();
//            
//            ctr=0;            
//            System.out.println("Cluster Set#2   SSE:"+sse);
//            System.out.println("==========================================================");
//            for(List<Point> lp:clustNpts){
//                System.out.println("Cluster#"+(++ctr)+"  Center:"+clustNcenters.get(ctr-1));
//                System.out.println("Members:"+"  (Count:"+lp.size()+")");
//                String finalString="";
//                for(Point p:lp){
//                    if(!finalString.contains(p.toString()))
//                        finalString+=p;
//                }
//                System.out.println(finalString); 
////                System.out.println();
////                System.out.println();
//            }
//            
//        }
             
        
        return retStr;
        
    }

    
    
//      ---- version arff  
    
    
//    private static List<Point> parseMe(String path) {
//        List<Point> allPoints=new ArrayList<Point>();
//        try(BufferedReader br=new BufferedReader(new FileReader(path))){
//            while(!br.readLine().equals("@data"));
//            String x;
//            int lNo=0;
//            while((x=br.readLine())!=null){
//                Point g=new Point(splitFine(x.split(",")[0]),splitFine(x.split(",")[1]));
//                allPoints.add(g);                              
//            }
//        } catch (Exception ex) {
//            Logger.getLogger(kmeans.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return allPoints;
//    }
//    
//    public static int splitFine(String x){
//        int def=0;
//        String y=x.replace("'", "");       
//        String[] k=y.split("-");
//            
//        switch(k.length){
//            case 2:
//                def=Integer.parseInt(k[0]);
//                break;
//            case 3:
//                def=(k[1].equals(""))?Integer.parseInt(k[0]):-1*Integer.parseInt(k[1]);
//                break;
//            case 4:
//                def=-1*Integer.parseInt(k[1]);
//                break;
//        }                        
//        return def;
//    }

    private static void formCluster(int k) {
                
        for(int i=0;i<k;i++){
           CclustNcenters.add(points.get(new Random().nextInt(points.size())));
           clustNcenters.add(points.get(new Random(Double.doubleToLongBits(Math.random())).nextInt(points.size())));
        }
             
//        int pC=0; int thereX=0;
//      System.out.println("Point No.:"+(++pC));
//        ++thereX;
//        System.out.println("thereX="+thereX);
        
        int converger = 0, iter=0;
        do{
//            System.out.println("converger="+converger+" iter="+iter);
            for(Point x:points){
                if(!there(x)){
                    int selectedClust=0;
                    double distance=CclustNcenters.get(0).getMyDistanceTo(x);
                    for(int p=1;p<CclustNcenters.size();p++){
                        if(CclustNcenters.get(p).getMyDistanceTo(x)<=distance){
                            distance=CclustNcenters.get(p).getMyDistanceTo(x);
                            selectedClust=p;
                        }
                    }                
                    CclustNpts.get(selectedClust).add(x);
                }else{

                    CclustNpts.get(where(x)).add(x);
                }            
            }
            
            converger=0;
            for(int ek=0;ek<k;ek++){
                Point b;
                if((b=checkNearest(ek,getMean(ek))).equals(CclustNcenters.get(ek))){
                    converger+=1;
                }else{                   
                    CclustNcenters.set(ek,b);
                    if(iter+1<maxIters){
                        for(int i=0;i<CclustNpts.size();i++){
                            CclustNpts.get(i).clear();
                        }
                    }
                }
            }
           iter+=1; 
//           if(converger==k) System.out.println("converger="+converger+" iter="+iter);
        }while(converger<k && iter<maxIters);                
        
        int temp=-1;
        for(List<Point> lp:CclustNpts){
            ++temp;
            for(Point p:lp){
                if(!CclustNcenters.get(temp).equals(p))
                    sse2+=Math.pow(CclustNcenters.get(temp).getMyDistanceTo(p),2);
            }
        }
                            
        converger = 0; iter=0;
        do{
//            System.out.println("converger="+converger+" iter="+iter);
            for(Point x:points){
                if(!there0(x)){
                    int selectedClust=0;
                    double distance=clustNcenters.get(0).getMyDistanceTo(x);
                    for(int p=1;p<clustNcenters.size();p++){
                        if(clustNcenters.get(p).getMyDistanceTo(x)<=distance){
                            distance=clustNcenters.get(p).getMyDistanceTo(x);
                            selectedClust=p;
                        }
                    }                
                    clustNpts.get(selectedClust).add(x);
                }else{
                    clustNpts.get(where0(x)).add(x);
                }            
            }
            
            converger=0;
            for(int ek=0;ek<k;ek++){
                Point b;
                if((b=checkNearest0(ek,getMean0(ek))).equals(clustNcenters.get(ek))){
                    converger+=1;
                }else{                   
                    clustNcenters.set(ek,b);
                    if(iter+1<maxIters){
                        for(int i=0;i<clustNpts.size();i++){
                            clustNpts.get(i).clear();
                        }
                    }
                }
            }
           iter+=1; 
//           if(converger==k) System.out.println("converger="+converger+" iter="+iter);
        }while(converger<k && iter<maxIters);                
        
        temp=-1;
        for(List<Point> lp:clustNpts){
            ++temp;
            for(Point p:lp){
                if(!clustNcenters.get(temp).equals(p))
                    sse+=Math.pow(clustNcenters.get(temp).getMyDistanceTo(p),2);
            }
        }
        
    }    

    
    
    private static boolean there(Point x) {
        for(Point t:CclustNcenters)
            if(t.getX()==x.getX() && t.getY()==x.getY())
                return true;
        
        return false;
    }

    private static int where(Point x) {
        int here=-1;
        int ctr=0;
        for(Point t:CclustNcenters){
            ctr++;
            if(t.getX()==x.getX() && t.getY()==x.getY()){
                here=ctr-1;
                break;
            }
        }
        return here;
    }

    private static Point getMean(int ek) {        
        double mX=0.0,mY=0.0;
        for(Point p:CclustNpts.get(ek)){
            mX+=(p.getX()/CclustNpts.get(ek).size());
            mY+=(p.getY()/CclustNpts.get(ek).size());
        }
        return new Point((int)mX, (int)mY);
    }

    private static Point checkNearest(int ek, Point mean) {
        Point prev=CclustNcenters.get(ek);
        Point selP = prev;
        if(!prev.equals(mean)){           
            double minDis=mean.getMyDistanceTo(prev);            
            for(Point p:CclustNpts.get(ek)){
               if(mean.getMyDistanceTo(p)<minDis){
                   selP=p;
                   minDis=mean.getMyDistanceTo(p);
               }
            }                
        }  
        return selP;
    }
    
    
    
    private static boolean there0(Point x) {
        for(Point t:clustNcenters)
            if(t.getX()==x.getX() && t.getY()==x.getY())
                return true;
        
        return false;
    }

    private static int where0(Point x) {
        int here=-1;
        int ctr=0;
        for(Point t:clustNcenters){
            ctr++;
            if(t.getX()==x.getX() && t.getY()==x.getY()){
                here=ctr-1;
                break;
            }
        }
        return here;
    }

    private static Point getMean0(int ek) {        
        double mX=0.0,mY=0.0;
        for(Point p:clustNpts.get(ek)){
            mX+=(p.getX()/clustNpts.get(ek).size());
            mY+=(p.getY()/clustNpts.get(ek).size());
        }
        return new Point((int)mX, (int)mY);
    }

    private static Point checkNearest0(int ek, Point mean) {
        Point prev=clustNcenters.get(ek);
        Point selP = prev;
        if(!prev.equals(mean)){           
            double minDis=mean.getMyDistanceTo(prev);            
            for(Point p:clustNpts.get(ek)){
               if(mean.getMyDistanceTo(p)<minDis){
                   selP=p;
                   minDis=mean.getMyDistanceTo(p);
               }
            }                
        }  
        return selP;
    }
    
    
    private static Connection getConnection() {
		Connection connection = null;
		try {
    		Class.forName("com.mysql.jdbc.Driver");
    	} catch (ClassNotFoundException e) {
    		System.out.println("Where is your MySQL JDBC Driver?");    		
    		return connection;
    	}
        try {
    		connection = DriverManager.getConnection("jdbc:mysql://localhost/studentlocation","root", "");     
    	} catch (SQLException e) {
    		System.out.println("Connection Failed! Check output console");    		
    		return connection;
    	}     
    	if (connection == null) {
            System.out.println("Failed to make connection!");
    	}
            return connection;
	}
    
    
}
