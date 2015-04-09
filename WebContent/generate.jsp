<%@page import="java.util.Iterator"%>
<%@page import="Model.DB.OfficeManager"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import= "Controller.*" %>
<%@ page import= "Model.*" %>
<%@ page import= "java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
        <meta name="viewport" content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1" />
        <link href="css/style.css" rel="stylesheet" type="text/css">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="jquery-2.1.3.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <title>Generate Control Number</title>
        <script type="text/javascript">
			var offices = [];
			var services = [];
			var serviceIds = [];
			
			function setOffice(index, value)
			{
				offices[index] = value;
			}
			
			function initArray(index) 
			{
				services[index] = [];
				serviceIds[index] = [];
			}
			
			function setService(index, office, value, id) 
			{
				services[office][index] = value;
				serviceIds[office][index] = id;
			}
			
			function emptyOptions( box ) 
			{
				while ( box.options.length ) box.options[0] = null;
			}
			
			function addOption(box, value, id) 
			{
				box.options[box.options.length] = new Option(value, id);
			}
			
			function updateServices(office, box)
			{
				emptyOptions(box);
				for(var i = 0; i < services[office].length ; i++)
				{
					addOption(box, services[office][i], serviceIds[office][i]);
				}	
			}
		</script>
    </head>
    <body>
    	<%
		OfficeManager of = new OfficeManager();
		Iterator<Service> services;
		Iterator<Office> iterator = of.getAllData();
		ArrayList<Office> offices = new ArrayList<Office>();
		while(iterator.hasNext())
		{
			offices.add((Office)iterator.next());
		}
		%>  
		<form action="generateControlNumServlet" method="post">
        <div class="centerlogindiv">
            <div class="centercard">
                <p><h1 class="label">Generate Control Number</h1></p>
                <div id="generateselect" class="form-horizontal row" style="margin-top:40px;">
                    <div class="form-group">
                        <label class="col-xs-12 addlabel control-label" >Office </label>
                        <select class="form-control" name = "offices" onchange="updateServices(this.selectedIndex, document.getElementById('services'));">
                            <% 
                            for(int i = 0; i < offices.size(); i++)
			   				{ 
			   				%>
			   					<script> initArray(<%= i %>); </script>
								<option value="<%= offices.get(i).getID() %>"> <%= offices.get(i).getName() %> </option>
								<script>setOffice(<%= i%>, "<%= offices.get(i).getName() %>"); </script>
								<% 
								services = offices.get(i).getServices();
								int j = 0;
								while(services.hasNext())
						   		{ 
						   			Service service = services.next();
						   		%>
									<script> setService(<%= j%>, <%= i%>, '<%= service.getName() %>', <%= service.getID() %>); </script>
								<% 
									j++;
						   		} 
						   		%>
									
							<% 
							};
							%>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="col-xs-12 addlabel control-label">Service </label>
                        <select class="form-control" id="services" name="services">
							<%
							services = offices.get(0).getServices();
							while(services.hasNext()) 
							{
								Service service = services.next();
								System.out.println(service.getName());
							%>
								<option value="<%= service.getID()%>"> <%= service.getName() %> </option>
							<% 
							} 
							%>
						</select> 
                    </div>
                </div>
                <div class="form-group floatright">
                    <button type="submit" class="blackbtn blackbtn-alignright">Generate</button>
                </div>
            </div>
        </div>
        </form>
    </body>
</html>

</body>
</html>