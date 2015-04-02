package Model;

public class Question {

	private int ID;
	private String question;
	private Boolean isArchived;
	
	public Question(int ID, String question, Boolean isArchived)
	{
		setID(ID);
		setQuestion(question);
		setIsArchived(isArchived);
	}
	
	
	public int getID()
	{
		return ID;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public Boolean getIsArchived()
	{
		return isArchived;
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}
	
	public void setQuestion(String question)
	{
		this.question = question;
	}
	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
	
}

