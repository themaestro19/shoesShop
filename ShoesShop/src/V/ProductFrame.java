package V;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;

import M.CustomerDB;
import M.CustomerManager;
import M.ProductDB;
import M.ProductManager;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductFrame extends JFrame
{

	private JPanel contentPane;
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_price_per_unit;
	private JTextField textField_description;
	private ImagePanel imagePanel;

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

					ProductFrame frame = new ProductFrame();
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
	public ProductFrame()
	{
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 766, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setBounds(10, 0, 681, 453);
		contentPane.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 10, 413, 433);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRowCount() < 1) return;
				int index = table.getSelectedRow();
				int product_id = Integer.parseInt(table.getValueAt(index, 0).toString());
				String product_name = table.getValueAt(index, 1).toString();
				String price_per_unit = table.getValueAt(index, 2).toString();
				String product_description = table.getValueAt(index, 3).toString();
				BufferedImage img = list.get(index).product_image;
				if (img != null)
				{
					imagePanel.setImage(img);
				} 
				
				textField_id.setText("" + product_id);
				textField_name.setText("" + product_name);
				textField_price_per_unit.setText("" + price_per_unit);
				textField_description.setText("" + product_description);
			}
		});
		scrollPane.setViewportView(table);

		JLabel lblProductId = new JLabel("product id");
		lblProductId.setBounds(446, 12, 56, 13);
		panel.add(lblProductId);

		textField_id = new JTextField();
		textField_id.setEditable(false);
		textField_id.setColumns(10);
		textField_id.setBackground(Color.YELLOW);
		textField_id.setBounds(512, 10, 96, 19);
		panel.add(textField_id);

		JLabel lblProductName = new JLabel("product name");
		lblProductName.setBounds(446, 49, 56, 13);
		panel.add(lblProductName);

		textField_name = new JTextField();
		textField_name.setColumns(10);
		textField_name.setBounds(512, 46, 96, 19);
		panel.add(textField_name);

		JLabel lblPricePerUnit = new JLabel("price per unit");
		lblPricePerUnit.setBounds(446, 87, 45, 13);
		panel.add(lblPricePerUnit);

		textField_price_per_unit = new JTextField();
		textField_price_per_unit.setColumns(10);
		textField_price_per_unit.setBounds(512, 75, 96, 19);
		panel.add(textField_price_per_unit);

		JLabel lblProductDes = new JLabel("product des");
		lblProductDes.setBounds(446, 122, 45, 13);
		panel.add(lblProductDes);

		textField_description = new JTextField();
		textField_description.setColumns(10);
		textField_description.setBounds(512, 119, 96, 19);
		panel.add(textField_description);

		JButton button_save = new JButton("SAVE NEW");
		button_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(!textField_price_per_unit.getText().trim().matches("[-+]?\\d*\\.?\\d+"))
				{
					JOptionPane.showMessageDialog(ProductFrame.this, "Number only Please!!");
					textField_price_per_unit.requestFocus();
					textField_price_per_unit.selectAll();
				}
				ProductDB x = new ProductDB();
				x.product_id =0;
				x.product_name = textField_name.getText().trim();
				x.product_description = textField_description.getText().trim();
				x.price_per_unit = Double.parseDouble(textField_price_per_unit.getText().trim());
				x.product_image = (BufferedImage) imagePanel.getImage();
				
				ProductManager.saveProduct(x);
				load();
				textField_id.setText("");
				textField_name.setText("");
				textField_description.setText("");
				textField_price_per_unit.setText("");
				
				JOptionPane.showMessageDialog(ProductFrame.this,"Finish!!");
			}
		});
		button_save.setBounds(446, 340, 85, 21);
		panel.add(button_save);

		JButton button_edit = new JButton("EDIT");
		button_edit.setBounds(446, 371, 85, 21);
		panel.add(button_edit);

		JButton button_delete = new JButton("DELETE");
		button_delete.setBounds(446, 402, 85, 21);
		panel.add(button_delete);

		imagePanel = new ImagePanel((Image) null);
		imagePanel.setBounds(506, 164, 175, 164);
		panel.add(imagePanel);

		btnBrowseImg = new JButton("Browse Img");
		btnBrowseImg.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new OpenFileFilter("jpeg", "Photo in JPEG format"));
				fc.addChoosableFileFilter(new OpenFileFilter("jpg", "Photo in JPEG format"));
				fc.addChoosableFileFilter(new OpenFileFilter("png", "PNG image"));
				fc.addChoosableFileFilter(new OpenFileFilter("svg", "Scalable Vector Graphic"));
				fc.setAcceptAllFileFilterUsed(false);
				int returnVal = fc.showOpenDialog(ProductFrame.this);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					File f = fc.getSelectedFile();
					try
					{
						BufferedImage bimg = ImageIO.read(f);
						imagePanel.setImage(bimg);
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}
				}

			}
		});
		btnBrowseImg.setBounds(423, 174, 79, 46);
		panel.add(btnBrowseImg);
		
		load();
	}
	ArrayList<ProductDB> list;
	private JTable table;
	private JButton btnBrowseImg;
	public void load()
	{
		list = ProductManager.getAllProduct();
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("xproduct_id");
		model.addColumn("xproduct_name");
		model.addColumn("xproduct_per_unit");
		model.addColumn("xproduct_description");
		for (ProductDB c : list)
		{
			model.addRow(new Object[]
			{ c.product_id, c.product_name, c.price_per_unit, c.product_description });
		}

		table.setModel(model);
	}
}

	

class OpenFileFilter extends FileFilter
{

	String description = "";
	String fileExt = "";

	public OpenFileFilter(String extension)
	{
		fileExt = extension;
	}

	public OpenFileFilter(String extension, String typeDescription)
	{
		fileExt = extension;
		this.description = typeDescription;
	}

	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		return (f.getName().toLowerCase().endsWith(fileExt));
	}

	@Override
	public String getDescription()
	{
		return description;
	}
}
