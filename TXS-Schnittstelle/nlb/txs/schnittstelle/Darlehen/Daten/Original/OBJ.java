/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen.Daten.Original;

/**
 * @author tepperc
 *
 */
public class OBJ 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;

    /**
     * Kundennummer
     */
    private String ivOkunr;
    
    /**
     * Kusy achtstellig 
     */
    private String ivOk;
    
    /**
     * Datum achtstellig OB-Einrichtungsdatum
     */                                        
    private String ivOein;
    
    /**
     * Datum achtstellig OB-Änderungsdatum 
     */
    private String ivOaen;
    
    /**
     * Datum achtstellig Objekt-Eröffnung 
     */                                       
    private String ivOer;
          
    /**
     * Schlüssel Objektzusatzangaben                                       
     */
    private String ivOszu;
    
    /**
     * Objektzusatzangaben
     */                                       
    private String ivOzu;
    
    /**
     * Art des Objektes
     */
    private String ivOao;
    
    /**
     * Schlüssel Mahnung 
     */                                        
    private String ivOm;
    
    /**
     * Datum achtstellig Mahnsperre/Stundung
     */
    private String ivOdm;
    
    /**
     * Kundenbetreuende Stelle
     */
    private String ivOks;
    
    /**
     * Anzahl Wohnungseinheiten 
     */
    private String ivOwe;
    
    /**
     * PLZ Objekt
     */
    private String ivOp;
    
    /**
     * Ort Objekt
     */
    private String ivOo;
    
    /**
     * Straße Objekt
     */
    private String ivOs;
    
    /**
     * Grundbuchnummer
     */
    private String ivOgr;
    
    /**
     * Grundbuchband 
     */
    private String ivOba;
    
    /**
     * Grundbuchblatt
     */
    private String ivObt;
    
    /**
     * Beleihungsgebiet 
     */
    private String ivObg;    
    
    /**
     * Kontruktor
     */
    public OBJ()
    {
        ivKopf = new DWHKOPF();
        this.ivOkunr = new String();
        this.ivOk = new String();
        this.ivOein = new String();
        this.ivOaen = new String();
        this.ivOer = new String();
        this.ivOszu = new String();
        this.ivOzu = new String();
        this.ivOao = new String();
        this.ivOm = new String();
        this.ivOdm = new String();
        this.ivOks = new String();
        this.ivOwe = new String();
        this.ivOp = new String();
        this.ivOo = new String(); 
        this.ivOs = new String();
        this.ivOgr = new String();
        this.ivOba = new String();
        this.ivObt = new String();
        this.ivObg = new String();    
    }
    
    /**
     * @return the kopf
     */
    public DWHKOPF getKopf() {
        return this.ivKopf;
    }
    
    /**
     * @param pvKopf the kopf to set
     */
    public void setKopf(DWHKOPF pvKopf) {
        this.ivKopf = pvKopf;
    }

    /**
     * @return the sOkunr
     */
    public String getsOkunr() {
        return this.ivOkunr;
    }

    /**
     * @param pvSOkunr the sOkunr to set
     */
    public void setsOkunr(String pvSOkunr) {
        this.ivOkunr = pvSOkunr;
    }

    /**
     * @return the sOk
     */
    public String getsOk() {
        return this.ivOk;
    }

    /**
     * @param pvSOk the sOk to set
     */
    public void setsOk(String pvSOk) {
        this.ivOk = pvSOk;
    }

    /**
     * @return the sOein
     */
    public String getsOein() {
        return this.ivOein;
    }

    /**
     * @param pvSOein the sOein to set
     */
    public void setsOein(String pvSOein) {
        this.ivOein = pvSOein;
    }

    /**
     * @return the sOaen
     */
    public String getsOaen() {
        return this.ivOaen;
    }

    /**
     * @param pvSOaen the sOaen to set
     */
    public void setsOaen(String pvSOaen) {
        this.ivOaen = pvSOaen;
    }

    /**
     * @return the sOer
     */
    public String getsOer() {
        return this.ivOer;
    }

    /**
     * @param pvSOer the sOer to set
     */
    public void setsOer(String pvSOer) {
        this.ivOer = pvSOer;
    }

    /**
     * @return the sOszu
     */
    public String getsOszu() {
        return this.ivOszu;
    }

    /**
     * @param pvSOszu the sOszu to set
     */
    public void setsOszu(String pvSOszu) {
        this.ivOszu = pvSOszu;
    }

    /**
     * @return the sOzu
     */
    public String getsOzu() {
        return this.ivOzu;
    }

    /**
     * @param pvSOzu the sOzu to set
     */
    public void setsOzu(String pvSOzu) {
        this.ivOzu = pvSOzu;
    }

    /**
     * @return the sOao
     */
    public String getsOao() {
        return this.ivOao;
    }

    /**
     * @param pvSOao the sOao to set
     */
    public void setsOao(String pvSOao) {
        this.ivOao = pvSOao;
    }

    /**
     * @return the sOm
     */
    public String getsOm() {
        return this.ivOm;
    }

    /**
     * @param pvSOm the sOm to set
     */
    public void setsOm(String pvSOm) {
        this.ivOm = pvSOm;
    }

    /**
     * @return the sOdm
     */
    public String getsOdm() {
        return this.ivOdm;
    }

    /**
     * @param pvSOdm the sOdm to set
     */
    public void setsOdm(String pvSOdm) {
        this.ivOdm = pvSOdm;
    }

    /**
     * @return the sOks
     */
    public String getsOks() {
        return this.ivOks;
    }

    /**
     * @param pvSOks the sOks to set
     */
    public void setsOks(String pvSOks) {
        this.ivOks = pvSOks;
    }

    /**
     * @return the sOwe
     */
    public String getsOwe() {
        return this.ivOwe;
    }

    /**
     * @param pvSOwe the sOwe to set
     */
    public void setsOwe(String pvSOwe) {
        this.ivOwe = pvSOwe;
    }

    /**
     * @return the sOp
     */
    public String getsOp() {
        return this.ivOp;
    }

    /**
     * @param pvSOp the sOp to set
     */
    public void setsOp(String pvSOp) {
        this.ivOp = pvSOp;
    }

    /**
     * @return the sOo
     */
    public String getsOo() {
        return this.ivOo;
    }

    /**
     * @param pvSOo the sOo to set
     */
    public void setsOo(String pvSOo) {
        this.ivOo = pvSOo;
    }

    /**
     * @return the sOs
     */
    public String getsOs() {
        return this.ivOs;
    }

    /**
     * @param pvSOs the sOs to set
     */
    public void setsOs(String pvSOs) {
        this.ivOs = pvSOs;
    }

    /**
     * @return the sOgr
     */
    public String getsOgr() {
        return this.ivOgr;
    }

    /**
     * @param pvSOgr the sOgr to set
     */
    public void setsOgr(String pvSOgr) {
        this.ivOgr = pvSOgr;
    }

    /**
     * @return the sOba
     */
    public String getsOba() {
        return this.ivOba;
    }

    /**
     * @param pvSOba the sOba to set
     */
    public void setsOba(String pvSOba) {
        this.ivOba = pvSOba;
    }

    /**
     * @return the sObt
     */
    public String getsObt() {
        return this.ivObt;
    }

    /**
     * @param pvSObt the sObt to set
     */
    public void setsObt(String pvSObt) {
        this.ivObt = pvSObt;
    }

    /**
     * @return the sObg
     */
    public String getsObg() {
        return this.ivObg;
    }

    /**
     * @param pvSObg the sObg to set
     */
    public void setsObg(String pvSObg) {
        this.ivObg = pvSObg;
    }

    /**
     * Setzt einen Wert des OBJ-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setOBJ(int pvPos, String pvWert)
    {
        switch (pvPos)
        {
          case 4:
              this.getKopf().setsDwhtdb(pvWert);
              break;
          case 5:
              this.getKopf().setsDwheart(pvWert);
              break;
          case 6:
              this.getKopf().setsDwhart(pvWert);
              break;
          case 7:
              this.getKopf().setsDwhtyp(pvWert);
              break;
          case 8:
              this.getKopf().setsDwhres10(pvWert);
              break;
          case 9:
              this.getKopf().setsDwhres11(pvWert);
              break;
          case 10:
              this.getKopf().setsDwhres12(pvWert);
              break;
          case 11:
              this.setsOkunr(pvWert);
              break;
          case 12:
              this.setsOk(pvWert);
              break;
          case 13:
              this.setsOein(pvWert);
              break;
          case 14:
              this.setsOaen(pvWert);
              break;
          case 15:
              this.setsOer(pvWert);
              break;
          case 16:
              this.setsOszu(pvWert);
              break;
          case 17:
              this.setsOzu(pvWert);
              break;           
          case 18:
              this.setsOao(pvWert);
              break;
          case 19:
              this.setsOm(pvWert);
              break;
          case 20:
              this.setsOdm(pvWert);
              break;
          case 21:
              this.setsOks(pvWert);
              break;
          case 22:
              this.setsOwe(pvWert);
              break;
          case 23:
              this.setsOp(pvWert);
              break;
          case 24:
              this.setsOo(pvWert);
              break;
          case 25:
              this.setsOs(pvWert);
              break;
          case 26:
              this.setsOgr(pvWert);
              break;
          case 27:
              this.setsOba(pvWert);
              break;
          case 28:
              this.setsObt(pvWert);
              break;
          case 29:
              this.setsObg(pvWert);
              break;
          default:
              System.out.println("OBJ: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 

}
