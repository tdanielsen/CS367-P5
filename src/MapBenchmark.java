// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapBenchmark<K, V>
{
	private static long min = -1;
	private static long max = 0;
	private static double totalTime = 0;
	private static long stdDiv = 0;
	private static long totalInput = 0;
	private static long sum = 0;
	private static ArrayList<Long> timeTable = new ArrayList<Long>();
	
	@SuppressWarnings("unchecked")
	public static  <K, V> void main(String[] args) throws IOException
	{
		int numIter = Integer.parseInt(args[1]); // number of iterations to run
		SimpleHashMap hash = new SimpleHashMap();
		SimpleTreeMap tree = new SimpleTreeMap();
		String fileName = args[0];
		
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		System.out.println();
		
		put(hash, max, min, stdDiv, in, numIter, fileName, sum, totalTime, totalInput, timeTable);
//			// Basic progress bar
//			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
//					+ "% done \r");

		
	}
	private static void put(SimpleMapADT map, long max, long min, long stdDiv,
			BufferedReader in, int numIter, String fileName, long sum,
			double totalTime, long totalInput, ArrayList<Long> timeTable) throws IOException
	{
		for (int ndx = 0; ndx < numIter; ndx++)
		{
			try
			{
			//	map = new SimpleHashMap();
				in = new BufferedReader(new FileReader(fileName));
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
				System.out.println("Elapsed time: " + elapsed);
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
		System.out.println("Mean: " + totalTime/(totalInput/numIter));
		System.out.println("Std Dev: " + Math.sqrt((sum/(totalInput/numIter))));
	}
}