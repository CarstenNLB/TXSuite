/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import org.junit.Test;

public class TestGrundbuchverweis {

  @Test
  public void testParseGrundbuchverweis() {
    Grundbuchverweis lvGrundbuchverweis = new Grundbuchverweis(null);
    String lvZeile = new String("15\tID\tLastID\tGrundbuchblattID\tLaufendeNrAbt3\tLoeschkennzeichen\tSicherheitenvereinbarungID");
    lvGrundbuchverweis.parseGrundbuchverweis(lvZeile, SicherheitenDaten.SAPCMS);

    //assertEquals("ID" , lvGrundbuchverweis.getId());
    //assertEquals("LastID" , lvGrundbuchverweis.getLastId());
    //assertEquals("GrundbuchblattID" , lvGrundbuchverweis.getGrundbuchblattId());
    //assertEquals("LaufendeNrAbt3" , lvGrundbuchverweis.getLaufendeNrAbt3());
    //assertEquals("Loeschkennzeichen" , lvGrundbuchverweis.getLoeschkennzeichen());
    //assertEquals("SicherheitenvereinbarungID" , lvGrundbuchverweis.getSicherheitenvereinbarungId());
  }

}
