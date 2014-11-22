package br.com.visualize.akan.domain.exception;

public class ConcreteCreatorException extends CreatorException {

	@Override
	public Exception createException(int typeException) {
		if (typeException == CreatorException.CONNECTION) {
			return new ConnectionFailedException();
		}
		else if (typeException == CreatorException.REQUEST) {
			return new RequestFailedException();
		}
		else if (typeException == CreatorException.CONGRESSMAN) {
			return new NullCongressmanException();
		} 
		else {
			return new IllegalArgumentException("Type of exception dont supported.");
		}
			
	}

}
