package converter;

import java.io.IOException;
import java.io.Writer;

import converter.text.BytesToString;

public class BinEncoder extends BytesToString {

	public BinEncoder(Writer writer) {
		super(writer);
	}

	@Override
	public void write(int b) throws IOException {
		writer.write((b & 0x80) == 0 ? '0' : '1');
		writer.write((b & 0x40) == 0 ? '0' : '1');
		writer.write((b & 0x20) == 0 ? '0' : '1');
		writer.write((b & 0x10) == 0 ? '0' : '1');
		writer.write((b & 0x08) == 0 ? '0' : '1');
		writer.write((b & 0x04) == 0 ? '0' : '1');
		writer.write((b & 0x02) == 0 ? '0' : '1');
		writer.write((b & 0x01) == 0 ? '0' : '1');
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
			return new BinEncoder(writer);
		}
		
	}
}
