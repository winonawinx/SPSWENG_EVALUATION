package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.User;

public class UserManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<User> users;

	private static UserManager userManager = null;

	public static synchronized UserManager getInstance()
	{
		if (userManager == null)
			userManager = new UserManager();
		return userManager;
	}

	public UserManager()
	{
		dbConnection = DBConnection.getInstance();
		users = new ArrayList<User>();
	}

	public User getData(String userName)
	{
		try {
			User user;
			String query = "SELECT * FROM users WHERE userName = ? AND isArchived = 0";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, userName);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
						resultSet.getBoolean(7));
				dbConnection.close();
				return user;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT User");
			e.printStackTrace();
		}
		return null;
	}

	public Iterator<User> getAllData()
	{
		try
		{
			String query = "SELECT * FROM users WHERE isArchived = 0;";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			users = new ArrayList<User>();
			while (resultSet.next()) {
				User user = new User(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
						resultSet.getString(6), resultSet.getBoolean(7));

				users.add(user);
			}
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return users.iterator();
	}

	public Iterator<User> getOfficeHead()
	{
		ArrayList<User> officeHeads = new ArrayList<User>();
		try
		{
			String query = "SELECT * FROM users WHERE type='officehead';";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next())
			{
				User user = new User(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
						resultSet.getString(6), resultSet.getBoolean(7));
				officeHeads.add(user);
			}
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return officeHeads.iterator();
	}

	public boolean updateData(Object obj)
	{
		try
		{
			User u = (User) obj;
			String query = "UPDATE users SET userId = ?, userName = ?, userEmail = ?, userPassword = ?, "
						+"type = ?, isArchived = ? WHERE userId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, u.getID());
			preparedStatement.setString(2, u.getUsername());
			preparedStatement.setString(3, u.getEmail());
			preparedStatement.setString(4, u.getPassword());
			preparedStatement.setString(5, u.getType());
			preparedStatement.setBoolean(6, u.getIsArchived());
			preparedStatement.setInt(7, u.getID());
			preparedStatement.executeUpdate();
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

	public void removeUser(int userId)
	{
		try
		{
			String query = "UPDATE users SET isArchived = ? WHERE userID = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setBoolean(1, true);
			preparedStatement.setInt(2, userId);
			preparedStatement.executeUpdate();
			dbConnection.close();
		}
		catch (SQLException e)
		{
			System.out.println("Delete Error");
			e.printStackTrace();
		}
	}

	public boolean insertData(String email, String username, String password, String type, String title)
	{
		try
		{
			String query = "INSERT INTO users (userEmail, userName, userTitle, userPassword, type, isArchived) VALUES(?,?,?,?,?,'0')";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, username);
			preparedStatement.setString(3, type);
			preparedStatement.setString(4, password);
			preparedStatement.setString(5, type);
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new user");
			e.printStackTrace();
		}
		return false;
	}

	public Iterator getList()
	{
		return users.iterator();
	}

	public void resetList()
	{
		users.clear();
	}

	public boolean isValid(String username, String password)
	{
		getAllData();
		for (int x = 0; x < users.size(); x++)
			if (username.equals(users.get(x).getUsername()))
				if (password.equals(users.get(x).getPassword()))
					return true;
				else
					return false;
		return false;
	}

}
