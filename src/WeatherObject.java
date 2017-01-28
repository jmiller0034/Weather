
public class WeatherObject {
	private String temp;
	private String pop;
	private String weatherdescr;
	private String text;
	
	public WeatherObject (String temp, String pop, String weatherdescr, String text)
	{
		this.temp = temp;
		this.pop = pop;
		this.weatherdescr = weatherdescr;
		this.text = text;
	}
	
	public String getTemp() {return this.temp;}
	
	public String getPop() {return this.pop;}
	
	public String getWeatherdescr() {return this.weatherdescr;}
	
	public String getText() {return this.text;}
}
