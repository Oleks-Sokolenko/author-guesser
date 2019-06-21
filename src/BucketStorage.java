import java.util.ArrayList;


class BucketStorage{

	private ArrayList<Entry<String, Integer>> table;

	public BucketStorage(){
		table = new ArrayList<>();
	}

	/**
	 * Get the value by key
	 * @param k key
	 * @return value
	 */
	public int get(String k){
		int index = getIndex(k);
		if(index == -1) return -1;
		return (int) table.get(index).getValue();
	}

	/**
	 * Put value in storage by key
	 * @param k key
	 * @param v value
	 * @return returns old value
	 */
	public int put(String k, int v){
		int index = getIndex(k);
		if(index == -1){
			table.add(new Entry<String, Integer>(k, Integer.valueOf(v)));
			return -1;
		}else{
			return table.get(index)
			            .setValue(v);
		}
	}

	/**
	 * Remove value from the storage by key
	 * @param k key
	 * @return value
	 */
	public int remove(String k){
		int index = getIndex(k);
		if(index == -1) return -1;
		return table.remove(index).getValue();
	}

	/**
	 * Size of the storage
	 * @return size
	 */
	public int size(){
		return table.size();
	}

	/**
	 * Get index of the key
	 * @param key to look up
	 * @return pos in storage, -1 if its not present
	 */
	private int getIndex(String key){
		for(int i = 0; i < table.size(); i++){
			if(table.get(i).getKey().equals(key)){
				return i;
			}
		}
		return -1;
	}
}