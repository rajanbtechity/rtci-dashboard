<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@page import="java.sql.*" import="oracle.jdbc.driver.OracleDriver"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
   

    <title>RTCI Test Automation Dashboard</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/main.css" rel="stylesheet">

    <!-- Fonts from Google Fonts -->
	<link href='http://fonts.googleapis.com/css?family=Lato:300,400,900' rel='stylesheet' type='text/css'>
	
	 <script type="text/javascript">
	function publish_flow() {

		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var Message = xmlhttp.responseText;
				alert(Message);
			}
		}
		//var key = document.getElementById("key").value;
		var flow_selected = document.getElementById("flow_list").value;

		xmlhttp.open("POST", "PublishFlow" + "?"+"flow_selected="+flow_selected , true);

		xmlhttp.send();
	}
</script> 
   
  </head>
  
 <div style="width:50%;">  
<body>

<div style="float:right; width:50%;"> 
<div id="headerwrap" >
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<h1>Pick your usecase.</h1>
					<form class="form-inline" role="form"  onclick="document.forms[0].action = 'process_req.jsp'; return true;" method="post">
					  <div class="form-group">
					  <select name="uc_list" class="form-control">
						<%
						try {	

						Connection con = null;
						//Class.forName("oracle.jdbc.xa.client.OracleXADataSource");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = yes) (ADDRESS = (PROTOCOL =TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521))(ADDRESS = (PROTOCOL=TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521)) (CONNECT_DATA = (SERVICE_NAME=TACTUAT)))", "tactuser", "tactuser");	
						String query = "select distinct uc_name,trigger_param from soapui_map";
						Statement st = con.createStatement();	
						ResultSet rs = st.executeQuery(query);
						%>
						
						<%
						while(rs.next())
						{
						String UseCaseName = rs.getString(1); 
						String TriggerParam= rs.getString(2);
						%>
						<option value="<%=TriggerParam %>"><%=UseCaseName %></option>
						<%
						}
						%>
						  </select>				
						<%
						} catch(Exception ex){
							 System.out.println(ex);
						} 
						
						%>
						

						</select>
					  </div>
					&nbsp;&nbsp;&nbsp;
					  <button type="submit" class="btn btn-warning btn-lg"  onclick="document.forms[0].action = 'process_req.jsp'; return true;">Execute</button>
					</form>					
				</div><!-- /col-lg-6 -->
				
				
			</div><!-- /row -->
		</div><!-- /container -->
	</div><!-- /headerwrap -->
</div>



</body>
</html>
