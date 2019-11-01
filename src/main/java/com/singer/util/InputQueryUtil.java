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
		int start = query.length() - 1;
		return query.delete(start, start + 1).append(")").toString();
	}

}
