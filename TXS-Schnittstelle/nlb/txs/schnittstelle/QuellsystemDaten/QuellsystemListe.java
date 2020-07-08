/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.QuellsystemDaten;

import java.util.HashMap;
import org.apache.log4j.Logger;

/**
 * @author tepperc
 *
 */
public class QuellsystemListe extends HashMap<String, QuellsystemDaten> 
{
    /**
     * UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Prueft, ob es ein Sicherungsobjekt zur Objektnummer gibt 
     * @param pvNr Kontonummer
     * @return true - Kontonummer vorhanden; false - Kontonummer nicht vorhanden
     */
    public boolean contains(String pvNr)
    {
        return this.containsKey(pvNr);
    }
        
    /**
     * Liefert die QuellsystemDaten zur Kontonummer
     * @param pvNr Kontonummer
     * @return QuellsystemDaten zur Kontonummer
     */
    public QuellsystemDaten getQuellsystemDaten(String pvNr)
    {
        return this.get(pvNr);
    }
    
    /**
     * Zerlegt die Zeile in unterschiedliche Daten/Felder und fuegt die QuellsystemDaten der Liste hinzu.
     * @param pvZeile Die einzulesende Zeile mit den Daten
     */
    public void parseQuellsystemDaten(String pvZeile, Logger pvLogger)
    {
        QuellsystemDaten lvQuellsystemDaten = new QuellsystemDaten(pvLogger);
        String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

        // Steuerung/Iteration Eingabesatz
        for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
        {

            // wenn Semikolon erkannt
            if (pvZeile.charAt(lvZzStr) == ';')
            {
                //System.out.println(lvTemp);
                lvQuellsystemDaten.setQuellsystemDaten(lvLfd, lvTemp);
                
                lvLfd++;                  // naechste Feldnummer
                // loeschen Zwischenbuffer
                lvTemp = new String();
            }
            else
            {
                // uebernehmen Byte aus Eingabesatzposition
                lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
            }
        } // ende for  
        
          this.put(lvQuellsystemDaten.getUrsprungskontonummer(), lvQuellsystemDaten);
    }
    
}
