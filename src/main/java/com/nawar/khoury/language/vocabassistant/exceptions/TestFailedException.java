package com.nawar.khoury.language.vocabassistant.exceptions;

public class TestFailedException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public TestFailedException(String message)
	{
		super(message);
	}

	public TestFailedException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TestFailedException(Throwable cause)
	{
		super(cause);
	}
}
