package vk.com.app7406601.vkstalkerbot;

import com.vk.api.sdk.objects.messages.Message;

public class Unknown extends Command {
	public Unknown(String name) {
		super(name);
	}
	@Override
	public void exec(Message message) {
		new VKManager().sendMesage("Неизвестная команда", message.getUserId());
		}
}
