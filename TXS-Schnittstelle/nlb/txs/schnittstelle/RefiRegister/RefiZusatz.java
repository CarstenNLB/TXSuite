/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.RefiRegister;


import nlb.txs.schnittstelle.Utilities.StringKonverter;

/**
 * @author tepperc
 *
 */
public class RefiZusatz 
{
    /**
     * Aktivkontonummer
     */
    private String ivKontonummer;
 
    /**
     * Passivkontonummer
     */
    private String ivPassivkontonummer;
    
    /**
     * Kommentar am Finanzgeschaeft
     */
    private String ivKommentarFinanzgeschaeft;
    
    /**
     * Sicherheiten-ID
     */
    private String ivSicherheitenID;
    
    /**
     * Pfandobjekt-ID
     */
    private String ivPfandobjektID;
    
    /**
     * Kundennummer (Konsorte)
     */
    private String ivKundennummer;
    
    /**
     * Umfang
     */
    private String ivUmfang;
    
    /**
     * Rechtsgrundlage
     */
    private String ivRechtsgrundlage;
    
    /**
     * Datum des rechtlichen Grundes
     */
    private String ivDatumRechtlicherGrund;
    
    /**
     * Bemerkung
     */
    private String ivBemerkung;
    
    /**
     * Konstruktor
     */
    public RefiZusatz() 
    {
        this.ivKontonummer = new String();
        this.ivPassivkontonummer = new String();
        this.ivKommentarFinanzgeschaeft = new String();
        this.ivSicherheitenID = new String();
        this.ivPfandobjektID = new String();
        this.ivKundennummer = new String();
        this.ivUmfang = new String();
        this.ivRechtsgrundlage = new String();
        this.ivDatumRechtlicherGrund = new String();
        this.ivBemerkung = new String();
    }

    /**
     * @return the kontonummer
     */
    public String getKontonummer() {
        return this.ivKontonummer;
    }

    /**
     * @param pvKontonummer the kontonummer to set
     */
    public void setKontonummer(String pvKontonummer) {
        this.ivKontonummer = pvKontonummer;
    }

    /**
     * @return the passivkontonummer
     */
    public String getPassivkontonummer() {
        return this.ivPassivkontonummer;
    }

    /**
     * @param pvPassivkontonummer the passivkontonummer to set
     */
    public void setPassivkontonummer(String pvPassivkontonummer) {
        this.ivPassivkontonummer = pvPassivkontonummer;
    }

    /**
     * @return the kommentarFinanzgeschaeft
     */
    public String getKommentarFinanzgeschaeft() {
        return this.ivKommentarFinanzgeschaeft;
    }

    /**
     * @param pvKommentarFinanzgeschaeft the kommentarFinanzgeschaeft to set
     */
    public void setKommentarFinanzgeschaeft(String pvKommentarFinanzgeschaeft) {
        this.ivKommentarFinanzgeschaeft = pvKommentarFinanzgeschaeft;
    }

    /**
     * @return the sicherheitenID
     */
    public String getSicherheitenID() {
        return this.ivSicherheitenID;
    }

    /**
     * @param pvSicherheitenID the sicherheitenID to set
     */
    public void setSicherheitenID(String pvSicherheitenID) {
        this.ivSicherheitenID = pvSicherheitenID;
    }

    /**
     * @return the pfandobjektID
     */
    public String getPfandobjektID() {
        return this.ivPfandobjektID;
    }

    /**
     * @param pvPfandobjektID the pfandobjektID to set
     */
    public void setPfandobjektID(String pvPfandobjektID) {
        this.ivPfandobjektID = pvPfandobjektID;
    }

    /**
     * @return the kundennummer
     */
    public String getKundennummer() {
        return this.ivKundennummer;
    }

    /**
     * @param pvKundennummer the kundennummer to set
     */
    public void setKundennummer(String pvKundennummer) {
        this.ivKundennummer = pvKundennummer;
    }

    /**
     * @return the umfang
     */
    public String getUmfang() {
        return this.ivUmfang;
    }

    /**
     * @param pvUmfang the umfang to set
     */
    public void setUmfang(String pvUmfang) {
        this.ivUmfang = pvUmfang.replace(",", ".");
    }

    /**
     * @return the rechtsgrundlage
     */
    public String getRechtsgrundlage() {
        return this.ivRechtsgrundlage;
    }

