package converter.command;

import java.util.HashMap;
import java.util.Map;

public class Arguments {

	Map<String, String> args = new HashMap<String, String>();
	
	Map<String, ArgumentFlag> flags = new HashMap<String, ArgumentFlag>();
	
	Arguments() {
		
	}
	
	public String getArgument(String name) {
		return args.get(name);
	}
	
	public ArgumentFlag getFlag(String name) {
		return flags.get(name);
	}
}
