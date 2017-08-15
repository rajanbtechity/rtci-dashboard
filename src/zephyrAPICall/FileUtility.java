package zephyrAPICall;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ExecuteSOAPTestCase {
	
	public static String executeSOAPUI(String loc, String filename)
	{
		try
		{
		String[] split;
		String suit_name="";
		String test_case="";
		String file_cycle=loc+"/"+filename+".txt";
		File file = new File(file_cycle);
		File soapui_exec_file=new File("C:/Users/xyz/Desktop/SOAPUI/trigger_soapui/execute_soapui.bat");
		FileOutputStream soapui_exec_write=new FileOutputStream(soapui_exec_file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(soapui_exec_write));
		FileReader cycle_fileReader = new FileReader(file);
		bw.write("cd C:/program files/SmartBear/SoapUI-5.0.0/bin");
		bw.newLine();

	
					BufferedReader bufferedReader = new BufferedReader(cycle_fileReader);
		
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						split=line.split(",");
						suit_name=split[0];
						test_case=split[1];
						System.out.println(suit_name+"#"+test_case);
						bw.write("CALL testrunner.bat -s"+suit_name+" -c\""+test_case+"\" -I -r -a -f C:/Users/xyz/Desktop/SOAPUI/report_txt C:/Users/v536139/Desktop/SOAPUI/RTCITestAutomation_Aug16_v3(local)-soapui-project.xml");
						bw.newLine();
						suit_name="";test_case="";
					}	
					bw.write("exit");
					
					bw.close();
					cycle_fileReader.close();
					executeScript();
					return "SOAPUI Execution Completed";

		}
		catch(Exception e)
		{
		e.printStackTrace();
		return "Error4";
		}
		
	}
	
	
	
	public static void executeScript() throws IOException
	{
		 Process proc=Runtime.getRuntime().exec("C:/Users/xyz/Desktop/SOAPUI/trigger_soapui/execute_soapui.bat");
		 BufferedReader read = new BufferedReader(new InputStreamReader(proc.getInputStream()));
       
           new InputStreamReader(proc.getInputStream());
           String line1 = null;
           while ((line1 = read.readLine()) != null) {System.out.println(line1); }
		}
	
	
	public static void main(String[] args)
	{
		System.out.println(executeSOAPUI("C:/Users/xyz/Desktop/Jira_Soap/zephyr","PPSHInfoTestSuite"));
		System.out.println("Rajan");
		
	}

}
