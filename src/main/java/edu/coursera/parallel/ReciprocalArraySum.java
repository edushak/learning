package edu.coursera.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class wrapping methods for implementing reciprocal array sum in parallel.
 */
public final class ReciprocalArraySum {

    private static final Logger LOGGER = Logger.getLogger(ReciprocalArraySum.class.getName());
    static {
        LOGGER.setLevel(Level.WARNING);
    }

    //    public static void main(String[] args) {
    //       int N = Integer.parseInt(args[0]);
    //       LOGGER.info("user passed N:" + N);
    //       boolean useManyTaskVersion;
    //       int ntasks;
    //   }

    /**
     * Default constructor.
     */
    private ReciprocalArraySum() {
    }

    /**
     * Sequentially compute the sum of the reciprocal values for a given array.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double seqArraySum(final double[] input) {
        double sum = 0;
        // Compute sum of reciprocals of array elements
        for (int i = 0; i < input.length; i++) {
            if (input[i] != 0)
                sum += 1 / input[i];
        }
        return sum;
    }

    /**
     * Computes the size of each chunk, given the number of chunks to create
     * across a given number of elements.
     *
     * @param nChunks   The number of chunks to create
     * @param nElements The number of elements to chunk across
     * @return The default chunk size
     */
    private static int getChunkSize(final int nChunks, final int nElements) {
        // Integer ceil
        return (nElements + nChunks - 1) / nChunks;
    }

    /**
     * Computes the inclusive element index that the provided chunk starts at,
     * given there are a certain number of chunks.
     *
     * @param chunk     The chunk to compute the start of
     * @param nChunks   The number of chunks created
     * @param nElements The number of elements to chunk across
     * @return The inclusive index that this chunk starts at in the set of
     * nElements
     */
    private static int getChunkStartInclusive(final int chunk,
                                              final int nChunks, final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        return chunk * chunkSize;
    }

    /**
     * Computes the exclusive element index that the provided chunk ends at,
     * given there are a certain number of chunks.
     *
     * @param chunk     The chunk to compute the end of
     * @param nChunks   The number of chunks created
     * @param nElements The number of elements to chunk across
     * @return The exclusive end index for this chunk
     */
    private static int getChunkEndExclusive(final int chunk, final int nChunks,
                                            final int nElements) {
        final int chunkSize = getChunkSize(nChunks, nElements);
        final int end = (chunk + 1) * chunkSize;
        if (end > nElements) {
            return nElements;
        } else {
            return end;
        }
    }

    /**
     * This class stub can be filled in to implement the body of each task
     * created to perform reciprocal array sum in parallel.
     */
    public static class ReciprocalArraySumTask extends RecursiveAction {
        /**
         * Starting index for traversal done by this task.
         */
        private final int startIndexInclusive;
        /**
         * Ending index for traversal done by this task.
         */
        private final int endIndexExclusive;
        /**
         * Input array to reciprocal sum.
         */
        private final double[] input;
        /**
         * Intermediate value produced by this task.
         */
        private double value;

        //   100 = 0.33333  102 ms
        //  1000 = 1.0000    76 ms
        //  5000 =           67 ms
        //  7000 =           67 ms
        // 10000 =           74 ms
        static int threshold = 2;
        static int numTasks = 2;

        private int chunkLength;
        /**
         * Constructor.
         *
         * @param setStartIndexInclusive Set the starting index to begin
         *                               parallel traversal at.
         * @param setEndIndexExclusive   Set ending index for parallel traversal.
         * @param setInput               Input values
         */
        ReciprocalArraySumTask(final int setStartIndexInclusive,
                               final int setEndIndexExclusive, final double[] setInput) {
            this.startIndexInclusive = setStartIndexInclusive;
            this.endIndexExclusive = setEndIndexExclusive;
            this.input = setInput;
            this.chunkLength = endIndexExclusive - startIndexInclusive;
        }

        /**
         * Getter for the value produced by this task.
         *
         * @return Value produced by this task
         */
        public double getValue() {
            return value;
        }

        /*
        @Override
        protected void compute() {
            if (chunkLength < threshold) {
                LOGGER.info("Start index is: " + startIndexInclusive);
                LOGGER.info("End index is: " + endIndexExclusive);

                for (int i = startIndexInclusive; i < endIndexExclusive; i++) {
                    LOGGER.info("Thread [" + Thread.currentThread().getName() + "] The elements used are: " + input[i]);
                    if (input[i] != 0) {
                        value += input[i];
                    }
                }
            } else {
                // find middle for given region
                int middleIndex = (endIndexExclusive + startIndexInclusive)/2;

                ReciprocalArraySumTask left = new ReciprocalArraySumTask(startIndexInclusive, middleIndex, input);
                ReciprocalArraySumTask right = new ReciprocalArraySumTask(middleIndex, endIndexExclusive, input);

                left.fork();
                right.compute();
                left.join();

                value = left.getValue() + right.getValue();
            }
        }
        */

