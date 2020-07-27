package nlb.txs.schnittstelle.Wechselkurse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.OutputXML.OutputXML;

public class LeseWechselkurse
{
   /**
    * 	
    */
	private int ivZaehlerWechselkurse;
	
	/**
	 * 
	 */
	private String ivFilenameWechselkurse;
   
	/**
	 * Konstruktor
	 * @param pvWechselkurseName 
	 */
	public LeseWechselkurse(String pvWechselkurseName)
	{
		ivFilenameWechselkurse = pvWechselkurseName;
		ivZaehlerWechselkurse = 0;
	}   

	/**
	 * @param pvOutputXML 
	 * @return 
	 *
	 */
	public int readWechselkurseCSV(OutputXML pvOutputXML)
	{
      // Oeffnen der Wechselkurse
      FileInputStream lvFis = null;
      try
      {
          lvFis = new FileInputStream(ivFilenameWechselkurse);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Wechselkursdatei nicht oeffnen!");
        return 0; 
      }
 	
      boolean lvFirst = true;
      String lvZeile = null;	
      Wechselkurse lvWk = null;
      BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
      // Verarbeitung
      try
      {
        while ((lvZeile = lvIn.readLine()) != null)  // Lesen Wechselkurse
        {
          if (!lvFirst)
          {
            if (lvZeile.length() > 4)
            {
              lvWk = parseWechselkurse(lvZeile); // Parsen der Felder
              pvOutputXML.printWechselkurse2XML(lvWk);
              ivZaehlerWechselkurse++;
            }
          }
          else
          {
            lvFirst = false;
          }
        }
      }
      catch (Exception e)
      {
         System.out.println("Fehler beim Verarbeiten der Wechselkurse!");
         e.printStackTrace();
      }

      try
      {
        lvIn.close();
      }
      catch (Exception e)
      {
        System.out.println("Konnte Wechselkursedatei nicht schliessen!");		
      } 
     return ivZaehlerWechselkurse;   	
  }
 
 
  /**
   * Liest die Wechselkurse ein.
   * @param pvZeile 
   * @return 
   */
  public Wechselkurse parseWechselkurse(String pvZeile) 
  {
    String stemp = new String(); // arbeitsbereich/zwischenspeicher feld
    int    lvLfd=1;                // lfd feldnr, pruefsumme je satzart
    int    lvZzStr=0;              // pointer fuer satzbereich

    Wechselkurse lvWk = new Wechselkurse();

    //System.out.println("Zeile: " + zeile);
    // steuerung/iteration eingabesatz
    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {
      // wenn Komma erkannt
      if (pvZeile.charAt(lvZzStr) == ';')
      {
    	  if (lvLfd == 1) lvWk.setInstNr(stemp); 
    	  if (lvLfd == 2) lvWk.setWaehrungscode(stemp);
    	  if (lvLfd == 3) lvWk.setMandant(stemp);
    	  if (lvLfd == 4) lvWk.setBuchungskreis(stemp);
    	  if (lvLfd == 5) lvWk.setQuellsystem(stemp);
    	  if (lvLfd == 6) lvWk.setGueltigkeitDatum(stemp);
    	  if (lvLfd == 7) lvWk.setGueltigkeitUhrzeit(stemp);
    	  if (lvLfd == 8) lvWk.setAnbieter(stemp);	
    	  if (lvLfd == 9) lvWk.setMarktplatz(stemp);
    	  if (lvLfd == 10) lvWk.setBriefkurs(stemp);
    	  if (lvLfd == 11) lvWk.setGeldkurs(stemp);
    	  if (lvLfd == 12) lvWk.setMittelkurs(stemp);
    	  lvLfd++;                  // naechste feldnummer
    	  // loeschen zwischenbuffer
    	  stemp = new String();
      }
      else
      {
    	  // uebernehmen byte aus eingabesatzposition
    	  stemp = stemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for
	
    return lvWk;
  }

}