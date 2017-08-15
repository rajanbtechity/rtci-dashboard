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
try {
			String trigger_param=(String)session.getAttribute("trigger_param_serv");
			String[] split = trigger_param.split("#");
			String suit_name=split[0];
			String uc_name=split[1];
			String pattern=suit_name+"-"+uc_name+"-Failure"+".txt";
			String script_exec="/home/v536139/pythonlib/bin/python3 /home/v536139/SOAPUI/JiraIR/CreateJIRATicket.py /home/v536139/SOAPUI/report_failure/"+pattern;
			System.out.println(script_exec);
            Process proc = Runtime.getRuntime().exec(script_exec); //Whatever you want to execute
            BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
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
		StringBuffer jiraLog = new StringBuffer();
		try
		{
			FileReader fileReader = new FileReader("/home/v536139/SOAPUI/JiraIR/JiraIRLog.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
		
			String line;
			while ((line = bufferedReader.readLine()) != null) 
			{
				jiraLog.append(line);
				jiraLog.append("\n");
			}
			
			fileReader.close();
		}
		catch (IOException e)
		{
		e.printStackTrace();
		}
		
		
%>



<div style="float:left; width:55%;">
<h3>Jira IR Details</h3>
<textarea rows="8" cols="85" name="description"><%= jiraLog %></textarea>
</div>






</body>
</html>
