package server.process;



/**
 * 
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import policy.HIPAAQuery;
import wrapper.EncryptionModule;
import decryption.DecryptionModule;

/**
 * @author sharps
 *
 */
public class ProcessInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static int numOfEncryptedVersionsOfKey = 0;  
    private static String randomFolderName = new String();
    
    //set bacsed on location of files
    private String hipaa_home = "/Users/frankwang/Documents/workspace/HIPAA_ABE_Encryption_new/";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessInput() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.setContentType("text/html");
		
		String filePath = hipaa_home + "src/temp/";
		String fileName = Integer.toString(new Random().nextInt());
		String fileLoc = filePath+fileName;
		
		PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        
        if (type.equals("encrypt")){
        	String sender =request.getParameter("sender");
            String owner = request.getParameter("owner");
            String purpose = request.getParameter("purpose");
            String message = request.getParameter("message");
            long beginTime = new Date().getTime();
            encryptMessage(sender, owner, purpose, message, fileLoc);
            
            String path = hipaa_home + "src/temp/" + randomFolderName;
            NumberFormat form = new DecimalFormat("000");
            String AESKeyfile = null;
            for (int count = 0; ; count++){
            	File file = new File(path+"/abeEncryptedAESKey"+form.format(count)+".cpabe");
            	if (file.exists()){
            		AESKeyfile = path+"/abeEncryptedAESKey"+form.format(count)+".cpabe";
            		break;
            	}
            	
            }
            String abeEncryptedAESKey = readFile(AESKeyfile);
            String resp = readFile(path + "/encryptedMssg.txt");
            byte[] encryptedKey = resp.getBytes();
            String resp1 = Base64Coder.encodeLines(encryptedKey);
            int encMssgLen = resp1.length();
            int encKeyLen = abeEncryptedAESKey.length();
            long endTime = new Date().getTime();
            long total = endTime - beginTime;
            out.println("encryptedMessage|"+resp1+
            		"|encryptedMessageLength|"+"<h4 class=\"title\">Number of Characters = " +encMssgLen+ "</h4>"+
            		"|encryptedAESKey|"+abeEncryptedAESKey+
            		"|encryptedKeyLength|"+"<h4 class=\"title\">Number of Characters = " +encKeyLen+ "</h4>" + 
            		"|numOfABEEncryptions|"+"<h4 class=\"title\">Number of ABE Encryptions = " +numOfEncryptedVersionsOfKey+ "</h4>");
            System.out.println("Encryption Time:" + total);
            System.out.println(sender + "->" + owner + "->" + purpose + "->" + message);
            deleteFile(fileLoc+".cpabe");
			
        } else if (type.equals("decrypt")){
        	
        	String receiver = request.getParameter("receiver");
        	String belief = request.getParameter("belief");
        	if (request.getParameter("belief").equals("minimum_necessary_to_purpose"))
        		belief = request.getParameter("belief")+request.getParameter("receiver");
        	String consent = request.getParameter("consent");
        	long startTime = new Date().getTime();
        	String resp2 = decryptMessage(receiver, belief, consent, filePath);
        	out.println("decryptedMessage|"+resp2);
        	long endTime = new Date().getTime();
        	long total = endTime - startTime;
        	System.out.println("Decryption Time:" + total);
        	System.out.println("Decrypted.");
        }
        
        
	}

	protected void encryptMessage(String sender, String owner, String purpose, String message, String fileLoc){
		EncryptionModule encMod = new EncryptionModule();
		randomFolderName = Integer.toString(Math.abs(new Random().nextInt(1048576)));
		
		String path = hipaa_home + "src/temp/" + randomFolderName;
		new File(path).mkdir();
		
		encMod.encryptedKeyFileName = path + "/aesKey.key";
		encMod.encryptedKeyPath = path;
		//encMod.toBeEncryptedFileName	= path +"/plainMssg.txt";
		encMod.encryptedFileName 		= path + "/encryptedMssg.txt";
		encMod.abeEncryptedKeyName		= path + "/abeEncryptedAESKey";
		encMod.q 						= new HIPAAQuery(sender, owner, "phi", purpose);
		//storeStringInFile("Message:"+message, encMod.toBeEncryptedFileName);
		System.out.println(encMod.q.toString());
		System.out.println(encMod.encryptedFileName);	
		numOfEncryptedVersionsOfKey = encMod.encrypt(message);
		System.out.println(message);
		
	}
	
	protected String decryptMessage(String receiver, String belief, String consent, String filePath){
		DecryptionModule decMod = new DecryptionModule();
		
		String path = hipaa_home + "src/temp/" + randomFolderName;
		
		decMod.userKeyPath 				= path;
		decMod.userKeyName 				= "accessorKey.key";
		decMod.abeEncryptedKeyPath 		= path;
		decMod.abeEncryptedKeyName 		= "/abeEncryptedAESKey";
		decMod.aesEncryptedFilePath 	= path;
		decMod.aesEncryptedFileName 	= "/encryptedMssg.txt";
		decMod.decryptedFilePath 		= path;
		decMod.decryptedFileName 		= "/decryptedMssg.txt";
		decMod.numberOfEncryptedKeys 	= numOfEncryptedVersionsOfKey;
		decMod.policies = "RX" + receiver + " and " + "CX" + consent + " and " + "BX" + belief;
		decMod.attributes = "RX" + receiver + ",CX" + consent + ",BX" + belief;
		
		decMod.generateABEKey();
		boolean found = decMod.decrypt();
		if (!found)
			return "Sorry, with your privileges, file cannot be accessed.";
		else{
			String result ="";
			try{
				int numRead = 0;
				byte[] buf = new byte[1024];
				FileInputStream in = new FileInputStream(decMod.decryptedFilePath+decMod.decryptedFileName);
				while ((numRead = in.read(buf)) >= 0) {
					result = result+ new String (buf);
				}
				in.close();
			}
			catch (Exception e){
			}
			return result.substring(result.indexOf(":")+1);
		}
	}
	
	protected boolean deleteFile(String filePath){
		File file = new File(filePath);
		
		if (file.exists() && file.isFile()){
			return file.delete();
		}
		
		return false;
	}
	
	protected boolean storeStringInFile(String str, String filePath){
		try {
			FileWriter fstream = new FileWriter(filePath);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(str);
			out.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	protected String readFile(String filePath){
		StringBuffer content = new StringBuffer();
		FileInputStream fis = null;
		int ch;
		try {
			fis = new FileInputStream(new File(filePath));
			while((ch = fis.read()) != -1){
				content.append((char)ch);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println("File " + filePath + " was not found.");
			return null;
		} catch (IOException e) {
			return null;
		}
		return content.toString();
	}
}

