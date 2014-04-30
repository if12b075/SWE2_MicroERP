package at.fh.technikum.wien.koller.krammer.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.fh.technikum.wien.koller.krammer.dao.IKontaktDao;
import at.fh.technikum.wien.koller.krammer.database.DatabaseConnection;
import at.fh.technikum.wien.koller.krammer.filter.KontaktFilter;
import at.fh.technikum.wien.koller.krammer.merp.constants.MicroERPConstants;
import at.fh.technikum.wien.koller.krammer.models.Firma;
import at.fh.technikum.wien.koller.krammer.models.Kontakt;
import at.fh.technikum.wien.koller.krammer.models.Person;

public class KontaktImplDao implements IKontaktDao {
	private static Connection c = DatabaseConnection.getConnection(MicroERPConstants.DB_CON);
	
	
	@Override
	public List<Kontakt> getAlleKontakte() {
		List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
		
		String selectPerson = "SELECT * FROM TB_PERSON";
		String selectFirma = "SELECT * FROM TB_FIRMA";
		
			try {
				PreparedStatement selectPersonStatement = c.prepareStatement(selectPerson);
				PreparedStatement selectFirmaStatement = c.prepareStatement(selectFirma);
				
				ResultSet rs = selectPersonStatement.executeQuery();
				
				while(rs.next()) {
					Person per = new Person();
					per.setId(rs.getLong("ID_PERSON"));
					per.setTitel(rs.getString("TITEL"));
					per.setVorname(rs.getString("VORNAME"));
					per.setNachname(rs.getString("NACHNAME"));
					per.setSuffix(rs.getString("SUFFIX"));
					per.setGeburtstag(rs.getDate("GEB_DATUM"));
					per.setFirmaid(rs.getLong("TB_FIRMA_ID"));
					
					kontaktListe.add(per);
					
				}
				
				selectPersonStatement.close();
				
				rs = selectFirmaStatement.executeQuery();
				
				while(rs.next()) {
					Firma f = new Firma();
					f.setId(rs.getLong("ID_FIRMA"));
					f.setName(rs.getString("NAME"));
					f.setUid(rs.getString("UID_NR"));
					
					kontaktListe.add(f);
					
				}
				
				selectFirmaStatement.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return kontaktListe;
	}


	@Override
	public List<Kontakt> getFilterKontakte(KontaktFilter kf) {
		try {
			List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
			
			if(kf.getIsFirma()!=null) {
				if(kf.getIsFirma()) {
					String select = "SELECT * FROM TB_FIRMA WHERE NAME LIKE ?";
					
					PreparedStatement selectStatement = c.prepareStatement(select);
					selectStatement.setString(1, "%" + kf.getName() + "%");
					
					ResultSet rs = selectStatement.executeQuery();
					
					while(rs.next()) {
						Firma f = new Firma();
						f.setId(rs.getLong("ID_FIRMA"));
						f.setName(rs.getString("NAME"));
						f.setUid(rs.getString("UID_NR"));
						
						kontaktListe.add(f);
						
					}
					
					selectStatement.close();
				} else {
					String select = "SELECT * FROM TB_PERSON WHERE (VORNAME LIKE ?) OR (NACHNAME LIKE ?)";
					
					PreparedStatement selectStatement = c.prepareStatement(select);
					selectStatement.setString(1, "%" + kf.getName() + "%");
					selectStatement.setString(2, "%" + kf.getName() + "%");
					
					ResultSet rs = selectStatement.executeQuery();
					
					while(rs.next()) {
						Person per = new Person();
						per.setId(rs.getLong("ID_PERSON"));
						per.setTitel(rs.getString("TITEL"));
						per.setVorname(rs.getString("VORNAME"));
						per.setNachname(rs.getString("NACHNAME"));
						per.setSuffix(rs.getString("SUFFIX"));
						per.setGeburtstag(rs.getDate("GEB_DATUM"));
						per.setFirmaid(rs.getLong("TB_FIRMA_ID"));
						
						kontaktListe.add(per);
						
					}
					
					selectStatement.close();
				}
					
			} else {
				String selectPerson = "SELECT * FROM TB_PERSON WHERE (VORNAME LIKE ?) OR (NACHNAME LIKE ?)";
				String selectFirma = "SELECT * FROM TB_FIRMA WHERE NAME LIKE ?";
				
				PreparedStatement selectPersonStatement = c.prepareStatement(selectPerson);
				PreparedStatement selectFirmaStatement = c.prepareStatement(selectFirma);
				
				selectFirmaStatement.setString(1, "%" + kf.getName() + "%");
				selectPersonStatement.setString(1, "%" + kf.getName() + "%");
				selectPersonStatement.setString(2, "%" + kf.getName() + "%");
				
				ResultSet rs = selectPersonStatement.executeQuery();
				
				while(rs.next()) {
					Person per = new Person();
					per.setId(rs.getLong("ID_PERSON"));
					per.setTitel(rs.getString("TITEL"));
					per.setVorname(rs.getString("VORNAME"));
					per.setNachname(rs.getString("NACHNAME"));
					per.setSuffix(rs.getString("SUFFIX"));
					per.setGeburtstag(rs.getDate("GEB_DATUM"));
					per.setFirmaid(rs.getLong("TB_FIRMA_ID"));
					
					kontaktListe.add(per);
					
				}
				
				selectPersonStatement.close();
				
				rs = selectFirmaStatement.executeQuery();
				
				while(rs.next()) {
					Firma f = new Firma();
					f.setId(rs.getLong("ID_FIRMA"));
					f.setName(rs.getString("NAME"));
					f.setUid(rs.getString("UID_NR"));
					
					kontaktListe.add(f);
					
				}
				
				selectFirmaStatement.close();

			}
			
			
			return kontaktListe;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
