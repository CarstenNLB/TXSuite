/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.ausloesemuster;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import de.nordlbit.prost.bd.Aufgabe;
import de.nordlbit.prost.bd.AufgabeHandler;
import de.nordlbit.prost.bd.Ausloesemuster;
import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.EreignisHandler;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.Protokoll;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.aufgabe.AufgabeDAO;
import de.nordlbit.prost.dao.ereignis.EreignisDAO;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;

/**
 * @author tepperc
 *
 */
public class AusloesemusterDAO 
{
    /**
     * Mandant
     */
    private Mandant ivMandant;
    
    /**
     * Konstruktor
     * @param pvMandant 
     */
    public AusloesemusterDAO(Mandant pvMandant)
    {
        super();
        this.ivMandant = pvMandant;
    }
    
    /**
     * Einfuegen eines Ausloesemusters
     * @param pvMuster
     */
    public void insertAusloesemuster(Ausloesemuster pvMuster)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".ausloesemuster (ausloesemuster_id, mandant) VALUES (?, ?)");
          stmt.setInt(1, this.getMaxId() + 1);
          stmt.setInt(2, (new Integer(pvMuster.getMandant().getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.INSERT, pvMuster.getId(), "Ausloesemuster", pvMuster.getProtokollEintrag(Protokoll.STRING, Protokoll.INSERT), pvMuster.getMandant().getId());
          DatenbankZugriff.closeConnection();
          
          
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster nicht einfuegen.");
        }  
    }

    /**
     * Einfuegen eines Ausloesemuster-Aufgabe
     * @param pvMuster 
     * @param pvAufgabe 
     */
    public void insertAusloesemusterAufgabe(Ausloesemuster pvMuster, Aufgabe pvAufgabe)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".ausloesemuster_aufgabe VALUES (?, ?)");
          stmt.setInt(1, (new Integer(pvMuster.getId())).intValue());
          stmt.setInt(2, (new Integer(pvAufgabe.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Aufgabe nicht einfuegen.");
        }  
    }

    /**
     * Einfuegen eines Ausloesemuster-Ereignis
     * @param pvMuster 
     * @param pvEreignis 
     */
    public void insertAusloesemusterEreignis(Ausloesemuster pvMuster, Ereignis pvEreignis)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("INSERT INTO " + lvSchemaOwner + ".ausloesemuster_ereignis VALUES (?, ?)");
          stmt.setInt(1, (new Integer(pvMuster.getId())).intValue());
          stmt.setInt(2, (new Integer(pvEreignis.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("INSERT-Statement: " + lvResult + " Datensaetze eingefuegt.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Ereignis nicht einfuegen.");
        }  
    }
  
    /**
     * Aendern der Daten eines Ausloesemuster
     * @param pvMuster 
     */
    public void updateAusloesemuster(Ausloesemuster pvMuster)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".ausloesemuster SET mandant = ? WHERE ausloesemuster_id = ?");
          stmt.setInt(1, (new Integer(pvMuster.getMandant().getId())).intValue());
          stmt.setInt(2, (new Integer(pvMuster.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.UPDATE, pvMuster.getId(), "Ausloesemuster", pvMuster.getProtokollEintrag(Protokoll.STRING, Protokoll.UPDATE), pvMuster.getMandant().getId());

          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster mit ID = " + pvMuster.getId() + " nicht veraendern.");
        }  
    }

    /**
     * Aendern der Daten eines Ausloesemuster-Aufgabe
     * @param pvMuster 
     * @param pvAufgabe 
     */
    public void updateAusloesemusterAufgabe(Ausloesemuster pvMuster, Aufgabe pvAufgabe)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection(); 
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".ausloesemuster_aufgabe SET aufgabe_id = ? WHERE ausloesemuster_id = ?");
          stmt.setInt(1, (new Integer(pvAufgabe.getId())).intValue());
          stmt.setInt(2, (new Integer(pvMuster.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Aufgabe mit ID = " + pvMuster.getId() + " nicht veraendern.");
        } 
    }

    /**
     * Aendern der Daten eines Ausloesemuster-Ereignis
     * @param pvMuster 
     * @param pvEreignis 
     */
    public void updateAusloesemusterEreignis(Ausloesemuster pvMuster, Ereignis pvEreignis)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          
          PreparedStatement stmt = lvCon.prepareStatement("UPDATE " + lvSchemaOwner + ".ausloesemuster_ereignis SET ereignis_id = ? WHERE ausloesemuster_id = ?");
          stmt.setInt(1, (new Integer(pvEreignis.getId())).intValue());
          stmt.setInt(2, (new Integer(pvMuster.getId())).intValue());
          int lvResult = stmt.executeUpdate();
          System.out.println("UPDATE-Statement: " + lvResult + " Datensaetze veraendert.");
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Ereignis mit ID = " + pvMuster.getId() + " nicht veraendern.");
        }  
    }

    /**
     * Loeschen eines Ausloesemuster
     * @param pvMuster 
     */
    public void deleteAusloesemuster(Ausloesemuster pvMuster)
    {
        try
        {
          deleteAusloesemusterAufgabe(pvMuster);
          deleteAusloesemusterEreignis(pvMuster);
            
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ausloesemuster WHERE ausloesemuster_id = " + pvMuster.getId());
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");

          stmt.close();
          
          // Protokolleintrag
          ProtokollDAO lvDAO = new ProtokollDAO(ivMandant);
          lvDAO.insertProtokollEintrag(Protokoll.DELETE, pvMuster.getId(), "Ausloesemuster", pvMuster.getProtokollEintrag(Protokoll.STRING, Protokoll.DELETE), pvMuster.getMandant().getId());
          
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster mit ID = " + pvMuster.getId() + " nicht loeschen.");
        }
    }

    /**
     * Loeschen eines Ausloesemuster-Aufgabe
     * @param pvMuster 
     */
    public void deleteAusloesemusterAufgabe(Ausloesemuster pvMuster)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ausloesemuster_aufgabe WHERE ausloesemuster_id = " + pvMuster.getId());
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Aufgaben mit AusloesemusterID = " + pvMuster.getId() + " nicht loeschen.");
        }
    }

    /**
     * Loeschen eines Ausloesemuster-Ereignis
     * @param pvMuster 
     */
    public void deleteAusloesemusterEreignis(Ausloesemuster pvMuster)
    {
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          int lvResult = stmt.executeUpdate("DELETE FROM " + lvSchemaOwner + ".ausloesemuster_ereignis WHERE ausloesemuster_id = " + pvMuster.getId());
          System.out.println("DELETE-Statement: " + lvResult + " Datensaetze geloescht.");
          stmt.close();
          DatenbankZugriff.closeConnection();
         }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster-Ereignisse mit AusloesemusterID = " + pvMuster.getId() + " nicht loeschen.");
        }
    }
    
    /**
     * Finden eines Ausloesemusters anhand der Ausloesemuster-ID
     * @param pvAusloesemusterID 
     * @return 
     */
    public Ausloesemuster findAusloesemusterByID(String pvAusloesemusterID)
    {
        Ausloesemuster lvAusloesemuster = null;

        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
              
          Mandant lvMandant = null;
          Aufgabe lvAufgabe = null;
          Ereignis lvEreignis = null;
          AufgabeDAO aufgabeDAO = new AufgabeDAO(ivMandant);
          EreignisDAO ereignisDAO = new EreignisDAO(ivMandant);
          HashSet<Aufgabe> aufgaben = new HashSet<Aufgabe>();
          HashSet<Ereignis> ereignisse = new HashSet<Ereignis>();

          ResultSet rs = stmt.executeQuery("SELECT mandant FROM " + lvSchemaOwner + ".ausloesemuster WHERE ausloesemuster_id = " + pvAusloesemusterID);
          if (rs.first())
          {
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(1))).toString());
          }
          
          // Ausloesemuster-Aufgabe
          rs = stmt.executeQuery("SELECT aufgabe_id FROM " + lvSchemaOwner + ".ausloesemuster_aufgabe WHERE ausloesemuster_id = " + pvAusloesemusterID);
          while (rs.next())
          {

              lvAufgabe = aufgabeDAO.findDateisucheByID((new Integer(rs.getInt(1))).toString());
             if (lvAufgabe == null)
             {
                 lvAufgabe = aufgabeDAO.findProzessstarterByID((new Integer(rs.getInt(1))).toString());                 
             }
             aufgaben.add(lvAufgabe);
          }
          
          // Ausloesemuster-Ereignis
          rs = stmt.executeQuery("SELECT ereignis_id FROM " + lvSchemaOwner + ".ausloesemuster_ereignis WHERE ausloesemuster_id = " + pvAusloesemusterID);
          while (rs.next())
          {
              lvEreignis = ereignisDAO.findEreignisByID((new Integer(rs.getInt(1))).toString());
              ereignisse.add(lvEreignis);             
          }
          
          lvAusloesemuster = new Ausloesemuster(ereignisse, aufgaben);
          if (lvMandant != null)
          {
            lvAusloesemuster.setMandant(lvMandant);
          }
          lvAusloesemuster.setId(pvAusloesemusterID);
          lvAusloesemuster.setVorgang(null);
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte Ausloesemuster mit ID = " + pvAusloesemusterID + " nicht finden.");
        }

        return lvAusloesemuster;
    }
    
    /**
     * Liefert alle Ausloesemuster fuer den Mandanten
     * @param pvMandantenID 
     * @return 
     */
    public HashMap<String, Ausloesemuster> findAusloesemusterByMandant(String pvMandantenID)
    {
        HashMap<String, Ausloesemuster> lvAusloesemusterHashMap = new HashMap<String, Ausloesemuster>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();

          Statement stmt = lvCon.createStatement();
          Statement stmtAufgabe = lvCon.createStatement();
          Statement stmtEreignis = lvCon.createStatement();
              
          Mandant lvMandant = null;
          Aufgabe lvAufgabe = null;
          Ereignis lvEreignis = null;
          Ausloesemuster lvAusloesemuster = null;
          int lvAusloesemusterID;
          ResultSet rsAufgabe = null;
          ResultSet rsEreignis = null;
          
          ResultSet rs = stmt.executeQuery("SELECT ausloesemuster_id, mandant FROM " + lvSchemaOwner + ".ausloesemuster WHERE mandant = " + pvMandantenID);
          while (rs.next())
          {
              HashSet<Aufgabe> aufgaben = new HashSet<Aufgabe>();
              HashSet<Ereignis> ereignisse = new HashSet<Ereignis>();

              lvAusloesemusterID = rs.getInt(1);
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(2))).toString());
          
              // Ausloesemuster-Aufgabe
              rsAufgabe = stmtAufgabe.executeQuery("SELECT aufgabe_id FROM " + lvSchemaOwner + ".ausloesemuster_aufgabe WHERE ausloesemuster_id = " + lvAusloesemusterID);
              while (rsAufgabe.next())
              {
                  lvAufgabe = AufgabeHandler.getInstance(lvMandant).getDateisuche().get((new Integer(rsAufgabe.getInt(1))).toString());
                  if (lvAufgabe == null)
                  {
                      lvAufgabe = AufgabeHandler.getInstance(lvMandant).getProzessStarter().get((new Integer(rsAufgabe.getInt(1))).toString());                 
                  }
                  aufgaben.add(lvAufgabe);
              }
          
              // Ausloesemuster-Ereignis
              rsEreignis = stmtEreignis.executeQuery("SELECT ereignis_id FROM " + lvSchemaOwner + ".ausloesemuster_ereignis WHERE ausloesemuster_id = " + lvAusloesemusterID);
              while (rsEreignis.next())
              {
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rsEreignis.getInt(1))).toString());
                  ereignisse.add(lvEreignis);             
              }
          
              lvAusloesemuster = new Ausloesemuster(ereignisse, aufgaben);
              if (lvMandant != null)
              {
                  lvAusloesemuster.setMandant(lvMandant);
              }
              lvAusloesemuster.setId((new Integer(lvAusloesemusterID)).toString());
              lvAusloesemuster.setVorgang(null);
              lvAusloesemusterHashMap.put((new Integer(lvAusloesemusterID)).toString(), lvAusloesemuster);
          }
          if (rs != null)
            rs.close();
          if (rsAufgabe != null)
            rsAufgabe.close();
          if (rsEreignis != null)
            rsEreignis.close();
          
          stmt.close();
          stmtAufgabe.close();
          stmtEreignis.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Ausloesemuster f�r den Mandanten mit ID = " + pvMandantenID + " finden.");
        }

        return lvAusloesemusterHashMap;
    }
    
    /**
     * Liefert eine HashMap aller Ausloesemuster
     * @return 
     */
    public HashMap<String, Ausloesemuster> getAusloesemuster()
    {
        HashMap<String, Ausloesemuster> lvAusloesemusterHashMap = new HashMap<String, Ausloesemuster>();
        try
        {
          DatenbankZugriff.openConnection(ivMandant.getInstitutsId());
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivMandant.getInstitutsId()).getUsername();
          Statement stmt = lvCon.createStatement();
          Statement stmtAufgabe = lvCon.createStatement();
          Statement stmtEreignis = lvCon.createStatement();
              
          Mandant lvMandant = null;
          Aufgabe lvAufgabe = null;
          Ereignis lvEreignis = null;
          Ausloesemuster lvAusloesemuster = null;
          int lvAusloesemusterID;
          ResultSet rsAufgabe = null;
          ResultSet rsEreignis = null;
          
          ResultSet rs = stmt.executeQuery("SELECT ausloesemuster_id, mandant FROM " + lvSchemaOwner + ".ausloesemuster");
          while (rs.next())
          {
              HashSet<Aufgabe> aufgaben = new HashSet<Aufgabe>();
              HashSet<Ereignis> ereignisse = new HashSet<Ereignis>();

              lvAusloesemusterID = rs.getInt(1);
              lvMandant = Mandant.getMandanten().get((new Integer(rs.getInt(2))).toString());
          
              // Ausloesemuster-Aufgabe
              rsAufgabe = stmtAufgabe.executeQuery("SELECT aufgabe_id FROM " + lvSchemaOwner + ".ausloesemuster_aufgabe WHERE ausloesemuster_id = " + lvAusloesemusterID);
              while (rsAufgabe.next())
              {
                  lvAufgabe = AufgabeHandler.getInstance(lvMandant).getDateisuche().get((new Integer(rsAufgabe.getInt(1))).toString());
                  if (lvAufgabe == null)
                  {
                      lvAufgabe = AufgabeHandler.getInstance(lvMandant).getProzessStarter().get((new Integer(rsAufgabe.getInt(1))).toString());                 
                  }
                  aufgaben.add(lvAufgabe);
              }
          
              // Ausloesemuster-Ereignis
              rsEreignis = stmtEreignis.executeQuery("SELECT ereignis_id FROM " + lvSchemaOwner + ".ausloesemuster_ereignis WHERE ausloesemuster_id = " + lvAusloesemusterID);
              while (rsEreignis.next())
              {
                  lvEreignis = EreignisHandler.getInstance(lvMandant).getEreignisse().get((new Integer(rsEreignis.getInt(1))).toString());
                  ereignisse.add(lvEreignis);             
              }
          
              lvAusloesemuster = new Ausloesemuster(ereignisse, aufgaben);
              if (lvMandant != null)
              {
                  lvAusloesemuster.setMandant(lvMandant);
              }
              lvAusloesemuster.setId((new Integer(lvAusloesemusterID)).toString());
              lvAusloesemuster.setVorgang(null);
              lvAusloesemusterHashMap.put((new Integer(lvAusloesemusterID)).toString(), lvAusloesemuster);
          }
          if (rs != null)
            rs.close();
          if (rsAufgabe != null)
            rsAufgabe.close();
          if (rsEreignis != null)
            rsEreignis.close();
          
          stmt.close();
          stmtAufgabe.close();
          stmtEreignis.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Ausloesemuster finden.");
        }

        return lvAusloesemusterHashMap;
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

          ResultSet rs = stmt.executeQuery("SELECT MAX(ausloesemuster_id) FROM " + lvSchemaOwner + ".ausloesemuster");
          
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
            System.out.println("AusloesemusterDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }


}
