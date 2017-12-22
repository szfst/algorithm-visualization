package quickSort;

import mergeSort.visualization.InsertionSort;

import java.awt.*;

public class AlgoVisualizer {

    private static int DELAY = 40;

    private QuickSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        // 初始化数据
        data = new QuickSortData(N, sceneHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Quick Sort Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        setData(-1, -1, -1, -1, -1);

        quickSort(0, data.N()-1);

        setData(-1, -1, -1, -1, -1);
    }

    private void quickSort(int left, int right) {
        if(right-left<=5){
            InsertionSort.sort(data,left,right);
            return;
        }
        int partition = partition(left, right);
        quickSort(left,partition-1);
        quickSort(partition+1,right);
    }
    private int partition(int left,int right){
        int i = left+1;
        int j = left;
        int pivot = data.get(left);
        for(;i<=right;i++){
            if(data.get(i)<pivot){
                data.swap(++j,i);
            }
        }
        data.swap (left,j);
       return j;
    }


    private void setData(int l, int r, int fixedPivot, int curPivot, int curElement){
        data.l = l;
        data.r = r;
        if(fixedPivot != -1)
            data.fixedPivots[fixedPivot] = true;
        data.curPivot = curPivot;
        data.curElement = curElement;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        int sceneWidth = 800;
        int sceneHeight = 800;
        int N = 100;

        AlgoVisualizer vis = new AlgoVisualizer(sceneWidth, sceneHeight, N);

    }
}