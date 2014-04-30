package at.fh.technikum.wien.koller.krammer.commands;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import commons.Globals;

import at.fh.technikum.wien.koller.krammer.dao.DaoFactory;
import at.fh.technikum.wien.koller.krammer.dao.IKontaktDao;
import at.fh.technikum.wien.koller.krammer.filter.KontaktFilter;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.ResponseBuilder;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.enums.StatusCode;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Request;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Response;
import at.technikum.wien.winterhalderkreuzriegler.swe1.common.domain.interfaces.Uri;

public class GetKontaktFilterCommand implements ICommand {

	@Override
	public Response handleRequest(Uri uri, Request request) {
		Response r = ResponseBuilder.buildResponse(StatusCode.STATUS_200);
		IKontaktDao kd = DaoFactory.createKontaktDao();
		ByteOutputStream temp = new ByteOutputStream();
		String requestData = "failed";

		try {
			
			InputStreamReader reader = new InputStreamReader(request.getBody());

			StringBuilder buf = new StringBuilder();
			char[] cbuf = new char[2048];
			int num;

			while (-1 != (num = reader.read(cbuf))) {
				buf.append(cbuf, 0, num);
			}

			InputStream is = new DataInputStream(new ByteArrayInputStream(buf
					.toString().getBytes(Globals.CHARSET)));

			XMLDecoder decoder = new XMLDecoder(is);
			KontaktFilter kf = (KontaktFilter) decoder.readObject();
			decoder.close();

			final XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(
					temp));
			encoder.writeObject(kd.getFilterKontakte(kf));
			encoder.close();

			r.setContentLength(temp.size());
			r.setContentType(Globals.XML_MIME);
			r.setBody(new ByteArrayInputStream(temp.getBytes()));
			
			if(temp != null)
				temp.close();

			return r;

		} catch (IOException e) {
			
			r.setContentLength(requestData.length());
			r.setContentType(Globals.XML_MIME);
			r.setBody(new ByteArrayInputStream(requestData.getBytes()));
			
			if(temp != null)
				temp.close();
			
			return r;
		}
	}
}
