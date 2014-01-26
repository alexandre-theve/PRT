package helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateHelper {
	public static final SimpleDateFormat FORMATTED_DATE_FORMAT = new SimpleDateFormat(
			"EEEE dd/MM/yyyy , hh'h'mm", Locale.FRANCE);
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy", Locale.FRANCE);
	public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
			"hh'h'mm", Locale.FRANCE);
}
