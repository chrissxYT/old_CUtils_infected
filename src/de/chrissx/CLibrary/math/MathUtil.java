package de.chrissx.CLibrary.math;

import java.util.ArrayList;
import java.util.List;

public class MathUtil {

	public int max(List<Integer> ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.size() - 1; i += 2) {
			results.add(Math.max(ints.get(i), ints.get(i + 1)));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return max(results);
		}
	}
	
	public int min(List<Integer> ints) {
		List<Integer> results = new ArrayList<Integer>();
		for(int i = 0; i < ints.size() - 1; i += 2) {
			results.add(Math.min(ints.get(i), ints.get(i + 1)));
		}
		if(results.size() > 1) {
			return results.get(0);
		}else {
			return min(results);
		}
	}
}