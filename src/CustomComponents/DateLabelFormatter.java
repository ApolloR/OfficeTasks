package CustomComponents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {
	
	//private String datePattern = "yyyy-MM-dd";
	private String datePattern = "dd-MM-yyyy";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
		
	@Override
	public Object stringToValue(String text)  {		
		try {
			return dateFormatter.parseObject(text);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Error DateLabelFormatter em stringToValue" + e.getMessage());
			e.printStackTrace();
		}
		
		return text;
		
		
		
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value!=null){
			Calendar cal = (Calendar) value;
			return dateFormatter.format(cal.getTime());
		}
		return "";
	}

}

 
