package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Comment;

public class CommentManager
{

	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Comment> comments;
	
	private static CommentManager commentManager = null;
	
	public static synchronized CommentManager getInstance() 
	{
        if (commentManager == null)
            commentManager = new CommentManager();
        return commentManager;
    }
	
	public CommentManager()
	{
		dbConnection = DBConnection.getInstance();
		comments = new ArrayList<Comment>();
	}
	
	public Comment getData(int controlNumberId)
	{
		try
		{
			Comment comment;
			String query = "SELECT * FROM comments WHERE controlNumberId = ?";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, controlNumberId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				comment = new Comment(resultSet.getInt(1), resultSet.getString(2));
				dbConnection.close();
				return comment;
			}
			
			else 
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Comment");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<Comment> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM comments";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			comments = new ArrayList<Comment>();
			while(resultSet.next())
			{
				Comment comment = new Comment(resultSet.getInt(1), resultSet.getString(2));
				comments.add(comment);			
			}
			dbConnection.close();	
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all answers from DB");
			e.printStackTrace();
		}
		return comments.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		return false;
	}
	
	public boolean insertData(Object obj)
	{
		try
		{
			Comment comment = (Comment) obj;
			String query = "INSERT INTO comments VALUES(?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, comment.getControlnumberid());
			preparedStatement.setString(2, comment.getComment());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new answer");
			e.printStackTrace();
		}
		return false;
	}

	public Iterator getList()
	{
		return comments.iterator();
	}
	
	
	public void resetList()
	{
		comments = new ArrayList<Comment>();
	}
	
	public Iterator<Comment> getControlComments(int controlNumberId)
	{
		try 
		{
			String query = "SELECT * FROM comments WHERE controlNmberId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			comments = new ArrayList<Comment>();
			while(resultSet.next())
			{
				Comment comment = new Comment(resultSet.getInt(1), resultSet.getString(2));
				comments.add(comment);			
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all comments from DB");
			e.printStackTrace();
		}
		return comments.iterator();
	}
	
	public Iterator<Comment> getServiceComments(int officeId, int serviceId)
	{
		try 
		{
			String query = "SELECT * FROM comments WHERE controlNumberId IN (SELECT controlNumberId FROM controlnumbers "
						+ "WHERE formId = (SELECT formid FROM forms WHERE officeId = ? ORDER BY formId DESC LIMIT 1) && "
						+ "serviceId = ? && status = 1);";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			preparedStatement.setInt(2, serviceId);
			resultSet = preparedStatement.executeQuery();
			comments = new ArrayList<Comment>();
			while(resultSet.next())
			{
				Comment comment = new Comment(resultSet.getInt(1), resultSet.getString(2));
				comments.add(comment);			
			}
			dbConnection.close();	
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all comments from DB");
			e.printStackTrace();
		}
		return comments.iterator();
	}
}
