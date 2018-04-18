package exceptions;

/**
 * Exception thrown when map dimensions (or ship positions) are either too small or when the sector dimension is not a factor of the map dimension
 */
public class MapDimensionException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public MapDimensionException() {}
	public MapDimensionException(String s) {
		super(s);
	}
}
