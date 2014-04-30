package at.fh.technikum.wien.koller.krammer.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.fh.technikum.wien.koller.krammer.dao.IKontaktDao;
import at.fh.technikum.wien.koller.krammer.filter.KontaktFilter;
import at.fh.technikum.wien.koller.krammer.models.Adresse;
import at.fh.technikum.wien.koller.krammer.models.Firma;
import at.fh.technikum.wien.koller.krammer.models.Kontakt;
import at.fh.technikum.wien.koller.krammer.models.Person;
import at.fh.technikum.wien.koller.krammer.models.AdresseEnums;

public class KontaktMockDao implements IKontaktDao{
	private ArrayList<Kontakt> al;
	
	public KontaktMockDao() {
		al = new ArrayList<Kontakt>();
		al.add(new Person("klaus","kleber",new Date(),1,new Adresse(AdresseEnums.WOHNADRESSE,"tri tra weg 1", "stock 5 top 7",1010,"Wien",1),1));
		al.add(new Firma("kaspale gmbh","AT02342345",new Adresse(AdresseEnums.WOHNADRESSE,"tri tra weg 1", "stock 5 top 7",1010,"Wien",1),2));
	}
	
	@Override
	public List<Kontakt> getAlleKontakte() {
		return al;
	}

	@Override
	public List<Kontakt> getFilterKontakte(KontaktFilter kf) {
		// TODO Auto-generated method stub
		return null;
	}

}
