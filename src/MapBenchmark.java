// TODO *** add comments as specified in the commenting guide ***

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MapBenchmark
{

	@SuppressWarnings("unchecked")
	public static <K, V> void main(String[] args) throws IOException
	{
		int numIter = Integer.parseInt(args[1]); // number of iterations to run
		SimpleHashMap hash = new SimpleHashMap();
		SimpleTreeMap tree = new SimpleTreeMap();
		String fileName = args[0];
		ArrayList<Long> timeTableHash = new ArrayList<Long>();
		ArrayList<Long> timeTableTree = new ArrayList<Long>();
		long hashMin = 0;
		long treeMin = 0;
		long hashMax = 0;
		long treeMax = 0;
		long hashTot = 0;
		long treeTot = 0;
		long hashStanDiv = 0;
		long treeStanDiv = 0;
		long totalInput = 0;
		

		for (int ndx = 0; ndx < numIter; ndx++)
		{
			// Basic progress bar
			System.out.print(String.format("%.2f", 100 * ndx / (float) numIter)
					+ "% done \r");
			try
			{
				BufferedReader in = new BufferedReader(new FileReader(
						fileName));
				String line;
				long startTime = System.currentTimeMillis();
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					K key = (K) token.nextElement();
					V value = (V) token.nextElement();
					hash.put((Comparable) key, value);
					tree.put((Comparable) key, value);
					totalInput++;
				}
				long elapsed = System.currentTimeMillis() - startTime;
				timeTableHash.add(elapsed);
				if (elapsed > hashMax)
				{
					hashMax = elapsed;
				}
				if (elapsed < hashMin)
				{
					hashMin = elapsed;
				}
				hashTot = hashTot + elapsed;
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File: " + fileName + " Not Found.");
			}
			try
			{
				BufferedReader in = new BufferedReader(new FileReader(
						fileName));
				String line;
				long startTime = System.currentTimeMillis();
				// Goes through each line
				while ((line = in.readLine()) != null)
				{
					StringTokenizer token = new StringTokenizer(line, " ");
					K key = (K) token.nextElement();
					V value = (V) token.nextElement();
					tree.put((Comparable) key, value);
				}
				long elapsed = System.currentTimeMillis() - startTime;
				timeTableTree.add(elapsed);
				if (elapsed > treeMax)
				{
					treeMax = elapsed;
				}
				if (elapsed < treeMin)
				{
					treeMin = elapsed;
				}
				treeTot = treeTot + elapsed;
				in.close();
			}
			catch(FileNotFoundException e)
			{
				System.out.println("File: " + fileName + " Not Found.");
			}
			long total = 0;
			for (int i = 0; i < timeTableHash.size(); i++)
			{
				total += Math.pow((timeTableHash.get(i) - hashTot/totalInput), 2);
			}
			
			System.out.println("Min: " + hashMin);
			System.out.println("Max: " + hashMax);
			System.out.println("Mean: " + hashTot/totalInput);
			System.out.println("Standard Deviation: " + Math.sqrt((total/totalInput)));
		}
	}
}