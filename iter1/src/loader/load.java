// This code loads file specified by "timeAdd" & "locationAdd".
package loader;

import java.io.*;
import java.math.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jsoup.Jsoup;

import com.mysql.jdbc.Statement;

public class load {	
	static int person_id=3;
	static GregorianCalendar dateTime;
	static double cell_id;
	static double latitude;
	static double longitude;
	static String address;
	static String dbRow=person_id+",";		
        static String timeAdd="";
	static String locationAdd="";
	//static String timeAdd="C:/Users/Bishal Timilsina/Desktop/studentFrequentLocation/Selected Dataset/Data in XLS/student13/locs.csv";
	//static String locationAdd="C:/Users/Bishal Timilsina/Desktop/studentFrequentLocation/Selected Dataset/Cell IDs with Lat&Long/allLocationsLACclusteredShafqat.csv";
	
	public static void main(String args[]){								
		try(BufferedReader br1 = new BufferedReader(new FileReader(timeAdd))){
			int token=0;
			String stringT;
			while((stringT=br1.readLine())!=null){					
				String[] strArray=stringT.split(",");					
				if(Double.parseDouble(strArray[3])!=0){
					cell_id=Double.parseDouble(strArray[3]);	
					dateTime=new GregorianCalendar(Integer.parseInt(strArray[2].trim().substring(0, 4)),
							getMth(strArray[1]), getDay(strArray[1]), 
									getHr(strArray[2]), getMin(strArray[2]));										
					String stringL;																																		
					try(BufferedReader br2 = new BufferedReader(new FileReader(locationAdd))){									
						while((stringL=br2.readLine())!=null){
							if(cell_id==Double.parseDouble(stringL.split(",")[0])){
								String[] s=stringL.split(",");									
								if(Double.parseDouble(s[3].trim())!=0||Double.parseDouble(s[4].trim())!=0){
									token++;
									dbRow+=(dateTime.get(Calendar.YEAR)+"/"+(dateTime.get(Calendar.MONTH)+1)+"/"+dateTime.get(Calendar.DAY_OF_MONTH)+" "+dateTime.get(Calendar.HOUR_OF_DAY)+":"+dateTime.get(Calendar.MINUTE)+",");
									dbRow+=(cell_id+",");
									latitude=Double.parseDouble(s[3].trim());
									dbRow+=(latitude+",");
									longitude=Double.parseDouble(s[4].trim());
									dbRow+=(longitude+",");
									address=Jsoup.connect("http://www.findlatitudeandlongitude.com/find-address-from-latitude-and-longitude/?lat="+
											new BigDecimal(latitude).setScale(1, RoundingMode.HALF_UP).toString()+
												"&lon="+new BigDecimal(longitude).setScale(1, RoundingMode.HALF_UP).toString()
												).get().getElementById("address").toString();
									address=address.split(">")[4].trim().equals("</span")?"US":address.split(">")[4].trim().substring(0,address.split(">")[4].trim().length()-6);															
									dbRow+=(address+".");
									Connection cnn=getConnection();
									Statement st=(Statement)cnn.createStatement(); 
								    st.executeUpdate("INSERT INTO placetime(person_id,dateTime,cell_id,latitude,longitude,address) " + "VALUES ("+person_id+",'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTime.getTime())+"',"+cell_id+","+latitude+","+longitude+",'"+address+"')");								     
									System.out.println(token+")"+dbRow);
									dbRow="";
								}																						
									break;
								}								
							}																	
					}catch(IOException e){
						System.out.println("Error with inner buffer reader");
					} catch (SQLException e) {
						System.out.println("Error with statement");
						e.printStackTrace();
					}
					}
				}
			} catch(IOException e){
				System.out.println("Error with outer buffer reader");
			}
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

	private static int getMin(String string) {
		return Integer.parseInt((string.substring(string.length() - 14,string.length() - 12)));
	}

	private static int getHr(String string) {
		int offset=0,hr;
		hr=Integer.parseInt((string.substring(string.length() - 17,string.length() - 15)).trim());
		if(string.substring(string.length()-4, string.length()-2).equals("PM"))	offset=12;
		return offset+hr;
	}

	private static int getDay(String string) {
		return Integer.parseInt((string.substring(string.length() - 2)));		
	}

	private static int getMth(String string) {
		int inx=-1;				
		switch(string.trim().split(" ")[0].substring(2).trim()){
			case "January":	inx=0;	break;
			case "February":	inx=1;	break;
			case "March":	inx=2;	break;
			case "April":	inx=3;	break;
			case "May":	inx=4;	break;
			case "June":	inx=5;	break;
			case "July":	inx=6;	break;
			case "August":	inx=7;	break;
			case "September":	inx=8;	break;
			case "October":	inx=9;	break;
			case "November":	inx=10;	break;
			case "December":	inx=11;	break;
		}		
		return inx;
	}
}

///  Commented Outs
//static String timeAdd="F:/DataMining Project/Selected Dataset/Data in XLS/student4/locs.csv";
//static String locationAdd="F:/DataMining Project/Selected Dataset/Cell IDs with Lat&Long/allLocationsLACclusteredShafqat.csv";