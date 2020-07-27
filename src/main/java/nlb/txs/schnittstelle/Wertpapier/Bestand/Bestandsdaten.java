package nlb.txs.schnittstelle.Wertpapier.Bestand;

import java.math.BigDecimal;

import nlb.txs.schnittstelle.Utilities.StringKonverter;

import org.apache.log4j.Logger;

public class Bestandsdaten 
{
	/**
	 * System
	 */
	private String ivSystem;
	
    /**
     * AKTPAS
     */
	private String ivAktivPassiv;
	
	/**
	 * PRODUKT
	 */
	private String ivProdukt;
	
	/**
	 * DEPOT_NR
	 */
	private String ivDepotNr;
	
	/**
	 * KZ_SOMA
	 */
	private String ivKennzeichenSOMA;
	
	/**
	 * DATUM_SOMA
	 */
	private String ivDatumSOMA;
	
	/**
	 * DEPOT_KZ
	 */
	private String ivDepotKennzeichen;
	
	/**
	 * WP_ART
	 */
	private String ivWertpapierart;
	
	/**
	 * SALDO
	 */
	private String ivSaldo;
	
	/**
	 * WAEHRUNG_SALDO
	 */
	private String ivSaldoWaehrung;
	
	/**
	 * MARKTKURS
	 */
	private String ivMarktkurs;
	
	/**
	 * NOMINALBETRAG
	 */
	private String ivNominalbetrag;
	
	/**
	 * WAEHRUNG_NOM
	 */
	private String ivNominalbetragWaehrung;
	
	/**
	 * VALUTIERUNG
	 */
	private String ivValutierung;
	
	/**
	 * B_WAEHRUNG
	 */
	private String ivBetragswaehrung;
	
	/**
	 * DECKUNGSSCHLUESSEL
	 */
	private String ivDeckungsschluessel;

	/**
	 * AUSPLATZIERUNGS_MERKMAL
	 */
	private String ivAusplatzierungsmerkmal;
	
	/**
	 * MAVPROD
	 */
	private String ivMAVPROD;
	
	/**
	 * KDNR
	 */
	private String ivKundennummer;
	
	/**
	 * PAS
	 */
	private String ivPAS;
	
	/**
	 * KUST
	 */
	private String ivKUST;
	
	/**
	 * VERWALTENDE_STELLE
	 */
	private String ivVerwaltendeStelle;

	/**
	 * Konstruktor
	 */
	public Bestandsdaten() 
	{
		this.ivSystem = new String();
		this.ivAktivPassiv = new String();
		this.ivProdukt = new String();
		this.ivDepotNr = new String();
		this.ivKennzeichenSOMA = new String();
		this.ivDatumSOMA = new String();
		this.ivDepotKennzeichen = new String();
		this.ivWertpapierart = new String();
		this.ivSaldo = new String();
		this.ivSaldoWaehrung = new String();
		this.ivMarktkurs = new String();
		this.ivNominalbetrag = new String();
		this.ivNominalbetragWaehrung = new String();
		this.ivValutierung = new String();
		this.ivBetragswaehrung = new String();
		this.ivDeckungsschluessel = new String();
		this.ivAusplatzierungsmerkmal = new String();
		this.ivMAVPROD = new String();
		this.ivKundennummer = new String();
		this.ivPAS = new String();
		this.ivKUST = new String();
		this.ivVerwaltendeStelle = new String();
	}
	
	/**
	 * Liefert System
	 */
	public String getSystem()
	{
		return this.ivSystem;
	}
	
	/**
	 * Setzt System
	 * @param pvSystem
	 */
	public void setSystem(String pvSystem)
	{
		this.ivSystem = pvSystem;
	}
	
	/**
	 * Liefert AktivPassiv
	 * @return the ivAktivPassiv
	 */
	public String getAktivPassiv() 
	{
		return ivAktivPassiv;
	}

	/**
	 * Setzt AktivPassiv
	 * @param pvAktivPassiv the ivAktivPassiv to set
	 */
	public void setAktivPassiv(String pvAktivPassiv) 
	{
		this.ivAktivPassiv = pvAktivPassiv;
	}

	/**
	 * Liefert Produkt
	 * @return the ivProdukt
	 */
	public String getProdukt() 
	{
		return ivProdukt;
	}

	/**
	 * Setzt Produkt
	 * @param pvProdukt the ivProdukt to set
	 */
	public void setProdukt(String pvProdukt) 
	{
		this.ivProdukt = pvProdukt;
	}

	/**
	 * Liefert DepotNr
	 * @return the ivDepotNr
	 */
	public String getDepotNr() 
	{
		return ivDepotNr;
	}

	/**
	 * Setzt DepotNr
	 * @param pvDepotNr the ivDepotNr to set
	 */
	public void setDepotNr(String pvDepotNr) 
	{
		this.ivDepotNr = pvDepotNr;
	}

