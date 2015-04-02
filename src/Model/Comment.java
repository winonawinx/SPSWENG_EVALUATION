package Model;

public class Comment
{
	private int controlnumberid;
	private String comment;
	
	public Comment(int Controlnumberid, String comment)
	{
		setControlnumberid(Controlnumberid);
		setComment(comment);
		
	}
	
	public int getControlnumberid() {
		return controlnumberid;
	}
	public void setControlnumberid(int controlnumberid) {
		this.controlnumberid = controlnumberid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
