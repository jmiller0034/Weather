import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.json.*;

// api key AIzaSyA_MTvcjFSCaapbmnCm0PWbPPTmcDYpfdI
// https://maps.googleapis.com/maps/api/geocode/json?address=Austin&components=administrative_area:TX|country:US&key=AIzaSyA_MTvcjFSCaapbmnCm0PWbPPTmcDYpfdI
public class Weather {
	
	public static JPanel gui;
	public static JTextArea editArea;
	final static String DEGREE = "\u00b0";
	public static double lat, lng;
	public static String current;
	public static int SIZE = 13;
	public static String location;
	public static String[] periodname = new String[SIZE];
	//public static ArrayList<WeatherObject> weathers;
	public static WeatherObject[]  weathers = new WeatherObject[SIZE]; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url = null;
		
		
		
		//= JPanel(new BorderLayout());
		gui = new JPanel();
        gui.setBorder(new EmptyBorder(2,3,2,3));

        // adjust numbers for a bigger default area
        editArea = new JTextArea(20,50);
        // adjust the font to a monospaced font.
        Font font = new Font(
                Font.MONOSPACED, 
                Font.PLAIN, 
                editArea.getFont().getSize());
        editArea.setFont(font);
        gui.add(new JScrollPane(editArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));

		JButton button, button1;
		button = new JButton();
		
		button.setText("Click Me");
		button.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  System.out.println(editArea.getText());
				  location = editArea.getText();
				  editArea.setText("");
				  doWork(location);
				  } 
			 
				} );
		
		gui.add(button, JButton.CENTER);
		button1 = new JButton();
		button1.setText("Clear");
		button1.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  editArea.setText("");
				  } 
			 
				} );
		gui.add(button1, JButton.CENTER);
		JFrame frame = new JFrame("Weather");
		frame.add(gui);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		

		//5. Show it.
		frame.setVisible(true);

		
	}
	
	public static void doWork(String location)
	{
		URL url;
		String line;
	    String input = "[";
		
		try
		{

			//location = scanner.nextLine();
			location = location.replaceAll("\\s+","");
			url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=" + location +
					"&components=administrative_area:TX|country:US&key=AIzaSyA_MTvcjFSCaapbmnCm0PWbPPTmcDYpfdI");
			BufferedReader bRead = new BufferedReader(new InputStreamReader(
                    url.openConnection().getInputStream()));
		    while((line = bRead.readLine() ) != null )
		    {
		    	
		    	input  = input + "" + line;
		    	
		    }
		    input = input + "]";
		    parseLoc(input);
			
			
			
			String urlString = "http://forecast.weather.gov/MapClick.php?lat=";
			urlString = urlString + lat;
			urlString = urlString + "&lon="; 
			urlString = urlString +lng; 
			urlString = urlString + "&FcstType=json";
			url = new URL(urlString);
			URLConnection connect = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(
                    connect.getInputStream()));
		    input = "[";
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
	
	//function used to create a JSON object and parse all data needed and call helper functions to access data
	public static void parser(String input)
	{
		JSONObject obj, loc;
		JSONArray temp, pop, weather, text;
		String tempS, weatherS, popS, textS;
		try
		{
			
		JSONArray array = new JSONArray(input);
		getCurrentWeather(array.getJSONObject(0).getJSONObject("currentobservation"));
		
		loc = array.getJSONObject(0).getJSONObject("location");
		System.out.println(loc.getString("areaDescription"));
		editArea.setText(loc.getString("areaDescription") + "\n");
		System.out.println(current);
		editArea.append(current + "\n");
		obj = array.getJSONObject(0).getJSONObject("time");
		populateperiodname(obj);
		obj = array.getJSONObject(0).getJSONObject("data");
		temp = obj.getJSONArray("temperature");
		pop = obj.getJSONArray("pop");
		weather = obj.getJSONArray("weather");
		text = obj.getJSONArray("text");
		 for(int i=0; i< SIZE; i++){
			 tempS = temp.getString(i);
			 popS = pop.get(i).toString();
			 if (popS.equals("null")) {popS = "0";}
			 weatherS = weather.getString(i);
			 textS = text.getString(i);
			 weathers[i] = (new WeatherObject(tempS, popS, weatherS, textS));
		 }
		 
		}
		catch (Exception e) { System.out.println(e.getMessage());
			e.printStackTrace(System.out);
		}
	}
	
	public static void parseLoc(String input)
	{
		JSONObject obj;
		JSONArray array;
		try
		{
			array = new JSONArray(input);
			obj = array.getJSONObject(0);
			array = obj.getJSONArray("results");
			obj = array.getJSONObject(0);
			obj = obj.getJSONObject("geometry").getJSONObject("location");
			
			lat = obj.getDouble("lat");
			lng = obj.getDouble("lng");
		}
		catch (Exception e) {e.printStackTrace(System.out);}
	}
	
	// helper function used to populate an array with period names ie today, tonight, etc
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

	
	//helper function used to get the current weather data
	public static void getCurrentWeather(JSONObject o)
	{
		String deg;
		try
		{
			deg = o.getString("Windd");
			deg = toDirection(Double.parseDouble(deg));
			current = "Currently: " + o.getString("Temp") + DEGREE +"F, " + o.getString("Winds") + "MPH  "
				+ deg + ", " + o.getString("Weather") + ", " + o.getString("Dewp") + DEGREE + "F Dew";
		}
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	
	//helper method used to display all stored future weather data
	public static void display()
	{
		String out;

		for (int i = 0; i < SIZE; i++)
		{
			out = periodname[i] + ": " + weathers[i].getTemp() + DEGREE +"F, " + weathers[0].getPop() + "% precip, ";
			out = out + weathers[i].getWeatherdescr();
			System.out.println(out);
			editArea.append(out + "\n");
		}
	}
	
	//helper function used to convert degrees to cardinal directions
	
	public static String toDirection(double x)
	{
		
		String directions[] = {"N", "NE", "E", "SE", "S", "SW", "W", "NW", "N"};
	    return directions[ (int)Math.round((  ((double)x % 360) / 45)) ];
	}
}
