package Model.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import DBConnection.DBConnection;
import Model.Form;
import Model.Office;
import Model.Question;
import Model.Service;
import Model.User;

public class FormManager
{
	private DBConnection connect;
	private ResultSet rs;
	private PreparedStatement statement;
	private ArrayList<Form> forms;
	private FormQuestionsManager fqm;
	
	private static FormManager fM = null;
	
	public static synchronized FormManager getInstance() 
	{
        if (fM == null) {
            fM = new FormManager();
        }
 
        return fM;
    }
	
	public FormManager()
	{
		connect = DBConnection.getInstance();
		forms = new ArrayList<Form>();
		fqm = new FormQuestionsManager();
	}
	
	public Form getData(int formID)
	{
		try
		{
			Form c;

			String query = "SELECT * FROM forms WHERE formID = ?;";	
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, formID);
			rs = statement.executeQuery();
			
			if (rs.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				Form f = new Form(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getBoolean(5));
				iterator = fqm.getAllData(f.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				f.setQuestions(questions);
				return f;
			}
			
			else 
				return null;
		}
		
		catch (SQLException e)
		{
			System.out.println("Unable to SELECT Form");
			e.printStackTrace();
		}
		
		connect.close();
		return null;
	}
	
	public Iterator<Form> getAllData() 
	{	
		try 
		{
			String query = "SELECT * FROM forms";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			forms = new ArrayList<Form>();
			
			while(rs.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				
				Form f = new Form(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getBoolean(5));
				iterator = fqm.getAllData(f.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				f.setQuestions(questions);
				forms.add(f);
			}
			
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		System.out.println(forms.get(0).getQuestions().size());
		return forms.iterator();
	}
	
	public boolean updateData(Object obj) 
	{
		Form f = (Form) obj;
		
		String query = "UPDATE forms SET serviceId = ?, startdate = ?, endDate = ?, isArchived = ? WHERE formID = ?";
		try 
		{
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, f.getService());
			statement.setDate(2, f.getStartDate());
			statement.setDate(3, f.getEndDate());
			statement.setBoolean(4, f.getIsArchived());
			statement.setInt(5, f.getID());
			statement.execute();
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
			Form f = (Form) obj;
			String query = "INSERT INTO forms values(?,?,?,?)";
			statement = connect.getConnection().prepareStatement(query);
			statement.setInt(1, f.getService());
			statement.setDate(2, f.getStartDate());
			statement.setDate(3, f.getEndDate());
			statement.setBoolean(4, f.getIsArchived());
			statement.execute();
			connect.close();
			return true;
		}
		catch (SQLException e)
		{
			System.out.println("Unable to INSERT new form");
			e.printStackTrace();
		}
		connect.close();
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

	
	public int getFormId(int serviceId) 
	{
		try {
			System.out.println(serviceId);
			String sql = "SELECT formId "
					+ "FROM forms "
					+ "WHERE serviceId = " + serviceId + " AND isArchived = 0;";
			statement = connect.getConnection().prepareStatement(sql);
			rs = statement.executeQuery(sql);
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error in hasForm");
		}
		return 0;
	}

	public Form getFormById(int formId) 
	{
		Form form = null;
		try 
		{
			String query = "SELECT * FROM forms WHERE formId = " + formId;
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			
			if(rs.next())
			{
				ArrayList<Question> questions = new ArrayList<Question>();
				Iterator iterator = null;
				
				form = new Form(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getBoolean(5));
				iterator = fqm.getAllData(form.getID());
				while(iterator.hasNext())
				{
					questions.add((Question)iterator.next());
				}
				form.setQuestions(questions);
				return form;
			}
			
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return form;
	}
	
	public String getFormOfficeAndService(int formID)
	{
		String OfficeService = null;
		try 
		{
			String query = "SELECT S.serviceName, O.officeName FROM services S, Offices O WHERE" + 
					" S.serviceID = (SELECT serviceID FROM forms WHERE formID = " + formID + ") AND " + 
					"O.officeID = (SELECT officeID FROM services WHERE serviceID = (SELECT serviceID FROM forms WHERE formID = " + formID + "));";
			statement = connect.getConnection().prepareStatement(query);
			rs = statement.executeQuery();
			
			
			if(rs.next())
			{
				OfficeService = rs.getString(2) + " - " + rs.getString(1);
			}
			
		} 
		catch (SQLException e) {
			System.out.println("ERROR in getting all data from DB");
			e.printStackTrace();
		}
		connect.close();
		return OfficeService;
	}
	
}
