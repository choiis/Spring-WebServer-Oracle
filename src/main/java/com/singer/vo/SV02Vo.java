package com.singer.vo;

public class SV02Vo extends SuperVo {

	private int seq;
	private int idx;
	private String content;

	private String regdate;

	private String userid;

	private int voted;

	private int totVoted;

	private int votedYn;

	public SV02Vo() {
		super();
	}

	public SV02Vo(int seq, int idx, String content, String regdate, String userid, int voted, int totVoted,
			int votedYn) {
		super();
		this.seq = seq;
		this.idx = idx;
		this.content = content;
		this.regdate = regdate;
		this.userid = userid;
		this.voted = voted;
		this.totVoted = totVoted;
		this.votedYn = votedYn;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getVoted() {
		return voted;
	}

	public void setVoted(int voted) {
		this.voted = voted;
	}

	public int getTotVoted() {
		return totVoted;
	}

	public void setTotVoted(int totVoted) {
		this.totVoted = totVoted;
	}

	public int getVotedYn() {
		return votedYn;
	}

	public void setVotedYn(int votedYn) {
		this.votedYn = votedYn;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "SV02Vo [seq=" + seq + ", idx=" + idx + ", content=" + content + ", regdate=" + regdate + ", userid="
				+ userid + ", voted=" + voted + ", totVoted=" + totVoted + ", votedYn=" + votedYn + "]";
	}

}
