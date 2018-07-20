package com.yy.common.exception.base.impl;

import com.yy.common.exception.base.CustomException;

/**
 * 参数为空异常
 * @author WangMin
 *
 */
public class NullParameterException extends CustomException{

	private static final long serialVersionUID = 1L;
	
	public NullParameterException() {
	}
	
	public NullParameterException(String message){
		super(message);
	}

}
