package converter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import converter.text.StringToBytes;

public class Base64Decoder extends StringToBytes {
	
	private List<Character> chars = Arrays.asList(new Character[] {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
        });
	
	private char[] cache = new char[4];
	
	private int[] cache2 = new int[4];
	
	private int count;

	public Base64Decoder(OutputStream out) {
		super(out);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++)
			write(cbuf[i + off]);
	}
	
	@Override
	public void write(int c) throws IOException {
		cache[count++] = (char) c;
		
		if (count == 4)
			parse();
	}

	@Override
	public void close() throws IOException {
		if (count != 0)
			throw new IllegalArgumentException("参数不是正确的base64编码");
		
		out.close();
	}

	private void parse() throws IOException {
		int pos = 4;
		
		for (int i = 0; i < 4; i++) {
			if(cache[i] == '='){
				if (pos == 4)
					pos = i;
				continue;
			}

			if (chars.indexOf(cache[i]) != -1)
				cache2[i] = chars.indexOf(cache[i]);
			else
				throw new IllegalArgumentException("参数不是正确的base64编码");
		}
		
		switch (pos) {
		case 4:
			out.write((cache2[0] << 2) | (cache2[1] >> 6));
			out.write((cache2[1] << 4) | (cache2[2] >> 2));
			out.write((cache2[2] << 6) | cache2[3]);
			break;
		case 2:
			out.write((cache2[0] << 2) | (cache2[1] >> 4));
			break;
		case 3:
			out.write((cache2[0] << 2) | (cache2[1] >> 6));
			out.write((cache2[1] << 4) | (cache2[2] >> 2));
			break;
		default:
			throw new IllegalArgumentException("参数不是正确的base64编码");
		}
		
		count = 0;
	}
	
	public static class Executer extends StringToBytes.Executer {
		
		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToBytes getInstance(OutputStream out) {
			return new Base64Decoder(out);
		}
	}
}
