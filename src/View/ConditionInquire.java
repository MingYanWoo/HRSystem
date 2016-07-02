package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import JBDCManager.ConnectDatabase;
import JBDCManager.DatabaseToArray;

import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class ConditionInquire extends JFrame {

	private JPanel contentPane;
	private JTextField nameTextField;
	private JTextField ageFirstField;
	private JTextField ageSecondField;
	private JTextField workStationField;
	private JTextField takePartJobFirstField;
	private JTextField takePartJobSecondField;
	private JTextField transferSecondField;
	private JTextField transferFirstField;
	private JTextField retireFirstField;
	private JTextField retireSecondField;
	private JTextField dismissFirstField;
	private JTextField dismissSecondField;
	private JTextField resignFirstField;
	private JTextField resignSecondField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JCheckBox nameCheckBox;
	private JCheckBox ageCheckBox;
	private JCheckBox eduCheckBox;
	private JCheckBox marrigeCheckBox;
	private JComboBox eduComboBox;
	private JComboBox marrigeComboBox;
	private JCheckBox workStationCheckBox;
	private JCheckBox takePartJobCheckBox;
	private JCheckBox notOnJobCheckBox;
	private JRadioButton transferRadioButton;
	private JRadioButton retireRadioButton;
	private JRadioButton dismissRadioButton;
	private JRadioButton resignRadioButton;
	private JTable table;
	private DefaultTableModel tableModel;

	
	//拼接符合条件的SQL语句
	private String conditionSQL(String sql) {
		//按姓名
		if (nameCheckBox.isSelected()) {
			sql = "select * from ("+sql+")a where 姓名 like '%"+nameTextField.getText()+"%'";
		}
		//按年龄
		if (ageCheckBox.isSelected()) {
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
			sql = "select * from ("+sql+")a where 年龄>="+firstAge+" and 年龄<="+secondAge;
		}
		//按学历
		if (eduCheckBox.isSelected()) {
			String edu = eduComboBox.getSelectedItem().toString();
			sql = "select * from ("+sql+")a where 学历='"+edu+"'";
		}
		//按婚姻状态
		if (marrigeCheckBox.isSelected()) {
			String marrige = marrigeComboBox.getSelectedItem().toString();
			sql = "select * from ("+sql+")a where 婚姻状态='"+marrige+"'";
		}
		//按岗位
		if (workStationCheckBox.isSelected()) {
			sql = "select * from ("+sql+")a where 工作岗位='"+workStationField.getText()+"'";
		}
		//按参加工作时间
		if (takePartJobCheckBox.isSelected()) {
			String firstTime,secondTime;
			if (takePartJobFirstField.getText().length() != 0) {
				firstTime = takePartJobFirstField.getText();
			}else {
				firstTime = "";
			}

			if (takePartJobSecondField.getText().length() == 0) {
				secondTime = "current_date";
			}else {
				secondTime = "'"+takePartJobSecondField.getText()+"'";
			}
			sql = "select * from ("+sql+")a where 参加工作时间>='"+firstTime+"' and 参加工作时间<="+secondTime+"";
		}
		return sql;
	}
	
	//拼接不在职的SQL语句
	private String notOnJobSQL(String sql, ColumnName columnNames) {
		//按转出时间
		if (transferRadioButton.isSelected()) {
			String firstTime,secondTime;
			if (transferFirstField.getText().length() != 0) {
				firstTime = transferFirstField.getText();
			}else {
				firstTime = "";
			}

			if (transferSecondField.getText().length() == 0) {
				secondTime = "current_date";
			}else {
				secondTime = "'"+transferSecondField.getText()+"'";
			}
			sql = "select 转出.职工号,姓名,性别,学历,工作岗位,转出时间,转出原因 from 转出,("+sql+")a where 转出.职工号=a.职工号 and 转出时间>='"+firstTime+"' and 转出时间<="+secondTime+"";
			String []column = {"职工号", "姓名", "性别", "学历", "工作岗位", "转出时间", "转出原因"};
			columnNames.column = column;
		}
		//按退休时间
		else if (retireRadioButton.isSelected()) {
			String firstTime,secondTime;
			if (retireFirstField.getText().length() != 0) {
				firstTime = retireFirstField.getText();
			}else {
				firstTime = "";
			}

			if (retireSecondField.getText().length() == 0) {
				secondTime = "current_date";
			}else {
				secondTime = "'"+retireSecondField.getText()+"'";
			}
			sql = "select 退休.职工号,姓名,性别,学历,工作岗位,退休时间,退休原因 from 退休,("+sql+")a where 退休.职工号=a.职工号 and 退休时间>='"+firstTime+"' and 退休时间<="+secondTime+"";
			String []column = {"职工号", "姓名", "性别", "学历", "工作岗位", "退休时间", "退休原因"};
			columnNames.column = column;
		}
		//按辞退时间
		else if (dismissRadioButton.isSelected()) {
			String firstTime,secondTime;
			if (dismissFirstField.getText().length() != 0) {
				firstTime = dismissFirstField.getText();
			}else {
				firstTime = "";
			}

			if (dismissSecondField.getText().length() == 0) {
				secondTime = "current_date";
			}else {
				secondTime = "'"+dismissSecondField.getText()+"'";
			}
			sql = "select 辞退.职工号,姓名,性别,学历,工作岗位,辞退时间,辞退原因 from 辞退,("+sql+")a where 辞退.职工号=a.职工号 and 辞退时间>='"+firstTime+"' and 辞退时间<="+secondTime+"";
			String []column = {"职工号", "姓名", "性别", "学历", "工作岗位", "辞退时间", "辞退原因"};
			columnNames.column = column;
		}
		//按辞职时间
		else if (resignRadioButton.isSelected()) {
			String firstTime,secondTime;
			if (resignFirstField.getText().length() != 0) {
				firstTime = resignFirstField.getText();
			}else {
				firstTime = "";
			}

			if (resignSecondField.getText().length() == 0) {
				secondTime = "current_date";
			}else {
				secondTime = "'"+resignSecondField.getText()+"'";
			}
			sql = "select 辞职.职工号,姓名,性别,学历,工作岗位,辞职时间,辞职原因 from 辞职,("+sql+")a where 辞职.职工号=a.职工号 and 辞职时间>='"+firstTime+"' and 辞职时间<="+secondTime+"";
			String []column = {"职工号", "姓名", "性别", "学历", "工作岗位", "辞职时间", "辞职原因"};
			columnNames.column = column;
		}
		return sql;
	}

	/**
	 * Create the frame.
	 */
	public ConditionInquire() {
		setTitle("条件查询");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 805, 669);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 442, 793, 199);
		contentPane.add(scrollPane);
		
		String[] columnNames = {"职工号","姓名","年龄","性别","学历","婚姻状态","职称","工作岗位","入职时间"};// 定义表格列名数组
		Object[][] dataObjects = null ;

		
		tableModel = new DefaultTableModel(dataObjects, columnNames){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		table = new JTable(tableModel);
		scrollPane.setViewportView(table);
		
		nameCheckBox = new JCheckBox("按姓名    包含有");
		nameCheckBox.setBounds(27, 17, 131, 23);
		contentPane.add(nameCheckBox);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(158, 16, 130, 26);
		contentPane.add(nameTextField);
		nameTextField.setColumns(10);
		
		ageCheckBox = new JCheckBox("按年龄");
		ageCheckBox.setBounds(27, 52, 80, 23);
		contentPane.add(ageCheckBox);
		
		eduComboBox = new JComboBox();
		eduComboBox.setModel(new DefaultComboBoxModel(new String[] {"小学", "初中", "高中", "中专", "大专", "本科", "硕士", "博士"}));
		eduComboBox.setBounds(103, 87, 76, 27);
		contentPane.add(eduComboBox);
		
		eduCheckBox = new JCheckBox("按学历");
		eduCheckBox.setBounds(27, 87, 80, 23);
		contentPane.add(eduCheckBox);
		
		marrigeCheckBox = new JCheckBox("按婚姻状态");
		marrigeCheckBox.setBounds(27, 122, 128, 23);
		contentPane.add(marrigeCheckBox);
		
		marrigeComboBox = new JComboBox();
		marrigeComboBox.setModel(new DefaultComboBoxModel(new String[] {"未婚", "已婚"}));
		marrigeComboBox.setBounds(135, 122, 80, 27);
		contentPane.add(marrigeComboBox);
		
		ageFirstField = new JTextField();
		ageFirstField.setColumns(10);
		ageFirstField.setBounds(103, 51, 55, 26);
		contentPane.add(ageFirstField);
		
		JLabel label = new JLabel("到");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(158, 56, 21, 16);
		contentPane.add(label);
		
		ageSecondField = new JTextField();
		ageSecondField.setColumns(10);
		ageSecondField.setBounds(180, 51, 55, 26);
		contentPane.add(ageSecondField);
		
		workStationCheckBox = new JCheckBox("按岗位");
		workStationCheckBox.setBounds(27, 157, 80, 23);
		contentPane.add(workStationCheckBox);
		
		workStationField = new JTextField();
		workStationField.setColumns(10);
		workStationField.setBounds(103, 156, 130, 26);
		contentPane.add(workStationField);
		
		notOnJobCheckBox = new JCheckBox("不在职");
		notOnJobCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//未选中“不在职”按钮时，按钮和文本框不可用
				if (notOnJobCheckBox.isSelected()) {
					transferRadioButton.setEnabled(true);
					transferFirstField.setEditable(true);
					transferSecondField.setEditable(true);
					
					retireRadioButton.setEnabled(true);
					retireFirstField.setEditable(true);
					retireSecondField.setEditable(true);
					
					dismissRadioButton.setEnabled(true);
					dismissFirstField.setEditable(true);
					dismissSecondField.setEditable(true);
					
					resignRadioButton.setEnabled(true);
					resignFirstField.setEditable(true);
					resignSecondField.setEditable(true);
				}else {
					transferRadioButton.setEnabled(false);
					transferFirstField.setEditable(false);
					transferSecondField.setEditable(false);
					
					retireRadioButton.setEnabled(false);
					retireFirstField.setEditable(false);
					retireSecondField.setEditable(false);
					
					dismissRadioButton.setEnabled(false);
					dismissFirstField.setEditable(false);
					dismissSecondField.setEditable(false);
					
					resignRadioButton.setEnabled(false);
					resignFirstField.setEditable(false);
					resignSecondField.setEditable(false);
				}
			}
		});
		notOnJobCheckBox.setBounds(27, 254, 80, 23);
		contentPane.add(notOnJobCheckBox);
		
		takePartJobCheckBox = new JCheckBox("按参加工作时间");
		takePartJobCheckBox.setBounds(27, 192, 131, 23);
		contentPane.add(takePartJobCheckBox);
		
		JLabel label_1 = new JLabel("到");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(267, 196, 26, 16);
		contentPane.add(label_1);
		
		takePartJobFirstField = new JTextField();
		takePartJobFirstField.setColumns(10);
		takePartJobFirstField.setBounds(157, 191, 111, 26);
		contentPane.add(takePartJobFirstField);
		
		takePartJobSecondField = new JTextField();
		takePartJobSecondField.setColumns(10);
		takePartJobSecondField.setBounds(292, 191, 111, 26);
		contentPane.add(takePartJobSecondField);
		
		transferRadioButton = new JRadioButton("按转出时间");
		transferRadioButton.setSelected(true);
		transferRadioButton.setEnabled(false);
		buttonGroup.add(transferRadioButton);
		transferRadioButton.setBounds(27, 289, 105, 23);
		contentPane.add(transferRadioButton);
		
		transferSecondField = new JTextField();
		transferSecondField.setEditable(false);
		transferSecondField.setColumns(10);
		transferSecondField.setBounds(270, 289, 111, 26);
		contentPane.add(transferSecondField);
		
		transferFirstField = new JTextField();
		transferFirstField.setEditable(false);
		transferFirstField.setColumns(10);
		transferFirstField.setBounds(135, 289, 111, 26);
		contentPane.add(transferFirstField);
		
		JLabel label_2 = new JLabel("到");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(245, 294, 26, 16);
		contentPane.add(label_2);
		
		retireRadioButton = new JRadioButton("按退休时间");
		retireRadioButton.setEnabled(false);
		buttonGroup.add(retireRadioButton);
		retireRadioButton.setBounds(27, 324, 105, 23);
		contentPane.add(retireRadioButton);
		
		retireFirstField = new JTextField();
		retireFirstField.setEditable(false);
		retireFirstField.setColumns(10);
		retireFirstField.setBounds(135, 324, 111, 26);
		contentPane.add(retireFirstField);
		
		JLabel label_3 = new JLabel("到");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(245, 329, 26, 16);
		contentPane.add(label_3);
		
		retireSecondField = new JTextField();
		retireSecondField.setEditable(false);
		retireSecondField.setColumns(10);
		retireSecondField.setBounds(270, 324, 111, 26);
		contentPane.add(retireSecondField);
		
		dismissRadioButton = new JRadioButton("按辞退时间");
		dismissRadioButton.setEnabled(false);
		buttonGroup.add(dismissRadioButton);
		dismissRadioButton.setBounds(27, 359, 105, 23);
		contentPane.add(dismissRadioButton);
		
		dismissFirstField = new JTextField();
		dismissFirstField.setEditable(false);
		dismissFirstField.setColumns(10);
		dismissFirstField.setBounds(135, 359, 111, 26);
		contentPane.add(dismissFirstField);
		
		JLabel label_4 = new JLabel("到");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(245, 364, 26, 16);
		contentPane.add(label_4);
		
		dismissSecondField = new JTextField();
		dismissSecondField.setEditable(false);
		dismissSecondField.setColumns(10);
		dismissSecondField.setBounds(270, 359, 111, 26);
		contentPane.add(dismissSecondField);
		
		resignRadioButton = new JRadioButton("按辞职时间");
		resignRadioButton.setEnabled(false);
		buttonGroup.add(resignRadioButton);
		resignRadioButton.setBounds(27, 394, 105, 23);
		contentPane.add(resignRadioButton);
		
		resignFirstField = new JTextField();
		resignFirstField.setEditable(false);
		resignFirstField.setColumns(10);
		resignFirstField.setBounds(135, 394, 111, 26);
		contentPane.add(resignFirstField);
		
		JLabel label_5 = new JLabel("到");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(245, 399, 26, 16);
		contentPane.add(label_5);
		
		resignSecondField = new JTextField();
		resignSecondField.setEditable(false);
		resignSecondField.setColumns(10);
		resignSecondField.setBounds(270, 394, 111, 26);
		contentPane.add(resignSecondField);
		
		JButton InquireBtn = new JButton("查   询");
		InquireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//判断是否选了“不在职”
				if (!notOnJobCheckBox.isSelected()) {
					String sql = "select * from 员工 where 是否在职='是'";
					//拼接符合条件的SQL语句
					sql = conditionSQL(sql);
					//查询的数据转为数组模型
					Object[][] data = DatabaseToArray.dataArray(sql);
					tableModel.setDataVector(data, columnNames);
				}else {
					//不在职的员工
					String sql = "select * from 员工 where 是否在职='否'";
					//拼接符合条件的SQL语句
					ColumnName columnName = new ColumnName();
					sql = conditionSQL(sql);
					sql = notOnJobSQL(sql,columnName);
					//查询的数据转为数组模型
					Object[][] data = DatabaseToArray.dataArray(sql);
					tableModel.setDataVector(data, columnName.column);
				}
			}
		});
		InquireBtn.setBounds(542, 289, 187, 59);
		contentPane.add(InquireBtn);
		
		JButton closeBtn = new JButton("关   闭");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		closeBtn.setBounds(542, 359, 187, 59);
		contentPane.add(closeBtn);
	}
	
	//列名
	private class ColumnName{
		public String[] column = {"职工号", "姓名", "性别", "学历", "工作岗位", "时间", "原因"};
	}
}

 
