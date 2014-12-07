// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapBenchmark<K, V>
{
	private long min = -1;
	private long max = 0;
	private double totalTime = 0;
	private long totalInput = 0;
	private double sum = 0;
	private ArrayList<Long> timeTable = new ArrayList<Long>();
	private SimpleMapADT map;
	private ArrayList<K> keyList = new ArrayList<K>();
	
	public MapBenchmark(SimpleMapADT aMap)
	{
		 map = aMap; 
	}
	@SuppressWarnings("unchecked")
	public static  <K, V> void main(String[] args) throws IOException
	{
		int numIter = Integer.parseInt(args[1]); // number of iterations to run
		SimpleHashMap hash = new SimpleHashMap();
		SimpleTreeMap tree = new SimpleTreeMap();
		String fileName = args[0];
		MapBenchmark hashBenchmark = new MapBenchmark(hash);
		MapBenchmark treeBenchmark = new MapBenchmark(tree);
		hashBenchmark.put(fileName, numIter, "HashMap", "put");
		hashBenchmark.get(fileName, hash, numIter, "HashMap", "get");
		hashBenchmark.floorKey(fileName, hash, numIter, "HashMap", "floorKey");
		hashBenchmark.remove(fileName, hash, numIter, "HashMap", "remove");
		treeBenchmark.put(fileName, numIter, "TreeMap", "put");
		treeBenchmark.get(fileName, tree, numIter, "TreeMap", "get");
		treeBenchmark.floorKey(fileName, tree, numIter, "TreeMap", "floorKey");
		treeBenchmark.remove(fileName, tree, numIter, "TreeMap", "remove");
	}
	private void put(String fileName, int numIter, String mapType, String op) throws IOException
	{
		min = -1;
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			try
			{
			//	map = new SimpleHashMap();
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				String line;
				long startTime = System.currentTimeMillis();;
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					Object key = token.nextElement();
					Object value = token.nextElement();
					map.put((Comparable) key, value);
					keyList.add((K) key);
					
					totalInput++;
				}
				long elapsed = System.currentTimeMillis() - startTime;
//				System.out.println("Elapsed time: " + elapsed);
				timeTable.add(elapsed);
				if (elapsed > max)
				{
					max = elapsed;
				}
				if (min == -1)
				{
					min = elapsed;
				}
				if (elapsed < min)
				{
					min = elapsed;
				}
				totalTime = totalTime + elapsed;
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File: " + fileName + " Not Found.");
			}
		}
		for (int i = 0; i < timeTable.size(); i++)
		{
			sum += Math.pow((timeTable.get(i) - totalTime/(totalInput/numIter)), 2);
		}
		String mean = String.format("%.3f", totalTime/(totalInput/numIter));
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/(totalInput/numIter)))));
		outPrint(mapType, op, min, max, mean, stdDiv);
	}
	private void get(String fileName, SimpleMapADT map, int numIter, String mapType, String op) throws IOException
	{
		min = -1;
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			
				
			for (int i = 0; i < keyList.size(); i++)
			{
				long startTime = System.currentTimeMillis();
				map.get((Comparable) keyList.get(i));
				long elapsed = System.currentTimeMillis() - startTime;
//					System.out.println("Elapsed time: " + elapsed);
				timeTable.add(elapsed);
				if (elapsed > max)
				{
					max = elapsed;
				}
				if (min == -1)
				{
					min = elapsed;
				}
				if (elapsed < min)
				{
					min = elapsed;
				}
				totalTime = totalTime + elapsed;
			}

		}
		for (int i = 0; i < timeTable.size(); i++)
		{
			sum += Math.pow((timeTable.get(i) - totalTime/(totalInput/numIter)), 2);
		}
		String mean = String.format("%.3f", totalTime/(totalInput/numIter));
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/(totalInput/numIter)))));
		outPrint(mapType, op, min, max, mean, stdDiv);
	}
	private void floorKey(String fileName, SimpleMapADT map, int numIter, String mapType, String op) throws IOException
	{
		min = -1;
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			try
			{
			//	map = new SimpleHashMap();
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				String line;
				long startTime = System.currentTimeMillis();;
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					Object key = token.nextElement();
					map.floorKey((Comparable) key);
					token.nextElement();
				}
				long elapsed = System.currentTimeMillis() - startTime;
//				System.out.println("Elapsed time: " + elapsed);
				timeTable.add(elapsed);
				if (elapsed > max)
				{
					max = elapsed;
				}
				if (min == -1)
				{
					min = elapsed;
				}
				if (elapsed < min)
				{
					min = elapsed;
				}
				totalTime = totalTime + elapsed;
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File: " + fileName + " Not Found.");
			}
		}
		for (int i = 0; i < timeTable.size(); i++)
		{
			sum += Math.pow((timeTable.get(i) - totalTime/(totalInput/numIter)), 2);
		}
		String mean = String.format("%.3f", totalTime/(totalInput/numIter));
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/(totalInput/numIter)))));
		outPrint(mapType, op, min, max, mean, stdDiv);
	}
	private void remove(String fileName, SimpleMapADT map, int numIter, String mapType, String op) throws IOException
	{
		min = -1;
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			try
			{
			//	map = new SimpleHashMap();
				BufferedReader in = new BufferedReader(new FileReader(fileName));
				String line;
				long startTime = System.currentTimeMillis();;
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					Object key = token.nextElement();
					map.remove((Comparable) key);
					token.nextElement();
				}
				long elapsed = System.currentTimeMillis() - startTime;
//				System.out.println("Elapsed time: " + elapsed);
				timeTable.add(elapsed);
				if (elapsed > max)
				{
					max = elapsed;
				}
				if (min == -1)
				{
					min = elapsed;
				}
				if (elapsed < min)
				{
					min = elapsed;
				}
				totalTime = totalTime + elapsed;
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File: " + fileName + " Not Found.");
			}
		}
		for (int i = 0; i < timeTable.size(); i++)
		{
			sum += Math.pow((timeTable.get(i) - totalTime/(totalInput/numIter)), 2);
		}
		String mean = String.format("%.3f", totalTime/(totalInput/numIter));
		String stdDiv = String.format("%.3f", (Math.sqrt((sum/(totalInput/numIter)))));
		outPrint(mapType, op, min, max, mean, stdDiv);
	}
	private void outPrint(String mapType, String op, long min, long max, String mean, String stdDiv)
	{
		System.out.println(mapType + ": " + op);
		System.out.println("--------------------");
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Mean: " + mean);
		System.out.println("Std Dev: " + stdDiv);
		System.out.println();
	}
}