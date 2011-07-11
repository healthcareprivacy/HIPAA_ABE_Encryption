/**
 * 
 */
package policy;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Nipun Dave
 *
 */
public final class HIPAAQuery extends Query<String, String> implements Serializable{
	private static final long serialVersionUID = 1L;

	public HIPAAQuery() {
		query = new HashMap<String, String> (4);
		query.put("sender", new String());
		query.put("owner", new String());
		query.put("purpose", new String());
		query.put("type", new String());
	}
	
	public HIPAAQuery(String sender, String owner, String type, String purpose){
		query = new HashMap<String, String> (4);
		query.put("sender", sender);
		query.put("owner", owner);
		query.put("purpose", purpose);
		query.put("type", type);
	}
	
	public String prologQuery(){
		return 	"pbh(a(" + query.get("sender") + 
				", R," + query.get("owner") + 
				"," + query.get("type")	+ ", " 
				+ query.get("purpose") +", null, C, B)).";
	}
	
	public int hashCode(){
		String hash = new String();
		for (String key : query.keySet()){
			hash += (key + ":"+ query.get(key)) + ",";
		}
		return hash.hashCode();
	}
	
	public String toString(){
		String str = new String();
		for (String key : query.keySet()){
			str += (key + ":"+ query.get(key)) + ",";
		}
		return str;
	}
}
