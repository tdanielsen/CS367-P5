///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  MapBenchmark.java
// File:             SimpleHashMap.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.LinkedList;

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
	private int tableSize; // the current table size
	private LinkedList<Entry<K, V>>[] hashMap;
	// keeps track how many elements in the array are used
	private double arrayElementsUsed;
	// the index of the next table size to be use from tableSizes
	private int nextTableSize;

	/**
	 * Makes a hash map with a starting size of the first size in tableSizes
	 *
	 * <p>
	 * Bugs: None known
	 *
	 * @author Tim Danielsen
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashMap()
	{
		tableSize = tableSizes[0];
		hashMap = new LinkedList[tableSize];
		arrayElementsUsed = 0;
		nextTableSize = 1;
	}

	/**
	 * makes a hash index from a given key that will be used to insert the Entry
	 * in to the hash map
	 *
	 * @param k
	 *            a key from an Entry
	 * @return an integer value to be used to store the Entry into the hash map
	 */
	private int hash(K k)
	{
		int hashIndex = (k.hashCode() % tableSize) - 1;
		// Handles the case of the hashIndex being negative
		if (hashIndex < 0)
		{
			hashIndex = hashIndex + (tableSize - 1);
		}
		return hashIndex;
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
		// Checks for valid input
		if (key == null)
		{
			throw new NullPointerException();
		}
		int hashKey = hash(key);
		LinkedList<Entry<K, V>> curr = hashMap[hashKey];
		if (curr != null)
		{
			for (int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).getKey().equals(key))
				{
					// returns the value if key is found
					return (V) curr.get(i).getValue();
				}
			}
		}
		// returns null if the key is not in the map
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
		// Checks for valid input
		if (key == null || value == null)
		{
			throw new NullPointerException();
		}
		Entry<K, V> newEntry = new Entry<K, V>(key, value);
		// keeps the previous value if there is one
		V oldValue = null;
		LinkedList<Entry<K, V>> curr = hashMap[hash(key)];
		// if the element in the array hasn't been used yet, this makes it able
		// to be used
		if (curr == null)
		{
			hashMap[hash(key)] = curr = new LinkedList<Entry<K, V>>();
		}
		if (curr.size() == 0)
		{
			curr.add(newEntry);
			arrayElementsUsed++;
		}
		else
		{
			for (int i = 0; i < curr.size(); i++)
			{
				// Checks if the key is already in the map
				if (curr.get(i).getKey().equals(key))
				{
					oldValue = curr.get(i).getValue();
					curr.get(i).setValue(value);
					return oldValue;
				}
			}
			curr.add(newEntry);
		}
		// checks to see if the map needs to grow
		if (arrayElementsUsed / tableSize >= lf)
		{
			@SuppressWarnings("unchecked")
			// Makes a temporary array of the next table size
			LinkedList<Entry<K, V>>[] tempArray = new LinkedList[tableSizes[nextTableSize]];
			for (int i = 0; i < hashMap.length; i++)
			{
				if (hashMap[i] != null)
				{
					// moves all non-null entries of the old array into the new
					// one
					for (int j = 0; j < hashMap[i].size(); j++)
					{
						LinkedList<Entry<K, V>> entries = new LinkedList<Entry<K, V>>();
						Entry<K, V> migrate = hashMap[i].get(j);
						int hashMigrateKey = hash(migrate.getKey());
						tempArray[hashMigrateKey] = entries;
						entries.add(migrate);
					}
				}
			}
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
		// Checks for valid input
		if (key == null)
		{
			throw new NullPointerException();
		}
		LinkedList<Entry<K, V>> curr = hashMap[hash(key)];
		if (curr != null)
		{
			for (int i = 0; i < curr.size(); i++)
			{
				if (curr.get(i).getKey().equals(key))
				{
					V oldValue = curr.get(i).getValue();
					curr.remove(i);
					if (curr.size() == 0)
					{
						arrayElementsUsed--;
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
		// Checks for valid input
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
					// sets the floorKey to a value so it can be tested against
					if (floorKey == null)
					{
						floorKey = curr.get(j).getKey();
					}
					if (curr.get(j).getKey().compareTo(key) == 0)
					{
						floorKey = curr.get(j).getKey();
						// Don't need to keep comparing if the key is the same
						// as the key being looked for
						return floorKey;
					}
					if (curr.get(j).getKey().compareTo(key) < 0
							&& curr.get(j).getKey().compareTo(floorKey) > 0)
					{
						floorKey = curr.get(j).getKey();
					}
					// In case the key being tested wasn't a floor key, this
					// resets it
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