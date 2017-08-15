

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GetReport
 */
@WebServlet("/GetReport")
public class GetReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetReport() {
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
		
		try {
			HttpSession session=request.getSession();
			
			String trigger_param=(String)session.getAttribute("trigger_param_serv");
			String[] split = trigger_param.split("#");
			String suit_name=split[0];
			String uc_name=split[1];
			String pattern=suit_name+"-"+uc_name+"-Report"+".xls";

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename="+pattern);
            //File temp = new File("C:/Users/v536139/Desktop/report_excel/"+pattern);
            File temp = new File("/home/v536139/SOAPUI/report_excel/"+pattern);// Actual file path
            FileInputStream fis = new FileInputStream(temp);
            OutputStream os = response.getOutputStream();
            int read = 0;
            while((read=fis.read())!=-1){
                  os.write(read);
            }
            fis.close();
            os.flush();
            os.close();
     } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
     }


		
	}

}
