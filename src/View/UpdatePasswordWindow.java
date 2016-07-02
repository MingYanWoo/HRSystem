package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import JBDCManager.ConnectDatabase;
import javafx.scene.control.PasswordField;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

public class UpdatePasswordWindow extends JFrame {

	private JPanel contentPane;
	private static JPasswordField passwordField;
	private static JPasswordField passwordField_1;
	private static JPasswordField passwordField_2;
	private Object username;

	/**
	 * Create the frame.
	 */
	public UpdatePasswordWindow(String str) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 297, 251);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel label = new JLabel("用户名");
		label.setBounds(60, 39, 52, 16);
		contentPane.add(label);
		
		JLabel username = new JLabel(str);
		username.setBounds(140, 39, 189, 16);
		contentPane.add(username);
		
		JLabel warn = new JLabel("");
		warn.setHorizontalAlignment(SwingConstants.CENTER);
		warn.setForeground(Color.RED);
		warn.setBounds(6, 11, 285, 16);
		contentPane.add(warn);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(124, 66, 117, 26);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(124, 96, 117, 26);
		contentPane.add(passwordField_1);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(124, 127, 117, 26);
		contentPane.add(passwordField_2);
		
		JLabel label_1 = new JLabel("当前密码");
		label_1.setBounds(51, 71, 61, 16);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("新密码");
		label_2.setBounds(60, 101, 52, 16);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("确认密码");
		label_3.setBounds(51, 132, 61, 16);
		contentPane.add(label_3);
		
		JButton button = new JButton("修改");
		button.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String sql = "select 密码 from 用户 where 账号='"+str+"'";
				ConnectDatabase connectDatabase = new ConnectDatabase(sql);
				ResultSet rs;
				try {
					rs = connectDatabase.pst.executeQuery();
					rs.next();
					if(passwordField.getText().equals(rs.getString("密码"))){
						if(passwordField_1.getText().equals(passwordField_2.getText())){
							String sql1 = "update 用户 set 密码='"+passwordField_1.getText()+"' where 账号='"+str+"'";
							ConnectDatabase connectDatabase2 = new ConnectDatabase(sql1);
							connectDatabase2.pst.execute();
							dispose();
							JOptionPane.showMessageDialog(null, "修改成功！", null, JOptionPane.INFORMATION_MESSAGE);
						}else{
							warn.setText("密码输入不一致！");
							passwordField.setText("");
							passwordField_1.setText("");
							passwordField_2.setText("");
						}
					}else{
						warn.setText("密码错误！");
						passwordField.setText("");
						passwordField_1.setText("");
						passwordField_2.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(156, 165, 85, 29);
		contentPane.add(button);
		
		JButton button_1 = new JButton("返回");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button_1.setBounds(61, 165, 85, 29);
		contentPane.add(button_1);
		
	}

}
