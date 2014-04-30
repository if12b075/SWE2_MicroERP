package at.fh.technikum.wien.koller.krammer.dao;

import at.fh.technikum.wien.koller.krammer.models.Person;

public interface IPersonDao {
	public void create(Person p);
	public void update(Person p);
	public void delete(long id);
}
