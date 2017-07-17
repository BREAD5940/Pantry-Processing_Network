package org.team5940.pantry.processing_network.data_flow;

import java.lang.reflect.Array;
import java.util.Arrays;

public class NodeUtils {

	public static <T extends Object> T[] appendValueToArray(T value, T... array) {
		T[] newArray = Arrays.copyOf(array, array.length + 1);
		newArray[array.length] = value;
		
		return newArray;
	}

}
