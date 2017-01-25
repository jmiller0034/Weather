import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import org.json.*;
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
		    System.out.println(in.toString());
		    parser(in.toString());
	
	        in.close();
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	
	public static void parser(String input)
	{
		JSONObject obj;
		try
		{
			
		JSONArray array = new JSONArray(input);
		System.out.println("array length: " + array.length());
		 for(int i=0; i<array.length(); i++){
			 //obj = array.getJSONObject(i);
			 //System.out.println(obj.toString());
			 System.out.println(array.get(i).toString());
		 }
		 
		}
		catch (Exception e) { System.out.println(e.getMessage());}
	}

}
