/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;


/**
 * @author tepperc
 *
 */
public class TXSPersonenDaten implements TXSTransaktion
{
    /**
     * 
     */
    private String ivAkqdat;
    
    /**
     * 
     */
    private String ivBankmit;
    
    /**
     * 
     */
    private String ivBic;
    
    /**
     * 
     */
    private String ivBlz;
    
    /**
     * 
     */
    private String ivBon;
    
    /**
     * 
     */
    private String ivBranche;
    
    /**
     * 
     */
    private String ivEbesch;
    
    /**
     * 
     */
    private String ivEinsolv;
    
    /**
     * 
     */
    private String ivErgeb;
    
    /**
     * 
     */
    private String ivErgebdat;
    
    /**
     * 
     */
    private String ivEstatus;
    
    /**
     * 
     */
    private String ivFax;
    
    /**
     * 
     */
    private String ivFstand;
    
    /**
     * 
     */
    private String ivGebdat;
    
    /**
     * 
     */
    private String ivGebiet;
        
    /**
     * 
     */
    private String ivGstand;
    
    /**
     * 
     */
    private String ivHausbes;
    
    /**
     * 
     */
    private String ivHausnr;
       
    /**
     * 
     */
    private String ivHreg;
    
    
    /**
     * 
     */
    private String ivIban;
    
    /**
     * 
     */
    private String ivKnebez;
        
    /**
     * 
     */
    private String ivKnenr;
        
    /**
     * 
     */
    private String ivKoerper;
        
    /**
     * 
     */
    private String ivKonzern;
    
    /**
     * 
     */
    private String ivKreditvers;
    
    /**
     * 
     */
    private String ivKwuerd;
    
    /**
     * 
     */
    private String ivLand;
        
    /**
     * 
     */
    private String ivLimit;
    
    /**
     * 
     */
    private String ivLimitdat;
    
    /**
     * 
     */
    private String ivLimitinan;
    
    /**
     * 
     */
    private String ivMail;
    
    /**
     * 
     */
    private String ivMieter;
    
    /**
     * 
     */
    private String ivMitarb;
    
    /**
     * 
     */
    private String ivNat;
    
    /**
     * 
     */
    private String ivNetto;
    
    /**
     * 
     */
    private String ivNettodat;
    
    /**
     * 
     */
    private String ivNettoverf;
    
    /**
     * 
     */
    private String ivNname;
        
    /**
     * 
     */
    private String ivOrt;
        
    /**
     * 
     */
    private String ivPlz;
    
   /**
     * 
     */
    private String ivRatfitch;   
    
    /**
     * 
     */
    private String ivRatfitchk;
       
    /**
     * 
     */
    private String ivRatint;
   
    /**
     * 
     */
    private String ivRatinttxt;
        
    /**
     * 
     */
    private String ivRatmoody;
       
    /**
     * 
     */
    private String ivRatmoodyk;
       
    /**
     * 
     */
    private String ivRatsp;
        
    /**
     * 
     */
    private String ivRatspk;
       
    /**
     * 
     */
    private String ivRform;
    
    /**
     * 
     */
    private String ivRgruppe;
    
    /**
     * 
     */
    private String ivStr;
       
    /**
     * 
     */
    private String ivTelgesch;
    
    /**
     * 
     */
    private String ivTelmob;
    
    /**
     * 
     */
    private String ivTelpriv;
    
    /**
     * 
     */
    private String ivText;
    
    /**
     * 
     */
    private String ivTitel;
    
    /**
     * 
     */
    private String ivUmsatz;
    
    /**
     * 
     */
    private String ivVname;
       
    /**
     * 
     */
    private String ivWhrg;

  /**
   *
   */
  private String ivKusyma;

  /**
   *
   */
  private String ivKugr;

  /**
   *
   */
  private String ivRatingID;

  /**
   *
   */
  private String ivRatingMaster;

  /**
   *
   */
  private String ivRatingDatum;

  /**
   *
   */
  private String ivRatingToolID;

  /**
   *
   */
  private String ivRatingTool;

  /**
     *
     */
    public TXSPersonenDaten()
    {
      initTXSPersonenDaten();
    }

