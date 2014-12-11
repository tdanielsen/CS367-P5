// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapBenchmark<K, V>
{
	private double totalPutTime = 0;
	private double totalGetTime = 0;
	private double totalFloorKeyTime = 0;
	private double totalRemoveTime = 0;
	private long totalInput = 0;
	private double sum = 0;
	private ArrayList<Long> timePutTable = new ArrayList<Long>();
	private ArrayList<Long> timeGetTable = new ArrayList<Long>();
	private ArrayList<Long> timeFloorKeyTable = new ArrayList<Long>();
	private ArrayList<Long> timeRemoveTable = new ArrayList<Long>();
	private SimpleMapADT<Integer, String> map;
	private ArrayList<Integer> keyList = new ArrayList<Integer>();
	
	public MapBenchmark(SimpleMapADT<Integer, String> aMap)
	{
		 map = aMap; 
	}
	@SuppressWarnings("unchecked")
	public static  <K, V> void main(String[] args) throws IOException
	{
		int numIter = Integer.parseInt(args[1]); // number of iterations to run
		SimpleHashMap<Integer, String> hash = new SimpleHashMap<Integer, String>();
		SimpleTreeMap<Integer, String> tree = new SimpleTreeMap<Integer, String>();
		String fileName = args[0];
		MapBenchmark<Integer, String> hashBenchmark = new MapBenchmark<Integer, String>(hash);
		MapBenchmark<Integer, String> treeBenchmark = new MapBenchmark<Integer, String>(tree);
		hashBenchmark.operation(fileName, numIter, "HashMap", "put");

		treeBenchmark.operation(fileName, numIter, "TreeMap", "put");

	}
	private void operation(String fileName, int numIter, String mapType, String op) throws IOException
	{
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
//			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
//					+ "% done \r");

			//	map = new SimpleHashMap();
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			ArrayList<Entry<Integer,String>> inputs = readIn(in, fileName);

			long startTime = System.currentTimeMillis();
			for (int i = 0; i < inputs.size(); i++)
			{
				map.put(inputs.get(i).getKey(), inputs.get(i).getValue());
			}
			long elapsed = System.currentTimeMillis() - startTime;
			timePutTable.add(elapsed);
			totalPutTime = totalPutTime + elapsed;
			totalInput++;
			//Iterate over all keys in keyList and measure their get times
			startTime = System.currentTimeMillis();
			for (int i = 0; i < inputs.size(); i++)
			{			
				map.get(inputs.get(i).getKey());
			}
			elapsed = System.currentTimeMillis() - startTime;
			timeGetTable.add(elapsed);
			totalGetTime = totalGetTime + elapsed;
			//Iterate over all keys in keyList and measure their floorKey times
			startTime = System.currentTimeMillis();
			for (int i = 0; i < inputs.size(); i++)
			{
				map.floorKey(inputs.get(i).getKey());
			}
			elapsed = System.currentTimeMillis() - startTime;
			timeFloorKeyTable.add(elapsed);
			totalFloorKeyTime = totalFloorKeyTime + elapsed;
			//Iterate over all keys in keyList and measure their remove times
			startTime = System.currentTimeMillis();
			for (int i = 0; i < inputs.size(); i++)
			{			
				map.remove(inputs.get(i).getKey());	
			}
			elapsed = System.currentTimeMillis() - startTime;
			timeRemoveTable.add(elapsed);
			totalRemoveTime = totalRemoveTime + elapsed;
		}
		outPrint(mapType, "put", totalPutTime, numIter, timePutTable);
		outPrint(mapType, "get", totalGetTime, numIter, timeGetTable);
		outPrint(mapType, "floorKey", totalFloorKeyTime, numIter, timeFloorKeyTable);
		outPrint(mapType, "remove", totalRemoveTime, numIter, timeRemoveTable);
	}
	
	private void outPrint(String mapType, String op, double totalTime, int numIter, ArrayList<Long> timeTable)
	{
		long max = 0;
		long min = -1;
		long time = 0;
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
			sum += Math.pow((timeTable.get(i) - totalTime/totalInput), 2);
		}
		String mean = String.format("%.3f", totalTime/timeTable.size());
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/totalInput))));
		System.out.println(mapType + ": " + op);
		System.out.println("--------------------");
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Mean: " + mean);
		System.out.println("Std Dev: " + stdDiv);
		System.out.println();
	}
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