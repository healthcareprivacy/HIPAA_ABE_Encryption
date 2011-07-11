package security.abemodule.encryption;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import policy.HIPAAQuery;
import policy.HIPAAResult;
import policy.ReadHIPAAPolicy;
import security.abemodule.utility.CommandExecutor;
import security.abemodule.utility.HipaaResultFormatter;


//Example Command for Document Encryption
//cpabe-enc pub_key security_report.pdf
//(sysadmin and (hire_date < 946702800 or security_team)) or
//(business_staff and 2 of (executive_level >= 5, audit_group, strategy_team))

public class DocumentEncryptor {
	
	//private String policyStoreFileName;
	private String keydirpath;
	//private String documentPath;
	private String documentName;

	public DocumentEncryptor(String keydirpath, String documentName){
		this.keydirpath = keydirpath;
		this.documentName = documentName;
		//this.documentPath = documentPath;
		//this.policyStoreFileName = policyStoreFileName;
	}

	public void gotokeydir(){
		CommandExecutor commandexecutor = new CommandExecutor();
		commandexecutor.setCommand("cd "+keydirpath);
		commandexecutor.executeCommand();
	}

	public void encryptDocument(String attributes, String encrypteddocname){
		CommandExecutor commandexecutor = new CommandExecutor();
		//String fulldocumentPath = documentPath + "/"+documentName;
		//String encDocPath = documentPath + "/"+encrypteddocname; 
		String fulldocumentPath = documentName; 
		String encDocPath = encrypteddocname; 
		attributes = "\""+attributes+"\"";
		String encryptcommand =  "./abe-enc -m CP -i "+ fulldocumentPath +" -p "+attributes +" -o "+encDocPath;
		String[] commands = new String[]{"cd "+keydirpath,encryptcommand};        
		commandexecutor.setCommand(commands);
		commandexecutor.executeCommand();
	}
	
	public int encryptDocumentUsingAttributeList (ArrayList<String> attributeList, String encrypteddocname){
		int count = 0;
		NumberFormat form = new DecimalFormat("000");
		for (String currentSet : attributeList){
			encryptDocument(currentSet, encrypteddocname+form.format(count));
			count++;
		}
		return count;
	}

	/*public static void main(String args[]){
		String attributes = "one or two or three or four or five or six or seven or eight or nine or ten or eleven or twelve" ;
		//String attributes = "((icu'jcu' or heart) and discharge)" ;
		HashMap<String, Set<HIPAAResult>> policy = null;
		try {
			policy = (HashMap<String, Set<HIPAAResult>>)ReadHIPAAPolicy.unserializeObject(policyStoreFileName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HIPAAQuery q = new HIPAAQuery("adult", "adult", "phi", "treatment");
		System.out.println("query hash = " + q.toString());
		Set<HIPAAResult> possibleResults = policy.get(q.toString());			
		System.out.println(possibleResults);
		HipaaResultFormatter h = new HipaaResultFormatter(possibleResults);
		//System.out.println(h.stringFormat());
		//String attributes = h.stringFormat();
		DocumentEncryptor documentencryptor = new DocumentEncryptor(System.getenv("ABE_LIB_LOCATION"), "hipaatest", System.getenv("ABE_DIR"));	
		documentencryptor.gotokeydir();
		ArrayList<String> attributeList = h.stringFormat();
		int count = documentencryptor.encryptDocumentUsingAttributeList(attributeList, "hipaaenc");
	}*/
}
