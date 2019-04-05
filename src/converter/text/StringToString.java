package converter.text;

import java.io.IOException;
import java.io.Writer;

public abstract class StringToString extends Writer {
	
	private Writer writer;
	
	public StringToString(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

}
