/*******************************************************************************
 * Copyright (c) 2015 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.DeutscheHypo2TXS;

import java.util.LinkedList;

/**
 * @author tepperc
 *
 */
public class KreditDeutscheHypo 
{
    /**
     * Kontonummer
     */
    private String ivKontonummer;
    
    /**
     * Zusagedatum
     */
    private String ivZusagedatum;
    
    /**
     * Vertragswaehrung 
     */
    private String ivVertragswaehrung;
    
    /**
     * Externer Key - Kondition
     */
    private String ivExternerKeyKondition;
    
    /**
     * Sicherheitennummer
     */
    private String ivSicherheitennummer;
    
    /**
     * Waehrung 
     */
    private String ivWaehrung;
    
    /**
     * Datum des rechtlichen Grundes
     */
    private String ivDatumRechtlicherGrund;
    
    /**
     * Nennbetrag
     */
    private String ivNennbetrag;
    
    /**
     * Verfuegungsbetrag
     */
    private String ivVerfuegungsbetrag;
    
    /**
     * Originalsicherheitanteil
     */
    private String ivOriginalsicherheitanteil;
    
    /**
     * Bemerkungen - Info Refi.register
     */
    private String ivBemerkungen;
    
    /**
     * rechtlicher Grund
     */
    private String ivRechtsgrundlage;
    
    /**
     * Originalsicherheit
     */
    private String ivOriginalsicherheit;
    
    /**
     * Sicherheitenart
     */
    private String ivSicherheitenart;
    
    /** 
     * Waehrung
     */
    private String ivSicherheitWaehrung;
    
    /**
     * Datum des rechlichen Grundes 
     */
    private String ivRechtlicherGrundVom;
    
    /**
     * Pfandrecht Nennbetrag
     */
    private String ivPfandrechtNennbetrag;
    
    /**
     * Pfandrecht Verfuegungsbetrag
     */
    private String ivPfandrechtVerfuegungsbetrag;
    
    /**
     * Originalsicherheitenanteil
     */
    private String ivOriginalsicherheitenanteil;
    
    /**
     * Bemerkungen (Info Refi.register)
     */
    private String ivBemerkungenOriginalsicherheiten;
    
    /**
     * 
     */
    private String ivPfandrechtRechtlicherGrund;
    
    /**
     * Grundbuchnummer
     */
    private String ivGrundbuchnummer;
    
    /**
     * Waehrung des Grundbuches
     */
    private String ivGrundbuchWaehrung;
    
    /**
     * Grundbuchabteilung
     */
    private String ivGrundbuchabteilung;
        
    /**
     * Bestandsverzeichnisnummer
     */
    private String ivBestandsverzeichnisnummer;
    
    /**
     * Datum des rechtlichen Grundes (eingetragen am)
     */
    private String ivEingetragenAm;
    
    /**
     * Grundbuchblattnummer
     */
    private String ivGrundbuchblattnummer;

    /**
     * Laufende Nummer
     */
    private String ivLaufendeNummer;
    
    /**
     * Gesamtgrundschuld 
     */
    private String ivGesamtgrundschuld;
        
    /**
     * Liste der GrundbuchDaten
     */
    private LinkedList<GrundbuchDaten> ivListeGrundbuchDaten;
          
