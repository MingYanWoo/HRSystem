package View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class MainWindowOfSuperUser extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainWindowOfSuperUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 521, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton transferBtn = new JButton("ת������");
		transferBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("ת��");
				deleteManager.setVisible(true);
			}
		});
		transferBtn.setBounds(201, 72, 117, 61);
		contentPane.add(transferBtn);
		
		JButton staffBtn = new JButton("Ա������");
		staffBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffManager staffManager = new StaffManager();
				staffManager.setVisible(true);
			}
		});
		staffBtn.setBounds(56, 72, 117, 61);
		contentPane.add(staffBtn);
		
		JButton dismissBtn = new JButton("���˹���");
		dismissBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("����");
				deleteManager.setVisible(true);
			}
		});
		dismissBtn.setBounds(340, 72, 117, 61);
		contentPane.add(dismissBtn);
		
		JButton userBtn = new JButton("�û�����");
		userBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManager userManager = new UserManager();
				userManager.setVisible(true);
			}
		});
		userBtn.setBounds(56, 145, 117, 61);
		contentPane.add(userBtn);
		
		JButton retireBtn = new JButton("���ݹ���");
		retireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("����");
				deleteManager.setVisible(true);
			}
		});
		retireBtn.setBounds(201, 145, 117, 61);
		contentPane.add(retireBtn);
		
		JButton resignBtn = new JButton("��ְ����");
		resignBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteManager deleteManager = new DeleteManager("��ְ");
				deleteManager.setVisible(true);
			}
		});
		resignBtn.setBounds(340, 145, 117, 61);
		contentPane.add(resignBtn);
		
		JButton inquireBtn = new JButton("������ѯ");
		inquireBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConditionInquire conditionInquire = new ConditionInquire();
				conditionInquire.setVisible(true);
			}
		});
		inquireBtn.setBounds(56, 218, 117, 61);
		contentPane.add(inquireBtn);
		
		JButton statisticsBtn = new JButton("����ͳ��");
		statisticsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statistics statistics = new Statistics();
				statistics.setVisible(true);
			}
		});
		statisticsBtn.setBounds(201, 218, 117, 61);
		contentPane.add(statisticsBtn);
		
		JButton exitBtn = new JButton("�˳�ϵͳ");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(340, 218, 117, 61);
		contentPane.add(exitBtn);
		
		JLabel lblNewLabel = new JLabel("���¹���ϵͳ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(201, 18, 117, 35);
		contentPane.add(lblNewLabel);
	}

}
