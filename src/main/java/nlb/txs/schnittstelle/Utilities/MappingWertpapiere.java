/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Utilities;

/**
 * Diese Klasse enthaelt die Mappings fuer die Wertpapier-Verarbeitung.
 * @author Carsten Tepper
 */
public abstract class MappingWertpapiere
{

  /**
   * Ermittelt den Zahlungstyp
   * @param pvGD841 Gattungsdatenfeld 841
   * @return TXS-Schluessel fuer den Zahlungstyp
   */
  public static String ermittleZahlungstyp(String pvGD841)
  {
    // 1	Annuitaet
    // 3	Endfaellig
    // 0	Keine
    // 2	Raten
    // 9	Sonderfall

    // Default -> Sonderfall
    String lvZahlungstyp = "9";
    // 2 -> Endfaellig/Festdarlehen
    if (pvGD841.equals("2"))
    {
      lvZahlungstyp = "3";
    }
    // 3,4,8,B,D,E und G -> Annuitaet/Tildungsdarlehen
    if (pvGD841.equals("3") || pvGD841.equals("4") || pvGD841.equals("8") || pvGD841.equals("B") || pvGD841.equals("D") || pvGD841.equals("E") || pvGD841.equals("G"))
    {
      lvZahlungstyp = "1";
    }
    // 2 -> Raten/Abzahlungsdarlehen
    if (pvGD841.equals("5"))
    {
      lvZahlungstyp = "2";
    }

    return lvZahlungstyp;
  }

  /**
   * Ermittelt die Zinsabweichung
   * @param pvGD321 Gattungsdatenfeld 321
   * @return TXS-Schluessel fuer die Zinsabweichung
   */
  public static String ermittleZinsabweichung(String pvGD321)
  {
    // TXS
    // 0		Keine
    // 1		Kurzzahlung
    // 2		Ueberlange Zahlung

    // Mapping
    // 11		kurz
    // 12		lang
    // 15		lang
    // 18		kurz
    // 19		kurz
    // 21		kurz
    // 22		lang
    // 23		lang
    // 25		lang
    // 26		kurz
    // 27		kurz
    // 30		lang
    // 31		kurz
    // 32		lang
    // 33		kurz
    // 34		lang
    // 35		kurz
    // 36		lang
    // sonst	keine

    // Default -> Keine
    String lvZinsabweichung = "0";
    // 11,18,19,21,26,27,31,33 und 35 -> Kurzzahlung
    if (pvGD321.equals("11") || pvGD321.equals("18") || pvGD321.equals("19") || pvGD321.equals("21") || pvGD321.equals("26") || pvGD321.equals("27") || pvGD321.equals("31") || pvGD321.equals("33") || pvGD321.equals("35"))
    {
      lvZinsabweichung = "1";
    }
    // 12,15,22,23,25,30,32,34 und 36 -> Ueberlange Zahlung
    if (pvGD321.equals("12") || pvGD321.equals("15") || pvGD321.equals("22") || pvGD321.equals("23") || pvGD321.equals("25") || pvGD321.equals("30") || pvGD321.equals("32") || pvGD321.equals("34") || pvGD321.equals("36"))
    {
      lvZinsabweichung = "2";
    }

    return lvZinsabweichung;
  }

