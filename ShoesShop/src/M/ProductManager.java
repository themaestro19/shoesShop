package M;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.mysql.jdbc.PreparedStatement;

import Common.GlobalData;

public class ProductManager
{
	public static ArrayList<ProductDB> getAllProduct()

	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();
		try
		{

			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME,
					GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM products";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				// product_id product_name product_per_unit product_description product_image

				int id = rs.getInt("product_id");
				String pName = rs.getString("product_name");
				double price = rs.getDouble("product_per_unit");
				String desc = rs.getString("product_description");
				byte[] img_byte = rs.getBytes("product_image");
				ByteArrayInputStream bais = new ByteArrayInputStream(img_byte);
				BufferedImage bufferedimg = ImageIO.read(bais);
				bais.close();
				ProductDB cc = new ProductDB(id, pName, price, desc, bufferedimg);

				list.add(cc);

			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		return list;
	}

	public static ArrayList<ProductDB> searchProduct(String s)

	{
		ArrayList<ProductDB> list = new ArrayList<ProductDB>();
		try
		{

			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME,
					GlobalData.DATABASE_PASSWORD);

			String query = "SELECT * FROM products WHERE product_name LIKE '"+s+"' OR product_description LIKE '"+s+"'  ";

			Statement st = conn.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next())
			{
				// product_id product_name product_per_unit product_description product_image

				int id = rs.getInt("product_id");
				String pName = rs.getString("product_name");
				double price = rs.getDouble("product_per_unit");
				String desc = rs.getString("product_description");
				byte[] img_byte = rs.getBytes("product_image");
				ByteArrayInputStream bais = new ByteArrayInputStream(img_byte);
				BufferedImage bufferedimg = ImageIO.read(bais);
				bais.close();
				ProductDB cc = new ProductDB(id, pName, price, desc, bufferedimg);

				list.add(cc);

			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}

		return list;
	}

	public static void saveProduct(ProductDB x)
	{
		try
		{

			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME,
					GlobalData.DATABASE_PASSWORD);

			String query = "INSERT INTO products VALUES(? , ? , ? , ? , ? )";
			PreparedStatement st = (PreparedStatement) conn.prepareStatement(query);
			st.setInt(1, 0);
			st.setString(2, x.product_name);
			st.setDouble(3, x.price_per_unit);
			st.setString(4, x.product_description);

			if (x.product_image != null)
			{
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				ImageIO.write(x.product_image, "png", outStream);
				byte[] buffer = outStream.toByteArray();
				st.setBytes(5, buffer);
				outStream.close();
			} else
			{
				byte[] buffer = new byte[0];
				st.setBytes(5, buffer);
			}

			st.executeUpdate();

			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}

}
