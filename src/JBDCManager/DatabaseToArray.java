package JBDCManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseToArray {

	public  static Object[][] dataArray(String sql) {  
	    Object[][] data = null; 
	    ConnectDatabase connectDatabase = new ConnectDatabase(sql);
	    try {
			ResultSet resultSet = connectDatabase.pst.executeQuery();
			resultSet.last();  
			int rows = resultSet.getRow();  
			data = new Object[rows][];    
			java.sql.ResultSetMetaData md = resultSet.getMetaData();//获取记录集的元数据  
			int columnCount = md.getColumnCount();//列数  
			resultSet.first(); 
			resultSet.previous();
			int k = 0;  
			while(resultSet.next()) {  
            Object[] row = new Object[columnCount];  
            for(int i=0; i<columnCount; i++) {  
            	row[i] = resultSet.getObject(i+1).toString();  
            }  
            data[k] = row;  
            k++;  
			}  
			resultSet.close();
			connectDatabase.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return data;  
	}
}
