package marvin.work.exception;

public class ProductNotFoundException extends RuntimeException {
	
	public ProductNotFoundException(String message){
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