  /**
   * Ermittelt den Zinsrythmus
   * @param pvGD311A Gattungsdatenfeld 311A
   * @param pvGD811 Gattungsdatenfeld 811
   * @return TXS-Schluessel fuer den Zinsrythmus
   */
  public static String ermittleZinsrythmus(String pvGD311A, String pvGD811)
  {
    // GD311A	GD811		Output
    // 14,15	 		    	0
    //		 	0			1
    // 			1			12
    //		 	2			6
    // 			3			4
    //		 	4			3
    //		 	5			9
    //		 	6			2
    //		 	A			5
    //		 	B			7
    //		 	C			8
    // 			D			10
    // 			E			11
    //			<> blank 	13

    String lvZinsrythmus = new String();
    if (pvGD311A.equals("14") || pvGD311A.equals("15"))
    {
      lvZinsrythmus = "0";
    }
    else
    {
      if (!pvGD811.isEmpty())
      {
        lvZinsrythmus = "13";

        if (pvGD811.equals("0"))
        {
          lvZinsrythmus = "1";
        }
        if (pvGD811.equals("1"))
        {
          lvZinsrythmus = "12";
        }
        if (pvGD811.equals("2"))
        {
          lvZinsrythmus = "6";
        }
        if (pvGD811.equals("3"))
        {
          lvZinsrythmus = "4";
        }
        if (pvGD811.equals("4"))
        {
          lvZinsrythmus = "3";
        }
        if (pvGD811.equals("5"))
        {
          lvZinsrythmus = "9";
        }
        if (pvGD811.equals("6"))
        {
          lvZinsrythmus = "2";
        }
        if (pvGD811.equals("A"))
        {
          lvZinsrythmus = "5";
        }
        if (pvGD811.equals("B"))
        {
          lvZinsrythmus = "7";
        }
        if (pvGD811.equals("C"))
        {
          lvZinsrythmus = "8";
        }
        if (pvGD811.equals("D"))
        {
          lvZinsrythmus = "10";
        }
        if (pvGD811.equals("E"))
        {
          lvZinsrythmus = "11";
        }
      }
    }

    return lvZinsrythmus;
  }

  /**
   * Ermittelt den Zinstyp
   * @param pvGD805 Gattungsdatenfeld 805
   * @return TXS-Schluessel fuer den Zinstyp
   */
  public static String ermittleZinstyp(String pvGD805)
  {
    //GD805		TXS-Wert		TXS-Schluessel
    // F		fest            1
    // H		staffZins       5
    // R		revFloater      3
    // V		variabel        2
    // Z		zeroBond        4
    // sonst	undefiniert

    String lvZinstyp = new String();

    if (pvGD805.equals("F"))
    {
      lvZinstyp = "1";
    }
    if (pvGD805.equals("H"))
    {
      lvZinstyp = "5";
    }
    if (pvGD805.equals("R"))
    {
      lvZinstyp = "3";
    }
    if (pvGD805.equals("V"))
    {
      lvZinstyp = "2";
    }
    if (pvGD805.equals("Z"))
    {
      lvZinstyp = "4";
    }

    return lvZinstyp;

  }

  /**
   * Ermittelt die Zinszahlart
   * @param pvGD805 Gattungsdatenfeld 805
   * @param pvGD312 Gattungsdatenfeld 312
   * @return TXS-Schluessel fuer die Zinszahlart
   */
  public static String ermittleZinszahlart(String pvGD805, String pvGD312)
  {
    // Wenn (GD805 = �Z� )  -> keine
    // sonst
    // Input-GD312 	| Output
    // 4 			| vorschuessig
    // 7 			| vorschuessig
    // 1 			| nachschuessig
    // 6 			| nachschuessig
    // sonst 		| keine

    // TXS-Schluessel		TXS-Text
    // 0					Keine
    // 1					Nachschuessig
    // 2					Vorschuessig

    // Default 'Keine'
    String lvZinszahlart = "0";
    if (!pvGD805.equals("Z"))
    {
      if (pvGD312.equals("4") || pvGD312.equals("7"))
      {
        lvZinszahlart = "2";
      }
      if (pvGD312.equals("1") || pvGD312.equals("6"))
      {
        lvZinszahlart = "1";
      }
    }

    return lvZinszahlart;
  }

  /**
   * Ermittelt die FixingKonvention
   * @param pvGD809C Gattungsdatenfeld 809C
   * @return TXS-Schluessel fuer die FixingKonvention
   */
  public static String ermittleFixingKonvention(String pvGD809C)
  {
    // Input-GD809C		Output			TXS-Schluessel
    // 1				Beginn  		1
    // 3				Beginn          1
    // 2				Ende            2
    // 4				Ende            2
    // 5				Beginn          1
    // 6				Ende            2
    // sonst			keine			0

    // Default 'keine'
    String lvFixingKonvention = "0";

    if (pvGD809C.equals("1") || pvGD809C.equals("3") || pvGD809C.equals("5"))
    {
      lvFixingKonvention = "1";
    }
    if (pvGD809C.equals("2") || pvGD809C.equals("4") || pvGD809C.equals("6"))
    {
      lvFixingKonvention = "2";
    }

    return lvFixingKonvention;
  }

