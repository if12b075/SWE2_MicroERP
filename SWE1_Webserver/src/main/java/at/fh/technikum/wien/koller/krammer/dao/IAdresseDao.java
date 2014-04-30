package at.fh.technikum.wien.koller.krammer.dao;

import java.util.List;

import at.fh.technikum.wien.koller.krammer.models.Adresse;

public interface IAdresseDao {
	public void create(Adresse a);
	public void update(Adresse a);
	public void delete(long id);
	public List<Adresse> getAlleAdressenVonKontakt(long kontaktid);
	
}