	/**
	 * Liefert KennzeichenSOMA
	 * @return the ivKennzeichenSOMA
	 */
	public String getKennzeichenSOMA() 
	{
		return ivKennzeichenSOMA;
	}

	/**
	 * Setzt KennzeichenSOMA
	 * @param pvKennzeichenSOMA the ivKennzeichenSOMA to set
	 */
	public void setKennzeichenSOMA(String pvKennzeichenSOMA) 
	{
		this.ivKennzeichenSOMA = pvKennzeichenSOMA;
	}

	/**
	 * Liefert DatumSOMA
	 * @return the ivDatumSOMA
	 */
	public String getDatumSOMA() 
	{
		return ivDatumSOMA;
	}

	/**
	 * Setzt DatumSOMA
	 * @param pvDatumSOMA the ivDatumSOMA to set
	 */
	public void setDatumSOMA(String pvDatumSOMA) 
	{
		this.ivDatumSOMA = pvDatumSOMA;
	}

	/**
	 * Liefert DepotKennzeichen
	 * @return the ivDepotKennzeichen
	 */
	public String getDepotKennzeichen() 
	{
		return ivDepotKennzeichen;
	}

	/**
	 * Setzt DepotKennzeichen
	 * @param pvDepotKennzeichen the ivDepotKennzeichen to set
	 */
	public void setDepotKennzeichen(String pvDepotKennzeichen) 
	{
		this.ivDepotKennzeichen = pvDepotKennzeichen;
	}

	/**
	 * Liefert Wertpapierart
	 * @return the ivWertpapierart
	 */
	public String getWertpapierart() 
	{
		return ivWertpapierart;
	}

	/**
	 * Setzt Wertpapierart
	 * @param pvWertpapierart the ivWertpapierart to set
	 */
	public void setWertpapierart(String pvWertpapierart) 
	{
		this.ivWertpapierart = pvWertpapierart;
	}

	/**
	 * Liefert Saldo
	 * @return the ivSaldo
	 */
	public String getSaldo() 
	{
		return ivSaldo;
	}

	/**
	 * Setzt Saldo
	 * @param pvSaldo the ivSaldo to set
	 */
	public void setSaldo(String pvSaldo) 
	{
		this.ivSaldo = pvSaldo;
	}

	/**
	 * Liefert SaldoWaehrung
	 * @return the ivSaldoWaehrung
	 */
	public String getSaldoWaehrung() 
	{
		return ivSaldoWaehrung;
	}

	/**
	 * Setzt SaldoWaehrung
	 * @param pvSaldoWaehrung the ivSaldoWaehrung to set
	 */
	public void setSaldoWaehrung(String pvSaldoWaehrung) 
	{
		this.ivSaldoWaehrung = pvSaldoWaehrung;
	}

	/**
	 * Liefert Marktkurs
	 * @return the ivMarktkurs
	 */
	public String getMarktkurs()
	{
		return ivMarktkurs;
	}

	/**
	 * Setzt Marktkurs
	 * @param pvMarktkurs the ivMarktkurs to set
	 */
	public void setMarktkurs(String pvMarktkurs) 
	{
		this.ivMarktkurs = pvMarktkurs;
	}

	/**
	 * Liefert Nominalbetrag
	 * @return the ivNominalbetrag
	 */
	public String getNominalbetrag() 
	{
		return ivNominalbetrag;
	}

	/**
	 * Setzt Nominalbetrag
	 * @param pvNominalbetrag the ivNominalbetrag to set
	 */
	public void setNominalbetrag(String pvNominalbetrag) 
	{
		this.ivNominalbetrag = pvNominalbetrag;
	}

	/**
	 * Liefert NominalbetragWaehrung
	 * @return the ivNominalbetragWaehrung
	 */
	public String getNominalbetragWaehrung() 
	{
		return ivNominalbetragWaehrung;
	}

	/**
	 * Setzt NominalbetragWaehrung
	 * @param pvNominalbetragWaehrung the ivNominalbetragWaehrung to set
	 */
	public void setNominalbetragWaehrung(String pvNominalbetragWaehrung) 
	{
		this.ivNominalbetragWaehrung = pvNominalbetragWaehrung;
	}

	/**
	 * Liefert Valutierung
	 * @return the ivValutierung
	 */
	public String getValutierung() 
	{
		return ivValutierung;
	}

	/**
	 * Setzt Valutierung
	 * @param pvValutierung the ivValutierung to set
	 */
	public void setValutierung(String pvValutierung) {
		this.ivValutierung = pvValutierung;
	}

	/**
	 * Liefert Betragswaehrung
	 * @return the ivBetragswaehrung
	 */
	public String getBetragswaehrung() 
	{
		return ivBetragswaehrung;
	}

