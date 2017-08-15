<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    


<%@page import="java.io.BufferedReader" import="java.io.IOException" import="java.io.InputStreamReader" import="java.io.*" import="java.sql.*" import="oracle.jdbc.driver.OracleDriver"%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test Results</title>
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
<body>


<div style="float:left; width:45%;"> 
<div id="headerwrap">
		<div class="container">
			<div class="row">
				<div class="col-lg-6">
					<h1>Select Your Version.</h1>
					<form class="form-inline" role="form"  onSubmit="publish_flow()" method="post">
					  <div class="form-group">

					  <select name="flow_list" id="flow_list" class="form-control">
						<%
						try {	

						Connection con = null;
						//Class.forName("oracle.jdbc.xa.client.OracleXADataSource");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = yes) (ADDRESS = (PROTOCOL =TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521))(ADDRESS = (PROTOCOL=TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521)) (CONNECT_DATA = (SERVICE_NAME=TACTUAT)))", "tactuser", "tactuser");	
						String query ="select scenario_name,scenario_id from (select scenario_name,scenario_id,rank() over(partition by substr(scenario_name,1,10) order by scenario_id desc) rank from scenario where (template_name like 'PPSHInfoReq%' or template_name like '%LOFOnDemand%') and scenario_status='1' order by scenario_id desc) where rank between 1 and 10 order by substr(scenario_name,1,10),scenario_id desc";
						Statement st = con.createStatement();	
						ResultSet rs = st.executeQuery(query);
						%>
						
						<%
						while(rs.next())
						{
						String scenario_name = rs.getString(1); 
						%>
						<option value="<%=scenario_name %>"><%=scenario_name %></option>
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
					  <button type="submit" class="btn btn-warning btn-lg" onSubmit="publish_flow()" >Publish</button>
					</form>
					<br>
					<br>
					<form class="form-inline" role="form" action="SoapuiDashboard.jsp" method="post">
					<button type="submit" class="btn btn-warning btn-lg"  onclick="document.forms[0].action = 'SoapuiDashboard.jsp'; return true;">Home</button>
					</form>					
				</div><!-- /col-lg-6 -->
				
				
			</div><!-- /row -->
		</div><!-- /container -->
	</div><!-- /headerwrap -->
</div>







</body>
</html>
