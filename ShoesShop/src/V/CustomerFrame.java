package V;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerFrame extends JFrame
{

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_surname;
	private JTextField textField_phone;
	ArrayList<CustomerDB> list;

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
					UIManager.setLookAndFeel(
				            UIManager.getSystemLookAndFeelClassName());
					
					CustomerFrame frame = new CustomerFrame();
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
	public CustomerFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 623, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 413, 433);
		contentPane.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if (table.getSelectedRowCount() < 1)
				{
					return;
				}
				int index = table.getSelectedRowCount();
				int id = 			Integer.parseInt(table.getValueAt(index, 0).toString());
				String name = 		table.getValueAt(index, 1).toString();
				String surname = 	table.getValueAt(index, 2).toString();
				String phone =		table.getValueAt(index, 3).toString();
				
				textField_id.setText(""+id);
				textField_name.setText(""+name);
				textField_surname.setText(""+surname);
				textField_phone.setText(phone);
			}
		});

		scrollPane.setViewportView(table);
		table.setBackground(Color.white);

		JLabel lblId = new JLabel("id");
		lblId.setBounds(446, 12, 45, 13);
		contentPane.add(lblId);

		textField_id = new JTextField();
		textField_id.setBackground(Color.YELLOW);
		textField_id.setEditable(false);
		textField_id.setBounds(484, 9, 96, 19);
		contentPane.add(textField_id);
		textField_id.setColumns(10);

		JLabel lblName = new JLabel("name");
		lblName.setBounds(446, 49, 45, 13);
		contentPane.add(lblName);

		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(484, 46, 96, 19);
		contentPane.add(textField_name);

		JLabel lblSurname = new JLabel("surname");
		lblSurname.setBounds(446, 87, 45, 13);
		contentPane.add(lblSurname);

		textField_surname = new JTextField();
		textField_surname.setColumns(10);
		textField_surname.setBounds(484, 84, 96, 19);
		contentPane.add(textField_surname);

		JLabel lblPhone = new JLabel("phone");
		lblPhone.setBounds(446, 122, 45, 13);
		contentPane.add(lblPhone);

		textField_phone = new JTextField();
		textField_phone.setColumns(10);
		textField_phone.setBounds(484, 119, 96, 19);
		contentPane.add(textField_phone);

		JButton btnSaveNew = new JButton("SAVE NEW");
		btnSaveNew.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				CustomerDB x = new CustomerDB(0, textField_name.getText().trim(), textField_surname.getText().trim(),
						textField_phone.getText().trim());
				CustomerManager.saveNewCustomer(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				
				JOptionPane.showMessageDialog(CustomerFrame.this,"Finish!!");
			}
		});
		btnSaveNew.setBounds(446, 173, 85, 21);
		contentPane.add(btnSaveNew);

		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CustomerDB x = new CustomerDB(Integer.parseInt(textField_id.getText()), 
						textField_name.getText().trim(),
						textField_surname.getText().trim(),
						textField_phone.getText().trim());
				CustomerManager.editNewCustomer(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				
				JOptionPane.showMessageDialog(CustomerFrame.this,"Finish!!");
			}
		});
		btnEdit.setBounds(446, 217, 85, 21);
		contentPane.add(btnEdit);

		JButton btnDelete = new JButton("DELETE");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(CustomerFrame.this, "Do you want to delete?","DELETE ?",JOptionPane.OK_CANCEL_OPTION))
				{
				CustomerDB x = new CustomerDB(Integer.parseInt(textField_id.getText()), 
						textField_name.getText().trim(),
						textField_surname.getText().trim(),
						textField_phone.getText().trim());
				CustomerManager.deleteNewCustomer(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_surname.setText("");
				textField_phone.setText("");
				
				JOptionPane.showMessageDialog(CustomerFrame.this,"Finish!!");
				}
			}
		});
		btnDelete.setBounds(446, 268, 85, 21);
		contentPane.add(btnDelete);

		load();
	}

	public void load()
	{
		list = CustomerManager.getAllCustomer();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("id");
		model.addColumn("name");
		model.addColumn("surname");
		model.addColumn("phone");
		for (CustomerDB c : list)
		{
			model.addRow(new Object[]
			{ c.id, c.name, c.surname, c.phone });
		}

		table.setModel(model);
	}
}
