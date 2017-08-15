

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;

import oracle.jdbc.driver.OracleDriver.*;
/**
 * Servlet implementation class PublishFlow
 */
@WebServlet("/PublishFlow")
public class PublishFlow extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishFlow() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//HttpSession session=request.getSession();
		//String flow_name=(String)session.getAttribute("trigger_param_var");
		String flow_name=request.getParameter("flow_selected");
		String event_name="";
		
		if(flow_name.startsWith("PPSHInfoReq"))  
		event_name="PPSHInfoReq" ;
		else  
		if(flow_name.matches("(.*)LOFOnDemand(.*)"))
		event_name="LOFOnDemandNew" ;
		else
		event_name="Dummy";
		
		System.out.println(event_name);
		System.out.println(flow_name);


		try {	

			Connection con = null;
			//Class.forName("oracle.jdbc.xa.client.OracleXADataSource");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@(DESCRIPTION = (LOAD_BALANCE = yes) (ADDRESS = (PROTOCOL =TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521))(ADDRESS = (PROTOCOL=TCP)(HOST=eqhttdd2.ebiz.verizon.com)(PORT=1521)) (CONNECT_DATA = (SERVICE_NAME=TACTUAT)))", "tactuser", "tactuser");	
			//String query = "update event_config set workflow_name='"+flow_name+"' where event_name='PPSHInfoReq' and rownum=1";
			String query="update event_config set workflow_name='"+flow_name+"' where event_name='"+event_name+"' and rownum=1";
			System.out.println(query);

			Statement st = con.createStatement();	
			ResultSet rs = st.executeQuery(query);
			}
		catch(Exception ex){
			 System.out.println(ex);
		} 
		response.getWriter().write("Flow Published");

			}
	
}