   /**
     * Initialisiert die Instanzvariablen mit leeren Strings
    */
    public void initTXSPersonenDaten()
    {
      this.ivAkqdat = new String();
      this.ivBankmit = new String();
      this.ivBic = new String();
      this.ivBlz = new String();
      this.ivBon = new String();
      this.ivBranche = new String();
      this.ivEbesch = new String();
      this.ivEinsolv = new String();
      this.ivErgeb = new String();
      this.ivErgebdat = new String();
      this.ivEstatus = new String();
      this.ivFax = new String();
      this.ivFstand = new String();
      this.ivGebdat = new String();
      this.ivGebiet = new String();
      this.ivGstand = new String();
      this.ivHausbes = new String();
      this.ivHausnr = new String();
      this.ivHreg = new String();
      this.ivIban = new String();
      this.ivKnebez = new String();
      this.ivKnenr = new String();
      this.ivKoerper = new String();
      this.ivKonzern = new String();
      this.ivKreditvers = new String();
      this.ivKwuerd = new String();
      this.ivLand = new String();
      this.ivLimit = new String();
      this.ivLimitdat = new String();
      this.ivLimitinan = new String();
      this.ivMail = new String();
      this.ivMieter = new String();
      this.ivMitarb = new String();
      this.ivNat = new String();
      this.ivNetto = new String();
      this.ivNettodat = new String();
      this.ivNettoverf = new String();
      this.ivNname = new String();
      this.ivOrt = new String();
      this.ivPlz = new String();
      this.ivRatfitch = new String();
      this.ivRatfitchk = new String();
      this.ivRatint = new String();
      this.ivRatinttxt = new String();
      this.ivRatmoody = new String();
      this.ivRatmoodyk = new String();
      this.ivRatsp = new String();
      this.ivRatspk = new String();
      this.ivRform = new String();
      this.ivRgruppe = new String();
      this.ivStr = new String();
      this.ivTelgesch = new String();
      this.ivTelmob = new String();
      this.ivTelpriv = new String();
      this.ivText = new String();
      this.ivTitel = new String();
      this.ivUmsatz = new String();
      this.ivVname = new String();
      this.ivWhrg = new String();
      this.ivKusyma = new String();
      this.ivKugr = new String();
      this.ivRatingID = new String();
      this.ivRatingMaster = new String();
      this.ivRatingDatum = new String();
      this.ivRatingToolID = new String();
      this.ivRatingTool = new String();
    }

    /**
     * @return the akqdat
     */
    public String getAkqdat() {
        return this.ivAkqdat;
    }

    /**
     * @param pvAkqdat the akqdat to set
     */
    public void setAkqdat(String pvAkqdat) {
        this.ivAkqdat = pvAkqdat;
    }

    /**
     * @return the bankmit
     */
    public String getBankmit() {
        return this.ivBankmit;
    }

    /**
     * @param pvBankmit the bankmit to set
     */
    public void setBankmit(String pvBankmit) {
        this.ivBankmit = pvBankmit;
    }

    /**
     * @return the bic
     */
    public String getBic() {
        return this.ivBic;
    }

    /**
     * @param pvBic the bic to set
     */
    public void setBic(String pvBic) {
        this.ivBic = pvBic;
    }

    /**
     * @return the blz
     */
    public String getBlz() {
        return this.ivBlz;
    }

    /**
     * @param pvBlz the blz to set
     */
    public void setBlz(String pvBlz) {
        this.ivBlz = pvBlz;
    }

    /**
     * @return the bon
     */
    public String getBon() {
        return this.ivBon;
    }

    /**
     * @param pvBon the bon to set
     */
    public void setBon(String pvBon) {
        this.ivBon = pvBon;
    }

    /**
     * @return the branche
     */
    public String getBranche() {
        return this.ivBranche;
    }

    /**
     * @param pvBranche the branche to set
     */
    public void setBranche(String pvBranche) {
        this.ivBranche = pvBranche;
    }

    /**
     * @return the ebesch
     */
    public String getEbesch() {
        return this.ivEbesch;
    }

    /**
     * @param pvEbesch the ebesch to set
     */
    public void setEbesch(String pvEbesch) {
        this.ivEbesch = pvEbesch;
    }

    /**
     * @return the einsolv
     */
    public String getEinsolv() {
        return this.ivEinsolv;
    }

