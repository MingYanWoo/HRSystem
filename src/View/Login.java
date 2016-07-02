package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.glass.ui.TouchInputSupport;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import com.sun.javafx.tk.Toolkit;
import com.sun.swing.internal.plaf.metal.resources.metal;

import JBDCManager.ConnectDatabase;
import jdk.internal.dynalink.beans.StaticClass;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private static JTextField usernameField;
	private static JPasswordField passwordField;
	private static JLabel warningLabel;
	private JButton exitBtn;
	private JButton loginBtn;
	public static Login frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();								
				}
			}
		});
	}
	
	//检查用户名和密码是否正确
	public static void checkUser(Login frame){
		if (usernameField.getText().length() != 0 && passwordField.getText().length() != 0) {
			String sql = "select * from 用户 where 账号='"+usernameField.getText()+"'";		//拼接SQL语句
			ConnectDatabase connectDatabase = new ConnectDatabase(sql);
			try {
				ResultSet resultSet = connectDatabase.pst.executeQuery();
				if (resultSet.next()) {														//判断是否有这个用户
					if (resultSet.getString("密码").equals(passwordField.getText())) {		//判断密码是否正确
						System.out.println("登录成功");
						if (resultSet.getString("是否管理员").equals("是")) {
							MainWindowOfSuperUser mainWindowOfSuperUser = new MainWindowOfSuperUser();
							mainWindowOfSuperUser.setVisible(true);
						} else {
							MainWindow mainWindow = new MainWindow();							//跳转到主窗口
							mainWindow.setVisible(true);
						}
						frame.dispose();													//销毁本窗口
					}else {
						warningLabel.setText("密码错误！");
						System.out.println("密码错误");
					}
				}else {
					warningLabel.setText("用户名不存在！");
					System.out.println("用户名错误");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else {
			warningLabel.setText("用户名或密码不能为空！");
		}
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 241);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		
		JLabel titleLabel = new JLabel("欢迎使用人事管理系统");
		titleLabel.setBounds(89, 30, 175, 20);
		titleLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		usernameField = new JTextField();
		usernameField.setBounds(134, 79, 130, 26);
		usernameField.setColumns(10);
		contentPane.setLayout(null);
		contentPane.add(titleLabel);
		contentPane.add(usernameField);
		
		JLabel usernameLabel = new JLabel("用户名");
		usernameLabel.setBounds(72, 84, 50, 16);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("密码");
		passwordLabel.setBounds(82, 117, 37, 16);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					checkUser(frame);
				}
			}
		});
		passwordField.setBounds(134, 112, 130, 26);
		contentPane.add(passwordField);
		
		loginBtn = new JButton("登录");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkUser(frame);
			}
		});
		
		loginBtn.setBounds(167, 158, 95, 29);
		contentPane.add(loginBtn);
		
		exitBtn = new JButton("退出");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(77, 158, 95, 29);
		contentPane.add(exitBtn);
		
		warningLabel = new JLabel("");
		warningLabel.setHorizontalAlignment(SwingConstants.CENTER);
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(89, 56, 161, 16);
		contentPane.add(warningLabel);
	}
}
