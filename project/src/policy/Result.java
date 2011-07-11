package policy;

import java.io.Serializable;
import java.util.HashMap;

public class Result<F,V> implements Serializable{
public HashMap<F, V> result;
public Query<F,V> query;
	
	public Result(){
		result = new HashMap<F, V>();
		query = new Query<F,V> ();
	}
}
