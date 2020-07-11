package vk.com.app7406601.vkstalkerbot;



import java.io.IOException;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Message;

public class Weather extends Command {
	
	public Weather(String name) {
		super(name);
	}
	
	public void exec(Message message) {
		new VKManager().sendMesage("Введите город", message.getUserId());
		VKCore vkCore;
		try {
			vkCore = new VKCore();
			while (true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block 
					e1.printStackTrace();
				}
				try {
					message = vkCore.getMessage();
					if(message != null) {
						System.out.println(message.toString());
						String city = message.getBody();
						new VKManager().sendMesage(getWeather(city), message.getUserId());
					}
				} catch ( ClientException | ApiException | NullPointerException e) {
					System.out.println("Возникли проблкемы");
					final int RECONNECT_TIME = 10000;
					System.out.println("Повторное соединение через " + RECONNECT_TIME/1000 + "секунд");
					try {
						Thread.sleep(RECONNECT_TIME);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
				}
			}
			
			
		} catch (ClientException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ApiException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		

	}
	
	public void service() {
		
	}
	
		private String getWeather(String city)  {
		
//		new VKManager().sendMesage("Введите город", message.getUserId());
		String answer = "Попробуй еще раз";
					
		//} catch(IOException e) {
			//System.out.println("что то пошло не так");
		
			GetWeather weather = new GetWeather(city);
			try {
				answer = weather.getWeatherData(weather.getWeatherRawData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return answer;
	}
}

