package com.singer.util;

public class InputQueryUtil {

	private String query;

	private InputQueryUtil() {
		// TODO Auto-generated constructor stub
	}

	public InputQueryUtil(String tableName) {
		query = "insert into " + tableName + " values(";
	}

	public void add(String input) {
		query += "\'" + input + "\',";
	}

	public void add(int input) {
		query += "\'" + input + "\',";
	}

	public void add(long input) {
		query += "\'" + input + "\',";
	}

	public void add(char input) {
		query += "\'" + input + "\',";
	}

	public String getQuery() {
		return query.substring(0, query.length() - 1) + ")";
	}

}
