package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.Statement;

import DBConnection.DBConnection;
import Model.ControlNumber;
import Model.ControlNumberFactory;
import Model.Question;

public class ControlNumManager {

	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<ControlNumber> controlnumbers;
	
	private static ControlNumManager cnM = null;
	
	public static synchronized ControlNumManager getInstance() 
	{
        if (cnM == null)
        {
            cnM = new ControlNumManager();
        }
 
        return cnM;
    }
	
	public ControlNumManager()
	{
		connect = DBConnection.getInstance();
		controlnumbers = new ArrayList<ControlNumber>();
	}
	
	public ControlNumber getData(String controlnumber)
	{
		try
		{
			ControlNumber c;
			
			String query = "SELECT * FROM controlnumbers WHERE controlnumber = ? and status = '0';";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, controlnumber);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				c =  new ControlNumber(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getTimestamp(5), rs.getBoolean(6));
				return c;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<ControlNumber> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM ControlNumbers WHERE status = '0' AND expirationTime >= time(now());";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			controlnumbers.clear();
			while(rs.next())
			{
				ControlNumber c = new ControlNumber(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(5), rs.getBoolean(6));
				controlnumbers.add(c);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all controlnumbers from DB");
			e.printStackTrace();
		}
		connect.close();
		return controlnumbers.iterator();
	}
	
	public Boolean controlNumberIsValid(String controlnumber)
	{
		try 
		{
			String query = "SELECT * FROM ControlNumbers WHERE status = '0' AND controlNumber = ? AND expirationTime >= time(now());";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, controlnumber);
			rs = statement.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in controlNumberIsValid()");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}
	
	public Boolean controlNumIsExpired(String controlnumber)
	{
		try 
		{
			String query = "SELECT * FROM ControlNumbers WHERE status = '0' AND controlNumber = ? AND expirationTime < time(now());";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, controlnumber);
			rs = statement.executeQuery();
			
			if(rs.next())
			{
				return true;
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in controlNumberIsValid()");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}
	
	public boolean updateData(Object obj) 
	{
		ControlNumber c = (ControlNumber) obj;
		
		String query = "UPDATE controlnumbers SET controlnumber = ?, formID = ?, expirationTime = ?, status = ? WHERE controlNumberid = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, c.getControlNumber());
			statement.setInt(2, c.getForm());
			statement.setTimestamp(3, c.getExpirationTime());
			statement.setBoolean(4, c.getStatus());
			statement.setInt(5, c.getId());
			statement.execute();
			//notifyObserver();
			connect.close();
			return true;
			
		} catch (SQLException a) {
	
			System.out.println("Update Error");
			a.printStackTrace();
		}
		connect.close();
		return false;
	}
	
	public boolean insertData(Object obj) {
		try
		{
			ControlNumber c = (ControlNumber) obj;
			String query = "INSERT INTO controlnumbers values(?,?,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, c.getControlNumber());
			statement.setInt(2, c.getForm());
			statement.setTimestamp(3, c.getExpirationTime());
			statement.setBoolean(4, c.getStatus());

			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new control number");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}

	public Iterator getList()
	{
		return controlnumbers.iterator();
	}
	
	
	public void resetList()
	{
		controlnumbers.clear();
	}
	
	public ControlNumber hasControlNum(String controlNum) {
		ControlNumber controlNumber = null;
		try 
		{
			String query = "SELECT * FROM controlnumbers WHERE controlNumber = '"+ controlNum +"'";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			controlnumbers.clear();
			if(rs.next())
			{
				controlNumber =  new ControlNumber(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getTimestamp(5), rs.getBoolean(6));
			}
				
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in hasControlNum");
			System.out.println(e);
		}
		connect.close();
		return controlNumber;
	}
	
	public String insertControlNum(int formId, int serviceId)
	{
		String controlNum = ControlNumberFactory.generateControlNum(serviceId);
		while(hasControlNum(controlNum) != null){
			controlNum = ControlNumberFactory.generateControlNum(serviceId);
		}
		Statement statement;

		try
		{
			PreparedStatement ps; 
			ps = connect.getConnection().prepareStatement("INSERT INTO controlnumbers (controlNumber,serviceId, formId,expirationTime, status) VALUES (?, ?, ?, ADDTIME(TIME(NOW()), '00:30:00'), '0')");
			ps.setString(1, controlNum);
			ps.setInt(2, serviceId);
			ps.setInt(3, formId);
			ps.executeUpdate();
		}
			catch (SQLException e)
		{
			System.out.println(e);
		}
		return controlNum;
	}

	public int getFormID(String controlnumber)
	{
		try
		{
			ControlNumber c;
			
			String query = "SELECT formID FROM controlnumbers WHERE controlnumber = ? and status = '0';";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, controlnumber);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				return rs.getInt(1);
			}
			
			else 
				return 0;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		
		connect.close();
		return 0;
	}
	
	public int getControlNumberID(String controlnumber)
	{
		try
		{
			ControlNumber c;
			
			String query = "SELECT controlNumberID FROM controlnumbers WHERE controlnumber = ? and status = '0';";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, controlnumber);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				return rs.getInt(1);
			}
			
			else 
				return 0;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		
		connect.close();
		return 0;
	}
}