    /**
     * @param pvRechtsgrundlage the rechtsgrundlage to set
     */
    public void setRechtsgrundlage(String pvRechtsgrundlage) {
        this.ivRechtsgrundlage = pvRechtsgrundlage;
    }

    /**
     * @return the datumRechtlicherGrund
     */
    public String getDatumRechtlicherGrund() {
        return this.ivDatumRechtlicherGrund;
    }

    /**
     * @param pvDatumRechtlicherGrund the datumRechtlicherGrund to set
     */
    public void setDatumRechtlicherGrund(String pvDatumRechtlicherGrund) {
        this.ivDatumRechtlicherGrund = pvDatumRechtlicherGrund;
    }

    /**
     * @return the bemerkung
     */
    public String getBemerkung() {
        return this.ivBemerkung;
    }

    /**
     * @param pvBemerkung the bemerkung to set
     */
    public void setBemerkung(String pvBemerkung) 
    {
        this.ivBemerkung = pvBemerkung.replace("#", ";");
    }
    
    /**
     * @param pvZeile 
     * @return 
     * 
     */
    public boolean parseRefiZusatz(String pvZeile, int pvAnzahlZeilen)
    {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
     int    lvZzStr=0;              // pointer fuer satzbereich
     
     // steuerung/iteration eingabesatz
     for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn Semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
       {
         this.setRefiZusatz(lvLfd, lvTemp, pvAnzahlZeilen);
       
         lvLfd++;                  // naechste Feldnummer
       
         // loeschen Zwischenbuffer
         lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
       }
     } // ende for
     
     // Letzte Feld auch noch setzen
     this.setRefiZusatz(lvLfd, lvTemp, pvAnzahlZeilen);     
     
     return true;
   }
    
    /**
     * Setzt einen Wert des RefiZusatz
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setRefiZusatz(int pvPos, String pvWert, int pvAnzahlZeilen)
    {
        switch (pvPos)
        {
            case 0:
                this.setKontonummer(pvWert);
                break;
            case 1:
              this.setPassivkontonummer(pvWert);
              break;
            case 2:
              this.setKommentarFinanzgeschaeft(pvWert);
              break;
            case 3:  
              this.setSicherheitenID(pvWert);
              break;
            case 4:
              this.setPfandobjektID(pvWert);
              break;
            case 5:
              this.setKundennummer(pvWert);
              break;
            case 6:
              this.setUmfang(pvWert.replace("%", ""));
              break;
            case 7:
              this.setRechtsgrundlage(pvWert.replace("#", ";"));
              break;
            case 8:
              this.setDatumRechtlicherGrund(pvWert);
              //System.out.println("Zeile " + (pvAnzahlZeilen + 1) + ": " + pvWert);
              break;
            case 9:
              this.setBemerkung(pvWert.replace("#", ";"));
              break;
          default:
              this.setBemerkung(this.getBemerkung() + ";" + pvWert);
              //System.out.println("Zeile " + (pvAnzahlZeilen + 1) + " - RefiZusatz: undefiniert - Feld: " + pvPos + " Wert: " + pvWert);
        }
    }
    
    /**
     * Schreibt den Inhalt des RefiRegisterFilterElements in einen String
     * @return
     */
    public String toString()
    {
        StringBuilder lvOut = new StringBuilder();

        lvOut.append("Kontonummer: " + ivKontonummer + StringKonverter.lineSeparator);
        lvOut.append("Passivkontonummer: " + ivPassivkontonummer + StringKonverter.lineSeparator);
        lvOut.append("Kommentar am Finanzgeschaeft: " + ivKommentarFinanzgeschaeft + StringKonverter.lineSeparator); 
        lvOut.append("SicherheitenID: " + ivSicherheitenID + StringKonverter.lineSeparator);
        lvOut.append("PfandobjektID: " + ivPfandobjektID + StringKonverter.lineSeparator);
        lvOut.append("Kundennummer (Konsorte): " + ivKundennummer + StringKonverter.lineSeparator);
        lvOut.append("Umfang: " + ivUmfang + StringKonverter.lineSeparator);
        lvOut.append("Rechtsgrundlage: " + ivRechtsgrundlage + StringKonverter.lineSeparator);
        lvOut.append("Datum des rechtlichen Grundes: " + ivDatumRechtlicherGrund + StringKonverter.lineSeparator);
        lvOut.append("Bemerkung: " + ivBemerkung + StringKonverter.lineSeparator);

        return lvOut.toString();
    }

}
