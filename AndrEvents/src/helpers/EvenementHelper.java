package helpers;

import java.util.Date;

import model.Evenement;

import org.joda.time.Period;

public class EvenementHelper {
	public static String getFormattedDate(Evenement evenement) {
		Period interval = new Period(evenement.getDateDebut().getTime(),
				evenement.getDateFin().getTime());
		if (interval.getDays() > 0) {
			return "Du "
					+ DateHelper.FORMATTED_DATE_FORMAT.format(new Date(
							evenement.getDateDebut().getTime()))
					+ " au "
					+ DateHelper.FORMATTED_DATE_FORMAT.format(new Date(
							evenement.getDateFin().getTime()));
		}
		return "Le "
				+ DateHelper.FORMATTED_DATE_FORMAT.format(new Date(evenement
						.getDateDebut().getTime()))
				+ " à "
				+ DateHelper.TIME_FORMAT.format(new Date(evenement.getDateFin()
						.getTime()));
	}
}
