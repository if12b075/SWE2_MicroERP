package at.fh.technikum.wien.koller.krammer.dao;

import java.util.List;

import at.fh.technikum.wien.koller.krammer.filter.KontaktFilter;
import at.fh.technikum.wien.koller.krammer.models.Kontakt;

public interface IKontaktDao {
	public List<Kontakt> getAlleKontakte();
	public List<Kontakt> getFilterKontakte(KontaktFilter kf);
}
