<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    


<%@page import="java.io.BufferedReader" import="java.io.IOException" import="java.io.InputStreamReader" import="java.io.*" %>    

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
</head>
<body>



<%
String trigger_param=request.getParameter("uc_list");
String[] split = trigger_param.split("#");
String suit_name=split[0];
String uc_name=split[1];
 
try {
			String script_exec="/home/v536139/SOAPUI/soapui_dashboard_exec.sh" + " " + suit_name + " "+uc_name;

            Process proc = Runtime.getRuntime().exec(script_exec); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            while (read.ready()) {
                System.out.println(read.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
        e.printStackTrace();
        }

%>

<%
		StringBuffer stringCurrent = new StringBuffer();
		try
		{
		File dir = new File("/home/v536139/SOAPUI/report_txt");
		//File dir = new File("C:/Users/v536139/Desktop/SOAPUI/report_txt");
		File[] files = dir.listFiles();
		String pattern = suit_name + '-' + uc_name;

		int line_num = 0;
		
		for(int i=0; i<files.length; i++)
		{
		    if(files[i].getName().startsWith(pattern))
		    {
					try{
					FileReader fileReader = new FileReader(files[i]);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
		
					String line;
					while ((line = bufferedReader.readLine()) != null && line_num<6) {
						stringCurrent.append(line);
						stringCurrent.append("\n");
						line_num++;
					}
					fileReader.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
			line_num = 0;
			}

		}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
%>


<%
		StringBuffer stringLast = new StringBuffer();
		try
		{
		File dir = new File("/home/v536139/SOAPUI/report_txt/BACKUP");
		//File dir = new File("C:/Users/v536139/Desktop/SOAPUI/report_txt/BACKUP");
		File[] files = dir.listFiles();
		String pattern = suit_name + '-' + uc_name;

		int line_num = 0;
		
		for(int i=0; i<files.length; i++)
		{
		    if(files[i].getName().startsWith(pattern))
		    {
					try{
					FileReader fileReader = new FileReader(files[i]);
					BufferedReader bufferedReader = new BufferedReader(fileReader);
		
					String line;
					while ((line = bufferedReader.readLine()) != null && line_num<6) {
						stringLast.append(line);
						stringLast.append("\n");
						line_num++;
					}
					fileReader.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
			line_num = 0;
			}

		}
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
%>



<div style="float:left; width:55%;">
<h3>Current Execution Details</h3>
<textarea rows="10" cols="55" name="description"><%= stringCurrent %></textarea>
</div>
<div style="float:right; width:45%;">
<h3>Last Execution Details</h3>
<textarea rows="10" cols="55" name="description"><%= stringLast %></textarea>
</div>


<% 
session.setAttribute("trigger_param_serv",trigger_param);
%>
<br>
<br>
<table>
<tr>
<td>
<form class="form-inline" role="form" action="GetReport" method="post">

<button type="submit" class="btn btn-warning btn-lg"  action="GetReport" method="post">Get Report</button>
</form>
</td>

<td>
<form class="form-inline" role="form" action="JiraIRDtls.jsp" method="post">
<button type="submit" class="btn btn-warning btn-lg"  onclick="document.forms[0].action = 'JiraIRDtls.jsp'; return true;">Log IR</button>
</form>
</td>

<td>
<form class="form-inline" role="form" action="publishFlow.jsp" method="post">
<button type="submit" class="btn btn-warning btn-lg"  onclick="document.forms[0].action = 'publishFlow.jsp'; return true;">Publish Flow</button>
</form>
</td>
</tr>
</table>

<br>
<br>

<table>
<tr>
<td>
<form class="form-inline" role="form" action="SoapuiDashboard.jsp" method="post">
<button type="submit" class="btn btn-warning btn-lg"  onclick="document.forms[0].action = 'SoapuiDashboard.jsp'; return true;">Back</button>
</form>
</td>
</tr>
</table>



</body>
</html>
