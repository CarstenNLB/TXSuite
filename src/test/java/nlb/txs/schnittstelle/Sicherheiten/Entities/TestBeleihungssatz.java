/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Sicherheiten.Entities;

import static org.junit.Assert.assertEquals;

import nlb.txs.schnittstelle.Sicherheiten.SicherheitenDaten;
import org.junit.Test;

public class TestBeleihungssatz {

  @Test
  public void testParseBeleihungssatz() {
    Beleihungssatz lvBeleihungssatz = new Beleihungssatz(null);
    String lvZeile = new String("49\tID\tObjektteilID\tTeilID\tImmobilieID\tGrundstueckID");
    lvBeleihungssatz.parseBeleihungssatz(lvZeile, SicherheitenDaten.SAPCMS);

    assertEquals("ID" , lvBeleihungssatz.getId());
    assertEquals("ObjektteilID" , lvBeleihungssatz.getObjektteilId());
    assertEquals("TeilID" , lvBeleihungssatz.getTeilId());
    assertEquals("ImmobilieID" , lvBeleihungssatz.getImmobilieId());
    assertEquals("GrundstueckID" , lvBeleihungssatz.getGrundstueckId());
  }
}