    /**
     * @param pvEinsolv the einsolv to set
     */
    public void setEinsolv(String pvEinsolv) {
        this.ivEinsolv = pvEinsolv;
    }

    /**
     * @return the ergeb
     */
    public String getErgeb() {
        return this.ivErgeb;
    }

    /**
     * @param pvErgeb the ergeb to set
     */
    public void setErgeb(String pvErgeb) {
        this.ivErgeb = pvErgeb;
    }

    /**
     * @return the ergebdat
     */
    public String getErgebdat() {
        return this.ivErgebdat;
    }

    /**
     * @param pvErgebdat the ergebdat to set
     */
    public void setErgebdat(String pvErgebdat) {
        this.ivErgebdat = pvErgebdat;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return this.ivEstatus;
    }

    /**
     * @param pvEstatus the estatus to set
     */
    public void setEstatus(String pvEstatus) {
        this.ivEstatus = pvEstatus;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return this.ivFax;
    }

    /**
     * @param pvFax the fax to set
     */
    public void setFax(String pvFax) {
        this.ivFax = pvFax;
    }

    /**
     * @return the fstand
     */
    public String getFstand() {
        return this.ivFstand;
    }

    /**
     * @param pvFstand the fstand to set
     */
    public void setFstand(String pvFstand) {
        this.ivFstand = pvFstand;
    }

    /**
     * @return the gebdat
     */
    public String getGebdat() {
        return this.ivGebdat;
    }

    /**
     * @param pvGebdat the gebdat to set
     */
    public void setGebdat(String pvGebdat) {
        this.ivGebdat = pvGebdat;
    }

    /**
     * @return the gebiet
     */
    public String getGebiet() {
        return this.ivGebiet;
    }

    /**
     * @param pvGebiet the gebiet to set
     */
    public void setGebiet(String pvGebiet) {
        this.ivGebiet = pvGebiet;
    }

    /**
     * @return the gstand
     */
    public String getGstand() {
        return this.ivGstand;
    }

    /**
     * @param pvGstand the gstand to set
     */
    public void setGstand(String pvGstand) {
        this.ivGstand = pvGstand;
    }

    /**
     * @return the hausbes
     */
    public String getHausbes() {
        return this.ivHausbes;
    }

    /**
     * @param pvHausbes the hausbes to set
     */
    public void setHausbes(String pvHausbes) {
        this.ivHausbes = pvHausbes;
    }

    /**
     * @return the hausnr
     */
    public String getHausnr() {
        return this.ivHausnr;
    }

    /**
     * @param pvHausnr the hausnr to set
     */
    public void setHausnr(String pvHausnr) {
        this.ivHausnr = pvHausnr;
    }

    /**
     * @return the hreg
     */
    public String getHreg() {
        return this.ivHreg;
    }

    /**
     * @param pvHreg the hreg to set
     */
    public void setHreg(String pvHreg) {
        this.ivHreg = pvHreg;
    }

    /**
     * @return the iban
     */
    public String getIban() {
        return this.ivIban;
    }

    /**
     * @param pvIban the iban to set
     */
    public void setIban(String pvIban) {
        this.ivIban = pvIban;
    }

    /**
     * @return the knebez
     */
    public String getKnebez() {
        return this.ivKnebez;
    }

    /**
     * @param pvKnebez the knebez to set
     */
    public void setKnebez(String pvKnebez) {
        this.ivKnebez = pvKnebez;
    }

    /**
     * @return the knenr
     */
    public String getKnenr() {
        return this.ivKnenr;
    }

    /**
     * @param pvKnenr the knenr to set
     */
    public void setKnenr(String pvKnenr) {
        this.ivKnenr = pvKnenr;
    }

    /**
     * @return the koerper
     */
    public String getKoerper() {
        return this.ivKoerper;
    }

    /**
     * @param pvKoerper the koerper to set
     */
    public void setKoerper(String pvKoerper) {
        this.ivKoerper = pvKoerper;
    }

    /**
     * @return the konzern
     */
    public String getKonzern() {
        return this.ivKonzern;
    }

    /**
     * @param pvKonzern the konzern to set
     */
    public void setKonzern(String pvKonzern) {
        this.ivKonzern = pvKonzern;
    }

    /**
     * @return the kreditvers
     */
    public String getKreditvers() {
        return this.ivKreditvers;
    }

