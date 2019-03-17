package com.singer.vo;

public class ErrorVo extends SuperVo {

	private String erroruri;
	private String errortime;
	private String errormsg;

	public ErrorVo() {
		super();
	}

	public ErrorVo(String erroruri, String errortime, String errormsg) {
		super();
		this.erroruri = erroruri;
		this.errortime = errortime;
		this.errormsg = errormsg;
	}

	public String getErroruri() {
		return erroruri;
	}

	public void setErroruri(String erroruri) {
		this.erroruri = erroruri;
	}

	public String getErrortime() {
		return errortime;
	}

	public void setErrortime(String errortime) {
		this.errortime = errortime;
	}

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	@Override
	public String toString() {
		return "ErrorVo [erroruri=" + erroruri + ", errortime=" + errortime + ", errormsg=" + errormsg + "]";
	}

}
