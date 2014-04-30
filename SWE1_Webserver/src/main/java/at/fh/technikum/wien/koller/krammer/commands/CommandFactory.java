package at.fh.technikum.wien.koller.krammer.commands;

import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

public class CommandFactory {
	public static ICommand createCommand(Uri uri) {
		String choice = uri.getPath().substring(7, uri.getPath().length()-1);
		ICommand c = null;
		switch(choice) {
		case commons.CommandRequestTitel.GET_ALLE_KONTAKTE:
				c = new GetAlleKontakteCommand();
			break;
		case commons.CommandRequestTitel.GET_ALLE_RECHNUNGEN:
			c = new GetAlleRechnungenCommand();
			break;
		case commons.CommandRequestTitel.GET_FILTER_KONTAKTE:
			c = new GetKontaktFilterCommand();
			default:
				break;
		}
		
		return c;	
	}
}
