package simpledb;

import simpledb.Predicate.Op;

/** A class to represent a fixed-width histogram over a single integer-based field.
 */
public class IntHistogram {
    private int buckets;
    private int min;
    private int max;
    private int width;
    private int[] histogram;
    private int numTuples = 0;
    /**
     * Create a new IntHistogram.
     * 
     * This IntHistogram should maintain a histogram of integer values that it receives.
     * It should split the histogram into "buckets" buckets.
     * 
     * The values that are being histogrammed will be provided one-at-a-time through the "addValue()" function.
     * 
     * Your implementation should use space and have execution time that are both
     * constant with respect to the number of values being histogrammed.  For example, you shouldn't 
     * simply store every value that you see in a sorted list.
     * 
     * @param buckets The number of buckets to split the input value into.
     * @param min The minimum integer value that will ever be passed to this class for histogramming
     * @param max The maximum integer value that will ever be passed to this class for histogramming
     */
    public IntHistogram(int buckets, int min, int max) {
    	this.buckets = buckets;
    	this.min = min;
    	this.max = max;
    	histogram = new int[buckets];
    	double range = (double)(max-min+1)/buckets;
    	width = (int) Math.ceil(range);
    }

    /**
     * Add a value to the set of values that you are keeping a histogram of.
     * @param v Value to add to the histogram
     */
    public void addValue(int v) {
    	if(v==max){
    		histogram[buckets-1]++;
    	}
    	else if(v==min){
    		histogram[0]++;
    	}
    	else{
    		int index = (v-min)/width; 
    		histogram[index]++;
    	}
    	//update the total number of tuples
    	numTuples++;
    }

    /**
     * Estimate the selectivity of a particular predicate and operand on this table.
     * 
     * For example, if "op" is "GREATER_THAN" and "v" is 5, 
     * return your estimate of the fraction of elements that are greater than 5.
     * 
     * @param op Operator
     * @param v Value
     * @return Predicted selectivity of this particular operator and value
     */
    public double estimateSelectivity(Predicate.Op op, int v) {
    	   int bucketIndex = (v-min)/width;
        	int height;
        	int left = bucketIndex * width + min;
        	int right = bucketIndex * width + min + width -1;

        	// some code goes here
        	if (op==Op.EQUALS) {
    		if (v<min || v>max) {
    			return 0.0;
    		} else {
    			height = histogram[bucketIndex];
        			return (double)(height/width)/numTuples;
    		}
        	}
        	if (op==Op.GREATER_THAN) {
        		if (v < min) {
        			return 1.0;
        		}
        		if (v > max-1) {
        			return 0.0;
        		} else {
    		    height = histogram[bucketIndex];
        		double b1 = (double)height/numTuples;
        		double b2 = (double)(right-v)/width;
        		double answer = b1*b2;
        		
        		for (int i=bucketIndex+1; i<histogram.length; i++) {
        			int height2 = histogram[i];
        			answer += (double)height2/numTuples;;
        		}
        		return answer;
        		}
        	}
        	if (op==Op.GREATER_THAN_OR_EQ) {
        		if (v <= min) {
        			return 1.0;
        		}
        		if (v > max) {
        			return 0.0;
        		} else {
        	    height = histogram[bucketIndex];
    		    double b1 = (double)height/numTuples;
        		double b2 = (double)(right-v)/width;
        		double answer = b1*b2;
        		
        		for (int i=bucketIndex+1; i<histogram.length; i++) {
        			int height2 = histogram[i];
        			answer += (double)height2/numTuples;
        		}
        		answer += (double)(height/width)/numTuples;
        		return answer;
        		}
        	}
        	if (op==Op.LESS_THAN) {
        		if (v <= min) {
        			return 0.0;
        		}
        		if (v > max) {
        			return 1.0;
        		} else {
    		    height = histogram[bucketIndex];
        		double b1 = (double)height/numTuples;
        		double b2 = (double)(v-left)/width;
        		double answer = b1*b2;
        		
        		for (int i=bucketIndex-1; i>=0; i--) {
        			int height2 = histogram[i];
        			answer += (double)height2/numTuples;
        		}
        		return answer;
        		}
        	}
        	if (op==Op.LESS_THAN_OR_EQ) {
        		if (v < min) {
        			return 0.0;
        		}
        		if (v >= max) {
        			return 1.0;
        		} else {
    		    height = histogram[bucketIndex];
        		double b1 = (double)height/numTuples;
        		double b2 = (double)(v-left)/width;
        		double answer = b1*b2;
        		
        		for (int i=bucketIndex-1; i>=0; i--) {
        			int height2 = histogram[i];
        			answer +=(double)height2/numTuples;
        		}
        		answer += (double)(height/width)/numTuples;
        		return answer;
        		}
        	}

        	if (op==Op.LIKE) {
    		if (v<min || v>max) {
    			return 0.0;
    		} else {
    			height = histogram[bucketIndex];
        			return (double)(height/width)/numTuples;
    		}
        	}
        	if (op==Op.NOT_EQUALS) {
    		if (v<min || v>max) {
    			return 1.0;
    		} else {
    			height = histogram[bucketIndex];
        			double answer = (double)(height/width)/numTuples;
        			return 1.0-answer;
    		}
        	}
    	    return 0.0; 
        }
    /**
     * @return
     *     the average selectivity of this histogram.
     *     
     *     This is not an indispensable method to implement the basic
     *     join optimization. It may be needed if you want to
     *     implement a more efficient optimization
     * */
    public double avgSelectivity()
    {
        // some code goes here
        return 1.0;
    }
    
    /**
     * @return A string describing this histogram, for debugging purposes
     */
    public String toString() {
        // some code goes here
        return null;
    }
}
