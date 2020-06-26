/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.dao.mandanten;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.dao.DatenbankZugriff;

/**
 * @author tepperc
 *
 */
public class MandantenDAO 
{    
    /**
     * Institut
     */
    private String ivInstitut;
    
    /**
     * Konstruktor
     * @param pvMandantenName 
     */
    public MandantenDAO(String pvInstitut)
    {
        super();
        this.ivInstitut = pvInstitut;
    }
    
    /**
     * Einfuegen eines Mandanten
     */
    public void insertMandant()
    {
        // Eventuell spaeter realisieren
    }
    
    /**
     * Loeschen eines Mandanten
     */
    public void deleteMandant()
    {
        // Eventuell spaeter realisieren
    }
        
    /**
     * Liefert die MandatenID fuer die Institutsnummer
     * @param pvInstitutsnummer 
     * @return 
     */
    public String findMandantenID(String pvInstitutsnummer)
    {
        String lvMandantenID = null;
        try
        {
          DatenbankZugriff.openConnection(ivInstitut);  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivInstitut).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT mandanten_id FROM " + lvSchemaOwner + ".mandanten WHERE institutsnummer = " + pvInstitutsnummer);

          if (rs.next()) 
          {
            lvMandantenID = (new Integer(rs.getInt(1)).toString());
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine MandantenID fuer Institutsnummer " + pvInstitutsnummer + " finden.");
        }
        return lvMandantenID;
    }
    
    /**
     * Liefert eine HashMap aller Mandanten
     * @return 
     */
    public HashMap<String, Mandant> getMandanten()
    {
        HashMap<String, Mandant> lvMandantenHashMap = new HashMap<String, Mandant>();
        try
        {
          DatenbankZugriff.openConnection(ivInstitut);  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivInstitut).getUsername();

          //System.out.println("lvSchemaOwner: " + lvSchemaOwner);
          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT mandanten_id, institutsnummer, bezeichnung FROM " + lvSchemaOwner + ".mandanten");

          Mandant lvMandant;
          while (rs.next()) 
          {
        	  //System.out.println("Abgelegt als: " + rs.getString(2) + " " + (new Integer(rs.getInt(1))).toString() + " " + rs.getString(2));
            lvMandant = new Mandant((new Integer(rs.getInt(1))).toString(), rs.getString(3), rs.getString(2));
            lvMandantenHashMap.put(rs.getString(1), lvMandant);
            //System.out.println("CT-Test:" + lvMandant.getInstitutsId());
          }
          rs.close();
          stmt.close();
          DatenbankZugriff.closeConnection();
        }
        catch (Exception exp)
        {
            System.out.println("Konnte keine Mandanten aus der Datenbank lesen.");
        }

        return lvMandantenHashMap;
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
          DatenbankZugriff.openConnection(ivInstitut);  
          Connection lvCon = DatenbankZugriff.getConnection();
          String lvSchemaOwner = DatenbankZugriff.getListeDatenbankParameter().get(ivInstitut).getUsername();

          Statement stmt = lvCon.createStatement();

          ResultSet rs = stmt.executeQuery("SELECT MAX(mandanten_id) FROM " + lvSchemaOwner + ".mandanten");
          
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
            System.out.println("MandantenDAO: Konnte MaxId nicht ermitteln.");
        }  
        
        return lvMaxId;
    }


}
