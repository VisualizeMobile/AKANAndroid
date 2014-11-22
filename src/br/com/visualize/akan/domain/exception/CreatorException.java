package br.com.visualize.akan.domain.exception;

public abstract class CreatorException {
	
	public static final int CONNECTION = 0;
	public static final int REQUEST = 1;
	public static final int CONGRESSMAN = 2;
	
	public abstract Exception createException(int typeException);

}
