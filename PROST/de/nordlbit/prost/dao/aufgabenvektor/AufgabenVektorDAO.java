/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.aufgabenvektor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import de.nordlbit.prost.bd.Aufgabe;
import de.nordlbit.prost.bd.AufgabeHandler;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.DatenbankZugriff;

/**
 * @author tepperc
 *
 */
public class AufgabenVektorDAO 
{    
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor 
     * @param pvMandant 
     */
    public AufgabenVektorDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen eines Elements in den Aufgaben-Vektor
     * @param pvAnfang 
     * @param pvEnde 
     * @param pvVorgangID 
     * @param pvMandant 
     * @param pvAufgabe 
     */
    public void insertAufgabenVektor(String pvAnfang, String pvEnde, String pvVorgangID, String pvMandant,
                                     String pvAufgabe)
    {
        try
        {
          //System.out.println("insertAufgabenVektor: " + ivMandant);
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          //System.out.println("lvSchemaOwner: " + lvSchemaOwner);
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".aufgaben_vektor VALUES (?,?,?,?,?,?)");
          
          //System.out.println("MaxId: " + this.getMaxId());
          stmt.setInt(1, this.getMaxId() + 1); // Dummy-Wert - Trigger setzt die ID
          if (pvAnfang != null)
          {
            stmt.setTimestamp(2, Timestamp.valueOf(pvAnfang));
          }
          else
          {
              stmt.setTimestamp(2, null);
          }
          
          if (pvEnde != null)
          {
            stmt.setTimestamp(3, Timestamp.valueOf(pvEnde));
          }
          else
          {
              stmt.setTimestamp(3, null);
          }

          stmt.setString(4, pvVorgangID);
          stmt.setInt(5, (new Integer(pvMandant)).intValue());
          stmt.setInt(6, (new Integer(pvAufgabe)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("AufgabenVektorDAO - INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          System.out.println("insertAufgaben: " + pvVorgangID + " " + pvAnfang + " " + pvEnde + " " + pvMandant + " " + pvAufgabe);

          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element in den Aufgaben-Vektor nicht einfuegen.");
        }  
    }
    
    /**
     * 
     * @param pvAufgabenVektorID
     * @param pvAnfang
     * @param pvEnde
     * @param pvVorgangID
     * @param pvMandant
     * @param pvAufgabe
     */
    public void updateAufgabenVektor(String pvAufgabenVektorID, Timestamp pvAnfang, Timestamp pvEnde, String pvVorgangID, String pvMandant,
                                     String pvAufgabe)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId()); 
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".aufgaben_vektor SET anfang = ?, ende = ?, vorgang_id = ?, mandant = ?, aufgabe = ? WHERE aufgaben_vektor_id = ?");
          
          if (pvAnfang != null)
          {
            stmt.setTimestamp(1, pvAnfang);
          }
          else
          {
              stmt.setTimestamp(1, null);
          }
          
          if (pvEnde != null)
          {
            stmt.setTimestamp(2, pvEnde);
          }
          else
          {
              stmt.setTimestamp(2, null);
          }

          stmt.setString(3, pvVorgangID);
          stmt.setInt(4, (new Integer(pvMandant)).intValue());
          stmt.setInt(5, (new Integer(pvAufgabe)).intValue());
          stmt.setInt(6, (new Integer(pvAufgabenVektorID)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("AufgabenVektorDAO - UPDATE-Statement: " + lvResult + " Datensaetze geaendert.");

          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element in dem Aufgaben-Vektor nicht aendern.");
        }  
    }
    
    /**
     * Einfuegen eines Elements in den Aufgaben-Vektor und Rueckgabe der ID
     * @param pvAnfang 
     * @param pvEnde 
     * @param pvVorgangID 
     * @param pvMandant 
     * @param pvAufgabe 
     * @return 
     */
    public String insertAufgabenVektorReturnID(String pvAnfang, String pvEnde, String pvVorgangID, String pvMandant,
                                         String pvAufgabe)
    {
        String lvAufgabenVektorID = null;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".aufgaben_vektor VALUES (?,?,?,?,?,?)");
          
          //System.out.println("MaxId: " + this.getMaxId());
          stmt.setInt(1, this.getMaxId() + 1); // Dummy-Wert - Trigger setzt die ID
          if (pvAnfang != null)
          {
            stmt.setTimestamp(2, Timestamp.valueOf(pvAnfang));
          }
          else
          {
              stmt.setTimestamp(2, null);
          }
          
