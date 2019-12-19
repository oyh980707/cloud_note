package com.loveoyh.note.test;
/**
 * javaÊÇÖµ´«µİ
 * @author HP
 *
 */
public class Demo {
	
	public static void main(String[] args) {
		Integer i = 0;
		int[] arr = {0};
		test(i, arr);
		System.out.println(i);
		System.out.println(arr[0]);
	}
	
	public static void test(Integer i, int[] arr){
		i++;
		arr[0]++;
	}
	
}