        // double sum = 0;

        @Override
        protected void compute() {
            // TODO
            LOGGER.info("chunkLength is: " + chunkLength);
            //LOGGER.info("Num tasks are : "+this.numTasks);

            //  2 <= 2
            if (chunkLength <= threshold || chunkLength < numTasks) { // sequential
                for (int i = startIndexInclusive; i < endIndexExclusive; i++) {
                    LOGGER.info(Thread.currentThread().getName() + "   The numbers being added are: " + input[i]);
                    if (input[i] != 0) {
                        value += (1 / input[i]);
                    }
                }
            } else if (this.numTasks > 2) {

                List<Integer> indices = findIndices(chunkLength, numTasks);
                int numTasks = indices.size() - 1;

                LOGGER.info(Thread.currentThread().getName() + " The indices for size: " + chunkLength + " (numTasks="+numTasks+") are: ");
                for (int x = 0; x < indices.size(); x++)
                    LOGGER.info(Thread.currentThread().getName() + " - " + indices.get(x));

                List<ReciprocalArraySumTask> actions = new ArrayList<>(numTasks);
                for (int tasks = 0; tasks < numTasks; tasks++) {
                    Integer from = indices.get(tasks);
                    Integer to = indices.get(tasks + 1);
                    LOGGER.info(Thread.currentThread().getName() + " from=" + from + ", to=" + to);
                    ReciprocalArraySumTask task = new ReciprocalArraySumTask(from, to, input);
                    // task.numTasks = numTasks;
                    actions.add(task);
                }
                invokeAll(actions);

                for (int x = 0; x < actions.size(); x++)
                    value += actions.get(x).getValue();
                //LOGGER.info("THe value before adding is " + value);
                //value += sum;
                //LOGGER.info("THe value after adding is " + value);

            } else {
                ReciprocalArraySumTask left = new ReciprocalArraySumTask(startIndexInclusive, (startIndexInclusive + endIndexExclusive) / 2, input);
                ReciprocalArraySumTask right = new ReciprocalArraySumTask((startIndexInclusive + endIndexExclusive) / 2, endIndexExclusive, input);
                left.fork();
                right.compute();
                left.join();
                value = left.getValue() + right.getValue();
            }
        }

        /**
         * TODO: Modify this method to compute the same reciprocal sum as
         * seqArraySum, but use two tasks running in parallel under the Java Fork
         * Join framework. You may assume that the length of the input array is
         * evenly divisible by 2.
         *
         * @param input Input array
         * @return The sum of the reciprocals of the array input
         */
        protected static double parArraySum(double[] input) {
            assert input.length % 2 == 0;

            ReciprocalArraySumTask task = new ReciprocalArraySumTask(0, input.length, input);
            // ForkJoinPool pool = new ForkJoinPool().commonPool();
            // pool.invoke(task);
            task.compute();
            // Compute sum of reciprocals of array elements
            //for (int i = 0; i < input.length; i++) {
            //   sum += 1 / input[i];
            //}
            // LOGGER.info("The task val is: " + task.getValue());
            return task.getValue();
        }

        static List<Integer> findIndices(int collectionSize, int numTasks) {
            LOGGER.info("collectionSize=" + collectionSize + ", numTasks=" + numTasks);
            if (numTasks > 16)
                numTasks = 16;
            if (numTasks < 1)
                numTasks = 1;

//            if (collectionSize < threshold) {
//
//            }
//            if (collectionSize < numTasks) {
//                numTasks = collectionSize;
//            }
            ArrayList <Integer> list = new ArrayList<>(numTasks);
            list.add(0);

            int chunkSize = (collectionSize / numTasks);
            if (collectionSize % numTasks != 0) {
                for (int x = 1; x < numTasks; x++)
                    list.add((chunkSize * x) + 1);

            } else {
                for (int x = 1; x < numTasks; x++)
                    list.add(chunkSize * x);
            }
            list.add(collectionSize);
            return list;
        }

        /**
         * TODO: Extend the work you did to implement parArraySum to use a set
         * number of tasks to compute the reciprocal array sum. You may find the
         * above utilities getChunkStartInclusive and getChunkEndExclusive helpful
         * in computing the range of element indices that belong to each chunk.
         *
         * @param input    Input array
         * @param numTasks The number of tasks to create
         * @return The sum of the reciprocals of the array input
         */
        protected static double parManyTaskArraySum(final double[] input, final int numTasks) {
            LOGGER.warning("The numbers used size " + input.length);
            for (int x = 0; x < input.length; x++)
                LOGGER.info("The numbers used are " + input[x]);

            ReciprocalArraySumTask.numTasks = numTasks;

            ReciprocalArraySumTask task = new ReciprocalArraySumTask(0, input.length, input);
            task.compute();
            return task.value;
        }
    }
}