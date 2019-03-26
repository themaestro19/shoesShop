package V;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;
import M.ProductDB;
import M.ProductManager;
import M.UserDB;
import M.UserManager;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_username;
	private JTextField textField_password;
	private JTable table;

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
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					UserFrame frame = new UserFrame();
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
	public UserFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 725, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 429, 433);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(table.getSelectedRowCount()<1)
				{
					return;
				}
				int index = table.getSelectedRow();
				int id = Integer.parseInt(table.getValueAt(index, 0).toString());
				String username = table.getValueAt(index, 1).toString();
				String password = ""+table.getValueAt(index, 2);
				String usertype = ""+table.getValueAt(index, 3);
				
				textField_id.setText(""+id);
				textField_username.setText(""+username);
				textField_password.setText(""+password);
				comboBox.setSelectedItem(usertype);
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel label = new JLabel("id");
		label.setBounds(456, 13, 45, 13);
		contentPane.add(label);
		
		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setColumns(10);
		textField_id.setBackground(Color.YELLOW);
		textField_id.setBounds(494, 10, 96, 19);
		contentPane.add(textField_id);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(456, 50, 45, 13);
		contentPane.add(lblUsername);
		
		textField_username = new JTextField();
		textField_username.setColumns(10);
		textField_username.setBounds(494, 47, 96, 19);
		contentPane.add(textField_username);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(456, 88, 45, 13);
		contentPane.add(lblPassword);
		
		textField_password = new JTextField();
		textField_password.setColumns(10);
		textField_password.setBounds(494, 85, 96, 19);
		contentPane.add(textField_password);
		
		JLabel lblUsertype = new JLabel("usertype");
		lblUsertype.setBounds(456, 123, 45, 13);
		contentPane.add(lblUsertype);
		
		JButton button_save = new JButton("SAVE NEW");
		button_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserDB x = new UserDB(0, textField_username.getText().trim(), textField_password.getText().trim(),
						comboBox.getSelectedItem().toString().trim());
				UserManager.saveNewUser(x);
				load();
				textField_id.setText("");
				textField_username.setText("");
				textField_password.setText("");
				
				JOptionPane.showMessageDialog(UserFrame.this,"Finish!!");
			}
		});
		button_save.setBounds(456, 174, 85, 21);
		contentPane.add(button_save);
		
		JButton button_edit = new JButton("EDIT");
		button_edit.setBounds(456, 218, 85, 21);
		contentPane.add(button_edit);
		
		JButton button_delete = new JButton("DELETE");
		button_delete.setBounds(456, 269, 85, 21);
		contentPane.add(button_delete);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"admin", "user"}));
		comboBox.setBounds(504, 119, 86, 21);
		contentPane.add(comboBox);
		
		load();
	}
	ArrayList<UserDB> list;
	private JComboBox<String> comboBox;
	public void load()
	{
		list = UserManager.getAllUser();
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("id");
		model.addColumn("username");
		model.addColumn("password");
		model.addColumn("usertype");
		
		for (UserDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.username, c.password, c.usertype });
		}

		table.setModel(model);
	}
}
