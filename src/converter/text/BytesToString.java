package converter.text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import converter.Main;
import converter.command.Arguments;
import converter.command.Command;
import converter.command.CommandBuilder;


public abstract class BytesToString extends OutputStream {
	
	public static abstract class Executer {
		
		private static final Command cmd = new CommandBuilder()
				.flag("-i", 1)
				.flag("-o", 2)
				.flag("-e", 1)
				.text("content")
				.latestOptional()
				.build();
		
		abstract public BytesToString getInstance(Writer writer);
		
		public void execute(String[] args) throws IOException {
			Arguments a = cmd.interpret(args);
			
			Writer w;
			
			if (a.getFlag("-o") == null) {
				w = new OutputStreamWriter(System.out);
			}
			else {
				w = new OutputStreamWriter(
						new FileOutputStream(a.getFlag("-o").getValues()[0]),
						a.getFlag("-o").getValues()[1]);
			}
			
			BytesToString ins = getInstance(w);
			
			if (a.getFlag("-i") == null) {
				String content;
				
				if (a.getArgument("content") == null) {
					content = Main.getInput("请输入需要转换的数据：");
				}
				else {
					content = a.getArgument("content");
				}
				
				if (a.getFlag("-e") == null) {
					ins.write(content.getBytes());
				}
				else {
					ins.write(content.getBytes(a.getFlag("-e").getValues()[0]));
				}
				
				ins.close();
			}
			else {
				FileInputStream in = new FileInputStream(
						a.getFlag("-i").getValues()[0]);
				
				int i;
				if (a.getFlag("-e") == null) {
					while ((i = in.read()) != -1) {
						ins.write(i);
					}
					in.close();
					ins.close();
				}
				else {
					Reader reader = new InputStreamReader(in);
					Writer writer = new OutputStreamWriter(ins, 
							a.getFlag("-e").getValues()[0]);
					
					while ((i = reader.read()) != -1) {
						writer.write(i);
					}
					
					reader.close();
					writer.close();
				}
			}
		}
	}
	
	protected Writer writer;
	
	public BytesToString(Writer writer) {
		this.writer = writer;
	}
	
	@Override
	public void flush() throws IOException {
		writer.flush();
	}
}