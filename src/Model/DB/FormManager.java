package Model.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Form;
import Model.Question;

public class FormManager
{
	private DBConnection dbConnection;
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	private ArrayList<Form> forms;
	private FormQuestionsManager formQuestionsManager;
	
	private static FormManager formManager = null;
	
	public static synchronized FormManager getInstance() 
	{
        if (formManager == null)
            formManager = new FormManager();
        return formManager;
    }
	
	public FormManager()
	{
		dbConnection = DBConnection.getInstance();
		forms = new ArrayList<Form>();
		formQuestionsManager = new FormQuestionsManager();
	}
	
	public Form getData(int formId)
	{
		try
		{
			String query = "SELECT * FROM forms WHERE formId = ?;";	
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, formId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				Form form = new Form(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getBoolean(5));
				iterator = formQuestionsManager.getAllData(form.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				form.setQuestions(questions);
				dbConnection.close();
				return form;
			}
			else 
			{
				dbConnection.close();
				return null;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Form");
			e.printStackTrace();
		}
		return null;
	}
	
	public Iterator<Form> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM forms";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			forms = new ArrayList<Form>();
			while(resultSet.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				Form form = new Form(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getBoolean(5));
				iterator = formQuestionsManager.getAllData(form.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				form.setQuestions(questions);
				forms.add(form);
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return forms.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		String query = "UPDATE forms SET serviceId = ?, startdate = ?, endDate = ?, isArchived = ? WHERE formId = ?";
		try 
		{
			Form form = (Form) obj;
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, form.getOffice());
			preparedStatement.setDate(2, form.getStartDate());
			preparedStatement.setDate(3, form.getEndDate());
			preparedStatement.setBoolean(4, form.getIsArchived());
			preparedStatement.setInt(5, form.getID());
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
			Form form = (Form) obj;
			String query = "INSERT INTO forms values(DEFAULT,?,?,?,?)";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, form.getOffice());
			preparedStatement.setDate(2, form.getStartDate());
			preparedStatement.setDate(3, form.getEndDate());
			preparedStatement.setBoolean(4, form.getIsArchived());
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new form");
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Form> getList()
	{
		return forms;
	}
	
	public void resetList()
	{
		forms.clear();
	}

	
	public int getFormId(int officeId) 
	{
		try
		{
			String query = "SELECT formId FROM forms WHERE officeId = " + officeId + " AND isArchived = 0;";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			resultSet = preparedStatement.executeQuery(query);
			if(resultSet.next())
			{
				int id = resultSet.getInt(1);
				dbConnection.close();
				return id;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	public Form getFormById(int formId) 
	{
		Form form = null;
		try 
		{
			String query = "SELECT * FROM forms WHERE formId = ?";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, formId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				form = new Form(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getBoolean(5));
				iterator = formQuestionsManager.getAllData(form.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				form.setQuestions(questions);
				dbConnection.close();
				return form;
			}
			
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return form;
	}
	
	public String getFormOfficeAndService(int controlNumberId, int formId)
	{
		String OfficeService = null;
		try 
		{
			String query = "SELECT S.serviceName, O.officeName FROM services S, Offices O, controlnumbers C WHERE"
						+ " S.officeId = O.officeId AND s.serviceId = C.serviceId AND C.controlNumberId = ? AND"
						+ " O.officeId = (SELECT officeId FROM forms WHERE formId = ? );";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, controlNumberId);
			preparedStatement.setInt(2, formId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
				OfficeService = resultSet.getString(2) + " - " + resultSet.getString(1);
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return OfficeService;
	}

	public boolean insertFormQuestions(int formId, int questionId)
	{
		try
		{
			String query = "INSERT INTO formquestions VALUES(?,?,'0')";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, formId);
			preparedStatement.setInt(2, questionId);
			preparedStatement.execute();
			dbConnection.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new formquestion");
			e.printStackTrace();
		}
		return false;
	}
	
	public Iterator<Form> getAllFormsByOffice(int officeId)
	{
		try 
		{
			String query = "SELECT * FROM forms WHERE officeId = ? ORDER BY formId DESC";
			preparedStatement = dbConnection.getConnection().prepareStatement(query);
			preparedStatement.setInt(1, officeId);
			resultSet = preparedStatement.executeQuery();
			forms = new ArrayList<Form>();	
			while(resultSet.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				Form form = new Form(resultSet.getInt(1), resultSet.getInt(2), resultSet.getDate(3), resultSet.getDate(4), resultSet.getBoolean(5));
				iterator = formQuestionsManager.getAllData(form.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				form.setQuestions(questions);
				forms.add(form);
			}
			dbConnection.close();
		} 
		catch (SQLException e)
		{
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		return forms.iterator();
	}
}
