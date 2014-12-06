import java.util.ArrayList;
import java.util.LinkedList;


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
		hash.put(2, "monkey");
		hash.put(2, "Gut");
		hash.put(1, "Bot");
		hash.put(42, "moot");
		System.out.println(hash.floorKey(2));
		

	}

}
