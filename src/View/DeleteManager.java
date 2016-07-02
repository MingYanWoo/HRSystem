package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import JBDCManager.ConnectDatabase;
import JBDCManager.DatabaseToArray;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class DeleteManager extends JFrame {

	private DefaultTableModel onJobTableModel;// 定义表格模型对象
	private DefaultTableModel transferTableModel;// 定义表格模型对象
	private JPanel contentPane;
	private JTable transferTable;
	private JTable onJobTable;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextField reasonTextField;
	private JTextField timeTextField;
	private JButton closeBtn;
	private String delectName;

		//将数据库员工表转化为表格数组模型
		public Object[][] onJobModel() {  
		    Object[][] data = DatabaseToArray.dataArray("select * from 员工 where 是否在职='是'"); 
		    return data;  
		}
		
		//将数据库对应删除表转化为表格数组模型
		public Object[][] deleteModel() {  
		    Object[][] data = DatabaseToArray.dataArray("select "+delectName+".职工号,员工.姓名,"+delectName+"原因,"+delectName+"时间 from "+delectName+",员工 where "+delectName+".职工号=员工.职工号"); 
		    return data;  
		}
	
	/**
	 * Create the frame.
	 */
	public DeleteManager(String delectName) {
		this.delectName = delectName;
		setResizable(false);
		setTitle(delectName+"管理");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(1, 24, 758, 182);
		contentPane.add(scrollPane);
		
		
		//辞职员工表
		String[] transferColumnNames = { "职工号", "姓名",delectName+"原因",delectName+"时间"};// 定义表格列名数组
		Object[][] transferTableValues = deleteModel();// 定义表格数据数组

		transferTableModel = new DefaultTableModel(transferTableValues, transferColumnNames){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// 无条件返回 false，任何单元格都不让编辑。
				return false;
			}
		};
		transferTable = new JTable(transferTableModel);
		scrollPane.setViewportView(transferTable);
		// 设置表格的选择模式为单选
		transferTable.enable(false);
		
		JLabel label = new JLabel(delectName+"员工");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 1, 760, 24);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("在职员工");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(0, 206, 760, 24);
		contentPane.add(label_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(1, 231, 758, 182);
		contentPane.add(scrollPane_1);
		
		//在职员工表
		String[] onJobcolumnNames = { "职工号","姓名","年龄","性别","学历","婚姻状态","职称","工作岗位","入职时间"};// 定义表格列名数组
		Object[][] onJobTableValues = onJobModel();// 定义表格数据数组
		
		onJobTableModel = new DefaultTableModel(onJobTableValues, onJobcolumnNames){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		onJobTable = new JTable(onJobTableModel);
		scrollPane_1.setViewportView(onJobTable);
		// 设置表格的选择模式为单选
		onJobTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JButton button = new JButton("确  认");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = onJobTable.getSelectedRow();// 获得被选中行的索引
				if (selectedRow != -1) {// 判断是否存在被选中行
					Object get = onJobTable.getValueAt(selectedRow, 0);
					if (reasonTextField.getText().length() != 0 && timeTextField.getText().length() != 0) {
						// 从表格模型当中删除指定行
						onJobTableModel.removeRow(selectedRow);
						//更新员工状态SQL语句
						String updateSQL = "update 员工 set 是否在职='否' where 职工号='"+get+"'";
						ConnectDatabase conn = new ConnectDatabase(updateSQL);
						try {
							conn.pst.execute();
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						//插入辞职表的SQL语句
						String insertSQL = "insert into "+delectName+" values ('"+get+"','"+reasonTextField.getText()+"','"+timeTextField.getText()+"')";
						ConnectDatabase conn1 = new ConnectDatabase(insertSQL);
						try {
							conn1.pst.execute();
							conn1.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						//更新transferTableModel
						transferTableModel.setDataVector(deleteModel(), transferColumnNames);

						//清空输入框
						reasonTextField.setText("");
						timeTextField.setText("");
						
					}else {
						JOptionPane.showMessageDialog(null, "有输入框为空！", "错误", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "未选择任何数据！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		button.setBounds(528, 422, 107, 50);
		contentPane.add(button);
		
		JLabel label_2 = new JLabel(delectName+"原因：");
		label_2.setBounds(18, 438, 65, 16);
		contentPane.add(label_2);
		
		reasonTextField = new JTextField();
		reasonTextField.setBounds(83, 433, 199, 26);
		contentPane.add(reasonTextField);
		reasonTextField.setColumns(10);
		
		JLabel label_3 = new JLabel(delectName+"时间：");
		label_3.setBounds(294, 438, 65, 16);
		contentPane.add(label_3);
		
		timeTextField = new JTextField();
		timeTextField.setColumns(10);
		timeTextField.setBounds(362, 433, 140, 26);
		contentPane.add(timeTextField);
		
		closeBtn = new JButton("关  闭");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeBtn.setBounds(647, 422, 107, 50);
		contentPane.add(closeBtn);
	}
}
