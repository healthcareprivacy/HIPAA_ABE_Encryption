package decryption;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import security.abemodule.accesscontrol.AccessController;
import security.abemodule.keygeneration.KeyGenerator;


// all paths (except for fileEncryptionKeyName and encryptedKeyName) need to be absolute

public class DecryptionModule {
	
	public String userKeyPath;
	public String userKeyName;
	public String attributes;
	public String policies;
	public String abeEncryptedKeyPath;
	public String abeEncryptedKeyName;
	public String aesEncryptedFilePath;
	public String aesEncryptedFileName;
	public String decryptedFilePath;
	public String decryptedFileName;
	public int numberOfEncryptedKeys;
	
	//needs to be set at time of installation
	public static String locationofABETools = "/Users/frankwang/Desktop/fenc-0.2.0/tools/";
	
	public boolean decrypt(){
		String decryptedKey="";
		boolean found = false;
		
		for (int i=0;i<numberOfEncryptedKeys;i++){
			NumberFormat form = new DecimalFormat("000");
			AccessController accesscontroller = new AccessController(abeEncryptedKeyPath+"/"+abeEncryptedKeyName+form.format(i)+".cpabe",userKeyPath+"/"+userKeyName,locationofABETools, decryptedFilePath);
			accesscontroller.accessDocument();
			try {		 
		        String sCurrentLine;
		        BufferedReader br = 
		             new BufferedReader(new FileReader(decryptedFilePath +"/"+"a.out"));
		        
		        while ((sCurrentLine = br.readLine()) != null) {
			       if(sCurrentLine.contains("Plaintext") && sCurrentLine.contains("Key-")){
			    	   found = true;
			    	   decryptedKey = sCurrentLine.substring(sCurrentLine.indexOf("-")+1);
			    	   break;
			       }
			 }          
		    } catch (FileNotFoundException e) {
		      e.printStackTrace(); 
		    } catch (IOException e) {	 
		      e.printStackTrace();		 
		    }
		    if (found)
		    	break;
		}
		if(found){
			try{
				SecretKey aesKey = new SecretKeySpec(decryptedKey.getBytes(), "AES");
				FileInputStream in = new FileInputStream(aesEncryptedFilePath+"/"+aesEncryptedFileName);
				int sizeOfCipherText = in.available();
				
				//get ciphertext with IV from file
				byte[] cipherTextInBytes = new byte[sizeOfCipherText];
				in.read(cipherTextInBytes);
				in.close();
				String cipherText = new String(cipherTextInBytes);
				int startIV = cipherText.indexOf("IV-") + 3;
				int startMessage = cipherText.indexOf("Message-") + "Message-".length();
				
				//get IV from ciphertext
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(cipherText.substring(startIV, startIV+16).getBytes());
				Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				dcipher.init(Cipher.DECRYPT_MODE, aesKey, paramSpec);
				String plainText = new String(dcipher.doFinal(cipherText.substring(startMessage).getBytes()));
				
				//output to file
				FileOutputStream out = new FileOutputStream(decryptedFilePath+"/"+decryptedFileName);
				out.write(plainText.getBytes());
				out.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return found;
	}
	
	public void generateABEKey(){
		KeyGenerator keygenerator = new KeyGenerator(userKeyPath, locationofABETools);	
		keygenerator.gotokeygeneratorpath();
		keygenerator.generateABEKey(userKeyName, attributes,policies);
	}
}