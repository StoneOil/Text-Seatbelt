package converter.text;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import converter.Base32Decoder;
import converter.Base64Decoder;
import converter.BinDecoder;
import converter.HexDecoder;
import converter.Main;
import converter.command.Arguments;
import converter.command.Command;
import converter.command.CommandBuilder;

public abstract class StringToBytes extends Writer {
	
	public static abstract class Executer {
		
		private static final Command cmd = new CommandBuilder()
				.flag("-i", 2)
				.flag("-o", 1)
				.flag("-e", 1)
				.text("content")
				.latestOptional()
				.build();
		
		abstract public StringToBytes getInstance(OutputStream out);
		
		public void execute(String[] args) throws IOException {
			Arguments a = cmd.interpret(args);
			
			OutputStream out;
			
			if (a.getFlag("-o") == null) {
				out = System.out;
			}
			else {
				out = new FileOutputStream(a.getFlag("-o").getValues()[0]);
			}
			
			if (a.getFlag("-e") != null) {
				out = new BufferedOutputStream(out) {
					
					public void flushBuffer() throws IOException {
						Reader reader = new InputStreamReader(
								new ByteArrayInputStream(buf, 0, count),
								a.getFlag("-e").getValues()[0]);
						Writer writer = new OutputStreamWriter(out);
						
						int i;
						while ((i = reader.read()) != -1) {
							writer.write(i);
						}
						
						reader.close();
						writer.close();
					}
					
					public synchronized void write(int b) throws IOException {
				        if (count >= buf.length) {
				            flushBuffer();
				        }
				        buf[count++] = (byte)b;
				    }
					
					public synchronized void flush() throws IOException {
				        flushBuffer();
				        out.flush();
				    }
				};
			}
			
			StringToBytes ins = getInstance(out);
			
			if (a.getFlag("-i") == null) {
				if (a.getArgument("content") == null) {
					ins.write(Main.getInput("请输入神秘代码："));
				}
				else {
					ins.write(a.getArgument("content"));
				}
			}
			else {
				Reader reader = new InputStreamReader(
						new FileInputStream(a.getFlag("-i").getValues()[0]), a.getFlag("-i").getValues()[1]);
				
				int i;
				while ((i = reader.read()) != -1) {
					if (i != '\n')
						ins.write(i);
				}
				
				reader.close();
			}
			
			ins.flush();
			System.out.println();
		}
	}

	protected OutputStream out;
	
	public StringToBytes(OutputStream out) {
		this.out = out;
	}

	public static Executer getExecuter(String name) {
		if (name.equalsIgnoreCase("b64d"))
			return Base64Decoder.Executer.INSTANCE;
		if (name.equalsIgnoreCase("b32d"))
			return Base32Decoder.Executer.INSTANCE;
		if (name.equalsIgnoreCase("hexd"))
			return HexDecoder.Executer.INSTANCE;
		if (name.equalsIgnoreCase("bind"))
			return BinDecoder.Executer.INSTANCE;
		return null;
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}
}
