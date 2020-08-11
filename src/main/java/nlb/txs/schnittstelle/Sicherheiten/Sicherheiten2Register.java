/*
 * Copyright © NORD/LB Norddeutsche Landesbank Girozentrale, Hannover - Alle Rechte vorbehalten -
 */

package nlb.txs.schnittstelle.Sicherheiten;

import nlb.txs.schnittstelle.Utilities.ObjekteListe;

public abstract interface Sicherheiten2Register {

  /**
   * Liefert die Sicherheit Hypothek in einem StringBuffer
   * @param pvKontonummer Kontonummer
   * @param pvPassivkontonummer Passivkontonummer
   * @param pvKundennummer Kundennummer
   * @param pvKredittyp Kredittyp
   * @param pvBuergschaftprozent Buergschaftprozent
   * @param pvQuellsystem Quellsystem
   * @param pvInstitutsnummer Institutsnummer
   * @param pvMappingRueckmeldungListe
   */
  public abstract StringBuffer importSicherheitHypotheken(String pvKontonummer, String pvPassivkontonummer, String pvKundennummer, String pvKredittyp, String pvBuergschaftprozent, String pvQuellsystem, String pvInstitutsnummer, ObjekteListe pvMappingRueckmeldungListe);

  /**
   * Liefert die Sicherheit Buergschaft in einem StringBuffer
   * @param pvKontonummer Kontonummer
   * @param pvQuellsystem Quellsystem
   * @param pvRestkapital Restkapital
   * @param pvBuergschaftprozent Buergschaftprozent
   * @param pvAusplatzierungsmerkmal Ausplatzierungsmerkmal
   * @param pvNominalbetrag Nominalbetrag
   * @param pvKundennummer Kundennummer
   * @param pvBuergennummer Buergennummer
   * @param pvInstitutsnummer Institutsnummer
   */
  public abstract StringBuffer importSicherheitBuergschaft(String pvKontonummer, String pvQuellsystem, String pvRestkapital, String pvBuergschaftprozent, String pvAusplatzierungsmerkmal, String pvNominalbetrag, String pvKundennummer, String pvBuergennummer, String pvInstitutsnummer);

  /**
   * Liefert die Sicherheit Schiff in einem StringBuffer
   * @param pvKontonummer Kontonummer
   * @param pvQuellsystem Quellsystem
   * @param pvInstitut Institutsnummer (009/004 oder NLB/BLB)
   */
  public abstract StringBuffer importSicherheitSchiff(String pvKontonummer, String pvQuellsystem, String pvInstitut);

  /**
   * Liefert die Sicherheit Flugzeug in einem StringBuffer
   * @param pvKontonummer Kontonummer
   * @param pvQuellsystem Quellsystem
   * @param pvInstitut Institutsnummer (009/004 oder NLB/BLB)
   */
  public abstract StringBuffer importSicherheitFlugzeug(String pvKontonummer, String pvQuellsystem, String pvInstitut);
}
