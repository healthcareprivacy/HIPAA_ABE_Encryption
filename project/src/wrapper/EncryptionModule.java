package wrapper;

import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import policy.HIPAAQuery;
import policy.HIPAAResult;
import policy.ReadHIPAAPolicy;
import security.abemodule.encryption.DocumentEncryptor;
import security.abemodule.utility.HipaaResultFormatter;


// all paths (except for fileEncryptionKeyName and encryptedKeyName) need to be absolute

public class EncryptionModule {
	
	//change these depending on location of server
	public static String policyStoreFileName = "/Users/frankwang/Documents/workspace/HIPAA_ABE_Encryption_new/src/policy/HIPAAPolicyJava.ser";
	public static String locationofABETools = "/Users/frankwang/Desktop/fenc-0.2.0/tools/";
	
	public String encryptedKeyFileName;// name of the des key of the file
	public String encryptedKeyPath;// path of des key
	//public String toBeEncryptedFileName;// path and name of unencrypted file
	public String encryptedFileName;// path and name of encrypted file (encrypted using des key)
	public String abeEncryptedKeyName; //name of abe encrypted key of des key
	public HIPAAQuery q;
	
	public int encrypt(String message){
		SecretKey key = null;
		try {
			// generate AES key
			key = KeyGenerator.getInstance("AES").generateKey();
			
			//create random IV for CBC mode
			SecureRandom random = new SecureRandom();
			byte[] iv = new byte[16];
			random.nextBytes(iv);
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			
			//encrypt plaintext using AES
			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			byte[] cipherText = ecipher.doFinal(message.getBytes());
			
			//store in cipherText and IV into a file
			FileOutputStream out = new FileOutputStream(encryptedFileName);
			//prepend IV to message
			String finalcipherText = "IV-"+new String(ecipher.getIV())+"Message-"+ new String(cipherText);
			out.write(finalcipherText.getBytes());
			out.close();
			System.out.println(finalcipherText);
			
			//store encrypted AES key in file
			FileOutputStream out2 = new FileOutputStream(encryptedKeyFileName);
			String finalKey = "Key-" + new String(key.getEncoded());
			out2.write(finalKey.getBytes());
			out2.close();
			System.out.println(finalKey);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long startTime = new Date().getTime();
		HashMap<String, Set<HIPAAResult>> policy = null;
		try {
			policy = (HashMap<String, Set<HIPAAResult>>)ReadHIPAAPolicy.unserializeObject(policyStoreFileName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("query hash = " + q.toString());
		Set<HIPAAResult> possibleResults = policy.get(q.toString());		
		long endTime = new Date().getTime();
		long total = endTime - startTime;
		System.out.println("Policy time:" + total);
		System.out.println(possibleResults);
		HipaaResultFormatter h = new HipaaResultFormatter(possibleResults);
		DocumentEncryptor documentencryptor = new DocumentEncryptor(locationofABETools,encryptedKeyFileName);	
		documentencryptor.gotokeydir();
		ArrayList<String> attributeList = h.stringFormat();
		int count = documentencryptor.encryptDocumentUsingAttributeList(attributeList, abeEncryptedKeyName);
		return count;
	}
}
