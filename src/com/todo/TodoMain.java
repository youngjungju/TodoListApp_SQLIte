package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		//boolean isList = false;
		boolean quit = false;
		//TodoUtil.loadList(l, "todolist.txt");
		
		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.nextLine();
			String[] schoice = choice.split(" ");
			switch (schoice[0]) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("I've sorted it by title order");
				TodoUtil.listAll(l,"title",1);
				//isList = true;
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬했습니다.");
				TodoUtil.listAll(l,"title",0);
				//isList = true;
				break;
				
			case "ls_date":
				System.out.println("날짜순으로 정렬했습니다.");
				TodoUtil.listAll(l,"due_date",1);
				//isList = true;
				break;
			
			case "help":
				Menu.displaymenu();
				break;
				
			case "find":
				TodoUtil.find(l,sc.next());
				break;
			
			case "find_cate":
				TodoUtil.find_cate(l,sc.next());
				break;
			
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
				
			case "ls_date_desc":
				System.out.println("날짜 역순 정렬 되었습니다.");
				TodoUtil.listAll(l,"due_date",0);
				break;

			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령어를 입력하세요. (도움말 - help)");
				break;
			}
			
			//if(isList) TodoUtil.listAll(l);
		} while (!quit);
		sc.close();
		//TodoUtil.saveList(l, "todolist.txt");
	}
}
