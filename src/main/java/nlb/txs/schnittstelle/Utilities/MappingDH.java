/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Utilities;

import org.apache.log4j.Logger;

/**
 * Diese Klasse enthaelt die Mappings fuer die DeutscheHypo-Verarbeitung.
 * @author Carsten Tepper
 */
public abstract class MappingDH {

  /**
   * Aendert den DeutscheHypo-Schluessel auf den TXS-Schluessel fuer die Objektgruppe
   *
   * @param pvText DH-Schluessel fuer die Objektgruppe
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer die Objektgruppe
   */
  public static String changeObjektgruppe(String pvText, Logger pvLogger) {
    // 100009	Wohn- / Geschäftsimmobilie	Wohnobjekt, Wohnteil überwiegt            (Altschlüssel)
    // 100005	Eigentumswohnung	Eigentumswohnung
    // 100001	Einfamilienhaus	Einfamilienhaus, freistehend
    // 100011	Handelsimmobilie	Fachmarkt
    // 100012	Sozialimmobilie	Krankenhaus/Reha + Kurklinik
    // 100012	Sozialimmobilie	Altenpflege- / -krankenheim
    // 100002	Zweifamilienhaus	Zweifamilienhaus
    // 100007	Mehrfamilienhaus	MFH mit gewerbl. Fläche
    // 100010	Büro- / Geschäftsimmobilie	Laden und/oder Bürogebäude
    // 100004	Doppelhaushälfte	Doppelhaushälfte
    // 100010	Büro- / Geschäftsimmobilie	Laden-/Büro-/Lagergebäude
    // 100012	Sozialimmobilie	Arztpraxis
    // 100003	Reihenhaus	Reihenhaus
    // 100010	Büro- / Geschäftsimmobilie	Büro(s) mit Lagerhalle(n)
    // 100010	Büro- / Geschäftsimmobilie	Büro- und Verwalt.gebäude (allgem.)
    // 100011	Handelsimmobilie	Einkaufszentrum oder SB-Warenhaus
    // 100007	Mehrfamilienhaus	Mehrfamilienhaus
    // 100010	Büro- / Geschäftsimmobilie	Bürogeb. mit Läden/Gaststätte(n)
    // 100009	Wohn- / Geschäftsimmobilie	Mehrfamilienhausanlage
    // 100013	Beherbergung und Gastronomie	Konferenz- und Businesshotels (ab 100 Zimmer)
    // 100011	Handelsimmobilie	Markt- und Messehalle
    // 100009	Wohn- / Geschäftsimmobilie	MFH Plattenbau
    // 100012	Sozialimmobilie	Altenwohnheim
    // 100011	Handelsimmobilie	Möbelhaus
    // 100011	Handelsimmobilie	Ladengeschäftsgebäude
    // 100011	Handelsimmobilie	Super-/Verbrauchermarkt oder Discounter
    // 100011	Handelsimmobilie	Waren-/Kaufhaus
    // 100013	Beherbergung und Gastronomie	Hotel- und Gaststättengebäude (Sonst.)
    // 100014	Lager- / Logistikimmobilie	Logistikzentrum (mehrnutzerfähig)
    // 100013	Beherbergung und Gastronomie	Stadthotel (mehrnutzerfähig)
    // 100014	Lager- / Logistikimmobilie	Warenlagergebäude
    // 100010	Büro- / Geschäftsimmobilie	Laden/Büro/Lager/Wohngebäude
    // 100010	Büro- / Geschäftsimmobilie	Büro(s)
    // 100013	Beherbergung und Gastronomie	Hotel mit Restaurant
    // 100013	Beherbergung und Gastronomie	Hotel, Garni und Pension
    // 100012	Sozialimmobilie	Betreutes / Servicewohnen
    // 100012	Sozialimmobilie	Altenheim
    // 100018	Spezialimmobilie	Sonstiges Nichtwohngebäude
    // 100015	Gewerbe- / Industrieimmobilie	Werkstattgebäude (allgem.)
    // 100016	Freizeitimmobilie	Sonst. Gebäude Unterhaltung
    // 100011	Handelsimmobilie	Sonstiges Handelsgebäude
    // 100013	Beherbergung und Gastronomie	Ferienhotel
    // 100011	Handelsimmobilie	Factory Outlet Center
    // 100012	Sozialimmobilie	Sonstiges Anstaltsgebäude
    // 100017	Verkehrsimmobilie	Tiefgarage, Parkhaus
    // 100015	Gewerbe- / Industrieimmobilie	Fabrik-/Industriegebäude (allg.)
    // 100009	Wohn- / Geschäftsimmobilie	Appartmentgebäude
    // 100099	Unfertiger / noch nicht ertragsfähiger Neubau	unbebautes Grundstück
    // 100022	Bauplatz	Bauplatz
    // 100009	Wohn- / Geschäftsimmobilie	Appartement (sonstige)
    // 100012	Sozialimmobilie	Schule/Seminargebäude
    // 100016	Freizeitimmobilie	Freizeit- und Gemeinschaftsgebäude
    // 100013	Beherbergung und Gastronomie	Hotelanlage mit mehreren Kategorien
    // 100013	Beherbergung und Gastronomie	Gaststätte ohne Beherbergung
    // 100013	Beherbergung und Gastronomie	Aparthotel

    // Default 'Unbekannt' - Damit eine Belieferung moeglich ist
    String lvHelpText = "Unbekannt"; // Unbekannt
    //pvLogger.info("pvText: " + pvText);

    // 100001
    if (pvText.equals("Einfamilienhaus, freistehend")) {
      lvHelpText = "100001";
    }

    // 100002
    if (pvText.equals("Zweifamilienhaus")) {
      lvHelpText = "100002";
    }

    // 100003
    if (pvText.equals("Reihenhaus")) {
      lvHelpText = "100003";
    }

    // 100004
    if (pvText.equals("Doppelhaushälfte")) {
      lvHelpText = "100004";
    }

    // 100005
    if (pvText.equals("Eigentumswohnung")) {
      lvHelpText = "100005";
    }

    // 100007
    if (pvText.equals("MFH mit gewerbl. Fläche") || pvText.equals("Mehrfamilienhaus")) {
      lvHelpText = "100007";
    }

    // 100009
    if (pvText.equals("Wohnobjekt, Wohnteil überwiegt            (Altschlüssel)") ||
        pvText.equals("Mehrfamilienhausanlage") ||
        pvText.equals("MFH Plattenbau") ||
        pvText.equals("Appartmentgebäude") ||
        pvText.equals("Wohnhochhaus") ||
        pvText.equals("Appartement (sonstige)")) {
      lvHelpText = "100009";
    }

    // 100010
    if (pvText.equals("Laden und/oder Bürogebäude") ||
        pvText.equals("Laden-/Büro-/Lagergebäude") ||
        pvText.equals("Büro(s) mit Lagerhalle(n)") ||
        pvText.equals("Büro- und Verwalt.gebäude (allgem.)") ||
        pvText.equals("Bürogeb. mit Läden/Gaststätte(n)") ||
        pvText.equals("Laden/Büro/Lager/Wohngebäude") ||
        pvText.equals("Büro(s)")) {
      lvHelpText = "100010";
    }

    // 100011
    if (pvText.equals("Fachmarkt") ||
        pvText.equals("Einkaufszentrum oder SB-Warenhaus") ||
        pvText.equals("Markt- und Messehalle") ||
        pvText.equals("Möbelhaus") ||
        pvText.equals("Ladengeschäftsgebäude") ||
        pvText.equals("Super-/Verbrauchermarkt oder Discounter") ||
        pvText.equals("SB-Warenhaus") ||
        pvText.equals("Waren-/Kaufhaus") ||
        pvText.equals("Sonstiges Handelsgebäude") ||
        pvText.equals("Handelsgebäude (mit Mischnutzung)") ||
        pvText.equals("Super-/Verbrauchermarkt") ||
        pvText.equals("Waren- oder Kaufhaus / Modehaus") ||
        pvText.equals("Factory Outlet Center")) {
      lvHelpText = "100011";
    }

    // 100012
    if (pvText.equals("Krankenhaus/Reha + Kurklinik") ||
        pvText.equals("Altenpflege- / -krankenheim") ||
        pvText.equals("Arztpraxis") ||
        pvText.equals("Altenwohnheim") ||
        pvText.equals("Betreutes / Servicewohnen") ||
        pvText.equals("Altenheim") ||
        pvText.equals("Sonstiges Anstaltsgebäude") ||
        pvText.equals("Sonstiges Schulgebäude") ||
        pvText.equals("Schule/Seminargebäude")) {
      lvHelpText = "100012";
    }

    // 100013
    if (pvText.equals("Konferenz- und Businesshotels (ab 100 Zimmer)") ||
        pvText.equals("Hotel- und Gaststättengebäude (Sonst.)") ||
        pvText.equals("Stadthotel (mehrnutzerfähig)") ||
        pvText.equals("Hotel mit Restaurant") ||
        pvText.equals("Hotel, Garni und Pension") ||
        pvText.equals("Ferienhotel") ||
        pvText.equals("Hotelanlage mit mehreren Kategorien") ||
        pvText.equals("Gaststätte ohne Beherbergung") ||
        pvText.equals("Aparthotel")) {
      lvHelpText = "100013";
    }

    // 100014
    if (pvText.equals("Logistikzentrum (mehrnutzerfähig)") || pvText.equals("Warenlagergebäude")) {
      lvHelpText = "100014";
    }

    // 100015
    if (pvText.equals("Werkstattgebäude (allgem.)") || pvText
        .equals("Fabrik-/Industriegebäude (allg.)")) {
      lvHelpText = "100015";
    }

    // 100016
    if (pvText.equals("Sonst. Gebäude Unterhaltung") || pvText
        .equals("Freizeit- und Gemeinschaftsgebäude")) {
      lvHelpText = "100016";
    }

    // 100017
    if (pvText.equals("Tiefgarage, Parkhaus")) {
      lvHelpText = "100017";
    }

    // 100018
    if (pvText.equals("Sonstiges Nichtwohngebäude")) {
      lvHelpText = "100018";
    }

    // 100022
    if (pvText.equals("Bauplatz")) {
      lvHelpText = "100022";
    }

    // 100099
    if (pvText.equals("unbebautes Grundstück")) {
      lvHelpText = "100099";
    }

    if (lvHelpText.equals("Unbekannt")) {
      pvLogger.error("Unbekannte Objektgruppe: " + pvText);
    }

    return lvHelpText;
  }


