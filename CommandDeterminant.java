package vk.com.app7406601.vkstalkerbot;

import java.util.Collection;

import com.vk.api.sdk.objects.messages.Message;

//Определяет команду

public class CommandDeterminant {
	public static Command getCommand(Collection<Command> commands, Message message) {
		String body = message.getBody();
		for(Command command : commands) {
			if(command.name.equals(body.split(" ")[0])) {  //скипаем пробелы или разеделяем пробелами
				return command;
			}
		}
		return new Unknown("unknown");
	}
}
