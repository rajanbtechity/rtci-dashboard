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



public class JiraIR {
	
 
    /** URLS */
    private static final String BASE_URL = "https://onejira.verizon.com";
    private String ZAPI_URL="";
    
    JiraIR(String resource_url)
    {
    	ZAPI_URL=BASE_URL + resource_url;
    }
    
    
 
    /** JIRA credentials: format "username:password" or "" for none. */
    private static final String CREDENTIALS = "vzid:password";


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
 
   
    private static JSONObject post(final String url, final JSONObject obj) throws IOException, JSONException {
        return sendRequest(url, obj, "POST");
    }
 

   
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
		String cycle_param="";
		String cycle_exe_param="";
		String Jira_ir_param="";
	
			JiraIR jira_ir_obj=new JiraIR("/rest/api/2/issue");
			Jira_ir_param="{\"fields\": {\"project\": {\"key\": \"EQHV\"},\"issuetype\": {\"id\": \"1\",\"name\": \"Bug\"},\"summary\":\""+
			"Test Summary"+
					"\",\"description\":\""+"Test Description"+"\"}}";
			JSONObject json_jira_param=new JSONObject(Jira_ir_param);
			System.out.println("url:"+jira_ir_obj.ZAPI_URL+" Param"+json_jira_param);
			System.out.println(post(jira_ir_obj.ZAPI_URL,json_jira_param));
		
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
