package nlb.txs.schnittstelle.Utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class ChangeBuchungsdatum 
{
	
    /**
     * Eingelesenes XML-Dokument
     */
    private Document ivDocument;

    /**
     * Konstruktor
     */
    public ChangeBuchungsdatum(String pvFilename, String pvDatum)
    {
    	// Einlesen der XML-Datei
    	leseXMLDatei(pvFilename);
    	
        // Zur Ausgabe
        Element lvMyrootelement = new Element("TXS");
        Document lvMydocument = new Document(lvMyrootelement);
        
        // Header anhaengen
        Element lvMyElementHeader = new Element("Header");
        // Bestandsdatum
        Element lvMyElementBestandsdatum = new Element("Bestandsdatum");
    	// Buchungsdatum auf 'pvDatum' setzen
        lvMyElementBestandsdatum.setText(pvDatum.replace("-", ""));
        lvMyElementHeader.addContent(lvMyElementBestandsdatum);
        
        // CorrelationID
        Element lvMyElementCorrelationID = new Element("CorrelationID");
        lvMyElementCorrelationID.setText("4710");
        lvMyElementHeader.addContent(lvMyElementCorrelationID);
        
        // SystemID
        Element lvMyElementSystemID = new Element("SystemID");
        lvMyElementSystemID.setText("TXS_P");
        lvMyElementHeader.addContent(lvMyElementSystemID);
        
        // CreationTimestamp
        Element lvMyElementCreationTimestamp = new Element("CreationTimestamp");
        // CreationTimestamp auf 'pvDatum' setzen
        lvMyElementCreationTimestamp.setText(pvDatum + "T00:00:00Z");
        lvMyElementHeader.addContent(lvMyElementCreationTimestamp);
        
        lvMyrootelement.addContent(lvMyElementHeader);
        
        // Request anhaengen
        Element lvMyElementRequest = new Element("Request");
        
        Element lvRootNode = ivDocument.getRootElement();
        Element lvNodeRequest = lvRootNode.getChildren("Request").get(0);
        List<Element> lvListRequest = lvNodeRequest.getChildren();
        
        Element lvHelpElement;
        for (int i = 0; i < lvListRequest.size(); i++)
        {
        	if (i == 0)
        	{
        		lvHelpElement = new Element("Institut");
        	}
        	else
        	{
        		lvHelpElement = new Element("ISIN");
        	}
        	lvNodeRequest = lvListRequest.get(i);
        	lvHelpElement.setAttribute("nr", lvNodeRequest.getAttributeValue("nr"));
        	lvMyElementRequest.addContent(lvHelpElement);
        }	
        lvMyrootelement.addContent(lvMyElementRequest);
        
        // Ausgabe
        FileOutputStream lvGDOS = null;
        File lvFileGD = new File(pvFilename.replace(".xml",  "") + pvDatum.replace("-", "") + ".xml");
        try
        {
          lvGDOS = new FileOutputStream(lvFileGD);
        }
        catch (Exception e)
        {
          System.out.println("Konnte in GattungsdatenRequest-Datei nicht schreiben!");
        }    

        XMLOutputter lvOutputter = new XMLOutputter(Format.getPrettyFormat());

        try
        {
          Format lvFormat = lvOutputter.getFormat();
          lvFormat.setEncoding("ISO-8859-1");
          lvOutputter.output(lvMydocument, lvGDOS);
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
    
    /**
     * Liest die XML-Datei ein
     * @param pvFilename Dateiname der XML-Datei
     */
    private void leseXMLDatei(String pvFilename)
    {
        // Zur Eingabe
        SAXBuilder lvBuilder = null;
        lvBuilder = new SAXBuilder();
        lvBuilder.setExpandEntities(true);
          
        try
        {
            ivDocument = (Document)lvBuilder.build(pvFilename);
        }
        catch(IOException io)
        {
             System.out.println(io.getMessage());
        }
        catch(JDOMException jdomex)
        {
             System.out.println(jdomex.getMessage());
        }
    }
    
    /**
     * Startroutine
     * @param argv 
     */
    public static void main(String[] argv) 
    {
        if (argv.length != 2)
        {
            System.out.println("Starten:");
            System.out.println("ChangeBuchungsdatum <XML-Datei> <Datum (JJJJ-MM-TT)>");
            System.exit(1);
        }
        else
        {                                  
            new ChangeBuchungsdatum(argv[argv.length - 2], argv[argv.length - 1]);              
        }
        System.exit(0);
    }
}