          if (pvEnde != null)
          {
            stmt.setTimestamp(3, Timestamp.valueOf(pvEnde));
          }
          else
          {
              stmt.setTimestamp(3, null);
          }

          stmt.setString(4, pvVorgangID);
          stmt.setInt(5, (new Integer(pvMandant)).intValue());
          stmt.setInt(6, (new Integer(pvAufgabe)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("AufgabenVektorDAO - INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          System.out.println("insertAufgabenVektorReturnID: " + pvVorgangID + " " + pvAnfang + " " + pvEnde + " " + pvMandant + " " + pvAufgabe);
          stmt.close();
          
          Statement stmtSelect = lvCon.createStatement();
       
          ResultSet rs = stmtSelect.executeQuery("SELECT aufgaben_vektor_id FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE vorgang_id = '" + pvVorgangID + "' AND mandant = " + pvMandant + " AND aufgabe = " + pvAufgabe);
                      
          if (rs.next()) 
          {
              lvAufgabenVektorID = (new Integer(rs.getInt(1))).toString();
          }
          rs.close();
          stmtSelect.close();     
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element in den Aufgaben-Vektor nicht einfuegen.");
        }
        
        return lvAufgabenVektorID;  
    }

    
    /**
     * Setzen des Endedatums
     * @param pvAufgabenVektorID 
     * @param pvEnde 
     */
    public void setEndeDatum(String pvAufgabenVektorID, String pvEnde)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".aufgaben_vektor SET ende = ? WHERE aufgaben_vektor_id = ?");
          if (pvEnde != null)
          {
            stmt.setTimestamp(1, Timestamp.valueOf(pvEnde));
          }
          else
          {
              stmt.setTimestamp(1, null);
          }

