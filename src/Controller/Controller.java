package Controller;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import Model.Answer;
import Model.Comment;
import Model.ControlNumber;
import Model.Form;
import Model.Office;
import Model.Question;
import Model.Service;
import Model.User;
import Model.DB.AnswerManager;
import Model.DB.CommentManager;
import Model.DB.ControlNumManager;
import Model.DB.FormManager;
import Model.DB.OfficeManager;
import Model.DB.QuestionManager;
import Model.DB.ServiceManager;
import Model.DB.UserManager;

public class Controller {

	private UserManager um;
	private OfficeManager om;
	private AnswerManager am;
	private FormManager fm;
	private ControlNumManager cm;
	private QuestionManager qm;
	private ServiceManager sm;
	private CommentManager cmm;
	
	public Controller()
	{
		um = new UserManager();
		om = new OfficeManager();
		am = new AnswerManager();
		fm = new FormManager();
		cm = new ControlNumManager();
		qm = new QuestionManager();
		sm = new ServiceManager();
		cmm = new CommentManager();
		
		getAllData();
	}
	
	public Iterator<User> getAllUsers()
	{
		return um.getAllData();
	}
	
	public void addUser(String email, String username, String password, String type, String title)
	{
		um.insertData(email, username, password, type, title);
	}
	
	public Iterator<Office> getAllOffices()
	{
		return om.getAllData();
	}
	
	public void addOffice(int ID, String name, Boolean isArchived)
	{
		om.insertData(new Office(ID, name, isArchived));
	}
	
	public Iterator<Answer> getAllAnswers()
	{
		return am.getAllData();
	}
	
	public void addAnswer(int questionID, int controlnumber, int answer, Boolean isArchived)
	{
		am.insertData(new Answer(questionID, controlnumber, answer, isArchived));
	}
	
	public boolean controlNumIsExpired(String cn)
	{
		return cm.controlNumIsExpired(cn);
	}
	
	public Iterator<Form> getAllForms()
	{
		return fm.getAllData();
	}
	
	public void addForm(int ID, int service, Date startDate, Date endDate, Boolean isArchived)
	{
		fm.insertData(new Form(ID, service, startDate, endDate, isArchived));
	}
	
	public Iterator<ControlNumber> getAllControlNumbers()
	{
		return cm.getAllData();
	}
	
	public void addControlNumber(int id, String controlNumber, int form, Timestamp expirationTime, Boolean status)
	{
		cm.insertData(new ControlNumber(id, controlNumber, form, expirationTime, status));
	}
	
	public Iterator<Question> getAllQuestions()
	{
		return qm.getAllData();
	}
	
	public void addQuestion(int ID, String question, Boolean isArchived)
	{
		qm.insertData(new Question(ID, question, isArchived));
	}
	
	public Iterator<Service> getAllServices(int officeID)
	{
		return sm.getAllData(officeID);
	}
	
	public void addService(int ID, String service, int officeID, Boolean isArchived)
	{
		sm.insertData(new Service(ID, service, officeID, isArchived));
	}
		
	public Boolean ValidateUser(String username, String password)
	{
		return um.isValid(username, password);
	}
	
	public void getAllData()
	{
		um.getAllData();
		om.getAllData();		
		am.getAllData();
		fm.getAllData();
		cm.getAllData();
		qm.getAllData();
		um.getAllData();
		sm.getAllData();
	}
	
	public void updateControlNumber(ControlNumber cn)
	{
		cm.updateData(cn);
	}
	
	
	public Iterator<Question> getOfficeQuestions(int officeid)
	{
		return qm.getOfficeQuestions(officeid);
	}
	
	public Office getOffice(int officeID)
	{
		return om.getData(officeID);
	}
	
	public Office getOffice(String office)
	{
		return om.getOffice(office);
	}
	
	public Office getOfficeByName(String officeName)
	{
		return om.getDataByName(officeName);
	}
	
	public User getUser(String email, String password)
	{
		Iterator<User> iterator = getAllUsers();
		ArrayList<User> users = new ArrayList<User>();
		while(iterator.hasNext())
		{
			users.add((User)iterator.next());
		}
		for(int i = 0, size = users.size(); i < size; i++)
		{
			User u = users.get(i);
			if(u.getEmail().equals(email) && u.getPassword().equals(password))
				return u;
		}
		return null;
	}
	
	public float getAVG(int questionID, int serviceID, int officeID)
	{
		return am.getAVG(questionID, serviceID, officeID);
	}
	
	public void insertControlNumber(ControlNumber controlnumber)
	{
		cm.insertData(controlnumber);
	}
	
	public Boolean ControlNumberIsValid(String controlnumber)
	{
		if(cm.controlNumberIsValid(controlnumber))
			return true;
		return false;
	}
	
	public int getControlNumberFormID(String controlnumber)
	{
		return cm.getFormID(controlnumber);
	}
	
	public Form getForm(int formID)
	{
		return fm.getData(formID);
	}
	
	public ControlNumber getControlNumber(String controlnumber)
	{
		return cm.getData(controlnumber);
	}
	
	public Boolean hasUser(String userEmail)
	{
		Iterator<User> iterator = um.getAllData();
		ArrayList<User> users = new ArrayList<User>();
		while(iterator.hasNext())
		{
			users.add((User)iterator.next());
		}
		for(int x = 0; x < users.size(); x++)
		{
			if(users.get(x).getEmail().equals(userEmail))
				return true;
		}
		return false;
	}
	
	public int getOfficeIDbyHead(int headID)
	{
		return om.getIDbyHead(headID);
	}
	
	public String getFormOfficeAndService(int controlNumberID, int formID)
	{
		return fm.getFormOfficeAndService(controlNumberID, formID);
	}
	
	public Iterator<Service> getOfficeServices(int officeid)
	{
		return sm.getOfficeServices(officeid);
	}
	
	public Iterator<Comment> getAllComments()
	{
		return cmm.getAllData();
	}
	
	public Iterator<Comment> getAllComments(int controlNumberid)
	{
		return cmm.getControlComments(controlNumberid);
	}
	
	public void insertComment(Comment comment)
	{
		cmm.insertData(comment);
	}
	
	public Iterator<Comment> getServiceComments(int serviceid)
	{
		return cmm.getServiceComments(serviceid);
	}
	
	public int getFormID(int officeID)
	{
		return fm.getFormId(officeID);
	}
	
	public int getServiceID(String service, int officeID)
	{
		return sm.getServiceID(service, officeID);
	}
	
	public Iterator<Office> getOfficeHeadOffices(int userID)
	{
		return om.getOfficesByHead(userID);
	}
	
	public Question getQuestion(String question)
	{
		return qm.getData(question);
	}
	
	public void addFormQuestions(ArrayList<Question> questions, int formID)
	{
		for(int x = 0; x < questions.size(); x++)
			fm.insertFormQuestions(formID, questions.get(x).getID());
	}
	
	public Service getService(int serviceId)
	{
		return sm.getService(serviceId);
	}
}
