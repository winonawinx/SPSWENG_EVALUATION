package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Form;
import Model.Question;

public class QuestionManager
{
	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Question> questions;
	
	private static QuestionManager qM = null;
	
	public static synchronized QuestionManager getInstance() 
	{
        if (qM == null)
        {
            qM = new QuestionManager();
        }
 
        return qM;
    }
	
	public QuestionManager()
	{
		connect = DBConnection.getInstance();
		questions = new ArrayList<Question>();
	}
	
	public Question getData(String question)
	{
		try
		{
			Question q;
			
			String query = "SELECT * FROM Questions WHERE question = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, question);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				q = new Question(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				return q;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Question");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<Question> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM Questions WHERE isArchived = '0'";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			questions.clear();
			while(rs.next())
			{
				Question q = new Question(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				questions.add(q);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return questions.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		Question q = (Question) obj;
		
		String query = "UPDATE questions SET question = ?, isArchived = ? WHERE QuestionID = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, q.getQuestion());
			statement.setBoolean(2, q.getIsArchived());
			statement.setInt(3, q.getID());
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
			Question q = (Question) obj;
			String query = "INSERT INTO questions values(DEFAULT,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setString(1, q.getQuestion());
			statement.setBoolean(2, q.getIsArchived());
			statement.execute();
			//notifyObserver();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new question");
			e.printStackTrace();
		}
		connect.close();
		return false;
	}

	public Iterator getList()
	{
		return questions.iterator();
	}
	
	
	public void resetList()
	{
		questions.clear();
	}
	
	
	public Iterator<Question> getOfficeQuestions(int officeid)
	{
		ArrayList<Question> list = new ArrayList<Question>();
		
		try 
		{
			String query = "SELECT * FROM Questions WHERE isArchived = '0' AND questionID IN (Select questionID FROM formquestions WHERE FormID IN (SELECT FormID from Forms WHERE officeID = ?))";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, officeid);
			rs = statement.executeQuery();
			

			while(rs.next())
			{
				Question q = new Question(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				list.add(q);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all office questions from DB");
			e.printStackTrace();
		}
		connect.close();
		return list.iterator();
		
	}
	
	public boolean removeData(Object obj, boolean isArchived)
	{
		Question q = (Question) obj;
		
		String query = "UPDATE questions SET isArchived = ? WHERE QuestionID = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setBoolean(1, isArchived);
			statement.setInt(2, q.getID());
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
	
}
