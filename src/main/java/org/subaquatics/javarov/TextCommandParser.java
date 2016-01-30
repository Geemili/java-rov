package org.subaquatics.javarov;

import com.flipkart.lois.channel.api.SendChannel;
import com.flipkart.lois.channel.exceptions.ChannelClosedException;
import org.subaquatics.javarov.commands.*;

public class TextCommandParser {

	private SendChannel<Command> robot;

	public TextCommandParser(SendChannel<Command> robot) {
		this.robot = robot;
	}

	/**
	 * @return Returns a string describing the error or null.
	 */
	public String parse(String input) {
		try {
			String[] lines = input.split("\n");
			for (String line: lines) {
				String trimmed = line.trim();
				if (trimmed.length() > 0 && trimmed.charAt(0)=='#') {
					continue;
				}
				String[] tokens = trimmed.split(" ");
				if (tokens.length > 0) {
					switch(tokens[0].toLowerCase()) {
						case "set-motorpins":
							break;
						case "set-pwmbounds":
							break;
						case "echo-string":
							if (tokens.length < 2 || tokens.length > 2) {
								return "\"echo-string\" takes only one argument!";
							}
							for (int i=0; i<tokens[1].length(); i++) {
								byte c = (byte)(tokens[1].charAt(i));
								robot.send(new EchoByteCommand(c));
							}
							break;
						default:
							return "Error: \""+tokens[0]+"\" is not a command.";
					}
				}
			}
		} catch(ChannelClosedException e) {
			e.printStackTrace();
			return e.toString();
		} catch(InterruptedException e) {
			e.printStackTrace();
			return e.toString();
		}
		return null;
	}

}