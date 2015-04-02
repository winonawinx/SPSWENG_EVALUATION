package Model;

public class Service {

	private int ID;
	private String name;
	private int officeID;
	private Boolean isArchived;
	
	public Service(int ID, String name, int officeID, Boolean isArchived)
	{
		setID(ID);
		setName(name);
		setOfficeID(officeID);
		setIsArchived(isArchived);
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Boolean getIsArchived()
	{
		return isArchived;
	}
	
	public int getOfficeID()
	{
		return officeID;
	}
	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setOfficeID(int officeID)
	{
		this.officeID = officeID;
	}
	
	
}
