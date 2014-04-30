package at.fh.technikum.wien.koller.krammer.dao;

import java.util.List;

import at.fh.technikum.wien.koller.krammer.models.Rechnungszeile;

public interface IRechnungszeileDao {
	public void create(Rechnungszeile r);
	public void update(Rechnungszeile r);
	public void delete(long id);
	public List<Rechnungszeile> getAlleRechnungszeilenZuRechnung(long rechnungsid);
}
