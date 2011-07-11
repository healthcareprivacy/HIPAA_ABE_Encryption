/**
 * 
 */
package policy;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Nipun Dave
 * @param <V>
 * @param <W>
 *
 */
public class Query<F, V> implements Serializable {
	public HashMap<F, V> query;
	
	public Query(){
		query = new HashMap<F, V>();
	}
	
	public void addElement(F field, V value){
		query.put(field, value);
	}
	
	public int hashCode(){
		int hashcode = query.size();
		for (Object key : query.keySet()){
			hashcode += (key.hashCode()*query.get(key).hashCode());
		}
		return hashcode;
	}
	
	public String toString(){
		return query.toString();
	}
	
	public boolean equals(Query<F,V> q){
		return query.equals(q.query);
	}
}
