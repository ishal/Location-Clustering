package simpleDm;

import com.mysql.jdbc.Statement;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Scanner;
import weka.clusterers.ClusterEvaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.PerformanceStats;

/**
 * An example class that shows the use of Weka 'clusterers' from Java.
 *
 * @author  FracPete
 */

public class clusteringDemo {
    
    public static String oHp;
    public static String oKp;
    
  /**
   * Run 'clusterers'
   *
   * @param filename      the name of the ARFF file to run on
   */
  public clusteringDemo(String filename) throws Exception {
      
    DistanceFunction df=new DistanceFunction() {

        @Override
        public void setInstances(Instances i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Instances getInstances() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setAttributeIndices(String string) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getAttributeIndices() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setInvertSelection(boolean bln) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean getInvertSelection() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double distance(Instance instnc, Instance instnc1) {
            int res;
            System.out.println("I am here.");
            res=new Scanner(System.in).nextInt();
            System.out.println("Entered="+res);
            double d=12742;
            double Xa=(double)instnc.value(0);
            double Xb=(double)instnc1.value(0);
            double dX=Xa-Xb;
            double dY=(double)instnc.value(1)-(double)instnc1.value(1);
            double d2r=Math.PI/180;
            double a=Math.pow(Math.sin((dX/2)*d2r), 2)+Math.pow(Math.sin((dY/2)*d2r), 2)
                    *Math.cos(Xa*d2r)*Math.cos(Xb*d2r);
            
            return d*Math.atan(Math.pow(a, 0.5)/Math.pow(1-a, 0.5));
        }

        @Override
        public double distance(Instance instnc, Instance instnc1, PerformanceStats ps) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double distance(Instance instnc, Instance instnc1, double d) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double distance(Instance instnc, Instance instnc1, double d, PerformanceStats ps) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void postProcessDistances(double[] doubles) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void update(Instance instnc) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void clean() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Enumeration listOptions() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setOptions(String[] strings) throws Exception {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String[] getOptions() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
      
    ClusterEvaluation eval;
    Instances data;
    String[]  options;
    SimpleKMeans   cl;    
    HierarchicalClusterer hc=new HierarchicalClusterer();
    hc.setDistanceFunction(df);

    data = new Instances(new BufferedReader(new FileReader(filename)));
       
    options    = new String[4];
    options[0] = "-t";
    options[1] = filename;
    options[2] = "-N";
    options[3] = "4";
    
    oHp=ClusterEvaluation.evaluateClusterer(hc, options);
    System.out.println(oHp);
    
    // manual call
    System.out.println("\n--> manual");
    cl   = new SimpleKMeans();
    options[0] = "-t";
    options[1] = filename;
    options[2] = "-N";
    options[3] = "4";
    oKp=ClusterEvaluation.evaluateClusterer(new SimpleKMeans(), options);
    System.out.println(oKp);   
  }

  public static void getClusters(String arffFile) throws Exception{
      new clusteringDemo(arffFile);
  }
    
  /**
   * usage:
   *   ClusteringDemo 'arff'-file
   */
  public static void main(String[] args) throws Exception {
      /*
    if (args.length != 1) {
//      System.out.println("usage: " + clusteringDemo.class.getName() + " <arff-file>");
      args=new String[1];
      args[0]="C:\\Program Files\\Weka-3-6\\data\\breast-cancer.arff";
//      System.exit(1);      
    }*/
      
      
//    --- loads to 'arff' DON'T UNCOMMENT        
//    new clusteringDemo(args[0]);
//    Connection cnn=getConnection();
//    Statement st=(Statement)cnn.createStatement(); 
//    st.executeQuery("Select latitude,longitude from placeTime where person_id=3");								     
//    ResultSet rs=st.getResultSet();    
//    while(rs.next()){        
//        int lat=(int)rs.getDouble(1);
//        int lon = (int)rs.getDouble(2);
//        try(FileWriter fw=new FileWriter("latlong3.arff",true);BufferedWriter bw=new BufferedWriter(fw)){
//            bw.write("'"+lat+"-"+(lat+1)+"',"+"'"+lon+"-"+(lon+1)+"'\n");
//        }catch(IOException e){
//            System.out.println("IO Error!");
//        }
//    }
      
      
//    --- loads to 'tocluster' DON'T UNCOMMENT  
//    long mono=0;  
//    Connection cnn=getConnection();
//    Statement st=(Statement)cnn.createStatement(); 
//    st.executeQuery("Select person_id,dateTime,latitude,longitude from placeTime");								     
//    ResultSet rs=st.getResultSet();    
//    while(rs.next()){      
//        int id=rs.getInt(1);
//        Date gc=rs.getDate(2);
//        double lat=new BigDecimal(rs.getDouble(3)).setScale(1, RoundingMode.HALF_UP).doubleValue();
//        double lon =new BigDecimal(rs.getDouble(4)).setScale(1, RoundingMode.HALF_UP).doubleValue();
//        System.out.println((++mono)+")  "+id+"  "+gc+"  "+lat+"  "+lon);  
//        Statement st2=(Statement)cnn.createStatement(); 
//        st2.executeUpdate("INSERT INTO tocluster(pid,dat,lat,lon) " + "VALUES ("+id+",'"+new SimpleDateFormat("yyyy-MM-dd").format(gc)+"',"+lat+","+lon+")");								             
//    }
      
      
      
      
      
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