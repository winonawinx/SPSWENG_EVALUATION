package Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Form {

	private int ID;
	private int office;
	private Date startDate;
	private Date endDate;
	private Boolean isArchived;
	private ArrayList<Question> questions;
	
	public Form(int ID, int office, Date startDate, Date endDate, Boolean isArchived)
	{
		setID(ID);
		setOffice(office);
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
	
	public int getOffice()
	{
		return office;
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
	
	public void setOffice(int office)
	{
		this.office = office;
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
