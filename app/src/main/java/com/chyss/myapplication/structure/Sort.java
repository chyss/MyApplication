package com.chyss.myapplication.structure;

public class Sort
{
	/**
	 * 冒泡排序(交换)
	 * 
	 * 对比相邻的两个数，如果后面的较小（大）则交换位置，重复执行此操作，直至所有数据有序。
	 * 
	 * array【2,3,4,9,5,2,7,8,1】
	 * 
	 * @param array
	 */
	public static void bubbleSort(int[] array)
	{
		for (int i = 0; i < array.length; i++)
		{
			for (int j = i+1; j < array.length; j++)
			{
				
				if (array[j] < array[i])  //从小到大排序
				{
					swap(array,i,j);
				}
			}
		}
	}
	
	/**
	 * 选择排序(选择)
	 * 
	 * 这一种最为简单的排序算法，每一次从数列中选取最大（最小）的数，然后把它放在最后（前面），重复执行此操作，直至所有数据有序。
	 * 
	 * array【2,3,4,9,5,2,7,8,1】
	 * 
	 * @param array
	 */
	public static void selectSort(int[] array)
	{
		int min;
		for (int i = 0; i < array.length; i++)
		{
			min = i;
			for (int j = i+1; j < array.length; j++)
			{
				if (array[j] < array[min])   //从小到大排序
				{
					min = j;
				}
			}
			swap(array,i,min);
		}
	}
	
	/**
	 * 插入排序(插入)
	 * 
	 * 从第一个元素开始，该元素可以认为已经被排序，
	 * 取出下一个元素，在已经排序的元素序列中从后向前扫描，如果该元素小于前面的已排序元素，
	 * 则依次与前面元素进行比较如果小于则交换，直到找到大于该元素的就则停止；
	 * 如果该元素大于前面的已排序元素，则重复步骤2，
	 * 重复步骤2~4 直到所有元素都排好序 。
	 * 
	 * array【2,3,4,9,5,2,7,8,1】
	 * 
	 * @param array
	 */
	public static void insertSort(int[] array)
	{
		for (int i = 1; i < array.length; i++)
		{
			for (int j = i; j > 0; j--)
			{
				if (array[j] < array[j-1])   //从小到大排序
				{
					swap(array,j,j-1);
				}
				else
				{
					break;
				}
			}
		}
	}
	
	/**
	 * 希尔排序(插入)
	 * 
	 * 希尔排序的实现是在插入排序的基础上改进的，插入排序的步长为1，每一次递减1，
	 * 希尔排序的步长为我们定义的h，然后每一次和前面的-h位置上的元素进行比较。
	 * 
	 * 比较在希尔排序中是最主要的操作，而不是交换。用这样步长的希尔排序比插入排序和堆排序都要快，
	 * 甚至在小数组中比快速排序还快，但是在涉及大量数据时希尔排序还是比快速排序慢。
	 * 
	 * array【2,3,4,9,5,2,7,8,1】
	 * 
	 * @param array
	 */
	public static void shellSort(int[] array)
	{
		//初始化步长
		int n = array.length;
		int h = n / 2;
		
		while (h >= 1)
		{
			for (int i = 1; i < n; i++)
			{
				for (int j = i; j >= h; j = j - h)
				{
					if (array[j] < array[j - h])   //从小到大排序
					{
						swap(array,j,j-h);
					}
					else
					{
						break;
					}
				}
			}
			h = h / 2;
		}
	}
	
	/**
	 * 交换数组中的两个数据
	 * 
	 * @param array
	 * @param i
	 * @param j
	 */
	private static void swap(int[] array,int i,int j)
	{
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
