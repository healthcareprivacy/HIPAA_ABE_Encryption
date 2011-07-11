/**
 * 
 */
package policy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import policy.HIPAAQuery;
import policy.HIPAAResult;
import security.abemodule.utility.*;
/**
 * @author Nipun Dave
 *
 */
public class ReadHIPAAPolicy implements Serializable{

	private static final long serialVersionUID = 1L;
	public static String policyStoreFileName = "src/policy/HIPAAPolicyJava.ser";
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			HashMap<String, Set<HIPAAResult>> policy = (HashMap<String, Set<HIPAAResult>>)unserializeObject(policyStoreFileName);
			HIPAAQuery q = new HIPAAQuery("adult", "adult", "phi", "treatment");
			System.out.println("query hash = " + q.toString());
			Set<HIPAAResult> possibleResults = policy.get(q.toString());			
			//System.out.println(possibleResults);
			HipaaResultFormatter h = new HipaaResultFormatter(possibleResults);
			System.out.println(h.stringFormat());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

	}
	
	public static Object unserializeObject(String fileName) throws ClassNotFoundException{
		 try {
			 FileInputStream fis = new FileInputStream(fileName);
			 ObjectInputStream in = new ObjectInputStream(fis);
			 return in.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
