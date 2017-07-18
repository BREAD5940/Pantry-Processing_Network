package org.team5940.pantry.processing_network;

import java.util.Arrays;

/**
 * Various utilities that Nodes may need to use.  
 * 
 * @author Michael Bentley
 *
 */
public class NodeUtils {

	/**
	 * Merges two arrays of type T.
	 * 
	 * @param array1
	 *            One array of type T.
	 * @param array2
	 *            An varargs (the ... or a fancy array) of type T.
	 * @return The merged arrays.
	 */
	public static <T extends Object> T[] mergeArrays(T[] array1, T... array2) {
		T[] newArray = Arrays.copyOf(array1, array1.length + array2.length);

		for (int i = 0; i < array2.length; i++) {
			newArray[i + array1.length] = array2[i];
		}

		return newArray;
	}
}