          stmt.setInt(2, (new Integer(pvAufgabenVektorID)).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("AufgabenVektorDAO - UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Endedatum fuer AufgabenVektor-ID = " + pvAufgabenVektorID + " nicht setzen.");
        }  
    }

    /**
     * Prueft auf EndeDatum == NULL
     * @param pvInstitut
     * @param pvAufgabenVektorID 
     * @return 
     */
    public boolean isEndeDatumNULL(String pvInstitut, String pvAufgabenVektorID)
    {
        boolean lvDatumNULL = true;
        try
        {
          DatenbankZugriff.openConnection(pvInstitut);  
          Connection lvCon = DatenbankZugriff.getConnection();
          //System.out.println("CTCTCT: " + ivMandant.getName());
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT ende FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE aufgaben_vektor_id =  '" + pvAufgabenVektorID + "' and ende is not null");
          
          if (rs.next()) 
          {
              lvDatumNULL = false;
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Endedatum fuer AufgabenVektor-ID = " + pvAufgabenVektorID + " nicht finden.");
        }  
        
        return lvDatumNULL;
    }

    /**
     * Liefert eine Liste mit leerem Endedatum fuer einen Vorgang
     * @param pvInstitut
     * @param pvVorgangID 
     * @return 
     */
    public ArrayList<String> getAllEndeDatumNULL(String pvInstitut, String pvVorgangID)
    {
        ArrayList<String> lvHelpListe = new ArrayList<String>();
        try
        {
          DatenbankZugriff.openConnection(pvInstitut);  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgaben_vektor_id FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE vorgang_id =  '" + pvVorgangID + "' and ende is null");
          
          while (rs.next()) 
          {
              lvHelpListe.add("" + rs.getInt(1));
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte die Liste der AufgabenVektor-IDs mit leerem Endedatum fuer Vorgang = " + pvVorgangID + " nicht ermitteln.");
        }  
        
        return lvHelpListe;
    }

    
    /**
     * 
     * @param pvAufgabenVektorID
     * @param pvAufgabeID
     */
    public void deleteAufgabeInAufgabenVektor(String pvAufgabenVektorID, String pvAufgabeID){
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE aufgaben_vektor_id = " + pvAufgabenVektorID + " AND aufgabe = " + pvAufgabeID);
          System.out.println("AufgabenVektorDAO - DELETE-Statement: " + lvResult + " Datensaetze geloescht.");

          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element mit ID = " + pvAufgabenVektorID + " Aufgabe: " + pvAufgabeID + " nicht loeschen.");
        }  
    }
    
    /**
     * Loeschen eines Elements im Aufgaben-Vektor
     * @param pvAufgabenVektorID 
     */
    public void deleteAufgabenVektor(String pvAufgabenVektorID)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE aufgaben_vektor_id = " + pvAufgabenVektorID);
          System.out.println("AufgabenVektorDAO - DELETE-Statement: " + lvResult + " Datensaetze geloescht.");

          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Element mit ID = " + pvAufgabenVektorID + " nicht loeschen.");
        }
    }
    
    /**
     * Finden der AufgabenVektor-ID anhand von Vorgang-ID, Mandanten-ID und Aufgabe-ID
     * @param pvVorgangID 
     * @param pvMandantenID 
     * @param pvAufgabeID 
     * @return 
     */
    public String findAufgabenVektorID(String pvVorgangID, String pvMandantenID, String pvAufgabeID)
    {
        String lvAufgabenVektorID = null;
        
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgaben_vektor_id FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE vorgang_id = '" + pvVorgangID + "' AND mandant = " + pvMandantenID + " AND aufgabe = " + pvAufgabeID);
                    
          if (rs.next()) 
          {
              lvAufgabenVektorID = (new Integer(rs.getInt(1))).toString();
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine AufgabenVektor-ID mit Vorgang-ID = " + pvVorgangID + ", Mandanten-ID = " + pvMandantenID + " und Aufgabe-ID = " + pvAufgabeID + " finden.");
        }

        return lvAufgabenVektorID;
    }

    /**
     * Finden aller Elemente aus dem Aufgaben-Vektor anhand der Vorgang-ID
     * @param pvVorgang
     * @return 
     */
    public HashMap<String, VgAufgabe> findAufgabenByVorgangID(Vorgang pvVorgang)
    {
        HashMap <String, VgAufgabe> lvAufgabenHashMap = new HashMap<String, VgAufgabe>();
        
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgaben_vektor_id, anfang, ende, mandant, aufgabe FROM " + lvSchemaOwner + ".aufgaben_vektor WHERE vorgang_id = '" + pvVorgang.getId() + "'");
          
          Aufgabe lvAufgabe;
          VgAufgabe lvVgAufgabe;
          
          while (rs.next()) 
          {
               Mandant lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(4))).toString());
              
              lvAufgabe = AufgabeHandler.getInstance(lvMandant).getDateisuche().get((new Integer(rs.getInt(5))).toString());
              if (lvAufgabe == null)
              {
                 lvAufgabe = AufgabeHandler.getInstance(lvMandant).getProzessStarter().get((new Integer(rs.getInt(5))).toString()); 
              }
              if (lvAufgabe == null)
              {
                  lvAufgabe = AufgabeHandler.getInstance(lvMandant).getTrigger().get((new Integer(rs.getInt(5))).toString());
              }
              
              lvVgAufgabe = new VgAufgabe(pvVorgang, lvAufgabe, (new Integer(rs.getInt(1))).toString());
              if (rs.getTimestamp(2)!= null)
              {
                lvVgAufgabe.setInArbeit(true);
                lvVgAufgabe.setAnfangZeitpunkt(rs.getTimestamp(2));
                if (rs.getTimestamp(3) != null)
                {
                    lvVgAufgabe.setErledigt(true);
                    lvVgAufgabe.setEndeZeitpunkt(rs.getTimestamp(3));
                }
                else
                {
                    lvVgAufgabe.setErledigt(false);
                }
              }
              else
              {
                lvVgAufgabe.setInArbeit(false);
                lvVgAufgabe.setErledigt(false);
              }
              
              lvVgAufgabe.setMandant(Mandant.getMandanten().get(rs.getString(4)));
              lvAufgabenHashMap.put(rs.getString(1), lvVgAufgabe); 
              
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
        	exp.printStackTrace();
            System.out.println("Konnte keine Aufgaben zum VorgangID = " + pvVorgang.getId() + " finden.");
        }

        return lvAufgabenHashMap;
    }   
        
    /**
     * Liefert die maximale ID
     * @return
     */
    private int getMaxId()
    {
    	int lvMaxId = 0;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT MAX(aufgaben_vektor_id) FROM " + lvSchemaOwner + ".aufgaben_vektor");
          
          if (rs.next()) 
          {
              lvMaxId = rs.getInt(1);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("AufgabenVektorDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

}
