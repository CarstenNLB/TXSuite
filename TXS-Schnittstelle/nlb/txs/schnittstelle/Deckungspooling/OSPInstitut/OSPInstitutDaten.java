/*******************************************************************************
 * Copyright (c) 2012 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Deckungspooling.OSPInstitut;

import java.util.ArrayList;

import nlb.txs.schnittstelle.Deckungspooling.Kunde.OSPKunde;
import nlb.txs.schnittstelle.Deckungspooling.Kunde.OSPKundenListe;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPGrundbuch;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheit;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPSicherheitenListe;
import nlb.txs.schnittstelle.Deckungspooling.Sicherheiten.OSPVermoegensobjekt;

/**
 * @author tepperc
 *
 */
public class OSPInstitutDaten 
{
    /**
     * Regionskennzeichen
     */
    private String ivRegKz;
    
    /**
     * OSPlus Institusnummer 
     */
    private String ivOSPInst;
    
    /**
     * DarKa-Kundennummer des Instituts
     */
    private String ivDarKaKuNr;
    
    /**
     * Liste der Kunden
     */
    private OSPKundenListe ivListeKunden;
    
    /**
     * Statistikinformationen Kunden
     */
    private OSPInstitutKundenStatistik ivKundenStatistik;
    
    /**
     * Liste der Sicherheiten
     */
    private OSPSicherheitenListe ivListeSicherheiten;
    
    /**
     * Statistikinformationen Sicherheiten
     */
    private OSPInstitutSicherheitenStatistik ivSicherheitenStatistik;
    
    /**
     * Liste der Vermögensobjekte
     */
    private ArrayList<OSPVermoegensobjekt> ivListeVermoegensobjekte;
    
    /**
     * Liste der Grundbücher
     */
    private ArrayList<OSPGrundbuch> ivListeGrundbuecher;
    
    /**
     * Konstruktor
     * @param pvRegKz Regionskennzeichen
     * @param pvInstnr Institutsnummer
     * @param pvKdnr Kundennummer
     */
    public OSPInstitutDaten(String pvRegKz, String pvInstnr, String pvKdnr)
    {
        this.ivRegKz = pvRegKz;
        this.ivOSPInst = pvInstnr;
        this.ivDarKaKuNr = pvKdnr;
        ivListeKunden = new OSPKundenListe();
        ivListeSicherheiten = new OSPSicherheitenListe();
        ivListeVermoegensobjekte = new ArrayList<OSPVermoegensobjekt>();
        ivListeGrundbuecher = new ArrayList<OSPGrundbuch>();
        ivKundenStatistik = new OSPInstitutKundenStatistik();
        ivSicherheitenStatistik = new OSPInstitutSicherheitenStatistik();
    }

    /**
     * Liefert das Regionskennzeichen des Instituts
     * @return Das Regionskennzeichen
     */
    public String getRegKz() 
    {
        return this.ivRegKz;
    }

    /**
     * Setzt das Regionskennzeichen des Instituts
     * @param pvRegKz Das zu setzende Regionskennzeichen
     */
    public void setRegKz(String pvRegKz) 
    {
        this.ivRegKz = pvRegKz;
    }

    /**
     * Liefert die OSPlus-Institutsnummer
     * @return Die Institutsnummer
     */
    public String getOSPInst() 
    {
        return this.ivOSPInst;
    }

    /**
     * Setzt die OSPlus-Institutsnummer
     * @param pvOSPInst Die zu setzende Institutsnummer
     */
    public void setOSPInst(String pvOSPInst) 
    {
        this.ivOSPInst = pvOSPInst;
    }

    /**
     * Liefert die DarKa-Kundennummer
     * @return Die DarKa-Kundennummer
     */
    public String getDarKaKuNr() 
    {
        return this.ivDarKaKuNr;
    }

    /**
     * Setzt die DarKa-Kundennummer
     * @param pvDarKaKuNr Die zu setzende DarKa-Kundennummer
     */
    public void setDarKaKuNr(String pvDarKaKuNr) 
    {
        this.ivDarKaKuNr = pvDarKaKuNr;
    }

    /**
     * @return the listeKunden
     */
    public OSPKundenListe getListeKunden() 
    {
        return this.ivListeKunden;
    }

    /**
     * @param pvListeKunden the listeKunden to set
     */
    public void setListeKunden(OSPKundenListe pvListeKunden) 
    {
        this.ivListeKunden = pvListeKunden;
    }
    
    /**
     * @param kunde 
     * 
     */
    public void addListeKunden(OSPKunde kunde)
    {
        ivListeKunden.add(kunde);
    }

    /**
     * @return the kundenStatistik
     */
    public OSPInstitutKundenStatistik getKundenStatistik() 
    {
        return this.ivKundenStatistik;
    }

