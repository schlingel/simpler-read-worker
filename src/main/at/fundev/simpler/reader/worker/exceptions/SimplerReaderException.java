package at.fundev.simpler.reader.worker.exceptions;

public class SimplerReaderException extends Exception {
	private static final long serialVersionUID = 1L;

	public SimplerReaderException(String message) {
		super(message);
	}
	
	public SimplerReaderException(Exception cause) {
		super(cause);
	}	
}
