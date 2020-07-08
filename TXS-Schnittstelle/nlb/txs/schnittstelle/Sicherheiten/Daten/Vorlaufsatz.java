/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Sicherheiten.Daten;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Vorlaufsatz 
{
    /**
     * Dateiname SAP-System
     */
    private String ivDateiname;
  
    /**
     * Bankarea (Institut)
     */
    private String ivBankarea;
    
    /**
     * Buchungsdatum
     */
    private String ivBuchungsdatum;
    
    /**
     * Verarbeitungskennzeichen (Delta-/Vollanlieferung)
     */
    private String ivVerarbeitungskennzeichen;
    
    /**
     * SAP-Programmname
     */
    private String ivProgrammname;
    
    /**
     * SAP-Username
     */
    private String ivUsername;
    
    /**
     * SAP-Systemname
     */
    private String ivSystemname;
    
    /**
     * Timestamp der Dateierstellung
     */
    private String ivTimestamp;

    /**
     * @return the filename
     */
    public String getDateiname() {
        return this.ivDateiname;
    }

    /**
     * @param pvDateiname
     */
    public void setDateiname(String pvDateiname) {
        this.ivDateiname = pvDateiname;
    }

    /**
     * @return the bankarea
     */
    public String getBankarea() {
        return this.ivBankarea;
    }

    /**
     * @return the bankarea
     */
    public String getBankareaValue() {
        return this.ivBankarea.substring(this.ivBankarea.indexOf(":") + 1);
    }
    
    /**
     * @param pvBankarea the bankarea to set
     */
    public void setBankarea(String pvBankarea) {
        this.ivBankarea = pvBankarea;
    }

    /**
     * @return the buchungsdatum
     */
    public String getBuchungsdatum() {
        return this.ivBuchungsdatum;
    }

    /**
     * @param pvBuchungsdatum the buchungsdatum to set
     */
    public void setBuchungsdatum(String pvBuchungsdatum) {
        this.ivBuchungsdatum = pvBuchungsdatum;
    }

    /**
     * @return the verarbeitungskennzeichen
     */
    public String getVerarbeitungskennzeichen() {
        return this.ivVerarbeitungskennzeichen;
    }

    /**
     * @param pvVerarbeitungskennzeichen the verarbeitungskennzeichen to set
     */
    public void setVerarbeitungskennzeichen(String pvVerarbeitungskennzeichen) {
        this.ivVerarbeitungskennzeichen = pvVerarbeitungskennzeichen;
    }

    /**
     * @return the programmname
     */
    public String getProgrammname() {
        return this.ivProgrammname;
    }

    /**
     * @param pvProgrammname the programmname to set
     */
    public void setProgrammname(String pvProgrammname) {
        this.ivProgrammname = pvProgrammname;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return this.ivUsername;
    }

    /**
     * @param pvUsername the username to set
     */
    public void setUsername(String pvUsername) {
        this.ivUsername = pvUsername;
    }

    /**
     * @return the systemname
     */
    public String getSystemname() {
        return this.ivSystemname;
    }

    /**
     * @param pvSystemname the systemname to set
     */
    public void setSystemname(String pvSystemname) {
        this.ivSystemname = pvSystemname;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return this.ivTimestamp;
    }

    /**
     * @param pvTimestamp the timestamp to set
     */
    public void setTimestamp(String pvTimestamp) {
        this.ivTimestamp = pvTimestamp;
    }
   
    /**
     * Schreibt den Inhalt des Vorlaufsatz in einen String
     * @return
     */
    public String toString()
    {
        String lvOut = new String();

        lvOut = ivDateiname + StringKonverter.lineSeparator;
        lvOut = lvOut + ivBankarea + StringKonverter.lineSeparator;
        lvOut = lvOut + ivBuchungsdatum + StringKonverter.lineSeparator;
        lvOut = lvOut + ivVerarbeitungskennzeichen + StringKonverter.lineSeparator;
        lvOut = lvOut + ivProgrammname + StringKonverter.lineSeparator;
        lvOut = lvOut + ivUsername + StringKonverter.lineSeparator;
        lvOut = lvOut + ivSystemname + StringKonverter.lineSeparator;
        lvOut = lvOut + ivTimestamp + StringKonverter.lineSeparator;

        return lvOut;
    }
}