    /**
     * @param pvKreditvers the kreditvers to set
     */
    public void setKreditvers(String pvKreditvers) {
        this.ivKreditvers = pvKreditvers;
    }

    /**
     * @return the kwuerd
     */
    public String getKwuerd() {
        return this.ivKwuerd;
    }

    /**
     * @param pvKwuerd the kwuerd to set
     */
    public void setKwuerd(String pvKwuerd) {
        this.ivKwuerd = pvKwuerd;
    }

    /**
     * @return the land
     */
    public String getLand() {
        return this.ivLand;
    }

    /**
     * @param pvLand the land to set
     */
    public void setLand(String pvLand) {
        this.ivLand = pvLand;
    }

    /**
     * @return the limit
     */
    public String getLimit() {
        return this.ivLimit;
    }

    /**
     * @param pvLimit the limit to set
     */
    public void setLimit(String pvLimit) {
        this.ivLimit = pvLimit;
    }

    /**
     * @return the limitdat
     */
    public String getLimitdat() {
        return this.ivLimitdat;
    }

    /**
     * @param pvLimitdat the limitdat to set
     */
    public void setLimitdat(String pvLimitdat) {
        this.ivLimitdat = pvLimitdat;
    }

    /**
     * @return the limitinan
     */
    public String getLimitinan() {
        return this.ivLimitinan;
    }

    /**
     * @param pvLimitinan the limitinan to set
     */
    public void setLimitinan(String pvLimitinan) {
        this.ivLimitinan = pvLimitinan;
    }

    /**
     * @return the mail
     */
    public String getMail() {
        return this.ivMail;
    }

    /**
     * @param pvMail the mail to set
     */
    public void setMail(String pvMail) {
        this.ivMail = pvMail;
    }

    /**
     * @return the mieter
     */
    public String getMieter() {
        return this.ivMieter;
    }

    /**
     * @param pvMieter the mieter to set
     */
    public void setMieter(String pvMieter) {
        this.ivMieter = pvMieter;
    }

    /**
     * @return the mitarb
     */
    public String getMitarb() {
        return this.ivMitarb;
    }

    /**
     * @param pvMitarb the mitarb to set
     */
    public void setMitarb(String pvMitarb) {
        this.ivMitarb = pvMitarb;
    }

    /**
     * @return the nat
     */
    public String getNat() {
        return this.ivNat;
    }

    /**
     * @param pvNat the nat to set
     */
    public void setNat(String pvNat) {
        this.ivNat = pvNat;
    }

    /**
     * @return the netto
     */
    public String getNetto() {
        return this.ivNetto;
    }

    /**
     * @param pvNetto the netto to set
     */
    public void setNetto(String pvNetto) {
        this.ivNetto = pvNetto;
    }

    /**
     * @return the nettodat
     */
    public String getNettodat() {
        return this.ivNettodat;
    }

    /**
     * @param pvNettodat the nettodat to set
     */
    public void setNettodat(String pvNettodat) {
        this.ivNettodat = pvNettodat;
    }

    /**
     * @return the nettoverf
     */
    public String getNettoverf() {
        return this.ivNettoverf;
    }

    /**
     * @param pvNettoverf the nettoverf to set
     */
    public void setNettoverf(String pvNettoverf) {
        this.ivNettoverf = pvNettoverf;
    }

    /**
     * @return the nname
     */
    public String getNname() {
        return this.ivNname;
    }

    /**
     * @param pvNname the nname to set
     */
    public void setNname(String pvNname) {
        this.ivNname = pvNname;
    }

    /**
     * @return the ort
     */
    public String getOrt() {
        return this.ivOrt;
    }

    /**
     * @param pvOrt the ort to set
     */
    public void setOrt(String pvOrt) {
        this.ivOrt = pvOrt;
    }

    /**
     * @return the plz
     */
    public String getPlz() {
        return this.ivPlz;
    }

    /**
     * @param pvPlz the plz to set
     */
    public void setPlz(String pvPlz) {
        this.ivPlz = pvPlz;
    }

    /**
     * @return the ratfitch
     */
    public String getRatfitch() {
        return this.ivRatfitch;
    }

    /**
     * @param pvRatfitch the ratfitch to set
     */
    public void setRatfitch(String pvRatfitch) {
        this.ivRatfitch = pvRatfitch;
    }

