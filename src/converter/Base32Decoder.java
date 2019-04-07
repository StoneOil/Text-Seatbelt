package converter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import converter.text.StringToBytes;

public class Base32Decoder extends StringToBytes {
	
	private char[] cache = new char[8];
	
	private int[] cache2 = new int[8];

	private int count;

	public Base32Decoder(OutputStream out) {
		super(out);
	}

	final public static List<Character> chars = Arrays.asList(new Character[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '2', '3', '4', '5', '6', '7',
	});
	
	final public static List<Character> chars2 = Arrays.asList(new Character[] {
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
			'y', 'z', '2', '3', '4', '5', '6', '7',
	});

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++)
			write(cbuf[i + off]);
	}
	
	@Override
	public void write(int c) throws IOException {
		cache[count++] = (char) c;
		
		if (count == 8)
			parse();
	}

	@Override
	public void close() throws IOException {
		flush();
		out.close();
	}
	
	@Override
	public void flush() throws IOException {
		if (count != 0)
			throw new IllegalArgumentException("参数不是正确的base32编码");
		out.flush();
	}
	
	public void parse() throws IOException {
		int pos = 8;
		
		for (int i = 0; i < 8; i++) {
			if(cache [i] == '='){
				if (pos == 8)
					pos = i;
				continue;
			}
			
			if (chars.indexOf(cache[i]) != -1)
				cache2[i] = chars.indexOf(cache[i]);
			else if (chars2.indexOf(cache[i]) != -1)
				cache2[i] = chars.indexOf(cache[i]);
			else
				throw new IllegalArgumentException("参数不是正确的base32编码");
		}
		
		switch (pos) {
		case 8:
			out.write((cache2[0] << 3) | (cache2[1] >>> 2));
			out.write((cache2[1] << 6) | (cache2[2] << 1) | (cache2[3] >>> 4));
			out.write((cache2[3] << 4) | (cache2[4] >>> 1));
			out.write((cache2[4] << 7) | (cache2[5] << 2) | (cache2[6] >>> 3));
			out.write((cache2[6] << 5) | cache2[7]);
			break;
		case 2:
			out.write((cache2[0] << 3) | (cache2[1] >>> 2));
			break;
		case 4:
			out.write((cache2[0] << 3) | (cache2[1] >>> 2));
			out.write((cache2[1] << 6) | (cache2[2] << 1) | (cache2[3] >>> 4));
			break;
		case 5:
			out.write((cache2[0] << 3) | (cache2[1] >>> 2));
			out.write((cache2[1] << 6) | (cache2[2] << 1) | (cache2[3] >>> 4));
			out.write((cache2[3] << 4) | (cache2[4] >>> 1));
			break;
		case 7:
			out.write((cache2[0] << 3) | (cache2[1] >>> 2));
			out.write((cache2[1] << 6) | (cache2[2] << 1) | (cache2[3] >>> 4));
			out.write((cache2[3] << 4) | (cache2[4] >>> 1));
			out.write((cache2[4] << 7) | (cache2[5] << 2) | (cache2[6] >>> 3));
			break;
		default:
			throw new IllegalArgumentException("参数不是正确的base32编码");
		}
		
		count = 0;
	}
	
	public static class Executer extends StringToBytes.Executer {
		
		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToBytes getInstance(OutputStream out) {
			return new Base32Decoder(out);
		}
	}
}
