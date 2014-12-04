/**
 * A map entry (key-value pair).
 */
public class Entry<K, V>
{
	private K key;
	private V value;

	/**
	 * Constructs the map entry with the specified key and value.
	 */
	public Entry(K k, V v)
	{
		key = k;
		value = v;
	}

	/**
	 * Returns the key corresponding to this entry.
	 *
	 * @return the key corresponding to this entry
	 */
	public K getKey()
	{
		return key;
	}

	/**
	 * Returns the value corresponding to this entry.
	 *
	 * @return the value corresponding to this entry
	 */
	public V getValue()
	{
		return value;
	}

	/**
	 * Replaces the value corresponding to this entry with the specified value.
	 *
	 * @param value
	 *            new value to be stored in this entry
	 * @return old value corresponding to the entry
	 */
	public V setValue(V newValue)
	{
		V oldValue = value;
		value = newValue;
		return oldValue;
	}
}