/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.OSPInstitut;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
@Deprecated
public class OSPInstitutKundenStatistik 
{
    /**
     * Anzahl der Kunden
     */
    private int ivAnzahlKunden;
    
    /**
     * Anzahl der Konten
     */
    private int ivAnzahlKonten;
    
    // Delta zum Vortag - Soll sp�ter in die Anlieferung eingebaut werden
    //  <DeltaVTag>
    //   <AnzKd>1</AnzKd> ---> ??? Info vorhanden zu Neu/Ge�ndert/Gel�scht? wenn ja
    //   <AnzKto>2</AnzKto> ---> ??? Info vorhanden zu Neu/Ge�ndert/Gel�scht? wenn ja
    //  --- dann
    //  <AnzNeuKd>1</AnzNeuKd>
    //  <AnzNeuKto>2</AnzNeuKto>
    //  <AnzModKd>0</AnzModKd>
    //  <AnzModKto>0</AnzModKto>
    //  <AnzDelKd>0</AnzDelKd>
    //  <AnzDelKto>0</AnzDelKto>
    //  --- dann
    //  </DeltaVTag>

    /**
     * Konstruktor
     * Setzt Anzahl der Kunden und Konten auf 0.
     */
    public OSPInstitutKundenStatistik()
    {
        ivAnzahlKunden = 0;
        ivAnzahlKonten = 0;
    }

    /**
     * Liefert die Anzahl der Kunden 
     * @return Die Anzahl der Kunden
     */
    public int getAnzahlKunden() 
    {
        return this.ivAnzahlKunden;
    }

    /**
     * Setzt die Anzahl der Kunden
     * @param pvAnzahlKunden Die zu setzende Anzahl der Kunden
     */
    public void setAnzahlKunden(int pvAnzahlKunden) 
    {
        this.ivAnzahlKunden = pvAnzahlKunden;
    }

    /**
     * Liefert die Anzahl der Konten
     * @return Die Anzahl der Konten
     */
    public int getAnzahlKonten() 
    {
        return this.ivAnzahlKonten;
    }

    /**
     * Setzt die Anzahl der Konten
     * @param pvAnzahlKonten Die zu setzende Anzahl der Konten
     */
    public void setAnzahlKonten(int pvAnzahlKonten) {
        this.ivAnzahlKonten = pvAnzahlKonten;
    }
    
    /**
     * Liefert die Statistikinformationen als String
     * @return String mit den Statistikinformationen
     */
    public String toString()
    {
        StringBuilder lvHelpString = new StringBuilder();
        
        lvHelpString.append("Start Statistik:" + StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Kunden: " + ivAnzahlKunden + StringKonverter.lineSeparator);
        lvHelpString.append("Anzahl Konten: " + ivAnzahlKonten + StringKonverter.lineSeparator);
        lvHelpString.append("Ende Statistik:" + StringKonverter.lineSeparator);
        
        return lvHelpString.toString();
    }
}