    /**
     * @param pvKundenStatistik the kundenStatistik to set
     */
    public void setKundenStatistik(OSPInstitutKundenStatistik pvKundenStatistik) 
    {
        this.ivKundenStatistik = pvKundenStatistik;
    }

    /**
     * @return the listeSicherheiten
     */
    public OSPSicherheitenListe getListeSicherheiten() 
    {
        return this.ivListeSicherheiten;
    }

    /**
     * @param pvListeSicherheiten the listeSicherheiten to set
     */
    public void setListeSicherheiten(OSPSicherheitenListe pvListeSicherheiten) 
    {
        this.ivListeSicherheiten = pvListeSicherheiten;
    }
    
    /**
     * 
     * @param sicherheit 
     */
    public void addListeSicherheiten(OSPSicherheit sicherheit)
    {
        ivListeSicherheiten.add(sicherheit);
    }
    
    /**
     * Liefert die Statistikdaten der Sicherheiten aus OSPlus
     * @return the sicherheitenStatistik Die Statistikdaten der Sicherheiten
     */
    public OSPInstitutSicherheitenStatistik getSicherheitenStatistik() 
    {
        return this.ivSicherheitenStatistik;
    }

    /**
     * Fügt eine Sicherheit aus OSPlus hinzu
     * @param pvSicherheitenStatistik Die einzufügende Sicherheit
     */
    public void setSicherheitenStatistik(OSPInstitutSicherheitenStatistik pvSicherheitenStatistik) 
    {
        this.ivSicherheitenStatistik = pvSicherheitenStatistik;
    }

    /**
     * Liefert die komplette Liste der Vermögensobjekte aus OSPlus
     * @return the listeVermoegensobjekt Die komplette Liste der Vermögensobjkete
     */
    public ArrayList<OSPVermoegensobjekt> getListeVermoegensobjekt() 
    {
        return this.ivListeVermoegensobjekte;
    }

    /**
     * Setzt die komplette Liste der Vermögensobjekte aus OSPlus
     * @param pvListeVermoegensobjekt Die zu setzende komplette Liste der Vermögensobjekte
     */
    public void setListeVermoegensobjekt(ArrayList<OSPVermoegensobjekt> pvListeVermoegensobjekt) 
    {
        this.ivListeVermoegensobjekte = pvListeVermoegensobjekt;
    }
    
    /**
     * Fügt ein Vermögensobjekt aus OSPlus der Liste hinzu
     * @param vObj Das einzufügende Vermögensobjekt
     */
    public void addListeVermoegensobjekte(OSPVermoegensobjekt vObj)
    {
        ivListeVermoegensobjekte.add(vObj);
    }

    /**
     * Liefert die komplette Liste der Grundbücher aus OSPlus
     * @return Die komplette Liste der Grundbücher
     */
    public ArrayList<OSPGrundbuch> getListeGrundbuecher() 
    {
        return this.ivListeGrundbuecher;
    }

    /**
     * Setzt die komplette Liste der Grundbücher aus OSPlus
     * @param pvListeGrundbuecher Die zu setzende komplette Liste der Grundbücher
     */
    public void setListeGrundbuecher(ArrayList<OSPGrundbuch> pvListeGrundbuecher) 
    {
        this.ivListeGrundbuecher = pvListeGrundbuecher;
    }
    
    /**
     * Fügt ein Grundbuch aus OSPlus der Liste hinzu
     * @param pvGb Das einzufügende Grundbuch
     */
    public void addListeGrundbuecher(OSPGrundbuch pvGb)
    {
        ivListeGrundbuecher.add(pvGb);
    }

    /**
     * Setzt das Bestandsdatum der Kunden Anlieferung
     * @param pvDatum Das zu setzende Bestandsdatum
     */
    public void setKundenBestandsdatum(String pvDatum)
    {
        ivListeKunden.setBestandsdatum(pvDatum);
    }
    
    /**
     * Setzt das Bestandsdatum der Sicherheiten Anlieferung
     * @param pvDatum Das zu setzende Bestandsdatum
     */
    public void setSicherheitenBestandsdatum(String pvDatum)
    {
        ivListeSicherheiten.setBestandsdatum(pvDatum);
    }

    /**
     * Liefert die Institutskennung
     * @param pvKontonummer Kontonummer zum gesuchten Konto
     * @return Die zugehörige Institutsnummer zum Konto
     */
    public String getInstitutskennung(String pvKontonummer)
    {
        String lvHelpKennung = new String();
        OSPKunde lvKunde;
        System.out.println("Liste-Kunden - Size: " + ivListeKunden.size());
        for (int x = 0; x < ivListeKunden.size(); x++)
        {
            lvKunde = ivListeKunden.get(x);
            if (lvKunde.containsKontonummer(pvKontonummer))
            {
                lvHelpKennung = ivRegKz + ivOSPInst;
            }
        }
        return lvHelpKennung;
    }
    
