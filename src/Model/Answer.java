package Model;

public class Answer {

	private int questionID;
	private int controlnumberid;
	private int answer;
	private Boolean isArchived;
	
	public Answer(int questionID, int controlnumberid, int answer, Boolean isArchived)
	{
		setQuestion(questionID);
		setControlNumber(controlnumberid);
		setAnswer(answer);
		setIsArchived(isArchived);
	}
	
	public int getControlNumberID()
	{
		return controlnumberid;
	}
			
	public int getQuestionID()
	{
		return questionID;
	}
	
	public int getAnswer()
	{
		return answer;
	}
		
	public Boolean getIsArchived()
	{
		return isArchived;
	}
	
	
	public void setQuestion(int questionID)
	{
		this.questionID = questionID;
	}
	
	public void setAnswer(int answer)
	{
		this.answer = answer;
	}
		
	public void setControlNumber(int controlnumberid)
	{
		this.controlnumberid = controlnumberid;
	}
	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
}
