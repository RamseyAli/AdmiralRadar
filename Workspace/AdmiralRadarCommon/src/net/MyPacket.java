package net;

import java.io.Serializable;
import java.util.UnknownFormatConversionException;

public class MyPacket implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyPacketable	payload;
	private ObjEnum			objectClass;

	private MyPacket(MyPacketable p, String n) {
		payload = p;

		switch (n) {
			case "GameMap":
				objectClass = ObjEnum.MAP;
				break;
			case "Position":
				objectClass = ObjEnum.POSITION;
				break;
			case "Role":
				objectClass = ObjEnum.ROLE;
				break;
			case "Spaceship":
				objectClass = ObjEnum.SPACESHIP;
				break;
			case "SendableString":
				objectClass = ObjEnum.STRING;
				break;
			case "User":
				objectClass = ObjEnum.USER;
				break;
			case "Direction":
				objectClass = ObjEnum.DIRECTION;
				break;
			case "SendablePath":
				objectClass = ObjEnum.PATH;
				break;
			case "Systems":
				objectClass = ObjEnum.SYSTEMS;
				break;
			default:
				throw new UnknownFormatConversionException( n );
		}
	}

	MyPacket(MyPacketable p) {
		this( p , p.getClass().getSimpleName() );
	}

	public MyPacketable getObject() {

		return payload;

	}

	public ObjEnum getObjectClass() {

		return objectClass;
	}

}
