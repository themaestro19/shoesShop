package V;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;
import M.ProductDB;
import M.ProductManager;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SearchProduct extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_search;
	private JTable table;
	private JButton button_search;
	private JButton button_select;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					SearchProduct frame = new SearchProduct();
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
	public SearchProduct()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 779, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(39, 61, 648, 365);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		textField_search = new JTextField();
		textField_search.setColumns(10);
		textField_search.setBounds(39, 25, 174, 26);
		panel.add(textField_search);
		
		button_search = new JButton("Search");
		button_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		button_search.setBounds(240, 27, 85, 21);
		panel.add(button_search);
		
		button_select = new JButton("Select");
		button_select.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRowCount() == 0)
				{
					JOptionPane.showMessageDialog(SearchProduct.this, "Please Select 1 Row");
					return;
				}

				if (xSelectProduct != null)
				{
					if (list != null)
					{
						xSelectProduct.select(list.get(table.getSelectedRow()));
						setVisible(false);
					}

				}
			}
		});
		button_select.setBounds(361, 27, 85, 21);
		panel.add(button_select);
	}
	SelectProduct xSelectProduct;
	public void setSelectProduct(SelectProduct x)
	{
		xSelectProduct =x;
	}
	
	ArrayList<ProductDB> list;
	public void search()
	{
		list = ProductManager.searchProduct(textField_search.getText());
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("product_id");
		model.addColumn("product_name");
		model.addColumn("product_per_unit");
		model.addColumn("product_description");
		
		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description });
		}

		table.setModel(model);
	}
}
interface SelectProduct
{
	public void select(ProductDB x);
}
