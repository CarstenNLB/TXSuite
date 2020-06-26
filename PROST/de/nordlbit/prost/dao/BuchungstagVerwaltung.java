/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Protokoll;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;


/**
 * @author tepperc
 *
 */
public class BuchungstagVerwaltung 
{   
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public BuchungstagVerwaltung(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
     }
    
    /**
     * Liefert das naechste Buchungsdatum
     * @param pvBuchungsdatum Format: JJMMTT
     * @return 
     */
    public String naechsteBuchungsdatum(String pvBuchungsdatum)
    {
        String lvResultTag = new String();
        boolean lvGefunden = false;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          System.out.println("Mandant: " + ivMandant.getName());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          System.out.println("SchemaOwner: " + lvSchemaOwner);
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage ORDER BY tag ASC");
          while (!lvGefunden && rs.next()) 
          {
            System.out.println(rs.getString(1) + " == " + pvBuchungsdatum);  
            if (rs.getString(1).equals(pvBuchungsdatum))
            {
                rs.next();
                lvResultTag = rs.getString(1);
                lvGefunden = true;
            }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte naechsten Buchungstag nicht bestimmen.");
            exp.printStackTrace();
        }
        return lvResultTag;
    }
    
    /**
     * Loescht den Buchungstag aus der Datenbank
     * @param pvBuchungstag Format: JJMMTT
     * @param pvMandant 
     */
    public void deleteBuchungstag(String pvBuchungstag, Mandant pvMandant)
    {
        try
        {
          DatenbankZugriff.openConnection(pvMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".buchungstage WHERE tag = " + pvBuchungstag);
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(pvMandant);
          lvDAO.insertProtokollEintrag(Protokoll.DELETE, pvBuchungstag, "Buchungstag", "Buchungstag: " + pvBuchungstag, pvMandant.getId());

          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Buchungstag " + pvBuchungstag + " nicht loeschen.");
        }
    }
    
