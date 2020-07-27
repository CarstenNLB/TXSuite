package nlb.txs.schnittstelle.Zinskurse;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import nlb.txs.schnittstelle.OutputXML.OutputXML;

public class LeseZinskurse
{
   private String ivFilenameZinskurse;
   private int ivZaehlerZinskurse;

   /**
    * Konstruktor
    * @param pvZinskurseName 
    */
  public LeseZinskurse(String pvZinskurseName)
  {
    ivFilenameZinskurse = pvZinskurseName;
    ivZaehlerZinskurse = 0;
  }   

  /**
    * @param pvOutputXML 
    * @return 
    *
    */
  public int readZinskurseCSV(OutputXML pvOutputXML)
  {
     // Oeffnen der Zinskurse
      FileInputStream lvFis = null;
      try
      {
          lvFis = new FileInputStream(ivFilenameZinskurse);
      }
      catch (Exception e)
      {
        System.out.println("Konnte Darlehensdatei nicht oeffnen!");
        return 0;
      }
 	
      String lvZeile = null;
      Zinskurse lvZk = null;
      BufferedReader lvIn = new BufferedReader(new InputStreamReader(lvFis));
      // Verarbeitung
      try
      {
        while ((lvZeile = lvIn.readLine()) != null)  // Lesen Zinskurse
        {
          if (lvZeile.length() > 4)
          {
        	//if (lvZeile.startsWith("ATXX800TXX802"))
        	//{
        	//  lvZk = parseZinskurseTXX800(lvZeile.substring(13));	
        	//}
        	//else
        	//{
              lvZk = parseZinskurse(lvZeile); // Parsen der Felder
        	//}
            pvOutputXML.printZinskurse2XML(lvZk);
            ivZaehlerZinskurse++;
          }
        }
      }
      catch (Exception e)
      {
         System.out.println("Fehler beim Verarbeiten der Zinskurse!");
         e.printStackTrace();
      }

      try
      {
        lvIn.close();
      }
      catch (Exception e)
      {
        System.out.println("Konnte Zinskursedatei nicht schliessen!");		
      }    

      return ivZaehlerZinskurse;	
  }
  
  /**
   * Liest die Zinskurse ein.
   * @param pvZeile 
   * @return 
   */
  public Zinskurse parseZinskurse(String pvZeile) 
  {
    String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
    int    lvLfd=1;                // lfd feldnr, pruefsumme je satzart
    int    lvZzStr=0;              // pointer fuer satzbereich

    Zinskurse lvZk = new Zinskurse();

    // steuerung/iteration eingabesatz
    for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
    {
      // wenn Komma erkannt
      if (pvZeile.charAt(lvZzStr) == ';')
      {
    	  if (lvLfd == 1) lvZk.setInstNr(lvTemp); 
    	  if (lvLfd == 2) lvZk.setWaehrungscode(lvTemp);
    	  if (lvLfd == 3) lvZk.setMandant(lvTemp);
    	  if (lvLfd == 4) lvZk.setBuchungskreis(lvTemp);
    	  if (lvLfd == 5) lvZk.setQuellsystem(lvTemp);
    	  if (lvLfd == 6) lvZk.setLaufzeit(lvTemp);
    	  if (lvLfd == 7) lvZk.setGueltigkeitDatum(lvTemp);
    	  if (lvLfd == 8) lvZk.setGueltigkeitUhrzeit(lvTemp);
    	  if (lvLfd == 9) lvZk.setMarktplatz(lvTemp);
    	  if (lvLfd == 10) lvZk.setAnbieter(lvTemp);
    	  if (lvLfd == 11) lvZk.setIdentifikation(lvTemp);	
    	  if (lvLfd == 12) lvZk.setBriefkurs(lvTemp);
    	  if (lvLfd == 13) lvZk.setGeldkurs(lvTemp);
    	  if (lvLfd == 14) lvZk.setMittelkurs(lvTemp);
    	  if (lvLfd == 15) lvZk.setVerzinsungsfrequenz(lvTemp);
    	  if (lvLfd == 16) lvZk.setTageszaehlmethode(lvTemp);
    	  if (lvLfd == 17) lvZk.setZinsmethode(lvTemp);
    	  if (lvLfd == 18) lvZk.setValutatage(lvTemp);
    	  if (lvLfd == 19) lvZk.setZahlungsfrequenz(lvTemp);
    	  if (lvLfd == 20) lvZk.setQuotierungsart(lvTemp);
    	  if (lvLfd == 21) lvZk.setArbeitstagekonvention(lvTemp);
    	  if (lvLfd == 22) lvZk.setSpezArbeitstagekonv(lvTemp);
    	  if (lvLfd == 23) lvZk.setMonatsendekonvention(lvTemp);
    	  if (lvLfd == 24) lvZk.setBankkalender(lvTemp);
    	  lvLfd++; // naechste feldnummer
    	  // loeschen zwischenbuffer
    	  lvTemp = new String();
      }
      else
      {
    	  // uebernehmen byte aus eingabesatzposition
    	  lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
      }
    } // ende for
	
    return lvZk;
  }
  
}