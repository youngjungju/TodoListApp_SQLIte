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
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]\n"
				+ "제목 > ");
		
		
		title = sc.nextLine().trim();
		if (l.isDuplicate(title)) {
			System.out.printf("제목이 중복됩니다!");
			return;
		}
		
	
		System.out.println("카테고리 입력 > ");
		category = sc.next();
		
		sc.nextLine();
		System.out.println("content > ");
		desc = sc.nextLine().trim();
		
		System.out.println("Due Date (yyyy/mm/dd) > ");
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		//list.addItem(t);
		if (l.addItem(t) > 0)
			System.out.printf("It's been added.");
	}

//	public static void deleteItem(TodoList l) {
//		
//		int count = 0;
//		int delnum; 
//		String yn;
//		
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("[항목삭제]\n"
//				+ "삭제할 항목의 번호를 입력하시오 > ");
//		delnum = sc.nextInt();
//		
//		for (TodoItem item : l.getList()) {
//			count ++;
//			if (count == delnum) {
//				System.out.print(count + ".");
//				System.out.println(item.toString());
//
//				System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
//				yn = sc.next();
//				if (yn.equals("y")) {
//					l.deleteItem(item);
//					System.out.println("삭제되었습니다.");
//					break;
//				}
//				else break;
//			}
//		}
//	}
	
	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n" + "항목 삭제 \n" + "삭제할 항목의 번호 >>  ");
		int index = sc.nextInt();
		if (l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다.");
	}


//	public static void updateItem(TodoList l) {
//		
//		int count = 0;
//		int updnum;
//		
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("[항목 수정]\n"
//				+ "수정할 항목의 번호를 입력하시오 > ");
//		
//		updnum = sc.nextInt();  
//		TodoItem t = l.getList().get(updnum-1);
//		System.out.println(updnum+". "+t.toString());
//		sc.nextLine();
//
//
//	
//
//		System.out.println("New title > ");
//		String new_title = sc.nextLine().trim();
//		if (l.isDuplicate(new_title)) {
//			System.out.println("The title overlaps");
//			return;
//		}
//	
//		System.out.print("New category > ");
//		String new_category = sc.next();
//		
//		sc.nextLine();
//		
//		System.out.println("New content > ");
//		String new_description = sc.nextLine().trim();
//		
//		
//		System.out.print("New Due Date (yyyy/mm/dd) > ");
//		String new_due_date = sc.nextLine().trim();
//		
//		l.deleteItem(t);
//		TodoItem item = new TodoItem(new_category,new_title, new_description, new_due_date);
//		l.addItem(item);
//		System.out.println("수정되었습니다.");
//
//	}
	
	public static void updateItem(TodoList l) {
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		System.out.println("\n" + "항목 수정\n" + "수정할 항목의 번호 >> ");
		int index = sc.nextInt();

		System.out.print("새 제목 >");
		new_title = sc.next().trim();
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine().trim();
		System.out.print("새 마감일자 > ");
		new_due_date = sc.nextLine().trim();
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if (l.updateItem(t) > 0)
			System.out.println("수정되었습니다.");
	}


	public static void listAll(TodoList l) {
		int count = 0; 
		int list = 0; 
		
		
		for (TodoItem item : l.getList()) {
			count++;
		}
		if(count != 0) System.out.println("[전체 목록" + " 총" + count + "개]");
		for (TodoItem item : l.getList()) {
			list++;
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d 개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void find(TodoList l, String key) {
		System.out.println("<키워드 \"" + key + "\"의 검색 결과>");
		int count = 0;
		for (TodoItem item : l.getList(key)) {
				System.out.println(item.toString());
				count ++;
		}
		System.out.printf("System: 총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void find_cate(TodoList l, String key) {
		System.out.println("<카테고리 \"" + key + "\"의 검색 결과>");
		int count = 0;
		for (TodoItem item : l.getListCategory(key)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.printf("System: 총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCate(TodoList l) {
		System.out.println("");
		System.out.println("<카테고리 목록>");
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item+" ");
			count ++;
		}
		System.out.printf("\nSystem: 총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	
	
//	public static void loadList(TodoList l, String filename) {
//		int count=0;
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(filename));
//			String newLine;
//			StringTokenizer st;
//			while((newLine = br.readLine()) != null) {
//				count++;
//				st = new StringTokenizer(newLine, "##");
//				String title = st.nextToken();
//				String category = st.nextToken();
//				String desc = st.nextToken();
//				String due_date = st.nextToken();
//				String date = st.nextToken();
//				l.addItem(new TodoItem(category,title, desc,due_date, date));
//			}
//			br.close();
//			if(count != 0) System.out.println(count+"개의 항목을 읽었습니다.");
//			
//		
//		} catch (FileNotFoundException e) {
//			System.out.println(filename + "파일이 없습니다.");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
//		
//	public static void saveList(TodoList l, String filename) {
//		try {
//			FileWriter f = new FileWriter(filename);
//			for(TodoItem x: l.getList()) 
//				f.write(x.toSaveString());
//			System.out.println("데이터가 저장되었습니다.");
//			f.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
}
