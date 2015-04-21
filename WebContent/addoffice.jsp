<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="Model.DB.*" %>
<%@ page import="Model.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta name="viewport"
	content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Add Office</title>
<%
OfficeManager om = new OfficeManager();
UserManager um = new UserManager();
Iterator<Office> offices = om.getAllData();
Iterator<User> heads = um.getOfficeHead();
ArrayList<Office> officeList = new ArrayList<Office>();
while(offices.hasNext())
	officeList.add(offices.next());
offices = om.getAllData();
int size = officeList.size();
%>
<script>
	var officeNames = new Array(<%=size%>);
	var tempIndex = 0;
	<%
		for(int i = 0; i < size; i++)
		{
	%>
		officeNames[tempIndex] = '<%=officeList.get(i).getName()%>';
		tempIndex++;
	<%
		}
	%>
</script>
</head>
<body>

	<div id="errorModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Error</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <h2 id="message"></h2>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" data-dismiss="modal">Okay</button>  
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="successModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Success</h2>
                    </div>
                    <div class="modal-body my-modal-body">
                        <div class="form-group control-group">
                            <h2>Office has been added! Proceeding</h2>
                            <h2>in making the evaluation form!</h2>
                        </div>
                        <div class="form-group clearfloat"></div>
                        <div class="floatright">
                            <button type="button" class="blackbtn" data-dismiss="modal" onclick="setSuccess(true);">Okay</button>  
                        </div>
                        <div class="clearfloat"></div>
                    </div>
                </div>
            </div>
        </div>
        
	<div class="centerdiv">
		<h1 class="headerlabel">Add Office</h1>
		<div id="addoffice" class="contentdiv">
			<form action="AddOfficeServlet" method="post" id="addSubmit" onsubmit="return checkEval();">
				<div class="form-horizontal" class="row">
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Name </label> <input
							type="text" name="officeName" class="form-control" id="officeName">
					</div>
					<!--<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Acronym </label> <input
							type="text" class="form-control">
					</div>  -->
					<div class="clearfloat"></div>
					<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Head </label> 
						<select class="form-control my-select" name="heads">
							<% 
							while(heads.hasNext())
							{
								User user = heads.next();
							%>
								<option value="<%= user.getID()%>"><%= user.getUsername()%></option>
							<% 	
							}
							%>
						</select>
					</div>
					<!--<div class="form-group control-group">
						<label class="col-xs-12 addlabel control-label">Hierarchy
						</label>

						<div style="padding-left: 10px;">
							<%
							while(offices.hasNext())
							{
								Office office = offices.next();
							%>
								<div class="checkbox">
									<label class="checkbox-label">
									<input name="office<%= office.getID() %>" value="<%= office.getID() %>" type="checkbox">
									<%= office.getName() %>
									</label>
								</div>
							<%
							}
							%>
						</div>
					</div> -->
					<div class="clearfloat"></div>
					<div class="form-group floatright">
						<button type="submit" class="blackbtn formfooterbtn">Add</button>
						<a href="editoffices.jsp" type="submit"
							class="blackbtn abtn formfooterbtn">Cancel</a>
					</div>
					<div class="clearfloat"></div>
				</div>
			</form>
		</div>
	</div>
	
	<script>
		var retValue = false;
		function validateLength()
		{
			if(document.getElementById("officeName").value.length<1)
				return false;	
			else
				return true;
		}
	    
		function sameName()
		{
			for(var i = 0; i < officeNames.length; i++)
				if(officeNames[i] == document.getElementById("officeName").value)
					return true;	
			return false;
		}
		
		function setSuccess(retValue)
    	{
    		this.retValue = retValue;
    		$('#successModal').modal("hide");
    		if(this.retValue)
    		{
                var button = document.getElementById("addSubmit").ownerDocument.createElement('input');
                button.style.display = 'none';
                button.type = 'submit';
                document.getElementById("addSubmit").appendChild(button).click();
                document.getElementById("addSubmit").removeChild(button);
    		}
    	}
		function checkEval()
		{
			if(!validateLength())
			{
				$("#message").text("Please provide a name for the new office");
				$('#errorModal').modal('show');
				return false;
			}
			else if(sameName())
			{
				$("#message").text("An office with that name already exists");
				$('#errorModal').modal('show');
				return false;
			}
			else if(!retValue)
			{
				$('#successModal').modal('show');
	    		return false;
			}
			else
				return true;
		}
	</script>
</body>
</html>