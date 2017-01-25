
public class WeatherObject {
	private String temp;
	private String pop;
	private String weatherdescr;
	
	public WeatherObject (String temp, String pop, String weatherdescr)
	{
		this.temp = temp;
		this.pop = pop;
		this.weatherdescr = weatherdescr;
	}
	
	public String getTemp() {return this.temp;}
	
	public String getPop() {return this.pop;}
	
	public String getWeatherdescr() {return this.weatherdescr;} 
}
