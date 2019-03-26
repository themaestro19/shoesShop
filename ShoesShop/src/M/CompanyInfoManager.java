package M;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Common.GlobalData;

public class CompanyInfoManager
{
	public static CompanyInfoDB getCompanyInfo() //����ͧ��������������Т����Ţͧ����ѷ�յ��������������
	{
		try
		{
			// create our mysql database connection
			String myDriver = "org.gjt.mm.mysql.Driver";
			String myUrl = "jdbc:mysql://" + GlobalData.DATABASE_LOCATION + ":" + GlobalData.DATABASE_PORT + "/"
					+ GlobalData.DATABASE_DATABASE_NAME;
			Class.forName(myDriver);
			Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME,
					GlobalData.DATABASE_PASSWORD);

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of
			// using "*"
			String query = "SELECT * FROM company_info";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next())
			{
				CompanyInfoDB cc = new CompanyInfoDB();
				cc.id = rs.getInt("id");
				cc.company_name = rs.getString("company_name");
				cc.address = rs.getString("address");
				cc.phone = rs.getString("phone");
				cc.email = rs.getString("email");
				
				return cc;

			}
			st.close();
		} catch (Exception e)
		{
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;  //����ѹ������ѡ��ǡ���������ҧ����ѹ������ҧ�͡
	
	}
	public static void editCompanyInfo(CompanyInfoDB x)
	{		
		try
	    {
	      // create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      String myUrl = "jdbc:mysql://"+GlobalData.DATABASE_LOCATION+":"+GlobalData.DATABASE_PORT+"/"+GlobalData.DATABASE_DATABASE_NAME;
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, GlobalData.DATABASE_USERNAME, GlobalData.DATABASE_PASSWORD);
	      
	      // our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = "UPDATE company_info SET "
	      		+ "company_name = '"+x.company_name+"' ,address = '" +x.address+"',phone = '"+x.phone+"' ,"
	      				+ "email = '"+x.email+"'WHERE id = "+x.id+" ";

	      // create the java statement
	      System.out.println(query); //��������������ͧ���鹤��������ͷ��ͺ��
	      Statement st = conn.createStatement();
	      	st.executeUpdate(query);
	      st.close();
	      
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	}
	

}
