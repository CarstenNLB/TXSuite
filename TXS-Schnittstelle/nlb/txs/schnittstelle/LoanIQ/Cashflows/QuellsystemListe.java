/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.LoanIQ.Cashflows;

import java.util.HashMap;

import nlb.txs.schnittstelle.LoanIQ.Cashflows.Daten.QuellsystemDaten;

/**
 * @author tepperc
 *
 */
public class QuellsystemListe extends HashMap<String, QuellsystemDaten> 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Prueft, ob es ein Sicherungsobjekt zur Objektnummer gibt 
     * @param pvNr Kontonummer
     * @return true - Kontonummer vorhanden; false - Kontonummer nicht vorhanden
     */
    public boolean contains(String pvNr)
    {
    	//System.out.println("contains: " + pvNr);
        return this.containsKey(pvNr);
    }
        
    /**
     * Liefert die QuellsystemDaten zur Kontonummer
     * @param pvNr Kontonummer
     * @return QuellsystemDaten zur Kontonummer
     */
    public QuellsystemDaten getQuellsystemDaten(String pvNr)
    {
    	//System.out.println("get: " + pvNr);
        return this.get(pvNr);
    }
    
    /**
     * Fuegt die QuellsystemDaten hinzu
     * @param pvZeile Die einzulesende Zeile mit den Daten
     * @param pvModus Verarbeitungsmodus LoanIQ (1) oder MIDAS (2)
     * @return Liefert immer 'true'
     */
    public boolean parseQuellsystemDaten(String pvZeile, int pvModus)
    {
        QuellsystemDaten lvQuellsystemDaten = new QuellsystemDaten();
        String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
        int    lvZzStr=0;              // pointer fuer satzbereich
                
        // Steuerung/Iteration Eingabesatz
        for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
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
        
        //System.out.println("put: " + lvQuellsystemDaten.getUrsprungskontonummer());
        
        // LoanIQ & Wertpapiere
        if (pvModus == CashflowsVerarbeitung.LOANIQ)
		{
          this.put(lvQuellsystemDaten.getUrsprungskontonummer(), lvQuellsystemDaten);
		}
        
        // MIDAS
        if (pvModus == CashflowsVerarbeitung.MIDAS)
        {
        	this.put(lvQuellsystemDaten.getZielkontonummer(), lvQuellsystemDaten);
        }
                
        return true; 
    }
    
}
