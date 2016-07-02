package View;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.*;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.mysql.jdbc.ResultSetMetaData;
import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import JBDCManager.ConnectDatabase;
import JBDCManager.DatabaseToArray;
import sun.security.action.GetIntegerAction;
import java.awt.Font;

public class StaffManager extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;// 定义表格模型对象
	private JTable table;
	private JTextField nameField;
	private JTextField numberField;
	private JTextField ageField;
	private JTextField jobTitleField;
	private JTextField workStationField;
	private JTextField timeField;
	private JComboBox sexComboBox;
	private JComboBox eduComboBox;
	private JComboBox maritalStatusComboBox;
	
	public int getEdu(String str) {
		if (str.equals("小学")) {
			return 0;
		}else if (str.equals("初中")) {
			return 1;
		}else if (str.equals("高中")) {
			return 2;
		}else if (str.equals("中专")) {
			return 3;
		}else if (str.equals("大专")) {
			return 4;
		}else if (str.equals("本科")) {
			return 5;
		}else if (str.equals("硕士")) {
			return 6;
		}else{
			return 7;
		}
	}
	
	public int getSex(String str) {
		if (str.equals("男")) {
			return 0;
		}else{
			return 1;
		}
	}
	
	public int getMarrige(String str) {
		if (str.equals("未婚")) {
			return 0;
		}else{
			return 1;
		}
	}
	
	public StaffManager() {
		super();
		setResizable(false);
		setTitle("员工管理");
		setBounds(100, 100, 834, 523);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 834, 392);
		getContentPane().add(scrollPane);
		setLocationRelativeTo(null);
		String[] columnNames = { "职工号","姓名","年龄","性别","学历","婚姻状态","职称","工作岗位","入职时间"};// 定义表格列名数组
		Object[][] tableValues = DatabaseToArray.dataArray("select * from 员工 where 是否在职='是'");// 定义表格数据数组
		
		//创建表格模型
		tableModel = new DefaultTableModel(tableValues, columnNames){
		    public boolean isCellEditable(int rowIndex, int columnIndex) {
		        // 无条件返回 false，任何单元格都不让编辑。
		        return false;
		    }
		};
		
		table = new JTable(tableModel);
