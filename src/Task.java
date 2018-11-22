import java.util.Random;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {
    private double[] mList;     //the list to be initialized
    private int mStart;
    private int mEnd;
    private int mThreshold;
    private int mMiddle;
    private Random mRandom;


    public Task(double[] mList, int mStart, int mEnd, int mThreshold) {
        this.mList = mList;
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mThreshold = mThreshold;
    }

    @Override
    protected void compute() {
        if (mEnd - mStart < mThreshold) {           //if the list is less size is less than the threshold add values sequentially
            mRandom = new Random();
            for (int i = mStart; i < mEnd; i++) {
                mList[i] = mRandom.nextDouble();
            }
        } else {                                    //if not, split the list into two equal parts and execute them concurrently
            mMiddle = (mStart + mEnd) / 2;          //get the half of the list size
            Task task1 = new Task(mList, mStart, mMiddle, mThreshold);  //assign the first half to the first task with a start
            //of mStart and an end of mMiddle

            Task task2 = new Task(mList, mMiddle, mEnd, mThreshold);    //assign the second half to the second task with a start
            //of mMiddle and an end of mEnd
            invokeAll(task1, task2);    //invoke both tasks
        }
    }
}
