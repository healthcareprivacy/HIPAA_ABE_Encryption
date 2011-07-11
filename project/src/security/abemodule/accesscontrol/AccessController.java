 package security.abemodule.accesscontrol;

import security.abemodule.utility.CommandExecutor;

//Example command for document access
//cpabe-dec pub_key kevin_priv_key security_report.pdf.cpabe

public class AccessController {
	String documentName;
	String entityKey;
	CommandExecutor commandexecutor;
	String []commands;
	String keydirpath;
	
	public AccessController(String documentName, String entityKey, String keydirpath, String outputDir){
		this.documentName = documentName;
		this.entityKey = entityKey;
		this.keydirpath = keydirpath;
		//System.out.println("entity:"+documentName);
		String decryptcommand = "./abe-dec -m CP -k "+this.entityKey+" -f "+this.documentName +" > " + outputDir +"/a.out";
		commandexecutor = new CommandExecutor();
		commands = new String[]{"cd "+keydirpath, decryptcommand};        	
	}
	
	public void gotokeydir(){
		commandexecutor.setCommand("cd "+keydirpath);
		commandexecutor.executeCommand();
	}
	
	public void accessDocument(){
		commandexecutor.setCommand(commands);
		commandexecutor.executeCommand();
	}
	
	public void setDocumentName(String documentName){
		this.documentName = documentName;
	}
	
	public void setEntityKey(String entityKey){
		this.entityKey = entityKey;
	}
	
	public static void main(String args[]){
		//AccessController accesscontroller = new AccessController(System.getenv("ABE_DIR")+"hipaaenc.cpabe", System.getenv("ABE_DIR")+"test2_privhipaaCP.key",System.getenv("ABE_LIB_LOCATION"));
		//accesscontroller.accessDocument();
	}
	
}
