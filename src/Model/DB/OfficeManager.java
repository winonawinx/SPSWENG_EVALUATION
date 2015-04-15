package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Office;
import Model.Question;
import Model.Service;

public class OfficeManager
{

	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Office> offices;
	private ServiceManager sm;
	
private static OfficeManager oM = null;
	
	public static synchronized OfficeManager getInstance() 
	{
        if (oM == null)
        {
            oM = new OfficeManager();
        }
 
        return oM;
    }
	
	public OfficeManager()
	{
		connect = DBConnection.getInstance();
		offices = new ArrayList<Office>();
		sm = new ServiceManager();
	}
	
	
	public void getServices(Office office) 
	{
		try {
			String sql = "SELECT * "
					+ "FROM services "
					+ "WHERE isArchived = 0 AND officeId = " + office.getID();
			statement = connect.getConnection().prepareStatement(sql);
			
			rs = statement.executeQuery("SELECT * "
											+ "FROM services "
											+ "WHERE isArchived = 0 AND officeId = " + office.getID());
			
			while(rs.next()){
				Service service = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				office.addService(service);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getServices");
		}
	}
	
	public Office getData(int ID)
	{
		try
		{
			Office o;
			
			String query = "SELECT * FROM Offices WHERE officeID = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, ID);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				o = new Office(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				iterator = sm.getAllData(ID);
				while(iterator.hasNext())
				{
					services.add((Service)iterator.next());
				}
				o.setServices(services);
				return o;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Office getOffice(String Name)
	{
		try
		{
			Office o;
			
			String query = "SELECT * FROM Offices WHERE officeName = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, Name);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				o = new Office(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				iterator = sm.getAllData(o.getID());
				while(iterator.hasNext())
				{
					services.add((Service)iterator.next());
				}
				o.setServices(services);
				return o;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Office getDataByName(String ID)
	{
		try
		{
			Office o;
			
			
			String query = "SELECT * FROM Offices WHERE officeName = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, ID);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				o = new Office(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				iterator = sm.getAllData(ID);
				while(iterator.hasNext())
				{
					services.add((Service)iterator.next());
				}
				o.setServices(services);
				return o;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<Office> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM Offices where isArchived = '0'";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			offices = new ArrayList<Office>();
			while(rs.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				Office o = new Office(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				iterator = sm.getAllData(o.getID());
				while(iterator.hasNext())
				{
					services.add((Service)iterator.next());
				}
				o.setServices(services);
				offices.add(o);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return offices.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		Office o = (Office) obj;
		
		String query = "UPDATE offices SET officeID = ?, officename = ?, isArchived = ? WHERE officeName = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, o.getID());
			statement.setString(2, o.getName());
			statement.setBoolean(3, o.getIsArchived());
			statement.setString(4, o.getName());
			statement.execute();
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
			Office o = (Office) obj;
			String query = "INSERT INTO offices values(?,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, o.getID());
			statement.setString(2, o.getName());
			statement.setBoolean(3, o.getIsArchived());
			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}
	
	public int addOffice(String officeName) 
	{
		int id = 0;
		try
		{
			String query = "INSERT INTO offices (officeName) values(?)";
			statement = connect.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, officeName);
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			
			if(rs.next()) {
				id = rs.getInt(1);
			}
			
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
		connect.close();
		return id;
	}
	
	public void setOfficeHead(int userId, int officeId) 
	{
		try
		{
			String query = "INSERT INTO officeheads (userId, officeId) values(?, ?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, userId);
			statement.setInt(2, officeId);
			statement.executeUpdate();
			
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
		connect.close();
	}

	public ArrayList<Office> getList()
	{
		return offices;
	}
	
	
	public void resetList()
	{
		offices.clear();
	}
	
	public int getIDbyHead(int headID)
	{
		try
		{
			Office o;
			
			String query = "SELECT officeID FROM Offices WHERE officehead = ?;";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, headID);
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
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		
		connect.close();
		return 0;
	}
	
	public int getOfficeID(String office)
	{
		try
		{
		
			Office o;
			
			String query = "SELECT officeID FROM Offices WHERE officeName = ?;";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, office);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				return rs.getInt(1) + 1;
			}
			
			else 
				return 0;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		
		connect.close();
		return 0;
	}
	
	public Iterator<Office> getOfficesByHead(int userID)
	{
		try 
		{
			String query = "SELECT * FROM Offices WHERE officeID IN (SELECT officeID FROM officeheads WHERE userID = ? AND isArchived = '0') and isArchived = '0'";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, userID);
			rs = statement.executeQuery();
			
			offices = new ArrayList<Office>();
			while(rs.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				Office o = new Office(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				iterator = sm.getAllData(o.getID());
				while(iterator.hasNext())
				{
					services.add((Service)iterator.next());
				}
				o.setServices(services);
				offices.add(o);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return offices.iterator();
	}
	
}
