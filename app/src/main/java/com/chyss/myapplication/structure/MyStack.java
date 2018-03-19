package com.chyss.myapplication.structure;

import java.util.LinkedList;

/**
 * 栈结构：后进先出
 * 
 * @author chyss
 *
 * @param <E>
 */
public class MyStack<E>
{
	LinkedList<E> list = new LinkedList<E>();
	
	/**
	 * 入栈
	 * Appends the specified element to the end of this list.
	 * @param entry
	 */
	public void push(E entry)
	{
		list.addLast(entry);
	}
	
	/**
	 * 出栈
	 * Removes and returns the last element from this list.
	 * @param entry
	 */
	public E pop()
	{
		return list.removeLast();
	}
}
