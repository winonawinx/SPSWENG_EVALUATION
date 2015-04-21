package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Office;
import Model.Service;

public class OfficeManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Office> offices;
	private ServiceManager serviceManager;

	private static OfficeManager officeManager = null;

	public static synchronized OfficeManager getInstance()
	{
		if (officeManager == null)
			officeManager = new OfficeManager();
		return officeManager;
	}

	public OfficeManager()
	{
		dbConnection = DBConnection.getInstance();
		offices = new ArrayList<Office>();
		serviceManager = new ServiceManager();
	}

	public void getServices(Office office)
	{
		try
		{
			String query = "SELECT * FROM services WHERE isArchived = 0 AND officeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, office.getID());
			resultSet = preparedStatement.executeQuery(query);
			while (resultSet.next())
			{
				Service service = new Service(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getBoolean(4));
				office.addService(service);
			}
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("error in getServices");
		}
	}
	
	public void editOffice(int officeId, String officeName)
	{
		try
		{
			String query = "UPDATE offices SET officeName = ? WHERE officeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, officeName);
			preparedStatement.setInt(2, officeId);
			preparedStatement.executeUpdate();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Unable to editOffice");
			e.printStackTrace();
		}
	}
	
	public void deleteOffice(int officeId)
	{
		try
		{
			String query = "UPDATE offices SET isArchived = '1' WHERE officeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			preparedStatement.executeUpdate();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Unable to deleteOffice");
			e.printStackTrace();
		}

	}

	public Office getData(int officeId)
	{
		try 
		{
			Office office;
			String query = "SELECT * FROM offices WHERE officeID = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				office = new Office(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				iterator = serviceManager.getAllData(officeId);
				while (iterator.hasNext())
				{
					services.add((Service) iterator.next());
				}
				office.setServices(services);
				dbConnection.close();
				return office;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		return null;
	}

	public Office getOffice(String officeName)
	{
		try
		{
			Office office;
			String query = "SELECT * FROM offices WHERE officeName = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, officeName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				office = new Office(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				iterator = serviceManager.getAllData(office.getID());
				while (iterator.hasNext())
				{
					services.add((Service) iterator.next());
				}
				office.setServices(services);
				dbConnection.close();
				return office;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		return null;
	}

	public Office getDataByName(String officeName)
	{
		try {
			Office office;
			String query = "SELECT * FROM offices WHERE officeName = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, officeName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				office = new Office(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				iterator = serviceManager.getAllData(officeName);
				while (iterator.hasNext())
				{
					services.add((Service) iterator.next());
				}
				office.setServices(services);
				dbConnection.close();
				return office;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		return null;
	}

	public Iterator<Office> getAllData()
	{
		try
		{
			String query = "SELECT * FROM offices where isArchived = '0'";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			offices = new ArrayList<Office>();
			while (resultSet.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				Office office = new Office(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getBoolean(3));
				iterator = serviceManager.getAllData(office.getID());
				while (iterator.hasNext())
				{
					services.add((Service) iterator.next());
				}
				office.setServices(services);
				offices.add(office);
			}
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return offices.iterator();
	}

	public boolean updateData(Object obj)
	{
		try
		{
			Office office = (Office) obj;
			String query = "UPDATE offices SET officeId = ?, officeName = ?, isArchived = ? WHERE officeName = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, office.getID());
			preparedStatement.setString(2, office.getName());
			preparedStatement.setBoolean(3, office.getIsArchived());
			preparedStatement.setString(4, office.getName());
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
			Office office = (Office) obj;
			String query = "INSERT INTO offices VALUES(?,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, office.getID());
			preparedStatement.setString(2, office.getName());
			preparedStatement.setBoolean(3, office.getIsArchived());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
		return false;
	}

	public int addOffice(String officeName)
	{
		int id = 0;
		try
		{
			String query = "INSERT INTO offices (officeName) VALUES(?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, officeName);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next())
				id = resultSet.getInt(1);
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
		return id;
	}

	public void setOfficeHead(int userId, int officeId)
	{
		try
		{
			String query = "INSERT INTO officeheads (userId, officeId) VALUES(?, ?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, officeId);
			preparedStatement.executeUpdate();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new office");
			e.printStackTrace();
		}
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
			String query = "SELECT officeId FROM offices WHERE officeHead = ?;";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, headID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				dbConnection.close();
				return resultSet.getInt(1);
			}
			else
			{
				dbConnection.close();
				return 0;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		return 0;
	}

	public int getOfficeID(String office)
	{
		try
		{
			String query = "SELECT officeId FROM offices WHERE officeName = ?;";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, office);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				dbConnection.close();
				return resultSet.getInt(1) + 1;
			}
			else
			{
				dbConnection.close();
				return 0;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Office");
			e.printStackTrace();
		}
		return 0;
	}

	public Iterator<Office> getOfficesByHead(int userID)
	{
		try {
			String query = "SELECT * FROM offices WHERE officeId IN (SELECT officeId FROM officeheads "
						+ "WHERE userId = ? AND isArchived = '0') and isArchived = '0'";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, userID);
			resultSet = preparedStatement.executeQuery();
			offices = new ArrayList<Office>();
			while (resultSet.next())
			{
				ArrayList<Service> services = new ArrayList<Service>();
				Iterator iterator = null;
				Office o = new Office(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getBoolean(3));
				iterator = serviceManager.getAllData(o.getID());
				while (iterator.hasNext())
				{
					services.add((Service) iterator.next());
				}
				o.setServices(services);
				offices.add(o);
			}
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return offices.iterator();
	}
}
