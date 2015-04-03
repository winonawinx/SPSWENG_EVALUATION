package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Question;
import Model.Service;

public class ServiceManager
{
	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Service> services;
	
	private static ServiceManager sM = null;
	
	public static synchronized ServiceManager getInstance() 
	{
        if (sM == null)
        {
            sM = new ServiceManager();
        }
 
        return sM;
    }
	
	public ServiceManager()
	{
		connect = DBConnection.getInstance();
		services = new ArrayList<Service>();
	}
	
	public Service getData(String service)
	{
		try
		{
			Service s;
			
			String query = "SELECT * FROM Services WHERE serviceName = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, service);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				s = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				return s;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Service");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public ArrayList<Service> getAllData(int officeID) 
	{	
		ArrayList<Service> ss = new ArrayList<Service>();
		try 
		{
			String query = "SELECT * FROM Services where officeID = ?";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, officeID);
			rs = statement.executeQuery();
			
			
			while(rs.next())
			{
				Service s = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				ss.add(s);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		connect.close();
		return ss;
	}
	
	public ArrayList<Service> getAllData(String officeID) 
	{	
		ArrayList<Service> ss = new ArrayList<Service>();
		try 
		{
			String query = "SELECT * FROM Services where officeID = (SELECT officeID FROM offices WHERE officeName = ?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, officeID);
			rs = statement.executeQuery();
			
			
			while(rs.next())
			{
				Service s = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				ss.add(s);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		connect.close();
		return ss;
	}
	
	public ArrayList<Service> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM Services group by serviceName";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			services = new ArrayList<Service>();
			while(rs.next())
			{
				Service s = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				services.add(s);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		connect.close();
		return services;
	}
	
		
	public boolean updateData(Object obj) 
	{
		Service s = (Service) obj;
		
		String query = "UPDATE services SET serviceID = ?, servicename = ?, officeID = ?, isArchived = ? WHERE serviceID = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, s.getID());
			statement.setString(2, s.getName());
			statement.setInt(3, s.getOfficeID());
			statement.setBoolean(4, s.getIsArchived());
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
			Service s = (Service) obj;
			String query = "INSERT INTO services values(?,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, s.getID());
			statement.setString(2, s.getName());
			statement.setInt(3, s.getOfficeID());
			statement.setBoolean(4, s.getIsArchived());
			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new service");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}

	public ArrayList<Service> getList()
	{
		return services;
	}
	
	
	public void resetList()
	{
		services.clear();
	}
	
	public int getServiceId(String serviceName) 
	{
		try {
			String sql = "SELECT serviceId "
					+ "FROM services "
					+ "WHERE isArchived = 0 AND serviceName = '" + serviceName + "'";
			statement = connect.getConnection().prepareStatement(sql);
			rs = statement.executeQuery("SELECT serviceId "
											+ "FROM services "
											+ "WHERE isArchived = 0 AND serviceName = '" + serviceName + "'");
			if(rs.next()){
				return Integer.valueOf(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in getServiceId");
		}
		connect.close();
		return 0;
	}

	public Iterator<Service> getOfficeServices(int officeid)
	{
		try 
		{
			String query = "SELECT * FROM Services WHERE OfficeID = ?";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, officeid);
			rs = statement.executeQuery();
			
			services = new ArrayList<Service>();
			while(rs.next())
			{
				Service s = new Service(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBoolean(4));
				services.add(s);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		connect.close();
		return services.iterator();
	}
	
	
	public int getServiceID(String service, int officeID)
	{
		try
		{
			int id = -1;
			
			String query = "SELECT serviceid FROM services WHERE serviceName = ? AND officeID = ?;";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, service);
			statement.setInt(2, officeID);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				id = rs.getInt(1);
				return id;
			}
			
			else 
				return -1;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Service");
			e.printStackTrace();
		}
		
		connect.close();
		return -1;
	}
	
}