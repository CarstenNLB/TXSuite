/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.util.HashMap;

/**
 * @author tepperc
 *
 */
public class SicherungsobjektListe extends HashMap<String, Sicherungsobjekt>
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param pvShpo
     * @param pvDeckungsstatus 
     * 
     */
    public void putSHPO(String pvShpo, String pvDeckungsstatus)
    {     
        
        Sicherungsobjekt lvSicherungsObj = null;
        //System.out.println(pvShpo);
        
        // Sicherheit merken und abschneiden
        String lvShKey = pvShpo.substring(0, pvShpo.indexOf(","));        
        pvShpo = pvShpo.substring(pvShpo.indexOf(",") + 1);
        String lvShStatus = pvShpo.substring(0, pvShpo.indexOf(","));
        pvShpo = pvShpo.substring(pvShpo.indexOf(",") + 1);
        //System.out.println("lvShkey: " + lvShKey);
        //System.out.println("lvShStatus: " + lvShStatus);
        //System.out.println("pvShpo: " + pvShpo);
        // Komma anhaengen, damit auch das letzte Element verarbeitet wird
        pvShpo = pvShpo + ",";
        
        // CT 14.08.2013 - Kann erst mit TXS 4.04 verwendet werden  -> Defect
        String lvDeckungsstatus = new String();
        String lvSicherungsobjekt = new String();
        String lvHelpString = new String();
        
        boolean lvFirst = true;
        for (int y = 0; y < pvShpo.length(); y++)
        {
            if (pvShpo.charAt(y) == ',')
            {
                if (lvFirst)
                {
                     lvSicherungsobjekt = lvHelpString;
                     //System.out.println("Sicherungsobjekt: " + lvSicherungsobjekt);
                     lvFirst = false;
                     lvHelpString = new String();
                }
                else
                {
                    // CT 08.07.2014 - Spaeter wieder reinnehmen
                    //lvDeckungsstatus = lvHelpString;
                    
                    // Deckungskennzeichenermittlung bei Objekten immer noch fehlerhaft
                    lvDeckungsstatus = pvDeckungsstatus;
                    
                    //System.out.println("Deckungsstatus: " + lvDeckungsstatus);
                      lvSicherungsObj = get(lvSicherungsobjekt);
                      if (lvSicherungsObj == null)
                      {
                        if (!lvDeckungsstatus.equals("F"))
                        {
                            lvSicherungsObj = new Sicherungsobjekt(lvSicherungsobjekt, lvShKey, lvDeckungsstatus);
                            this.put(lvSicherungsobjekt, lvSicherungsObj);
                        }
                      }
                      else
                      {
                        if (lvDeckungsstatus.equals("D"))
                        {
                            lvSicherungsObj.setDeckungsstatus(lvDeckungsstatus);
                        }
                      }  
                      lvFirst = true;
                      lvHelpString = new String();
                      lvSicherungsobjekt = new String();
                      lvDeckungsstatus = new String();
                }
            }
            else
            {
                lvHelpString = lvHelpString + pvShpo.charAt(y);                       
            }
        }           
     }

    /**
     * @param pvSicherungsobjektID 
     * @return 
     * 
     */
    public Sicherungsobjekt getSHPO(String pvSicherungsobjektID)
    {
        return this.get(pvSicherungsobjektID);
    }

}
