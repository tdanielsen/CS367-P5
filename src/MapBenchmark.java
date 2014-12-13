///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            CS-367 P5
// Files:            MapBenchmark.java, Entry.java, SimpleHashMap.java,
//                   SimpleTreeMap.java, SimpleMapADT.java
// Semester:         CS367 Fall 2014
//
// Author:           Tim Danielsen
// Email:            tdanielsen@wisc.edu
// CS Login:         danielsen
// Lecturer's Name:  J. Skrentny
// Lab Section:      N/A
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Runs a benchmark test of an input file for a user determined number of
 * iterations, displaying the results of the put, get, floorKey, and remove
 * methods in both SimpleHashMap and SimpleTreeMap
 *
 * <p>Bugs: None known
 *
 * @author Tim Danielsen
 */
public class MapBenchmark<K, V>
{
	//The total time it takes for the put operation
	private double totalPutTime = 0; 
	//The total time it takes for the get operation
	private double totalGetTime = 0; 
	//The total time it takes for the floorKey operation
	private double totalFloorKeyTime = 0;
	//The total time it takes for the remove operation
	private double totalRemoveTime = 0;
	//A list of the times for the put operation for each iteration
	private ArrayList<Long> timePutTable = new ArrayList<Long>();
	//A list of the times for the get operation for each iteration
	private ArrayList<Long> timeGetTable = new ArrayList<Long>();
	//A list of the times for the floorKey operation for each iteration
	private ArrayList<Long> timeFloorKeyTable = new ArrayList<Long>();
	//A list of the times for the remove operation for each iteration
	private ArrayList<Long> timeRemoveTable = new ArrayList<Long>();
	private SimpleMapADT<Integer, String> map;
	
