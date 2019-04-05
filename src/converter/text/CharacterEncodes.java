package converter.text;

public class CharacterEncodes {

	public Encoder getEncoder(String name) {
		if (name.equalsIgnoreCase("UTF-8"))
			return new UTF_8.Encoder(false);
		
		if (name.equalsIgnoreCase("UTF8"))
			return new UTF_8.Encoder(false);
		
		if (name.equalsIgnoreCase("UTF-16"))
			return new UTF_16LE.Encoder(true);
		
		if (name.equalsIgnoreCase("UTF-16LE"))
			return new UTF_16LE.Encoder(true);
		
		if (name.equalsIgnoreCase("UNICODE"))
			return new UTF_16LE.Encoder(true);
		
		return null;
	}
	
	public Decoder getDecoder(String name) {
		if (name.equalsIgnoreCase("UTF-8"))
			return new UTF_8.Decoder(false);
		
		if (name.equalsIgnoreCase("UTF8"))
			return new UTF_8.Decoder(false);
		
		if (name.equalsIgnoreCase("UTF-16"))
			return new UTF_16LE.Decoder(true);
		
		if (name.equalsIgnoreCase("UTF-16LE"))
			return new UTF_16LE.Decoder(true);
		
		if (name.equalsIgnoreCase("UNICODE"))
			return new UTF_16LE.Decoder(true);
		
		return null;
	}
}
