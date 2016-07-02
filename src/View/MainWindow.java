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
				StaffManager StaffManager = new StaffManager();
				StaffManager.setVisible(true);
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
		inquireBtn.setBounds(56, 145, 117, 61);
		contentPane.add(inquireBtn);
		
		JButton statisticsBtn = new JButton("����ͳ��");
		statisticsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statistics statistics = new Statistics();
				statistics.setVisible(true);
			}
		});
		statisticsBtn.setBounds(131, 218, 117, 61);
		contentPane.add(statisticsBtn);
		
		JButton exitBtn = new JButton("�˳�ϵͳ");
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exitBtn.setBounds(272, 218, 117, 61);
		contentPane.add(exitBtn);
		
		JLabel lblNewLabel = new JLabel("���¹���ϵͳ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		lblNewLabel.setBounds(201, 18, 117, 35);
		contentPane.add(lblNewLabel);
	}

}
