package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Answer;
import Model.Question;

public class AnswerManager{

	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Answer> answers;
	
	private static AnswerManager aM = null;
	
	public static synchronized AnswerManager getInstance() 
	{
        if (aM == null)
        {
            aM = new AnswerManager();
        }
 
        return aM;
    }
	
	public AnswerManager()
	{
		connect = DBConnection.getInstance();
		answers = new ArrayList<Answer>();
	}
	
	public Answer getData(int controlNumberid)
	{
		try
		{
			Answer a;
			
			String query = "SELECT * FROM answers WHERE controlNumberID = ?";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, controlNumberid);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				a = new Answer(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
				return a;
			}
			
			else 
				return null;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Answer");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<Answer> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM Answers";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			answers = new ArrayList<Answer>();
			while(rs.next())
			{
				Answer a = new Answer(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getBoolean(4));
				answers.add(a);			
			}
						
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all answers from DB");
			e.printStackTrace();
		}
		connect.close();
		return answers.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		Answer a = (Answer) obj;
		
		String query = "UPDATE answers SET questionID = ?, controlnumberid = ?, answer = ?, isArchived = ? WHERE controlnumberid = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, a.getQuestionID());
			statement.setInt(2, a.getControlNumberID());
			statement.setInt(3, a.getAnswer());
			statement.setBoolean(4, a.getIsArchived());
			statement.setInt(5, a.getControlNumberID());
			statement.execute();
			connect.close();
			return true;
			
		} catch (SQLException x) {
	
			System.out.println("Update Error");
			x.printStackTrace();
		}
		connect.close();
		return false;
	}
	
	public boolean insertData(Object obj) {
		try
		{
			Answer a = (Answer) obj;
			String query = "INSERT INTO answers values(?,?,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, a.getQuestionID());
			statement.setInt(2, a.getControlNumberID());
			if(a.getAnswer() == 0)
				statement.setNull(3, java.sql.Types.VARCHAR);
			else
				statement.setInt(3, a.getAnswer());
			statement.setBoolean(4, a.getIsArchived());
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
		return answers.iterator();
	}
	
	
	public void resetList()
	{
		answers.clear();
	}
	
	
	public float getAVG(int questionID, int serviceID, int officeID)
	{
		
		try
		{
			float a;
			
			String query = " SELECT AVG(a.answer) "
					+ "FROM answers a LEFT JOIN controlnumbers c ON a.controlNumberID = c.controlNumberID "
					+ "LEFT JOIN formquestions fq ON c.formId = fq.formId AND a.questionId = fq.questionId "
					+ "LEFT JOIN forms f ON fq.formId = f.formId "
					+ "LEFT JOIN offices o ON f.officeId = o.officeId "
					+ "LEFT JOIN services s ON o.officeId = s.officeId AND s.serviceId = c.serviceId "
					+ "WHERE a.questionId = ? AND s.serviceId = ?";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, questionID);
			statement.setInt(2,serviceID);
			rs = statement.executeQuery();
			
			if (rs.next() != false)
			{
				a = rs.getFloat(1);
				return a;
			}
			
			else 
				return 0;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to get AVG");
			e.printStackTrace();
		}
		
		connect.close();
		return 0;
		
	}
	
}
