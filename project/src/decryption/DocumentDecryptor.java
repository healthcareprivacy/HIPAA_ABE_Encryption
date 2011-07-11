/**
 * 
 */
package decryption;

import security.abemodule.utility.CommandExecutor;

/**
 * @author sharps
 *
 */
public class DocumentDecryptor {
	CommandExecutor commandexecutor;
	String libPath;
	String keyFileLoc;
	String encMssgLoc;
	String resultLoc;
	
	public DocumentDecryptor(String lp, String kf, String em, String rl){
		commandexecutor = new CommandExecutor();
		this.libPath = lp; 
		this.keyFileLoc = kf;
		this.encMssgLoc = em;
		this.resultLoc = rl;
	}
	
	public void gotokeydir(){
		commandexecutor.setCommand("cd "+libPath);
		commandexecutor.executeCommand();
	}
	
	public void decryptDocument(){
		String encryptcommand =  "./abe-dec -m CP -k "+ keyFileLoc +" -f " + encMssgLoc;
		String[] commands = new String[]{"cd "+libPath ,encryptcommand};        
		commandexecutor.setCommand(commands);
		commandexecutor.executeCommand();
	}

}
