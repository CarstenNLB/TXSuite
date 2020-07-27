package nlb.txs.schnittstelle.MAVIS;

import org.apache.log4j.Logger;

public class RueckmeldeDatenMAVIS 
{
	/**
	 * ZeitMM
	 */
    private String ivZeitMM;
    
    /**
     * BilanzArt
     */
    private String ivBilanzArt;
    
    /**
     * Institut
     */
    private String ivInstitut;
    
    /**
     * FinanzKonto
     */
    private String ivFinanzKonto;
    
    /**
     * AktPas
     */
    private String ivAktPas;
  
    /**
     * Bestandstyp
     */
    private String ivBestandstyp;

    /**
     * Ebene
     */
    private String ivEbene;
   
    /**
     * Produkt
     */
    private String ivProdukt;
    
    /**
     * KZSoma
     */
    private String ivKZSoma;
    
    /**
     * DatumSoma
     */
    private String ivDatumSoma;
   
    /**
     * Depotnummer
     */
    private String ivDepotNR;
    
    /**
     * Kennzeichen (J|N), ob 99 schon einmal zurueckgemeldet wurde.
     */
    private String iv99Zurueckgemeldet;
    
    /**
     * Anlieferungsdatum (JJJJ-MM-TT)
     */
    private String ivAnlieferungsdatum; 

    /**
     * Konstruktor - Initialiserung mit (neuen) leeren Strings
     */
	public RueckmeldeDatenMAVIS() 
	{
		this.ivZeitMM = new String();
		this.ivBilanzArt = new String();
		this.ivInstitut = new String();
		this.ivFinanzKonto = new String();
		this.ivAktPas = new String();
		this.ivBestandstyp = new String();
		this.ivEbene = new String();
		this.ivProdukt = new String();
		this.ivKZSoma = new String();
		this.ivDatumSoma = new String();
		this.ivDepotNR = new String();
		this.iv99Zurueckgemeldet = new String();
		this.ivAnlieferungsdatum = new String();
	}

	/**
	 * 
	 * @return
	 */
	public String getZeitMM() 
	{
		return ivZeitMM;
	}

	/**
	 * 
	 * @param pvZeitMM
	 */
	public void setZeitMM(String pvZeitMM) 
	{
		this.ivZeitMM = pvZeitMM;
	}

	/**
	 * 
	 * @return
	 */
	public String getBilanzArt() 
	{
		return ivBilanzArt;
	}

	/**
	 * 
	 * @param pvBilanzArt
	 */
	public void setBilanzArt(String pvBilanzArt) 
	{
		this.ivBilanzArt = pvBilanzArt;
	}

	/**
	 * 
	 * @return
	 */
	public String getInstitut() 
	{
		return ivInstitut;
	}

	/**
	 * 
	 * @param pvInstitut
	 */
	public void setInstitut(String pvInstitut) 
	{
		this.ivInstitut = pvInstitut;
	}

	/**
	 * 
	 * @return
	 */
	public String getFinanzKonto() 
	{
		return ivFinanzKonto;
	}

	/**
	 * 
	 * @param pvFinanzKonto
	 */
	public void setFinanzKonto(String pvFinanzKonto) 
	{
		this.ivFinanzKonto = pvFinanzKonto;
	}

	/**
	 * 
	 * @return
	 */
	public String getAktPas() 
	{
		return ivAktPas;
	}

	/**
	 * 
	 * @param pvAktPas
	 */
	public void setAktPas(String pvAktPas) 
	{
		this.ivAktPas = pvAktPas;
	}

	/**
	 * 
	 * @return
	 */
	public String getBestandstyp() 
	{
		return ivBestandstyp;
	}

	/**
	 * 
	 * @param pvBestandstyp
	 */
	public void setBestandstyp(String pvBestandstyp) 
	{
		this.ivBestandstyp = pvBestandstyp;
	}

	/**
	 * 
	 * @return
	 */
	public String getEbene() 
	{
		return ivEbene;
	}

	/**
	 * 
	 * @param pvEbene
	 */
	public void setEbene(String pvEbene) 
	{
		this.ivEbene = pvEbene;
	}

	/**
	 * 
	 * @return
	 */
	public String getProdukt() 
	{
		return ivProdukt;
	}

	/**
	 * 
	 * @param pvProdukt
	 */
	public void setProdukt(String pvProdukt) 
	{
		this.ivProdukt = pvProdukt;
	}

	/**
	 * 
	 * @return
	 */
	public String getKZSoma() 
	{
		return ivKZSoma;
	}

	/**
	 * 
	 * @param pvKZSoma
	 */
	public void setKZSoma(String pvKZSoma) 
	{
		this.ivKZSoma = pvKZSoma;
	}

	/**
	 * 
	 * @return
	 */
	public String getDatumSoma() 
	{
		return ivDatumSoma;
	}

