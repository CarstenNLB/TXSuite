/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package de.nordlbit.prost.FilePing;

import de.nordlbit.prost.bd.Mandant;

/**
 * @author tepperc
 *
 */
public class FilePingTask 
{
  /**
   * Dateiname
   */
  private String ivDateiname;
  
  /**
   * Vorgang-ID
   */
  private String ivVorgangID;
  
  /**
   * Mandanten-ID 
   */
  private String ivMandantenID;
  
  /**
   * Ereignis-ID
   */
  private String ivEreignisID;
  
  /**
   * AufgabenVektor-ID
   */
  private String ivAufgabenVektorID;
  
  /**
   * Mandant
   */
  private Mandant ivMandant;
  
  /**
   * Logging
   */
  //private Logging ivLogging;
  
  /**
   * Groesse der Datei
   */
  private long ivFileSize;
  
  /**
   * @param pvDateiname
   * @param pvVorgangID
   * @param pvMandantenID
   * @param pvEreignisID
   * @param pvAufgabenVektorID
   * @param pvMandant
   */
  public FilePingTask(String pvDateiname, String pvVorgangID, String pvMandantenID, String pvEreignisID, String pvAufgabenVektorID, Mandant pvMandant) 
  {
    this.ivDateiname = pvDateiname;
    this.ivVorgangID = pvVorgangID;
    this.ivMandantenID = pvMandantenID;
    this.ivEreignisID = pvEreignisID;
    this.ivAufgabenVektorID = pvAufgabenVektorID;
    this.ivMandant = pvMandant;
    //ivLogging = new Logging();   
    //ivLogging.openLog("F:\\TXS_" + pvMandant.getInstitutsId() + "_PROD\\PROTOKOLL\\LB\\FilePing_" + pvVorgangID + "_" + pvAufgabenVektorID + "_" + pvEreignisID + ".log");
    this.ivFileSize = 0;
  }

  /**   
   * @return the dateiname
   */
  public String getDateiname() {
    return this.ivDateiname;
  }

  /**
   * @param pvDateiname the dateiname to set
   */
  public void setDateiname(String pvDateiname) {
      this.ivDateiname = pvDateiname;
  }

  /**
   * @return the vorgangID
   */
  public String getVorgangID() {
    return this.ivVorgangID;
  }

  /**
   * @param pvVorgangID the vorgangID to set
   */
  public void setVorgangID(String pvVorgangID) {
      this.ivVorgangID = pvVorgangID;
  }

  /**
   * @return the mandantenID
   */
  public String getMandantenID() {
      return this.ivMandantenID;
  }

  /**
   * @param pvMandantenID the mandantenID to set
   */
  public void setMandantenID(String pvMandantenID) {
      this.ivMandantenID = pvMandantenID;
  }

  /**
   * @return the ereignisID
   */
  public String getEreignisID() {
      return this.ivEreignisID; 
  }

  /**
   * @param pvEreignisID the ereignisID to set
   */
  public void setEreignisID(String pvEreignisID) {
      this.ivEreignisID = pvEreignisID; 
  }

  /**
   * @return the aufgabenVektorID
   */
  public String getAufgabenVektorID() {
      return this.ivAufgabenVektorID;
  }

  /**
   * @param pvAufgabenVektorID the aufgabenVektorID to set
   */
  public void setAufgabenVektorID(String pvAufgabenVektorID) {
      this.ivAufgabenVektorID = pvAufgabenVektorID;
  }

  /**
   * @return the mandant
   */
  public Mandant getMandant() {
      return this.ivMandant;
  }

  /**
   * @param pvMandant the mandant to set
   */
  public void setInstitut(Mandant pvMandant) {
      this.ivMandant = pvMandant;
  }

  /**
   * @return the logging
   */
  //public Logging getLogging() {
  //    return this.ivLogging;
  //}   

  /**
   * @param pvLogging the logging to set
   */
  //public void setLogging(Logging pvLogging) {
  //    this.ivLogging = pvLogging;
  //}

  /**
   * @return the fileSize   
   */
  public long getFileSize() {
    return this.ivFileSize;
  }

  /**
   * @param pvFileSize the fileSize to set
   */
  public void setFileSize(long pvFileSize) {
    this.ivFileSize = pvFileSize;
  }

}