  /**
   * Aendert den DeutscheHypo-Schluessel auf den TXS-Schluessel fuer die Nutzungsart
   *
   * @param pvText DH-Schluessel fuer die Nutzungsart
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer die Nutzungsart
   */
  public static String changeNutzungsart(String pvText, Logger pvLogger) {
    // 1	Wohnwirtschaftlich	ausschl. Wohnzw., Eigennutzung
    // 1	Wohnwirtschaftlich	ausschl. Wohnzwecke, Vermietung
    // 2	Gewerblich	auss.gewerbl.Nutzung,Vermietung
    // 2	Gewerblich	gew. Teil überwiegt,Vermietung
    // 1	Wohnwirtschaftlich	Wohnteil überwiegt, Vermietung
    // 1	Wohnwirtschaftlich	ausschl. Wohnzw.,ü.50%eigengen.
    // 1	Wohnwirtschaftlich	Wohnteil überwiegt,ü.50%eigeng.
    // 2	Gewerblich	gew.Teil überwiegt,ü.50%vermiet.
    // 2	Gewerblich	auss.gewerbl.Nutzung, Eigennut.
    // 2	Gewerblich	auss.gew.Nutzung,ü. 50%vermiet.
    // 1	Wohnwirtschaftlich	Wohnteil überwiegt,ü.50%vermiet.
    // 2	Gewerblich	auss.gew.Nutzung,ü.50%eigengen.
    // 5	Sonstige Grundstücke	gemeinnützig,sonstige Nutzung

    // Default 'Ohne Zuordnung' - Damit eine Belieferung moeglich ist
    String lvHelpText = "0"; // Ohne Zuordnung

    // Wohnwirtschaftlich
    if (pvText.equals("ausschl. Wohnzw., Eigennutzung") ||
        pvText.equals("ausschl. Wohnzwecke, Vermietung") ||
        pvText.equals("Wohnteil überwiegt, Vermietung") ||
        pvText.equals("ausschl. Wohnzw.,ü.50%eigengen.") ||
        pvText.equals("Wohnteil überwiegt,ü.50%eigeng.") ||
        pvText.equals("Wohnteil überwiegt,ü.50%vermiet.")) {
      lvHelpText = "1";
    }

    // Gewerblich
    if (pvText.equals("auss.gewerbl.Nutzung,Vermietung") ||
        pvText.equals("gew. Teil überwiegt,Vermietung") ||
        pvText.equals("gew.Teil überwiegt,ü.50%vermiet.") ||
        pvText.equals("auss.gewerbl.Nutzung, Eigennut.") ||
        pvText.equals("auss.gew.Nutzung,ü. 50%vermiet.") ||
        pvText.equals("auss.gew.Nutzung,ü.50%eigengen.")) {
      lvHelpText = "2";
    }

    // Sonstige Grundstuecke
    if (pvText.equals("gemeinnützig,sonstige Nutzung")) {
      lvHelpText = "5";
    }

    return lvHelpText;
  }

