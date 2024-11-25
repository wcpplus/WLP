package com.farm.sfile.exception;

/**
 * 文件不存在
 * 
 * @author macpl
 *
 */
public class FileNotExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2169402402793297968L;

	public FileNotExistException(String message) {
		super(message);
	}

}
