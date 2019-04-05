package converter.command;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Command {

	List<String> args;
	
	int count;
	
	boolean latestOptional;
	
	List<Flag> flags;
	
	Command(List<String> args, List<Flag> flags, boolean latestOptional) {
		this.args = args;
		this.flags = flags;
		this.latestOptional = latestOptional;
	}
	
	public Arguments interpret(String[] args) {
		count = 0;
		
		Queue<String> qargs = new LinkedBlockingQueue<String>(Arrays.asList(args));
		
		Arguments a = new Arguments();
		
		while (!qargs.isEmpty()) {
			String next = qargs.remove();
			if (next.startsWith("-")/* || next.startsWith("/")*/)
				flag(qargs, next, a);
			else
				text(qargs, next, a);
		}
		
		if (latestOptional) {
			if (count < this.args.size() - 1)
				throw new IllegalArgumentException();
		}
		else {
			if (count < this.args.size())
				throw new IllegalArgumentException();
		}
		
		return a;
	}

	private void text(Queue<String> qargs, String next, Arguments a) {
		if (count < args.size())
			a.args.put(args.get(count++), next);
		else
			throw new IllegalArgumentException();
	}

	private void flag(Queue<String> qargs, String next, Arguments a) {
		Optional<Flag> op = flags.stream().filter(f -> f.name == next).findFirst();
		
		if (op.isPresent()) {
			String[] array = new String[op.get().after];
			
			for (int i = 0; i < array.length; i++) {
				if (qargs.isEmpty())
					throw new IllegalArgumentException();
				
				String str = qargs.remove();
				if (str.startsWith("-") || str.startsWith("/"))
					throw new IllegalArgumentException();
				
				array[i] = str;
			}
			
			a.flags.put(next, new ArgumentFlag(next, array));
		} else {
			throw new IllegalArgumentException();
		}
	}
}
