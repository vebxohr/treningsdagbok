package utility;

import java.time.LocalTime;

public class Utility {
	
	public static Boolean isTime(String timeString) {
		try {
			LocalTime.parse(timeString);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}

}
