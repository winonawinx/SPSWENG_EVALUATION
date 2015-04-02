package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Question;
import Model.User;

public class UserManager
{
	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<User> users;

	private static UserManager uM = null;
	
	public static synchronized UserManager getInstance() 
	{
        if (uM == null)
        {
            uM = new UserManager();
        }
 
        return uM;
    }
	
	public UserManager()
	{
		connect = DBConnection.getInstance();
		users = new ArrayList<User>();
	}	
	
	public User getData(String username)
	{
		try
		{
			User u;
			
			String query = "SELECT * FROM Users WHERE username = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, username);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
				return u;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT User");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public ArrayList<User> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM Users;";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			users = new ArrayList<User>();
			while(rs.next())
			{
				User u = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));

				users.add(u);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return users;
	}
	
	public boolean updateData(Object obj) 
	{
		User u = (User) obj;
		
		String query = "UPDATE users SET userID = ?, username = ?, useremail = ?, userpassword = ?, type = ?, isArchived = ? WHERE userID = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, u.getID());
			statement.setString(2, u.getUsername());
			statement.setString(3, u.getEmail());
			statement.setString(4, u.getPassword());
			statement.setString(5, u.getType());
			statement.setBoolean(6, u.getIsArchived());
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
	
	//public boolean insertData(Object obj) {
	public boolean insertData(String email, String username, String password, String type, String title)
	{
		try
		{
			String query = "INSERT INTO users (userEmail, userName, userTitle, userPassword, userType, isArchived) values(?,?,?,?,'0')";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, username);
			statement.setString(3, title);
			statement.setString(4, password);
			statement.setString(5, type);
			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new user");
			e.printStackTrace();
		}
		connect.close();
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
	
	
	public Boolean isValid(String username, String password)
	{
		getAllData();
		for(int x = 0; x < users.size(); x++)
		{
			if(username.equals(users.get(x).getUsername()))
			{
				if(password.equals(users.get(x).getPassword()))
				{
					return true;
				}
				else return false;
			}
		}
		
		return false;
	}
	
}
