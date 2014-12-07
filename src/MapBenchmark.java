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
	private long stdDiv = 0;
	private long totalInput = 0;
	private double sum = 0;
	private ArrayList<Long> timeTable = new ArrayList<Long>();
	private SimpleMapADT map;
	
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
		hashBenchmark.put(fileName, numIter);
		treeBenchmark.put(fileName, numIter);
		
	}
	private void put(String fileName, int numIter) throws IOException
	{
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
				long startTime = System.currentTimeMillis();
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					Object key = token.nextElement();
					Object value = token.nextElement();
					map.put((Comparable) key, value);
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
		System.out.println("Total number of items: " + (totalInput/numIter));
		System.out.println("Hash: put");
		System.out.println("--------------------");
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Mean: " + String.format("%.3f", totalTime/(totalInput/numIter)));
		System.out.println("Std Dev: " + String.format("%.3f", (Math.sqrt((sum/(totalInput/numIter))))));
	}
}