  /**
   * Aendert den DeutscheHypo-Schluessel auf den TXS-Schluessel fuer den Refzins
   *
   * @param pvText DH-Schluessel fuer den Refzins
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer den Refzins
   */
  public static String changeRefzins(String pvText, Logger pvLogger) {

    // Default 'keine' - Damit eine Belieferung moeglich ist
    String lvHelpText = "keine";

    if (pvText.equals("EURIBOR 1 Mon.")) {
      lvHelpText = "EURIBOR1MD";
    }

    if (pvText.equals("EURIBOR 3 Mon.")) {
      lvHelpText = "EURIBOR3MD";
    }

    if (pvText.equals("EURIBOR 6 Mon.")) {
      lvHelpText = "EURIBOR6MD";
    }

    if (pvText.equals("LIBOR CHF 3 Mon.")) {
      lvHelpText = "CHFLIBOR3MD";
    }

    if (pvText.equals("LIBOR CHF 6 Mon.")) {
      lvHelpText = "CHFLIBOR6MD";
    }

    if (pvText.equals("LIBOR GBP 3 Mon.")) {
      lvHelpText = "LIGBP3MD";
    }

    return lvHelpText;
  }

  /**
   * Aendert den DeutscheHypo-Schluessel auf den TXS-Schluessel fuer das Gebiet
   *
   * @param pvText DH-Schluessel fuer das Gebiet
   * @param pvLogger log4j-Logger
   * @return TXS-Schluessel fuer das Gebiet
   */
  public static String changeGebiet(String pvText, Logger pvLogger) {
    String lvHelpText = "";

    // Gebiete in NL
    if (pvText.equals("Flevoland"))
    {
      lvHelpText = "NL_02";
    }

    if (pvText.equals("Friesland"))
    {
      lvHelpText = "NL_03";
    }

    if (pvText.equals("Gelderland (mit Arnheim)"))
    {
      lvHelpText = "NL_04";
    }

    if (pvText.equals("Groningen"))
    {
      lvHelpText = "NL_05";
    }

    if (pvText.equals("Limburg (NL)"))
    {
      lvHelpText = "NL_06";
    }


    if (pvText.equals("Noord-Brabant (mit Eindhovn, Denbosch)"))
    {
      lvHelpText = "NL_07";
    }

    if (pvText.equals("Amsterdam") || (pvText.equals("Noord-Holland (ohne Amsterdam)") || pvText.equals("Noord-Nederland        (bitte nicht auswählen)")))
    {
      lvHelpText = "NL_08";
    }

    if (pvText.equals("Overijssel"))
    {
      lvHelpText = "NL_09";
    }

    if (pvText.equals("Utrecht"))
    {
      lvHelpText = "NL_10";
    }

    if (pvText.equals("Zeeland"))
    {
      lvHelpText = "NL_11";
    }

    if (pvText.equals("Zuid-Holland"))
    {
      lvHelpText = "NL_12";
    }

    // Gebiete in AT
    if (pvText.equals("Wien"))
    {
      lvHelpText = "AT_W";
    }

    // Gebiete in BE
    if (pvText.equals("Rég.Bruxelles-Cap./Brussels Hfdst.Gew"))
    {
      lvHelpText = "BE_BE1";
    }

    if (pvText.equals("Antwerpen"))
    {
      lvHelpText = "BE_01";
    }

    if (pvText.equals("Limburg (B)"))
    {
      lvHelpText = "BE_05";
    }

    // Gebiete in ES
    if (pvText.equals("Comunidad de Madrid"))
    {
      lvHelpText = "ES_28";
    }

    if (pvText.equals("Canarias"))
    {
      lvHelpText = "ES_59";
    }

    if (pvText.equals("Barcelona"))
    {
      lvHelpText = "ES_08";
    }

    // Gebiete in FR

    // Gebiete in LU
    if (pvText.equals("Luxembourg (Grand-Duché)"))
    {
      lvHelpText = "LU_01";
    }

    // Gebiete in US
    if (pvText.equals("Dist. Of Columbia"))
    {
      lvHelpText = "US_DC";
    }

    // Gebiete in IE
    if (pvText.contains("Dublin"))
    {
      lvHelpText = "IE_DB";
    }

    if (lvHelpText.isEmpty())
    {
      pvLogger.error("Unbekanntes Gebiet: " + pvText);
    }

    return lvHelpText;
  }

}
