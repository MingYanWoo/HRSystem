package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton transferBtn = new JButton("转出管理");
		transferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("转出");
				deleteManager.setVisible(true);
			}
		});
		transferBtn.setBounds(201, 72, 117, 61);
		contentPane.add(transferBtn);
		
		JButton staffBtn = new JButton("员工管理");
		staffBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffManager StaffManager = new StaffManager();
				StaffManager.setVisible(true);
			}
		});
		staffBtn.setBounds(56, 72, 117, 61);
		contentPane.add(staffBtn);
		
		JButton dismissBtn = new JButton("辞退管理");
		dismissBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("辞退");
				deleteManager.setVisible(true);
			}
		});
		dismissBtn.setBounds(340, 72, 117, 61);
		contentPane.add(dismissBtn);
		
		JButton retireBtn = new JButton("退休管理");
		retireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("退休");
				deleteManager.setVisible(true);
			}
		});
		retireBtn.setBounds(201, 145, 117, 61);
		contentPane.add(retireBtn);
		
		JButton resignBtn = new JButton("辞职管理");
		resignBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("辞职");
				deleteManager.setVisible(true);
			}
		});
		resignBtn.setBounds(340, 145, 117, 61);
		contentPane.add(resignBtn);
		
		JButton inquireBtn = new JButton("条件查询");
		inquireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConditionInquire conditionInquire = new ConditionInquire();
				conditionInquire.setVisible(true);
			}
		});
		inquireBtn.setBounds(56, 145, 117, 61);
		contentPane.add(inquireBtn);
		
		JButton statisticsBtn = new JButton("条件统计");
		statisticsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statistics statistics = new Statistics();
				statistics.setVisible(true);
			}
		});
		statisticsBtn.setBounds(131, 218, 117, 61);
		contentPane.add(statisticsBtn);
		
		JButton exitBtn = new JButton("退出系统");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(272, 218, 117, 61);
		contentPane.add(exitBtn);
		
		JLabel lblNewLabel = new JLabel("人事管理系统");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(201, 18, 117, 35);
		contentPane.add(lblNewLabel);
	}

}
