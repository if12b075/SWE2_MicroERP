package at.fh.technikum.wien.koller.krammer.dao;

import java.util.List;

import at.fh.technikum.wien.koller.krammer.models.Rechnung;

public interface IRechnungDao {
	public void create(Rechnung r);
	public void update(Rechnung r);
	public void delete(long id);
	public List<Rechnung> getAlleRechnungen();
}
