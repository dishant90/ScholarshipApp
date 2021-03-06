package com.tripleS.exception;

public class InvalidFileNumberException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String errCode = "S001";
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

	public InvalidFileNumberException(String fileNo) {
		this.errMsg = "Invalid case number: " + fileNo;
	}
}
