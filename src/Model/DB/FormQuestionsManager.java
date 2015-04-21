package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Question;

public class FormQuestionsManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Question> questions;

	private static FormQuestionsManager formQuestionsManager = null;
	
	public static synchronized FormQuestionsManager getInstance() 
	{
        if (formQuestionsManager == null)
            formQuestionsManager = new FormQuestionsManager();
        return formQuestionsManager;
    }
	
	public FormQuestionsManager()
	{
		dbConnection = DBConnection.getInstance();
		questions = new ArrayList<Question>();
	}
	
	public Iterator<Question> getAllData(int formId) 
	{	
		try 
		{
			String query = "SELECT * FROM Questions WHERE questionId IN (SELECT questionID from formquestions WHERE formId = ?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, formId);
			resultSet = preparedStatement.executeQuery();
			questions = new ArrayList<Question>();
			while(resultSet.next())
			{
				Question question = new Question(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
				questions.add(question);			
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all qns from DB");
			e.printStackTrace();
		}
		return questions.iterator();
	}
	
	public Iterator<Question> getList()
	{
		return questions.iterator();
	}
}
