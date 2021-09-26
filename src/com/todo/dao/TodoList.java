package com.todo.dao;

import java.util.*;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + myitem.getDesc());
		}
	}
	
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public void find(String[] schoice) {
		String t = "";
		int a = 1;
		int count = 0;

		for(int i = 1;i<schoice.length;i++) {
			if(i==schoice.length-1) t += schoice[i];
			else t += schoice[i] + " ";
			
		}
		for(TodoItem item : list) {
			if(item.getTitle().contains(t)||item.getDesc().contains(t))
				System.out.println(a+". "+item.toString());
			a++;
			
		}
		for(TodoItem item : list) {
			if(item.getTitle().contains(t)||item.getDesc().contains(t))
				count++;
		}
		System.out.println("총" + count +"개의 항목을 찾았습니다.");
	}
	
	public void find_cate(String[] schoice) {
		String t = "";
		int a = 1;
		for(int i = 1;i<schoice.length;i++) {
			if(i==schoice.length-1) t += schoice[i];
			else t += schoice[i] + " ";
		}
		for(TodoItem item : list) {
			if(item.getCategory().contains(t))
				System.out.println(a+". "+item.toString());
			a++;
		}
	}
	
	public void ls_cate() {
		HashSet<String> set = new HashSet<String>();
		for(TodoItem item : list) set.add(item.getCategory());
		int size = set.size();
		int a = 1;
		Iterator<String> iter = set.iterator();
		while(iter.hasNext()) {
			if(a<size) System.out.print(iter.next()+" / ");
			else System.out.print(iter.next());
			a++;
		}
		System.out.println();
		System.out.println("총 "+size+"개의 카테고리가 등록되어 있습니다.");
	}
	
	public int getlsize() {
		return this.list.size();
	}
	
}