    /**
     * @return the ratfitchk
     */
    public String getRatfitchk() {
        return this.ivRatfitchk;
    }

    /**
     * @param pvRatfitchk the ratfitchk to set
     */
    public void setRatfitchk(String pvRatfitchk) {
        this.ivRatfitchk = pvRatfitchk;
    }

    /**
     * @return the ratint
     */
    public String getRatint() {
        return this.ivRatint;
    }

    /**
     * @param pvRatint the ratint to set
     */
    public void setRatint(String pvRatint) {
        this.ivRatint = pvRatint;
    }

    /**
     * @return the ratinttxt
     */
    public String getRatinttxt() {
        return this.ivRatinttxt;
    }

    /**
     * @param pvRatinttxt the ratinttxt to set
     */
    public void setRatinttxt(String pvRatinttxt) {
        this.ivRatinttxt = pvRatinttxt;
    }

    /**
     * @return the ratmoody
     */
    public String getRatmoody() {
        return this.ivRatmoody;
    }

    /**
     * @param pvRatmoody the ratmoody to set
     */
    public void setRatmoody(String pvRatmoody) {
        this.ivRatmoody = pvRatmoody;
    }

    /**
     * @return the ratmoodyk
     */
    public String getRatmoodyk() {
        return this.ivRatmoodyk;
    }

    /**
     * @param pvRatmoodyk the ratmoodyk to set
     */
    public void setRatmoodyk(String pvRatmoodyk) {
        this.ivRatmoodyk = pvRatmoodyk;
    }

    /**
     * @return the ratsp
     */
    public String getRatsp() {
        return this.ivRatsp;
    }

    /**
     * @param pvRatsp the ratsp to set
     */
    public void setRatsp(String pvRatsp) {
        this.ivRatsp = pvRatsp;
    }

    /**
     * @return the ratspk
     */
    public String getRatspk() {
        return this.ivRatspk;
    }

    /**
     * @param pvRatspk the ratspk to set
     */
    public void setRatspk(String pvRatspk) {
        this.ivRatspk = pvRatspk;
    }

    /**
     * @return the rform
     */
    public String getRform() {
        return this.ivRform;
    }

    /**
     * @param pvRform the rform to set
     */
    public void setRform(String pvRform) {
        this.ivRform = pvRform;
    }

    /**
     * @return the rgruppe
     */
    public String getRgruppe() {
        return this.ivRgruppe;
    }

    /**
     * @param pvRgruppe the rgruppe to set
     */
    public void setRgruppe(String pvRgruppe) {
        this.ivRgruppe = pvRgruppe;
    }

    /**
     * @return the str
     */
    public String getStr() {
        return this.ivStr;
    }

    /**
     * @param pvStr the str to set
     */
    public void setStr(String pvStr) {
        this.ivStr = pvStr;
    }

    /**
     * @return the telgesch
     */
    public String getTelgesch() {
        return this.ivTelgesch;
    }

    /**
     * @param pvTelgesch the telgesch to set
     */
    public void setTelgesch(String pvTelgesch) {
        this.ivTelgesch = pvTelgesch;
    }

    /**
     * @return the telmob
     */
    public String getTelmob() {
        return this.ivTelmob;
    }

    /**
     * @param pvTelmob the telmob to set
     */
    public void setTelmob(String pvTelmob) {
        this.ivTelmob = pvTelmob;
    }

    /**
     * @return the telpriv
     */
    public String getTelpriv() {
        return this.ivTelpriv;
    }

    /**
     * @param pvTelpriv the telpriv to set
     */
    public void setTelpriv(String pvTelpriv) {
        this.ivTelpriv = pvTelpriv;
    }

    /**
     * @return the text
     */
    public String getText() {
        return this.ivText;
    }

    /**
     * @param pvText the text to set
     */
    public void setText(String pvText) {
        this.ivText = pvText;
    }

    /**
     * @return the titel
     */
    public String getTitel() {
        return this.ivTitel;
    }

    /**
     * @param pvTitel the titel to set
     */
    public void setTitel(String pvTitel) {
        this.ivTitel = pvTitel;
    }

    /**
     * @return the umsatz
     */
    public String getUmsatz() {
        return this.ivUmsatz;
    }

