package com.todo.service;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[Add an item]\n"
				+ "title > ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("The title overlaps!");
			return;
		}
		
		sc.nextLine();
		System.out.println("content > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.printf("It's been added.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[Delete the item]\n"
				+ "Enter the title of the item you want to delete > ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.printf("It's been deleted.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[Item modification]\n"
				+ "Enter the title of the item you want to modify > ");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("It's not a title");
			return;
		}

		System.out.println("New title > ");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("The title overlaps");
			return;
		}
		
		System.out.println("New content > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("It's been modified");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		int count=0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String newLine;
			StringTokenizer st;
			while((newLine = br.readLine()) != null) {
				count++;
				st = new StringTokenizer(newLine, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String date = st.nextToken();
				l.addItem(new TodoItem(title, desc, date));
			}
			br.close();
			if(count != 0) System.out.println(count+"개의 항목을 읽었습니다.");
			
		
		} catch (FileNotFoundException e) {
			System.out.println(filename + "파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter f = new FileWriter(filename);
			for(TodoItem x: l.getList()) 
				f.write(x.toSaveString());
			System.out.println("데이터가 저장되었습니다.");
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
