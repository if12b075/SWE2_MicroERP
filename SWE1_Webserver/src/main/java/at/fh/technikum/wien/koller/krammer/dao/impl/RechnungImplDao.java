package at.fh.technikum.wien.koller.krammer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import at.fh.technikum.wien.koller.krammer.dao.IRechnungDao;
import at.fh.technikum.wien.koller.krammer.database.DatabaseConnection;
import at.fh.technikum.wien.koller.krammer.merp.constants.MicroERPConstants;
import at.fh.technikum.wien.koller.krammer.models.Rechnung;

public class RechnungImplDao implements IRechnungDao {
	private static Connection c = DatabaseConnection.getConnection(MicroERPConstants.DB_CON);
	
	@Override
	public void create(Rechnung r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Rechnung r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Rechnung> getAlleRechnungen() {
		List<Rechnung> rechnungsListe = new ArrayList<Rechnung>();
		
		String selectRechnung = "SELECT * FROM TB_RECHNUNG";
		
			try {
				PreparedStatement selectRechnungStatement = c.prepareStatement(selectRechnung);

				
				ResultSet rs = selectRechnungStatement.executeQuery();
				
				while(rs.next()) {
					Rechnung rg = new Rechnung();
					rg.setId(rs.getLong("ID_RECHNUNG"));
					rg.setKontaktid(rs.getLong("TB_KONTAKT_ID"));
					rg.setKommentar(rs.getString("KOMMENTAR"));
					rg.setBezahltAm(rs.getDate("BEZAHLT"));
					rg.setDatum(rs.getDate("DATUM"));
					rg.setFaelligkeitsdatum(rs.getDate("FAELLIGKEIT"));
					rg.setNachricht(rs.getString("NACHRICHT"));
					rg.setRechnungsnummer(rs.getLong("RG_NUMMER"));
					
					rechnungsListe.add(rg);	
				}
				
				selectRechnungStatement.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		return rechnungsListe;
	}

}
