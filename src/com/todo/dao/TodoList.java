package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.Dbload;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;


public class TodoList {
//	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.conn = Dbload.getConnection();
	}

//	public TodoList() {
//		this.list = new ArrayList<TodoItem>();
//	}

//	public void addItem(TodoItem t) {
//		list.add(t);
//	}
	public int addItem(TodoItem t) {
		String sql = "insert into list (title, desc, category, current_date, due_date)" + " values (?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

//	public void deleteItem(TodoItem t) {
//		list.remove(t);
//	}
	
	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

//	void editItem(TodoItem t, TodoItem updated) {
//		int index = list.indexOf(t);
//		list.remove(index);
//		list.add(updated);
//	}
	
	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, desc=?, category=?, current_date=?, due_date=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return count;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}


//	public ArrayList<TodoItem> getList() {
//		return new ArrayList<TodoItem>(list);
//	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		key = "%" + key + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or desc like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setString(2, key);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListCategory(String key) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if (ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String description = rs.getString("desc");
				String category = rs.getString("category");
				String current_date = rs.getString("current_date");
				String due_date = rs.getString("due_date");
				TodoItem t = new TodoItem(title, description, category, due_date);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

//	public void sortByName() {
//		Collections.sort(list, new TodoSortByName());
//
//	}

//	public void listAll() {
//		System.out.println("\n"
//				+ "inside list_All method\n");
//		for (TodoItem myitem : list) {
//			System.out.println(myitem.getTitle() + myitem.getDesc());
//		}
//	}
	
//	public void reverseList() {
//		Collections.reverse(list);
//	}
//
//	public void sortByDate() {
//		Collections.sort(list, new TodoSortByDate());
//	}
//
//	public int indexOf(TodoItem t) {
//		return list.indexOf(t);
//	}
//
	public Boolean isDuplicate(String title) {
		for (TodoItem item : getList()) {
			if (title.equals(item.getCategory())) return true;
		}
		return false;
	}
	
//	
//	public void find(String[] schoice) {
//		String t = "";
//		int a = 1;
//		int count = 0;
//
//		for(int i = 1;i<schoice.length;i++) {
//			if(i==schoice.length-1) t += schoice[i];
//			else t += schoice[i] + " ";
//			
//		}
//		for(TodoItem item : list) {
//			if(item.getTitle().contains(t)||item.getDesc().contains(t))
//				System.out.println(a+". "+item.toString());
//			a++;
//			
//		}
//		for(TodoItem item : list) {
//			if(item.getTitle().contains(t)||item.getDesc().contains(t))
//				count++;
//		}
//		System.out.println("총" + count +"개의 항목을 찾았습니다.");
//	}
//	
//	public void find_cate(String[] schoice) {
//		String t = "";
//		int a = 1;
//		for(int i = 1;i<schoice.length;i++) {
//			if(i==schoice.length-1) t += schoice[i];
//			else t += schoice[i] + " ";
//		}
//		for(TodoItem item : list) {
//			if(item.getCategory().contains(t))
//				System.out.println(a+". "+item.toString());
//			a++;
//		}
//	}
	
//	public void ls_cate() {
//		HashSet<String> set = new HashSet<String>();
//		for(TodoItem item : list) set.add(item.getCategory());
//		int size = set.size();
//		int a = 1;
//		Iterator<String> iter = set.iterator();
//		while(iter.hasNext()) {
//			if(a<size) System.out.print(iter.next()+" / ");
//			else System.out.print(iter.next());
//			a++;
//		}
//		System.out.println();
//		System.out.println("총 "+size+"개의 카테고리가 등록되어 있습니다.");
//	}
	
//	public int getlsize() {
//		return this.list.size();
//	}
//	public void importData(String filename) {
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(filename));
//			String line;
//			String sql = "insert into list (title, desc, category, current_date, due_date)" + " values (?,?,?,?,?);";
//			int records = 0;
//			while ((line = br.readLine()) != null) {
//				StringTokenizer st = new StringTokenizer(line, "##");
//				String category = st.nextToken();
//				String title = st.nextToken();
//				String description = st.nextToken();
//				String due_date = st.nextToken();
//				String current_date = st.nextToken();
//
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1, category);
//				pstmt.setString(2, title);
//				pstmt.setString(3, description);
//				pstmt.setString(4, due_date);
//				pstmt.setString(5, current_date);
//				
//				int count = pstmt.executeUpdate();
//				if (count > 0)
//					records++;
//				pstmt.close();
//			}
//			System.out.println(records + "records read!!");
//			br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
}
