package vk.com.app7406601.vkstalkerbot;

import com.vk.api.sdk.objects.messages.Message;

/**
 * Обработка сообщений, получаемых через сервис Вконтакте. Имеет ряд дополнительной информации.
 * @param message сообщение (запрос) пользователя
 */

public class Commander {
	public static void execute(Message message) {
		CommandDeterminant.getCommand(CommandManager.getCommands(), message).exec(message);
	}
}
