package nlb.txs.schnittstelle.Darlehen2Excel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import nlb.txs.schnittstelle.Darlehen.LeseVorlaufsatz;
import nlb.txs.schnittstelle.Darlehen.Daten.Original.DWHVOR;
import nlb.txs.schnittstelle.Utilities.StringKonverter;

public class Darlehen2Excel extends JDialog implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Status BAUFI
    private static final int BAUFI = 1;
    
    // Status INF
    private static final int INF = 2;
    
    // Status KON
    private static final int KON = 3;
    
    // Status KONTS
    private static final int KONTS = 4;

    // Status KTOZB
    private static final int KTOZB = 5;

    // Status KTR
    private static final int KTR = 6;

    // Status KTS
    private static final int KTS = 7;

    // Status OBJ
    private static final int OBJ = 8;

    // Status REC
    private static final int REC = 9;

    // Status UMS
    private static final int UMS = 10;
    
    // Status undefiniert
    private static final int UNDEFINIERT = 11;

    // Status
    private int ivStatus;
    
    /**
     * Kundennummer
     */
    private String ivKundennummer;     
    
    /**
     * Objektnummer 
     */
    private String ivObjektnummer;
    
    /**
     * Kontonummer
     */                             
    private String ivKontonummer;   
    private String ivAlteKontonummer;

    private File ivFileDarlehen;    
    
    private DWHVOR ivVorlaufsatz;
    
    private int ivZaehlerVorlaufsatz = 0;
    private int ivZaehlerBAUFI = 0;
    private int ivZaehlerINF = 0;
    private int ivZaehlerKON = 0;
    private int ivZaehlerKONTS = 0;
    private int ivZaehlerKTOZB = 0;
    private int ivZaehlerKTR = 0;
    private int ivZaehlerKTS = 0;
    private int ivZaehlerOBJ = 0;
    private int ivZaehlerREC = 0;
    private int ivZaehlerUMS = 0;
    
    private WritableWorkbook ivWorkbook;

    private WritableSheet ivSheetVorlaufsatz;
    private WritableSheet ivSheetBAUFI;
    private WritableSheet ivSheetOBJ;
    private WritableSheet ivSheetINF;
    private WritableSheet ivSheetKTS;
    private WritableSheet ivSheetKTR;
    private WritableSheet ivSheetUMS;
    private WritableSheet ivSheetKONTS;
    private WritableSheet ivSheetKTOZB;
    private WritableSheet ivSheetKON;
    private WritableSheet ivSheetREC;
 
    private JButton ivButtonExit;
    private JButton ivButtonStart;
    private JButton ivButtonSource;
    private JTextField ivFieldSource;
    private JButton ivButtonTarget;
    private JTextField ivFieldTarget;

    private JTextArea ivArea;

    /**
     * @param argv 
     */
    public static void main(String argv[])
    {
      new Darlehen2Excel();
      //Darlehen2Excel txx2xls = new Darlehen2Excel();
      //System.exit(0);
    }

    /**
     * 
     */
    public Darlehen2Excel()
    {
      initDialog();
    }

    /**
     * 
     */
    private void convertDarlehen2Excel()
    {
      try
      {
        ivWorkbook = Workbook.createWorkbook(new File(ivFieldTarget.getText()));
      }
      catch (Exception e)
      {
        JOptionPane.showMessageDialog((JDialog)this, "Konnte Excel-Datei nicht erzeugen!", "Fehler",
                    JOptionPane.OK_OPTION);
        return;
      }

      ivSheetVorlaufsatz = createSheetVorlaufsatz();
      ivSheetOBJ = createSheetOBJ();
      ivSheetBAUFI = createSheetBAUFI();
      ivSheetKTS = createSheetKTS();
      ivSheetKTR = createSheetKTR();
      ivSheetKON = createSheetKON();
      ivSheetKTOZB = createSheetKTOZB();
      ivSheetKONTS = createSheetKONTS();
      ivSheetINF = createSheetINF();
      ivSheetREC = createSheetREC();
      ivSheetUMS = createSheetUMS();

      readDarlehen(ivFieldSource.getText());
      
      // All sheets and cells added. Now write out the workbook
      try
      {
        ivWorkbook.write();
        ivWorkbook.close();
      }
      catch (Exception e)
      {
      }
      
      ivArea.append(printStatistik());
    }

  /**
   * 
   */
  private void initDialog()
  {
      this.setTitle("Darlehen2Excel");
      this.setSize(300,300);
      this.getContentPane().setLayout(new BorderLayout());

        JPanel lvPanel = new JPanel();
        lvPanel.setLayout(new GridLayout(2, 3));
        JLabel lvLabelSource = new JLabel("Darlehen-Datei");
      ivFieldSource = new JTextField("Darlehen.txt");
      ivButtonSource = new JButton("Suchen");
      ivButtonSource.addActionListener(this);
        lvPanel.add(lvLabelSource);
        lvPanel.add(ivFieldSource);
        lvPanel.add(ivButtonSource);

      JLabel labelTarget = new JLabel("Zieldatei");
      ivFieldTarget = new JTextField("output.xls");
      ivButtonTarget = new JButton("Suchen");
      ivButtonTarget.addActionListener(this);
        lvPanel.add(labelTarget);
        lvPanel.add(ivFieldTarget);
        lvPanel.add(ivButtonTarget);
 
      this.getContentPane().add(lvPanel, "North");

      ivArea = new JTextArea();
      this.getContentPane().add(ivArea, "Center");

        JPanel lvButtonsPanel = new JPanel();
	
      ivButtonStart = new JButton("Start");
      ivButtonStart.addActionListener(this);	

      ivButtonExit = new JButton("Exit");
      ivButtonExit.addActionListener(this);

        lvButtonsPanel.add(ivButtonStart);
        lvButtonsPanel.add(ivButtonExit);

      this.getContentPane().add(lvButtonsPanel, "South");

      this.setVisible(true);  
  }  

  /**
   * 
   */
  private WritableSheet createSheetVorlaufsatz()
  {
   WritableSheet lvSheet = ivWorkbook.createSheet("DWHVOR", 0);

    Label lvLabel = null;
    
    for (int x = 0; x < 9; x++)
    {
      switch (x)
      {
        case 0:
            lvLabel = new Label(x, 0, "Institutsnr.");
            break;
        case 1:
            lvLabel = new Label(x, 0, "Anwendung");
            break;
        case 2:
            lvLabel = new Label(x, 0, "Satztyp");
            break;
        case 3:
            lvLabel = new Label(x, 0, "Buchungsdatum JJJJMMTT");
            break;
        case 4:
            lvLabel = new Label(x, 0, "Datum des Berichtsmonats JJJJMM");
            break;
        case 5:
            lvLabel = new Label(x, 0, "Datum der Bestandserstellung (Maschinendatum)");
            break;
        case 6:
            lvLabel = new Label(x, 0, "Uhrzeit der Bestandserstellung (HHMMSSTH)");
            break;
        case 7:
            lvLabel = new Label(x, 0, "Versionsnr. z.B. 01.00");
            break;
        case 8:
            lvLabel = new Label(x, 0, "Typ = D500808 ");
            break;
      }
      try
      {
          lvSheet.addCell(lvLabel);
      }
      catch (Exception e)
      {
        JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes DWHVOR!", "Fehler",
                    JOptionPane.OK_OPTION);   
      }
    }

    return lvSheet;
  }
  
    /**
     * 
     */
    private WritableSheet createSheetBAUFI()
    {
      WritableSheet lvSheet = ivWorkbook.createSheet("BAUFI", 2);

      Label lvLabel = null;

      for (int x = 0; x < 7; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "vierstelliges Baujahr");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Beleihungswert");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Sachwert");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Ertragswert");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Verwendungszweck");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Zusatzsicherheiten");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Grundschuld, Grundpfandrecht");
            break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes BAUFI!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }
    
    /**
     * 
     */
    private WritableSheet createSheetOBJ()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("OBJ", 1);

      Label lvLabel = null;

      for (int x = 0; x < 19; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Kundennummer");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Kusy achtstellig");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Datum achtstellig OB-Einrichtungsdatum");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Datum achtstellig OB-Änderungsdatum");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Datum achtstellig Objekt-Eröffnung ");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Schlüssel Objektzusatzangaben");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Objektzusatzangaben");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Art des Objektes");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Schlüssel Mahnung");
            break;
          case 9:
              lvLabel = new Label(x, 0, "Datum achtstellig Mahnsperre/Stundung");
              break;
          case 10:
              lvLabel = new Label(x, 0, "Kundenbetreuende Stelle");
              break;
          case 11:
              lvLabel = new Label(x, 0, "Anzahl Wohnungseinheiten");
              break;
          case 12:
              lvLabel = new Label(x, 0, "PLZ Objekt");
              break;
          case 13:
              lvLabel = new Label(x, 0, "Ort Objekt");
              break;
          case 14:
              lvLabel = new Label(x, 0, "Straße Objekt");
              break;
          case 15:
              lvLabel = new Label(x, 0, "Grundbuchnummer");
              break;
          case 16:
              lvLabel = new Label(x, 0, "Grundbuchband");
              break;
          case 17:
              lvLabel = new Label(x, 0, "Grundbuchblatt");
              break;
          case 18:
              lvLabel = new Label(x, 0, "Beleihungsgebiet");
              break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes OBJ!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }
    
    /**
     * 
     */
    private WritableSheet createSheetINF()
    {
      WritableSheet lvSheet = ivWorkbook.createSheet("INF", 8);

      Label lvLabel = null;

      for (int x = 0; x < 9; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Informationsart/-typ für EDR");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Wertstellung");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Kontonummer");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Buchungsschlüssel");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Buchungstag");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Grundbuchnummer");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Betrag (Währung sh. BWISO)");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Schlüssel Rücklastschrift");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Mahnfriststellungsdatum");
            break;
         }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes INF!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }

    /**
     * 
     */
    private WritableSheet createSheetKTS()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("KTS", 3);

      Label lvLabel = null;

      for (int x = 0; x < 33; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Konto-Einrichtung");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Konto-Änderung");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Datum der Konto-Eröffnung");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Datum der Vollauszahlung");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Datum der Schließung");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Nächste Zinsfälligkeit");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Nächste Tilgungsfälligkeit");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Tilgungsbeginn");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Datum der Annahme");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "Datum der Bewilligung");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Datum Zinsanpassung");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "Datum letzte Zinsanpassung");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "Datum letzte Statistikmeldung");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "Ursprungskapital");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "Auszahlungsverpflichtung");
            break;
          case 15:
              lvLabel = new Label(x, 0, "Restkapital (Saldo)");
              break;
          case 16:
              lvLabel = new Label(x, 0, "Saldo Stundungsdarlehen");
              break;
          case 17:
              lvLabel = new Label(x, 0, "Passivkontonr. / Einheitsdarlehn");
              break;
          case 18:
              lvLabel = new Label(x, 0, "Finanzkontor. (Bestandkto.)");
              break;
          case 19:
              lvLabel = new Label(x, 0, "Bewilligende Organisationseinh.");
              break;
          case 20:
              lvLabel = new Label(x, 0, "Verwaltende Organisationseinheit");
              break;
          case 21:
              lvLabel = new Label(x, 0, "Produktverantwortliche OE (PAS)");
              break;
          case 22:
              lvLabel = new Label(x, 0, "Produktschlüssel");
              break;
          case 23:
              lvLabel = new Label(x, 0, "Schlüssel Darlehensbesonderh.");
              break;
          case 24:
              lvLabel = new Label(x, 0, "Schlüssel Refinanzierung");
              break;
          case 25:
              lvLabel = new Label(x, 0, "Schlüssel Kompensation/Saldier.");
              break;
          case 26:
              lvLabel = new Label(x, 0, "Kontozustand");
              break;
          case 27:
              lvLabel = new Label(x, 0, "Schlüssel Sollstellung");
              break;
          case 28:
              lvLabel = new Label(x, 0, "Schlüssel Zinsrechnung");
              break;
          case 29:
              lvLabel = new Label(x, 0, "Schlüssel Fristigkeit");
              break;
          case 30:
              lvLabel = new Label(x, 0, "Schlüssel Sicherheiten");
              break;
          case 31:
              lvLabel = new Label(x, 0, "Schlüssel Bürgschaft");
              break;
          case 32:
              lvLabel = new Label(x, 0, "Währungsschlüssel Darlehnskonto");
              break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes KTS!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }    
    
    /**
     * 
     */
    private WritableSheet createSheetKTR()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("KTR", 4);

      Label lvLabel = null;

      for (int x = 0; x < 55; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Schl. Manuelle Leistungsrechnung");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Zusatzangaben, Zeile 1");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Zusatzangaben, Zeile 2");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Schlüssel Verwendungszweck");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Schl. Wohnungs-/Neubau-Finanz.");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "%-Satz Auszahlungskurs");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Nächstes Zinsperiodenende");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Nächstes Tilgungsperiodenende");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Nächster Tilgungsabschr.Termin");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "Anzahl Zinsperioden");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Anzahl Tilgungsperioden");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "Anzahl Tilgungsabschreibungstermine");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "Betreuer");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "Bewilligungsbescheid-Nr.");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "Ordnungsbegriff Großkunde");
            break;
          case 15:
                    lvLabel = new Label(x, 0, "Schlüssel Deckung");
            break;
          case 16:
                    lvLabel = new Label(x, 0, "Prozentsatz Deckung");
            break;
          case 17:
                    lvLabel = new Label(x, 0, "Kundennr. des Bürgen");
            break;
          case 18:
                    lvLabel = new Label(x, 0, "%-Satz Bürgschaft");
            break;
          case 19:
                  lvLabel = new Label(x, 0, "Schlüssel Wertberichtigung");
                  break;
          case 20:
              lvLabel = new Label(x, 0, "Laufzeit der Zinsanpassung");
              break;
          case 21:
              lvLabel = new Label(x, 0, "%-Satz Effektivzins");
              break;
          case 22:
              lvLabel = new Label(x, 0, "Kontonummer Konsortialhauptkonto");
              break;
          case 23:
              lvLabel = new Label(x, 0, "Gesamtbetrag Konsortialkredit");
              break;
          case 24:
              lvLabel = new Label(x, 0, "Kunden-Nummer des Konsortialführers/Pege");
              break;
          case 25:
              lvLabel = new Label(x, 0, "Berichtigungsposten Zins.Stundungsdarl.");
              break;
          case 26:
              lvLabel = new Label(x, 0, "Korrespondenznummer");
              break;
          case 27:
              lvLabel = new Label(x, 0, "Datum der Konditionierung");
              break;
          case 28:
              lvLabel = new Label(x, 0, "Schlüssel Tilgung");
              break;
          case 29:
              lvLabel = new Label(x, 0, "Letztes Zinsperiodenende");
              break;
          case 30:
              lvLabel = new Label(x, 0, "letztes Tilgungsperiodenende");
              break;
          case 31:
              lvLabel = new Label(x, 0, "Schlüssel Ultimo Zinsen");
              break;
          case 32:
              lvLabel = new Label(x, 0, "Datum Kontoschließung /  DKZ=8");
              break;
          case 33:
              lvLabel = new Label(x, 0, "Datum d. unwiderruflichen Zusage");
              break;
          case 34:
              lvLabel = new Label(x, 0, "Kennzeichen Rahmenkredit");
              break;
          case 35:
              lvLabel = new Label(x, 0, "Auszahlungsdatum");
              break;
          case 36:
              lvLabel = new Label(x, 0, "Datum letzte Rate");
              break;
          case 37:
              lvLabel = new Label(x, 0, "Schlüssel Tilgungsbesonderheit");
              break;
          case 38:
              lvLabel = new Label(x, 0, "Personalnummer für Angestellte");
              break;
          case 39:
              lvLabel = new Label(x, 0, "Datum Vertrag bis");
              break;
          case 40:
              lvLabel = new Label(x, 0, "DummyFeld als Füller 22.03.2005");
              break;
          case 41:
              lvLabel = new Label(x, 0, "Niedrigzinssatz");
              break;
          case 42:
              lvLabel = new Label(x, 0, "Höchstzinssatz");
              break;
          case 43:
              lvLabel = new Label(x, 0, "ZAS-Ausschlusskennzeichen");
              break;
          case 44:
              lvLabel = new Label(x, 0, "Datum Zinsbeginn");
              break;
          case 45:
              lvLabel = new Label(x, 0, "Beleihungswert");
              break;
          case 46:
              lvLabel = new Label(x, 0, "Zinsaufschlag/-abschlag");
              break;
          case 47:
              lvLabel = new Label(x, 0, "Größe der Zinsperiode");
              break;
          case 48:
              lvLabel = new Label(x, 0, "Schlüssel revolvierender Kreditrahmen");
              break;
          case 49:
              lvLabel = new Label(x, 0, "Ursprungslaufzeit");
              break;
          case 50:
              lvLabel = new Label(x, 0, "Wertpapierkennummer");
              break;
          case 51:
              lvLabel = new Label(x, 0, "Urkundennummer");
              break;
          case 52:
              lvLabel = new Label(x, 0, "%-Satz ohne Haftung -- neu 28.04.2005");
              break;
          case 53:
              lvLabel = new Label(x, 0, "Kz unvollstaendige Sicherheiten");
              break;
          case 54:
              lvLabel = new Label(x, 0, "Kz unvollstaendige Sicherheiten");
              break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes KTR!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }

    /**
     * 
     */
    private WritableSheet createSheetUMS()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("UMS", 10);

      Label lvLabel = null;

      for (int x = 0; x < 5; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Buchungstag");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Wertstellungsdatum");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Buchungsschlüssel");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Betrag (Währung sh. UWISO)");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Grundbuchnummer");
            break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes UMS!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }
    
    /**
     * 
     */
    private WritableSheet createSheetKONTS()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("KONTS", 7);

      Label lvLabel = null;

      for (int x = 0; x < 16; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "gueltig ab");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Einrichtungsdatum");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Änderungsdatum");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Datum nächste Fälligkeit");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Datum nächstes Periodenende");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Datum nächster Abschreibungstermin");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Anzahl Abschreibungstermine");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Anzahl Perioden");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Periodengröße");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "Schl. Ultimofälligkeit");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Schl. Fälligkeitsbesonderheit");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "Schl. Besonderheit");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "Schl. Sollstellung");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "Schl. Rechnungsmodus");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "%-Niedrigstzinssatz");
            break;
          case 15:
              lvLabel = new Label(x, 0, "%-Höchstzinssatz");
              break;
        }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes KONTS!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }

        return lvSheet;
    }
    
    /**
     * 
     */
    private WritableSheet createSheetKTOZB()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("KTOZB", 6);

      Label lvLabel = null;
     
      for (int x = 0; x < 18; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "gueltig ab");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Einrichtungsdatum");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Änderungsdatum");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Datum Konditionierung vom");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Datum Konditionsannahme");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Datum Zinsanpassung");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Laufzeit Zinsanpassung");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "%-Auszahlungskurs");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Schl. Zinsvereinbarung (Fest/Variabel)");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "%-Satz:Abweichung Berechnungsgrundlage");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Berechnungsgrundlage (z. B. LIBOR)");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "%-Effektivzinssatz ");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "%-Einstandssatz");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "%-Marge");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "%-Barwert");
            break;
         case 15:
                    lvLabel = new Label(x, 0, "Margenbarwert (Betrag)");
            break;
         case 16:
                    lvLabel = new Label(x, 0, "%-Satz Risikoaufschlag");
            break;
         case 17:
                    lvLabel = new Label(x, 0, "%-Satz Bereichsaufschlag");
            break;
         }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes KTOZB!", "Fehler",
                      JOptionPane.OK_OPTION);	
        }
      }
        return lvSheet;
    }

    /**
     * 
     */
    private WritableSheet createSheetKON()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("KON", 5);

      Label lvLabel = null;
     
      for (int x = 0; x < 24; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Gueltigkeitsdatum ab");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Datum Einrichtung Kondition");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "% - Bereitstellungszinsen");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "% - Darlehenszinsen");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "% - Zinsen Stundungsdarlehn");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Schlüssel Tilgung");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "% - Satz Tilgung");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Betrag Tilgungsrate");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Betrag Berechnungsnominale");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "% - Annuitätenzinsen");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Betrag Leistung (Annuität)");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "Schlüssel Nebenleistung 1");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "Art der Nebenleistung 1");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "% - Satz Nebenleistung 1");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "Betrag Nebenleistung 1");
            break;
         case 15:
                    lvLabel = new Label(x, 0, "Schlüssel Nebenleistung 2");
            break;
         case 16:
                    lvLabel = new Label(x, 0, "Art der Nebenleistung 2");
            break;
         case 17:
                    lvLabel = new Label(x, 0, "% - Satz Nebenleistung 2");
            break;
         case 18:
             lvLabel = new Label(x, 0, "Betrag Nebenleistung 2");
             break;
         case 19:
             lvLabel = new Label(x, 0, "Schl. Zuschuss/Zinsverbilligung");
             break;
         case 20:
             lvLabel = new Label(x, 0, "Betrag Zuschuss(Anlaufzuschuss)");
             break;
         case 21:
             lvLabel = new Label(x, 0, "%-Satz Zinsverbilligung");
             break;
         case 22:
             lvLabel = new Label(x, 0, "KD-AEND");
             break;
         case 23:
             lvLabel = new Label(x, 0, "Schlüssel Roll-Over");
             break;
         }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes KON!", "Fehler",
                      JOptionPane.OK_OPTION);   
        }
      }
        return lvSheet;
    }

    /**
     * 
     */
    private WritableSheet createSheetREC()
    {
        WritableSheet lvSheet = ivWorkbook.createSheet("REC", 9);

      Label lvLabel = null;
     
      for (int x = 0; x < 65; x++)
      {
        switch (x)
        {
          case 0:
                    lvLabel = new Label(x, 0, "Kennzeichen Ultimo");
            break;
          case 1:
                    lvLabel = new Label(x, 0, "Verzinsendes Kapital/Tag");
            break;
          case 2:
                    lvLabel = new Label(x, 0, "Durchschnittskapital lfd.Monat");
            break;
          case 3:
                    lvLabel = new Label(x, 0, "Offene Leistung rückständig Tilg.");
            break;
          case 4:
                    lvLabel = new Label(x, 0, "Offene Leistung rückständig Zins");
            break;
          case 5:
                    lvLabel = new Label(x, 0, "Offene Leistung rückständig Gebü.");
            break;
          case 6:
                    lvLabel = new Label(x, 0, "Offene Leist.noch nicht fällig Tilg. ");
            break;
          case 7:
                    lvLabel = new Label(x, 0, "Offene Leist.noch nicht fällig Gebü.");
            break;
          case 8:
                    lvLabel = new Label(x, 0, "Abgrenzung einbehalten Agio");
            break;
          case 9:
                    lvLabel = new Label(x, 0, "Abgrenzung einbehalten Disagio");
            break;
          case 10:
                    lvLabel = new Label(x, 0, "Abgrenzung einbehalten Gebühren");
            break;
          case 11:
                    lvLabel = new Label(x, 0, "noch abzugrenzen Agio");
            break;
          case 12:
                    lvLabel = new Label(x, 0, "noch abzugrenzen Disagio");
            break;
          case 13:
                    lvLabel = new Label(x, 0, "noch abzugrenzen Gebühren");
            break;
          case 14:
                    lvLabel = new Label(x, 0, "noch abzugrenzen Agio per 31.12.");
            break;
         case 15:
                    lvLabel = new Label(x, 0, "noch abzugrenz.Disagio per 31.12.");
            break;
         case 16:
                    lvLabel = new Label(x, 0, "noch abzugrenz.Gebühr.per 31.12.");
            break;
         case 17:
                    lvLabel = new Label(x, 0, "Kennzeichen fest/variabel");
            break;
         case 18:
             lvLabel = new Label(x, 0, "Kennzeichen aktiv/passiv");
             break;
         case 19:
             lvLabel = new Label(x, 0, "Girokontonummer Zinsen");
             break;
         case 20:
             lvLabel = new Label(x, 0, "Girokontonummer Tilgung");
             break;
         case 21:
             lvLabel = new Label(x, 0, "Girokontonummer Gebühren");
             break;
         case 22:
             lvLabel = new Label(x, 0, "Bankleitzahl Zinsen");
             break;
         case 23:
             lvLabel = new Label(x, 0, "Bankleitzahl Tilgung");
             break;
         case 24:
             lvLabel = new Label(x, 0, "Bankleitzahl Gebühren");
             break;
         case 25:
             lvLabel = new Label(x, 0, "Summe rückständige Leistung");
             break;
         case 26:
             lvLabel = new Label(x, 0, "Summe noch nicht fäll. Tilgung");
             break;
         case 27:
             lvLabel = new Label(x, 0, "Saldo Stundungsanteil");
             break;
         case 28:
             lvLabel = new Label(x, 0, "Summe Vorauszahlungen");
             break;
         case 29:
             lvLabel = new Label(x, 0, "Konsortialanteil SURULE");
             break;
         case 30:
             lvLabel = new Label(x, 0, "Konsortialanteil SUNNFT");
             break;
         case 31:
             lvLabel = new Label(x, 0, "Konsortialanteil SALSTD");
             break;
         case 32:
             lvLabel = new Label(x, 0, "Konsortialanteil SUVORZ");
             break;
         case 33:
             lvLabel = new Label(x, 0, "Konsortialanteil Restkapital");
             break;
         case 34:
             lvLabel = new Label(x, 0, "Gesamtforderung (sh.Anm.)");
             break;
         case 35:
             lvLabel = new Label(x, 0, "Konsotialanteil Gesamtforderung");
             break;
         case 36:
             lvLabel = new Label(x, 0, "Anteilige ZINSEN");
             break;
         case 37:
             lvLabel = new Label(x, 0, "Kundengruppe");
             break;
         case 38:
             lvLabel = new Label(x, 0, "Kreditart");
             break;
         case 39:
             lvLabel = new Label(x, 0, "Konsortialanteil Ursprungskapital");
             break;
         case 40:
             lvLabel = new Label(x, 0, "Gebuchte Zinsen");
             break;
         case 41:
             lvLabel = new Label(x, 0, "Gebuchte weitere Erfolge");
             break;
         case 42:
             lvLabel = new Label(x, 0, "Anteilige Zinsen (IFRS)");
             break;
         case 43:
             lvLabel = new Label(x, 0, "Anteilige weitere Erfolge");
             break;
         case 44:
             lvLabel = new Label(x, 0, "Anteilige Zinsen / Vorjahresende");
             break;
         case 45:
             lvLabel = new Label(x, 0, "Anteilige weitere Erfolge /VJ-Ende");
             break;
         case 46:
             lvLabel = new Label(x, 0, "Gebuchte,sofort vereinnahmte Zinserfolge");
             break;
         case 47:
             lvLabel = new Label(x, 0, "Gebuchte,sofort vereinnahmte Nebenleistungen");
             break;
         case 48:
             lvLabel = new Label(x, 0, "Gebuchte,sofort verauslagte Provision");
             break;
         case 49:
             lvLabel = new Label(x, 0, "Disagiodifferenz");
             break;
         case 50:
             lvLabel = new Label(x, 0, "Kapitallaufzeitende");
             break;
         case 51:
             lvLabel = new Label(x, 0, "Gebuchte Abgrenzung Agio");
             break;
         case 52:
             lvLabel = new Label(x, 0, "Gebuchte Abgrenzung Disagio");
             break;
         case 53:
             lvLabel = new Label(x, 0, "Gebuchte Abgrenzung Gebühren");
             break;
         case 54:
             lvLabel = new Label(x, 0, "in NGEBZ enthaltene Bereitstellungzinsen");
             break;
         case 55:
             lvLabel = new Label(x, 0, "in NANTZ enthaltene Bereitstellungzinsen");
             break;
         case 56:
             lvLabel = new Label(x, 0, "in N31VZ enthaltene Bereitstellungzinsen");
             break;
         case 57:
             lvLabel = new Label(x, 0, "gebuchte, nicht zugeordnete Bereitstellgs.zs.");
             break;
         case 58:
             lvLabel = new Label(x, 0, "im lfd Jahr als Geschvorfall ang.BeZs");
             break;
         case 59:
             lvLabel = new Label(x, 0, "im lfd Jahr sofort vereinnahmte BerZs");
             break;
         case 60:
             lvLabel = new Label(x, 0, "Termin nächste Tilgungsleistung");
             break;
         case 61:
             lvLabel = new Label(x, 0, "Betrag nächste Tilgungsleistung");
             break;
         case 62:
             lvLabel = new Label(x, 0, "Termin nächste Zinsleistung");
             break;
         case 63:
             lvLabel = new Label(x, 0, "Betrag nächste Zinsleistung ");
             break;
         case 64:
             lvLabel = new Label(x, 0, "Betrag nächste Nebenleistung");
             break;
         }
        try
        {
                lvSheet.addCell(lvLabel);
        }
        catch (Exception e)
        {
          JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Erzeugen des Blattes REC!", "Fehler",
                      JOptionPane.OK_OPTION);   
        }
      }
        return lvSheet;
    }
    
    /**
     * 
     */
    private void readDarlehen(String pvDateiname)
    {
        String lvZeile = null;
                
        // Oeffnen der Dateien
        FileInputStream fis = null;
        ivFileDarlehen = new File(pvDateiname);
        try
        {
            fis = new FileInputStream(ivFileDarlehen);
        }
        catch (Exception e)
        {
            System.out.println("Konnte Darlehen-Datei nicht oeffnen!");
            return;
        }

        BufferedReader lvInputReader = new BufferedReader(new InputStreamReader(fis));
        boolean start = true;
          
        try
        {
            while ((lvZeile = lvInputReader.readLine()) != null) // Lesen Darlehen-Datei
            {
              if (start)
              {
                  //System.out.println("Zeile: " + s);
                  LeseVorlaufsatz lVor = new LeseVorlaufsatz();
                  ivVorlaufsatz = lVor.parseVorlaufsatz(lvZeile);
                  ivZaehlerVorlaufsatz++;
                  
                  // Vorlaufsatz nach Excel schreiben                        
                  setVorlaufsatz(0, ivVorlaufsatz.getsDwvinst());
                  setVorlaufsatz(1, ivVorlaufsatz.getsDwvanw());
                  setVorlaufsatz(2, ivVorlaufsatz.getsDwvstyp());
                  setVorlaufsatz(3, ivVorlaufsatz.getsDwvbdat());
                  setVorlaufsatz(4, ivVorlaufsatz.getsDwvbmon());
                  setVorlaufsatz(5, ivVorlaufsatz.getsDwvdat());
                  setVorlaufsatz(6, ivVorlaufsatz.getsDwvuhr());
                  setVorlaufsatz(7, ivVorlaufsatz.getsDwvver());
                  setVorlaufsatz(8, ivVorlaufsatz.getsDwvtyp());
                   
                  start = false;
              }
              else
              {
                    if (!parseDarlehen(lvZeile)) // Parsen der Felder
                  {
                      System.out.println("Datenfehler!!!\n");
                  }
                  //System.out.println(s);
                  if (!ivKontonummer.equals("0000000000"))
                  {
                      //einfuegenSegment();
                      ivAlteKontonummer = ivKontonummer;
                  }
              }
            }
          }
          catch (Exception e)
          {
            System.out.println("Fehler beim Verarbeiten einer Zeile!");
            e.printStackTrace();
          }

          // Das letzte Darlehen muss noch verarbeitet werden
          //if (ktr != null)
          //{
            //System.out.println("Deckungskennzeichen: " + ktr.getsDd());

            // Pruefe, ob das Datum im KON, KONTS und KTOZB korrekt ist
            //originalDarlehen.pruefeDatum();
            
            //if (!originalDarlehen.existsDummySegment() && 
            //    originalDarlehen.existsPflichtsegmente() &&   
            //    (ktr.getsDd().equals("F") || ktr.getsDd().equals("D") || ktr.getsDd().equals("5") || ktr.getsDd().equals("6")))
            //{
                //System.out.println(originalDarlehen.printStatistik());
                // CT - Für Debugzwecke
                //if (ktoListe.contains(ktr.getKopf().getsDwhknr()))
                //{
            //      verarbeiteDarlehen();
                //}
            //}
          //}
          
          try
          {
            lvInputReader.close();
          }
          catch (Exception e)
          {
            System.out.println("Konnte Darlehen-Datei nicht schliessen!");
          }
             
          //if (ivLogging)
          //{
          //    ivLog.write(printStatistik());
          //}
        }

      /**
       * Gibt die Statistikdaten als String aus
       * @return 
       */
      public String printStatistik()
      {
        StringBuffer lvOut = new StringBuffer();
        lvOut.append(ivVorlaufsatz.toString());
        
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerVorlaufsatz + " Vorlaufsatz gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerBAUFI + " BAUFI-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerINF + " INF-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKON + " KON-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKONTS + " KONTS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTOZB + " KTOZB-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTR + " KTR-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerKTS + " KTS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerOBJ + " OBJ-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerREC + " REC-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        lvOut.append(ivZaehlerUMS + " UMS-Segmente gelesen...");
        lvOut.append(StringKonverter.lineSeparator);
        
        return lvOut.toString();
      }

      /**
       * @param pvZeile 
       * @return 
       *       
       */
     private boolean parseDarlehen(String pvZeile)
     {    
        //String shID = null;
        //System.out.print("Zeile: " + zeile);
         
        String lvTemp = new String(); // arbeitsbereich/zwischenspeicher feld
        int    lvLfd=0;                // lfd feldnr, pruefsumme je satzart
        int    lvZzStr=0;              // pointer für satzbereich
        
        ivStatus = UNDEFINIERT;
        
        // steuerung/iteration eingabesatz
        for (lvZzStr=0; lvZzStr < pvZeile.length(); lvZzStr++)
        {

          // wenn semikolon erkannt
          if (pvZeile.charAt(lvZzStr) == ';')
          {
              if (lvLfd < 4)
              {
                  if (lvLfd == 0)
                  {
                      ivKundennummer = lvTemp;
                  }
                  if (lvLfd == 1)
                  {
                      ivObjektnummer = lvTemp;
                  }
                  if (lvLfd == 2)
                  {
                      ivKontonummer = lvTemp;
                  }
                  
                  if (lvLfd == 3)
                  {
                      if (ivAlteKontonummer != null)
                      {
                        if (!ivKontonummer.equals(ivAlteKontonummer))
                        {
                          if (!ivKontonummer.equals("0000000000"))
                          {
                            //System.out.println("Kontonummerwechsel: von " + alteKontonummer + " nach " + kontonummer);
                            //if (ktr != null)
                            //{
                              //System.out.println("Deckungskennzeichen: " + ktr.getsDd());
                              
                              // Pruefe, ob das Datum in den KON, KONTS und KTOZB korrekt ist
                              //originalDarlehen.pruefeDatum();
                              
                              //if (!originalDarlehen.existsDummySegment() &&
                              //    originalDarlehen.existsPflichtsegmente() &&    
                              //   (ktr.getsDd().equals("F") || ktr.getsDd().equals("D") || ktr.getsDd().equals("5") || ktr.getsDd().equals("6")))
                              //{
                                  //System.out.println(darlehen.printStatistik());
                                  // CT - für Debugzwecke
                                  //if (ktoListe.contains(ktr.getKopf().getsDwhknr()))
                                  //{
                              //      verarbeiteDarlehen();
                                  //}
                              //}
                            //}
                            //originalDarlehen.clearLists();
                            //ivInf = null;
                            //ivKon = null;
                            //ivKonts = null;
                            //ivKtozb = null;
                            //ivKtr = null;
                            //ivKts = null;
                            //ivRec = null;
                            //ivUms = null;
                          }
                        }
                      }
                    //stemp = stemp.trim();
                    erzeugeSegment(lvTemp.trim());
                  }
              }
              else
              {
                setzeWert(ivStatus, lvLfd, lvTemp); 
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
      
      // Letzten Wert der Zeile/Segment auch noch setzen
      if (!lvTemp.isEmpty())
      {
          setzeWert(ivStatus, lvLfd, lvTemp);
      }
      
      // Daten ins Excel-Dokument schreiben
      writeSegment2Excel(ivStatus);
      
      return true; 
     }

     /**
      * 
      */
     private void writeSegment2Excel(int pvStatus)
     {
         if (pvStatus == KTR)
         {
             
         }
     }
     
     
    /**
      * Erzeugen eines Segments
      * @param pvTtemp
      */
     private void erzeugeSegment(String pvTtemp)
     {
         if (pvTtemp.equals("BAUFI"))
         {
             ivStatus = BAUFI;
             //ivBaufi = new BAUFI();
             //ivBaufi.getKopf().setsDwhkdnr(ivKundennummer);
             //ivBaufi.getKopf().setsDwhonr(ivObjektnummer);
             //ivBaufi.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerBAUFI++;
         }
         if (pvTtemp.equals("INF"))
         {
             ivStatus = INF;
             //ivInf = new INF();
             //ivInf.getKopf().setsDwhkdnr(ivKundennummer);
             //ivInf.getKopf().setsDwhonr(ivObjektnummer);
             //ivInf.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerINF++;
         }
         if (pvTtemp.equals("KON"))
         {
             ivStatus = KON;
             //ivKon = new KON();
             //ivKon.getKopf().setsDwhkdnr(ivKundennummer);
             //ivKon.getKopf().setsDwhonr(ivObjektnummer);
             //ivKon.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKON++;
         }
         if (pvTtemp.equals("KONTS"))
         {
             ivStatus = KONTS;
             //ivKonts = new KONTS();
             //ivKonts.getKopf().setsDwhkdnr(ivKundennummer);
             //ivKonts.getKopf().setsDwhonr(ivObjektnummer);
             //ivKonts.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKONTS++;
         }
         if (pvTtemp.equals("KTOZB"))
         {
             ivStatus = KTOZB;
             //ivKtozb = new KTOZB();
             //ivKtozb.getKopf().setsDwhkdnr(ivKundennummer);
             //ivKtozb.getKopf().setsDwhonr(ivObjektnummer);
             //ivKtozb.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTOZB++;
         }
         if (pvTtemp.equals("KTR"))
         {
             ivStatus = KTR;
             //ivKtr = new KTR();
             //ivKtr.getKopf().setsDwhkdnr(ivKundennummer);
             //ivKtr.getKopf().setsDwhonr(ivObjektnummer);
             //ivKtr.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTR++;
         }
         if (pvTtemp.equals("KTS"))
         {
             ivStatus = KTS;
             //ivKts = new KTS();
             //ivKts.getKopf().setsDwhkdnr(ivKundennummer);
             //ivKts.getKopf().setsDwhonr(ivObjektnummer);
             //ivKts.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerKTS++;
         }
         if (pvTtemp.equals("OBJ"))
         {
             ivStatus = OBJ;
             //ivObj = new OBJ();
             //ivObj.getKopf().setsDwhkdnr(ivKundennummer);
             //ivObj.getKopf().setsDwhonr(ivObjektnummer);
             //ivObj.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerOBJ++;
         }
         if (pvTtemp.equals("DWHNREC"))
         {
             ivStatus = REC;
             //ivRec = new REC();
             //ivRec.getKopf().setsDwhkdnr(ivKundennummer);
             //ivRec.getKopf().setsDwhonr(ivObjektnummer);
             //ivRec.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerREC++;
         }
         if (pvTtemp.equals("UMS"))
         {
             ivStatus = UMS;
             //ivUms = new UMS();
             //ivUms.getKopf().setsDwhkdnr(ivKundennummer);
             //ivUms.getKopf().setsDwhonr(ivObjektnummer);
             //ivUms.getKopf().setsDwhknr(ivKontonummer);
             ivZaehlerUMS++;
         }
     }
          
     /**
      * Setzen eines Wertes in ein Segment
      */
     private void setzeWert(int pvStatus, int pvLfd, String pvTemp)
     {
         switch (pvStatus)
         {
             case BAUFI:
                 if (pvLfd > 10)
                 {
                   Label labelBAUFI = new Label(pvLfd - 11, ivZaehlerBAUFI, pvTemp);
                   try
                   {
                     ivSheetBAUFI.addCell(labelBAUFI);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt BAUFI!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case INF:
                 if (pvLfd > 10)
                 {
                   Label labelINF = new Label(pvLfd - 11, ivZaehlerINF, pvTemp);
                   try
                   {
                     ivSheetINF.addCell(labelINF);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt INF!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case KON:
                 if (pvLfd > 10)
                 {
                   Label labelKON = new Label(pvLfd - 11, ivZaehlerKON, pvTemp);
                   try
                   {
                     ivSheetKON.addCell(labelKON);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt KON!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case KONTS:
                 if (pvLfd > 10)
                 {
                   Label labelKONTS = new Label(pvLfd - 11, ivZaehlerKONTS, pvTemp);
                   try
                   {
                     ivSheetKONTS.addCell(labelKONTS);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt KONTS!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case KTOZB:
                 if (pvLfd > 10)
                 {
                   Label labelKTOZB = new Label(pvLfd - 11, ivZaehlerKTOZB, pvTemp);
                   try
                   {
                     ivSheetKTOZB.addCell(labelKTOZB);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt KTOZB!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case KTR:
                 if (pvLfd > 10)
                 {
                   Label labelKTR = new Label(pvLfd - 11, ivZaehlerKTR, pvTemp);
                   try
                   {
                     ivSheetKTR.addCell(labelKTR);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt KTR!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case KTS:
                 if (pvLfd > 10)
                 {
                   Label labelKTS = new Label(pvLfd - 11, ivZaehlerKTS, pvTemp);
                   try
                   {
                     ivSheetKTS.addCell(labelKTS);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt KTS!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case OBJ:
                 if (pvLfd > 10)
                 {
                   Label labelOBJ = new Label(pvLfd - 11, ivZaehlerOBJ, pvTemp);
                   try
                   {
                     ivSheetOBJ.addCell(labelOBJ);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt BAUFI!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case REC:
                 if (pvLfd > 10)
                 {
                   Label labelREC = new Label(pvLfd - 11, ivZaehlerREC, pvTemp);
                   try
                   {
                     ivSheetREC.addCell(labelREC);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt REC!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                 break;
             case UMS:
                 if (pvLfd > 10)
                 {
                   Label labelUMS = new Label(pvLfd - 11, ivZaehlerUMS, pvTemp);
                   try
                   {
                     ivSheetUMS.addCell(labelUMS);
                   }
                   catch (Exception e)
                   {
                     JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt UMS!", "Fehler",
                                 JOptionPane.OK_OPTION);   
                   }
                 }
                  break;                         
              default:
                 System.out.println("Unbekannte Satzart: " + pvStatus);
         }   
     }    
    
     /**
      * 
      */
     private void setVorlaufsatz(int pos, String wert)
     {
       Label label = new Label(pos, ivZaehlerVorlaufsatz, wert);

       try
       {
         ivSheetVorlaufsatz.addCell(label);
       }
       catch (Exception e)
       {
           JOptionPane.showMessageDialog((JDialog)this, "Fehler beim Eintragen in das Blatt Vorlaufsatz!", "Fehler",
                       JOptionPane.OK_OPTION);   
       }
     }
     
   /**
    * @param pvEvent 
    */
  public void actionPerformed(ActionEvent pvEvent)
  {
    if (pvEvent.getSource() == ivButtonExit)
    {
      System.exit(0);
    }

    if (pvEvent.getSource() == ivButtonStart)
    {
      //System.out.println("buttonStart");
      convertDarlehen2Excel();
    }

    if (pvEvent.getSource() == ivButtonSource)
    {
      //System.out.println("buttonSource");
      JFileChooser lvFc = new JFileChooser();
      int lvReturnVal = lvFc.showOpenDialog(this);
      if (lvReturnVal == JFileChooser.APPROVE_OPTION)
        ivFieldSource.setText(lvFc.getSelectedFile().getAbsolutePath());
    }

    if (pvEvent.getSource() == ivButtonTarget)
    {
      //System.out.println("buttonTarget");
      JFileChooser lvFc = new JFileChooser();
      int returnVal = lvFc.showSaveDialog(this);
      if (returnVal == JFileChooser.APPROVE_OPTION)
        ivFieldTarget.setText(lvFc.getSelectedFile().getAbsolutePath());
    } 	   
  }
}
