/**
 * 
 */
package policy;

/**
 * @author sharps
 *
 */
public class EncryptionParameters {
	String sender;
	String owner;
	String purpose;
	String message;
	
	public EncryptionParameters(){
	}
	
	public void setSender(String value){
		sender = value;
	}
	
	public void setOwner(String value){
		owner = value;
	}
	
	public void setPurpose(String value){
		purpose = value;
	}
	
	public void setMessage(String value){
		message = value;
	}
	
	public String getSender()	{return sender;}
	
	public String getOwner()	{return owner;}
	
	public String getPurpose()	{return purpose;}
	
	public String getMessage()	{return message;}

}
