package zephyrAPICall;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import zephyrAPICall.ZephyrUtility;


public class ZAPIHandler {
	
 
    /** URLS */
    private static final String BASE_URL = "https://jira.xyz.com";
    private String ZAPI_URL="";
    
    ZAPIHandler(String resource_url)
    {
    	ZAPI_URL=BASE_URL + resource_url;
    }
    
    //private static final String ZAPI_URL = BASE_URL + "/rest/zapi/latest/";
 
    /** HTTP Proxy details */
    
 
    /** JIRA credentials: format "username:password" or "" for none. */
    private static final String CREDENTIALS = "username:password";
   
    // ================================================================================
    // HTTP request methods
    // ================================================================================
 
    /**
     * Send GET request to the specified URL
     * 
     * @param url
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONObject httpGetJSONObject(final String url) throws IOException, JSONException {
        return new JSONObject(httpGetJSONString(url));
    }
 
    /**
     * Send GET request to the specified URL
     * 
     * @param url
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONArray httpGetJSONArray(final String url) throws IOException, JSONException {
        return new JSONArray(httpGetJSONString(url));
    }
 
    /**
     * Get a string from a url.
     * 
     * @param url
     *            the URL to perform the GET method on
     * @return a String representing the body of the http response
     * @throws IOException
     */
    private static String httpGetJSONString(final String url) throws IOException {
        final HttpURLConnection httpCon = createHttpCon(url, "GET");
        final BufferedReader br =
                new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
 
        final StringBuffer httpResponse = new StringBuffer();
        String line = "";
        while (null != (line = br.readLine())) {
            httpResponse.append(line);
        }
 
        return httpResponse.toString();
    }
    
    private static String httpGetJSONString(final String url,final String param) throws IOException {
        HttpURLConnection httpCon =createHttpCon(url,"GET");
        OutputStreamWriter wr = new OutputStreamWriter(httpCon.getOutputStream());

		wr.write(param);
		wr.close();
        System.out.println(param);
        System.out.println(httpCon);
        final BufferedReader br =new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
System.out.println(br); 
        final StringBuffer httpResponse = new StringBuffer();
        String line = "";
        System.out.println(httpResponse);
        while (null != (line = br.readLine())) {
            httpResponse.append(line);
        }
 
        return httpResponse.toString();
    }
 
    /**
     * Send a request with JSON content with the specified method
     * 
     * @param url
     *            - the URL to send the request to
     * @param obj
     *            - the JSON content to send
     * @param method
     *            - e.g. PUT
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONObject sendRequest(final String url, final JSONObject obj,final String method) throws IOException {
        final HttpURLConnection httpCon = createHttpCon(url, method);
        final StringBuffer result = new StringBuffer();
 
        if (null != obj) {
            final OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
            out.write(obj.toString());
            out.close();
        }
 
        final BufferedReader rd =
                new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
        
        String line = "";
        while (null != (line = rd.readLine())) {
            result.append(line);
        }
        
       
			try {
				return new JSONObject(result.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return obj;
			}
        
    }
 
    /**
     * Send PUT request to the specified URL
     * 
     * @param url
     *            - the URL to send the request to
     * @param obj
     *            - the JSON content to send
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONObject put(final String url, final JSONObject obj) throws IOException, JSONException {
        return sendRequest(url, obj, "PUT");
    }
 
    /**
     * Send POST request to the specified URL
     * 
     * @param url
     *            - the URL to send the request to
     * @param obj
     *            - the JSON content to send
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONObject post(final String url, final JSONObject obj) throws IOException, JSONException {
        return sendRequest(url, obj, "POST");
    }
 
    /**
     * Send DELETE request to the specified URL
     * 
     * @param url
     *            - the URL to send the request to
     * @throws IOException
     * @throws JSONException 
     */
    private static JSONObject delete(final String url) throws IOException, JSONException {
        return sendRequest(url, null, "DELETE");
    }
 
