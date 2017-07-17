package org.team5940.pantry.processing_network.data_flow;

import java.lang.reflect.Array;
import java.util.Arrays;

public class NodeUtils {

	public static <T extends Object> T[] mergeArrays(T[] array1, T... array2) {
		T[] newArray = Arrays.copyOf(array1, array1.length + array2.length);
		
		for (int i = 0; i < array2.length; i++) {
			newArray[i + array1.length] = array2[i];
		}
		
		return newArray;
	}
}
