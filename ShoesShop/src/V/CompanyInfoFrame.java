package V;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CompanyInfoDB;
import M.CompanyInfoManager;
import M.CustomerDB;
import M.CustomerManager;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CompanyInfoFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_name;
	private JTextField textField_add;
	private JTextField textField_phone;
	private JTextField textField_email;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					CompanyInfoFrame frame = new CompanyInfoFrame();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CompanyInfoFrame()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 716, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_name = new JLabel("Company name");
		label_name.setBounds(57, 41, 120, 14);
		contentPane.add(label_name);
		
		JLabel label_add = new JLabel("Address");
		label_add.setBounds(57, 76, 120, 14);
		contentPane.add(label_add);
		
		JLabel label_phone = new JLabel("phone");
		label_phone.setBounds(57, 116, 120, 14);
		contentPane.add(label_phone);
		
		JLabel label_email = new JLabel("E-mail");
		label_email.setBounds(57, 153, 120, 14);
		contentPane.add(label_email);
		
		textField_name = new JTextField();
		textField_name.setBounds(199, 36, 339, 25);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		textField_add = new JTextField();
		textField_add.setColumns(10);
		textField_add.setBounds(199, 71, 339, 25);
		contentPane.add(textField_add);
		
		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(199, 111, 339, 25);
		contentPane.add(textField_phone);
		
		textField_email = new JTextField();
		textField_email.setColumns(10);
		textField_email.setBounds(199, 148, 339, 25);
		contentPane.add(textField_email);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				xCompanyInfoDB.company_name = textField_name.getText();
				xCompanyInfoDB.address = textField_add.getText();
				xCompanyInfoDB.phone = textField_phone.getText();
				xCompanyInfoDB.email = textField_email.getText();
				CompanyInfoManager.editCompanyInfo(xCompanyInfoDB);
			}
		});
		btnSave.setBounds(279, 206, 89, 23);
		contentPane.add(btnSave);
		
		load();
	}
	CompanyInfoDB xCompanyInfoDB;
	public void load()
	{
		xCompanyInfoDB = CompanyInfoManager.getCompanyInfo();
		textField_name.setText(xCompanyInfoDB.company_name);
		textField_add.setText(xCompanyInfoDB.address);
		textField_phone.setText(xCompanyInfoDB.phone);
		textField_email.setText(xCompanyInfoDB.email);
	}
}
