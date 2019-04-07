package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.StringToString;

public class MorseEncoder extends StringToString {
	
	private boolean parted;

	public MorseEncoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		for (int i = 0; i < len; i++)
			write(cbuf[i + off]);
	}

	@Override
	public void write(int c) throws IOException {
		String str = MorseCodeTable.table.get((char) Character.toUpperCase(c));
		
		if (parted)
			writer.write('/');
		else
			parted = true;
		
		if (str == null)
			throw new IllegalArgumentException(
					"字符 '" + (char) c + "' 没有对应的莫尔斯电码");
		else
			writer.write(str);
	}

	@Override
	public void close() throws IOException {
		writer.close();
	}
	
	@Override
	public void flush() throws IOException {
		writer.flush();
	}

	public static class Executer extends StringToString.Executer {
		
		public static final Executer INSTANCE = new Executer();

		@Override
		public StringToString getInstance(Writer writer) {
			return new MorseEncoder(writer);
		}
	}
}
