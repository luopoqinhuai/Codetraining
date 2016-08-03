package com.reno.sorts;

import org.junit.Assert;
import org.junit.Test;

public class HeapSort {
	
	public static void hpSort(int[] data){
		buildMaxHeap(data);
		sortHeap(data);
	}
	
	public static int[] getTopK(int[] data, int K){
		buildMaxHeap(data);
		int start=data.length-1;
		int[] out=new int[K];
		int index=0;
		for(int i=start;i>start-K;i--){
			int tmp=data[0];
			data[0]=data[i];
			data[i]=tmp;
			out[index++]=tmp;
			adjustHeap(data,i,0);
			
		}
		
		return out;
	}
	
	private static void adjustHeap(int[] data,int sizeOfHeap,int index){
		int left=2*index+1;
		int right=2*index+2;
		int largest=index;	
		if(left<sizeOfHeap&&data[left]>data[index]) largest = left;
		if(right<sizeOfHeap&&data[right]>data[largest]) largest=right;
		if(largest!=index){
			int tmp=data[index];
			data[index]=data[largest];
			data[largest]=tmp;
			adjustHeap(data,sizeOfHeap,largest);
		}
	}
	//初始化堆
	private static void buildMaxHeap(int[] data){
		int size=data.length;
		for(int i=size/2;i>=0;i--){
			adjustHeap(data,size,i);
		}
	}
	//完全堆排序
	private static void sortHeap(int[] data){
		for(int i=data.length-1;i>=0;i--){
			int tmp=data[0];
			data[0]=data[i];
			data[i]=tmp;
			adjustHeap(data,i,0);
		}
	}
	
	
	@Test
	public void tesSort(){
		int [] d=  {8,2,1,3,4,5,1,2,4,6};
		int [] std={1,1,2,2,3,4,4,5,6,8};
		hpSort(d);
		boolean flag=true;
		for(int i=0;i<d.length;i++){
			if(d[i]!=std[i]){
				flag=false;
				break;
			}
		}
		Assert.assertEquals("快速排序",flag,true );          
	}
	
	
	@Test
	public void tesTopK(){
		int [] d=  {2,1,4,6,8,2,1,3,4,5};
		int [] std={8,6,5};
		int[] actual=getTopK(d, 3);
		boolean flag=true;
		for(int i=0;i<std.length;i++){
			if(actual[i]!=std[i]){
				flag=false;
				break;
			}
		}
		Assert.assertEquals("快速排序",flag,true );          
	}

}
