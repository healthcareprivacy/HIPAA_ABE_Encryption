/**
 * 
 */
package policy;

/**
 * @author sharps
 *
 */
public class DecryptionParameters {
	String receiver;
	String belief;
	String consent;
	
	public DecryptionParameters(){
	}
	
	public void setReceiver(String value){
		receiver = value;
	}
	
	public void setBelief(String value){
		belief = value;
	}
	
	public void setConsent(String value){
		consent = value;
	}
	
	public String getReceiver()	{return receiver;}
	
	public String getBelief()	{return belief;}
	
	public String getConsent()	{return consent;}
	
}
