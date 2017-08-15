
import java.io.*;

public class FileProcessing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File dir = new File("C:/Users/v536139/Desktop/SOAPUIReport");
		File[] files = dir.listFiles();
		String pattern = "PPSHInfoSuit-PPSH_UBB-SetUbbAlert_Above";
		StringBuffer stringBuffer = new StringBuffer();

		int line_num = 0;
		
		for(int i=0; i<files.length; i++)
		
			if(files[i].getName().startsWith(pattern))
			{
			
			try{
			FileReader fileReader = new FileReader(files[i]);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			while ((line = bufferedReader.readLine()) != null && line_num<6) {
				stringBuffer.append(line);
				stringBuffer.append("\n");
				line_num++;
			}
			fileReader.close();
			System.out.println(stringBuffer.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			line_num = 0;

		}


}
	
}
