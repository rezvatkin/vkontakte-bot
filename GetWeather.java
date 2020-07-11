package vk.com.app7406601.vkstalkerbot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class GetWeather {
	
	String city;	
	String API_CALL_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
	Properties properties = new Properties();
	String weatherApiToken;// = "&units=metric&APPID=0624c6dc41ec4cd9f7a1a9d1f507d8e7"; 
	
	public GetWeather(String city){
		
		this.city = city;
	
		try {
		properties.load(new FileInputStream("src/main/resources/vkconfig.properties"));
		weatherApiToken = "&units=metric&APPID=" + properties.getProperty("weather_key");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Ошибка прни загрузке файла конфигурации");
		}
	}
	
	public String getWeatherRawData() {
		StringBuffer response = new StringBuffer();
		try {
			//создаем строку для запроса начальная часть, город из конструктора, токен приложения.
			String urlString = API_CALL_TEMPLATE + city + weatherApiToken;
			System.out.println(urlString);
			//создаем соединение, используя объект
			URL urlObject = new URL(urlString);
			//создаём соединение, используя объект
			HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
			//выбираем тип запроса (GET)
			connection.setRequestMethod("GET");
			//тут мы указываем, данные о себе, что мы можем принять всё то,
			//что примет и любой современный браузер
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			//В начало ответа сервер всегда вставляет число, по которому можно судить, прошло ли всё хорошо.
			//200 - значит OK
			int responseCode = connection.getResponseCode();
			//на несуществующий город или город с опечаткой, сервер выдаст код ответа 404,
			//бросаем на него исключение, чтобы обработать на уровне повыше и предложить
			//пользователю ввести город заново
			if(responseCode == 404) {
				System.out.println("somthing wrong");
				throw new IllegalArgumentException();
			}
			// создаём поток, вычитываем все строки, и склеиваем в одну большую строку,
			//которую будем потом обрабатывать в других методах
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	public String getWeatherData (String data) throws IOException {
		ArrayList<String> weatherList = new ArrayList<>();
		StringBuilder temperature = new StringBuilder();
		//JsonNode - это один из узлов в древовидной иерархии, от которого идут ветви
		//получаем узел, который называется "list"
		JsonNode arrNode = new ObjectMapper().readTree(data).get("list");
		//если это действительно массив узлов
		if(arrNode.isArray()) {
			//выполняем для каждого узла, который содержится в массиве
			for(final JsonNode objNode : arrNode) {
				//в атрибуте "dt_txt" каждого маленького узла хранилось время прогноза, я отобрал данные за 6 утра и 15 вечера
				String forecastTime = objNode.get("dt_txt").toString();
				if(forecastTime.contains("6:00") || forecastTime.contains("15:00")) {
					weatherList.add(objNode.toString());
				}
				
			}
			
				
			}
			for (String line : weatherList) {
				ObjectMapper objectMapper = new ObjectMapper();
				
				String dayAndTime = objectMapper.readTree(line).get("dt_txt").toString();
				temperature.append(dayAndTime + " ");
				
				JsonNode mainNode = objectMapper.readTree(line).get("main");
				temperature.append(mainNode.get("temp").asText() + '\u2103' + " " + '\n'); //mainNode.get("temp").asText();
							
		}
		return temperature.toString();
	}
	
}
