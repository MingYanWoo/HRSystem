package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import JBDCManager.ConnectDatabase;
import JBDCManager.DatabaseToArray;
import sun.awt.RepaintArea;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class AddUserWindow extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public AddUserWindow(JTable table,DefaultTableModel tableModel,String[] columnNames) {
		setAlwaysOnTop(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		textField = new JTextField();
		textField.setBounds(103, 43, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(103, 73, 130, 26);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(103, 103, 130, 26);
		contentPane.add(passwordField_1);
		
		JLabel label = new JLabel("用户名");
		label.setBounds(56, 48, 47, 16);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setBounds(66, 78, 40, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("确认密码");
		label_2.setBounds(42, 108, 61, 16);
		contentPane.add(label_2);
		
		JLabel warn = new JLabel("");
		warn.setForeground(Color.RED);
		warn.setBounds(103, 15, 117, 16);
		contentPane.add(warn);
		
		JButton button = new JButton("确认");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String getUser = textField.getText();
				String sql = "select * from 用户 where 账号='"+getUser+"'";
				ConnectDatabase connectDatabase = new ConnectDatabase(sql);
				ResultSet rs;
				try {
					rs = connectDatabase.pst.executeQuery();
					if(rs.next()){
						warn.setText("用户名已存在！");
					}else{
						
						if(passwordField.getText().equals(passwordField_1.getText()))
						{
							String sql1 = "insert into 用户 values('"+textField.getText()+"','"+passwordField.getText()+"','否')";
							ConnectDatabase connectDatabase2 = new ConnectDatabase(sql1);
							connectDatabase2.pst.execute();
							connectDatabase2.close();
							
							Object [][]data = DatabaseToArray.dataArray("select 账号,密码 from 用户 where 是否管理员='否'");
							tableModel.setDataVector(data, columnNames);
							table.updateUI();
							
							dispose();
						}else{
							warn.setText("输入密码不一致！");
							passwordField.setText("");
							passwordField_1.setText("");
						}
					}
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(151, 141, 98, 29);
		contentPane.add(button);
		
		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(52, 141, 98, 29);
		contentPane.add(button_1);
		
		
	}

}
