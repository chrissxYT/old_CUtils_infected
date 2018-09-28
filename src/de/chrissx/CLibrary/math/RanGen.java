package de.chrissx.CLibrary.math;

import java.util.Random;

public class RanGen {

	public static long genLong(long start, long end) {
		Random rand = new Random();
		long l = start;
		float t = (float) ((float) (System.currentTimeMillis() / 1000) / (float) (System.nanoTime() / 1000000));
		long lf = (long) (end / t);
		if(lf < start) {
			lf /= rand.nextFloat();
		}
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf /= (rand.nextDouble() * 2);
		lf /= (rand.nextDouble() * 2);
		if(lf < start) {
			lf += genLong(start / 10, end / 10);
		}
		if(lf > end) {
			lf -= genLong(start / 10, end / 10);
		}
		l = lf;
		if(l < start) {
			return genLong(start, end);
		}
		if(l > end) {
			return genLong(start, end);
		}
		return l;
	}
	
	public static int genInt(int end) {
		Random rnd = new Random();
		return rnd.nextInt(end);
	}
	
	public static int genInt(int start, int end) {
		Random rand = new Random();
		int i = start;
		float t = (float) ((float) (System.currentTimeMillis() / 1000) / (float) (System.nanoTime() / 1000000));
		int lf = (int) (end / t);
		if(lf < start) {
			lf /= rand.nextFloat();
		}
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf *= rand.nextFloat();
		lf /= (rand.nextDouble() * 2);
		lf /= (rand.nextDouble() * 2);
		if(lf < start) {
			lf += genInt(start / 10, end / 10);
		}
		if(lf > end) {
			lf -= genInt(start / 10, end / 10);
		}
		i = lf;
		if(i < start) {
			return genInt(start, end);
		}
		if(i > end) {
			return genInt(start, end);
		}
		return i;
	}
}