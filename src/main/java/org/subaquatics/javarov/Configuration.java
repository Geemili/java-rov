package org.subaquatics.javarov;

import java.util.ArrayList;
import org.subaquatics.javarov.commands.*;

public class Configuration {

	public ArrayList<SetMotorPinsCommand> motors = new ArrayList<>();
	public SetPWMBoundsCommand pwmBounds = new SetPWMBoundsCommand(0, 255);

	public static Configuration fromString(String input) {
		Configuration configuration = new Configuration();
		String[] lines = input.split("\n");
		for (String line: lines) {
			String trimmed = line.trim();
			if (trimmed.contains("#")) {
				trimmed = trimmed.substring(0, trimmed.indexOf('#'));
			}
			String[] tokens = trimmed.split(" ");
			if (tokens.length > 0) {
				switch(tokens[0].toLowerCase()) {
					case "set-motorpins":
						if (tokens.length < 5 || tokens.length > 5) {
							return "Correct usage: control-motor <id> <pwm> <left> <right>";
						}
						int id = Integer.parseInt(tokens[1]);
						int pwm = Integer.parseInt(tokens[2]);
						int left = Integer.parseInt(tokens[3]);
						int right = Integer.parseInt(tokens[4]);
						configuration.add(new SetMotorPinsCommand(id, pwm, left, right));
						break;
					case "set-pwmbounds":
						if (tokens.length < 3 || tokens.length > 3) {
							System.out.println("Correct usage: set-pwmbounds <min> <max>");
							continue;
						}
						int min = Integer.parseInt(tokens[1]);
						int max = Integer.parseInt(tokens[2]);
						configuration.add(new SetPWMBoundsCommand(min, max));
						break;
					default:
						return "Error: \""+tokens[0]+"\" is not a configuration command.";
				}
			}
		}
		return configuration;
	}

}