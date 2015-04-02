package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Form {

	private int ID;
	private int service;
	private Date startDate;
	private Date endDate;
	private Boolean isArchived;
	private ArrayList<Question> questions;
	
	public Form(int ID, int service, Date startDate, Date endDate, Boolean isArchived)
	{
		setID(ID);
		setService(service);
		setStartDate(startDate);
		setEndDate(endDate);
		setIsArchived(isArchived);
		questions = new ArrayList<Question>();
	}
	
	public int getID()
	{
		return ID;
	}
	
	public ArrayList<Question> getQuestions()
	{
		return questions;
	}
	
	public int getService()
	{
		return service;
	}
	
	public Date getStartDate()
	{
		return startDate;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	public Boolean getIsArchived()
	{
		return isArchived;
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}
	
	public void setQuestions(ArrayList<Question> questions)
	{
		this.questions = questions;
	}
	
	public void setService(int service)
	{
		this.service = service;
	}
	
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
	
}