	/**
	 * Setzt Betragswaehrung
	 * @param pvBetragswaehrung the ivBetragswaehrung to set
	 */
	public void setBetragswaehrung(String pvBetragswaehrung) 
	{
		this.ivBetragswaehrung = pvBetragswaehrung;
	}

	/**
	 * Liefert Deckungsschluessel
	 * @return the ivDeckungsschluessel
	 */
	public String getDeckungsschluessel() 
	{
		return ivDeckungsschluessel;
	}

	/**
	 * Setzt Deckungsschluessel
	 * @param pvDeckungsschluessel the ivDeckungsschluessel to set
	 */
	public void setDeckungsschluessel(String pvDeckungsschluessel) 
	{
		this.ivDeckungsschluessel = pvDeckungsschluessel;
	}

	/**
	 * Liefert Ausplatzierungsmerkmal
	 * @return the ivAusplatzierungsmerkmal
	 */
	public String getAusplatzierungsmerkmal() 
	{
		return ivAusplatzierungsmerkmal;
	}

	/**
	 * Setzt Ausplatzierungsmerkmal
	 * @param pvAusplatzierungsmerkmal the ivAusplatzierungsmerkmal to set
	 */
	public void setAusplatzierungsmerkmal(String pvAusplatzierungsmerkmal) 
	{
		this.ivAusplatzierungsmerkmal = pvAusplatzierungsmerkmal;
	}

	/**
	 * Liefert MAVPROD
	 * @return the ivMAVPROD
	 */
	public String getMAVPROD() 
	{
		return ivMAVPROD;
	}

	/**
	 * Setzt MAVPROD
	 * @param pvMAVPROD the ivMAVPROD to set
	 */
	public void setMAVPROD(String pvMAVPROD) 
	{
		this.ivMAVPROD = pvMAVPROD;
	}

	/**
	 * Liefert Kundennummer
	 * @return the ivKundennummer
	 */
	public String getKundennummer() 
	{
		return ivKundennummer;
	}

	/**
	 * Setzt Kundennummer
	 * @param pvKundennummer the ivKundennummer to set
	 */
	public void setKundennummer(String pvKundennummer) {
		this.ivKundennummer = pvKundennummer;
	}

	/**
	 * Liefert PAS
	 * @return the ivPAS
	 */
	public String getPAS() 
	{
		return ivPAS;
	}

	/**
	 * Setzt PAS
	 * @param pvPAS the ivPAS to set
	 */
	public void setPAS(String pvPAS) 
	{
		this.ivPAS = pvPAS;
	}

	/**
	 * Liefert KUST
	 * @return the ivKUST
	 */
	public String getKUST() 
	{
		return ivKUST;
	}

	/**
	 * Setzt KUST
	 * @param pvKUST the ivKUST to set
	 */
	public void setKUST(String pvKUST) 
	{
		this.ivKUST = pvKUST;
	}

	/**
	 * Liefert VerwaltendeStelle
	 * @return the ivVerwaltendeStelle
	 */
	public String getVerwaltendeStelle() 
	{
		return ivVerwaltendeStelle;
	}

	/**
	 * Setzt VerwaltendeStelle
	 * @param pvVerwaltendeStelle the ivVerwaltendeStelle to set
	 */
	public void setVerwaltendeStelle(String pvVerwaltendeStelle) 
	{
		this.ivVerwaltendeStelle = pvVerwaltendeStelle;
	}

	/**
	 * Parst eine Zeile der Bestandsdaten
     * @param pvZeile
     * @param pvLogger 
     * @return 
     *       
     */
	public boolean parseBestandsdaten(String pvZeile, Logger pvLogger)
	{                 
      String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
      int    lvZzStr=0;              // pointer fuer satzbereich
         
      // Steuerung/Iteration Eingabesatz
      for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn Semikolon erkannt
        if (pvZeile.charAt(lvZzStr) == ';')
        {
            //System.out.println(lvTemp);
            this.setBestandsdaten(lvLfd, lvTemp, pvLogger);
            
            lvLfd++;                  // naechste Feldnummer
            // loeschen Zwischenbuffer
            lvTemp = new String();
        }
        else
        {
            // uebernehmen Byte aus Eingabesatzposition
            lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
        }
      } // ende for  
     