  /**
   * Ermittelt die Kalenderkonvention
   * @param pvGD821B Gattungsdatenfeld 821B
   * @return TXS-Schluessel der Kalenderkonvention
   */
  public static String ermittleKalenderkonvention(String pvGD821B)
  {
    // Leer -> 0
    // 03 -> act_360
    // 09 -> act_act_isma
    // 01 -> 30_360
    // 06 -> 30e_360
    // 04 -> act_365f

    String lvKalenderkonvention = new String();
    if (pvGD821B.isEmpty())
    {
      lvKalenderkonvention = "0";
    }
    else
    {
      if (pvGD821B.equals("01"))
        lvKalenderkonvention = "30_360";
      if (pvGD821B.equals("03"))
        lvKalenderkonvention = "act_360";
      if (pvGD821B.equals("04"))
        lvKalenderkonvention = "act_365f";
      if (pvGD821B.equals("06"))
        lvKalenderkonvention = "30e_360";
      if (pvGD821B.equals("09"))
        lvKalenderkonvention = "act_act_isma";
    }

    return lvKalenderkonvention;
  }

  /**
   * Ermittelt den Referenzzins
   * @param pvGD808 Gattungsdatenfeld 808
   * @param pvGD808A Gattungsdatenfeld 808A
   * @param pvGD808B Gattungsdatenfeld 808B
   * @return TXS-Schluessel fuer den Referenzzins
   */
  public static String ermittleReferenzzins(String pvGD808, String pvGD808A, String pvGD808B)
  {
    // GD808B	GD808A	GD808	Code833
    //							BOT6M
    //							LIGBP1MD
    // 3		C		23		LIEUR3MD
    // 6		C		23		LIEUR6MD
    // 1		C		23		LIEUR1MD
    // 9		C		23		LIEUR9MD
    // 12		C		23		LIEUR1YD
    // 2		C		23		LIEUR2MD
    //	 	 					LIGBP1WKD
    //	 	 					LIGBP2MD
    //	 	 					LIGBP6MD
    // 12		C		52		REX10Y
    // 1		B		23		LIEUR1WKD
    //	 	 					LIGBP3MD
    //	 	 					LIGBP1YD
    // 3		B		36		EURIBOR3WD
    // 					64		EONIA
    // 2		B		36		EURIBOR2WD
    //	 	 					LIGBP9MD
    // 1		C		36		EURIBOR1MD
    // 12		C		36		EURIBOR1YMD
    // 2		C		36		EURIBOR2MD
    // 3		C		36		EURIBOR3MD
    // 5		C		36		EURIBOR5MD
    // 6		C		36		EURIBOR6MD
    // 7		C		36		EURIBOR7MD
    // 8		C		36		EURIBOR8MD
    // 11		C		36		EURIBOR11MD
    // 9		C		36		EURIBOR9MD
    // 1		B		36		EURIBORSWD
    // 10		C		36		EURIBOR10MD
    // 4		C		36		EURIBOR4MD
    //					<> 23, 36, 52, 64	Sonderfall
    //	 				blank	keine

    // Default 'Sonderfall'
    String lvReferenzzins = "Sonderfall";
    if (pvGD808.equals("23"))
    {
      if (pvGD808A.equals("C"))
      {
        lvReferenzzins = "LIEUR" + pvGD808B + "MD";
        if (pvGD808B.equals("12"))
        {
          lvReferenzzins = "LIEUR1YD";
        }
      }
      if (pvGD808A.equals("B"))
      {
        if (pvGD808B.equals("1"))
        {
          lvReferenzzins = "LIEUR1WKD";
        }
      }
    }
    if (pvGD808.equals("36"))
    {
      if (pvGD808A.equals("C"))
      {
        lvReferenzzins = "EURIBOR" + pvGD808B + "MD";
        if (pvGD808B.equals("12"))
        {
          lvReferenzzins = "EURIBOR1YMD";
        }
      }
      if (pvGD808A.equals("B"))
      {
        lvReferenzzins = "EURIBOR" + pvGD808B + "WD";
        if (pvGD808B.equals("1"))
        {
          lvReferenzzins = "EURIBORSWD";
        }
      }
    }
    if (pvGD808.equals("52"))
    {
      lvReferenzzins = "REX10Y";
    }
    if (pvGD808.equals("64"))
    {
      lvReferenzzins = "EONIA";
    }

    return lvReferenzzins;
  }

}
