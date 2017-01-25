import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class Weather {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		URL url = null;
		try
		{
			String urlString = "http://forecast.weather.gov/MapClick.php?lat=38.4247341&lon=-86.9624086&FcstType=json";
			url = new URL(urlString);
			URLConnection connect = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(
                    connect.getInputStream()));
		    
		     String inputLine;
		        while ((inputLine = in.readLine()) != null) 
		            System.out.println(inputLine);
		        in.close();
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	public static void parser(String input)
	{
		
	}

}