    /**
     * Return a HttpURLConnection object for the specified URL and request method
     * 
     * @param url
     *            the URL to connect to
     * @param method
     *            - e.g. GET
     */
    private static HttpURLConnection createHttpCon(final String url, final String method)
            throws IOException {
        final HttpURLConnection httpCon;

            httpCon = (HttpURLConnection) new URL(url).openConnection();
            
        httpCon.setDoInput(true);
        httpCon.setDoOutput(true);
        httpCon.setRequestMethod(method);
 
        if (!CREDENTIALS.isEmpty()) {
            final String encoding = new Base64().encodeToString(CREDENTIALS.getBytes());
            httpCon.setRequestProperty("Authorization", "Basic " + encoding);
        }
 
        httpCon.setRequestProperty("Content-type", "application/json");
 
        return httpCon;
    }
    
    
	public static void main(String[] args) throws IOException, JSONException {
		// TODO Auto-generated method stub
		try{
	
		String projectID="";
		String cycle_param="";
		String cycle_exe_param="";
		String Jira_ir_param="";
		String test_case_dtls;
		String[] test_case_split;
		String testcaseid="";
		String testcasestatus="";
		String status_upd_param="";
		ArrayList<String> testCase_list = new ArrayList<String>();
		ArrayList<String>testcase_exec_list=new ArrayList<String>();
		String cycle_name="PPSHInfoTestSuite";
				
		//Fetch project list
		ZAPIHandler projlist= new ZAPIHandler("/rest/zapi/latest/util/project-list");
		
		

		//jsonobj=httpGetJSONString(obj1.ZAPI_URL);
		projectID=ZephyrUtility.getProjectID(httpGetJSONString(projlist.ZAPI_URL),"EQHV_RTCI");
		System.out.println("ProjectId:"+projectID);

		cycle_param="?projectId="+projectID;
		
		//fetch cycle list details
		ZAPIHandler cyclelist=new ZAPIHandler("/rest/zapi/latest/cycle"+cycle_param);
		System.out.println("Cycle List"+httpGetJSONString(cyclelist.ZAPI_URL));
		cycle_exe_param=ZephyrUtility.getExecutionParam(httpGetJSONString(cyclelist.ZAPI_URL),cycle_name);
		
		System.out.println("cycle_exe_param:"+cycle_exe_param);
		
		//fecth cycle list execution details
		ZAPIHandler cycle_param_obj=new ZAPIHandler("/rest/zapi/latest/execution"+cycle_exe_param);
		testCase_list=ZephyrUtility.getCycleTestCase(httpGetJSONString(cycle_param_obj.ZAPI_URL));
		System.out.println(testCase_list);
		
		
		System.out.println("Test Cycle Details Written in file:"+FileUtility.writeCycleTestCase(testCase_list,cycle_name));
		
		//System.out.println("Executing Selenium Test case");
		//ExecuteSeleniumTestCase.executeSelenium("C:/Users/v536139/Desktop/Jira_Soap/zephyr",cycle_name);
		
		ExecuteSOAPTestCase.executeSOAPUI("C:/Users/v536139/Desktop/Jira_Soap/zephyr",cycle_name);
		
		System.out.println("Updating Execution Status in zephyr using put");
		System.out.println(testCase_list);
		testcase_exec_list=FileUtility.readTestCaseReport("C:/Users/v536139/Desktop/Jira_Soap/zephyr",cycle_name);
		//testcase_exec_list=FileUtility.readTestCaseReport("C:/Users/v536139/Desktop/Jira_Soap/zephyr",cycle_name,"Selenium");

		System.out.println("Test Case Execution List:"+testcase_exec_list);
		System.out.println("Test Execution List Size:"+testcase_exec_list.size());
	
		for (int i=0;i<testcase_exec_list.size();i++)
		{
			
			test_case_dtls=testcase_exec_list.get(i);
			
			test_case_split=test_case_dtls.split("#");
			testcaseid=test_case_split[1];
			testcasestatus=test_case_split[2];
			status_upd_param="{\"status\": \""+testcasestatus+"\"}";
			JSONObject json_exec_put=new JSONObject(status_upd_param);
			
		ZAPIHandler exec_upd_obj=new ZAPIHandler("/rest/zapi/latest/execution/"+testcaseid+"/execute");
		System.out.println("url:"+exec_upd_obj.ZAPI_URL+" Param"+json_exec_put);
		System.out.println(put(exec_upd_obj.ZAPI_URL,json_exec_put));
		
		if(testcasestatus.equals("2"))
		{
			ZAPIHandler jira_ir_obj=new ZAPIHandler("/rest/api/2/issue");
			Jira_ir_param="{\"fields\": {\"project\": {\"key\": \"EQHV\"},\"issuetype\": {\"id\": \"1\",\"name\": \"Bug\"},\"summary\":\""+test_case_split[0]+
					"\",\"description\":\""+test_case_split[3]+"\"}}";
			JSONObject json_jira_param=new JSONObject(Jira_ir_param);
			System.out.println("url:"+jira_ir_obj.ZAPI_URL+" Param"+json_jira_param);
			System.out.println("Creating IR"+post(jira_ir_obj.ZAPI_URL,json_jira_param));
		}
		
		}
		
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}

	}

}
