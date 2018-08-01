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
		pvText = pvText.replace("´", "&#180;");
		
		// Slash
		pvText = pvText.replace("/", "&#47;");
		
		pvText = pvText.replace("'", "&apos;");
		pvText = pvText.replace("<", "&lt;");
		pvText = pvText.replace(">", "&gt;");
	
		// Umlaute
		pvText = pvText.replace("Ä", "&#196;");
		pvText = pvText.replace("Ö", "&#214;");
		pvText = pvText.replace("Ü", "&#220;");
		pvText = pvText.replace("ä", "&#228;");
		pvText = pvText.replace("ö", "&#246;");
		pvText = pvText.replace("ü", "&#252;");
		pvText = pvText.replace("ß", "&#223;");
		
		// Franzoesische Umlaute
		pvText = pvText.replace("â", "&#226;");
		pvText = pvText.replace("á", "&#225;");
		pvText = pvText.replace("à", "&#224;");
		
		pvText = pvText.replace("ê", "&#234;");
		pvText = pvText.replace("é", "&#233;");
		pvText = pvText.replace("è", "&#232;");
		
		pvText = pvText.replace("î", "&#238;");
		pvText = pvText.replace("í", "&#237;");
		pvText = pvText.replace("ì", "&#236;");
		
		pvText = pvText.replace("û", "&#251;");
		pvText = pvText.replace("ú", "&#250;");
		pvText = pvText.replace("ù", "&#249;");
		
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
