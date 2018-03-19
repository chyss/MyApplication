package com.chyss.myapplication.structure;

import java.util.Arrays;

/**
 * 数组是一种大小固定的线性数据结构,数组实现的线性表优点：在于可以通过下标来访问或修改元素，比较高效；
 * 缺点：在于插入、删除的花费开销较大。
 * 
 * @author Administrator
 *
 */
public class Array
{
	/**
	 * 数组中插入数据，需要对数组进行拓展，然后把index后面的元素进行后移，再对数据进行插入。
	 * 
	 * @param array
	 * @param index
	 * @param value
	 */
	public static int[] insertArray(int[] array,int index,int value)
	{
		int n = array.length;
		if (index > n || index < 0)
		{
			return null;
		}
		
		// 对数组进行拓展
		array = Arrays.copyOf(array, n+1);
		
		for (int i = n; i > index; i--)
		{
			array[i] = array[i - 1];
		}
		
		array[index] = value;
		
		return array;
	}
	
	/**
	 * 数组中删除数据
	 * 
	 * @param array
	 * @param index
	 */
	public static int[] delectArray(int[] array,int index)
	{
		int n = array.length;
		if (index > n - 1 || index < 0)
		{
			return null;
		}
		
		for (int i = index; i < n - 1; i++)
		{
			array[i] = array[i + 1];
		}

		array = Arrays.copyOf(array, n-1);
		
		return array;
	}
}
