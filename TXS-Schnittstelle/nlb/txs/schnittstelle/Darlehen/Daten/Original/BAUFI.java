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
public class BAUFI 
{
    /**
     * Kopfbereich des Segments
     */
    private DWHKOPF ivKopf;
    
    /**
     * vierstelliges Baujahr 
     */
    private String ivVbbj;           
    
    /**
     * Beleihungswert
     */
    private String ivVbbelw;               
  
    /**
     * Sachwert
     */
    private String ivVbsach;                 
    
    /**
     * Ertragswert
     */
    private String ivVbbert;
    
    /**
     * Verwendungszweck
     */
    private String ivVzw1;             
        
    /**
     * Zusatzsicherheiten
     */
    private String ivVbss;       
    
    /**
     * Grundschuld, Grundpfandrecht
     */
    private String ivVbgs;

    /**
     * Kontruktor
     */
    public BAUFI()
    {
        ivKopf = new DWHKOPF();
        ivVbbj = new String();           
        ivVbbelw = new String();               
        ivVbsach = new String();                 
        ivVbbert = new String();
        ivVzw1 = new String();             
        ivVbss = new String();       
        ivVbgs = new String();
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
     * @return the sVbbj
     */
    public String getsVbbj() {
        return this.ivVbbj;
    }

    /**
     * @param pvSVbbj the sVbbj to set
     */
    public void setsVbbj(String pvSVbbj) {
        this.ivVbbj = pvSVbbj;
    }

    /**
     * @return the sVbbelw
     */
    public String getsVbbelw() {
        return this.ivVbbelw;
    }

    /**
     * @param pvSVbbelw the sVbbelw to set
     */
    public void setsVbbelw(String pvSVbbelw) {
        this.ivVbbelw = pvSVbbelw;
    }

    /**
     * @return the sVbsach
     */
    public String getsVbsach() {
        return this.ivVbsach;
    }

    /**
     * @param pvSVbsach the sVbsach to set
     */
    public void setsVbsach(String pvSVbsach) {
        this.ivVbsach = pvSVbsach;
    }

    /**
     * @return the sVbbert
     */
    public String getsVbbert() {
        return this.ivVbbert;
    }

    /**
     * @param pvSVbbert the sVbbert to set
     */
    public void setsVbbert(String pvSVbbert) {
        this.ivVbbert = pvSVbbert;
    }

    /**
     * @return the sVzw1
     */
    public String getsVzw1() {
        return this.ivVzw1;
    }

    /**
     * @param pvSVzw1 the sVzw1 to set
     */
    public void setsVzw1(String pvSVzw1) {
        this.ivVzw1 = pvSVzw1;
    }

    /**
     * @return the sVbss
     */
    public String getsVbss() {
        return this.ivVbss;
    }

    /**
     * @param pvSVbss the sVbss to set
     */
    public void setsVbss(String pvSVbss) {
        this.ivVbss = pvSVbss;
    }

    /**
     * @return the sVbgs
     */
    public String getsVbgs() {
        return this.ivVbgs;
    }

    /**
     * @param pvSVbgs the sVbgs to set
     */
    public void setsVbgs(String pvSVbgs) {
        this.ivVbgs = pvSVbgs;
    }

    /**
     * Setzt einen Wert des BAUFI-Segment
     * @param pvPos Position
     * @param pvWert Wert
     */
    public void setBAUFI(int pvPos, String pvWert)
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
              this.setsVbbj(pvWert);
              break;
           case 12:
              this.setsVbbelw(pvWert);
              break;
          case 13:
              this.setsVbsach(pvWert);
              break;
          case 14:
              this.setsVbbert(pvWert);
              break;
          case 15:
              this.setsVzw1(pvWert);
              break;
          case 16:
              this.setsVbss(pvWert);
              break;
          case 17:
              this.setsVbgs(pvWert);
              break;
          default:
              System.out.println("BAUFI: unbekannte Position: " + pvPos + " Wert: " + pvWert);
        }
    } 
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BAUFI [kopf=" + this.ivKopf + ", sVbbelw=" + this.ivVbbelw + ", sVbbert=" + this.ivVbbert + ", sVbbj=" + this.ivVbbj + ", sVbgs=" + this.ivVbgs
                + ", sVbsach=" + this.ivVbsach + ", sVbss=" + this.ivVbss + ", sVzw1=" + this.ivVzw1 + "]";
    }

}
