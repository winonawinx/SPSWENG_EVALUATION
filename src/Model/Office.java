package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class Office {
	
	private int ID;
	private String name;
	private boolean isArchived;
	private ArrayList<Service> services;

	public Office(int ID, String name, Boolean isArchived)
	{
		setID(ID);
		setName(name);
		setIsArchived(isArchived);
		services = new ArrayList<Service>();
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
	
	public Iterator<Service> getServices()
	{
		return services.iterator();
	}
	
	public void setID(int iD)
	{
		ID = iD;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setIsArchived(Boolean isArchived)
	{
		this.isArchived = isArchived;
	}
	
	public void setServices(ArrayList<Service> services)
	{
		this.services = services;
	}
	
	public void addService(Service service)
	{
		services.add(service);
	}

}
