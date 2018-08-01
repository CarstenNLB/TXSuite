/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.FRISCO2TXS;

import java.util.ArrayList;

/**
 * @author tepperc
 *
 */
public class Flugzeug extends ObjektDaten
{
    /**
     * Kennzeichen
     */
    private String ivKennzeichen;
    
    /**
     * FlugzeugnummerVO
     */
    private String ivFlugzeugnummerVO;
    
    /**
     * SeriennrMSN
     */
    private String ivSeriennrMSN;
    
    /**
     * Flugzeugmuster
     */
    private String ivFlugzeugmuster;
    
    /**
     * Flugzeugvariante
     */
    private String ivFlugzeugvariante;
    
    /**
     * Flugzeugtyp
     */
    private String ivFlugzeugtyp;
    
    /**
     * Registerland des Flugzeuges
     */
    private String ivFlugzeugRegisterland;
    
    /**
     * Triebwerktyp
     */
    private String ivTriebwerktyp;
    
    /**
     * Triebwerkvariante
     */
    private String ivTriebwerkvariante;
    
    /**
     * Seriennummern
     */
    private ArrayList<String> ivSeriennummern;
    
    /**
     * FinanzObjektNr // Hier richtig, oder besser in Klasse 'ObjektDaten' !!!
     */
    private String ivFinanzObjektNr;
    
    /**
     * Konstruktor - Initialisierung
     */
    public Flugzeug()
    {
        super();
        ivKennzeichen = new String();
        ivFlugzeugnummerVO = new String();
        ivSeriennrMSN = new String();
        ivFlugzeugmuster = new String();
        ivFlugzeugvariante = new String();
        ivFlugzeugtyp = new String();
        ivFlugzeugRegisterland = new String();
        ivTriebwerktyp = new String();
        ivTriebwerkvariante = new String();
        ivSeriennummern = new ArrayList<String>();
        ivFinanzObjektNr = new String();
    }

    /**
     * @return the kennzeichen
     */
    public String getKennzeichen() {
        return this.ivKennzeichen;
    }

    /**
     * @param pvKennzeichen the kennzeichen to set
     */
    public void setKennzeichen(String pvKennzeichen) {
        this.ivKennzeichen = pvKennzeichen;
    }

    /**
     * @return the flugzeugnummerVO
     */
    public String getFlugzeugnummerVO() {
        return this.ivFlugzeugnummerVO;
    }

    /**
     * @param pvFlugzeugnummerVO the flugzeugnummerVO to set
     */
    public void setFlugzeugnummerVO(String pvFlugzeugnummerVO) {
        this.ivFlugzeugnummerVO = pvFlugzeugnummerVO;
    }

    /**
     * @return the seriennrMSN
     */
    public String getSeriennrMSN() {
        return this.ivSeriennrMSN;
    }

    /**
     * @param pvSeriennrMSN the seriennrMSN to set
     */
    public void setSeriennrMSN(String pvSeriennrMSN) {
        this.ivSeriennrMSN = pvSeriennrMSN;
    }

    /**
     * @return the flugzeugmuster
     */
    public String getFlugzeugmuster() {
        return this.ivFlugzeugmuster;
    }

    /**
     * @param pvFlugzeugmuster the flugzeugmuster to set
     */
    public void setFlugzeugmuster(String pvFlugzeugmuster) {
        this.ivFlugzeugmuster = pvFlugzeugmuster;
    }

    /**
     * @return the flugzeugvariante
     */
    public String getFlugzeugvariante() {
        return this.ivFlugzeugvariante;
    }

    /**
     * @param pvFlugzeugvariante the flugzeugvariante to set
     */
    public void setFlugzeugvariante(String pvFlugzeugvariante) {
        this.ivFlugzeugvariante = pvFlugzeugvariante;
    }

    /**
     * @return the flugzeugtyp
     */
    public String getFlugzeugtyp() {
        return this.ivFlugzeugtyp;
    }

    /**
     * @param pvFlugzeugtyp the flugzeugtyp to set
     */
    public void setFlugzeugtyp(String pvFlugzeugtyp) {
        this.ivFlugzeugtyp = pvFlugzeugtyp;
    }

    /**
     * @return the flugzeugRegisterland
     */
    public String getFlugzeugRegisterland() {
        return this.ivFlugzeugRegisterland;
    }

    /**
     * @param pvFlugzeugRegisterland the flugzeugRegisterland to set
     */
    public void setFlugzeugRegisterland(String pvFlugzeugRegisterland) {
        this.ivFlugzeugRegisterland = pvFlugzeugRegisterland;
    }

    /**
     * @return the triebwerktyp
     */
    public String getTriebwerktyp() {
        return this.ivTriebwerktyp;
    }

    /**
     * @param pvTriebwerktyp the triebwerktyp to set
     */
    public void setTriebwerktyp(String pvTriebwerktyp) {
        this.ivTriebwerktyp = pvTriebwerktyp;
    }

