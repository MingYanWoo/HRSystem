package JBDCManager;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class ConnectDatabase {
    public static final String url = "jdbc:mysql://127.0.0.1/HRDB?useUnicode=true&characterEncoding=utf-8&useSSL=false";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "123456";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public ConnectDatabase(String sql) {  
        try {  
            Class.forName(name);													//指定连接类型  
            conn = (Connection) DriverManager.getConnection(url, user, password);	//获取连接  
            pst = (PreparedStatement) conn.prepareStatement(sql);					//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}
