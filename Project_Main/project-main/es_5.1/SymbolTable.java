import java.util.*;

public class SymbolTable {

     Map <String, Integer> OffsetMap = new HashMap <String,Integer>();
     
	public void insert( String s, int address) {
            if( OffsetMap.containsValue(address) ){
	           throw new IllegalArgumentException("Reference to a memory location already occupied by another variable");
            } 
            OffsetMap.put(s,address);
    }

	public int lookupAddress ( String s ) {
            if( OffsetMap.containsKey(s) ) {
                return OffsetMap.get(s);
            }
            else
                return -1;
	}
}
