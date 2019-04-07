package converter.text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import converter.Main;
import converter.MorseDecoder;
import converter.MorseEncoder;
import converter.command.Arguments;
import converter.command.Command;
import converter.command.CommandBuilder;

public abstract class StringToString extends Writer {
	
	public static abstract class Executer {
		
		private static final Command cmd = new CommandBuilder()
				.flag("-i", 2)
				.flag("-o", 2)
				.text("content")
				.latestOptional()
				.build();
		
		abstract public StringToString getInstance(Writer writer);
		
		public void execute(String[] args) throws IOException {
			Arguments a = cmd.interpret(args);
			
			Writer writer;
			if (a.getFlag("-o") == null) {
				writer = new OutputStreamWriter(System.out);
			}
			else {
				writer = new OutputStreamWriter(
						new FileOutputStream(a.getFlag("-o").getValues()[0]), 
						a.getFlag("-o").getValues()[1]);
			}
			
			StringToString ins = getInstance(writer);
			
			if (a.getFlag("-i") == null) {
				if (a.getArgument("content") == null)
					ins.write(Main.getInput("请输入需要转换的数据："));
				else
					if (a.getArgument("content").startsWith(";"))
						ins.write(a.getArgument("content").substring(1));
					else
						ins.write(a.getArgument("content"));
			}
			else {
				Reader reader = new InputStreamReader(
						new FileInputStream(a.getFlag("-i").getValues()[0]),
						a.getFlag("-i").getValues()[1]);
				
				int i;
				while ((i = reader.read()) != -1)
					if (i != '\r' && i != '\n')
						ins.write(i);
				
				reader.close();
			}
			
			ins.flush();
			System.out.println();
		}
	}
	
	protected Writer writer;
	
	public StringToString(Writer writer) {
		this.writer = writer;
	}

	public static Executer getExecuter(String name) {
		if (name.equalsIgnoreCase("more"))
			return MorseEncoder.Executer.INSTANCE;
		if (name.equalsIgnoreCase("mord"))
			return MorseDecoder.Executer.INSTANCE;
		return null;
	}

	@Override
	public void flush() throws IOException {
		writer.flush();
	}

}
