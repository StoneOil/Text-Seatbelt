package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.BytesToString;

public class Base32Encoder extends BytesToString {

	private char[] chars = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7',
	};
	
	private byte[] cache = new byte[5];
	
	private int count;
	
	public Base32Encoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(int b) throws IOException {
		cache[count++] = (byte) b;
		
		if (count == 5)
			parse();
	}
	
	@Override
	public void flush() throws IOException {
		if (count != 0) {
			for (int i = count; i < 5; i++)
				cache[i] = 0;
			
			writer.append(chars[(cache[0] & 255) >>> 3]);
			if (count * 8 > 5)writer.append(chars[((cache[0] & 7) << 2) | (cache[1] >>> 6)]);
			else writer.append('=');
			if (count * 8 > 10)writer.append(chars[(cache[1] >>> 1) & 31]);
			else writer.append('=');
			if (count * 8 > 15)writer.append(chars[((cache[1] & 1) << 4) | (cache[2] >>> 4)]);
			else writer.append('=');
			if (count * 8 > 20)writer.append(chars[((cache[2] & 15) << 1) | (cache[3] >>> 7)]);
			else writer.append('=');
			if (count * 8 > 25)writer.append(chars[(cache[3] >>> 2) & 31]);
			else writer.append('=');
			if (count * 8 > 30)writer.append(chars[((cache[3] & 3) << 3) | (cache[4] >>> 5)]);
			else writer.append('=');
			writer.append('=');
		}
		
		count = 0;
		
		writer.flush();
	}
	
	@Override
	public void close() throws IOException {
		flush();
		writer.close();
	}
	
	private void parse() throws IOException {
		writer.append(chars[(cache[0] & 255) >>> 3]);
		writer.append(chars[((cache[0] & 7) << 2) | ((cache[1] & 255) >>> 6)]);
		writer.append(chars[((cache[1] & 255) >>> 1) & 31]);
		writer.append(chars[((cache[1] & 1) << 4) | ((cache[2] & 255) >>> 4)]);
		writer.append(chars[((cache[2] & 15) << 1) | ((cache[3] & 255) >>> 7)]);
		writer.append(chars[((cache[3] & 255) >>> 2) & 31]);
		writer.append(chars[((cache[3] & 3) << 3) | ((cache[4] & 255) >>> 5)]);
		writer.append(chars[cache[4] & 31]);
		count = 0;
	}
	
	public static class Executer extends BytesToString.Executer {
		public static final Executer INSTANCE = new Executer();

		@Override
		public BytesToString getInstance(Writer writer) {
			return new Base32Encoder(writer);
		}
		
	}
}
