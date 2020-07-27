/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Rueckmeldung;

import java.util.LinkedList;

/**
 * Diese Klasse wurde in der alten Ruckmeldung an SAP CMS verwendet.
 * An ihre Stelle wird in der neuen Rueckmeldung an SAP CMS die Klasse 'RueckmeldeTripel' verwendet.
 * @author tepperc
 */
@Deprecated
public class Sicherungsobjekt 
{
    /**
     * 
     */
    private String ivObjKey;
    
    /**
     * 
     */
    private LinkedList<String> ivShKeys;
    
    /**
     * 
     */
    private String ivDeckungsstatus;
    
    /**
     * Konstruktor 
     * @param pvObjKey 
     * @param pvShkey 
     * @param pvDeckungsstatus 
     */
    public Sicherungsobjekt(String pvObjKey, String pvShkey, String pvDeckungsstatus)
    {
        this.ivObjKey = pvObjKey;
        ivShKeys = new LinkedList<String>();
        ivShKeys.add(pvShkey);
        this.ivDeckungsstatus = pvDeckungsstatus;
    }
    
    /**
     * @param pvShkey 
     * @param pvDs 
     * 
     */
    public void addShKey(String pvShkey, String pvDs)
    {
        String lvHelpKey;
        
        boolean lvEnthalten = false;
        
        for (int x = 0; x < ivShKeys.size(); x++)
        {
            lvHelpKey = ivShKeys.get(x);
            if (lvHelpKey.equals(pvShkey))
            {
                lvEnthalten = true;
            }
        }
        
        if (!lvEnthalten)
        {
            ivShKeys.add(pvShkey);
        }
        
        if (!this.ivDeckungsstatus.equals(pvDs))
        {
            if (pvDs.equals("D"))
            {
                this.ivDeckungsstatus = pvDs;
            }
        }
    }

    /**
     * @return the sObjKey
     */
    public String getsObjKey() 
    {
        return this.ivObjKey;
    }

    /**
     * @param pvSObjKey the sObjKey to set
     */
    public void setsObjKey(String pvSObjKey) 
    {
        this.ivObjKey = pvSObjKey;
    }

    /**
     * @return the shKeys
     */
    public LinkedList<String> getShKeys() 
    {
        return this.ivShKeys;
    }

    /**
     * @param pvShKeys the shKeys to set
     */
    public void setShKeys(LinkedList<String> pvShKeys) 
    {
        this.ivShKeys = pvShKeys;
    }

    /**
     * @return the deckungsstatus
     */
    public String getDeckungsstatus() 
    {
        return this.ivDeckungsstatus;
    }

    /**
     * @param pvDeckungsstatus the deckungsstatus to set
     */
    public void setDeckungsstatus(String pvDeckungsstatus) 
    {
        this.ivDeckungsstatus = pvDeckungsstatus;
    }  
}
