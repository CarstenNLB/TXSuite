/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.FRISCO2TXS;

/**
 * @author tepperc
 *
 */
@Deprecated
public class Schiff extends ObjektDaten
{
    /**
     * SchiffsNr
     */
    private String ivSchiffsNr;
    
    /**
     * Schiffsname
     */
    private String ivSchiffsname;
    
    /**
     * IMO-Nr
     */
    private String ivImoNr;
    
    /**
     * Bemerkung DeckRegV (Sicherheit)
     */
    private String ivBemerkungDeckRegV;
    
    /**
     * Bermerkung DeckRegV (Finanzierung)
     */
    private String ivBemerkungDeckRegVFin;
    
    /**
     * Konstruktor - Initialisierung
     */
    public Schiff()
    {
        super();
        this.ivSchiffsNr = new String();
        this.ivSchiffsname = new String();
        this.ivImoNr = new String();
        this.ivBemerkungDeckRegV = new String();
        this.ivBemerkungDeckRegVFin = new String();
    }
    
    /**
     * @return the schiffsNr
     */
    public String getSchiffsNr() {
        return this.ivSchiffsNr;
    }

    /**
     * @param pvSchiffsNr the schiffsNr to set
     */
    public void setSchiffsNr(String pvSchiffsNr) {
        this.ivSchiffsNr = pvSchiffsNr;
    }

    /**
     * @return the schiffsname
     */
    public String getSchiffsname() {
        return this.ivSchiffsname;
    }

    /**
     * @param pvSchiffsname the schiffsname to set
     */
    public void setSchiffsname(String pvSchiffsname) {
        this.ivSchiffsname = pvSchiffsname;
    }

    /**
     * @return the imoNr
     */
    public String getImoNr() {
        return this.ivImoNr;
    }

    /**
     * @param pvImoNr the imoNr to set
     */
    public void setImoNr(String pvImoNr) {
        this.ivImoNr = pvImoNr;
    }

    /**
     * @return the bemerkungDeckRegV
     */
    public String getBemerkungDeckRegV() {
        return this.ivBemerkungDeckRegV;
    }

    /**
     * @param pvBemerkungDeckRegV the bemerkungDeckRegV to set
     */
    public void setBemerkungDeckRegV(String pvBemerkungDeckRegV) {
        this.ivBemerkungDeckRegV = pvBemerkungDeckRegV;
    }

    /**
     * @return the bemerkungDeckRegVFin
     */
    public String getBemerkungDeckRegVFin() {
        return this.ivBemerkungDeckRegVFin;
    }

    /**
     * @param pvBemerkungDeckRegVFin the bemerkungDeckRegVFin to set
     */
    public void setBemerkungDeckRegVFin(String pvBemerkungDeckRegVFin) {
        this.ivBemerkungDeckRegVFin = pvBemerkungDeckRegVFin;
    }

    /**
     * Setzen eines Wertes
     * @param pvLfd 
     * @param pvWert 
     */
    public void setzeWert(int pvLfd, String pvWert)
    {
        //System.out.println("setzeWert - pvWert: " + pvWert);
        switch (pvLfd)
        {
            case 0:
                this.setObjektNr(pvWert);
                break;
            case 1:
                this.setKontonummer(pvWert);
                break;
            case 2:
                this.setFinanzierungsnummer(pvWert);
                break;
            case 3:
                this.setBeginnFinanzierung(pvWert);
                break;
            case 4:
                this.setSchiffsname(pvWert);
                break;
            case 5:
                this.setSchiffsNr(pvWert);
                break;
            case 6:
                this.setImoNr(pvWert);
                break;
            case 7:
                this.setBranchennummer(pvWert);
                break;
            case 8:
                this.setBeleihungswertAkt(pvWert.replace(",", "."));
                break;
            case 9:
                this.setWaehrungBeleihungswertAkt(pvWert);
                break;
            case 10:
                this.setBeleihungswertVom(pvWert);
                break;
            case 11:
                this.setBeleihungswertInd(pvWert.replace(",", "."));
                break;
            case 12:
                this.setWaehrungBeleihungswertInd(pvWert);
                break;
            case 13:
                this.setBeleihungswertIndVom(pvWert);
                break;
            case 14:
                this.setRegisterort(pvWert);
                break;
            case 15:
                this.setVerzeichnisblattNr(pvWert);
                break;
            case 16:
                this.setRegisterland(pvWert);
                break;
            case 17:
                this.setBlatt(pvWert);
                break;
            case 18:
                this.setRegistereintragsnr(pvWert);
                break;
            case 19:
                this.setAbteilung3(pvWert);
                break;
            case 20:
                this.setSicherheitenNr(pvWert);
                break;
            case 21:
                this.setWaehrungSicherheit(pvWert);
                break;
            case 22:
                this.setNennbetrag(pvWert.replace(",", "."));
                break;
            case 23:
                this.setVerfuegungsbetrag(pvWert.replace(",", "."));
                break;
            case 24:
                this.setFertigstellung(pvWert);
                break;
            case 25:
                this.setAuslieferung(pvWert);
                break;
            case 26:
                this.setWaehrungNennbetragFinanzierung(pvWert);
                break;
            case 27:
                this.setNennbetragFinanzierung(pvWert.replace(",", "."));                
                break; 
            case 28:
                this.setFaelligkeitFinanzierung(pvWert);
                break;
            case 29:
                this.setBemerkungDeckRegV(pvWert);
                break;
            case 30:
                this.setBemerkungDeckRegVFin(pvWert);
                break;
         default:
              System.out.println("Schiff: unbekannte Position: " + pvLfd + " Wert: " + pvWert);
        }      
    } 
 
