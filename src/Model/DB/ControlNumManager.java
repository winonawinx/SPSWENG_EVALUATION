package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.ControlNumber;
import Model.ControlNumberFactory;

public class ControlNumManager
{

	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<ControlNumber> controlNumbers;
	
	private static ControlNumManager controlNumManager = null;
	
	public static synchronized ControlNumManager getInstance() 
	{
        if (controlNumManager == null)
            controlNumManager = new ControlNumManager();
        return controlNumManager;
    }
	
	public ControlNumManager()
	{
		dbConnection = DBConnection.getInstance();
		controlNumbers = new ArrayList<ControlNumber>();
	}
	
	public ControlNumber getData(String controlNum)
	{
		try
		{
			ControlNumber controlNumber;
			String query = "SELECT * FROM controlnumbers WHERE controlNumber = ?;";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNum);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				controlNumber =  new ControlNumber(resultSet.getInt(1),resultSet.getString(2), resultSet.getInt(3), resultSet.getTimestamp(5), resultSet.getBoolean(6));
				dbConnection.close();
				return controlNumber;
			}
			
			else 
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<ControlNumber> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM controlnumbers WHERE status = '0' AND expirationTime >= TIME(NOW());";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			controlNumbers.clear();
			while(resultSet.next())
			{
				ControlNumber controlNumber = new ControlNumber(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getTimestamp(5), resultSet.getBoolean(6));
				controlNumbers.add(controlNumber);			
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all controlnumbers from DB");
			e.printStackTrace();
		}
		return controlNumbers.iterator();
	}
	
	public boolean controlNumberIsValid(String controlNumber)
	{
		try 
		{
			String query = "SELECT * FROM controlnumbers WHERE status = '0' AND controlNumber = ? AND expirationTime >= TIME(NOW());";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNumber);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				dbConnection.close();
				return true;
			}
						
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in controlNumberIsValid()");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean controlNumIsExpired(String controlNumber)
	{
		try 
		{
			String query = "SELECT * FROM ControlNumbers WHERE status = '0' AND controlNumber = ? AND expirationTime < TIME(NOW());";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNumber);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				dbConnection.close();
				return true;
			}			
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in controlNumberIsValid()");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean updateData(Object obj) 
	{
		try 
		{
			ControlNumber controlNumber = (ControlNumber) obj;
			String query = "UPDATE controlnumbers SET controlnumber = ?, formID = ?, expirationTime = ?, status = ? WHERE controlNumberid = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNumber.getControlNumber());
			preparedStatement.setInt(2, controlNumber.getForm());
			preparedStatement.setTimestamp(3, controlNumber.getExpirationTime());
			preparedStatement.setBoolean(4, controlNumber.getStatus());
			preparedStatement.setInt(5, controlNumber.getId());
			preparedStatement.execute();
			dbConnection.close();
			return true;
			
		} catch (SQLException a)
		{
			System.out.println("Update Error");
			a.printStackTrace();
		}
		return false;
	}
	
	public boolean insertData(Object obj)
	{
		try
		{
			ControlNumber controlNumber = (ControlNumber) obj;
			String query = "INSERT INTO controlnumbers VALUES(?,?,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNumber.getControlNumber());
			preparedStatement.setInt(2, controlNumber.getForm());
			preparedStatement.setTimestamp(3, controlNumber.getExpirationTime());
			preparedStatement.setBoolean(4, controlNumber.getStatus());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new control number");
			e.printStackTrace();
		}
		return false;
	}

	public Iterator getList()
	{
		return controlNumbers.iterator();
	}
	
	
	public void resetList()
	{
		controlNumbers.clear();
	}
	
	public ControlNumber hasControlNum(String controlNum)
	{
		ControlNumber controlNumber = null;
		try 
		{
			String query = "SELECT * FROM controlnumbers WHERE controlNumber = '"+ controlNum +"'";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			controlNumbers.clear();
			if(resultSet.next())
				controlNumber =  new ControlNumber(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getTimestamp(5), resultSet.getBoolean(6));
			dbConnection.close();			
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in hasControlNum");
			System.out.println(e);
		}
		return controlNumber;
	}
	
	public String insertControlNum(int formId, int serviceId)
	{
		String controlNum = ControlNumberFactory.generateControlNum(serviceId);
		while(hasControlNum(controlNum) != null)
		{
			controlNum = ControlNumberFactory.generateControlNum(serviceId);
		}
		try
		{
			preparedStatement = dbConnection.getConnection().prepareStatement("INSERT INTO controlnumbers (controlNumber,serviceId, formId,expirationTime, status) VALUES (?, ?, ?, ADDTIME(TIME(NOW()), '00:30:00'), '0')");
			preparedStatement.setString(1, controlNum);
			preparedStatement.setInt(2, serviceId);
			preparedStatement.setInt(3, formId);
			preparedStatement.executeUpdate();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println(e);
		}
		return controlNum;
	}

	public int getFormID(String controlNum)
	{
		try
		{
			String query = "SELECT formID FROM controlnumbers WHERE controlNumber = ? AND status = '0';";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNum);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				int id = resultSet.getInt(1);
				dbConnection.close();
				return id;
			}
			else 
			{
				dbConnection.close();
				return 0;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		return 0;
	}
	
	public int getControlNumberID(String controlNum)
	{
		try
		{
			String query = "SELECT controlNumberID FROM controlnumbers WHERE controlNumber = ? AND status = '0';";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, controlNum);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				int id = resultSet.getInt(1);
				dbConnection.close();
				return id;
			}
			else 
			{
				dbConnection.close();
				return 0;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT ControlNum");
			e.printStackTrace();
		}
		return 0;
	}
}