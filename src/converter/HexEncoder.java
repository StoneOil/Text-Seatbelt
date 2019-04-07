package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.BytesToString;

public class HexEncoder extends BytesToString {
	
	final public static char[] Digits = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
	};

	public HexEncoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(int b) throws IOException {
		b = b & 255;
		writer.write(Digits[b >> 4]);
		writer.write(Digits[b & 15]);
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	public static class Executer extends BytesToString.Executer {
		public static final Executer INSTANCE = new Executer();

		@Override
		public BytesToString getInstance(Writer writer) {
			return new HexEncoder(writer);
		}
		
	}
}
