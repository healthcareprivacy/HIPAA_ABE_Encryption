package security.abemodule.utility;

import java.util.ArrayList;
import java.util.Set;

import policy.HIPAAResult;

public class HipaaResultFormatter {
	public Set<HIPAAResult> hipaaResults;
	
	public HipaaResultFormatter(Set<HIPAAResult> hresults){
		hipaaResults = hresults;
	}
	int count = 0;
	public ArrayList<String> stringFormat(){
		String result = "";
		ArrayList<String> results = new ArrayList<String>();
		System.out.println("***********************"+hipaaResults.size());
		for (HIPAAResult hr:hipaaResults){
			//String belief = hr.belief();
			String belief = hr.belief().replaceAll("b\\((.*?),","").replace(")","").replace(",","");
			//String belief = hr.belief().replace(",", "").replace("(", "").replace(")","");
			String receiver = hr.receiver().replace(",", "").replace("(", "").replace(")","");
			String consent = hr.consent().replace(",", "").replace("(", "").replace(")","");
			if(result==""){
				//result = result + "(" +hr.receiver()+" and "+hr.consent()+" and "+hr.belief()+")";
				result = result + receiver + " and " + consent + " and " + belief;
			}else{					
				//result = result + " or "+ "(" +hr.receiver()+" and "+hr.consent()+" and "+hr.belief()+")";
				result = result + " or " + receiver + " and " + consent + " and " + belief;
			}
			/*if(count == 1){
				results.add(result);
				count = 0;
				result = "";	
			}*/
			if (result !=""){
				results.add(result);
				result = "";
			}
		}
		return results;
	}
}
