
public class TesterClass
{

	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		int[] test = {1,2,3,4,5,7};
		int[] tempArray = new int[10];
		System.out.println(test.length);
		System.arraycopy(test, 0, tempArray, 0, test.length);
		test = tempArray;
		System.out.println(test.length);
		for (int i = 0; i < test.length; i++)
		{
			System.out.println(test[i]);
		}
		
		SimpleHashMap hash = new SimpleHashMap();
		System.out.println(hash.size());
		hash.put(2, "monkey");

	}

}
