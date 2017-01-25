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
		    String line;
		    String input = "[";
		    while((line = in.readLine() ) != null )
		    {
		    	
		    	input  = input + "" + line;
		    	
		    }
		    input = input + "]";
		    parser(input);
	
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
		obj = array.getJSONObject(0).getJSONObject("data");
		array = obj.getJSONArray("temperature");
		System.out.println("array length: " + array.length());
		
		 for(int i=0; i<array.length(); i++){
			 System.out.println(array.get(i).toString());
		 }
		 
		}
		catch (Exception e) { System.out.println(e.getMessage());}
	}

}
