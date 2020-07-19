package read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Pattern;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class Test {
	
	public static void main (String[] args) {
		File file = new File("C:/Users/fl/workspace/catalina.out");
		txt2String(file);
	}
	
  public static void txt2String(File file){
	  Connection conn = getConn();
        try{
        	//File out = new File("C:/Users/fl/workspace/1.txt");
        	PrintWriter pw = new PrintWriter("C:/Users/fl/workspace/ALL0824.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	int insert = s.indexOf("INSERT INTO");
            	int update = s.indexOf("UPDATE");
            	int delete = s.indexOf("DELETE FROM");
            	int aaa = s.indexOf("Preparing Statement");
            	int session = s.indexOf("C_USERSESSION");
            	int BBB = s.indexOf("INSERT INTO C_BUSINESS");
            	if((insert != -1 || update != -1 || delete != -1)&& session == -1 && aaa != -1 && BBB ==-1) {
            		String s1 = s.substring(insert > 0 ? insert : (update >0 ? update: delete));
            		//System.out.println(s1);
            		String paramLine;
            		String Parameters = "" ;
            		while((paramLine = br.readLine()) != null) {
            			int p = paramLine.indexOf("Parameters");
            			if(p != -1) {
            				 Parameters = paramLine.substring(p+11);
            				//System.out.println(Parameters);
            				break;
            			}
            		}
            		
            		String s2 = Parameters.replace("[", "").replace("]", "");
            		String[] aa = s2.split(",");
            		for(String ss : aa) {
            			ss = ss.trim();
            			if("".equals(ss)) {
            				ss = "''";
            			}
            			if(!"null".equals(ss) && !isInteger(ss) && !"true".equals(ss)) {
            				ss = "'"+ss+"'";
            			} 
            			s1 = s1.replaceFirst("\\?", ss);
            		}
            		System.out.println(s1);
            		 //pw.write(s1);
            		 PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(s1);
            		 try {
						pstmt.execute();
					} catch (Exception e) {
						e.printStackTrace();
						pw.write(e.getMessage());
					}
            		 // System.out.println(s);
            	}
            	// System.out.println(s);
            }
            br.close(); 
            pw.flush();
			   pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
  
  public static boolean isInteger(String str) {  
      Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
      return pattern.matcher(str).matches();  
}
  
  private static Connection getConn() {
	    String driver = "com.mysql.jdbc.Driver";
	    String url = "jdbc:mysql://121.40.214.176:3306/tz_yztb?useUnicode=true&characterEncoding=UTF8";
	    String username = "fline";
	    String password = "000000";
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}

}