    /**
     * @param pvUmsatz the umsatz to set
     */
    public void setUmsatz(String pvUmsatz) {
        this.ivUmsatz = pvUmsatz;
    }

    /**
     * @return the vname
     */
    public String getVname() {
        return this.ivVname;
    }

    /**
     * @param pvVname the vname to set
     */
    public void setVname(String pvVname) {
        this.ivVname = pvVname;
    }

    /**
     * @return the whrg
     */
    public String getWhrg() {
        return this.ivWhrg;
    }

    /**
     * @param pvWhrg the whrg to set
     */
    public void setWhrg(String pvWhrg) {
        this.ivWhrg = pvWhrg;
    }

  public String getKusyma() {
    return ivKusyma;
  }

  public void setKusyma(String ivKusyma) {
    this.ivKusyma = ivKusyma;
  }

  public String getKugr() {
    return ivKugr;
  }

  public void setKugr(String ivKugr) {
    this.ivKugr = ivKugr;
  }

  public String getRatingID() {
    return ivRatingID;
  }

  public void setRatingID(String ivRatingID) {
    this.ivRatingID = ivRatingID;
  }

  public String getRatingMaster() {
    return ivRatingMaster;
  }

  public void setRatingMaster(String ivRatingMaster) {
    this.ivRatingMaster = ivRatingMaster;
  }

  public String getRatingDatum() {
    return ivRatingDatum;
  }

  public void setRatingDatum(String ivRatingDatum) {
    this.ivRatingDatum = ivRatingDatum;
  }

  public String getRatingToolID() {
    return ivRatingToolID;
  }

  public void setRatingToolID(String ivRatingToolID) {
    this.ivRatingToolID = ivRatingToolID;
  }

  public String getRatingTool() {
    return ivRatingTool;
  }

  public void setRatingTool(String ivRatingTool) {
    this.ivRatingTool = ivRatingTool;
  }

  /**
     * TXSPersonDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("      <txsi:persdaten ");
    }
    
    /**
     * TXSPersonDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
       StringBuffer lvHelpXML = new StringBuffer();
        
        lvHelpXML.append("nname=\"" + this.ivNname);

        if (this.ivGebiet.length() > 0)
        {
            lvHelpXML.append("\" gebiet=\"" + this.ivGebiet);
        }
        lvHelpXML.append("\" land=\"" + this.ivLand);
        if (this.ivVname.length() > 0)
        {
          lvHelpXML.append("\" vname=\"" + this.ivVname);
        }
        if (this.ivOrt.length() > 0)
        {
          lvHelpXML.append("\" ort=\"" + this.ivOrt);
        }
        if (this.ivPlz.length() > 0)
        {
          lvHelpXML.append("\" plz=\"" + this.ivPlz);
        }
        if (this.ivStr.length() > 0)
        {
          lvHelpXML.append("\" str=\"" + this.ivStr);
        }
      if (this.ivHausnr.length() > 0)
      {
        lvHelpXML.append("\" hausnr=\"" + this.ivHausnr);
      }
      if (this.ivKusyma.length() > 0)
      {
        lvHelpXML.append("\" kusyma=\"" + this.ivKusyma);
      }
      if (this.ivKugr.length() > 0)
      {
        lvHelpXML.append("\" kugr=\"" + this.ivKugr);
      }
      if (this.ivRatingID.length() > 0)
      {
        lvHelpXML.append("\" rating_id=\"" + this.ivRatingID);
      }
      if (this.ivRatingMaster.length() > 0)
      {
        lvHelpXML.append("\" rating_master=\"" + this.ivRatingMaster);
      }
      if (this.ivRatingDatum.length() > 0)
      {
        lvHelpXML.append("\" rating_datum=\"" + this.ivRatingDatum);
      }
      if (this.ivRatingToolID.length() > 0)
      {
        lvHelpXML.append("\" rating_tool_id=\"" + this.ivRatingToolID);
      }
      if (this.ivRatingTool.length() > 0)
      {
        lvHelpXML.append("\" rating_tool=\"" + this.ivRatingTool);
      }
        lvHelpXML.append("\">");

        return lvHelpXML;
     }

    /**
     * TXSPersonDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
        return new StringBuffer("</txsi:persdaten>\n");
    }

}
