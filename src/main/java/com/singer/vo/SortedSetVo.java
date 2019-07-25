package com.singer.vo;

public class SortedSetVo extends SuperVo {

	private String key;
	private double score;

	public SortedSetVo() {
		super();
	}

	public SortedSetVo(String key, double score) {
		super();
		this.key = key;
		this.score = score;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "SortedSetVo [key=" + key + ", score=" + score + "]";
	}

}
