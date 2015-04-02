package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Form;
import Model.Question;

public class FormQuestionsManager
{
	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Question> questions;

	private static FormQuestionsManager qM = null;
	
	public static synchronized FormQuestionsManager getInstance() 
	{
        if (qM == null) {
            qM = new FormQuestionsManager();
        }
 
        return qM;
    }
	
	public FormQuestionsManager()
	{
		connect = DBConnection.getInstance();
		questions = new ArrayList<Question>();
	}
	
	public ArrayList<Question> getAllData(int formID) 
	{	
		try 
		{
			String query = "SELECT * FROM Questions WHERE questionID IN (SELECT questionID from formquestions WHERE formID = ?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, formID);
			rs = statement.executeQuery();
			
			questions = new ArrayList<Question>();
			while(rs.next())
			{
				Question q = new Question(rs.getInt(1), rs.getString(2), rs.getBoolean(3));
				questions.add(q);			
			}
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all qns from DB");
			e.printStackTrace();
		}
		connect.close();
		return questions;
	}
	
	public ArrayList<Question> getList()
	{
		return (ArrayList<Question>) questions.clone();
	}
}
