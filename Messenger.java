package vk.com.app7406601.vkstalkerbot;

import com.vk.api.sdk.objects.messages.Message;

public class Messenger implements Runnable {
	private Message message;
	
	public Messenger(Message message) {
		this.message = message;
	}
	
	@Override
	public void run() {
		Commander.execute(message);
	}
}
