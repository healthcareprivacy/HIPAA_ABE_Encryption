/**
 * 
 */
package security.abemodule.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @author sharps
 *
 */
public class SaveStringInTempFile {
	public String str;
	public String filePath;
	
	public SaveStringInTempFile(String s, String fP){
		str 		= s;
		filePath 	= fP;
	}
	
	public void run(){
		try{
		    // Create file 
			File file = new File(filePath);
			if (!file.exists()){
				file.createNewFile();
			}
		    PrintStream out = new PrintStream(new FileOutputStream(file));
		    out.println(str);
		    out.close();
	    }
		catch (Exception e){
	      System.err.println("Error: " + e.getMessage());
	    }
	}
	
}
