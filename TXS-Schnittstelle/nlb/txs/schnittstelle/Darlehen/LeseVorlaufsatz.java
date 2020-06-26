/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Darlehen;

import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;

/**
 * @author tepperc
 *
 */
public class LeseVorlaufsatz 
{    
    
    /**
     * Zerlegt eine Zeile in die einzelnen Felder
     * @param pvZeile die zu zerlegende Zeile
     * @return Vorlaufsatz
     * 
     */
   public DWHVOR parseVorlaufsatz(String pvZeile)
   {
     String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
     int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart

     DWHVOR lvVorlaufsatz = new DWHVOR();
     
     // steuerung/iteration eingabesatz
     for (int lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
     {

       // wenn semikolon erkannt
       if (pvZeile.charAt(lvZzStr) == ';')
       {
           // Die ersten drei Felder koennen ueberlesen werden...
           if (lvLfd > 3)
           {
               //stemp = stemp;
               setVorlaufsatz(lvVorlaufsatz, lvLfd, lvTemp);
           }
       
           lvLfd++;                  // naechste feldnummer
       
           // loeschen zwischenbuffer
           lvTemp = new String();

       }
       else
       {
           // uebernehmen byte aus eingabesatzposition
           lvTemp = lvTemp + pvZeile.charAt(lvZzStr);
       }
     } // ende for
     
     return lvVorlaufsatz;
   }
   
   /**
    * Setzt einen Wert des Vorlaufsatzes
    * @param pvPos Position
    * @param pvWert Wert
    */
   private void setVorlaufsatz(DWHVOR pvVorlaufsatz, int pvPos, String pvWert)
   {
       if (pvPos > 10)
       {
         switch (pvPos)
         {
           case 11:
               pvVorlaufsatz.setsDwvinst(pvWert);
               break;
           case 12:
               pvVorlaufsatz.setsDwvanw(pvWert);
               break;
           case 13:
               pvVorlaufsatz.setsDwvstyp(pvWert);
               break;
           case 14:
               pvVorlaufsatz.setsDwvbdat(pvWert);
               break;
           case 15:
               pvVorlaufsatz.setsDwvbmon(pvWert);
               break;
           case 16:
               pvVorlaufsatz.setsDwvdat(pvWert);
               break;
           case 17:
               pvVorlaufsatz.setsDwvuhr(pvWert);
               break;
           case 18:
               pvVorlaufsatz.setsDwvver(pvWert);
               break;
           case 19:
               pvVorlaufsatz.setsDwvtyp(pvWert);
               break;
           default:
               System.out.println("Vorlaufsatz: unbekannte Pos:" + pvPos + " Wert:" + pvWert);
         }
       }
   }

}
