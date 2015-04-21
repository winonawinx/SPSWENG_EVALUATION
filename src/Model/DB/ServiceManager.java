package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Service;

public class ServiceManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Service> services;
	
	private static ServiceManager serviceManager = null;
	
	public static synchronized ServiceManager getInstance() 
	{
        if (serviceManager == null)
            serviceManager = new ServiceManager();
        return serviceManager;
    }
	
	public ServiceManager()
	{
		dbConnection = DBConnection.getInstance();
		services = new ArrayList<Service>();
	}
	
	public void addService(int officeId, String service) 
	{
		try
		{
			String query = "INSERT INTO services (officeId, serviceName) VALUES(?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			preparedStatement.setString(2, service);
			preparedStatement.execute();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Unable to addService()");
			e.printStackTrace();
		}
	}
	
	
	public Service getData(String s)
	{
		try
		{
			Service service;
			String query = "SELECT * FROM services WHERE serviceName = ?";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, s);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				dbConnection.close();
				return service;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Service");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<Service> getAllData(int officeID) 
	{	
		ArrayList<Service> serviceList = new ArrayList<Service>();
		try 
		{
			String query = "SELECT * FROM services where officeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeID);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Service service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				serviceList.add(service);			
			}
			dbConnection.close();	
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		return serviceList.iterator();
	}
	
	public Iterator<Service> getAllData(String officeName) 
	{
		ArrayList<Service> serviceList = new ArrayList<Service>();
		try 
		{
			String query = "SELECT * FROM services where officeId = (SELECT officeId FROM offices WHERE officeName = ?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, officeName);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Service service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				serviceList.add(service);			
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		return serviceList.iterator();
	}
	
	public Iterator<Service> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM services GROUP BY serviceName";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			services = new ArrayList<Service>();
			while(resultSet.next())
			{
				Service service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				services.add(service);			
			}
			dbConnection.close();		
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		return services.iterator();
	}
	
		
	public boolean updateData(Object obj) 
	{
		try 
		{
			Service service = (Service) obj;
			String query = "UPDATE services SET serviceId = ?, serviceName = ?, officeId = ?, isArchived = ? WHERE serviceId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, service.getID());
			preparedStatement.setString(2, service.getName());
			preparedStatement.setInt(3, service.getOfficeID());
			preparedStatement.setBoolean(4, service.getIsArchived());
			preparedStatement.execute();
			dbConnection.close();
			return true;
			
		}
		catch (SQLException e)
		{
			System.out.println("Update Error");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertData(Object obj)
	{
		try
		{
			Service service = (Service) obj;
			String query = "INSERT INTO services VALUES(?,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, service.getID());
			preparedStatement.setString(2, service.getName());
			preparedStatement.setInt(3, service.getOfficeID());
			preparedStatement.setBoolean(4, service.getIsArchived());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new service");
			e.printStackTrace();
		}
		return false;
	}

	public Iterator<Service> getList()
	{
		return services.iterator();
	}
	
	
	public void resetList()
	{
		services.clear();
	}
	
	public int getServiceId(String serviceName) 
	{
		try
		{
			String query = "SELECT serviceId FROM services WHERE isArchived = 0 AND serviceName = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, serviceName);
			resultSet = preparedStatement.executeQuery(query);
			if(resultSet.next())
			{
				int id = resultSet.getInt(1);
				dbConnection.close();
				return id;
			}
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.out.println("error in getServiceId");
		}
		return 0;
	}

	public Iterator<Service> getOfficeServices(int officeId)
	{
		try 
		{
			String query = "SELECT * FROM services WHERE OfficeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			resultSet = preparedStatement.executeQuery();
			services = new ArrayList<Service>();
			while(resultSet.next())
			{
				Service service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				services.add(service);			
			}
			dbConnection.close();	
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all services from DB");
			e.printStackTrace();
		}
		return services.iterator();
	}
	
	
	public int getServiceID(String service, int officeId)
	{
		try
		{
			int id = -1;
			String query = "SELECT serviceId FROM services WHERE serviceName = ? AND officeId = ?;";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, service);
			preparedStatement.setInt(2, officeId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				id = resultSet.getInt(1);
				dbConnection.close();
				return id;
			}
			else 
			{
				dbConnection.close();
				return -1;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Service");
			e.printStackTrace();
		}
		return -1;
	}
	
	public Service getService(int serviceId)
	{
		try
		{
			Service service;
			String query = "SELECT * FROM services WHERE serviceId = ?";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, serviceId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				dbConnection.close();
				return service;
			}
			else 
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Service");
			e.printStackTrace();
		}
		return null;
	}
}