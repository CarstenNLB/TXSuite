/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Wertpapier.Bestand;

import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class DepotSummierung
{
  /**
   * Liste der RueckmeldeDaten
   */
  private HashMap<String, DepotSummierungDaten> ivListeDepotSummierungDaten;

  /**
   * log4j-Logger
   */
  private Logger ivLogger;

  /**
   * Konstruktor
   */
  public DepotSummierung(Logger pvLogger)
  {
     ivLogger = pvLogger;
     ivListeDepotSummierungDaten = new HashMap<String, DepotSummierungDaten>();
  }

  /**
   * Fuegt eine Summierung hinzu
   */
  public void addDepotSummierungDaten(String pvISIN, String pvDepotnummer, String pvNominalbetrag)
  {
    if (ivListeDepotSummierungDaten.containsKey(pvISIN))
    {
      DepotSummierungDaten lvDepotSummierungDaten = ivListeDepotSummierungDaten.get(pvISIN);
      lvDepotSummierungDaten.setISIN(pvISIN);
      lvDepotSummierungDaten.setDepotnummer(lvDepotSummierungDaten.getDepotnummer() + "_" + pvDepotnummer);
      BigDecimal lvNominalbetrag = new BigDecimal(lvDepotSummierungDaten.getNominalbetrag());
      lvNominalbetrag.add(new BigDecimal(pvNominalbetrag));
      lvDepotSummierungDaten.setNominalbetrag(lvNominalbetrag.toPlainString());
      ivLogger.info("Doppelte ISIN - Depotnummer: " + lvDepotSummierungDaten.getDepotnummer());
    }
    else
    {
      DepotSummierungDaten lvDepotSummierungDaten = new DepotSummierungDaten();
      lvDepotSummierungDaten.setISIN(pvISIN);
      lvDepotSummierungDaten.setDepotnummer(pvDepotnummer);
      lvDepotSummierungDaten.setNominalbetrag(pvNominalbetrag);
      ivListeDepotSummierungDaten.put(pvISIN, lvDepotSummierungDaten);
    }
  }

  /**
   * Schreibt alle Depotsummierungen in einen String
   * @return
   */
  public String toString()
  {
     StringBuilder lvResultString = new StringBuilder();
     for (DepotSummierungDaten  lvDepotSummierungDaten :  ivListeDepotSummierungDaten.values())
     {
        lvResultString.append(lvDepotSummierungDaten.toString());
     }
     return lvResultString.toString();
  }
}
