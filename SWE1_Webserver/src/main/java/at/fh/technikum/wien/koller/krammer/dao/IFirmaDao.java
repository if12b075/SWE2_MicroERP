package at.fh.technikum.wien.koller.krammer.dao;

import at.fh.technikum.wien.koller.krammer.models.Firma;

public interface IFirmaDao {
	public void create(Firma f);
	public void update(Firma f);
	public void delete(long id);
}
