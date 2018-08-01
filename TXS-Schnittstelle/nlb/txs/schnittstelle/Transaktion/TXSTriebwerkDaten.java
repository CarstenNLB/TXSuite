/*******************************************************************************
 * Copyright (c) 2013 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Transaktion;

/**
 * @author tepperc
 *
 */
public class TXSTriebwerkDaten implements TXSTransaktion
{

    /**
     * Betriebstatus
     */
    private String ivBstatus;
    
    /**
     * Seriennummer
     */
    private String ivSn;
    
    /**
     * Externer Triebwerk Key
     */
    private String ivTwkey;
    
    /**
     * Triebwerktyp
     */
    private String ivTyp;
    
    /**
     * Triebwerkvariante 
     */
    private String ivVariante;

    /**
     * Konstruktor zur Initialisierung
     */
    public TXSTriebwerkDaten()
    {
        super();
        this.ivBstatus = new String();
        this.ivSn = new String();
        this.ivTwkey = new String();
        this.ivTyp = new String();
        this.ivVariante = new String();
    }

    /**
     * @return the bstatus
     */
    public String getBstatus() {
        return this.ivBstatus;
    }

    /**
     * @param pvBstatus the bstatus to set
     */
    public void setBstatus(String pvBstatus) {
        this.ivBstatus = pvBstatus;
    }

    /**
     * @return the sn
     */
    public String getSn() {
        return this.ivSn;
    }

    /**
     * @param pvSn the sn to set
     */
    public void setSn(String pvSn) {
        this.ivSn = pvSn;
    }

    /**
     * @return the twkey
     */
    public String getTwkey() {
        return this.ivTwkey;
    }

    /**
     * @param pvTwkey the twkey to set
     */
    public void setTwkey(String pvTwkey) {
        this.ivTwkey = pvTwkey;
    }

    /**
     * @return the typ
     */
    public String getTyp() {
        return this.ivTyp;
    }

    /**
     * @param pvTyp the typ to set
     */
    public void setTyp(String pvTyp) {
        this.ivTyp = pvTyp;
    }

    /**
     * @return the variante
     */
    public String getVariante() {
        return this.ivVariante;
    }

    /**
     * @param pvVariante the variante to set
     */
    public void setVariante(String pvVariante) {
        this.ivVariante = pvVariante;
    }
    
    /**
     * TXSTriebwerkDatenStart in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionStart()
    {
        return new StringBuffer("        <txsi:twdaten");
    }
    
    /**
     * TXSTriebwerkDaten in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionDaten()
    {
        StringBuffer lvHelpXML = new StringBuffer();
        
        if (this.ivBstatus.length() > 0)
        {
          lvHelpXML.append(" bstatus=\"" + this.ivBstatus + "\"");
        }
        if (this.ivSn.length() > 0)
        {
            lvHelpXML.append(" sn=\"" + this.ivSn + "\"");
        }
        if (this.ivTwkey.length() > 0)
        {
            lvHelpXML.append(" twkey=\"" + this.ivTwkey + "\"");
        }
        if (this.ivTyp.length() > 0)
        {
            lvHelpXML.append(" typ=\"" + this.ivTyp + "\"");
        }
        if (this.ivVariante.length() > 0)
        {
            lvHelpXML.append(" variante=\"" + this.ivVariante + "\"");
        }
        lvHelpXML.append(">");
        return lvHelpXML;
    }
    
    /**
     * TXSTriebwerkDatenEnde in die XML-Datei schreiben
     * @return 
     */
    public StringBuffer printTXSTransaktionEnde()
    {
      return new StringBuffer("</txsi:twdaten>\n");
    }
}
