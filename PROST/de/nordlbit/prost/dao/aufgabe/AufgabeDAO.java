/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.aufgabe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;

import de.nordlbit.prost.bd.Aufgabe;
import de.nordlbit.prost.bd.Dateisuche;
import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.EreignisHandler;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Protokoll;
import de.nordlbit.prost.bd.ProzessStarter;
import de.nordlbit.prost.bd.Trigger;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;

/**
 * @author tepperc
 *
 */
public class AufgabeDAO 
{    
    /**
     * Aktuelle Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public AufgabeDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen einer Aufgabe
     * @param pvAufgabe 
     * @param pvTyp 
     * @param pvDateisuche 
     * @param pvProzessStarter 
     * @param pvTrigger 
     */
    public void insertAufgabe(Aufgabe pvAufgabe,
                              String pvTyp, boolean pvDateisuche, boolean pvProzessStarter, boolean pvTrigger)
    { 
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".aufgabe (aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe, fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis, dateisuche, prozessstarter, dateiname, "
                  + "dateipfad, ursache_ereignis, ursache_ereignis_vorhanden, datumsqualifier, timer, startzeitpunkt) "
                  + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
          stmt.setInt(1, this.getMaxId() + 1);
          stmt.setInt(2, (new Integer(pvTyp)).intValue());
          stmt.setInt(3, (new Integer(pvAufgabe.getMandant().getId())).intValue());
          stmt.setString(4, pvAufgabe.getBezeichnung());
          stmt.setInt(5, (new Integer(pvAufgabe.isUnbedingteAufgabe() ? "1" : "0")).intValue());
          stmt.setInt(6, (new Integer(pvAufgabe.getFertigstellungsEreignis().getId())).intValue());
          stmt.setInt(7, (new Integer(pvAufgabe.getSortNr().toString())).intValue());
          if (pvAufgabe.getZeitfensterVon() != null && !pvAufgabe.getZeitfensterVon().isEmpty())
          {
            stmt.setTimestamp(8, Timestamp.valueOf(pvAufgabe.getZeitfensterVon()));
          }
          else
          {
              stmt.setTimestamp(8, null);
          }
          if (pvAufgabe.getZeitfensterBis() != null && !pvAufgabe.getZeitfensterBis().isEmpty())
          {
            stmt.setTimestamp(9, Timestamp.valueOf(pvAufgabe.getZeitfensterBis()));
          }
          else
          {
              stmt.setTimestamp(9, null);
          }
          stmt.setInt(10, (new Integer(pvDateisuche ? "1" : "0")).intValue());
          stmt.setInt(11, (new Integer(pvProzessStarter ? "1" : "0")).intValue());
          if(pvDateisuche){
            stmt.setString(12, ((Dateisuche)pvAufgabe).getDateiname());
            stmt.setString(13, ((Dateisuche)pvAufgabe).getDateiPfad());
          } else {
            stmt.setString(12, ((ProzessStarter)pvAufgabe).getProzessname());
            stmt.setString(13, ((ProzessStarter)pvAufgabe).getPfad());
          }
          stmt.setString(14, null);
          stmt.setInt(15, 0);
          if(pvDateisuche){
            stmt.setString(16, ((Dateisuche)pvAufgabe).getQualifier());
          } else {
            stmt.setString(16, "");              
          }
          stmt.setInt(17, (new Integer(pvTrigger ? "1" : "0")).intValue()); // Timer
          if(pvTrigger){
            stmt.setString(18, ((Trigger)pvAufgabe).getAusloesezeitpunkt());
          } else {
            stmt.setString(18, null);
          }
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");

          stmt.close();
          
          
          // Protokolleintrag
          String lvClassName = "";
          String lvProtokollEintrag = "";
          if(pvDateisuche){
              lvClassName = "Dateisuche";
              Dateisuche lvAufgabe = (Dateisuche) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.INSERT);
          } else if(pvProzessStarter){
              lvClassName = "ProzessStarter";
              ProzessStarter lvAufgabe = (ProzessStarter) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.INSERT);
          } else if(pvTrigger){
              lvClassName = "Trigger";
              Trigger lvAufgabe = (Trigger) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.INSERT);
          }
          
          
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.INSERT, pvAufgabe.getId(), lvClassName, lvProtokollEintrag, pvAufgabe.getMandant().getId());
          DatenbankZugriff.closeConnection();
          
          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Aufgabe nicht einfuegen.");
        }  
    }
    
    /**
     * Aendern der Daten einer Aufgabe
     * @param pvAufgabe 
     * @param pvTyp 
     * @param pvDateisuche 
     * @param pvProzessStarter 
     * @param pvTrigger 
     */
    public void updateAufgabe(Aufgabe pvAufgabe, String pvTyp, boolean pvDateisuche, boolean pvProzessStarter, boolean pvTrigger)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".aufgabe SET typ = ?, mandant = ?, bezeichnung = ?, unbedingte_aufgabe = ?," +
                  " fertig_ereignis = ?, sort_nr = ?, zeitfenster_von = ?, zeitfenster_bis = ?," +
                  " dateisuche = ?, prozessstarter = ?, timer = ?, dateiname = ?, dateipfad = ?, ursache_ereignis = ?," +
                  " ursache_ereignis_vorhanden = ?, datumsqualifier = ?, startzeitpunkt = ? WHERE aufgabe_id = ?");
          
          stmt.setInt(1, (new Integer(pvTyp)).intValue());
          stmt.setInt(2, (new Integer(pvAufgabe.getMandant().getId())).intValue());
          stmt.setString(3, pvAufgabe.getBezeichnung());
          stmt.setInt(4, (new Integer(pvAufgabe.isUnbedingteAufgabe() ? "1" : "0")).intValue());
          stmt.setInt(5, (new Integer(pvAufgabe.getFertigstellungsEreignis().getId())).intValue());
          stmt.setInt(6, (new Integer(pvAufgabe.getSortNr().toString())).intValue());
          if (pvAufgabe.getZeitfensterVon() != null && !pvAufgabe.getZeitfensterVon().isEmpty())
          {
              stmt.setTimestamp(7, Timestamp.valueOf(pvAufgabe.getZeitfensterVon()));            
          }
          else
          {
              stmt.setTimestamp(7, null);
          }
          if (pvAufgabe.getZeitfensterBis() != null && !pvAufgabe.getZeitfensterBis().isEmpty())
          {
            stmt.setTimestamp(8, Timestamp.valueOf(pvAufgabe.getZeitfensterBis()));
          }
          else
          {
              stmt.setTimestamp(8, null);
          }
          stmt.setInt(9, (new Integer(pvDateisuche ? "1" : "0")).intValue());
          stmt.setInt(10, (new Integer(pvProzessStarter ? "1" : "0")).intValue());
          stmt.setInt(11, (new Integer(pvTrigger ? "1" : "0")).intValue());
          if(pvDateisuche){
            stmt.setString(12, ((Dateisuche)pvAufgabe).getDateiname());
            stmt.setString(13, ((Dateisuche)pvAufgabe).getDateiPfad());
          } else {
            stmt.setString(12, "");
            stmt.setString(13, "");
          }
          stmt.setString(14, null);
          stmt.setInt(15, 0);
          if(pvDateisuche){
            stmt.setString(16, ((Dateisuche)pvAufgabe).getQualifier());
          } else {
            stmt.setString(16, "");              
          }
          if(pvTrigger){
            stmt.setString(17, ((Trigger)pvAufgabe).getAusloesezeitpunkt());
          } else {
            stmt.setString(17, null);
          }
          stmt.setInt(21, (new Integer(pvAufgabe.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          
          // Protokolleintrag
          String lvClassName = "";
          String lvProtokollEintrag = "";
          if(pvDateisuche){
              lvClassName = "Dateisuche";
              Dateisuche lvAufgabe = (Dateisuche) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE);
          } else if(pvProzessStarter){
              lvClassName = "ProzessStarter";
              ProzessStarter lvAufgabe = (ProzessStarter) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE);
         } else if(pvTrigger){
              lvClassName = "Trigger";
              Trigger lvAufgabe = (Trigger) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE);
          }
          System.out.println("Fertig2...");
          
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.UPDATE, pvAufgabe.getId(), lvClassName, lvProtokollEintrag, pvAufgabe.getMandant().getId());
          DatenbankZugriff.closeConnection();

        }
        catch (Exception exp)
        {
            System.out.println("Konnte Aufgabe mit ID = " + pvAufgabe.getId() + " nicht veraendern.");
        }  
    }
    
    /**
     * Loeschen einer Aufgabe
     * @param pvAufgabe 
     */
    public void deleteAufgabe(Aufgabe pvAufgabe)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".aufgabe WHERE aufgabe_id = " + pvAufgabe.getId());
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          
          
          // Protokolleintrag
          String lvClassName = "";
          String lvProtokollEintrag = "";
          if(pvAufgabe instanceof Dateisuche){
              lvClassName = "Dateisuche";
              Dateisuche lvAufgabe = (Dateisuche) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.DELETE);
          } else if(pvAufgabe instanceof ProzessStarter){
              lvClassName = "ProzessStarter";
              ProzessStarter lvAufgabe = (ProzessStarter) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.DELETE);
          } else if(pvAufgabe instanceof Trigger){
              lvClassName = "Trigger";
              Trigger lvAufgabe = (Trigger) pvAufgabe;
              lvProtokollEintrag = lvAufgabe.getProtokollEintrag(Protokoll.STRING, Protokoll.DELETE);
          }
          
          
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.DELETE, pvAufgabe.getId(), lvClassName, lvProtokollEintrag, pvAufgabe.getMandant().getId());
          DatenbankZugriff.closeConnection();
          
          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Aufgabe mit ID = " + pvAufgabe.getId() + " nicht loeschen.");
        }
    }
    
    /**
     * Finden einer Dateisuche anhand der ID
     * @param pvAufgabeID 
     * @return 
     */
    public Aufgabe findDateisucheByID(String pvAufgabeID)
    {
        Aufgabe lvAufgabe = null;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, datumsqualifier, exklusiv FROM " + lvSchemaOwner + ".aufgabe where dateisuche = 1");
          		                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              if (pvAufgabeID.equals((new Integer(rs.getInt(1))).toString()))
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
                  //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  if (rs.getInt(5) == 1)
                  {
                      lvUnbedingteAufgabe = true;
                  }
                  else
                  {
                      lvUnbedingteAufgabe = false;
                  }

                  if (rs.getInt(17) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }

                  // Dateisuche
                  lvAufgabe = new Dateisuche((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, lvUnbedingteAufgabe, rs.getString(16), lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Dateisuche mit ID " + pvAufgabeID + " nicht finden.");
        }

        return lvAufgabe;
    }
    
    /**
     * Finden aller unbedingten Dateisuchen anhand der Mandanten-ID 
     * @param pvUnbedingt 
     * @param pvMandantenID 
     * @return 
     */
    public HashMap<String, Aufgabe> findUnbedingteDateisuchenByMandant(boolean pvUnbedingt, String pvMandantenID)
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, datumsqualifier, exklusiv FROM " + lvSchemaOwner + ".aufgabe WHERE dateisuche = 1 AND mandant = " + pvMandantenID);
                                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          Aufgabe lvAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              if (pvUnbedingt)
              {
                  if ("1".equals((new Integer(rs.getInt(5))).toString()))
                  {
                	  System.out.println("Dateisuchen: " + (new Integer(rs.getInt(3))).toString());

                      lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                      System.out.println("Jajaja: " + lvMandant);
                      lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                      //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
                      //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                      if (rs.getInt(17) == 1)
                      {
                          lvExklusiv = true;
                      }
                      else
                      {
                          lvExklusiv = false;
                      }
                      // Dateisuche
                      lvAufgabe = new Dateisuche((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, pvUnbedingt, rs.getString(16), lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
                      lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);
                  }
              }
              else
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
                  //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  if (rs.getInt(17) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }
                  // Dateisuche
                  lvAufgabe = new Dateisuche((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, pvUnbedingt, rs.getString(16), lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
                  lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);                  
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
        	exp.printStackTrace();
            System.out.println("Konnte keine Dateisuchen mit Mandanten-ID " + pvMandantenID + " finden.");
        }

        return lvAufgabenHashMap;
    }

    /**
     * Finden aller unbedingten Prozessstarter anhand der Mandanten-ID 
     * @param pvUnbedingt 
     * @param pvMandantenID 
     * @return 
     */
    public HashMap<String, Aufgabe> findUnbedingteProzessstarterByMandant(boolean pvUnbedingt, String pvMandantenID)
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, exklusiv FROM " + lvSchemaOwner + ".aufgabe WHERE prozessstarter = 1 AND mandant = " + pvMandantenID);
                                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          Aufgabe lvAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
        
              if (pvUnbedingt)
              {
                  if ("1".equals((new Integer(rs.getInt(5))).toString()))
                  {
                	  System.out.println("Prozessstarter: " + (new Integer(rs.getInt(3))).toString());

                      lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                      System.out.println("Jajaja: " + lvMandant);
                      lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                      //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
                      //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                      if (rs.getInt(16) == 1)
                      {
                          lvExklusiv = true;
                      }
                      else
                      {
                          lvExklusiv = false;
                      }
                      // Prozessstarter
                      // TODO Zeitfenster
                      lvAufgabe = new ProzessStarter((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, pvUnbedingt, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
                      lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);
                  }
              }
              else
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString()); 
                  //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  if (rs.getInt(16) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }

                  // Prozessstarter
                  // TODO Zeitfenster
                  lvAufgabe = new ProzessStarter((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, pvUnbedingt, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
                  lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);                 
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
        	exp.printStackTrace();
            System.out.println("Konnte keine Prozessstarter mit Mandanten-ID " + pvMandantenID + " finden.");
        }

        return lvAufgabenHashMap;
    }

    /**
     * Finden eines Prozessstarters anhand der ID
     * @param pvAufgabeID 
     * @return 
     */
    public Aufgabe findProzessstarterByID(String pvAufgabeID)
    {
        Aufgabe lvAufgabe = null;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, exklusiv FROM " + lvSchemaOwner + ".aufgabe where prozessstarter = 1");
                                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              if (pvAufgabeID.equals((new Integer(rs.getInt(1))).toString()))
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
                  //lvLoeschEreignis =  EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  if (rs.getInt(5) == 1)
                  {
                      lvUnbedingteAufgabe = true;
                  }
                  else
                  {
                      lvUnbedingteAufgabe = false;
                  }
                  if (rs.getInt(16) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }
              
                  // Prozessstarter
                    // TODO Zeitfenster
                  lvAufgabe = new ProzessStarter((new Integer(rs.getInt(1))).toString(), rs.getString(4), null, null, rs.getString(12), rs.getString(13), lvUnbedingteAufgabe, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv); 
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Prozessstarter mit ID " + pvAufgabeID + " nicht finden.");
        }

        return lvAufgabe;
    }
    
    /**
     * Finden aller unbedingten Prozessstarter anhand der Mandanten-ID 
     * @param pvUnbedingt 
     * @param pvMandantenID 
     * @return 
     */
    public HashMap<String, Aufgabe> findUnbedingteTriggerByMandant(boolean pvUnbedingt, String pvMandantenID)
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, dateiname, timer, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, startzeitpunkt, exklusiv, stop_aufgabe FROM " + lvSchemaOwner + ".aufgabe WHERE timer = 1 AND mandant = " + pvMandantenID);
                                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          Aufgabe lvAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              if (pvUnbedingt)
              {
                  if ("1".equals((new Integer(rs.getInt(5))).toString()))
                  {
                	  System.out.println("Trigger: " + (new Integer(rs.getInt(3))).toString());
                      lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                      //System.out.println("Jajaja: " + lvMandant);

                      lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                      //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                      //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(19))).toString());
                      if (rs.getInt(18) == 1)
                      {
                          lvExklusiv = true;
                      }
                      else
                      {
                          lvExklusiv = false;
                      }

                      // Trigger
                      // TODO Zeitfenster
                      lvAufgabe = new Trigger((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(17), pvUnbedingt, lvEreignis,  lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv, (new Integer(rs.getInt(19))).toString());
                      lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);
                  }
              }
              else
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(19))).toString());          
                  if (rs.getInt(18) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }
                  // Trigger
                  // TODO Zeitfenster
                  lvAufgabe = new Trigger((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(17), pvUnbedingt, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv, (new Integer(rs.getInt(19))).toString());
                  lvAufgabenHashMap.put(lvAufgabe.getId(), lvAufgabe);                 
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
        	exp.printStackTrace();
            System.out.println("Konnte keine Trigger mit Mandanten-ID " + pvMandantenID + " finden.");
        }

        return lvAufgabenHashMap;
    }

    /**
     * Finden eines Triggers anhand der ID
     * @param pvAufgabeID 
     * @return 
     */
    public Aufgabe findTriggerByID(String pvAufgabeID)
    {
        Aufgabe lvAufgabe = null;
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                           " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                           " dateisuche, prozessstarter, timer, dateiname, dateipfad, ursache_ereignis," +
                                           " ursache_ereignis_vorhanden, startzeitpunkt, exklusiv FROM " + lvSchemaOwner + ".aufgabe where prozessstarter = 1");
                                
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              if (pvAufgabeID.equals((new Integer(rs.getInt(1))).toString()))
              {
                  lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
                  //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
                  //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(19))).toString());
                  if (rs.getInt(5) == 1)
                  {
                      lvUnbedingteAufgabe = true;
                  }
                  else
                  {
                      lvUnbedingteAufgabe = false;
                  }
                  if (rs.getInt(18) == 1)
                  {
                      lvExklusiv = true;
                  }
                  else
                  {
                      lvExklusiv = false;
                  }
              
                  // Trigger
                    // TODO Zeitfenster
                  lvAufgabe = new Trigger((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(17), lvUnbedingteAufgabe, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv, (new Integer(rs.getInt(19))).toString());
              }
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Trigger mit ID " + pvAufgabeID + " nicht finden.");
        }

        return lvAufgabe;
    }

    
    
    /**
     * Liefert eine HashMap aller Dateisuchen
     * @return 
     */
    public HashMap<String, Aufgabe> getDateisuchen()
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                                   " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                                   " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                                   " ursache_ereignis_vorhanden, datumsqualifier, exklusiv FROM " + lvSchemaOwner + ".aufgabe where dateisuche = 1 AND mandant = " + ivMandant.getId());
          Aufgabe lvAufgabe;
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          //Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              //lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
              
              lvEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
              //lvAktivEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
              //lvLoeschEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());

              if (rs.getInt(5) == 1)
              {
                  lvUnbedingteAufgabe = true;
              }
              else
              {
                  lvUnbedingteAufgabe = false;
              }
              if (rs.getInt(17) == 1)
              {
                  lvExklusiv = true;
              }
              else
              {
                  lvExklusiv = false;
              }
              
              // Dateisuche
              lvAufgabe = new Dateisuche((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, lvUnbedingteAufgabe, rs.getString(16), lvEreignis, lvAktivEreignis, lvLoeschEreignis, ivMandant, new Integer(rs.getInt(7)), lvExklusiv); 
              lvAufgabenHashMap.put((new Integer(rs.getInt(1))).toString(), lvAufgabe);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Dateisuchen aus der Datenbank lesen.");
        }

        return lvAufgabenHashMap;
    }
    
    /**
     * Liefert eine HashMap aller Prozessstarter
     * @return 
     */
    public HashMap<String, Aufgabe> getProzessstarter()
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        try
        {
          //System.out.println("Prozessstarter: " + ivMandant);
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
         
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                                   " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                                   " dateisuche, prozessstarter, dateiname, dateipfad, ursache_ereignis," +
                                                   " ursache_ereignis_vorhanden, exklusiv FROM " + lvSchemaOwner + ".aufgabe where prozessstarter = 1 AND mandant = " + ivMandant.getId());
          Aufgabe lvAufgabe;
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          //Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              //lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
              lvEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
              //lvAktivEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(16))).toString());
              //lvLoeschEreignis = EreignisHandler.getInstance(ivMandant).getEreignisse().get((new Integer(rs.getInt(17))).toString());
              if (rs.getInt(5) == 1)
              {
                  lvUnbedingteAufgabe = true;
              }
              else
              {
                  lvUnbedingteAufgabe = false;
              }
              if (rs.getInt(16) == 1)
              {
                  lvExklusiv = true;
              }
              else
              {
                  lvExklusiv = false;
              }

              // Prozessstarter
              // TODO Zeitfenster
              lvAufgabe = new ProzessStarter((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(12), rs.getString(13), null, null, lvUnbedingteAufgabe, lvEreignis, lvAktivEreignis, lvLoeschEreignis, ivMandant, new Integer(rs.getInt(7)), lvExklusiv);
              lvAufgabenHashMap.put((new Integer(rs.getInt(1))).toString(), lvAufgabe);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
        	exp.printStackTrace();
            System.out.println("Konnte keine Prozessstarter aus der Datenbank lesen.");
        }

        return lvAufgabenHashMap;
    }

    /**
     * Liefert eine HashMap aller Trigger
     * @return 
     */
    public HashMap<String, Aufgabe> getTrigger()
    {
        HashMap<String, Aufgabe> lvAufgabenHashMap = new HashMap<String, Aufgabe>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT aufgabe_id, typ, mandant, bezeichnung, unbedingte_aufgabe," +
                                                   " fertig_ereignis, sort_nr, zeitfenster_von, zeitfenster_bis," +
                                                   " dateisuche, prozessstarter, timer, dateiname, dateipfad, ursache_ereignis," +
                                                   " ursache_ereignis_vorhanden, startzeitpunkt, exklusiv, stop_aufgabe FROM " + lvSchemaOwner + ".aufgabe where timer = 1 AND mandant = " + ivMandant.getId());
          Aufgabe lvAufgabe;
          Ereignis lvEreignis;
          Ereignis lvAktivEreignis = null;
          Ereignis lvLoeschEreignis = null;
          Mandant lvMandant; 
          boolean lvUnbedingteAufgabe;
          boolean lvExklusiv;
          
          while (rs.next()) 
          {
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(3))).toString());
              lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(6))).toString());
              //lvAktivEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(18))).toString());
              //lvLoeschEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rs.getInt(19))).toString());
              if (rs.getInt(5) == 1)
              {
                  lvUnbedingteAufgabe = true;
              }
              else
              {
                  lvUnbedingteAufgabe = false;
              }
              if (rs.getInt(18) == 1)
              {
                  lvExklusiv = true;
              }
              else
              {
                  lvExklusiv = false;
              }

              // Trigger
              // TODO Zeitfenster
              lvAufgabe = new Trigger((new Integer(rs.getInt(1))).toString(), rs.getString(4), rs.getString(17), lvUnbedingteAufgabe, lvEreignis, lvAktivEreignis, lvLoeschEreignis, lvMandant, new Integer(rs.getInt(7)), lvExklusiv, (new Integer(rs.getInt(19))).toString());
              lvAufgabenHashMap.put((new Integer(rs.getInt(1))).toString(), lvAufgabe);
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Trigger aus der Datenbank lesen.");
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

          ResultSet rs = stmt.executeQuery("SELECT MAX(aufgabe_id) FROM " + lvSchemaOwner + ".aufgabe");
          
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
            System.out.println("AufgabeDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }

   /**
    * Ist die Aufgabe exklusiv? 
    * @param pvAufgabeID
    * @return
    */
    public boolean isExklusiv(String pvAufgabeID)
    {
    	boolean lvExklusiv = false;
    	
        try
        {
        	DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
        	Connection lvCon = DatenbankZugriff.getConnection();
        	String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

        	Statement stmt = lvCon.createStatement();	

        	ResultSet rs = stmt.executeQuery("SELECT EXKLUSIV FROM " + lvSchemaOwner + ".aufgabe where aufgabe_id = " + pvAufgabeID);
        
        	if (rs.next()) 
        	{
        		if (rs.getInt(1) == 1)
        		{
        			lvExklusiv = true;
        		}
        	}
        	rs.close();
        	stmt.close();
        	DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
        	System.out.println("AufgabeDAO: Konnte Exklusiv-Kennzeichen nicht ermitteln.");
        }  
        
        return lvExklusiv;
    }

}
