package converter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import converter.text.StringToBytes;

public class HexDecoder extends StringToBytes {

	private int theLast = -1;

	final public static List<Character> Digits = Arrays.asList(new Character[] {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	});

	public HexDecoder(OutputStream out) {
		super(out);
	}

	@Override
	public void close() throws IOException {
		flush();
		out.close();
	}
	
	@Override
	public void flush() throws IOException {
		if (theLast != -1)
			throw new IllegalArgumentException("参数不是正确的十六进制字符串");
		out.flush();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) {
			write(cbuf[i + off]);
		}
	}

	@Override
	public void write(int c) throws IOException {
		c = Character.toUpperCase(c);
		
		if(theLast == -1)
			theLast = c;
		else {
			out.write(
					(Digits.indexOf((char)theLast) << 4) +
					Digits.indexOf((char)c));
			theLast = -1;
		}
	}

	public static class Executer extends StringToBytes.Executer {
		
		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToBytes getInstance(OutputStream out) {
			return new HexDecoder(out);
		}
	}
}
