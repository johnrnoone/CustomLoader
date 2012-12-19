package classLoader;
import java.util.Arrays;
import java.util.Iterator;


public class MemCopy {
	public static void main (String args[]) {
	 int[] src = {1, 2, 3, 4};
	 int[] dst = Arrays.copyOf(src, src.length);
	 System.out.println(Arrays.toString(dst));
	 System.out.println(Arrays.toString(src.clone()));
	 System.out.println(Arrays.toString(myCopy(src, src.length)));
	}
	
		private static int[] myCopy (int[] inArray, int len) {
		int outArray[] = new int[len];
        int i = 0;
		while (i<len) outArray[i]=inArray[i++];
		return outArray;
	}

}
