package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Answer;
import Model.Comment;

public class CommentManager {

	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Comment> comments;
	
	private static CommentManager cM = null;
	
	public static synchronized CommentManager getInstance() 
	{
        if (cM == null)
        {
            cM = new CommentManager();
        }
 
        return cM;
    }
	
	public CommentManager()
	{
		connect = DBConnection.getInstance();
		comments = new ArrayList<Comment>();
	}
	
	public Comment getData(int controlNumberid)
	{
		try
		{
			Comment c;
			
			String query = "SELECT * FROM comments WHERE controlNumberID = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, controlNumberid);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				c = new Comment(rs.getInt(1), rs.getString(2));
				return c;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Comment");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<Comment> getAllData() 
	{	
		
		try 
		{
			String query = "SELECT * FROM comments";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			comments = new ArrayList<Comment>();
			while(rs.next())
			{
				Comment c = new Comment(rs.getInt(1), rs.getString(2));
				comments.add(c);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all answers from DB");
			e.printStackTrace();
		}
		connect.close();
		return comments.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		return false;
	}
	
	public boolean insertData(Object obj) {
		try
		{
			Comment c = (Comment) obj;
			String query = "INSERT INTO comments values(?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, c.getControlnumberid());
			statement.setString(2, c.getComment());
			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new answer");
			e.printStackTrace();
		}
		connect.close();
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
	
	public Iterator<Comment> getControlComments(int controlNumberid)
	{
		try 
		{
			String query = "SELECT * FROM comments where controlnumberid = ?";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			comments = new ArrayList<Comment>();
			while(rs.next())
			{
				Comment c = new Comment(rs.getInt(1), rs.getString(2));
				comments.add(c);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all comments from DB");
			e.printStackTrace();
		}
		connect.close();
		return comments.iterator();
	}
	
	public Iterator<Comment> getServiceComments(int serviceid)
	{
		try 
		{
			String query = "SELECT * FROM comments where controlnumberid IN (select controlnumberid from controlnumbers where formid = (SELECT formid from forms where serviceid = ?));";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, serviceid);
			rs = statement.executeQuery();
			
			comments = new ArrayList<Comment>();
			while(rs.next())
			{
				Comment c = new Comment(rs.getInt(1), rs.getString(2));
				comments.add(c);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all comments from DB");
			e.printStackTrace();
		}
		connect.close();
		return comments.iterator();
	}
	
	
}