    /**
     * Liefert die Kundennummer 
     * @param pvKontonummer Kontonummer zum gesuchten Konto
     * @return Die zugehörige Kundennummer zum Konto
     */
    public String getKundennummer(String pvKontonummer)
    {
        String lvHelpKennung = new String();
        OSPKunde lvKunde;
        for (int x = 0; x < ivListeKunden.size(); x++)
        {
            lvKunde = ivListeKunden.get(x);
            if (lvKunde.containsKontonummer(pvKontonummer))
            {
                lvHelpKennung = lvKunde.getKundennr();
            }
        }
        return lvHelpKennung;
    }
    
    /**
     * Schreibt die Daten des Instituts in einen String
     * @return String mit Daten
     */
    public String toString()
    {
      String helpString;
      helpString = "OSPlus-Institut:\n";
      helpString = helpString + "Regionkennzeichen: " + ivRegKz + "\n";
      helpString = helpString + "OSPlus-Institutsnummer: " + ivOSPInst + "\n";
      helpString = helpString + "DarKa-Institutsnummer:  " + ivDarKaKuNr + "\n";
      
      helpString = helpString + ivKundenStatistik.toString();
      helpString = helpString + ivListeKunden.toString();
      
      helpString = helpString + ivListeSicherheiten.toString();
      helpString = helpString + ivListeVermoegensobjekte.toString();
      helpString = helpString + ivListeGrundbuecher.toString();
      
      return helpString;
    }

    /**
     * Liefert ein Vermögensobjekt aus OSPlus
     * @param pvString Die Vermögensobjekt-Nr des zu suchenden Vermögensobjekt
     * @return Das gefundene Vermögensobjekt
     */
    public OSPVermoegensobjekt getVermoegensobjekt(String pvString) 
    {
        OSPVermoegensobjekt lvVermoegensObj = null;
        for (int x = 0; x < ivListeVermoegensobjekte.size(); x++)
        {
            lvVermoegensObj = ivListeVermoegensobjekte.get(x);
            if (lvVermoegensObj.getNr().equals(pvString))
            {
                return lvVermoegensObj;
            }
        }
        return lvVermoegensObj;
    }

    /**
     * Liefert ein Grundbuch aus OSPLus 
     * @param pvString Die Grundbuch-Nr des zu suchenden Grundbuch
     * @return Das gefundene Grundbuch
     */
    public OSPGrundbuch getGrundbuch(String pvString) 
    {
        OSPGrundbuch lvGb = null;
        for (int x = 0; x < ivListeGrundbuecher.size(); x++)
        {
            lvGb = ivListeGrundbuecher.get(x);
            if (lvGb.getNr().equals(pvString))
            {
                return lvGb;
            }
        }
        return lvGb;
    }
    
    /**
     * Prüft die Statistikdaten, ob diese mit den tatsächlich gelieferten
     * Datenanzahlen übereinstimmt.
     * @return true - Daten stimmen überein; false - Daten inkonsistent
     */
    public boolean checkStatistik()
    {
        boolean lvOkay = true;
        if (ivKundenStatistik.getAnzahlKunden() != ivListeKunden.size())
        {
            System.out.println("Ungleiche Kundenanzahl: " + ivKundenStatistik.getAnzahlKunden() + "!=" + ivListeKunden.size());
            lvOkay = false;
        }
                
        if (ivSicherheitenStatistik.getAnzahlSicherheiten() != ivListeSicherheiten.size())
        {
            System.out.println("Ungleiche Sicherheitenanzahl: " + ivSicherheitenStatistik.getAnzahlSicherheiten() + "!=" + ivListeSicherheiten.size());
            lvOkay = false;
        }
        
        if (ivSicherheitenStatistik.getAnzahlVermoegensobjekte() != ivListeVermoegensobjekte.size())
        {
            System.out.println("Ungleiche Vermögensobjekteanzahl: " + ivSicherheitenStatistik.getAnzahlVermoegensobjekte() + "!=" + ivListeVermoegensobjekte.size());
            lvOkay = false;
        }

        if (ivSicherheitenStatistik.getAnzahlGrundbuecher() != ivListeGrundbuecher.size())
        {
            System.out.println("Ungleiche Grundbücheranzahl: " + ivSicherheitenStatistik.getAnzahlGrundbuecher() + "!=" + ivListeGrundbuecher.size());
            lvOkay = false;
        }

        return lvOkay;
    }
}