    /**
     * Konstruktor - Initialisierung
     */
    public KreditDeutscheHypo() 
    {
        super();
        this.ivKontonummer = new String();
        this.ivZusagedatum = new String();
        this.ivVertragswaehrung = new String();
        this.ivExternerKeyKondition = new String();
        this.ivSicherheitennummer = new String();
        this.ivWaehrung = new String();
        this.ivDatumRechtlicherGrund = new String();
        this.ivNennbetrag = new String();
        this.ivVerfuegungsbetrag = new String();
        this.ivOriginalsicherheitanteil = new String();
        this.ivBemerkungen = new String();
        this.ivRechtsgrundlage = new String();
        this.ivOriginalsicherheit = new String();
        this.ivSicherheitenart = new String();
        this.ivSicherheitWaehrung = new String();
        this.ivRechtlicherGrundVom = new String();
        this.ivPfandrechtNennbetrag = new String();
        this.ivPfandrechtVerfuegungsbetrag = new String();
        this.ivOriginalsicherheitenanteil = new String();
        this.ivBemerkungenOriginalsicherheiten = new String();
        this.ivPfandrechtRechtlicherGrund = new String();
        this.ivGrundbuchnummer = new String();
        this.ivGrundbuchWaehrung = new String();
        this.ivGrundbuchabteilung = new String();
        this.ivBestandsverzeichnisnummer = new String();
        this.ivEingetragenAm = new String();
        this.ivGrundbuchblattnummer = new String();
        this.ivLaufendeNummer = new String();
        this.ivGesamtgrundschuld = new String();
        this.ivListeGrundbuchDaten = new LinkedList<GrundbuchDaten>();
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
     * @return the zusagedatum
     */
    public String getZusagedatum() {
        return this.ivZusagedatum;
    }

    /**
     * @param pvZusagedatum the zusagedatum to set
     */
    public void setZusagedatum(String pvZusagedatum) {
        this.ivZusagedatum = pvZusagedatum;
    }

    /**
     * @return the vertragswaehrung
     */
    public String getVertragswaehrung() {
        return this.ivVertragswaehrung;
    }

    /**
     * @param pvVertragswaehrung the vertragswaehrung to set
     */
    public void setVertragswaehrung(String pvVertragswaehrung) {
        this.ivVertragswaehrung = pvVertragswaehrung;
    }

    /**
     * @return the externerKeyKondition
     */
    public String getExternerKeyKondition() {
        return this.ivExternerKeyKondition;
    }

    /**
     * @param pvExternerKeyKondition the externerKeyKondition to set
     */
    public void setExternerKeyKondition(String pvExternerKeyKondition) {
        this.ivExternerKeyKondition = pvExternerKeyKondition;
    }

    /**
     * @return the sicherheitennummer
     */
    public String getSicherheitennummer() {
        return this.ivSicherheitennummer;
    }

    /**
     * @param pvSicherheitennummer the sicherheitennummer to set
     */
    public void setSicherheitennummer(String pvSicherheitennummer) {
        this.ivSicherheitennummer = pvSicherheitennummer;
    }

    /**
     * @return the waehrung
     */
    public String getWaehrung() {
        return this.ivWaehrung;
    }

    /**
     * @param pvWaehrung the waehrung to set
     */
    public void setWaehrung(String pvWaehrung) {
        this.ivWaehrung = pvWaehrung;
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
     * @return the nennbetrag
     */
    public String getNennbetrag() {
        return this.ivNennbetrag;
    }

    /**
     * @param pvNennbetrag the nennbetrag to set
     */
    public void setNennbetrag(String pvNennbetrag) {
        this.ivNennbetrag = pvNennbetrag;
    }

    /**
     * @return the verfuegungsbetrag
     */
    public String getVerfuegungsbetrag() {
        return this.ivVerfuegungsbetrag;
    }

    /**
     * @param pvVerfuegungsbetrag the verfuegungsbetrag to set
     */
    public void setVerfuegungsbetrag(String pvVerfuegungsbetrag) {
        this.ivVerfuegungsbetrag = pvVerfuegungsbetrag;
    }

    /**
     * @return the originalsicherheitanteil
     */
    public String getOriginalsicherheitanteil() {
        return this.ivOriginalsicherheitanteil;
    }

    /**
     * @param pvOriginalsicherheitanteil the originalsicherheitanteil to set
     */
    public void setOriginalsicherheitanteil(String pvOriginalsicherheitanteil) {
        this.ivOriginalsicherheitanteil = pvOriginalsicherheitanteil;
    }

    /**
     * @return the bemerkungen
     */
    public String getBemerkungen() {
        return this.ivBemerkungen;
    }

    /**
     * @param pvBemerkungen the bemerkungen to set
     */
    public void setBemerkungen(String pvBemerkungen) {
        this.ivBemerkungen = pvBemerkungen.replace("#", ";");
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
     * @return the originalsicherheit
     */
    public String getOriginalsicherheit() {
        return this.ivOriginalsicherheit;
    }

    /**
     * @param pvOriginalsicherheit the originalsicherheit to set
     */
    public void setOriginalsicherheit(String pvOriginalsicherheit) {
        this.ivOriginalsicherheit = pvOriginalsicherheit;
    }

    /**
     * @return the sicherheitenart
     */
    public String getSicherheitenart() {
        return this.ivSicherheitenart;
    }

    /**
     * @param pvSicherheitenart the sicherheitenart to set
     */
    public void setSicherheitenart(String pvSicherheitenart) {
        this.ivSicherheitenart = pvSicherheitenart;
    }

    /**
     * @return the sicherheitWaehrung
     */
    public String getSicherheitWaehrung() {
        return this.ivSicherheitWaehrung;
    }

    /**
     * @param pvSicherheitWaehrung the sicherheitWaehrung to set
     */
    public void setSicherheitWaehrung(String pvSicherheitWaehrung) {
        this.ivSicherheitWaehrung = pvSicherheitWaehrung;
    }

    /**
     * @return the rechtlicherGrundVom
     */
    public String getRechtlicherGrundVom() {
        return this.ivRechtlicherGrundVom;
    }

    /**
     * @param pvRechtlicherGrundVom the rechtlicherGrundVom to set
     */
    public void setRechtlicherGrundVom(String pvRechtlicherGrundVom) {
        this.ivRechtlicherGrundVom = pvRechtlicherGrundVom;
    }

    /**
     * @return the pfandrechtNennbetrag
     */
    public String getPfandrechtNennbetrag() {
        return this.ivPfandrechtNennbetrag;
    }

    /**
     * @param pvPfandrechtNennbetrag the pfandrechtNennbetrag to set
     */
    public void setPfandrechtNennbetrag(String pvPfandrechtNennbetrag) {
        this.ivPfandrechtNennbetrag = pvPfandrechtNennbetrag;
    }

    /**
     * @return the pfandrechtVerfuegungsbetrag
     */
    public String getPfandrechtVerfuegungsbetrag() {
        return this.ivPfandrechtVerfuegungsbetrag;
    }

    /**
     * @param pvPfandrechtVerfuegungsbetrag the pfandrechtVerfuegungsbetrag to set
     */
    public void setPfandrechtVerfuegungsbetrag(String pvPfandrechtVerfuegungsbetrag) {
        this.ivPfandrechtVerfuegungsbetrag = pvPfandrechtVerfuegungsbetrag;
    }

    /**
     * @return the originalsicherheitenanteil
     */
    public String getOriginalsicherheitenanteil() {
        return this.ivOriginalsicherheitenanteil;
    }

    /**
     * @param pvOriginalsicherheitenanteil the originalsicherheitenanteil to set
     */
    public void setOriginalsicherheitenanteil(String pvOriginalsicherheitenanteil) {
        this.ivOriginalsicherheitenanteil = pvOriginalsicherheitenanteil;
    }

    /**
     * @return the bemerkungenOriginalsicherheiten
     */
    public String getBemerkungenOriginalsicherheiten() {
        return this.ivBemerkungenOriginalsicherheiten;
    }

    /**
     * @param pvBemerkungenOriginalsicherheiten the bemerkungenOriginalsicherheiten to set
     */
    public void setBemerkungenOriginalsicherheiten(String pvBemerkungenOriginalsicherheiten) {
        this.ivBemerkungenOriginalsicherheiten = pvBemerkungenOriginalsicherheiten.replace("#", ";");
    }

    /**
     * @return the pfandrechtRechtlicherGrund
     */
    public String getPfandrechtRechtlicherGrund() {
        return this.ivPfandrechtRechtlicherGrund;
    }

    /**
     * @param pvPfandrechtRechtlicherGrund the pfandrechtRechtlicherGrund to set
     */
    public void setPfandrechtRechtlicherGrund(String pvPfandrechtRechtlicherGrund) {
        this.ivPfandrechtRechtlicherGrund = pvPfandrechtRechtlicherGrund;
    }

    /**
     * @return the grundbuchnummer
     */
    public String getGrundbuchnummer() {
        return this.ivGrundbuchnummer;
    }

    /**
     * @param pvGrundbuchnummer the grundbuchnummer to set
     */
    public void setGrundbuchnummer(String pvGrundbuchnummer) {
        this.ivGrundbuchnummer = pvGrundbuchnummer;
    }

    /**
     * @return the grundbuchWaehrung
     */
    public String getGrundbuchWaehrung() {
        return this.ivGrundbuchWaehrung;
    }

    /**
     * @param pvGrundbuchWaehrung the grundbuchWaehrung to set
     */
    public void setGrundbuchWaehrung(String pvGrundbuchWaehrung) {
        this.ivGrundbuchWaehrung = pvGrundbuchWaehrung;
    }

    /**
     * @return the grundbuchabteilung
     */
    public String getGrundbuchabteilung() {
        return this.ivGrundbuchabteilung;
    }

    /**
     * @param pvGrundbuchabteilung the grundbuchabteilung to set
     */
    public void setGrundbuchabteilung(String pvGrundbuchabteilung) {
        this.ivGrundbuchabteilung = pvGrundbuchabteilung;
    }

    /**
     * @return the eingetragenAm
     */
    public String getEingetragenAm() {
        return this.ivEingetragenAm;
    }

    /**
     * @param pvEingetragenAm the eingetragenAm to set
     */
    public void setEingetragenAm(String pvEingetragenAm) {
        this.ivEingetragenAm = pvEingetragenAm;
    }

    /**
     * @return the grundbuchblattnummer
     */
    public String getGrundbuchblattnummer() {
        return this.ivGrundbuchblattnummer;
    }

    /**
     * @param pvGrundbuchblattnummer the grundbuchblattnummer to set
     */
    public void setGrundbuchblattnummer(String pvGrundbuchblattnummer) {
        this.ivGrundbuchblattnummer = pvGrundbuchblattnummer;
    }

    /**
     * @return the bestandsverzeichnisnummer
     */
    public String getBestandsverzeichnisnummer() {
        return this.ivBestandsverzeichnisnummer;
    }

    /**
     * @param pvBestandsverzeichnisnummer the bestandsverzeichnisnummer to set
     */
    public void setBestandsverzeichnisnummer(String pvBestandsverzeichnisnummer) {
        this.ivBestandsverzeichnisnummer = pvBestandsverzeichnisnummer;
    }

    /**
     * @return the laufendeNummer
     */
    public String getLaufendeNummer() {
        return this.ivLaufendeNummer;
    }

    /**
     * @param pvLaufendeNummer the laufendeNummer to set
     */
    public void setLaufendeNummer(String pvLaufendeNummer) {
        this.ivLaufendeNummer = pvLaufendeNummer;
    }

    /**
     * @return the gesamtgrundschuld
     */
    public String getGesamtgrundschuld() {
        return this.ivGesamtgrundschuld;
    }

    /**
     * @param pvGesamtgrundschuld the gesamtgrundschuld to set
     */
    public void setGesamtgrundschuld(String pvGesamtgrundschuld) {
        this.ivGesamtgrundschuld = pvGesamtgrundschuld;
    }
    
    /**
     * Fuegt Grundbuchdaten der Liste hinzu 
     * @param pvGrundbuchDaten 
     */
    public void addGrundbuchDaten(GrundbuchDaten pvGrundbuchDaten)
    {
        this.ivListeGrundbuchDaten.add(pvGrundbuchDaten);
    }
    
    /**
     * @return the listeGrundbuchDaten
     */
    public LinkedList<GrundbuchDaten> getListeGrundbuchDaten() {
        return this.ivListeGrundbuchDaten;
    }

    /**
     * @param pvListeGrundbuchDaten the listeGrundbuchDaten to set
     */
    public void setListeGrundbuchDaten(LinkedList<GrundbuchDaten> pvListeGrundbuchDaten) {
        this.ivListeGrundbuchDaten = pvListeGrundbuchDaten;
    }

    /**
     * Setzen eines Wertes
     * @param pvLfd 
     * @param pvWert 
     */
    public void setzeWert(int pvLfd, String pvWert)
    {
        System.out.println("KreditDeutscheHypo - setzeWert - pvLfd: " + pvLfd + " pvWert: " + pvWert);
        switch (pvLfd)
        {
            case 0:
                // Laufende Nummer
                break;
            case 1: // Aktenzeichen
                this.setKontonummer(pvWert);
                break;
            case 2: 
                // Externer Key
                break;
            case 3:
                this.setZusagedatum(pvWert);
                break;
            case 4:
                if (pvWert.equals("DM"))
                {
                    pvWert = "DEM";
                }
                this.setVertragswaehrung(pvWert);
                break;
            case 5:
                // Wiederholung Vertragswaehrung
                break;
            case 6:
                this.setSicherheitennummer(pvWert);
                break;
            case 7:
                if (pvWert.equals("DM"))
                {
                    pvWert = "DEM";
                }
                this.setWaehrung(pvWert);
                break;
            case 8:
                this.setDatumRechtlicherGrund(pvWert);
                break;
            case 9:
                this.setNennbetrag(pvWert.replace(".", "").replace(",", "."));
                break;
            case 10:
                this.setVerfuegungsbetrag(pvWert.replace(".", "").replace(",", "."));
                break;
            case 11:
                this.setBemerkungen(pvWert);
                break;
            case 12:
                this.setRechtsgrundlage(pvWert);
                break;
            case 13:
                this.setOriginalsicherheit(pvWert);
                break;
            case 14:
                this.setSicherheitenart(pvWert);
                break;
            case 15:
                if (pvWert.equals("DM"))
                {
                    pvWert = "DEM";
                }
                this.setSicherheitWaehrung(pvWert);
                break;
            case 16:
                this.setRechtlicherGrundVom(pvWert);
                break;
            case 17:
                this.setPfandrechtNennbetrag(pvWert.replace(".", "").replace(",", "."));
                break;
            case 18:
                this.setPfandrechtVerfuegungsbetrag(pvWert.replace(".", "").replace(",", "."));
                break;
            case 19:
                this.setBemerkungenOriginalsicherheiten(pvWert);
                break;
            case 20:
                this.setPfandrechtRechtlicherGrund(pvWert);
                break;
            case 21:
                this.setGrundbuchnummer(pvWert);
                break;
            case 22:
                if (pvWert.equals("DM"))
                {
                    pvWert = "DEM";
                }
                this.setGrundbuchWaehrung(pvWert);
                break;
            case 23:
                // this.setGrundbuchabteilung(pvWert);
                break;
             case 24:
                this.setBestandsverzeichnisnummer(pvWert);
                break;
            case 25:
                this.setLaufendeNummer(pvWert);
                break;
            case 26:
                this.setGrundbuchblattnummer(pvWert);
                break;
           default:
              System.out.println("KreditDeuscheHypo: unbekannte Position: " + pvLfd + " Wert: " + pvWert);
        }      
    }

}
