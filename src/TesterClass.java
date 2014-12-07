import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;


public class TesterClass
{

	@SuppressWarnings("unchecked")
	public static <V, K> void main(String[] args)
	{
		LinkedList<String>[] control = new LinkedList[11];
		
		LinkedList<String> strings = new LinkedList<String>(); 
		java.util.Arrays.fill(control, strings);
		control[0].add("Hat");
		System.out.println(control[0].get(0));
		System.out.println(control[1].get(0));
		control[0].add("Matt?");
		LinkedList<String> curr = control[0];
		System.out.println("* " + curr.get(0));
		Entry newEntry = new Entry(1, "monkey");
		SimpleHashMap hash = new SimpleHashMap();
		System.out.println(hash.size());
		hash.put(1, "monkey");
		hash.put(2, "Gut");
		hash.put(3, "Bot");
		hash.put(4, "moot");
		hash.put(5, "moot");
		hash.put(6, "moot");
		hash.put(7, "moot");
		hash.put(8, "Zork");
		hash.put(9, "moot");
		hash.put(10, "moot");
		System.out.println(hash.get(8));
		hash.put(11, "Hallo");
		hash.put(17, "Problem Child");
		double max = 0;
		Random rng = new Random();
		rng.setSeed(20);
		for (int i = 20; i > 0; i--)
		{
			double nextNum = 6 * Math.random();
			if (nextNum > max)
			{
				max = nextNum;
			}
		}
		System.out.println(max);
	}

}
