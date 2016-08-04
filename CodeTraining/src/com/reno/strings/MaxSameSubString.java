package com.reno.strings;

import java.util.LinkedList;

import org.junit.Test;

public class MaxSameSubString {

	//最长公共子串，使用动态规划
	//maxLen[i][j]  表示A中第i个结尾  ，B中第j个结尾时的子串长度
	//当A[i]==A[j] 时（A中第i+1个B中与第j+1相同） 此时得到地推公式：  maxLen[i+1][j+1]=maxLen[i][j]+1;
	public static void  getSameSubString(String A,String B){
		int m=A.length();
		int n=B.length();
		int[][]maxLen = new int [m+1][n+1];
		LinkedList<Integer> maxIndexs=new LinkedList<Integer>();
		int maxSize=-1;
		for(int i=0;i<m;i++){
			for(int j=0;j<n;j++){
				if(A.charAt(i)==B.charAt(j)){
					maxLen[i+1][j+1]=maxLen[i][j]+1;
				}else{
					maxLen[i+1][j+1]=0;
				}
				if(maxSize<maxLen[i+1][j+1]){
					maxSize=maxLen[i+1][j+1];
					maxIndexs.clear();
					maxIndexs.add(i+1);
				}if(maxSize==maxLen[i+1][j+1]){
					maxIndexs.add(i+1);
				}
			}
		}
		System.out.println("最长子串长度："+maxSize);
		for(int i:maxIndexs){
			System.out.println(A.substring(i-maxSize,i));
		}
	}
	
	
	@Test
	public void test(){
		String  A="12assdsd34fddf56ghg7878";
		String B="34qw12qw56qw78qw34";
		
		getSameSubString(A,B);
		
	}

}
