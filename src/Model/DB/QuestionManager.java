package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Question;

public class QuestionManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Question> questions;
	
	private static QuestionManager questionManager = null;
	
	public static synchronized QuestionManager getInstance() 
	{
        if (questionManager == null)
            questionManager = new QuestionManager();
        return questionManager;
    }
	
	public QuestionManager()
	{
		dbConnection = DBConnection.getInstance();
		questions = new ArrayList<Question>();
	}
	
	public Question getData(String q)
	{
		try
		{
			Question question;
			String query = "SELECT * FROM questions WHERE question = ?";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, q);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				question = new Question(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				dbConnection.close();
				return question;
			}
			else 
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Question");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<Question> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM questions WHERE isArchived = '0'";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			questions.clear();
			while(resultSet.next())
			{
				Question q = new Question(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				questions.add(q);		
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return questions.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		try 
		{
			Question q = (Question) obj;
			String query = "UPDATE questions SET question = ?, isArchived = ? WHERE QuestionID = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, q.getQuestion());
			preparedStatement.setBoolean(2, q.getIsArchived());
			preparedStatement.setInt(3, q.getID());
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
			Question question = (Question) obj;
			String query = "INSERT INTO questions values(DEFAULT,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setString(1, question.getQuestion());
			preparedStatement.setBoolean(2, question.getIsArchived());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new question");
			e.printStackTrace();
		}
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
	
	
	public Iterator<Question> getOfficeQuestions(int officeId)
	{
		ArrayList<Question> questionList = new ArrayList<Question>();
		try 
		{
			String query = "SELECT * FROM questions WHERE isArchived = '0' AND questionId "
						+ "IN (Select questionId FROM formquestions WHERE formId IN "
						+ "(SELECT formId from forms WHERE officeId = ?))";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				Question question = new Question(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				questionList.add(question);			
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all office questions from DB");
			e.printStackTrace();
		}
		return questionList.iterator();
	}
	
	public boolean removeData(Object obj, boolean isArchived)
	{
		try 
		{
			Question question = (Question) obj;
			String query = "UPDATE questions SET isArchived = ? WHERE questionId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setBoolean(1, isArchived);
			preparedStatement.setInt(2, question.getID());
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
	
}
