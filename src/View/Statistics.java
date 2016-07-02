package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import JBDCManager.ConnectDatabase;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Statistics extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField ageFirstField;
	private JTextField ageSecondField;
	private JTextField timeFirstField;
	private JTextField timeSecondField;
	private DefaultTableModel tableModel;// 定义表格模型对象
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	//根据SQL获取数据
	public Object[][] getData(String sql) {  
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
	
	//设置按性别，按学历，按婚姻状态，按岗位的数据
	public void setData(String str) {
		Object [][] tableValues = getData("select "+str+",count(职工号) from 员工 group by "+str+"");
		String []columnNames = {str, "人数"};
		tableModel.setDataVector(tableValues, columnNames);
	}

	/**
	 * Create the frame.
	 */
	public Statistics() {
		setTitle("条件统计");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 142, 546, 209);
		contentPane.add(scrollPane);
		
		String[] columnNames = {};// 定义表格列名数组
		Object[][] tableValues = {};// 定义表格数据数组
		
		tableModel = new DefaultTableModel(tableValues, columnNames){
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				// 无条件返回 false，任何单元格都不让编辑。
				return false;
			}
		};
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		// 设置表格的选择模式为单选
		table.enable(false);
		
		JLabel label = new JLabel("请选择条件");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 558, 22);
		contentPane.add(label);
		
		JRadioButton eduRadioButton = new JRadioButton("按学历");
		buttonGroup.add(eduRadioButton);
		eduRadioButton.setBounds(168, 34, 90, 23);
		contentPane.add(eduRadioButton);
		
		JRadioButton sexRadioButton = new JRadioButton("按性别");
		buttonGroup.add(sexRadioButton);
		sexRadioButton.setBounds(43, 34, 90, 23);
		contentPane.add(sexRadioButton);
		
		JRadioButton marrigeRadioButton = new JRadioButton("按婚姻状态");
		buttonGroup.add(marrigeRadioButton);
		marrigeRadioButton.setBounds(404, 34, 141, 23);
		contentPane.add(marrigeRadioButton);
		
		JRadioButton workStationRadioButton = new JRadioButton("按岗位");
		buttonGroup.add(workStationRadioButton);
		workStationRadioButton.setBounds(292, 34, 90, 23);
		contentPane.add(workStationRadioButton);
		
		JRadioButton ageRadioButton = new JRadioButton("按年龄");
		buttonGroup.add(ageRadioButton);
		ageRadioButton.setBounds(43, 104, 90, 22);
		contentPane.add(ageRadioButton);
		
		ageFirstField = new JTextField();
		ageFirstField.setBounds(130, 102, 47, 26);
		contentPane.add(ageFirstField);
		ageFirstField.setColumns(10);
		
		JLabel label_1 = new JLabel("到");
		label_1.setBounds(189, 107, 18, 16);
		contentPane.add(label_1);
		
		ageSecondField = new JTextField();
		ageSecondField.setColumns(10);
		ageSecondField.setBounds(211, 102, 47, 26);
		contentPane.add(ageSecondField);
		
		JRadioButton timeRadioButton = new JRadioButton("按参加工作时间");
		buttonGroup.add(timeRadioButton);
		timeRadioButton.setBounds(43, 69, 130, 23);
		contentPane.add(timeRadioButton);
		
		timeFirstField = new JTextField();
		timeFirstField.setColumns(10);
		timeFirstField.setBounds(178, 69, 112, 26);
		contentPane.add(timeFirstField);
		
		JLabel label_2 = new JLabel("到");
		label_2.setBounds(302, 69, 18, 26);
		contentPane.add(label_2);
		
		timeSecondField = new JTextField();
		timeSecondField.setColumns(10);
		timeSecondField.setBounds(328, 68, 112, 26);
		contentPane.add(timeSecondField);
		
		JButton statisticBtn = new JButton("统计");
		statisticBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sexRadioButton.isSelected()) {
					setData("性别");
				}else if (eduRadioButton.isSelected()) {
					setData("学历");
				}else if (workStationRadioButton.isSelected()) {
					setData("工作岗位");
				}else if (marrigeRadioButton.isSelected()) {
					setData("婚姻状态");
				}else if (timeRadioButton.isSelected()) {
					String firstTime,secondTime;
					if (timeFirstField.getText().length() != 0) {
						firstTime = timeFirstField.getText();
					}else {
						firstTime = "";
					}
					
					if (timeSecondField.getText().length() == 0) {
						secondTime = "current_date";
					}else {
						secondTime = "'"+timeSecondField.getText()+"'";
					}
					String sql = "select count(职工号) from 员工 where 参加工作时间>='"+firstTime+"' and 参加工作时间<="+secondTime+"";
					Object [][] tableValues = getData(sql);
					String []columnNames = {"人数"};
					tableModel.setDataVector(tableValues, columnNames);
				}else if (ageRadioButton.isSelected()) {
					String firstAge,secondAge;
					if (ageFirstField.getText().length() == 0) {
						firstAge = "0";
					}else {
						firstAge = ageFirstField.getText();
					}
					
					if (ageSecondField.getText().length() == 0) {
						secondAge = "1000";
					}else {
						secondAge = ageSecondField.getText();
					}
					String sql = "select count(职工号) from 员工 where 年龄>='"+firstAge+"' and 年龄<='"+secondAge+"'";
					Object [][] tableValues = getData(sql);
					String []columnNames = {"人数"};
					tableModel.setDataVector(tableValues, columnNames);
				}
			}
		});
		statisticBtn.setBounds(292, 103, 112, 26);
		contentPane.add(statisticBtn);
		
		JButton button = new JButton("关闭");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(404, 103, 112, 26);
		contentPane.add(button);
		
	}
}