    /**
     * Einfuegen des Buchungstag in die Datenbank
     * @param pvBuchungstag Format: JJMMTT
     * @param pvMandant 
     */
    public void insertBuchungstag(String pvBuchungstag, Mandant pvMandant)
    {
        try
        {
          DatenbankZugriff.openConnection(pvMandant.getInstitutsId());  
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Connection lvCon = DatenbankZugriff.getConnection(); 
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".buchungstage VALUES (?,?,?,?)");
          stmt.setString(1, pvBuchungstag);
          stmt.setInt(2, 0);
          stmt.setInt(3, 0);
          stmt.setInt(4, 0);
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(pvMandant);
          lvDAO.insertProtokollEintrag(Protokoll.INSERT, pvBuchungstag, "Buchungstag", "Buchungstag: " + pvBuchungstag, pvMandant.getId());
          
          
          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Buchungstag " + pvBuchungstag + " nicht einfuegen.");
        }  
    }
    
    /**
     * Liefert eine Liste der Buchungstage eines Monats
     * @param pvMonat Format: MM
     * @return 
     */
    public ArrayList<String> getBuchungstage(String pvMonat)
    {
        ArrayList<String> lvResult = new ArrayList<String>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId()); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          Connection lvCon = DatenbankZugriff.getConnection(); 
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag like '%" + pvMonat + "%'");
          while (rs.next()) 
          {
              lvResult.add(rs.getString(1));
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Buchungstage nicht aus der Datenbank lesen.");
        }

        return lvResult;
    }
    
    /**
     * Prueft, ob es sich um einen Buchungstag handelt
     * @param pvBuchungstag 
     * @return 
     */
    public boolean isBuchungstag(String pvBuchungstag)
    {
        //System.out.println("isBuchungstag: " + pvBuchungstag);
        boolean lvVorhanden = false;
        try
        {
            DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
            Connection lvCon = DatenbankZugriff.getConnection();
            String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
           
            Statement stmt = lvCon.createStatement();
            
            ResultSet rsResultSet = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag = '" + pvBuchungstag + "'");
            lvVorhanden = rsResultSet.next();
            rsResultSet.close();
            stmt.close();
            DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Buchungstag nicht ueberpruefen.");
        }
        
        return lvVorhanden;
    }
    
    /**
     * Prueft, ob es sich um einen Buchungstag handelt
     * @param pvBuchungstag 
     * @param pvMandant 
     * @return 
     */
    public boolean isBuchungstag(String pvBuchungstag, Mandant pvMandant)
    {
        //System.out.println("isBuchungstag: " + pvBuchungstag);
        boolean lvVorhanden = false;
        try
        {
            DatenbankZugriff.openConnection(pvMandant.getInstitutsId());
            Connection lvCon = DatenbankZugriff.getConnection();
            String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
            
            Statement stmt = lvCon.createStatement();
            
            ResultSet rsResultSet = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag = '" + pvBuchungstag + "'");
            lvVorhanden = rsResultSet.next();
            rsResultSet.close();
            stmt.close();
            DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Buchungstag nicht ueberpruefen.");
        }
        
        return lvVorhanden;
    }

    /**
     * Liefert den Ultimo eines Monats
     * @param pvMonat Format: MM
     * @param pvMandant 
     * @param pvMonatsultimo 
     * @param pvQuartalsultimo 
     * @param pvJahresultimo 
     * @return 
     */
    public String getUltimo(String pvMonat, Mandant pvMandant, boolean pvMonatsultimo, boolean pvQuartalsultimo, boolean pvJahresultimo)
    {
        //System.out.println("pvMonat: " + pvMonat);
        String lvMonatsultimo = new String();
        try
        {
          DatenbankZugriff.openConnection(pvMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          Statement stmt = lvCon.createStatement();

          ResultSet rs = null;
          if (pvMonatsultimo)
          {
            rs = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag like '%" + pvMonat + "%' AND monatsultimo=1");
          }
          if (pvQuartalsultimo)
          {
              rs = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag like '%" + pvMonat + "%' AND quartalsultimo=1");              
          }
          if (pvJahresultimo)
          {
              rs = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag like '%" + pvMonat + "%' AND jahresultimo=1");
          }
          while (rs.next()) 
          {
              //if (rs.getInt(2) == 1)
              //{
                  lvMonatsultimo = rs.getString(1);
              //}
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ultimo nicht aus der Datenbank lesen.");
        }

        return lvMonatsultimo;
    }
    
    /**
     * Prueft, ob es sich um einen Ultimo handelt
     * @param pvBuchungstag 
     * @param pvMandant 
     * @param pvMonatsultimo 
     * @param pvQuartalsultimo 
     * @param pvJahresultimo 
     * @return 
     */
    public boolean isUltimo(String pvBuchungstag, Mandant pvMandant, boolean pvMonatsultimo, boolean pvQuartalsultimo, boolean pvJahresultimo)
    {
         
        return isUltimo(pvBuchungstag, pvMandant.getName(), pvMonatsultimo, pvQuartalsultimo, pvJahresultimo);
    }
    
        /**
     * Prueft, ob es sich um einen Ultimo handelt
     * @param pvBuchungstag 
     * @param pvInstitutsId 
     * @param pvMandant 
     * @param pvMonatsultimo 
     * @param pvQuartalsultimo 
     * @param pvJahresultimo 
     * @return 
     */
    public boolean isUltimo(String pvBuchungstag, String pvInstitutsId, boolean pvMonatsultimo, boolean pvQuartalsultimo, boolean pvJahresultimo)
    {
        //System.out.println("isBuchungstag: " + pvBuchungstag);
        boolean lvVorhanden = false;
        try
        {
            DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
            Connection lvCon = DatenbankZugriff.getConnection();
            String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
            
            Statement stmt = lvCon.createStatement();
            
            ResultSet rsResultSet = null;
            if (pvMonatsultimo)
            {
              rsResultSet = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag = '" + pvBuchungstag + "' AND monatsultimo=1");
            }
            if (pvQuartalsultimo)
            {
                rsResultSet = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag = '" + pvBuchungstag + "' AND quartalsultimo=1");
            }
            if (pvJahresultimo)
            {
                rsResultSet = stmt.executeQuery("SELECT tag FROM " + lvSchemaOwner + ".buchungstage WHERE tag = '" + pvBuchungstag + "' AND jahresultimo=1");
            }
            lvVorhanden = rsResultSet.next();
            rsResultSet.close();
            stmt.close();
            DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte den Ultimo nicht ueberpruefen.");
        }
        
        return lvVorhanden;
    }
    
    
    /**
     * Aendert die Ultimoeinstellungen eines Monats
     * @param pvUltimo
     * @param pvMandant 
     * @param pvMonatsultimo 
     * @param pvQuartalsultimo 
     * @param pvJahresultimo 
     * @return 
     */
    public void updateUltimo(String pvUltimo, Mandant pvMandant, boolean pvMonatsultimo, boolean pvQuartalsultimo, boolean pvJahresultimo)
    {
        try
        {
          DatenbankZugriff.openConnection(pvMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".buchungstage SET monatsultimo = ?, quartalsultimo = ?, jahresultimo = ? WHERE tag = ?");
          if (pvMonatsultimo)
          {
              stmt.setInt(1, 1);
          }
          else
          {
              stmt.setInt(1, 0);
          }
          if (pvQuartalsultimo)
          {
              stmt.setInt(2, 1);
          }
          else
          {
              stmt.setInt(2, 0);
          }
          if (pvJahresultimo)
          {
              stmt.setInt(3, 1);
          }
          else
          {
              stmt.setInt(3, 0);
          }
          stmt.setString(4, pvUltimo);
          int lvResult = stmt.executeUpdate();
          System.out.println("BuchungstagVerwaltung - UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ultimoeinstellungen fuer den " + pvUltimo + " nicht aendern.");
        }         
    }


    
}
