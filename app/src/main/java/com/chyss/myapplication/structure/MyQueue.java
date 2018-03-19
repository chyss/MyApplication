package com.chyss.myapplication.structure;

import java.util.LinkedList;

/**
 * 队列结构：先进先出
 * 
 * @author chyss
 *
 * @param <E>
 */
public class MyQueue<E>
{
	LinkedList<E> list = new LinkedList<E>();
	
	/**
	 * 入队
	 * Appends the specified element to the end of this list.
	 * @param entry
	 */
	public void enQueue(E entry)
	{
		list.addLast(entry);
	}
	
	/**
	 * 出队
	 * Removes and returns the first element from this list.
	 * @param entry
	 */
	public E deQueue()
	{
		if (list.isEmpty())
		{
			return null;
		}
		return list.removeFirst();
	}
}