    /**
     * Setzen eines Wertes
     * @param pvLfd 
     * @param pvWert 
     */
    /* public void setzeWert(int pvLfd, String pvWert)
    {
        System.out.println("setzeWert - pvWert: " + pvWert);
        switch (pvLfd)
        {
            case 0:
                this.setSchiffsNr(pvWert);
                break;
            case 1:
                this.setSchiffsname(pvWert);
                break;
            case 2:
                this.setKontonummer(pvWert);
                break;
            case 3:
                this.setLeitwaehrung(pvWert);
                break;
            case 4:
                this.setWaehrung(pvWert);
                break;
            case 5:
                this.setKontosaldoInd(pvWert);
                break;
            case 6:
                this.setKontosaldoAkt(pvWert);
                break;
            case 7:
                this.setKontosaldoIndEUR(pvWert);
                break;
            case 8:
                this.setKontosaldoAktEUR(pvWert);
                break;
            case 9:
                this.setErhoehungSaldo(pvWert);
                break;
            case 10:
                this.setNennbetrag(pvWert);
                break;
            case 11:
                this.setNennbetragEUR(pvWert);
                break;
            case 12:
                this.setBeleihungswertIndISO(pvWert);
                break;
            case 13:
                this.setBeleihungswertInd(pvWert);
                break;
            case 14:
                this.setBeleihungswertAktISO(pvWert);
                break;
            case 15:
                this.setBeleihungswertAkt(pvWert);
                break;
            case 16:
                this.setBeleihungswertIndEUR(pvWert);
                break;
            case 17:
                this.setBeleihungswertAktEUR(pvWert);
                break;
            case 18:
                this.setBeleihungswertIndGueltigAb(pvWert);
                break;
            case 19:
                this.setBeleihungswertAktGueltigAb(pvWert);
                break;
            case 20:
                this.setImoNr(pvWert);
                break;
            case 21:
                this.setSchiffstyp(pvWert);
                break;
            case 22:
                this.setSegment(pvWert);
                break;
            case 23:
                this.setLaufendeNrDR(pvWert);
                break;
            case 24:
                this.setSchiffDeckung(pvWert);
                break;
            case 25:
                this.setRegStaatHyp(pvWert);
                break;
            case 26:
                this.setRegOrtHyp(pvWert);
                break;
            case 27:
                this.setRegisterHypothekenBlatt(pvWert);
                break;
            case 28:
                this.setLfdNrAbt3(pvWert);
                break;
            case 29:
                this.setDinglicheSicherheitISO(pvWert);
                break;
            case 30:
                this.setDinglicheSicherheit(pvWert);
                break;
            case 31:
                this.setDinglicheSicherheitNetto(pvWert);
                break;
            case 32:
                this.setDinglicheSicherheitEUR(pvWert);
                break;
            case 33:
                this.setDinglicheSicherheitNettoEUR(pvWert);
                break;
            case 34:
                this.setRangfolge(pvWert);
                break;
            case 35:
                this.setRegStaatFlagge(pvWert);
                break;
            case 36:
                this.setRegOrtFlagge(pvWert);
                break;
            case 37:
                this.setGroe�enklasse(pvWert);
                break;
            case 38:
                this.setDeckMMAnw(pvWert);
                break;
            case 39:
                this.setDeckRelAkt(pvWert);
                break;
            case 40:
                this.setDeckMMAkt(pvWert);
                break;
            case 41:
                this.setDeckMMAbweichend(pvWert);
                break;
            case 42:
                this.setDeckRegAktion(pvWert);
                break;
            case 43:
                this.setStatus(pvWert);
                break;
            case 44:
                this.setAnwNr(pvWert);
                break;
            case 45:
                this.setUschl(pvWert);
                break;
            case 46:
                this.setAp(pvWert);
                break;
            case 47:
                this.setProduktNr(pvWert);
                break;
            case 48:
                this.setObjektNr(pvWert);
                break;
            case 49:
                this.setKontrahentenNr(pvWert);
                break;
            case 50:
                this.setBuergenNr(pvWert);
                break;
            case 51:
                this.setBuergenNameZ1(pvWert);
                break;
            case 52:
                this.setBuergenNameZ2(pvWert);
                break;
            case 53:
                this.setKusyma(pvWert);
                break;
            case 54:
                this.setIsin(pvWert);
                break;
            case 55:
                this.setKontoMM(pvWert);
                break;
            case 56:
                this.setRueckstandBetr(pvWert);
                break;
            case 57:
                this.setRueckstandEUR(pvWert);
                break;
            case 58:
                this.setZinsanpassungAm(pvWert);
                break;
            case 59:
                this.setMargenbindungBis(pvWert);
                break;
            case 60:
                this.setKontozustand(pvWert);
                break;
            case 61:
                this.setZinsart(pvWert);
                break;
            case 62:
                this.setZinssatz(pvWert);
                break;
            case 63:
                this.setBeginnGesch(pvWert);
                break;
            case 64:
                this.setEndeGesch(pvWert);
                break;
            case 65:
                this.setGueltigAb(pvWert);
                break;
            case 66:
                this.setGueltigBis(pvWert);
                break;
            case 67:
                this.setSicherMM(pvWert);
                break;
          default:
              System.out.println("Schiff: unbekannte Position: " + pvLfd + " Wert: " + pvWert);
        }      
    } */

}