//		table.setRowSorter(new TableRowSorter<>(tableModel));// 设置表格的排序器
		// 设置表格的选择模式为单选
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// 为表格添加鼠标事件监听器
		table.addMouseListener(new MouseAdapter() {
			// 发生了点击事件
			public void mouseClicked(MouseEvent e) {
				// 获得被选中行的索引
				int selectedRow = table.getSelectedRow();
				// 从表格模型中获得指定单元格的值
				Object number = tableModel.getValueAt(selectedRow, 0);
				Object name = tableModel.getValueAt(selectedRow, 1);
				Object age = tableModel.getValueAt(selectedRow, 2);
				Object sex = tableModel.getValueAt(selectedRow, 3);
				Object edu = tableModel.getValueAt(selectedRow, 4);
				Object marrige = tableModel.getValueAt(selectedRow, 5);
				Object jobTitle = tableModel.getValueAt(selectedRow, 6);
				Object workStation = tableModel.getValueAt(selectedRow, 7);
				Object time = tableModel.getValueAt(selectedRow, 8);
				
				// 将值赋值给文本框
				numberField.setText(number.toString());
				nameField.setText(name.toString());
				ageField.setText(age.toString());
				jobTitleField.setText(jobTitle.toString());
				workStationField.setText(workStation.toString());
				timeField.setText(time.toString());
				eduComboBox.setSelectedIndex(getEdu(edu.toString()));
				sexComboBox.setSelectedIndex(getSex(sex.toString()));
				maritalStatusComboBox.setSelectedIndex(getMarrige(marrige.toString()));
			}
		});
		scrollPane.setViewportView(table);
		final JPanel panel = new JPanel();
		panel.setBounds(0, 392, 834, 109);
		getContentPane().add(panel);
		panel.setLayout(null);
		JLabel label_1 = new JLabel("职工号：");
		label_1.setBounds(38, 11, 52, 16);
		panel.add(label_1);
		numberField = new JTextField("", 5);
		numberField.setBounds(89, 6, 107, 26);
		panel.add(numberField);
		JLabel label_2 = new JLabel("姓名：");
		label_2.setBounds(202, 11, 39, 16);
		panel.add(label_2);
		nameField = new JTextField("", 5);
		nameField.setBounds(242, 6, 107, 26);
		panel.add(nameField);
		JLabel label_3 = new JLabel("年龄：");
		label_3.setBounds(358, 11, 39, 16);
		panel.add(label_3);
		ageField = new JTextField("", 3);
		ageField.setBounds(397, 6, 107, 26);
		panel.add(ageField);
		final JButton addButton = new JButton("添加");
		addButton.setBounds(561, 8, 107, 47);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numberField.getText().length()!=0 && nameField.getText().length()!=0 && ageField.getText().length()!=0 
						&& jobTitleField.getText().length()!=0 && workStationField.getText().length()!=0 
						&& timeField.getText().length()!=0) {
					int rowCount = tableModel.getRowCount();
					int mark = 0;
					for (int i = 0; i < rowCount; i++){
						if (numberField.getText().equals(tableModel.getValueAt(i, 0))) {
							JOptionPane.showMessageDialog(null, "已存在该职工号！", "错误", JOptionPane.ERROR_MESSAGE);
							mark = 1;
							break;
						}
					}
					if (mark == 0) {
						String rowValue[] = {numberField.getText(), nameField.getText(), ageField.getText(), 
								sexComboBox.getSelectedItem().toString(), eduComboBox.getSelectedItem().toString(), 
								maritalStatusComboBox.getSelectedItem().toString(), jobTitleField.getText(),
								workStationField.getText(), timeField.getText()};
						tableModel.addRow(rowValue);
						//转换
						String sex = sexComboBox.getSelectedItem().toString();
						String edu = eduComboBox.getSelectedItem().toString();
						String marrige = maritalStatusComboBox.getSelectedItem().toString();
						//SQL语句
						String sql = "insert into 员工 values ('"+numberField.getText()+"','"
						+nameField.getText()+"',"+ageField.getText()+",'"+sex+"','"+edu+"','"
						+marrige+"','"+jobTitleField.getText()+"','"+workStationField.getText()+"','"
						+timeField.getText()+"','是')";
						//连接数据库
						ConnectDatabase connectDatabase = new ConnectDatabase(sql);
						try {
							connectDatabase.pst.execute();
							connectDatabase.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						//清空文本框
						numberField.setText("");
						nameField.setText("");
						ageField.setText("");
						jobTitleField.setText("");
						workStationField.setText("");
						timeField.setText("");
						sexComboBox.setSelectedIndex(0);
						eduComboBox.setSelectedIndex(0);
						maritalStatusComboBox.setSelectedIndex(0);
					}
				}else {
					JOptionPane.showMessageDialog(null, "有输入框为空！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		JLabel label = new JLabel("性别：");
		label.setBounds(51, 43, 39, 16);
		panel.add(label);
		
		sexComboBox = new JComboBox();
		sexComboBox.setBounds(89, 39, 107, 27);
		sexComboBox.setModel(new DefaultComboBoxModel(new String[] {"男", "女"}));
		sexComboBox.setToolTipText("");
		panel.add(sexComboBox);
		
		JLabel lblNewLabel = new JLabel("学历：");
		lblNewLabel.setBounds(202, 43, 39, 16);
		panel.add(lblNewLabel);
		
		eduComboBox = new JComboBox();
		eduComboBox.setBounds(242, 39, 107, 27);
		eduComboBox.setModel(new DefaultComboBoxModel(new String[] {"小学", "初中", "高中","中专","大专", "本科", "硕士", "博士"}));
		panel.add(eduComboBox);
		panel.add(addButton);
		final JButton updButton = new JButton("修改");
		updButton.setBounds(680, 8, 107, 47);
		updButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();// 获得被选中行的索引
				if (selectedRow != -1) {// 判断是否存在被选中行
					Object get = table.getValueAt(selectedRow, 0);
					if (numberField.getText().length()!=0 && nameField.getText().length()!=0 
							&& ageField.getText().length()!=0 && jobTitleField.getText().length()!=0 
							&& workStationField.getText().length()!=0 && timeField.getText().length()!=0) {
						if (numberField.getText().equals(get)) {
							//设置表格数值
							tableModel.setValueAt(numberField.getText(), selectedRow, 0);
							tableModel.setValueAt(nameField.getText(), selectedRow, 1);
							tableModel.setValueAt(ageField.getText(), selectedRow, 2);
							tableModel.setValueAt(sexComboBox.getSelectedItem().toString(), selectedRow, 3);
							tableModel.setValueAt(eduComboBox.getSelectedItem().toString(), selectedRow, 4);
							tableModel.setValueAt(maritalStatusComboBox.getSelectedItem().toString(), selectedRow, 5);
							tableModel.setValueAt(jobTitleField.getText(), selectedRow, 6);
							tableModel.setValueAt(workStationField.getText(), selectedRow, 7);
							tableModel.setValueAt(timeField.getText(), selectedRow, 8);
							//转换
							String sex = sexComboBox.getSelectedItem().toString();
							String edu = eduComboBox.getSelectedItem().toString();
							String marrige = maritalStatusComboBox.getSelectedItem().toString();
							//SQL语句
							String sql = "update 员工 set 职工号='"+numberField.getText()+"',姓名='"
							+nameField.getText()+"',年龄="+ageField.getText()+",性别='"+sex+"',学历='"
							+edu+"',婚姻状态='"+marrige+"',职称='"+jobTitleField.getText()+"',工作岗位='"
							+workStationField.getText()+"',参加工作时间='"+timeField.getText()
							+"',是否在职='是' where 职工号='"+get+"'";
							//连接数据库
							ConnectDatabase connectDatabase = new ConnectDatabase(sql);
							try {
								connectDatabase.pst.execute();
								connectDatabase.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
							//清空文本框
							numberField.setText("");
							nameField.setText("");
							ageField.setText("");
							jobTitleField.setText("");
							workStationField.setText("");
							timeField.setText("");
							sexComboBox.setSelectedIndex(0);
							eduComboBox.setSelectedIndex(0);
							maritalStatusComboBox.setSelectedIndex(0);
						}else {
							int rowCount = tableModel.getRowCount();
							int mark = 0;
							for (int i = 0; i < rowCount; i++){
								if (numberField.getText().equals(tableModel.getValueAt(i, 0))) {
									JOptionPane.showMessageDialog(null, "职工号重复！", "错误", JOptionPane.ERROR_MESSAGE);
									mark = 1;
									break;
								}
							}
							if (mark == 0) {
								//设置表格数值
								tableModel.setValueAt(numberField.getText(), selectedRow, 0);
								tableModel.setValueAt(nameField.getText(), selectedRow, 1);
								tableModel.setValueAt(ageField.getText(), selectedRow, 2);
								tableModel.setValueAt(sexComboBox.getSelectedItem().toString(), selectedRow, 3);
								tableModel.setValueAt(eduComboBox.getSelectedItem().toString(), selectedRow, 4);
								tableModel.setValueAt(maritalStatusComboBox.getSelectedItem().toString(), selectedRow, 5);
								tableModel.setValueAt(jobTitleField.getText(), selectedRow, 6);
								tableModel.setValueAt(workStationField.getText(), selectedRow, 7);
								tableModel.setValueAt(timeField.getText(), selectedRow, 8);
								//转换
								String sex = sexComboBox.getSelectedItem().toString();
								String edu = eduComboBox.getSelectedItem().toString();
								String marrige = maritalStatusComboBox.getSelectedItem().toString();
								//SQL语句
								String sql = "update 员工 set 职工号='"+numberField.getText()+"',姓名='"
								+nameField.getText()+"',年龄="+ageField.getText()+",性别='"+sex+"',学历='"+edu+"',婚姻状态='"
										+marrige+"',职称='"+jobTitleField.getText()+"',工作岗位='"
								+workStationField.getText()+"',参加工作时间='"+timeField.getText()+"',是否在职='是' where 职工号='"+get+"'";
								ConnectDatabase connectDatabase = new ConnectDatabase(sql);
								try {
									connectDatabase.pst.execute();
									connectDatabase.close();
								} catch (SQLException e1) {
										e1.printStackTrace();
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(null, "有输入框为空！", "错误", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "未选择任何数据！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panel.add(updButton);
		final JButton delButton = new JButton("删除");
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();// 获得被选中行的索引
				if (selectedRow != -1){// 判断是否存在被选中行
					Object get =table.getValueAt(selectedRow, 0);
					// 从表格模型当中删除指定行
					tableModel.removeRow(selectedRow);
					//SQL语句
					String sql = "delete from 员工 where 职工号='"+get+"'";
					ConnectDatabase connectDatabase = new ConnectDatabase(sql);
					try {
						connectDatabase.pst.execute();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
					//清空文本框
					numberField.setText("");
					nameField.setText("");
					ageField.setText("");
					jobTitleField.setText("");
					workStationField.setText("");
					timeField.setText("");
					sexComboBox.setSelectedIndex(0);
					eduComboBox.setSelectedIndex(0);
					maritalStatusComboBox.setSelectedIndex(0);
				}else {
					JOptionPane.showMessageDialog(null, "未选择任何数据！", "错误", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		delButton.setBounds(561, 56, 107, 47);
		delButton.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		panel.add(delButton);
		
		JButton button = new JButton("关闭");
		button.setBounds(680, 56, 107, 47);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel.add(button);
		
		JLabel label_4 = new JLabel("婚姻状态：");
		label_4.setBounds(358, 43, 75, 16);
		panel.add(label_4);
		
		
		maritalStatusComboBox = new JComboBox();
		maritalStatusComboBox.setModel(new DefaultComboBoxModel(new String[] {"未婚", "已婚"}));
		maritalStatusComboBox.setToolTipText("");
		maritalStatusComboBox.setBounds(422, 39, 82, 27);
		panel.add(maritalStatusComboBox);
		
		JLabel label_5 = new JLabel("职称：");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(38, 75, 52, 16);
		panel.add(label_5);
		
		jobTitleField = new JTextField("", 5);
		jobTitleField.setBounds(89, 71, 107, 26);
		panel.add(jobTitleField);
		
		JLabel label_6 = new JLabel("工作岗位：");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(202, 75, 65, 16);
		panel.add(label_6);
		
		workStationField = new JTextField("", 5);
		workStationField.setBounds(265, 71, 82, 26);
		panel.add(workStationField);
		
		JLabel label_7 = new JLabel("入职时间：");
		label_7.setBounds(358, 75, 75, 16);
		panel.add(label_7);
		
		timeField = new JTextField("", 5);
		timeField.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		timeField.setBounds(422, 71, 82, 26);
		panel.add(timeField);
		
	}
}