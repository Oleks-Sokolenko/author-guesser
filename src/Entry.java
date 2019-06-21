public class Entry<K, V>{

	private K key;
	private V value;

	Entry(K k, V v){
		key = k;
		value = v;
	}

	/**
	 * Gets the key of the Entry
	 * @return key
	 */
	public K getKey(){ return key; }

	/**
	 * Sets the key of the Entry
	 * @param key new value for key
	 */
	public void setKey(K key){ this.key = key; }

	/**
	 * Gets the value from the Entry
	 * @return value
	 */
	public V getValue(){ return value; }

	/**
	 * Sets the value for Entry
	 * @param v value to be set
	 * @return old value
	 */
	public V setValue(V v){
		V oldValue = value;
		value = v;
		return oldValue;
	}

}
