import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.*;
public class Weather {
	
	final static String DEGREE = "\u00b0";
	public static String current;
	public static int SIZE = 13;
	public static String[] periodname = new String[SIZE];
	//public static ArrayList<WeatherObject> weathers;
	public static WeatherObject[]  weathers = new WeatherObject[SIZE]; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url = null;
		
		try
		{
			String urlString = "http://forecast.weather.gov/MapClick.php?lat=30.2672&lon=-97.7431&FcstType=json";
			url = new URL(urlString);
			URLConnection connect = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(
                    connect.getInputStream()));
		    String line;
		    String input = "[";
		    while((line = in.readLine() ) != null )
		    {
		    	
		    	input  = input + "" + line;
		    	
		    }
		    input = input + "]";
		    parser(input);
	
	        in.close();
	        display();
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	public static void parser(String input)
	{
		JSONObject obj, loc;
		JSONArray temp, pop, weather;
		String tempS, weatherS, popS;
		try
		{
			
		JSONArray array = new JSONArray(input);
		getCurrentWeather(array.getJSONObject(0).getJSONObject("currentobservation"));
		
		loc = array.getJSONObject(0).getJSONObject("location");
		System.out.println(loc.getString("areaDescription"));
		System.out.println(current);
		obj = array.getJSONObject(0).getJSONObject("time");
		populateperiodname(obj);
		obj = array.getJSONObject(0).getJSONObject("data");
		temp = obj.getJSONArray("temperature");
		pop = obj.getJSONArray("pop");
		weather = obj.getJSONArray("weather");
		
		 for(int i=0; i< SIZE; i++){
			 tempS = temp.getString(i);
			 popS = pop.get(i).toString();
			 if (popS.equals("null")) {popS = "0";}
			 weatherS = weather.getString(i);
			 weathers[i] = (new WeatherObject(tempS, popS, weatherS));
		 }
		 
		}
		catch (Exception e) { System.out.println(e.getMessage());
			e.printStackTrace(System.out);
		}
	}
	
	public static void populateperiodname(JSONObject inObject)
	{
		try 
		{
			JSONArray array = inObject.getJSONArray("startPeriodName");
			for (int i = 0; i < array.length(); i ++)
			{
				periodname[i] = array.getString(i);
			}
		}
		catch (Exception e) {}
	}

	public static void getCurrentWeather(JSONObject o)
	{
		String deg;
		try
		{
			deg = o.getString("Windd");
			deg = toDirection(Double.parseDouble(deg));
			current = o.getString("Date") + ", " + o.getString("Temp") + DEGREE +", " + o.getString("Winds") + "MPH  "
				+ deg + ", " + o.getString("Weather") + ", " + o.getString("Dewp") + DEGREE;
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public static void display()
	{
		String out;

		for (int i = 0; i < SIZE; i++)
		{
			out = periodname[i] + ": " + weathers[0].getTemp() + DEGREE +", " + weathers[0].getPop() + "%, ";
			out = out + weathers[i].getWeatherdescr();
			System.out.println(out);
		}
	}
	
	public static String toDirection(double x)
	{
		
		String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
	    return directions[ (int)Math.round((  ((double)x % 360) / 45)) ];
	}
}
