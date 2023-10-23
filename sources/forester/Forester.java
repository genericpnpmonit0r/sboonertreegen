package forester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import net.minecraft.src.Block;

/** This class contains only static utility methods to use in the various tree classes, the tree options as well as the tree getter can be found in {@link forester.Tree} */
public class Forester {
	private Forester() {}; //static methods only.
	
	public static int[] range(int start, int end, int step) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		
		if (step < 0) {
			for (int i = start; i > end; i += step) {
				arr.add(i);
			}
		} else {
			for (int i = start; i < end; i += step) {
				arr.add(i);
			}
		}

		return arr.stream().mapToInt(i -> i).toArray();
	}
	
	public static Integer[] primitiveToObjInt(int[] primitive) {
		int length = primitive.length;
		Integer[] out = new Integer[length];
		for (int i = 0; i < length; i++) {
			out[i] = Integer.valueOf(primitive[i]);
		}
		return out;
	}
	
	public static int[] objToPrimitive(Integer[] obj) {
		int length = obj.length;
		int[] out = new int[length];
		for (int i = 0; i < length; i++) {
			out[i] = obj[i].intValue();
		}
		return out;
	}
	
	public static int max_key_abs(int[] arr) {
		int biggestABSValueSoFar = 0;
		int biggestValueSoFar = 0;
		
		for (int i = 0; i < arr.length; i++) {
			int abs = (int)Math.abs(arr[i]);
			if (abs > biggestABSValueSoFar) {
				biggestABSValueSoFar = abs;
				biggestValueSoFar = arr[i];
			}
		}
		
		return biggestValueSoFar;
	}
	
	public static int getArrayIndex(int[] arr, int value) {
        int k = -1;
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                k = i;
                break;
            }
        }
        
	    return k;
	}
	
	public static int[] getOtherIndexes(int[] arr, int aroundIndex) {
		int[] indexes = new int[2];
		int i2 = 0;
		
		for (int i = 0; i < arr.length; i++) {
			if (i == aroundIndex) {
				if (i - 1 > -1) {
					indexes[i2] = i - 1;
					i2++;
				} else {
					indexes[i2] = arr.length - 1;
					i2++;
				}
				
				if (i + 1 <= arr.length - 1) {
					indexes[i2] = i + 1;
					i2++;
				} else {
					indexes[i2] = 0;
					i2++;
				}
			}
		}
		
		return indexes;
	}
	
	public static void assign_value(int x, int y, int z, int id, int meta, MCWorldAccessor mcmap) {
		mcmap.setBlockAndMetadataWithNotify(x, y, z, id, meta);
	}

	public static int choice(Random rnd, int... src) {
		return src[rnd.nextInt(src.length)];
	}

	public static int dist_to_mat(int[] cord, int[] vec, int[] matidxlist, 
			MCWorldAccessor mcmap, boolean invert, Integer limit) {
		double[] curcord = { 0, 0, 0 };
		for (int i = 0; i < 3; i++) {
			curcord[i] = i + .5;
		}
		
		int iterations = 0;
		boolean on_map = true;

		while (on_map) {
			int x = (int) curcord[0];
			int y = (int) curcord[1];
			int z = (int) curcord[2];
			Block block = Block.blocksList[mcmap.getBlockId(x, y, z)];
			
			if (block == null) {
				break;
			} else {
				int blockID = block.blockID;

				if (Arrays.asList(primitiveToObjInt(matidxlist)).contains(blockID) && !invert) {
					break;
				} else if (!Arrays.asList(primitiveToObjInt(matidxlist)).contains(blockID) && invert) {
					break;
				} else {
					for (int i = 0; i < 3; i++) {
						curcord[i] = curcord[i] + vec[i];
					}
					iterations++;
				}
			}
			
			if (limit != null && iterations > limit) {
				break;
			}
		}
		
		return iterations;
	}
}