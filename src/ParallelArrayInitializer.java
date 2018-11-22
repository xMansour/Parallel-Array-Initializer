import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ParallelArrayInitializer {
    private static double[] mList;
    private static int mStart = 0;
    private static int mListSize = 9000000;
    private static int mThreshold = 10000;
    private static long mTime;
    private static Random mRandom;

    public static void main(String[] args) {
        mList = new double[mListSize];

        //gets the system's current time in milliseconds
        mTime = System.currentTimeMillis();
        //calling the sequential method with the mList as an argument
        sequentialArrayFilling(mList);
        //calculating the sequential elapsed time by subtracting the current time - starting time
        System.out.println("Sequential Processing Time: " + (System.currentTimeMillis() - mTime));
        mTime = System.currentTimeMillis();
        //calling the parallel method with the mList as an argument
        parallelArrayFilling(mList);
        //calculating the parallel elapsed time
        System.out.println("Parallel Processing Time: " + (System.currentTimeMillis() - mTime));

    }

    //this method is used to assign random values to the array called mList
    public static void sequentialArrayFilling(double[] mList) {
        //creating a Random object that generates a random double number when invoked with .nextDouble() and assign
        //the random value to each element of the mList
        mRandom = new Random();
        for (int i = 0; i < mList.length; i++) {
            mList[i] = mRandom.nextDouble();
        }
    }

    //this method is used to assign random values to the array using the fork/join framework by making
    //a new task and send the mList, 0 as the start, the list's size as an end and a threshold defined above
    public static void parallelArrayFilling(double[] mList) {
        //creating a task object that extends the RecursiveAction class
        Task task = new Task(mList, mStart, mListSize, mThreshold);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        //assigning the task to the ForkJoinPool object
        forkJoinPool.invoke(task);
    }

}

