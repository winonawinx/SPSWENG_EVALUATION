package Model;

import java.sql.Time;
import java.sql.Timestamp;

public class ControlNumber {

	private int id;
	private String controlnumber;
	private int form;
	private Timestamp expirationTime;
	private Boolean status;
	
	public ControlNumber(int id, String controlnumber, int form, Timestamp expirationTime, Boolean status)
	{
		setId(id);
		setForm(form);
		setExpirationTime(expirationTime);
		setStatus(status);
		setControlNumber(controlnumber);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getControlnumber() {
		return controlnumber;
	}

	public void setControlnumber(String controlnumber) {
		this.controlnumber = controlnumber;
	}

	public Boolean getStatus()
	{
		return status;
	}
	
	public String getControlNumber()
	{
		return controlnumber;
	}

	public int getForm()
	{
		return form;
	}
	
	
	public Timestamp getExpirationTime()
	{
		return expirationTime;
	}

	public void setStatus(Boolean status)
	{
		this.status = status;
	}
	
	public void setForm(int form)
	{
		this.form = form;
	}
	
	public void setControlNumber(String controlnumber)
	{
		this.controlnumber = controlnumber;
	}


	public void setExpirationTime(Timestamp expirationTime)
	{
		this.expirationTime = expirationTime;
	}

}
