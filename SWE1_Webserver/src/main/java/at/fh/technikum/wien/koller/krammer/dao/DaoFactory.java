package at.fh.technikum.wien.koller.krammer.dao;

import at.fh.technikum.wien.koller.krammer.dao.impl.KontaktImplDao;
import at.fh.technikum.wien.koller.krammer.dao.impl.RechnungImplDao;
import at.fh.technikum.wien.koller.krammer.dao.mock.KontaktMockDao;

public class DaoFactory {
	private static final boolean MOCK_KONTAKT = false;
	private static final boolean MOCK_RECHNUNG = false;
	
	public static IKontaktDao createKontaktDao() {
		if(MOCK_KONTAKT)
			return new KontaktMockDao();
		else
			return new KontaktImplDao();
	}
	
	public static IRechnungDao createRechnungDao() {
		if(MOCK_RECHNUNG)
			return null;
		else
			return new RechnungImplDao();
	}
	
}
