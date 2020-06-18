package vk.com.app7406601.vkstalkerbot;

import com.vk.api.sdk.objects.messages.Message;

public class Weather extends Command {
	
	public Weather(String name) {
		super(name);
	}
	
	public void exec(Message message) {
		new VKManager().sendMesage(getWeather(), message.getUserId());
	}
	
	public void service() {
		
	}
	
	private String getWeather() {
		String weather;
		try {
			
		}
	}

}
