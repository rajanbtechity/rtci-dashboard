package zephyrAPICall;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZephyrUtility {
	public static String projectID="";

	
	public static String getProjectID(String json_rsp, String project) throws JSONException
	{
		JSONObject jsonobj=new JSONObject(json_rsp);
		JSONArray jsonarry=new JSONArray();
		jsonarry=jsonobj.optJSONArray("options");
		
		int arry_len=jsonarry.length();
		
		for(int i=0;i<arry_len-1;i++)
		{
			if(((JSONObject)jsonarry.get(i)).optString("label").equalsIgnoreCase(project))
			projectID=((JSONObject)jsonarry.get(i)).optString("value");
		}
	return projectID;
		
	}
	
	
	public static String getExecutionParam(String json_rsp, String cycle) throws JSONException
	{
		String versionId="";
		String cycle_name="";
		String cycle_details="";
		String expand="";
		JSONObject json_cylce_dtls;
		
		JSONObject jsonobj=new JSONObject(json_rsp);
		//System.out.println(jsonobj.optJSONArray("-1").optJSONObject(0));
		jsonobj=jsonobj.optJSONArray("-1").optJSONObject(0);
		Iterator<?> keys = jsonobj.keys();

		while( keys.hasNext() ) {
		    String key = (String)keys.next();
		    if ( jsonobj.get(key) instanceof JSONObject ) {
		    	
		    	cycle_details=(jsonobj.get(key).toString());
		    	json_cylce_dtls=new JSONObject(cycle_details);
		    	versionId=json_cylce_dtls.optString("versionId");
		    	cycle_name=json_cylce_dtls.optString("name");
		    	expand=json_cylce_dtls.optString("expand");
		    	
		    	if(cycle_name.equals(cycle))
		    		{
		    		return "?cycleId="+key+"&action=expand";
		    		}

		    }
		}
		return "No Match Found";
	}
	
	//ArrayList<String>
	
	public static ArrayList<String> getCycleTestCase(String json_rsp) throws JSONException
	{
		System.out.println(json_rsp);
		ArrayList<String> test_case = new ArrayList<String>();
		String issueId="";
		String issueKey="";
		String summary="";
		String id="";
		JSONArray jsonarry;
		
		JSONObject jsonobj=new JSONObject(json_rsp);
		jsonarry=jsonobj.optJSONArray("executions");
		
		for(int i=0;i<=jsonarry.length()-1;i++)
		{
			id=((JSONObject) jsonarry.get(i)).optString("id");
			issueId=((JSONObject) jsonarry.get(i)).optString("issueId");
			issueKey=((JSONObject) jsonarry.get(i)).optString("issueKey");
			summary=((JSONObject) jsonarry.get(i)).optString("summary");
			test_case.add(summary+","+id+","+issueKey+","+issueId);
		}
		//System.out.println(jsonarry);
		return test_case;
	}
	
	
	
	/*
	public static void main(String[] args) throws JSONException
	{
		String json_rsp="{\"status\":{\"1\":{\"id\":1,\"color\":\"#75B000\",\"description\":\"Test was executed and passed successfully.\",\"name\":\"PASS\"},\"2\":{\"id\":2,\"color\":\"#CC3300\",\"description\":\"Test was executed and failed.\",\"name\":\"FAIL\"},\"3\":{\"id\":3,\"color\":\"#F2B000\",\"description\":\"Test execution is a work-in-progress.\",\"name\":\"WIP\"},\"4\":{\"id\":4,\"color\":\"#6693B0\",\"description\":\"The test execution of this test was blocked for some reason.\",\"name\":\"BLOCKED\"},\"5\":{\"id\":5,\"color\":\"#ff9966\",\"description\":\"When test case is not valid\",\"name\":\"NA\"},\"6\":{\"id\":6,\"color\":\"#6600cc\",\"description\":\"No applicable Data\",\"name\":\"NA - DATA \"},\"7\":{\"id\":7,\"color\":\"#ff00cc\",\"description\":\"Out of Scope\",\"name\":\"NA - SCOPE\"},\"-1\":{\"id\":-1,\"color\":\"#A0A0A0\",\"description\":\"The test has not yet been executed.\",\"name\":\"UNEXECUTED\"}},\"executions\":[{\"id\":436522,\"orderId\":414380,\"executionStatus\":\"2\",\"executedOn\":\"Friday 3:29 AM\",\"executedBy\":\"V682532\",\"executedByDisplay\":\"Reddy, Nishitha\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":12914,\"cycleName\":\"PPSHInfoTestSuite\",\"versionId\":-1,\"versionName\":\"Unscheduled\",\"projectId\":10800,\"createdBy\":\"v682532\",\"modifiedBy\":\"v682532\",\"issueId\":920605,\"issueKey\":\"EQHV-55\",\"summary\":\"PPSH_NWG\",\"label\":\"\",\"component\":\"\",\"projectKey\":\"EQHV\",\"executionDefectCount\":0,\"stepDefectCount\":0,\"totalDefectCount\":0},{\"id\":422759,\"orderId\":401452,\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":12914,\"cycleName\":\"PPSHInfoTestSuite\",\"versionId\":-1,\"versionName\":\"Unscheduled\",\"projectId\":10800,\"createdBy\":\"v682532\",\"modifiedBy\":\"v682532\",\"assignedTo\":\"Reddy, Nishitha\",\"assignedToDisplay\":\"Reddy, Nishitha\",\"assignedToUserName\":\"V682532\",\"assigneeType\":\"assignee\",\"issueId\":899907,\"issueKey\":\"EQHV-51\",\"summary\":\"OMNI_EQUIPACK\",\"issueDescription\":\"<p>Testing equipment acknowledgement usecase.\",\"label\":\"\",\"component\":\"\",\"projectKey\":\"EQHV\",\"executionDefectCount\":0,\"stepDefectCount\":0,\"totalDefectCount\":0},{\"id\":422758,\"orderId\":401451,\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":12914,\"cycleName\":\"PPSHInfoTestSuite\",\"versionId\":-1,\"versionName\":\"Unscheduled\",\"projectId\":10800,\"createdBy\":\"v682532\",\"modifiedBy\":\"v682532\",\"assignedTo\":\"Reddy, Nishitha\",\"assignedToDisplay\":\"Reddy, Nishitha\",\"assignedToUserName\":\"V682532\",\"assigneeType\":\"assignee\",\"issueId\":899906,\"issueKey\":\"EQHV-50\",\"summary\":\"OMNI_REWARDS\",\"issueDescription\":\"<p>Testing Omi rewards usecase\",\"label\":\"\",\"component\":\"\",\"projectKey\":\"EQHV\",\"executionDefectCount\":0,\"stepDefectCount\":0,\"totalDefectCount\":0},{\"id\":422757,\"orderId\":401450,\"executionStatus\":\"-1\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":12914,\"cycleName\":\"PPSHInfoTestSuite\",\"versionId\":-1,\"versionName\":\"Unscheduled\",\"projectId\":10800,\"createdBy\":\"v682532\",\"modifiedBy\":\"v682532\",\"assignedTo\":\"Reddy, Nishitha\",\"assignedToDisplay\":\"Reddy, Nishitha\",\"assignedToUserName\":\"V682532\",\"assigneeType\":\"assignee\",\"issueId\":899905,\"issueKey\":\"EQHV-49\",\"summary\":\"PPSH_GENERIC_WAVE\",\"issueDescription\":\"<p>Testing WAVE Usecase.\",\"label\":\"\",\"component\":\"\",\"projectKey\":\"EQHV\",\"executionDefectCount\":0,\"stepDefectCount\":0,\"totalDefectCount\":0},{\"id\":422756,\"orderId\":401449,\"executionStatus\":\"1\",\"executedOn\":\"Friday 3:29 AM\",\"executedBy\":\"V682532\",\"executedByDisplay\":\"Reddy, Nishitha\",\"comment\":\"\",\"htmlComment\":\"\",\"cycleId\":12914,\"cycleName\":\"PPSHInfoTestSuite\",\"versionId\":-1,\"versionName\":\"Unscheduled\",\"projectId\":10800,\"createdBy\":\"v682532\",\"modifiedBy\":\"v536139\",\"assignedTo\":\"Reddy, Nishitha\",\"assignedToDisplay\":\"Reddy, Nishitha\",\"assignedToUserName\":\"V682532\",\"assigneeType\":\"assignee\",\"issueId\":899699,\"issueKey\":\"EQHV-48\",\"summary\":\"PPSH_UBB\",\"issueDescription\":\"<p>Testing UBB Usecase\",\"label\":\"\",\"component\":\"\",\"projectKey\":\"EQHV\",\"executionDefectCount\":0,\"stepDefectCount\":0,\"totalDefectCount\":0}],\"currentlySelectedExecutionId\":\"\",\"recordsCount\":5}";
		//System.out.println(getExecutionParam(json_rsp,"PPSHInfoTestSuite"));
		//System.out.println(getProjectID(json_rsp,"PROJECT_NAME"));
		//ArrayList<String> list=new ArrayList<String>(){{add("PPSH_NWG,EQHV-55,920605");
		//add("OMNI_EQUIPACK,EQHV-51,899907");}};
		

		System.out.println(getCycleTestCase(json_rsp));
		//System.out.println(writeCycleTestCase(list,"PPSHInfoTestSuite"));
	}
	*/
}
