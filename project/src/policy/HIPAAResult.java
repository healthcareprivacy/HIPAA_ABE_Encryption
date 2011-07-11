/**
 * 
 */
package policy;

import java.io.Serializable;
import java.util.HashMap;

import security.abemodule.utility.HipaaResultFormatter;

/**
 * @author Nipun Dave
 *
 */
public final class HIPAAResult extends Result<String, String> implements Serializable{
	private static final long serialVersionUID = 1L;

	public HIPAAResult(){
		result = new HashMap<String, String>(3);
		query = new HIPAAQuery();
		result.put("receiver", new String());
		result.put("belief", new String());
		result.put("consent", new String());
	}
	
	public HIPAAResult(HIPAAQuery q, String receiver, String belief, String consent){
		result = new HashMap<String, String> (3);
		query = q;
		result.put("receiver", receiver);
		result.put("belief", belief);
		result.put("consent", consent);
	}
	
	public String toString(){
		return 	"R = " + result.get("receiver").toString() + 
				", C = "+ result.get("consent").toString() + 
				", B = "+ result.get("belief").toString() + "\n";
	}
	
	public String receiver(){
		String rec=result.get("receiver").toString();
		if(rec.contains("'")){
			rec = rec.substring(1, (rec.length()-1));
		}
		return 	"RX" + rec;
	}

	public String consent(){
		String con=result.get("consent").toString();
		if(con.contains("(")){
			con = con.substring(1, (con.length()-1));
		}
		String []consents = con.split(",");
		String result="";
		for (String c:consents){
			if(result==""){
				result = result+"CX"+c;
			}else{
				result = result + " and "+"CX"+c;
			}
		}
		return result;
	}

	public String belief(){
		return 	"BX"+ result.get("belief").toString();
	}


}
