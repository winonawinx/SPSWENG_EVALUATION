package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Answer;

public class AnswerManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Answer> answers;
	
	private static AnswerManager answerManager = null;
	
	public static synchronized AnswerManager getInstance() 
	{
        if (answerManager == null)
        	answerManager = new AnswerManager();
        return answerManager;
    }
	
	public AnswerManager()
	{
		dbConnection = DBConnection.getInstance();
		answers = new ArrayList<Answer>();
	}
	
	public Answer getData(int controlNumberId)
	{
		try
		{
			Answer answer;
			String query = "SELECT * FROM answers WHERE controlNumberId = ?";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, controlNumberId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				answer = new Answer(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getBoolean(4));
				dbConnection.close();
				return answer;
			}
			else
			{
				dbConnection.close();
				return null;
			}
		}
		catch (Exception e)
		{
			System.out.println("Unable to SELECT Answer");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<Answer> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM answers";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			answers = new ArrayList<Answer>();
			while(resultSet.next())
			{
				Answer answer = new Answer(resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3), resultSet.getBoolean(4));
				answers.add(answer);	
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all ANSWERS from DB");
			e.printStackTrace();
		}
		return answers.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		try 
		{
			Answer answer = (Answer) obj;
			String query = "UPDATE answers SET questionId = ?, controlNumberId = ?, answer = ?, isArchived = ? WHERE controlNumberId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, answer.getQuestionID());
			preparedStatement.setInt(2, answer.getControlNumberID());
			preparedStatement.setInt(3, answer.getAnswer());
			preparedStatement.setBoolean(4, answer.getIsArchived());
			preparedStatement.setInt(5, answer.getControlNumberID());
			preparedStatement.execute();
			dbConnection.close();
			return true;
			
		}
		catch (SQLException e)
		{
	
			System.out.println("Update error in ANSWERS");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertData(Object obj)
	{
		try
		{
			Answer answer = (Answer) obj;
			String query = "INSERT INTO answers VALUES(?,?,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, answer.getQuestionID());
			preparedStatement.setInt(2, answer.getControlNumberID());
			if(answer.getAnswer() == 0)
				preparedStatement.setNull(3, java.sql.Types.VARCHAR);
			else
				preparedStatement.setInt(3, answer.getAnswer());
			preparedStatement.setBoolean(4, answer.getIsArchived());
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
		return answers.iterator();
	}
	
	
	public void resetList()
	{
		answers.clear();
	}
	
	
	public float getAVG(int questionID, int formID, int serviceID, int officeID)
	{
		try
		{
			float average;
			String query = " SELECT AVG(a.answer) "
					+ "FROM answers a LEFT JOIN controlnumbers c ON a.controlNumberId = c.controlNumberId "
					+ "LEFT JOIN formquestions fq ON c.formId = fq.formId AND a.questionId = fq.questionId "
					+ "LEFT JOIN forms f ON fq.formId = f.formId "
					+ "LEFT JOIN offices o ON f.officeId = o.officeId "
					+ "LEFT JOIN services s ON o.officeId = s.officeId AND s.serviceId = c.serviceId "
					+ "WHERE a.questionId = ? AND f.formId = ? AND s.serviceId = ? AND o.officeId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, questionID);
			preparedStatement.setInt(2, formID);
			preparedStatement.setInt(3, serviceID);
			preparedStatement.setInt(4, officeID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				average = resultSet.getFloat(1);
				dbConnection.close();
				return average;
			}
			else
			{
				dbConnection.close();
				return 0;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to get AVG");
			e.printStackTrace();
		}
		return 0;
	}
}
