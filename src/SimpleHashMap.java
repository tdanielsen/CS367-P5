import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

/**
 *
 * A map is a data structure that creates a key-value mapping. Keys are unique
 * in the map. That is, there cannot be more than one value associated with a
 * same key. However, two keys can map to a same value.
 *
 * The SimpleHashMap takes two generic parameters, K and V, standing for the
 * types of keys and values respectively.
 *
 */
public class SimpleHashMap<K extends Comparable<K>, V> implements
		SimpleMapADT<K, V>
{

	private int[] tableSizes = { 11, 23, 47, 97, 197, 397, 797, 1597, 3203,
			6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237,
			3292489, 6584983, 13169977, 26339969, 52679969, 105359939,
			210719881, 421439783, 842879579, 1685759167 };
	private double lf = 0.75;
	private int tableSize;
	private LinkedList<Entry<K,V>>[] hashMap;
	private double numEntries;
	private int nextTableSize;
	
	@SuppressWarnings("unchecked")
	public SimpleHashMap()
	{
		tableSize = tableSizes[0];
		hashMap = new LinkedList[tableSize];
		numEntries = 0;
		nextTableSize = 1;
	}

	private int hash(K k)
	{
		int hashIndex = (k.hashCode() % tableSize) - 1;
		if (hashIndex < 0)
		{
			hashIndex = hashIndex + (tableSize - 1);
		}
		return hashIndex;
	}
	
	public int size()
	{
		return hashMap.length;
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key
	 *            the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this
	 *         map contains no mapping for the key
	 * @throws NullPointerException
	 *             if the specified key is null
	 */

	public V get(K key)
	{
		if (key == null)
		{
			throw new NullPointerException();
		}
		int hashKey = hash(key);
		LinkedList<Entry<K,V>> curr = hashMap[hashKey];
		if (curr != null)
		{
			for (int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).getKey().equals(key))
				{
					return (V) curr.get(i).getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Associates the specified value with the specified key in this map.
	 * Neither the key nor the value can be null. If the map previously
	 * contained a mapping for the key, the old value is replaced.
	 *
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key.
	 * @throws NullPointerException
	 *             if the key or value is null
	 */

	public V put(K key, V value)
	{
		if (key == null || value == null)
		{
			throw new NullPointerException();
		}
		Entry<K,V> newEntry = new Entry<K,V>(key, value);
		V oldValue = null;
		int hashKey = hash(key);
		if (hashMap[hashKey] == null)
		{
			LinkedList<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();
			hashMap[hashKey] = entries;
			entries.add(newEntry);
			System.out.println("Put " + key);
			numEntries++;
		}
		else if(hashMap[hashKey].size() == 0)
		{
			hashMap[hashKey].add(newEntry);
		}
		else
		{
			LinkedList<Entry<K,V>> curr = hashMap[hashKey];
			for (int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).getKey().equals(key))
				{
					oldValue = curr.get(i).getValue();
					curr.get(i).setValue(value);
					return oldValue;
				}
				curr.add(newEntry);
			}
			//curr.add(newEntry);
		}
		System.out.println("Migrate? " + numEntries/tableSize + ". Needed: " + lf);
		if (numEntries/tableSize >= lf)
		{
			
			@SuppressWarnings("unchecked")
			LinkedList<Entry<K,V>>[] tempArray = new LinkedList[tableSizes[nextTableSize]];
			for (int i = 0; i < hashMap.length; i++)
			{
				if (hashMap[i] != null)
				{
					for (int j = 0; j < hashMap[i].size(); j++)
					{
						LinkedList<Entry<K,V>> entries = new LinkedList<Entry<K,V>>();
						Entry<K,V> migrate = hashMap[i].get(j);
						int index = hash(migrate.getKey());
						System.out.println("Migrating " + migrate.getKey());
						tempArray[index] = entries;
						entries.add(migrate);
					}
				}
			}
			System.out.println("Done migrating");
			hashMap = tempArray;
			tableSize = tableSizes[nextTableSize];
			nextTableSize++;
		}
		return oldValue;
	}

	/**
	 * Removes the mapping for the specified key from this map if present. This
	 * method does nothing if the key is not in the map.
	 *
	 * @param key
	 *            key whose mapping is to be removed from the map
	 * @return the previous value associated with key, or null if there was no
	 *         mapping for key.
	 * @throws NullPointerException
	 *             if key is null
	 */

	public V remove(K key)
	{
		if (key == null)
		{
			throw new NullPointerException();
		}
		if (hashMap[hash(key)] != null)
		{
			System.out.println("Remove " + hash(key));
			LinkedList<Entry<K,V>> curr = hashMap[hash(key)];
			for (int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).getKey().equals(key))
				{
					V oldValue = curr.get(i).getValue();
					curr.remove(i);
					if (curr.size() == 0)
					{
						numEntries--;
					}
					return oldValue;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the greatest key less than or equal to the given key, or null if
	 * there is no such key. Throws NullPointerException if key is null.
	 * 
	 * @param key
	 *            key whose floor should be found
	 * @return the largest key smaller than the one passed to it
	 * @throws NullPointerException
	 *             if key is null
	 */
	public K floorKey(K key)
	{
		if (key == null)
		{
			throw new NullPointerException();
		}
		K floorKey = null;
		for (int i = 0; i < hashMap.length; i++)
		{
			LinkedList<Entry<K, V>> curr = hashMap[i];
			if (curr != null)
			{
				for (int j = 0; j < curr.size(); j++)
				{
					if (floorKey == null)
					{
						floorKey = curr.get(j).getKey();
					}
					if (curr.get(j).getKey().compareTo(key) <= 0
							&& curr.get(j).getKey().compareTo(floorKey) > 0)
					{
						floorKey = curr.get(j).getKey();
					}
					else
					{
						floorKey = null;
					}
				}
			}

		}
		return floorKey;
	}
}