	/**
	 * 
	 * @param pvDatumSoma
	 */
	public void setDatumSoma(String pvDatumSoma) {
		this.ivDatumSoma = pvDatumSoma;
	}

	/**
	 * 
	 * @return
	 */
	public String getDepotNR() 
	{
		return ivDepotNR;
	}

	/**
	 * 
	 * @param pvDepotNR
	 */
	public void setDepotNR(String pvDepotNR) 
	{
		this.ivDepotNR = pvDepotNR;
	}

	/**
	 * 
	 * @return
	 */
	public String get99Zurueckgemeldet() 
	{
		return iv99Zurueckgemeldet;
	}

	/**
	 * 
	 * @param pv99Zurueckgemeldet
	 */
	public void set99Zurueckgemeldet(String pv99Zurueckgemeldet) 
	{
		this.iv99Zurueckgemeldet = pv99Zurueckgemeldet;
	}

	/**
	 * 
	 * @return
	 */
	public String getAnlieferungsdatum() 
	{
		return ivAnlieferungsdatum;
	}

	/**
	 * 
	 * @param pvAnlieferungsdatum
	 */
	public void setAnlieferungsdatum(String pvAnlieferungsdatum) 
	{
		this.ivAnlieferungsdatum = pvAnlieferungsdatum;
	}	

	/**
	 * Zerlegt eine Zeile der RueckmeldeDatenMAVIS
     * @param pvZeile
     * @param pvLogger
     */
	public void parseRueckmeldeDaten(String pvZeile, Logger pvLogger)
	{                 
      String lvTemp = new String();  // Arbeitsbereich/Zwischenspeicher Feld
      int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

      // Steuerung/Iteration Eingabesatz
      for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
      {

        // wenn Semikolon erkannt
        if (pvZeile.charAt(lvZzStr) == ';')
        {
            //System.out.println(lvTemp);
            this.setRueckmeldeDaten(lvLfd, lvTemp, pvLogger);
            
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
      
      // Letzten Wert auch noch setzen
      this.setRueckmeldeDaten(lvLfd, lvTemp, pvLogger);
   }

    /**
     * Setzt einen Wert der RueckmeldeDatenMAVIS
     * @param pvPos Position
     * @param pvWert Wert
     */
    private void setRueckmeldeDaten(int pvPos, String pvWert, Logger pvLogger) 
    {
        //System.out.println("Pos: " + pvPos + " Wert:" + pvWert);
        switch (pvPos)
        {
          case 0:
        	  this.ivZeitMM = pvWert;
        	  break;
          case 1:
              this.ivBilanzArt = pvWert;
              break;
          case 2:
              this.ivInstitut = pvWert;
              break;
          case 3:
              this.ivFinanzKonto = pvWert;
              break;
          case 4:
              this.ivAktPas = pvWert;
              break;
          case 5:
              this.ivBestandstyp = pvWert;
              break;
          case 6:
              this.ivEbene = pvWert;
              break;
          case 7:
              this.ivProdukt = pvWert;
              break;
          case 8:
              this.ivKZSoma = pvWert;
              break;
          case 9:
              this.ivDatumSoma = pvWert;
              break;
          case 10:
              this.ivDepotNR = pvWert;
              break;
          case 11:
              this.iv99Zurueckgemeldet = pvWert;
              break;
          case 12:
             this.ivAnlieferungsdatum = pvWert;
             break;
          default:
              pvLogger.error("RueckmeldeDatenMAVIS: Feld " + pvPos + " undefiniert - Wert: " + pvWert);
        }
    }


	/**
	 * Liefert die Daten als String
	 * @return 
	 */
	@Override
	public String toString() 
	{
		StringBuilder lvHelpStringBuilder = new StringBuilder();
		
		lvHelpStringBuilder.append(ivZeitMM + ";");
		lvHelpStringBuilder.append(ivBilanzArt + ";");
		lvHelpStringBuilder.append(ivInstitut + ";");
		lvHelpStringBuilder.append(ivFinanzKonto + ";");
		lvHelpStringBuilder.append(ivAktPas + ";");
		lvHelpStringBuilder.append(ivBestandstyp + ";");
		lvHelpStringBuilder.append(ivEbene + ";");
		lvHelpStringBuilder.append(ivProdukt + ";");
		lvHelpStringBuilder.append(ivKZSoma + ";");
		lvHelpStringBuilder.append(ivDatumSoma + ";");
		lvHelpStringBuilder.append(ivDepotNR + ";");
		if (iv99Zurueckgemeldet.isEmpty())
		{
			lvHelpStringBuilder.append("N;");
		}
		else
		{
			lvHelpStringBuilder.append(iv99Zurueckgemeldet + ";");
		}
		lvHelpStringBuilder.append(ivAnlieferungsdatum);
		//lvHelpStringBuilder.append(StringKonverter.lineSeparator);
		
		return lvHelpStringBuilder.toString();
	}

}
