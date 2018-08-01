package nlb.txs.schnittstelle.Utilities;

public abstract class String2XML 
{
	/**
	 * Konvertiert die Sonderzeichen in das XML-Format dieser.
	 * @param pvText urspruengliche Text/Zeile
	 * @return konvertierter Text/Zeile mit Sonderzeichen im XML-Format
	 */
	public static String change2XML(String pvText)
	{	 	   
		// Ampersand
		pvText = pvText.replace("&", "&amp;");
		
		// Quote
		pvText = pvText.replace("\"", "&quot;");
		
		// Slash am Ende entfernen
		//if (text.endsWith("/"))
		//  text = text.substring(0, text.length() - 1);
		
		// Akzent Akut
		pvText = pvText.replace("�", "&#180;");
		
		// Slash
		pvText = pvText.replace("/", "&#47;");
		
		pvText = pvText.replace("'", "&apos;");
		pvText = pvText.replace("<", "&lt;");
		pvText = pvText.replace(">", "&gt;");
	
		// Umlaute
		pvText = pvText.replace("�", "&#196;");
		pvText = pvText.replace("�", "&#214;");
		pvText = pvText.replace("�", "&#220;");
		pvText = pvText.replace("�", "&#228;");
		pvText = pvText.replace("�", "&#246;");
		pvText = pvText.replace("�", "&#252;");
		pvText = pvText.replace("�", "&#223;");
		
		// Franzoesische Umlaute
		pvText = pvText.replace("�", "&#226;");
		pvText = pvText.replace("�", "&#225;");
		pvText = pvText.replace("�", "&#224;");
		
		pvText = pvText.replace("�", "&#234;");
		pvText = pvText.replace("�", "&#233;");
		pvText = pvText.replace("�", "&#232;");
		
		pvText = pvText.replace("�", "&#238;");
		pvText = pvText.replace("�", "&#237;");
		pvText = pvText.replace("�", "&#236;");
		
		pvText = pvText.replace("�", "&#251;");
		pvText = pvText.replace("�", "&#250;");
		pvText = pvText.replace("�", "&#249;");
		
		return pvText;
	}
	
	/**
	 * Entfernt alle Leerzeichen am Ende des Textes
	 * @param pvText An diesem Text sollen die Leerzeichen am Ende entfernt werden.
	 * @return Um die Leerzeichen bereinigter Text
	 */
	public static String rtrim(String pvText)
	{
	   int lvIndex = pvText.length();
	   while (lvIndex > 0 && Character.isWhitespace(pvText.charAt(lvIndex - 1)))
	   { 
	       lvIndex--;
	   }
	   pvText = pvText.substring(0, lvIndex);
	   
	   return pvText;
    }

}
