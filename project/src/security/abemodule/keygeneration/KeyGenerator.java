package security.abemodule.keygeneration;

import security.abemodule.utility.*;
import java.io.IOException;

public class KeyGenerator {
	
	CommandExecutor commandexecutor;
	String keydirpath;
	String keyGeneratorPath;
	String commands[];
	
	public KeyGenerator(String keydirpath, String keyGeneratorPath){
		commandexecutor = new CommandExecutor();
		this.keydirpath = keydirpath;
		this.keyGeneratorPath = keyGeneratorPath;
	}
	
	public void gotokeygeneratorpath(){
		commandexecutor.setCommand("cd "+keyGeneratorPath);
		commandexecutor.executeCommand();
	}
	
	/*public void generatePublicMasterKey(){
		commands = new String[]{"cd "+keyGeneratorPath,"./abe-setup -m CP"};
		commandexecutor.setCommand(commands);
		commandexecutor.executeCommand();
	}*/
	
	public void generateABEKey(String keyname, String attributes, String policies){
		
	    String privateKeyPath = keydirpath + "/"+keyname; 
	    attributes = "\'"+attributes+"\'";
	    policies = "\'" + policies+"\'";
	    String generatekeycommand =  "./abe-keygen -m CP -p " + policies + " -a "+attributes+" -o "+privateKeyPath;
	    commands = new String[]{"cd "+keyGeneratorPath,generatekeycommand};        
		commandexecutor.setCommand(commands);
		commandexecutor.executeCommand();
		    
	} 
	
	
	public static void main(String args[]){
		//KeyGenerator keygenerator = new KeyGenerator("/Users/frankwang/Desktop/ABE", "/Users/frankwang/Desktop/fenc-0.2.0/tools/");	
		//keygenerator.gotokeygeneratorpath();
		//keygenerator.generatePublicMasterKey();
		//keygenerator.generateABEKey("test_privhipaaCP.key", "RXvolunteer,CXadult,CXconsented,BXvoid");
		//keygenerator.generateABEKey("test2_privhipaaCP.key", "one,twelve,nine");
		//System.out.println("here it is");
	}

}