    /**
     * @return the triebwerkvariante
     */
    public String getTriebwerkvariante() {
        return this.ivTriebwerkvariante;
    }

    /**
     * @param pvTriebwerkvariante the triebwerkvariante to set
     */
    public void setTriebwerkvariante(String pvTriebwerkvariante) {
        this.ivTriebwerkvariante = pvTriebwerkvariante;
    }

    /**
     * @return the seriennummern
     */
    public ArrayList<String> getSeriennummern() {
        return this.ivSeriennummern;
    }

    /**
     * @param pvSeriennummern the seriennummern to set
     */
    public void setSeriennummern(ArrayList<String> pvSeriennummern) {
        this.ivSeriennummern = pvSeriennummern;
    }
      
    /**
     * @return the finanzObjektNr
     */
    public String getFinanzObjektNr() {
        return this.ivFinanzObjektNr;
    }

    /**
     * @param pvFinanzObjektNr the finanzObjektNr to set
     */
    public void setFinanzObjektNr(String pvFinanzObjektNr) {
        this.ivFinanzObjektNr = pvFinanzObjektNr;
    }

    /**
     * Setzen eines Wertes
     * @param pvLfd 
     * @param pvWert 
     */
    public void setzeWert(int pvLfd, String pvWert)
    {
        System.out.println("setzeWert - pvLfd: " + pvLfd + " pvWert: " + pvWert);
        switch (pvLfd)
        {
            case 0:
                this.setObjektNr(pvWert.substring(pvWert.length() - 11));
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
                this.setNennbetragFinanzierung(pvWert);
                break;
            case 5:
                this.setWaehrungNennbetragFinanzierung(pvWert);
                break;
            case 6:
                this.setFaelligkeitFinanzierung(pvWert);
                break;
            case 7:
                this.setKennzeichen(pvWert);
                break;
            case 8:
                this.setFlugzeugnummerVO(pvWert);
                break;
            case 9:
                this.setSeriennrMSN(pvWert);
                break;
            case 10:
                this.setBranchennummer(pvWert);
                break;
            case 11:
                this.setFlugzeugmuster(pvWert);
                break;
            case 12:
                this.setFlugzeugvariante(pvWert);
                break;
            case 13:
                this.setFlugzeugtyp(pvWert);
                break;
            case 14:
                this.setFlugzeugRegisterland(pvWert);
                break;
            case 15:
                this.setBeleihungswertAkt(pvWert.replace(",", "."));
                break;
            case 16:
                this.setBeleihungswertVom(pvWert);
                break;
            case 17:
                this.setWaehrungBeleihungswertAkt(pvWert);
                break;
            case 18:
                this.setFertigstellung(pvWert);
                break;
            case 19:
                this.setAuslieferung(pvWert);
                break;
            case 20:
                // Finanz Objekt Nr
                this.setFinanzObjektNr(pvWert);
                break;
            case 21:
                this.setBeleihungswertInd(pvWert.replace(",", "."));
                break;
            case 22:
                this.setWaehrungBeleihungswertInd(pvWert);
                break;
            case 23:
                this.setVerzeichnisblattNr(pvWert);
                break;
            case 24:
                this.setRegisterland(pvWert);
                break;
            case 25:
                this.setRegisterort(pvWert);
                break;
            case 26:
                this.setBlatt(pvWert);
                break;
            case 27:
                this.setRegistereintragsnr(pvWert);
                break;
            case 28:
                this.setRang(pvWert);
                break;
            case 29:
                if (!pvWert.equals("n.r."))
                {
                  this.setAbteilung3(pvWert);
                }
                break;
            case 30:
                this.setWaehrungSicherheit(pvWert);
                break;
            case 31:
                this.setNennbetrag(pvWert.replace(",", "."));
                break;
            case 32:
                this.setVerfuegungsbetrag(pvWert.replace(",", "."));
                break;
            case 33:
                this.setTriebwerktyp(pvWert);
                break;
            case 34:
                this.setTriebwerkvariante(pvWert);
                break;
            case 35:
                // Seriennummer 1
                if (!pvWert.isEmpty())
                {
                  this.ivSeriennummern.add(pvWert);
                }
                break;
            case 36:
                // Seriennummer 2
                if (!pvWert.isEmpty())
                {
                  this.ivSeriennummern.add(pvWert);
                }
                break;
            case 37:
                // Seriennummer 3
                if (!pvWert.isEmpty())
                {
                  this.ivSeriennummern.add(pvWert);
                }
                break;
            case 38:
                // Seriennummer 4
                if (!pvWert.isEmpty())
                {
                  this.ivSeriennummern.add(pvWert);
                }
                break;
           default:
              System.out.println("Flugzeug: unbekannte Position: " + pvLfd + " Wert: " + pvWert);
        }      
    }

}