    return true; 
   }

    /**
     * Setzt einen Wert der Bestandsdaten
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setBestandsdaten(int pvPos, String pvWert, Logger pvLogger) 
    {
        //System.out.println("Pos: " + pvPos + " Wert:" + pvWert);
        switch (pvPos)
        {
          case 0:
        	  this.ivSystem = pvWert;
        	  break;
          case 1:
              this.ivAktivPassiv = pvWert;
              break;
          case 2:
              this.ivProdukt = pvWert;
              break;
          case 3:
              this.ivDepotNr = pvWert.trim();
              break;
          case 4:
              this.ivKennzeichenSOMA = pvWert;
              break;
          case 5:
              this.ivDatumSOMA = pvWert;
              break;
          case 6:
              this.ivDepotKennzeichen = pvWert.trim();
              break;
          case 7:
              this.ivWertpapierart = pvWert;
              break;
          case 8:
              pvWert = pvWert.trim();
              if (pvWert.startsWith("-"))
              {
                 pvWert = pvWert.substring(1);
              }
              this.ivSaldo = pvWert;
              break;
          case 9:
              this.ivSaldoWaehrung = pvWert.trim();
              break;
          case 10:
        	  BigDecimal lvHelpMarktkurs = StringKonverter.convertString2BigDecimal(pvWert.trim()); // Fuehrende Nullen entfernen
              this.ivMarktkurs = lvHelpMarktkurs.toPlainString();
              break;
          case 11:
              pvWert = pvWert.trim();
              if (pvWert.startsWith("-"))
              {
                 pvWert = pvWert.substring(1);
              }
              BigDecimal lvHelpNominalbetrag = StringKonverter.convertString2BigDecimal(pvWert); // Fuehrende Nullen entfernen
              this.ivNominalbetrag = lvHelpNominalbetrag.toPlainString();
              break;
          case 12:
             this.ivNominalbetragWaehrung = pvWert;
              break;
          case 13:
              pvWert = pvWert.trim();
              if (pvWert.startsWith("-"))
              {
                 pvWert = pvWert.substring(1);
              }
              BigDecimal lvHelpValutierung = StringKonverter.convertString2BigDecimal(pvWert); // Fuehrende Nullen entfernen
              this.ivValutierung = lvHelpValutierung.toPlainString();
              break;
          case 14:
              this.ivBetragswaehrung = pvWert;
              break;
          case 15:
              this.ivDeckungsschluessel = pvWert;
              break;
          case 16:
        	  this.ivAusplatzierungsmerkmal = pvWert;
              break;
          case 17:
              this.ivMAVPROD = pvWert.trim();
              break;
          case 18:
              this.ivKundennummer = pvWert;
              break;
          case 19:
              this.ivPAS = pvWert;
              break;
          case 20:
              this.ivKUST = pvWert;
              break;
          case 21:
              this.ivVerwaltendeStelle = pvWert;
              break;
          default:
              pvLogger.error("MAVIS-Bestandsdaten: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
        }
    }

	/**
	 * Liefert die Bestandsdaten als String
	 * @return Bestandsdaten als String
	 */
	public String toString() 
	{
		StringBuilder lvOut = new StringBuilder();
		lvOut.append("System: ");
		lvOut.append(this.ivSystem);
		lvOut.append("AktivPassiv: ");
		lvOut.append(this.ivAktivPassiv);
		lvOut.append("Produkt: ");
		lvOut.append(this.ivProdukt);
		lvOut.append("DepotNr: ");
		lvOut.append(this.ivDepotNr);
		lvOut.append("KennzeichenSOMA: ");
		lvOut.append(this.ivKennzeichenSOMA);
		lvOut.append("DatumSOMA: ");
		lvOut.append(this.ivDatumSOMA);
		lvOut.append("DepotKennzeichen: ");
		lvOut.append(this.ivDepotKennzeichen);
		lvOut.append("Wertpapierart: ");
		lvOut.append(this.ivWertpapierart);
		lvOut.append("Saldo: ");
		lvOut.append(this.ivSaldo);
		lvOut.append("SaldoWaehrung: ");
		lvOut.append(this.ivSaldoWaehrung);
		lvOut.append("Marktkurs: ");
		lvOut.append(this.ivMarktkurs);
		lvOut.append("Nominalbetrag: ");
		lvOut.append(this.ivNominalbetrag);
		lvOut.append("NominalbetragWaehrung: ");
		lvOut.append(this.ivNominalbetragWaehrung);
		lvOut.append("Valutierung: ");
		lvOut.append(this.ivValutierung);
		lvOut.append("Betragswaehrung: ");
		lvOut.append(this.ivBetragswaehrung);
		lvOut.append("Deckungsschluessel: ");
		lvOut.append(this.ivDeckungsschluessel);
		lvOut.append("Ausplatzierungsmerkmal: ");
		lvOut.append(this.ivAusplatzierungsmerkmal);
		lvOut.append("MAVPROD: ");
		lvOut.append(this.ivMAVPROD);
		lvOut.append("Kundennummer: ");
		lvOut.append(this.ivKundennummer);
		lvOut.append("PAS: ");
		lvOut.append(this.ivPAS);
		lvOut.append("KUST: ");
		lvOut.append(this.ivKUST);
		lvOut.append("VerwaltendeStelle: ");
		lvOut.append(this.ivVerwaltendeStelle);
		
		return lvOut.toString();
	}
 
}
