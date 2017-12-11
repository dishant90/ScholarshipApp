package com.tripleS.exception;

public class NoFileFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String errCode = "S002";
	private String errMsg;

	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public NoFileFoundException() {
		this.errMsg = "No file found";
	}
}
