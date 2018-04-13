package exceptions;

import net.ObjEnum;

public class PacketPayloadIncompatable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PacketPayloadIncompatable() {
		super();
	}

	public PacketPayloadIncompatable(String m) {
		super( m );
	}
	
	public PacketPayloadIncompatable(ObjEnum o) {
		super( o.name() );
	}

}
