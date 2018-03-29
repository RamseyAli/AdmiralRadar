package exceptions;

public class PacketPayloadIncompatable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PacketPayloadIncompatable(){
		super();
	}
	
	public PacketPayloadIncompatable(String m){
		super(m);
	}

	
}
