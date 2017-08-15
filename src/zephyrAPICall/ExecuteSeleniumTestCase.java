package zephyrAPICall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ExecuteSeleniumTestCase {
	
	public static String executeSelenium(String loc, String filename)
    {
           try
           {
           String[] split;
           String suit_name="";
           String test_case="";
           String file_cycle=loc+"/"+filename+".txt";
           File file = new File(file_cycle);
           File soapui_exec_file=new File("C:/Users/xyz/workspace/RadarSelenium/execute_selenium.bat");
           FileOutputStream soapui_exec_write=new FileOutputStream(soapui_exec_file);
           BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(soapui_exec_write));
           FileReader cycle_fileReader = new FileReader(file);
           //bw.write("cd C:/program files/SmartBear/SoapUI-5.0.0/bin");
           bw.write(":: Run JUnit test and report Issue Requests to JIRA");
           bw.newLine();
           bw.write("echo off");
           bw.newLine();
           bw.write("echo Compiling test classes...");
           bw.newLine();
           bw.write("javac -cp lib\\* -d bin C:\\Users\\xyz\\workspace\\RadarSelenium\\src\\*.java");
           bw.newLine();
           bw.write(":: echo Running JUnit tests...");
           bw.newLine();
    
                               BufferedReader bufferedReader = new BufferedReader(cycle_fileReader);
                               bw.write("java -cp C:\\Users\\xyz\\workspace\\RadarSelenium\\bin HelloWorld");
                               bw.newLine();
           
                               String line;
                               while ((line = bufferedReader.readLine()) != null) {
                                      split=line.split(",");
                                      test_case=split[0];
                                      System.out.println(suit_name+"#"+test_case);
                                      bw.write("java -cp \"C:\\Users\\xyz\\workspace\\RadarSelenium\\bin;C:\\Program Files (x86)\\Eclipse_Kepler_4.3\\plugins\\org.junit_4.11.0.v201303080030\\junit.jar;C:\\Program Files (x86)\\Eclipse_Kepler_4.3\\plugins\\org.hamcrest.core_1.3.0.v201303031735.jar;C:\\Users\\v536139\\workspace\\RadarSelenium\\lib\\client-combined-3.0.0-beta3-nodeps.jar;C:\\Users\\v536139\\workspace\\RadarSelenium\\lib\\hamcrest-core-1.3.jar;C:\\Users\\v536139\\workspace\\RadarSelenium\\lib\\selenium-server-standalone-2.47.1.jar;C:\\Users\\v536139\\workspace\\RadarSelenium\\lib\\selenium-server-standalone-3.0.0-beta3 (1).jar\" -Dwebdriver.chrome.driver=C:\\Users\\v536139\\workspace\\RadarSelenium\\lib\\chromedriver.exe org.junit.runner.JUnitCore "+test_case);

                                      bw.newLine();
                                      suit_name="";test_case="";
                               }      
                               bw.write("exit");
                               
                               bw.close();
                               cycle_fileReader.close();
                               executeScript();
                               return "Selenium Execution Completed";

           }
           catch(Exception e)
           {
           e.printStackTrace();
           return "Error4";
           }
           
    }
    
    
    
    public static void executeScript() throws IOException
    {
           Process proc=Runtime.getRuntime().exec("C:/Users/xyz/workspace/RadarSelenium/execute_selenium.bat");
           BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
    
        new InputStreamReader(proc.getInputStream());
        String line1 = null;
        while ((line1 = read.readLine()) != null) 
        { 
              //comment below line after u r done with testing
              System.out.println(line1);
        }
           }
    
    
    public static void main(String[] args)
    {
           System.out.println(executeSelenium("C:/Users/xyz/workspace/RadarSelenium","selenium"));
           System.out.println("Rajan Exit");
           
    }

}
