package at.technikum.wien.koller.krammer.swe2.plugins.microerp;

import at.fh.technikum.wien.koller.krammer.commands.CommandFactory;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;
import at.technikum.wien.winterhalderkreuzriegler.swe1.plugins.interfaces.Pluggable;

public class MicroERPPlugin implements Pluggable{

	@Override
	public Response request(Uri uri, Request request) {
		return CommandFactory.createCommand(uri).handleRequest(uri, request);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