	/**
	 * Runs the benchmark of both data structures
	 *
	 * @param args the file to be inputed and the number of iterations to be done
	 * @return the results of the benchmark for each map
	 */
	public static void main(String[] args) throws IOException
	{
		int numIter = Integer.parseInt(args[1]); // number of iterations to run
		SimpleHashMap<Integer, String> hash = new SimpleHashMap<Integer, String>();
		SimpleTreeMap<Integer, String> tree = new SimpleTreeMap<Integer, String>();
		String fileName = args[0];
		MapBenchmark<Integer, String> hashBenchmark = new MapBenchmark<Integer, String>(hash);
		MapBenchmark<Integer, String> treeBenchmark = new MapBenchmark<Integer, String>(tree);
		
		hashBenchmark.benchmark(fileName, numIter, "HashMap");

		treeBenchmark.benchmark(fileName, numIter, "TreeMap");

	}
	/**
	 * Runs each operation (put, get, floorKey, and remove) and computes their
	 * min, max, and mean times as well as the standard deviation
	 *
	 * @param fileName the name of the file to use the benchmark on
	 * @param numIter the number of times the file is run through
	 * @param mapType the type of map being bench marked
	 */
	private void benchmark(String fileName, int numIter, String mapType) throws IOException
	{
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			// Reads in the files
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			// Holds the inputs to be used from the file that was read in
			ArrayList<Entry<Integer,String>> inputs = readIn(in, fileName);
			
			// The total time it took to do the put operation for all inputs
			long totalTime = 0;
			for (int i = 0; i < inputs.size(); i++)
			{
				Integer key = inputs.get(i).getKey();
				String value = inputs.get(i).getValue();
				long startTime = System.currentTimeMillis();
				map.put(key, value);
				long elapsed = System.currentTimeMillis() - startTime;
				totalTime += elapsed;
			}
			timePutTable.add(totalTime);
			totalPutTime = totalPutTime + totalTime;
			
			// Resets totalTime
			totalTime = 0;
			// Measures total time it took to do the get operation for all inputs
			for (int i = 0; i < inputs.size(); i++)
			{	
				Integer key = inputs.get(i).getKey();
				long startTime = System.currentTimeMillis();
				map.get(key);
				long elapsed = System.currentTimeMillis() - startTime;
				totalTime += elapsed;
			}
			timeGetTable.add(totalTime);
			totalGetTime = totalGetTime + totalTime;
			
			totalTime = 0;
			// Measures total time it took to do the floorKey operation for all inputs
			for (int i = 0; i < inputs.size(); i++)
			{
				Integer key = inputs.get(i).getKey();
				long startTime = System.currentTimeMillis();
				map.floorKey(key);
				long elapsed = System.currentTimeMillis() - startTime;
				totalTime += elapsed;
			}
			timeFloorKeyTable.add(totalTime);
			totalFloorKeyTime = totalFloorKeyTime + totalTime;
			
			totalTime = 0;
			// Measures total time it took to do the remove operation for all inputs
			for (int i = 0; i < inputs.size(); i++)
			{	
				Integer key = inputs.get(i).getKey();
				long startTime = System.currentTimeMillis();
				map.remove(key);	
				long elapsed = System.currentTimeMillis() - startTime;
				totalTime += elapsed;
			}
			timeRemoveTable.add(totalTime);
			totalRemoveTime = totalRemoveTime + totalTime;
		}
		outPrint(mapType, "put", totalPutTime, numIter, timePutTable);
		outPrint(mapType, "get", totalGetTime, numIter, timeGetTable);
		outPrint(mapType, "floorKey", totalFloorKeyTime, numIter, timeFloorKeyTable);
		outPrint(mapType, "remove", totalRemoveTime, numIter, timeRemoveTable);
	}
	/**
	 * Prints out the min, max, mean, and standard deviation for an operation
	 * 
	 * @param mapType the type of map that the results are coming from
	 * @param op the operation the results are coming from
	 * @param totalTime the total amount of time the operation took for all iterations
	 * @param numIter the number of iterations 
	 * @param timeTable an array list that holds the times of the operation for every iteration
	 */
	private void outPrint(String mapType, String op, double totalTime, int numIter, ArrayList<Long> timeTable)
	{
		long max = 0;
		long min = -1;
		long time = 0;
		double sum = 0;
		for (int i = 0; i < timeTable.size(); i++)
		{
			time = timeTable.get(i);
			if (time > max)
			{
				max = time;
			}
			if (min == -1)
			{
				min = time;
			}
			if (time < min)
			{
				min = time;
			}
			//Finds the summation part for finding standard deviation
			sum += Math.pow((timeTable.get(i) - (totalTime/timeTable.size())), 2);
		}
		String mean = String.format("%.3f", totalTime/timeTable.size());
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/timeTable.size()))));
		System.out.println(mapType + ": " + op);
		System.out.println("--------------------");
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Mean: " + mean);
		System.out.println("Std Dev: " + stdDiv);
		System.out.println();
	}
	/**
	 * Creates a map from a class that implements SimpleMapADT
	 *
	 * @param aMap a class that implements SimpleMapADT
	 * @return the map inputed, but now a MapBenchmark
	 */
	private MapBenchmark(SimpleMapADT<Integer, String> aMap)
	{
		 map = aMap; 
	}
	/**
	 * Reads in a file and makes an array list of Entries
	 *
	 * @param in a BufferedReader that reads in the file
	 * @param fileName the name of the file to be read in
	 * @return an array list of Entries from the file
	 */
	private ArrayList<Entry<Integer,String>> readIn(BufferedReader in, String fileName) throws NumberFormatException, IOException
	{
		ArrayList<Entry<Integer,String>> entryList = new ArrayList<Entry<Integer,String>>();
		try
		{	
			String line;	
			// Goes through each line
			while ((line = in.readLine()) != null)
			{
				StringTokenizer token = new StringTokenizer(line, " ");
				Integer key = Integer.parseInt(token.nextToken());
				String value = token.nextToken();
				Entry<Integer, String> newEntry = new Entry<Integer, String>(
						key, value);
				entryList.add(newEntry);
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File: " + fileName + " Not Found.");
		}
		return entryList;
	}
}