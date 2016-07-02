package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import JBDCManager.ConnectDatabase;
import JBDCManager.DatabaseToArray;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserManager extends JFrame {
	private static final long serialVersionUID = 1L;
	public static DefaultTableModel tableModel;// 定义表格模型对象
	static JTable table;// 定义表格对象
	
	public Object[][] userModel() {
		Object [][]data = DatabaseToArray.dataArray("select 账号 from 用户 where 是否管理员='否'");
		return data;
	}
	
	public UserManager() {
		super();
		setResizable(false);
		setTitle("用户管理");
		setBounds(100, 100, 508, 362);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 510, 279);
		getContentPane().add(scrollPane);
		setLocationRelativeTo(null);
		String[] columnNames = { "用户列表"};// 定义表格列名数组
		Object[][] dataObjects = userModel();

		
		tableModel = new DefaultTableModel(dataObjects, columnNames){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		
		table = new JTable(tableModel);// 创建指定表格模型的表格
		table.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		// 设置表格的选择模式为单选
		
		scrollPane.setViewportView(table);
		
		JButton delete = new JButton("删除");
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1){// 判断是否存在被选中行
					Object get =table.getValueAt(selectedRow, 0);
					// 从表格模型当中删除指定行
					tableModel.removeRow(selectedRow);
					String sql = "delete from 用户 where 账号 ='"+get+"'";
					ConnectDatabase connectDatabase = new ConnectDatabase(sql);
					try {
						connectDatabase.pst.execute();
						connectDatabase.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else {
					JOptionPane.showMessageDialog(null, "未选中任何数据！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		delete.setBounds(259, 291, 105, 36);
		getContentPane().add(delete);
		
		JButton updatePassword = new JButton("修改密码");
		updatePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					String get =(String) table.getValueAt(selectedRow, 0);
					UpdatePasswordWindow updatePasswordWindow = new UpdatePasswordWindow(get);
					updatePasswordWindow.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "未选中任何数据！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		updatePassword.setBounds(142, 291, 105, 36);
		getContentPane().add(updatePassword);
		
		JButton addUserBtn = new JButton("添加用户");
		addUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddUserWindow addUserWindow = new AddUserWindow(table, tableModel, columnNames);
				addUserWindow.setVisible(true);
			}
		});
		addUserBtn.setBounds(25, 291, 105, 36);
		getContentPane().add(addUserBtn);
		
		JButton closeBtn = new JButton("关闭");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeBtn.setBounds(376, 291, 105, 36);
		getContentPane().add(closeBtn);
	
	}
}