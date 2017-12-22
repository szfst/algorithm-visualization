package mergeSort.visualization;

import java.awt.*;
import java.util.Arrays;

public class AlgoVisualizer {

    private static int DELAY = 40;

    private MergeSortData data;
    private AlgoFrame frame;

    public AlgoVisualizer(int sceneWidth, int sceneHeight, int N){

        // 初始化数据
        data = new MergeSortData(N, sceneHeight);

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Merge Sort Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){

        setData(-1, -1, -1);

        //自顶向下归并排序
//        mergeSort(0, data.N()-1);
        //自底向上的归并排序
        for(int sz = 1;sz<data.N();sz*=2){
            //如果i>data.N()-sz,i+sz>data.N(),i+sz-1>data.N()-1,mid值就会越界
            for(int i = 0;i<data.N()-sz;i+=2*sz){
                merge(i,i+sz-1,Math.min(i+2*sz-1,data.N()-1));
            }
        }

        setData(0, data.N()-1, data.N()-1);
    }

    private void mergeSort(int left, int right) {
        //优化：对小于5的数据用插入排序
        if(right-left<=5){
            InsertionSort.sort(data,left,right);
            return;
        }
        int mid = (left+right)/2;
        mergeSort(left,mid);
        mergeSort(mid+1,right);
        //优化：当右边的最小值大于左边的最大值的时候，不需要merge（也就是右边的最小值大于左边的最大值的时候才merge），对于近乎有序的数组性能有很大的提高
        if(data.get(mid+1)<data.get(mid)){
            merge(left,mid,right);
        }
    }

    private void merge(int left, int mid, int right) {
        //左闭右开
        int[] aux = Arrays.copyOfRange(data.numbers,left,right+1);
        int offset = left;
        int k = left;
        left = left-offset;
        mid  = mid-offset;
        right = right-offset;
        int left1 = mid+1;

        while(left<=mid || left1<=right){
            //左半部分结束
            if(left>mid){
                data.numbers[k++]=aux[left1++];
            }//右半部分结束
            else if(left1>right) {
                data.numbers[k++]=aux[left++];
            }//左半部分的左值小于右半部分的左值
            else if(aux[left]<=aux[left1]){
                data.numbers[k++]=aux[left++];
            }//左半部分的左值大于右半部分的左值
            else{
                data.numbers[k++]=aux[left1++];
            }
            setData(left,right,k);
        }
    }


    private void setData(int l, int r, int mergeIndex){
        data.l = l;
        data.r = r;
        data.mergeIndex = mergeIndex;
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