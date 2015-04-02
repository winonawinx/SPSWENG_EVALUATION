package Model;

public class User
{

	private int ID;
	private String username;
	private String title;
	private String email;
	private String password;
	private String type;
	private Boolean isArchived;
	
	public User(int ID, String username, String title, String email, String password, String type, Boolean isArchived)
	{
		setID(ID);
		setUsername(username);
		setTitle(title);
		setEmail(email);
		setPassword(password);
		setType(type);	
		setIsArchived(isArchived);
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public String getType()
	{
		return type;
	}
	
	public Boolean getIsArchived()
	{
		return isArchived;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	
	
}
