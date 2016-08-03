package com.reno.sorts;

import junit.framework.Assert;

import org.junit.Test;

public class QuickSort {
	
	public static void qkSort(int[] data){
		Sort(data,0,data.length-1);
	}
	private static void Sort(int[] data,int left,int right){
		if(left<0||right>data.length) return;
		if(left<right){
			int i=left;
			int j=right;
			int base=data[i];
			while(i<j){
				while(i<j&&data[j]>base) j--;
				if(i<j) data[i++]=data[j];
				while(i<j&&data[i]<base) i++;
				if(i<j) data[j--]=data[i];
			}
			data[i]=base;
			Sort(data,left,i-1);
			Sort(data,i+1,right);
		}
	}

	@Test
	public void tesSort(){
		int [] d=  {1,2,4,6,8,2,1,3,4,5};
		int [] std={1,1,2,2,3,4,4,5,6,8};
		qkSort(d);
		boolean flag=true;
		for(int i=0;i<d.length;i++){
			if(d[i]!=std[i]){
				flag=false;
				break;
			}
		}
		
		
		Assert.assertEquals("¿ìËÙÅÅÐò",flag,true );          
	}
}
