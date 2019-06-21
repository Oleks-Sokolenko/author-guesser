import java.util.Random;

public class FrequencyHashTable{

	private int mTotalSize;
	private BucketStorage[] mTable;
	private int mCapacity;
	private int mSize;
	private final int SCALE,SHIFT, PRIME;

	/**
	 * Constructor to instantiate variables and create the table
	 */
	FrequencyHashTable(){
		Random rand = new Random();
		mTable = new BucketStorage[9973];
		mCapacity = mTable.length;
		PRIME = 433494437;
		SCALE = rand.nextInt(PRIME);
		SHIFT = rand.nextInt(PRIME + 1);
		mTotalSize = 0;
	}

	/**
	 * Increments the count for the specified word or adds it the table if its not in the table yet
	 * @param key word to increment in the table
	 */
	public void increment(String key){
		int count = get(key);
		if (count == -1){
			put(key, 1);
		}else{
			put(key, count + 1);
		}
		mTotalSize++;
	}

	/**
	 * Removes word from the table
	 * @param key word to be removed
	 * @return count of occurrences
	 */
	public int remove(String key){
		int answer = bucketRemove(hashValue(key), key);
		if(answer != -1) mTotalSize -= answer;
		return answer;
	}

	/**
	 * Counts the frequency of the word in the table
	 * @param key word to check
	 * @return frequency of the word in the table
	 */
	public double getFrequency(String key){
		if(get(key) == -1) return 0.0000001;
		return get(key) / (double)mTotalSize;
	}

	/**
	 * Total sum of counts in the table
	 * @return sum of counts
	 */
	public int totalSize(){
		return mTotalSize;
	}

	/**
	 * Gets the count of the word from the table
	 * @param key word to look up
	 * @return count
	 */
	private int get(String key){
		return bucketGet(hashValue(key), key);
	}

	/**
	 * Adds the word to the table
	 * @param key word to add
	 * @param value number of occurrences
	 * @return old value for occurrences
	 */
	private int put(String key, int value){
		return bucketPut(hashValue(key), key, value);
	}

	/**
	 * Checks if table is empty
	 * @return true is table is empty, false otherwise
	 */
	public boolean isEmpty(){
		return mSize == 0;
	}

	/**
	 * Size of the table
	 * @return size
	 */
	public int size(){
		return mSize;
	}

	/**
	 * Count the hashValue of the word to store it in the table
	 * @param k word
	 * @return hash value
	 */
	private int hashValue(String k){
		return (Math.abs(((k.hashCode()*SCALE + SHIFT)%PRIME)%mCapacity));
	}

	/**
	 * Gets the specified word by its has from the underlying bucket structure
	 * @param hash value to look up
	 * @param k word
	 * @return count of occurrences of the word
	 */
	private int bucketGet(int hash, String k){
		BucketStorage bucket = mTable[hash];
		if(bucket == null){
			return -1;
		}
		return bucket.get(k);
	}

	/**
	 * Puts the specified word by its hash value into the bucket structure
	 * @param hash hash value of the word
	 * @param k word
	 * @param v number of occurrences of the word
	 * @return old value of occurrences
	 */
	private int bucketPut(int hash, String k, int v){
		BucketStorage bucket = mTable[hash];
		if(bucket == null){
			bucket = mTable[hash] = new BucketStorage();
		}
		int oldSize = bucket.size();
		int value = bucket.put(k, v);
		mSize += (bucket.size() - oldSize);
		return value;
	}

	/**
	 * Removes value from the bucket by its hash value
	 * @param hash hashvalue
	 * @param k word
	 * @return count of occurrences
	 */
	private int bucketRemove(int hash, String k){
		BucketStorage bucket = mTable[hash];
		if(bucket == null) return -1;
		int oldSize = bucket.size();
		int value = bucket.remove(k);
		mSize -= oldSize - bucket.size();
		return value;
	}

}
