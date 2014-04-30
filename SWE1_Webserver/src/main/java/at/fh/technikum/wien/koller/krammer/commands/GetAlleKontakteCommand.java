package at.fh.technikum.wien.koller.krammer.commands;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import commons.Globals;

import at.fh.technikum.wien.koller.krammer.dao.DaoFactory;
import at.fh.technikum.wien.koller.krammer.dao.IKontaktDao;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

public class GetAlleKontakteCommand implements ICommand {
	
	@Override
	public Response handleRequest(Uri uri, Request request) {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		IKontaktDao kd = DaoFactory.createKontaktDao();
		ByteOutputStream temp = new ByteOutputStream();
		
		final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(temp)); 
		encoder.writeObject(kd.getAlleKontakte());
		encoder.close();
		
		r.setContentLength(temp.size());
		r.setContentType(Globals.XML_MIME);
		r.setBody(new ByteArrayInputStream(temp.getBytes()));
		return r;
	}

}
