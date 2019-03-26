package V;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import M.CompanyInfoDB;
import M.CompanyInfoManager;
import M.CustomerDB;
import M.ProductDB;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InvoiceFrame extends JFrame
{

	private JPanel contentPane;
	private JPanel panel;
	private JTable table;
	CustomerDB xCustomerDB;
	private JLabel label_customer_info;
	private JButton btnSelectCustomer;
	private JButton btnSelectProduct;
	private JButton btnSave;
	private JButton btnPrint;

	ArrayList<InvoiceDetail> detailList;

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
					InvoiceFrame frame = new InvoiceFrame();
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
	public InvoiceFrame()
	{
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent arg0)
			{
				panel.setBounds(0, 60, getWidth() - 20, getHeight() - 100);
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();

		setBounds(0, 0, 1000, (int) height);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 60, getWidth() - 20, getHeight() - 100);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblInvoice = new JLabel("Invoice");
		lblInvoice.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblInvoice.setBounds(482, 29, 109, 95);
		panel.add(lblInvoice);

		JLabel label_company_info = new JLabel("New label");
		label_company_info.setBounds(35, 144, 617, 13);
		panel.add(label_company_info);

		label_customer_info = new JLabel("New label");
		label_customer_info.setBounds(180, 178, 482, 13);
		panel.add(label_customer_info);

		JLabel lblNewLabel = new JLabel("\u0E23\u0E32\u0E22\u0E25\u0E30\u0E40\u0E2D\u0E35\u0E22\u0E14");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 218, 83, 39);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(742, 652, 45, 13);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(742, 693, 45, 13);
		panel.add(lblNewLabel_2);

		JLabel label_date = new JLabel("New label");
		label_date.setBounds(884, 110, 45, 13);
		panel.add(label_date);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 255, 894, 360);
		panel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		btnSelectCustomer = new JButton("Select Customer");
		btnSelectCustomer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchCustomer cc = new SearchCustomer();
				cc.setSelectCustomer(new SelectCustomer()
				{

					@Override
					public void select(CustomerDB x)
					{

						xCustomerDB = x;
						String s = x.name + " " + x.surname + "( " + x.phone + " )(id " + x.id + ")";
						label_customer_info.setText(s);
					}
				});
				cc.setVisible(true);
			}
		});
		btnSelectCustomer.setBounds(10, 10, 143, 30);
		contentPane.add(btnSelectCustomer);

		btnSelectProduct = new JButton("Select Product");
		btnSelectProduct.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SearchProduct ss = new SearchProduct();
				ss.setSelectProduct(new SelectProduct()
				{

					@Override
					public void select(ProductDB x)
					{
						setDetail(x);
					}
				});
				ss.setVisible(true);
			}
		});
		btnSelectProduct.setBounds(163, 10, 148, 30);
		contentPane.add(btnSelectProduct);

		btnSave = new JButton("Save");
		btnSave.setBounds(342, 10, 103, 30);
		contentPane.add(btnSave);

		btnPrint = new JButton("Print");
		btnPrint.setBounds(485, 10, 112, 30);
		contentPane.add(btnPrint);

		CompanyInfoDB x = CompanyInfoManager.getCompanyInfo();
		String CompanyInfo = x.company_name + " ที่อยู่ " + x.address + "โทร .  " + x.phone + " email " + x.email;

		label_company_info.setText(CompanyInfo);
		label_date.setText(new SimpleDateFormat().format(Calendar.getInstance().getTime()).toString());

		JLabel label = new JLabel("\u0E44\u0E14\u0E49\u0E23\u0E31\u0E1A\u0E40\u0E07\u0E34\u0E19\u0E08\u0E32\u0E01");
		label.setBounds(43, 178, 75, 13);
		panel.add(label);

		detailList = new ArrayList<InvoiceDetail>();
	}

	public void setDetail(ProductDB xProduct)
	{
		InvoiceDetail x = new InvoiceDetail();
		x.no = detailList.size() + 1;
		x.price_per_unit = xProduct.price_per_unit;
		x.productName = xProduct.product_name;
		x.qty = 1;
		x.totalPrice = x.price_per_unit * x.qty;
		x.product = xProduct;

		detailList.add(x);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("no");
		model.addColumn("productName");
		model.addColumn("qty");
		model.addColumn("price_per_unit");
		model.addColumn("totalPrice");
		for (InvoiceDetail c : detailList)
		{
			model.addRow(new Object[]
			{ c.no, c.productName, c.qty, c.price_per_unit, c.totalPrice });
		}

		table.setModel(model);
	}
}

class InvoiceDetail
{
	public int no;
	public String productName;
	public int qty;
	public double price_per_unit;
	public double totalPrice;

	public ProductDB product;
}
