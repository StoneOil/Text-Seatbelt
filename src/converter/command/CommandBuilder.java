package converter.command;

import java.util.ArrayList;
import java.util.List;

public class CommandBuilder {
	
	private List<String> args = new ArrayList<String>();
	
	private List<Flag> flags = new ArrayList<Flag>();
	
	private boolean latestOptional;

	public CommandBuilder() {
	}
	
	public CommandBuilder text(String name) {
		args.add(name);
		return this;
	}
	
	public CommandBuilder flag(String name, int after) {
		flags.add(new Flag(name, after));
		return this;
	}
	
	public CommandBuilder latestOptional() {
		latestOptional = true;
		return this;
	}
	
	public Command build() {
		return new Command(args, flags, latestOptional);
	}
}
