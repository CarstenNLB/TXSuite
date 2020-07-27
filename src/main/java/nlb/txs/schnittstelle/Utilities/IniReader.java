/*******************************************************************************
 * Copyright (c) 2011 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tepperc
 * Diese Klasse liest Properties-Files im Microsoft-Format.
 * Folgende Struktur:
 * <pre>; a comment
 * [section]
 * key=value</pre>
 * 
 */
public class IniReader 
{
	  /**
	   * 
	   */
      private Map<String, Map<String,String>> ivSections = new HashMap<String, Map<String, String>>();
    
      /**
       * Konstruktor
       * @param pvPathname
       * @throws FileNotFoundException
       * @throws IOException
       */
      public IniReader(String pvPathname) throws FileNotFoundException, IOException 
      {
          this(new FileReader(pvPathname));
      }
      
      /**
       * Konstruktor
       * @param pvInput
       * @throws FileNotFoundException
       * @throws IOException
       */
      public IniReader(InputStream pvInput) throws FileNotFoundException, IOException 
      {
          this(new InputStreamReader(pvInput));
      }
    
      /**
       * Konstruktor
       * @param pvInput
       * @throws FileNotFoundException
       * @throws IOException
       */
      public IniReader(Reader pvInput) throws FileNotFoundException, IOException 
      {
          initialize(new BufferedReader(pvInput));
      }
    
      /**
       * Initialisierung
       * @param pvReader
       * @throws IOException
       */
      private void initialize(BufferedReader pvReader) throws IOException 
      {
          String lvSection = null;
          String lvLine;
          
          while ((lvLine = pvReader.readLine()) != null) 
          {
              lvLine = lvLine.trim();
              if (lvLine.equals("") || lvLine.startsWith(";")) 
              {
                  continue;
              }
              if (lvLine.startsWith("[")) 
              {
                  if (!lvLine.endsWith("]")) {
                      throw new IOException("']' erwartet...");
                  }
                  lvSection = lvLine.substring(1, lvLine.length() - 1).toLowerCase();
              } 
              else if (lvSection == null) 
              {
                  throw new IOException("'[section]' erwartet...");
              } 
              else 
              {
                  int lvIndex = lvLine.indexOf('=');
                  if (lvIndex < 0) {
                      throw new IOException("Schluessel/Wert ohne '='");
                  }
                  String lvKey = lvLine.substring(0, lvIndex).trim().toLowerCase();
                  String lvValue = lvLine.substring(lvIndex + 1).trim();
                  Map<String, String> lvMap = ivSections.get(lvSection);
                  if (lvMap == null) 
                  {
                      ivSections.put(lvSection, (lvMap = new HashMap<String, String>()));
                  }
                  lvMap.put(lvKey, lvValue);
              }
          }
      }
    
      /**
       * Liefert ein Property als String
       * @param pvSection
       * @param pvKey
       * @param pvDefaultValue
       * @return
       */
      public String getPropertyString(String pvSection, String pvKey, String pvDefaultValue) 
      {
          Map<String, String> lvMap = ivSections.get(pvSection.toLowerCase());
          if (lvMap != null) {
              String lvValue = (String) lvMap.get(pvKey.toLowerCase());
              if (lvValue != null) {
                  return lvValue;
              }
          }
          return pvDefaultValue;
      }
    
      /**
       * Liefert ein Property als 'int'
       * @param pvSection
       * @param pvKey
       * @param pvDefaultValue
       * @return
       */
      public int getPropertyInt(String pvSection, String pvKey, int pvDefaultValue) 
      {
          String lvProperty = getPropertyString(pvSection, pvKey, null);
          if (lvProperty != null) 
          {
              return Integer.parseInt(lvProperty);
          }
          return pvDefaultValue;
      }
    
      /**
       * Liefert ein Property als 'boolean'
       * @param pvSection
       * @param pvKey
       * @param pvDefaultValue
       * @return
       */
      public boolean getPropertyBool(String pvSection, String pvKey, boolean pvDefaultValue) 
      {
          String lvProperty = getPropertyString(pvSection, pvKey, null);
          if (lvProperty != null) {
              return lvProperty.equalsIgnoreCase("true");
          }
          return pvDefaultValue;
      }    
}
