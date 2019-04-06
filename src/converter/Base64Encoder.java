package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.BytesToString;

public class Base64Encoder extends BytesToString {

	private int count;
	
	private byte[] cache = new byte[3];
	
	private final char[] chars = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        };
	
	public Base64Encoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(int b) throws IOException {
		cache[count++] = (byte) b;
		
		if (count == 3)
			parse();
	}
	
	@Override
	public void close() throws IOException {
		switch (count) {
		case 2:
			writer.append(chars[(cache[0] & 255) >> 2]);
			writer.append(chars[(((cache[0] & 255) << 4) | ((cache[1] & 255) >> 4)) & 63]);
			writer.append(chars[((cache[1] & 255) << 2) & 63]);
			writer.append('=');
		case 1:
			writer.append(chars[(cache[0] & 255) >> 2]);
			writer.append(chars[((cache[0] & 255) << 4) & 63]);
			writer.append('=');
			writer.append('=');
		}
		
		writer.close();
	}
	
	private void parse() throws IOException {
		writer.append(chars[(cache[0] & 255) >> 2]);
		writer.append(chars[(((cache[0] & 255) << 4) | ((cache[1] & 255) >> 4)) & 63]);
		writer.append(chars[(((cache[1] & 255) << 2) | ((cache[2] & 255) >> 6)) & 63]);
		writer.append(chars[(cache[2] & 255) & 63]);
		count = 0;
	}
	
	public static class Executer extends BytesToString.Executer {
		public static final Executer INSTANCE = new Executer();

		@Override
		public BytesToString getInstance(Writer writer) {
			return new Base64Encoder(writer);
		}
		
	}
}
