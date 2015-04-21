<%@page import="java.util.ArrayList"%>
<%@page import="Model.Office"%>
<%@page import="java.util.Iterator"%>
<%@page import="Controller.Controller"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
<link href="css/style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap.min.css" rel="stylesheet">
<script src="jquery-2.1.3.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<title>Edit Offices</title>
<script>
	<%
		Controller con = new Controller();
		Iterator<Office> offices = con.getAllOffices();
	%>
	var currId;
	var currName;
	function showOfficeModal() {
		$('#editOfficeModal').modal('show');
		document.getElementById("ofcName").value = currName;
		return false;
	}
	
	function press(element) {
		currId = element.id;
		currName = element.value;
	}
	
	function editOffice() {
		var editted = document.getElementById("ofcName").value;

		//document.getElementById('ofcName').value = editted;
		//document.getElementById(currId).value = editted;
		//$('#' + currId).text(editted);
		$.ajax({
			type: 'POST',
			url : 'EditOfficeModalServlet',
			data : {"officeId": currId,
					"officeName": editted},
			error : function(data) {
				alert('error');
			},
			success : function(data) {
				$('#editOfficeModal').modal('hide');
				$('#message').text('Successfully changed office name to ' + editted);
				$('#successModal').modal('show');
			}
		});
		<%
			offices = con.getAllOffices();
		%>
	}
	
	function reload()
	{
		location.reload();
	}
	
	function deleteOffice() {
		$.ajax({
			type: 'POST',
			url : 'DeleteOfficeModalServlet',
			data : {"officeId": currId},
			error : function(data) {
				alert('error');
			},
			success : function(data) {
				alert('successfully deleted!');
				location.reload();
			}
		});
		
	}
	
	function editForm() {
 		document.getElementById("mhidden").value = currName;
	}
	
	
</script>
</head>
<body>
	<!-- Modal HTML -->
	<form action="ModifyFormServlet" method="post">
		<div id="editOfficeModal" class="modal fade my-modal">
			<div class="modal-dialog my-modal-dialog">
				<div class="modal-content my-modal-content">
					<div class="modal-header my-modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h2 class="modal-title">Edit Office</h2>
					</div>
					<div class="modal-body my-modal-body">
						<div class="form-group control-group">
							<label class="col-xs-12 addlabel control-label">Name </label> 
							<input type="text" class="form-control" id="ofcName" name="ofcName">
						</div>
						<div class="form-group clearfloat"></div>
						<div class="floatright">
							<button type="button" class="blackbtn" onClick="editOffice()">Save</button>
							<button 
								type="submit"
								id="editformbtn" 
								name="editformbtn" 
								value="Edit Form"
								class="blackbtn abtn" 
								onclick="editForm();">
									EditForm
							</button>
							<button type="button" class="blackbtn" data-dismiss="modal">Cancel</button>
							<button type="button" id="removeofficebtn" class="blackbtn"
								onClick="deleteOffice()">Delete</button>
							<input type="hidden" id="mhidden" name="mhidden">
						</div>
						<div class="clearfloat"></div>
					</div>
				</div>
			</div>
		</div>
	</form>

	<div id="successModal" class="modal fade my-modal">
            <div class="modal-dialog my-modal-dialog">
                <div class="modal-content my-modal-content">
                    <div class="modal-header my-modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h2 class="modal-title">Success</h2>
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
        
		<div class="centerdiv">
			<form action = "EditOfficesBackServlet" method = "post">
			<h1 class="headerlabel">Edit Offices
				<div class="floatright headermenu">
					<button type="submit" class="blackbtn abtn headermenubtn" id = "BackBtn" name = "BackBtn" value = "Back">Back</button>
				</div>
			</h1>
			</form>
			
			<div class="contentdiv">
				<div class="row">
					<form action="" method="" onSubmit="return showOfficeModal() ;">
					<%
						while (offices.hasNext()) {
							Office office = offices.next();
					%>
					<div class="col-xs-4">
						<button type="submit" class="blackbtn viewabtn view"
							name="<%=office.getName()%>" id="<%=office.getID()%>"
							value="<%=office.getName()%>" onclick="press(this);">
							<%=office.getName()%>
						</button>
					</div>
					<%
						}
					%>
					</form>
					<div class="col-xs-4">
						<button type="button" class="blackbtn viewabtn view" onclick="location.href = 'addoffice.jsp'">
							Add Office</button>
					</div>
				</div>
			</div>
		</div>
		<input type="hidden" name="click" id="click">
	

</body>
</html>