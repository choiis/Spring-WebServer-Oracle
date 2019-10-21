package com.singer.util;

public class InputQueryUtil {

	private StringBuilder query;

	@SuppressWarnings("unused")
	private InputQueryUtil() {
		// TODO Auto-generated constructor stub
	}

	public InputQueryUtil(String tableName) {
		query = new StringBuilder("insert into " + tableName + " values(");
	}

	public void add(String input) {
		query.append("\'" + input + "\',");
	}

	public void add(int input) {
		query.append("\'" + input + "\',");
	}

	public void add(long input) {
		query.append("\'" + input + "\',");
	}

	public void add(char input) {
		query.append("\'" + input + "\',");
	}

	public String getQuery() {
		return query.toString().substring(0, query.length() - 1) + ")";
	}

}
