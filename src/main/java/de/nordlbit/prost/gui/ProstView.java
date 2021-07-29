package de.nordlbit.prost.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.StringCrypt;
import de.nordlbit.prost.bd.Aufgabe;
import de.nordlbit.prost.bd.AufgabeComparator;
import de.nordlbit.prost.bd.AufgabeHandler;
import de.nordlbit.prost.bd.Ausloesemuster;
import de.nordlbit.prost.bd.Dateisuche;
import de.nordlbit.prost.bd.Ereignis;
import de.nordlbit.prost.bd.EreignisHandler;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.ProtokollEintrag;
import de.nordlbit.prost.bd.ProzessStarter;
import de.nordlbit.prost.bd.Trigger;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.VgAufgabenvektor;
import de.nordlbit.prost.bd.VgEreignis;
import de.nordlbit.prost.bd.VgEreignisvektor;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.BuchungstagVerwaltung;
import de.nordlbit.prost.dao.DatenbankParameter;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.protokoll.ProtokollDAO;

/**
 *
 * @author frankew
 */
public class ProstView extends javax.swing.JFrame {

    private Ereignis ivSelectedEreignis;
    private Dateisuche ivSelectedDateisuche;
    private ProzessStarter ivSelectedProzessStarter;
    private Trigger ivSelectedTrigger;
    private Ausloesemuster ivSelectedAusloesemuster;
    private Set<VgEreignis> ivVgEreignisse;
    private Set<VgAufgabe> ivVgAufgaben;
    private javax.swing.Timer ivTimer  = null;
    private ArrayList<String> ivListeMandanten; 

    /**
     * Creates new form ProstView
     * @param pvFilenameINI 
     */
    public ProstView(String pvFilenameINI) 
    {
        System.out.println("FilenameINI: " + pvFilenameINI);

        initComponents();

        // Parameter einlesen
        ivListeMandanten = new ArrayList<String>();
        //ivListeDatenbankParameter = new HashMap<String, DatenbankParameter>();
        HashMap<String, Mandant> lvListeNeuerMandanten = new HashMap<String, Mandant>();
        int lvMandantenNr = 1;
        IniReader lvReader = null;  
        String lvMandantname = new String();

        try 
        {
            lvReader = new IniReader(pvFilenameINI);
            do
            {
              lvMandantname = lvReader.getPropertyString("Mandanten", "Mandant" + lvMandantenNr, "Fehler");
              if (!lvMandantname.equals("Fehler"))
              {
                System.out.println("Mandant: " + lvMandantname);
                ivListeMandanten.add(lvMandantname);
                lvMandantenNr++;
              }
            } while (!lvMandantname.equals("Fehler"));
            System.out.println(ivListeMandanten.size() + " Mandanten eingelesen...");

            if (lvMandantenNr == 1)
            {
                System.out.println("Keine Mandanten in der 'mandanten.ini'...");
                System.exit(1);
            }
            
            DatenbankParameter lvDatenbankParameter;
            Mandant lvNeuerMandant;
            for (int lvLauf = 0; lvLauf < ivListeMandanten.size(); lvLauf++)
            {
                lvMandantname = ivListeMandanten.get(lvLauf);
                lvDatenbankParameter = new DatenbankParameter();
            
                String lvID = lvReader.getPropertyString(lvMandantname, "ID", "Fehler");
                if (lvID.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Keine ID in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("ID: " + lvID);

                String lvInstitutID = lvReader.getPropertyString(lvMandantname, "InstitutID", "Fehler");
                if (lvInstitutID.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Keine InstitutID in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("InstitutID: " + lvInstitutID);
                lvNeuerMandant = new Mandant(lvID, lvMandantname, lvInstitutID);
                lvListeNeuerMandanten.put(lvID, lvNeuerMandant);

                String lvUsername = lvReader.getPropertyString(lvMandantname, "Username", "Fehler");
                if (lvUsername.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Kein Username in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("Username: " + lvUsername);
                lvDatenbankParameter.setUsername(lvUsername);       
                
                String lvPassword = lvReader.getPropertyString(lvMandantname, "Password", "Fehler");
                if (lvPassword.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Kein Password in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("Password: " + lvPassword);
                lvDatenbankParameter.setPassword(StringCrypt.decodeROT13(lvPassword));
                
                String lvDatenbank = lvReader.getPropertyString(lvMandantname, "Datenbankname", "Fehler");
                if (lvDatenbank.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Kein Datenbankname in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("Datenbank: " + lvDatenbank);
                lvDatenbankParameter.setDatenbankname(lvDatenbank);

                String lvIP =  lvReader.getPropertyString(lvMandantname, "IP", "Fehler");
                if (lvIP.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Keine IP in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("IP: " + lvIP);
                lvDatenbankParameter.setIP(lvIP);
            
                String lvPort =  lvReader.getPropertyString(lvMandantname, "Port", "Fehler");
                if (lvPort.equals("Fehler"))
                {
                    System.out.println("[" + lvMandantname + "]Kein Port in der 'mandanten.ini'...");
                    System.exit(1);
                }
                System.out.println("Port: " + lvPort);
                lvDatenbankParameter.setPort(lvPort);
                
                DatenbankZugriff.getListeDatenbankParameter().put(lvMandantname, lvDatenbankParameter);
            }
        }
        catch (Exception exp)
        {
            System.out.println("[Mandanten] Fehler beim Laden der 'mandanten.ini'...");
            System.exit(1);
        }
        Mandant.setMandanten(lvListeNeuerMandanten);        
        
        // DB-Verbindung aufmachen
        // TODO Muss ueberarbeitet werden...
        //DatenbankZugriff.openConnection(lvMandant);
        this.loadData();
    }
    
    private void loadData(){
         // MandantenList
        this.fillMandantenList(); 
        this.fillMusterEreignisAuswahl();
        this.fillMusterAufgabenAuswahl();


        this.jComboBoxEreignisMandanten.setSelectedIndex(0);
        this.jComboBoxDateisucheMandant.setSelectedIndex(0);
        this.jComboBoxPSMandant.setSelectedIndex(0);
        this.jComboBoxTriggerMandant.setSelectedIndex(0);
        this.jComboBoxMusterMandant.setSelectedIndex(0);

        // EreignisList fuellen
        //this.refreshEreignisList();
        if (!this.defaultListModelEreignisse.isEmpty()) {
            this.jListEreignisseEreignisse.setSelectedIndex(0);
            this.jListEreignisseEreignisseMouseClicked(null);
        }
        
        if(!this.defaultListModelDateisuche.isEmpty()){
            this.jListDateisuche.setSelectedIndex(0);
            this.jListDateisucheMouseClicked(null);
        }
        
        if(!this.defaultListModelProzessStarter.isEmpty()){
            this.jListProzessStarter.setSelectedIndex(0);
            this.jListProzessStarterMouseClicked(null);
        }
        
        if(!this.defaultListModelTrigger.isEmpty()){
            this.jListTrigger.setSelectedIndex(0);
            this.jListTriggerMouseClicked(null);
        }
        
       
        this.initBuchungstagListe();  
        this.initVorgangBuchungstagAuswahl();
        this.initProtokollzeitraum(); 
    }
    
    private void enableEdit(boolean pvBool){
        /*
        this.jTabbedPane2.setEnabledAt(0, pvBool);
        this.jTabbedPane2.setEnabledAt(1, pvBool);
        this.jTabbedPane2.setEnabledAt(2, pvBool);
        this.jTabbedPane2.setEnabledAt(3, pvBool);
        this.jTabbedPane2.setEnabledAt(4, pvBool);
        this.repaint();
         */
    }

    // Mandanten holen
    private void fillMandantenList() {
        this.defaultComboBoxModelMandanten.removeAllElements();
        Collection<Mandant> lvMandanten = Mandant.getMandanten().values();
        for (Mandant pvMandant : lvMandanten) {
            this.defaultComboBoxModelMandanten.addElement(pvMandant);
        }
    }


    // Ereignisse
    private void refreshEreignisList() {
        this.defaultListModelEreignisse.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxEreignisMandanten.getSelectedItem();
        ArrayList<Ereignis> lvEreignisse = new ArrayList<Ereignis>((EreignisHandler.getInstance(lvMandant).getEreignisse()).values());
        Collections.sort(lvEreignisse);
        for (Ereignis pvEreignis : lvEreignisse) {
            this.defaultListModelEreignisse.addElement(pvEreignis);
        }
    }

    private void refreshDateisucheList() {
        this.defaultListModelDateisuche.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxDateisucheMandant.getSelectedItem();
        ArrayList<Aufgabe> lvDateisuche = new ArrayList<Aufgabe>((AufgabeHandler.getInstance(lvMandant).getDateisuche()).values());
        Collections.sort(lvDateisuche, new AufgabeComparator());
        for (Aufgabe pvAufgabe : lvDateisuche) {
            this.defaultListModelDateisuche.addElement((Dateisuche) pvAufgabe);
        }
    }

    private void refreshProzessStarterList() {
        this.defaultListModelProzessStarter.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxPSMandant.getSelectedItem();
        ArrayList<Aufgabe> lvProzessStarter = new ArrayList<Aufgabe>((AufgabeHandler.getInstance(lvMandant).getProzessStarter()).values());
        Collections.sort(lvProzessStarter, new AufgabeComparator());
        for (Aufgabe pvAufgabe : lvProzessStarter) {
            this.defaultListModelProzessStarter.addElement((ProzessStarter) pvAufgabe);
        }
    }
    
    private void refreshTriggerList() {
        this.defaultListModelTrigger.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxTriggerMandant.getSelectedItem();
        ArrayList<Aufgabe> lvTrigger = new ArrayList<Aufgabe>((AufgabeHandler.getInstance(lvMandant).getTrigger()).values());
        Collections.sort(lvTrigger, new AufgabeComparator());
        for (Aufgabe pvAufgabe : lvTrigger) {
            this.defaultListModelTrigger.addElement((Trigger) pvAufgabe);
        }
    }

    private String getNameOfSelectedEreignis() {
        if (this.ivSelectedEreignis != null) {
            return this.ivSelectedEreignis.getName();
        } else {
            return "";
        }
    }

    private void enableEreignisPflegeItems(boolean pvBool) {
        this.jButtonSaveEreignis.setEnabled(pvBool);
        this.jTextFieldEreignisBezeichnung.setEditable(pvBool);
        this.jCheckBoxEndeEreignis.setEnabled(pvBool);
        this.jCheckBoxStopEreignis.setEnabled(pvBool);
    }

    private void setEreignisFelder() {
        this.jTextFieldEreignisBezeichnung.setText(this.getNameOfSelectedEreignis());
        this.jCheckBoxEndeEreignis.setSelected(this.ivSelectedEreignis.isEndeEreignis());
        this.jCheckBoxStopEreignis.setSelected(this.ivSelectedEreignis.isStopEreignis());
    }

    // Dateisuche
    private void enableDateisuchePflegeItems(boolean pvBool) {
        this.jButtonSaveDateisuche.setEnabled(pvBool);
        this.jTextFieldNameDateisuche.setEditable(pvBool);
        this.jTextFieldPfadDateisuche.setEditable(pvBool);
        this.jTextFieldBezeichnungDateisuche.setEditable(pvBool);
        this.jComboBoxDateisucheFertigEreignis.setEnabled(pvBool);
        this.jCheckBoxDateisucheUnbedingteAufgabe.setEnabled(pvBool);
        this.jComboBoxDateisucheQualifier.setEnabled(pvBool);        
    }

    private void setDateisucheFelder() {
        this.jTextFieldBezeichnungDateisuche.setText(this.ivSelectedDateisuche.getBezeichnung());
        this.jTextFieldNameDateisuche.setText(this.ivSelectedDateisuche.getDateiname());
        this.jTextFieldPfadDateisuche.setText(this.ivSelectedDateisuche.getDateiPfad());
        this.jComboBoxDateisucheFertigEreignis.setSelectedIndex(this.getIndexOfFertigEreignisOfSelectedDateisuche());
        this.jCheckBoxDateisucheUnbedingteAufgabe.setSelected(this.ivSelectedDateisuche.isUnbedingteAufgabe());
        this.jComboBoxDateisucheQualifier.getModel().setSelectedItem(this.ivSelectedDateisuche.getQualifier());

    }
    
    private void setTriggerFelder(){
        this.jTextFieldBezeichnungTrigger.setText(this.ivSelectedTrigger.getBezeichnung());
        this.jFormattedTextFieldTriggerAusloesezeitpunkt.setText(this.ivSelectedTrigger.getAusloesezeitpunkt());
        this.jComboBoxTriggerFertigEreignis.setSelectedIndex(this.getIndexOfFertigEreignisOfSelectedTrigger());
        this.jCheckBoxTriggerUnbedingteAufgabe.setSelected(this.ivSelectedTrigger.isUnbedingteAufgabe());
        
    }
    
    private void enableTriggerPflegeItems(boolean pvBool) {
        this.jButtonSaveTrigger.setEnabled(pvBool);
        this.jTextFieldBezeichnungTrigger.setEditable(pvBool);
        this.jComboBoxTriggerFertigEreignis.setEnabled(pvBool);
        this.jCheckBoxTriggerUnbedingteAufgabe.setEnabled(pvBool);
        this.jFormattedTextFieldTriggerAusloesezeitpunkt.setEditable(pvBool);
    }

    private void fillDateisucheFertigEreignisList() {
        this.defaultComboBoxModelDateisucheFertigEreignis.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxDateisucheMandant.getSelectedItem();
        ArrayList<Ereignis> lvEreignisse = new ArrayList<Ereignis>((EreignisHandler.getInstance(lvMandant).getEreignisse()).values());
        Collections.sort(lvEreignisse);
        for (Ereignis pvEreignis : lvEreignisse) {
            this.defaultComboBoxModelDateisucheFertigEreignis.addElement(pvEreignis);
        }
    }
    
    private void fillTriggerFertigEreignisList() {
        this.defaultComboBoxModelTriggerFertigEreignisse.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxTriggerMandant.getSelectedItem();
        ArrayList<Ereignis> lvEreignisse = new ArrayList<Ereignis>((EreignisHandler.getInstance(lvMandant).getEreignisse()).values());
        Collections.sort(lvEreignisse);
        for (Ereignis pvEreignis : lvEreignisse) {
            this.defaultComboBoxModelTriggerFertigEreignisse.addElement(pvEreignis);
        }
    }

    private int getIndexOfFertigEreignisOfSelectedDateisuche() {
        if (this.ivSelectedDateisuche != null && this.ivSelectedDateisuche.getFertigstellungsEreignis() != null) {
            int lvIndex = this.defaultComboBoxModelDateisucheFertigEreignis.getIndexOf(this.ivSelectedDateisuche.getFertigstellungsEreignis());
            return lvIndex;
        } else {
            return 0;
        }
    }

    // Prozess-Starter
    private void enableProzessStarterPflegeItems(boolean pvBool) {
        this.jButtonSavePS.setEnabled(pvBool);
        this.jTextFieldPSName.setEditable(pvBool);
        this.jTextFieldPSPfad.setEditable(pvBool);
        this.jTextFieldPSBezeichnung.setEditable(pvBool);
        this.jComboBoxPSFertigEreignis.setEnabled(pvBool);
        this.jCheckBoxPsUndbedingteAufgabe.setEnabled(pvBool);
        this.jFormattedTextFieldVonZeitpunkt.setEditable(pvBool);
        this.jFormattedTextFieldBisZeitpunkt.setEditable(pvBool);
    }

    private void setProzessStarterFelder() {
        this.jTextFieldPSBezeichnung.setText(this.ivSelectedProzessStarter.getBezeichnung());
        this.jTextFieldPSName.setText(this.ivSelectedProzessStarter.getProzessname());
        this.jTextFieldPSPfad.setText(this.ivSelectedProzessStarter.getPfad());
        this.jFormattedTextFieldVonZeitpunkt.setText(this.ivSelectedProzessStarter.getZeitfensterVon());
        this.jFormattedTextFieldBisZeitpunkt.setText(this.ivSelectedProzessStarter.getZeitfensterBis());
        this.jComboBoxPSFertigEreignis.setSelectedIndex(this.getIndexOfFertigEreignisOfivSelectedProzessStarter());
        this.jCheckBoxPsUndbedingteAufgabe.setSelected(this.ivSelectedProzessStarter.isUnbedingteAufgabe());
      }
    
    private void fillProzessStarterFertigEreignisList() {
        this.defaultComboBoxModelPSFertigEreignisse.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxPSMandant.getSelectedItem();
        ArrayList<Ereignis> lvEreignisse = new ArrayList<Ereignis>((EreignisHandler.getInstance(lvMandant).getEreignisse()).values());
        Collections.sort(lvEreignisse);
        for (Ereignis pvEreignis : lvEreignisse) {
            this.defaultComboBoxModelPSFertigEreignisse.addElement(pvEreignis);
        }
    }

    private int getIndexOfFertigEreignisOfivSelectedProzessStarter() {
        if (this.ivSelectedProzessStarter != null && this.ivSelectedProzessStarter.getFertigstellungsEreignis() != null) {
            int lvIndex = this.defaultComboBoxModelPSFertigEreignisse.getIndexOf(this.ivSelectedProzessStarter.getFertigstellungsEreignis());
            return lvIndex;
        } else {
            return 0;
        }
    }
    
    private int getIndexOfFertigEreignisOfSelectedTrigger() {
        if (this.ivSelectedTrigger != null && this.ivSelectedTrigger.getFertigstellungsEreignis() != null) {
            int lvIndex = this.defaultComboBoxModelTriggerFertigEreignisse.getIndexOf(this.ivSelectedTrigger.getFertigstellungsEreignis());
            return lvIndex;
        } else {
            return 0;
        }
    }

    // Sortierung unbedingte Aufgaben
    private void fillUnbedingteAufgabenList() {
        this.defaultListModelAufgabenSortierung.removeAllElements();
        ArrayList<Aufgabe> lvAufgaben = new ArrayList<Aufgabe>();
        Mandant lvMandant = (Mandant) this.jComboBoxAufgabenSortierungMandant.getSelectedItem();
        lvAufgaben.addAll(AufgabeHandler.getUnbedingteAufgaben(lvMandant));
        Collections.sort(lvAufgaben);

        for (Aufgabe pvAufgabe : lvAufgaben) {
            System.out.println(pvAufgabe.toString() + " : " + pvAufgabe.getSortNr());
            defaultListModelAufgabenSortierung.addElement(pvAufgabe);
        }
    }

    // Ausloesemuster
    private void refreshMusterAnzeige() {
        this.jTreeMuster.setModel(new AusloesemusterTreeModel((Mandant) this.jComboBoxMusterMandant.getSelectedItem()));
        this.fillMusterAufgabenAuswahl();
        this.fillMusterEreignisAuswahl();
        this.jListMusterEreignisse.clearSelection();
        this.jListMusterAufgaben.clearSelection();
        try {
            this.jTreeMuster.setSelectionRow(1);
            this.jTreeMusterMouseClicked(null);
        } catch (Exception ex){
            // do nothing
        }
        this.jTreeMuster.updateUI();
    }

    private void fillMusterEreignisAuswahl() {
        this.defaultListModelMusterAuswahlEreignisse.removeAllElements();
        Mandant lvMandant = (Mandant) this.jComboBoxMusterMandant.getSelectedItem();
        System.out.println(lvMandant.toString());
        ArrayList<Ereignis> lvEreignisse = new ArrayList<Ereignis>((EreignisHandler.getInstance(lvMandant).getEreignisse()).values());
        Collections.sort(lvEreignisse);
        for (Ereignis pvEreignis : lvEreignisse) {
            this.defaultListModelMusterAuswahlEreignisse.addElement(pvEreignis);
        }
    }

    private void fillMusterAufgabenAuswahl() {
        this.defaultListModelMusterAuswahlAufgaben.removeAllElements();
        ArrayList<Aufgabe> lvAufgaben = new ArrayList<Aufgabe>();
        Mandant lvMandant = (Mandant) this.jComboBoxMusterMandant.getSelectedItem();
        Collection<Aufgabe> lvDateisucheAufgaben = AufgabeHandler.getDateisuche(lvMandant).values();
        Collection<Aufgabe> lvProzessStarterAufgaben = AufgabeHandler.getProzessStarter(lvMandant).values();

        lvAufgaben.addAll(lvDateisucheAufgaben);
        lvAufgaben.addAll(lvProzessStarterAufgaben);

        Collections.sort(lvAufgaben, new AufgabeComparator());

        for (Aufgabe pvAufgabe : lvAufgaben) {
            System.out.println(pvAufgabe.toString() + " : " + pvAufgabe.getSortNr());
            defaultListModelMusterAuswahlAufgaben.addElement(pvAufgabe);
        }
    }

    private void setMusterEreignisse(Ausloesemuster pvMuster) {
        this.jListMusterEreignisse.clearSelection();
        Collection<Ereignis> lvEreignisse = pvMuster.getEreignisse();
        int i = 0;
        int[] lvIndex;
        lvIndex = new int[lvEreignisse.size()];
        for (Ereignis pvEreignis : lvEreignisse) {
            lvIndex[i] = defaultListModelMusterAuswahlEreignisse.indexOf(pvEreignis);
            i++;
        }
        jListMusterEreignisse.setSelectedIndices(lvIndex);
    }

    private void setMusterAufgaben(Ausloesemuster pvMuster) {
        this.jListMusterAufgaben.clearSelection();
        Collection<Aufgabe> lvAufgaben = pvMuster.getMassnahmen();
        int i = 0;
        int[] lvIndex;
        lvIndex = new int[lvAufgaben.size()];
        for (Aufgabe pvAufgabe : lvAufgaben) {
            lvIndex[i] = defaultListModelMusterAuswahlAufgaben.indexOf(pvAufgabe);
            i++;
        }
        jListMusterAufgaben.setSelectedIndices(lvIndex);
    }
    
    /*
    private void fillUebersichtMandantenAuswahl() {
        this.defaultComboBoxModelUebersichtMandant.removeAllElements();
        Collection<Mandant> lvMandanten = Mandant.getMandanten().values();
        for (Mandant pvMandant : lvMandanten) {
            this.defaultComboBoxModelUebersichtMandant.addElement(pvMandant);
        }
    }
    */
    protected void loadVgObjekte() {
        String lvDatum = "";
        Date lvDate = this.jXDatePickerBuchungsdatum.getDate();
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.applyPattern("dd.MM.yyyy");
        if(lvDate != null){
            lvDatum = lvSdf.format(lvDate);
        }
        this.defaultListModelVorgaenge.removeAllElements();
        this.defaultComboBoxModelVgLebenszeichen.removeAllElements();
        this.jTableVgAufgaben.setModel(new DefaultTableModel());
        this.jTableVgEreignisse.setModel(new DefaultTableModel());
        this.jListVorgangsUebersicht.updateUI();
        this.jTableVgAufgaben.updateUI();
        this.jTableVgEreignisse.updateUI();


        if (!lvDatum.isEmpty()) {
            ArrayList<Vorgang> lvVorgaengeSorted = new ArrayList<Vorgang>(Vorgang.getVorgaenge((Mandant) this.jComboBoxUebersichtMandant.getSelectedItem(), lvDatum).values());
            Collections.sort(lvVorgaengeSorted);
            for (Vorgang pvVorgang : lvVorgaengeSorted) {
                this.defaultListModelVorgaenge.addElement(pvVorgang);
            }
        }
        
        if(!this.defaultListModelVorgaenge.isEmpty()){
            this.jListVorgangsUebersicht.setSelectedIndex(0);
            this.jListVorgangsUebersichtMouseClicked(null);
        } else {
            JOptionPane.showMessageDialog(this, "Es wurden keine Vorgaenge fuer das Buchungsdatum gefunden!");
        }
        
    }

    private void loadVgObjekte(Vorgang pvVorgang) {
        if(pvVorgang != null){
            this.jTableVgAufgaben.setModel(new DefaultTableModel());
            this.jTableVgEreignisse.setModel(new DefaultTableModel());
            this.jTableVgAufgaben.updateUI();
            this.jTableVgEreignisse.updateUI();

            // Aufgaben fuellen
            Collection<VgAufgabe> lvVgAufgaben = VgAufgabenvektor.getVgAufgaben(pvVorgang);
            this.ivVgAufgaben = new HashSet<VgAufgabe>();

            for (VgAufgabe pvAufgabe : lvVgAufgaben) {
                this.ivVgAufgaben.add(pvAufgabe);
            }

            // Ereignisse fuellen
            Collection<VgEreignis> lvVgEreignisse = VgEreignisvektor.getVgEreignisse(pvVorgang);
            this.ivVgEreignisse = new HashSet<VgEreignis>();

            for (VgEreignis pvEreignis : lvVgEreignisse) {
                this.ivVgEreignisse.add(pvEreignis);
            }
            
            // Lebenszeichen fuellen
            ArrayList<String> lvLebenszeichen = pvVorgang.getAllLebenszeichen();
            Comparator<String> lvComparator = Collections.reverseOrder();
            Collections.sort(lvLebenszeichen, lvComparator);
            
            this.defaultComboBoxModelVgLebenszeichen.removeAllElements();
            for (Iterator<String> it = lvLebenszeichen.iterator(); it.hasNext();) {
                String lvLz = it.next();
                this.defaultComboBoxModelVgLebenszeichen.addElement(lvLz);
            }
            
        }

    }

    private void fillVgEreignisTable() {
        int lvRow = 0;
        this.defaultTableModelVgEreignisse = new DefaultTableModel(this.ivVgEreignisse.size(), 3);
        String lvSpalten[] = {"Vorgang", "Ereignis", "Ausloesezeitpunkt"};
        this.defaultTableModelVgEreignisse.setColumnIdentifiers(lvSpalten);
        for (VgEreignis pvEreignis : this.ivVgEreignisse) {
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getVorgangId(), lvRow, 0);
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getEreignis().getName(), lvRow, 1);
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getAusloesezeitpunkt(), lvRow, 2);
            lvRow = lvRow + 1;
        }
        this.jTableVgEreignisse.setModel(defaultTableModelVgEreignisse);
        this.jTableVgEreignisse.getRowSorter().toggleSortOrder(2);
    }

    private void fillVgAufgabenTable() {

      this.jTableVgAufgaben.setModel(defaultTableModelVgAufgaben);
      ArrayList<VgAufgabe> lvList = new ArrayList<VgAufgabe>();
      lvList.addAll(this.ivVgAufgaben);
      this.jTableVgAufgaben.setDefaultRenderer(Object.class, new ColoredTableCellRenderer());
      this.jTableVgAufgaben.setModel(new VgAufgabeTableModel(lvList));
      this.jTableVgAufgaben.getRowSorter().toggleSortOrder(3);
        
        
      TableColumnModel columnModel = jTableVgAufgaben.getColumnModel();
            
      // Die einzelnen Columns ansprechen und die Groesse setzen
      columnModel.getColumn( 0 ).setPreferredWidth( 185 );
      columnModel.getColumn( 1 ).setPreferredWidth( 60 );
      columnModel.getColumn( 2 ).setPreferredWidth( 60 );
      columnModel.getColumn( 3 ).setPreferredWidth( 150);
      columnModel.getColumn( 4 ).setPreferredWidth( 150);  
    }

    public void cleanEreignisFelder() {
        this.jTextFieldEreignisBezeichnung.setText("");
        this.jCheckBoxEndeEreignis.setSelected(false);
    }

    public void cleanProzessStarterFelder() {
        this.jTextFieldPSBezeichnung.setText("");
        this.jTextFieldPSName.setText("");
        this.jTextFieldPSPfad.setText("");
        this.jComboBoxPSFertigEreignis.setSelectedIndex(-1);
        this.jCheckBoxPsUndbedingteAufgabe.setSelected(false);
    }

    public void cleanDateisucheFelder() {
        this.jTextFieldPfadDateisuche.setText("");
        this.jTextFieldBezeichnungDateisuche.setText("");
        this.jTextFieldNameDateisuche.setText("");
        this.jComboBoxDateisucheFertigEreignis.setSelectedIndex(-1);
        this.jCheckBoxDateisucheUnbedingteAufgabe.setSelected(false);
    }
    
    public void cleanTriggerFelder() {
        this.jTextFieldBezeichnungTrigger.setText("");
        this.jFormattedTextFieldTriggerAusloesezeitpunkt.setText("");
        this.jCheckBoxTriggerUnbedingteAufgabe.setSelected(false);
    }
    
    public void refreshEreignisListen(){
        // andere Ereignislisten neu fuellen
        this.fillDateisucheFertigEreignisList();
        this.fillProzessStarterFertigEreignisList();
        this.fillTriggerFertigEreignisList();
        this.fillMusterEreignisAuswahl();
        this.enableEreignisPflegeItems(false);
    }
    
    public void refreshAufgabenListen(){
        // andere Aufgabenlisten neu fuellen
        this.fillMusterAufgabenAuswahl();
        this.fillUnbedingteAufgabenList();
    }
    
    public static int okcancel(String theMessage) {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
            "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }
    
    public static boolean checkPasswort() {
        
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setEchoChar('#');
        JOptionPane.showMessageDialog(
                null,
                passwordField,
                "Enter password for PROST",
                JOptionPane.OK_OPTION);
        String lvPasswort = String.valueOf(passwordField.getPassword());
        if(lvPasswort.endsWith("prost123")){
            return true;
        }
        return false;
    }
    
    private void initBuchungstagListe(){
        
        Mandant lvMandant = (Mandant) this.jComboBoxBuchungstageMandanten.getSelectedItem();
        if(lvMandant != null){
            GregorianCalendar lvAktCal = (GregorianCalendar) this.jXMonthViewBuchungstage.getCalendar();
            DateFormat dfmt = new SimpleDateFormat("yyMM" );
            String lvSearch = dfmt.format(lvAktCal.getTime()); 

            BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(lvMandant);
            ArrayList<String> lvBuchungstage = lvBuTagVerw.getBuchungstage(lvSearch);


            Date[] lvDates = new Date[lvBuchungstage.size()];
            int i = 0;
            for (Iterator<String> it = lvBuchungstage.iterator(); it.hasNext();) {
                String lvDateString = (String)it.next();
                GregorianCalendar lvCal = new GregorianCalendar();
                String lvYear = lvDateString.substring(0, 2);
                String lvMonth = lvDateString.substring(2, 4);
                String lvDay = lvDateString.substring(4, 6);
                int lvYearInt = Integer.parseInt(lvYear);
                int lvMonthInt = Integer.parseInt(lvMonth);
                int lvDayInt = Integer.parseInt(lvDay);
                lvCal.set(2000 + lvYearInt, lvMonthInt - 1, lvDayInt);
                lvDates[i] = lvCal.getTime();
                i = i + 1;
            }

            this.jXMonthViewBuchungstage.setFlaggedDates(lvDates);

            boolean lvGefunden = false;
            String lvUltimo = lvBuTagVerw.getUltimo(lvSearch, lvMandant, false, false, true);
            // Kein Jahresultimo
            if (lvUltimo.isEmpty())
            {
                jCheckBoxJahresultimo.setSelected(false);
                //jFormattedTextField1.setText("");
            }
            else
            {
                // Jahresultimo
                lvGefunden = true;
                jCheckBoxJahresultimo.setSelected(true);
                jFormattedTextFieldUltimo.setText(lvUltimo.substring(4) + "." + lvUltimo.substring(2, 4) + ".20" + lvUltimo.substring(0, 2));
            }
            
            
           lvUltimo = lvBuTagVerw.getUltimo(lvSearch, lvMandant, false, true, false);
           // Kein Quartalsultimo
           if (lvUltimo.isEmpty())
           {
               jCheckBoxQuartalsultimo.setSelected(false);
               //jFormattedTextField1.setText("");
           }
           else
           {
               // Quartalsultimo
               lvGefunden = true;
               jCheckBoxQuartalsultimo.setSelected(true);
               jFormattedTextFieldUltimo.setText(lvUltimo.substring(4) + "." + lvUltimo.substring(2, 4) + ".20" + lvUltimo.substring(0, 2));
           }
               
           lvUltimo = lvBuTagVerw.getUltimo(lvSearch, lvMandant, true, false, false);
           // Kein Monatsultimo
           if (lvUltimo.isEmpty())
           {
              jCheckBoxMonatsultimo.setSelected(false);
              //jFormattedTextField1.setText("");
           }
           else
           {
              // Monatsultimo
               lvGefunden = true;
              jCheckBoxMonatsultimo.setSelected(true);
              jFormattedTextFieldUltimo.setText(lvUltimo.substring(4) + "." + lvUltimo.substring(2, 4) + ".20" + lvUltimo.substring(0, 2));
           }
           
           if (!lvGefunden)
           {
               jFormattedTextFieldUltimo.setText("");              
           }
             
        /*
        GregorianCalendar lvAktCal = (GregorianCalendar) this.jXMonthViewBuchungstage.getCalendar();
        GregorianCalendar lvCompareCal = new GregorianCalendar();
        lvCompareCal.set(lvAktCal.get(GregorianCalendar.YEAR), lvAktCal.get(GregorianCalendar.MONTH), lvAktCal.get(GregorianCalendar.DAY_OF_MONTH));
        ArrayList<Date> lvUnselectableDates = new ArrayList<Date>();
        for (int i = 1; i <= lvCompareCal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH); i++) {
            if(lvCompareCal.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SATURDAY || lvCompareCal.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
                lvUnselectableDates.add(lvCompareCal.getTime());
                System.out.println(lvCompareCal.getTime());
                        
            }
            lvCompareCal.roll(GregorianCalendar.DAY_OF_MONTH, 1);
        }
        this.jXMonthViewBuchungstage.setUnselectableDates(lvUnselectableDates.toArray(lvSet));
        */
    
        }

    }
    
    private void initVorgangBuchungstagAuswahl(){
        GregorianCalendar lvAktCal = (GregorianCalendar) this.jXDatePickerBuchungsdatum.getMonthView().getCalendar();
        DateFormat dfmt = new SimpleDateFormat("yy" );
        String lvSearch = dfmt.format(lvAktCal.getTime()); 
        
        BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(((Mandant) this.jComboBoxUebersichtMandant.getSelectedItem()));
        ArrayList<String> lvBuchungstage = lvBuTagVerw.getBuchungstage(lvSearch);
        

        Date[] lvDates = new Date[lvBuchungstage.size()];
        int i = 0;
        for (Iterator<String> it = lvBuchungstage.iterator(); it.hasNext();) {
            String lvDateString = (String)it.next();
            GregorianCalendar lvCal = new GregorianCalendar();
            String lvYear = lvDateString.substring(0, 2);
            String lvMonth = lvDateString.substring(2, 4);
            String lvDay = lvDateString.substring(4, 6);
            int lvYearInt = Integer.parseInt(lvYear);
            int lvMonthInt = Integer.parseInt(lvMonth);
            int lvDayInt = Integer.parseInt(lvDay);
            lvCal.set(2000 + lvYearInt, lvMonthInt - 1, lvDayInt);
            lvDates[i] = lvCal.getTime();
            i = i + 1;
        }
        
        this.jXDatePickerBuchungsdatum.getMonthView().setFlaggedDayForeground(new java.awt.Color(255, 0, 0));
        this.jXDatePickerBuchungsdatum.getMonthView().setFlaggedDates(lvDates);

    }
    
    private void initProtokollzeitraum(){
        this.jXDatePickerProtokollVon.setDate(new Date());
        this.jXDatePickerProtokollBis.setDate(new Date());        
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        defaultListModelEreignisse = new javax.swing.DefaultListModel<Ereignis>();
        defaultListModelDateisuche = new javax.swing.DefaultListModel<Dateisuche>();
        defaultComboBoxModelMandanten = new javax.swing.DefaultComboBoxModel<Mandant>();
        defaultComboBoxModelPSFertigEreignisse = new javax.swing.DefaultComboBoxModel<Ereignis>();
        defaultComboBoxModelDateisucheFertigEreignis = new javax.swing.DefaultComboBoxModel<Ereignis>();
        defaultListModelProzessStarter = new javax.swing.DefaultListModel<ProzessStarter>();
        defaultListModelAufgabenSortierung = new javax.swing.DefaultListModel<Aufgabe>();
        defaultListModelMusterAuswahlEreignisse = new javax.swing.DefaultListModel<Ereignis>();
        defaultListModelMusterAuswahlAufgaben = new javax.swing.DefaultListModel<Aufgabe>();
        defaultTableModelVgEreignisse = new javax.swing.table.DefaultTableModel();
        defaultTableModelVgAufgaben = new javax.swing.table.DefaultTableModel();
        defaultListModelVorgaenge = new javax.swing.DefaultListModel<Vorgang>();
        defaultListModelTrigger = new javax.swing.DefaultListModel<Trigger>();
        defaultComboBoxModelTriggerFertigEreignisse = new javax.swing.DefaultComboBoxModel<Ereignis>();
        defaultComboBoxModelVgLebenszeichen = new javax.swing.DefaultComboBoxModel<String>();
        defaultTableModelProtokoll = new javax.swing.table.DefaultTableModel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanelEreignisse = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListEreignisseEreignisse = new javax.swing.JList<Ereignis>();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldEreignisBezeichnung = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxEreignisMandanten = new javax.swing.JComboBox<Mandant>();
        jButtonEreignisseNew = new javax.swing.JButton();
        jButtonEreignisseDelete = new javax.swing.JButton();
        jButtonEditEreignis = new javax.swing.JButton();
        jButtonSaveEreignis = new javax.swing.JButton();
        jButtonCancelEreignis = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        jCheckBoxEndeEreignis = new javax.swing.JCheckBox();
        jCheckBoxStopEreignis = new javax.swing.JCheckBox();
        jPanelAufgaben = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxDateisucheMandant = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListDateisuche = new javax.swing.JList<Dateisuche>();
        jLabel9 = new javax.swing.JLabel();
        jButtonNewDateisuche = new javax.swing.JButton();
        jButtonDeleteDateisuche = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldNameDateisuche = new javax.swing.JTextField();
        jTextFieldPfadDateisuche = new javax.swing.JTextField();
        jButtonEditDateisuche = new javax.swing.JButton();
        jButtonSaveDateisuche = new javax.swing.JButton();
        jButtonCancelDateisuche = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jComboBoxDateisucheFertigEreignis = new javax.swing.JComboBox<Ereignis>();
        jCheckBoxDateisucheUnbedingteAufgabe = new javax.swing.JCheckBox();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldBezeichnungDateisuche = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxDateisucheQualifier = new javax.swing.JComboBox();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jComboBoxPSMandant = new javax.swing.JComboBox<Mandant>();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jListProzessStarter = new javax.swing.JList<ProzessStarter>();
        jButtonNewPS = new javax.swing.JButton();
        jButtonDeletePS = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldPSName = new javax.swing.JTextField();
        jButtonEditPS = new javax.swing.JButton();
        jButtonSavePS = new javax.swing.JButton();
        jButtonCancelPS = new javax.swing.JButton();
        jCheckBoxPsUndbedingteAufgabe = new javax.swing.JCheckBox();
        jLabel27 = new javax.swing.JLabel();
        jComboBoxPSFertigEreignis = new javax.swing.JComboBox<Ereignis>();
        jLabel34 = new javax.swing.JLabel();
        jTextFieldPSPfad = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jTextFieldPSBezeichnung = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldVonZeitpunkt = new javax.swing.JFormattedTextField();
        jFormattedTextFieldBisZeitpunkt = new javax.swing.JFormattedTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxTriggerMandant = new javax.swing.JComboBox<Mandant>();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTrigger = new javax.swing.JList<Trigger>();
        jButtonNewTrigger = new javax.swing.JButton();
        jButtonDeleteTrigger = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldBezeichnungTrigger = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jFormattedTextFieldTriggerAusloesezeitpunkt = new javax.swing.JFormattedTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jComboBoxTriggerFertigEreignis = new javax.swing.JComboBox<Ereignis>();
        jCheckBoxTriggerUnbedingteAufgabe = new javax.swing.JCheckBox();
        jButtonEditTrigger = new javax.swing.JButton();
        jButtonSaveTrigger = new javax.swing.JButton();
        jButtonCancelTrigger = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jListUnbedingteAufgaben = new javax.swing.JList<Aufgabe>();
        jButtonAufgabeUp = new javax.swing.JButton();
        jButtonAufgabeDown = new javax.swing.JButton();
        jButtonUACancel = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jComboBoxAufgabenSortierungMandant = new javax.swing.JComboBox<Mandant>();
        jButtonSaveReihenfolge = new javax.swing.JButton();
        jPanelAusloesemuster = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTreeMuster = new javax.swing.JTree();
        jButtonMusterNew = new javax.swing.JButton();
        jButtonMusterDelete = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jListMusterEreignisse = new javax.swing.JList<Ereignis>();
        jScrollPane12 = new javax.swing.JScrollPane();
        jListMusterAufgaben = new javax.swing.JList<Aufgabe>();
        jButtonEditMuster = new javax.swing.JButton();
        jButtonSaveMuster = new javax.swing.JButton();
        jButtonCancelMuster = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        jComboBoxMusterMandant = new javax.swing.JComboBox<Mandant>();
        jTextFieldMusterBezeichnung = new javax.swing.JTextField();
        jPanelBuchungstage = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jButtonAddBuchungstag = new javax.swing.JButton();
        jButtonRemoveBuchungstag = new javax.swing.JButton();
        jXMonthViewBuchungstage = new org.jdesktop.swingx.JXMonthView();
        jButtonLastMonth = new javax.swing.JButton();
        jButtonNextMonth = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jComboBoxBuchungstageMandanten = new javax.swing.JComboBox<Mandant>();
        jLabel45 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jFormattedTextFieldUltimo = new javax.swing.JFormattedTextField();
        jCheckBoxMonatsultimo = new javax.swing.JCheckBox();
        jCheckBoxQuartalsultimo = new javax.swing.JCheckBox();
        jCheckBoxJahresultimo = new javax.swing.JCheckBox();
        jPanelVorgaenge = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jComboBoxUebersichtMandant = new javax.swing.JComboBox<Mandant>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jListVorgangsUebersicht = new javax.swing.JList<Vorgang>();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableVgAufgaben = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableVgEreignisse = new javax.swing.JTable();
        jButtonVorgangLoad = new javax.swing.JButton();
        jButtonRepeatAufgabe = new javax.swing.JButton();
        jButtonCloseVorgang = new javax.swing.JButton();
        jCheckBoxRepeadLoad = new javax.swing.JCheckBox();
        jXDatePickerBuchungsdatum = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jComboBoxVgLebenszeichen = new javax.swing.JComboBox<String>();
        jPanelProtokoll = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jXDatePickerProtokollVon = new org.jdesktop.swingx.JXDatePicker();
        jLabel40 = new javax.swing.JLabel();
        jXDatePickerProtokollBis = new org.jdesktop.swingx.JXDatePicker();
        jLabel41 = new javax.swing.JLabel();
        jButtonLoadAenderungen = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableProtokoll = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jComboBoxProtokollMandant = new javax.swing.JComboBox<Mandant>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuReload = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROST - Prozess-Steuerung-TXS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jListEreignisseEreignisse.setModel(defaultListModelEreignisse);
        jListEreignisseEreignisse.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListEreignisseEreignisse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListEreignisseEreignisseMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListEreignisseEreignisse);

        jLabel1.setText("Bezeichnung:");

        jTextFieldEreignisBezeichnung.setEditable(false);
        jTextFieldEreignisBezeichnung.setText(getNameOfSelectedEreignis());

        jLabel2.setText("Mandant:");

        jComboBoxEreignisMandanten.setModel(defaultComboBoxModelMandanten);
        jComboBoxEreignisMandanten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEreignisMandantenActionPerformed(evt);
            }
        });

        jButtonEreignisseNew.setText("+");
        jButtonEreignisseNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEreignisseNewActionPerformed(evt);
            }
        });

        jButtonEreignisseDelete.setText("-");
        jButtonEreignisseDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEreignisseDeleteActionPerformed(evt);
            }
        });

        jButtonEditEreignis.setText("Edit");
        jButtonEditEreignis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditEreignisActionPerformed(evt);
            }
        });

        jButtonSaveEreignis.setText("Save");
        jButtonSaveEreignis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveEreignisActionPerformed(evt);
            }
        });

        jButtonCancelEreignis.setText("Cancel");
        jButtonCancelEreignis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelEreignisActionPerformed(evt);
            }
        });

        jLabel28.setText("Ereignisse:");

        jCheckBoxEndeEreignis.setText("Bearbeitungsendeereignis");
        jCheckBoxEndeEreignis.setEnabled(false);

        jCheckBoxStopEreignis.setText("Stopereignis");
        jCheckBoxStopEreignis.setEnabled(false);

        javax.swing.GroupLayout jPanelEreignisseLayout = new javax.swing.GroupLayout(jPanelEreignisse);
        jPanelEreignisse.setLayout(jPanelEreignisseLayout);
        jPanelEreignisseLayout.setHorizontalGroup(
            jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                        .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxEreignisMandanten, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                                .addComponent(jButtonEreignisseNew)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonEreignisseDelete))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxEndeEreignis)
                                    .addComponent(jTextFieldEreignisBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxStopEreignis)))
                            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jButtonEditEreignis)
                                .addGap(26, 26, 26)
                                .addComponent(jButtonSaveEreignis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCancelEreignis)))))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        jPanelEreignisseLayout.setVerticalGroup(
            jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxEreignisMandanten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelEreignisseLayout.createSequentialGroup()
                        .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEreignisBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxEndeEreignis, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxStopEreignis)
                        .addGap(27, 27, 27)
                        .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEditEreignis)
                            .addComponent(jButtonSaveEreignis)
                            .addComponent(jButtonCancelEreignis)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelEreignisseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonEreignisseNew)
                    .addComponent(jButtonEreignisseDelete))
                .addContainerGap(166, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Ereignisse", jPanelEreignisse);

        jLabel8.setText("Mandant:");

        jComboBoxDateisucheMandant.setModel(defaultComboBoxModelMandanten);
        jComboBoxDateisucheMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDateisucheMandantActionPerformed(evt);
            }
        });

        jListDateisuche.setModel(defaultListModelDateisuche);
        jListDateisuche.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListDateisucheMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jListDateisuche);

        jLabel9.setText("Dateisuche:");

        jButtonNewDateisuche.setText("+");
        jButtonNewDateisuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewDateisucheActionPerformed(evt);
            }
        });

        jButtonDeleteDateisuche.setText("-");
        jButtonDeleteDateisuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteDateisucheActionPerformed(evt);
            }
        });

        jLabel10.setText("Dateiname:");

        jLabel11.setText("Dateipfad:");

        jTextFieldNameDateisuche.setEditable(false);

        jTextFieldPfadDateisuche.setEditable(false);

        jButtonEditDateisuche.setText("Edit");
        jButtonEditDateisuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditDateisucheActionPerformed(evt);
            }
        });

        jButtonSaveDateisuche.setText("Save");
        jButtonSaveDateisuche.setEnabled(false);
        jButtonSaveDateisuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveDateisucheActionPerformed(evt);
            }
        });

        jButtonCancelDateisuche.setText("Cancel");
        jButtonCancelDateisuche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelDateisucheActionPerformed(evt);
            }
        });

        jLabel25.setText("Fertig-Ereignis:");

        jComboBoxDateisucheFertigEreignis.setModel(defaultComboBoxModelDateisucheFertigEreignis);
        jComboBoxDateisucheFertigEreignis.setEnabled(false);

        jCheckBoxDateisucheUnbedingteAufgabe.setText("unbedingte Aufgabe");
        jCheckBoxDateisucheUnbedingteAufgabe.setEnabled(false);

        jLabel19.setText("Bezeichnung:");

        jTextFieldBezeichnungDateisuche.setEditable(false);

        jLabel3.setText("Qualifier:");

        jComboBoxDateisucheQualifier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { ".Djjmmdd", "_jjjjmmdd.csv", "_jjjjmmdd.xml", "jjjjmmdd_" }));
        jComboBoxDateisucheQualifier.setEnabled(false);
        jComboBoxDateisucheQualifier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDateisucheQualifierActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonNewDateisuche)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDeleteDateisuche))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxDateisucheMandant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNameDateisuche, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldBezeichnungDateisuche, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jButtonEditDateisuche)
                        .addGap(26, 26, 26)
                        .addComponent(jButtonSaveDateisuche)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonCancelDateisuche))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldPfadDateisuche)
                            .addComponent(jComboBoxDateisucheFertigEreignis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jCheckBoxDateisucheUnbedingteAufgabe)
                            .addComponent(jComboBoxDateisucheQualifier, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel3)
                    .addComponent(jLabel11))
                .addContainerGap(343, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldBezeichnungDateisuche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextFieldNameDateisuche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldPfadDateisuche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxDateisucheFertigEreignis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxDateisucheQualifier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(7, 7, 7)
                        .addComponent(jCheckBoxDateisucheUnbedingteAufgabe)
                        .addGap(34, 34, 34)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEditDateisuche)
                            .addComponent(jButtonSaveDateisuche)
                            .addComponent(jButtonCancelDateisuche))
                        .addGap(43, 43, 43))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBoxDateisucheMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonDeleteDateisuche)
                            .addComponent(jButtonNewDateisuche))))
                .addContainerGap(202, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Dateisuche", jPanel6);

        jLabel16.setText("Mandant:");

        jComboBoxPSMandant.setModel(defaultComboBoxModelMandanten);
        jComboBoxPSMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPSMandantActionPerformed(evt);
            }
        });

        jLabel17.setText("Prozess-Starter:");

        jListProzessStarter.setModel(defaultListModelProzessStarter);
        jListProzessStarter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListProzessStarterMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jListProzessStarter);

        jButtonNewPS.setText("+");
        jButtonNewPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewPSActionPerformed(evt);
            }
        });

        jButtonDeletePS.setText("-");
        jButtonDeletePS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeletePSActionPerformed(evt);
            }
        });

        jLabel18.setText("Name:");

        jTextFieldPSName.setEditable(false);

        jButtonEditPS.setText("Edit");
        jButtonEditPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditPSActionPerformed(evt);
            }
        });

        jButtonSavePS.setText("Save");
        jButtonSavePS.setEnabled(false);
        jButtonSavePS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSavePSActionPerformed(evt);
            }
        });

        jButtonCancelPS.setText("Cancel");
        jButtonCancelPS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelPSActionPerformed(evt);
            }
        });

        jCheckBoxPsUndbedingteAufgabe.setText("unbedingteAufgabe");
        jCheckBoxPsUndbedingteAufgabe.setEnabled(false);

        jLabel27.setText("Fertig-Ereignis:");

        jComboBoxPSFertigEreignis.setModel(defaultComboBoxModelPSFertigEreignisse);
        jComboBoxPSFertigEreignis.setEnabled(false);

        jLabel34.setText("Pfad:");

        jTextFieldPSPfad.setEditable(false);

        jLabel32.setText("Bezeichnung:");

        jTextFieldPSBezeichnung.setEditable(false);

        jLabel4.setText("Ausfuehrungszeitpunkt:");

        jLabel5.setText("von: (hh:mm)");

        jLabel6.setText("bis: (hh:mm)");

        jFormattedTextFieldVonZeitpunkt.setEditable(false);
        jFormattedTextFieldVonZeitpunkt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        jFormattedTextFieldBisZeitpunkt.setEditable(false);
        jFormattedTextFieldBisZeitpunkt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButtonNewPS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonDeletePS))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxPSMandant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jButtonEditPS)
                                .addGap(75, 75, 75)
                                .addComponent(jButtonSavePS)
                                .addGap(50, 50, 50)
                                .addComponent(jButtonCancelPS))
                            .addComponent(jLabel4)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addComponent(jLabel18)
                                            .addGap(52, 52, 52))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                            .addComponent(jLabel34)
                                            .addGap(57, 57, 57)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldPSPfad, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldPSName, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel8Layout.createSequentialGroup()
                                    .addComponent(jLabel32)
                                    .addGap(27, 27, 27)
                                    .addComponent(jTextFieldPSBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jFormattedTextFieldVonZeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxPSFertigEreignis, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextFieldBisZeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxPsUndbedingteAufgabe, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10))
                    .addComponent(jLabel17))
                .addContainerGap(376, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jComboBoxPSMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jTextFieldPSBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextFieldPSName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(jTextFieldPSPfad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jFormattedTextFieldVonZeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jFormattedTextFieldBisZeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jComboBoxPSFertigEreignis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxPsUndbedingteAufgabe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEditPS)
                            .addComponent(jButtonSavePS)
                            .addComponent(jButtonCancelPS)))
                    .addComponent(jScrollPane6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonDeletePS)
                    .addComponent(jButtonNewPS))
                .addGap(119, 119, 119))
        );

        jTabbedPane3.addTab("Prozess-Starter", jPanel8);

        jLabel14.setText("Mandant:");

        jComboBoxTriggerMandant.setModel(defaultComboBoxModelMandanten);
        jComboBoxTriggerMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTriggerMandantActionPerformed(evt);
            }
        });

        jLabel15.setText("Trigger:");

        jListTrigger.setModel(defaultListModelTrigger);
        jListTrigger.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListTriggerMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListTrigger);

        jButtonNewTrigger.setText("+");
        jButtonNewTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewTriggerActionPerformed(evt);
            }
        });

        jButtonDeleteTrigger.setText("-");
        jButtonDeleteTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteTriggerActionPerformed(evt);
            }
        });

        jLabel20.setText("Bezeichnung:");

        jTextFieldBezeichnungTrigger.setEditable(false);

        jLabel33.setText("Ausloesezeitpunkt:");

        jFormattedTextFieldTriggerAusloesezeitpunkt.setEditable(false);
        jFormattedTextFieldTriggerAusloesezeitpunkt.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT))));

        jLabel37.setText("( hh:mm )");

        jLabel38.setText("Fertigereignis:");

        jComboBoxTriggerFertigEreignis.setModel(defaultComboBoxModelTriggerFertigEreignisse);
        jComboBoxTriggerFertigEreignis.setEnabled(false);

        jCheckBoxTriggerUnbedingteAufgabe.setText("unbedingte Aufgabe");
        jCheckBoxTriggerUnbedingteAufgabe.setEnabled(false);
        jCheckBoxTriggerUnbedingteAufgabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxTriggerUnbedingteAufgabeActionPerformed(evt);
            }
        });

        jButtonEditTrigger.setText("Edit");
        jButtonEditTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditTriggerActionPerformed(evt);
            }
        });

        jButtonSaveTrigger.setText("Save");
        jButtonSaveTrigger.setEnabled(false);
        jButtonSaveTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveTriggerActionPerformed(evt);
            }
        });

        jButtonCancelTrigger.setText("Cancel");
        jButtonCancelTrigger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelTriggerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonNewTrigger)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonDeleteTrigger))
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel15)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxTriggerMandant, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel20)
                            .addComponent(jLabel38))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBoxTriggerUnbedingteAufgabe)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButtonCancelTrigger)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldBezeichnungTrigger)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jFormattedTextFieldTriggerAusloesezeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel37))
                                    .addComponent(jComboBoxTriggerFertigEreignis, 0, 220, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonEditTrigger)
                        .addGap(119, 119, 119)
                        .addComponent(jButtonSaveTrigger)))
                .addContainerGap(350, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxTriggerMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jTextFieldBezeichnungTrigger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jFormattedTextFieldTriggerAusloesezeitpunkt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(jComboBoxTriggerFertigEreignis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxTriggerUnbedingteAufgabe)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonEditTrigger)
                            .addComponent(jButtonSaveTrigger)
                            .addComponent(jButtonCancelTrigger))))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNewTrigger)
                    .addComponent(jButtonDeleteTrigger))
                .addContainerGap(201, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Trigger", jPanel1);

        jLabel35.setText("Reihenfolge der unbedingten Aufgaben:");

        jListUnbedingteAufgaben.setModel(defaultListModelAufgabenSortierung);
        jScrollPane15.setViewportView(jListUnbedingteAufgaben);

        jButtonAufgabeUp.setText("Up");
        jButtonAufgabeUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAufgabeUpActionPerformed(evt);
            }
        });

        jButtonAufgabeDown.setText("Down");
        jButtonAufgabeDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAufgabeDownActionPerformed(evt);
            }
        });

        jButtonUACancel.setBackground(new java.awt.Color(255, 255, 0));
        jButtonUACancel.setText("Cancel");
        jButtonUACancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUACancelActionPerformed(evt);
            }
        });

        jLabel26.setText("Mandant:");

        jComboBoxAufgabenSortierungMandant.setModel(defaultComboBoxModelMandanten);
        jComboBoxAufgabenSortierungMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAufgabenSortierungMandantActionPerformed(evt);
            }
        });

        jButtonSaveReihenfolge.setText("Save");
        jButtonSaveReihenfolge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveReihenfolgeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(jComboBoxAufgabenSortierungMandant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addComponent(jButtonSaveReihenfolge))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonUACancel)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonAufgabeUp, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonAufgabeDown))))
                .addContainerGap(567, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jComboBoxAufgabenSortierungMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel35)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(jButtonAufgabeUp)
                        .addGap(38, 38, 38)
                        .addComponent(jButtonAufgabeDown))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonUACancel)
                    .addComponent(jButtonSaveReihenfolge))
                .addContainerGap(127, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Unbedingte Aufgaben", jPanel9);

        javax.swing.GroupLayout jPanelAufgabenLayout = new javax.swing.GroupLayout(jPanelAufgaben);
        jPanelAufgaben.setLayout(jPanelAufgabenLayout);
        jPanelAufgabenLayout.setHorizontalGroup(
            jPanelAufgabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3)
        );
        jPanelAufgabenLayout.setVerticalGroup(
            jPanelAufgabenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jTabbedPane2.addTab("Aufgaben", jPanelAufgaben);

        jLabel29.setText("Ausloesemuster:");

        jTreeMuster.setModel(new AusloesemusterTreeModel((Mandant)this.jComboBoxMusterMandant.getSelectedItem()));
        jTreeMuster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeMusterMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(jTreeMuster);

        jButtonMusterNew.setText("+");
        jButtonMusterNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMusterNewActionPerformed(evt);
            }
        });

        jButtonMusterDelete.setText("-");
        jButtonMusterDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMusterDeleteActionPerformed(evt);
            }
        });

        jLabel30.setText("Ereignisse");

        jLabel31.setText("Aufgaben:");

        jListMusterEreignisse.setModel(defaultListModelMusterAuswahlEreignisse);
        jListMusterEreignisse.setEnabled(false);
        jScrollPane11.setViewportView(jListMusterEreignisse);

        jListMusterAufgaben.setModel(defaultListModelMusterAuswahlAufgaben);
        jListMusterAufgaben.setEnabled(false);
        jScrollPane12.setViewportView(jListMusterAufgaben);

        jButtonEditMuster.setText("Edit");
        jButtonEditMuster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditMusterActionPerformed(evt);
            }
        });

        jButtonSaveMuster.setText("Save");
        jButtonSaveMuster.setEnabled(false);
        jButtonSaveMuster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveMusterActionPerformed(evt);
            }
        });

        jButtonCancelMuster.setText("Cancel");
        jButtonCancelMuster.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelMusterActionPerformed(evt);
            }
        });

        jLabel36.setText("Mandant:");

        jComboBoxMusterMandant.setModel(defaultComboBoxModelMandanten);
        jComboBoxMusterMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxMusterMandantActionPerformed(evt);
            }
        });

        jTextFieldMusterBezeichnung.setEnabled(false);

        javax.swing.GroupLayout jPanelAusloesemusterLayout = new javax.swing.GroupLayout(jPanelAusloesemuster);
        jPanelAusloesemuster.setLayout(jPanelAusloesemusterLayout);
        jPanelAusloesemusterLayout.setHorizontalGroup(
            jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAusloesemusterLayout.createSequentialGroup()
                        .addGap(234, 234, 234)
                        .addComponent(jButtonMusterDelete))
                    .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                                .addComponent(jLabel29)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldMusterBezeichnung))
                            .addComponent(jButtonMusterNew)
                            .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxMusterMandant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel31))
                    .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonEditMuster)
                            .addComponent(jLabel30)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                                .addComponent(jButtonSaveMuster)
                                .addGap(136, 136, 136)
                                .addComponent(jButtonCancelMuster))
                            .addComponent(jScrollPane12))))
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanelAusloesemusterLayout.setVerticalGroup(
            jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAusloesemusterLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jComboBoxMusterMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jTextFieldMusterBezeichnung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12)
                    .addComponent(jScrollPane11)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addGap(22, 22, 22)
                .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonMusterNew)
                    .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonEditMuster)
                        .addComponent(jButtonMusterDelete))
                    .addGroup(jPanelAusloesemusterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonCancelMuster)
                        .addComponent(jButtonSaveMuster)))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Ausloesemuster", jPanelAusloesemuster);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel12.setText("Buchungstage:");

        jButtonAddBuchungstag.setText("Add");
        jButtonAddBuchungstag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddBuchungstagActionPerformed(evt);
            }
        });

        jButtonRemoveBuchungstag.setText("Remove");
        jButtonRemoveBuchungstag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveBuchungstagActionPerformed(evt);
            }
        });

        jXMonthViewBuchungstage.setBackground(new java.awt.Color(255, 255, 255));
        jXMonthViewBuchungstage.setFlaggedDayForeground(new java.awt.Color(255, 0, 0));
        jXMonthViewBuchungstage.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jXMonthViewBuchungstage.setSelectionMode(org.jdesktop.swingx.calendar.DateSelectionModel.SelectionMode.MULTIPLE_INTERVAL_SELECTION);
        jXMonthViewBuchungstage.setShowingWeekNumber(true);
        jXMonthViewBuchungstage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jXMonthViewBuchungstageActionPerformed(evt);
            }
        });

        jButtonLastMonth.setText("<");
        jButtonLastMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLastMonthActionPerformed(evt);
            }
        });

        jButtonNextMonth.setText(">");
        jButtonNextMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextMonthActionPerformed(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("( in roter Schrift )");

        jLabel43.setText("Mandant:");

        jComboBoxBuchungstageMandanten.setModel(defaultComboBoxModelMandanten);
        jComboBoxBuchungstageMandanten.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBuchungstageMandantenActionPerformed(evt);
            }
        });

        jLabel45.setText("Datum");

        jButton1.setText("Set");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jCheckBoxMonatsultimo.setText("Monatsultimo");
        jCheckBoxMonatsultimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMonatsultimoActionPerformed(evt);
            }
        });

        jCheckBoxQuartalsultimo.setText("Quartalsultimo");
        jCheckBoxQuartalsultimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxQuartalsultimoActionPerformed(evt);
            }
        });

        jCheckBoxJahresultimo.setText("Jahresultimo");
        jCheckBoxJahresultimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxJahresultimoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBuchungstageLayout = new javax.swing.GroupLayout(jPanelBuchungstage);
        jPanelBuchungstage.setLayout(jPanelBuchungstageLayout);
        jPanelBuchungstageLayout.setHorizontalGroup(
            jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                                        .addComponent(jButtonAddBuchungstag)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButtonRemoveBuchungstag))
                                    .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                                        .addComponent(jButtonLastMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel13)
                                            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                                                .addComponent(jXMonthViewBuchungstage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonNextMonth))))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBuchungstageLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(231, 231, 231)))
                        .addGap(52, 52, 52)
                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1))
                            .addComponent(jCheckBoxMonatsultimo)
                            .addComponent(jCheckBoxQuartalsultimo)
                            .addComponent(jCheckBoxJahresultimo))))
                .addGap(0, 352, Short.MAX_VALUE))
            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(jComboBoxBuchungstageMandanten, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanelBuchungstageLayout.setVerticalGroup(
            jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jComboBoxBuchungstageMandanten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jXMonthViewBuchungstage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonLastMonth)
                            .addComponent(jButtonNextMonth))
                        .addGap(39, 39, 39)
                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddBuchungstag)
                            .addComponent(jButtonRemoveBuchungstag)))
                    .addGroup(jPanelBuchungstageLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanelBuchungstageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jButton1)
                            .addComponent(jFormattedTextFieldUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addComponent(jCheckBoxMonatsultimo)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxQuartalsultimo)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxJahresultimo)))
                .addGap(0, 355, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Buchungstage", jPanelBuchungstage);

        jLabel21.setText("Mandant:");

        jComboBoxUebersichtMandant.setModel(defaultComboBoxModelMandanten);

        jLabel22.setText("Vorgaenge:");

        jListVorgangsUebersicht.setModel(defaultListModelVorgaenge);
        jListVorgangsUebersicht.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListVorgangsUebersichtMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jListVorgangsUebersicht);

        jLabel23.setText("Aufgaben:");

        jTableVgAufgaben.setAutoCreateRowSorter(true);
        jTableVgAufgaben.setModel(defaultTableModelVgAufgaben);
        jTableVgAufgaben.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane8.setViewportView(jTableVgAufgaben);

        jLabel24.setText("Ereignisse:");

        jTableVgEreignisse.setAutoCreateRowSorter(true);
        jTableVgEreignisse.setModel(defaultTableModelVgEreignisse);
        jScrollPane9.setViewportView(jTableVgEreignisse);

        jButtonVorgangLoad.setText("Load");
        jButtonVorgangLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVorgangLoadActionPerformed(evt);
            }
        });

        jButtonRepeatAufgabe.setText("Restart");
        jButtonRepeatAufgabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRepeatAufgabeActionPerformed(evt);
            }
        });

        jButtonCloseVorgang.setText("Close Vorgang");
        jButtonCloseVorgang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseVorgangActionPerformed(evt);
            }
        });

        jCheckBoxRepeadLoad.setText("Autom. Refresh");
        jCheckBoxRepeadLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRepeadLoadActionPerformed(evt);
            }
        });

        jLabel7.setText("Buchungsdatum:");

        jLabel42.setText("Lebenszeichen:");

        jComboBoxVgLebenszeichen.setModel(defaultComboBoxModelVgLebenszeichen);

        javax.swing.GroupLayout jPanelVorgaengeLayout = new javax.swing.GroupLayout(jPanelVorgaenge);
        jPanelVorgaenge.setLayout(jPanelVorgaengeLayout);
        jPanelVorgaengeLayout.setHorizontalGroup(
            jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jXDatePickerBuchungsdatum, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21))
                            .addComponent(jLabel22))
                        .addGap(10, 10, 10))
                    .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                        .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                                .addGap(139, 139, 139)
                                .addComponent(jButtonCloseVorgang, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel42))
                            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jComboBoxVgLebenszeichen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                        .addComponent(jComboBoxUebersichtMandant, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jButtonVorgangLoad)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBoxRepeadLoad))
                    .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRepeatAufgabe, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addComponent(jScrollPane8))
                .addGap(44, 44, 44))
        );
        jPanelVorgaengeLayout.setVerticalGroup(
            jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVorgaengeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jComboBoxUebersichtMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVorgangLoad)
                    .addComponent(jCheckBoxRepeadLoad)
                    .addComponent(jXDatePickerBuchungsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(24, 24, 24)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonRepeatAufgabe)
                    .addComponent(jButtonCloseVorgang))
                .addGap(1, 1, 1)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelVorgaengeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxVgLebenszeichen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(230, 230, 230))
        );

        jTabbedPane2.addTab("Vorgangsuebersicht", jPanelVorgaenge);

        jLabel39.setText("Aenderungszeitraum von:");

        jLabel40.setText("bis:");

        jLabel41.setText("Aenderungen:");

        jButtonLoadAenderungen.setText("Load");
        jButtonLoadAenderungen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoadAenderungenActionPerformed(evt);
            }
        });

        jTableProtokoll.setAutoCreateRowSorter(true);
        jTableProtokoll.setModel(defaultTableModelProtokoll);
        jScrollPane5.setViewportView(jTableProtokoll);

        jLabel44.setText("Mandatnt:");

        jComboBoxProtokollMandant.setModel(defaultComboBoxModelMandanten);

        javax.swing.GroupLayout jPanelProtokollLayout = new javax.swing.GroupLayout(jPanelProtokoll);
        jPanelProtokoll.setLayout(jPanelProtokollLayout);
        jPanelProtokollLayout.setHorizontalGroup(
            jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProtokollLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 999, Short.MAX_VALUE)
                    .addGroup(jPanelProtokollLayout.createSequentialGroup()
                        .addGroup(jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelProtokollLayout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxProtokollMandant, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanelProtokollLayout.createSequentialGroup()
                                .addGroup(jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel41))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jXDatePickerProtokollVon, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jXDatePickerProtokollBis, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jButtonLoadAenderungen)
                        .addGap(0, 497, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelProtokollLayout.setVerticalGroup(
            jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelProtokollLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jComboBoxProtokollMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanelProtokollLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jXDatePickerProtokollVon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(jButtonLoadAenderungen)
                    .addComponent(jXDatePickerProtokollBis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel41)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Protokoll", jPanelProtokoll);

        jMenuReload.setText("Reload");
        jMenuReload.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenuReloadMenuSelected(evt);
            }
        });
        jMenuBar1.add(jMenuReload);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        DatenbankZugriff.closeConnection();
    }//GEN-LAST:event_formWindowClosed

    private void jMenuReloadMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenuReloadMenuSelected
        this.loadData();
    }//GEN-LAST:event_jMenuReloadMenuSelected

    private void jCheckBoxRepeadLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxRepeadLoadActionPerformed
        if(jCheckBoxRepeadLoad.isSelected()){
            if(ivTimer == null){
                ivTimer = new javax.swing.Timer( 5000, new ActionListener() {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        loadVgObjekte();
                    }
                });
                ivTimer.start();
            }
        } else {
            if(ivTimer != null){
                ivTimer.stop();
                ivTimer = null;
            }
        }
    }//GEN-LAST:event_jCheckBoxRepeadLoadActionPerformed

    private void jButtonCloseVorgangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseVorgangActionPerformed
        Vorgang lvVorgang = (Vorgang) this.jListVorgangsUebersicht.getSelectedValue();
        if(lvVorgang != null){
            int i = okcancel("Are your sure ?");
            if( i == 0){
                lvVorgang.close();
            }
        }
    }//GEN-LAST:event_jButtonCloseVorgangActionPerformed

    private void jButtonRepeatAufgabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRepeatAufgabeActionPerformed
        int lvSelectedRow = this.jTableVgAufgaben.getSelectionModel().getLeadSelectionIndex();
        if(lvSelectedRow >= 0){
            lvSelectedRow = this.jTableVgAufgaben.convertRowIndexToModel(lvSelectedRow);
            VgAufgabe lvAufgabe = ((VgAufgabeTableModel) this.jTableVgAufgaben.getModel()).getVgAufgabe(lvSelectedRow);
            if(lvAufgabe != null){
                int i = okcancel("Are you sure ?");
                if( i == 0){
                    lvAufgabe.enableRestart();
                }
            }
        }
    }//GEN-LAST:event_jButtonRepeatAufgabeActionPerformed

    private void jButtonVorgangLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVorgangLoadActionPerformed
        this.loadVgObjekte();
    }//GEN-LAST:event_jButtonVorgangLoadActionPerformed

    private void jListVorgangsUebersichtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListVorgangsUebersichtMouseClicked
        Vorgang lvVorgang = (Vorgang) this.jListVorgangsUebersicht.getSelectedValue();
        this.loadVgObjekte(lvVorgang);
        this.fillVgAufgabenTable();
        this.fillVgEreignisTable();
    }//GEN-LAST:event_jListVorgangsUebersichtMouseClicked

    private void jButtonNextMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextMonthActionPerformed
        GregorianCalendar lvAktCal = (GregorianCalendar) this.jXMonthViewBuchungstage.getCalendar();
        lvAktCal.set(Calendar.DAY_OF_MONTH, 1);
        lvAktCal.add(Calendar.MONTH, 1);
        this.jXMonthViewBuchungstage.setFirstDisplayedDay(lvAktCal.getTime());
        this.initBuchungstagListe();
    }//GEN-LAST:event_jButtonNextMonthActionPerformed

    private void jButtonLastMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLastMonthActionPerformed

        GregorianCalendar lvAktCal = (GregorianCalendar) this.jXMonthViewBuchungstage.getCalendar();
        lvAktCal.set(Calendar.DAY_OF_MONTH, 1);
        lvAktCal.add(Calendar.MONTH, -1);
        this.jXMonthViewBuchungstage.setFirstDisplayedDay(lvAktCal.getTime());
        this.initBuchungstagListe();
    }//GEN-LAST:event_jButtonLastMonthActionPerformed

    private void jButtonRemoveBuchungstagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveBuchungstagActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            Mandant lvMandant = (Mandant) this.jComboBoxBuchungstageMandanten.getSelectedItem();
            BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(lvMandant);
            DateFormat dfmt = new SimpleDateFormat("yyMMdd" );

            SortedSet<Date> lvSelections = this.jXMonthViewBuchungstage.getSelection();
            for (Iterator<Date> it = lvSelections.iterator(); it.hasNext();) {
                Date lvDate = (Date)it.next();
                if(this.jXMonthViewBuchungstage.isFlaggedDate(lvDate)){ // Buchungstage sind als FlaggedDate markiert
                    lvBuTagVerw.deleteBuchungstag(dfmt.format(lvDate), lvMandant);
                }
            }
            this.initBuchungstagListe();
        }
    }//GEN-LAST:event_jButtonRemoveBuchungstagActionPerformed

    private void jButtonAddBuchungstagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddBuchungstagActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            GregorianCalendar lvAktCal = (GregorianCalendar) this.jXMonthViewBuchungstage.getCalendar();
            DateFormat dfmt = new SimpleDateFormat("yyMM" );
            String lvSearch = dfmt.format(lvAktCal.getTime());
            Mandant lvMandant = (Mandant) this.jComboBoxBuchungstageMandanten.getSelectedItem();
            BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(lvMandant);
            ArrayList<String> lvBuchungstage = lvBuTagVerw.getBuchungstage(lvSearch);

            DateFormat dfmt2 = new SimpleDateFormat("yyMMdd" );

            SortedSet<Date> lvSelections = this.jXMonthViewBuchungstage.getSelection();
            for (Iterator<Date> it = lvSelections.iterator(); it.hasNext();) {
                Date lvDate = (Date)it.next();
                if(!this.jXMonthViewBuchungstage.isFlaggedDate(lvDate))
                {// Buchungstage sind als FlaggedDate markiert
                    if (!lvBuTagVerw.isBuchungstag(dfmt2.format(lvDate), lvMandant))
                    {
                      lvBuTagVerw.insertBuchungstag(dfmt2.format(lvDate), lvMandant);
                    }
                    else
                    {
                        System.out.println("Buchungstag " + lvDate.toString() + "bereits eingetragen...");
                    }
                }
            }
            this.initBuchungstagListe();
        }
    }//GEN-LAST:event_jButtonAddBuchungstagActionPerformed

    private void jComboBoxMusterMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxMusterMandantActionPerformed
        if(this.jComboBoxMusterMandant.getSelectedItem() != null){
            this.refreshMusterAnzeige();
        }
    }//GEN-LAST:event_jComboBoxMusterMandantActionPerformed

    private void jButtonCancelMusterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelMusterActionPerformed
        this.jTreeMusterMouseClicked(null);
        this.jListMusterEreignisse.setEnabled(false);
        this.jListMusterAufgaben.setEnabled(false);
        this.jButtonSaveMuster.setEnabled(false);
        this.jTreeMuster.setEnabled(true);
        this.jButtonMusterNew.setEnabled(true);
        this.jButtonMusterDelete.setEnabled(true);
        this.jTextFieldMusterBezeichnung.setEnabled(false);
    }//GEN-LAST:event_jButtonCancelMusterActionPerformed

    private void jButtonSaveMusterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveMusterActionPerformed
        int ok = okcancel("Are you sure ?");
        if( ok == 0){
            // TODO leere Muster duerfen nicht gesichert werden.
            if (this.ivSelectedAusloesemuster != null) {
                HashSet<Ereignis> lvEreignisse = new HashSet<Ereignis>();
                HashSet<Aufgabe> lvAufgaben = new HashSet<Aufgabe>();
                Object lvSelectedEreignisse[] = jListMusterEreignisse.getSelectedValues();
                Object lvSelectedAufgaben[] = jListMusterAufgaben.getSelectedValues();

                for (int i = 0; i < lvSelectedEreignisse.length; i++) {
                    lvEreignisse.add((Ereignis) lvSelectedEreignisse[i]);
                }
                for (int i = 0; i < lvSelectedAufgaben.length; i++) {
                    lvAufgaben.add((Aufgabe) lvSelectedAufgaben[i]);
                }

                this.ivSelectedAusloesemuster.setEreignisse(lvEreignisse);
                this.ivSelectedAusloesemuster.setMassnahmen(lvAufgaben);
                this.ivSelectedAusloesemuster.setBezeichnung(this.jTextFieldMusterBezeichnung.getText());

                this.ivSelectedAusloesemuster.save();
                this.jListMusterEreignisse.setEnabled(false);
                this.jListMusterAufgaben.setEnabled(false);
                this.jButtonSaveMuster.setEnabled(false);
                this.jTextFieldMusterBezeichnung.setEnabled(false);

                this.jTreeMuster.setEnabled(true);
                this.jButtonMusterNew.setEnabled(true);
                this.jButtonMusterDelete.setEnabled(true);

                AusloesemusterTreeModel lvModel = (AusloesemusterTreeModel) this.jTreeMuster.getModel();

                DefaultMutableTreeNode lvSelection = (DefaultMutableTreeNode) this.jTreeMuster.getSelectionPath().getLastPathComponent();
                if (lvSelection.getUserObject() instanceof Ausloesemuster) {
                    lvModel.refreshAusloesemusterTreeNodeInhalte(ivSelectedAusloesemuster, lvSelection);
                }
                this.jTreeMuster.updateUI();

            }
        }
    }//GEN-LAST:event_jButtonSaveMusterActionPerformed

    private void jButtonEditMusterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditMusterActionPerformed
        if(this.ivSelectedAusloesemuster != null){
            this.jListMusterEreignisse.setEnabled(true);
            this.jListMusterAufgaben.setEnabled(true);
            this.jButtonSaveMuster.setEnabled(true);
            this.jTextFieldMusterBezeichnung.setEnabled(true);
        }
    }//GEN-LAST:event_jButtonEditMusterActionPerformed

    private void jButtonMusterDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMusterDeleteActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            DefaultMutableTreeNode lvSelection = (DefaultMutableTreeNode) this.jTreeMuster.getSelectionPath().getLastPathComponent();
            if (lvSelection.getUserObject() instanceof Ausloesemuster) {
                Ausloesemuster lvMuster = (Ausloesemuster) lvSelection.getUserObject();
                lvMuster.delete();
            }
            this.refreshMusterAnzeige();
        }
    }//GEN-LAST:event_jButtonMusterDeleteActionPerformed

    private void jButtonMusterNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMusterNewActionPerformed
        // Dummy-Muster anlegen
        this.ivSelectedAusloesemuster = new Ausloesemuster(new HashSet<Ereignis>(), new HashSet<Aufgabe>());
        this.ivSelectedAusloesemuster.setMandant((Mandant) this.jComboBoxMusterMandant.getSelectedItem());
        this.ivSelectedAusloesemuster.save(); // wg. Id-Vergabe
        this.refreshMusterAnzeige();
        // leeres neues Muster finden

        AusloesemusterTreeModel lvModel = (AusloesemusterTreeModel) this.jTreeMuster.getModel();

        TreeNode[] nodes = lvModel.getPathToRoot(lvModel.getEmptyMuster());
        TreePath path = new TreePath(nodes);
        this.jTreeMuster.setExpandsSelectedPaths(true);
        this.jTreeMuster.setSelectionPath(new TreePath(nodes));
        this.jTreeMusterMouseClicked(null);

        // Button u. Liste Freigeben
        this.jListMusterEreignisse.clearSelection();
        this.jListMusterEreignisse.setEnabled(true);
        this.jListMusterAufgaben.clearSelection();
        this.jListMusterAufgaben.setEnabled(true);
        this.jButtonSaveMuster.setEnabled(true);
        this.jButtonMusterNew.setEnabled(false);
        this.jButtonMusterDelete.setEnabled(false);
        this.jTextFieldMusterBezeichnung.setEnabled(true);

        this.jTreeMuster.setEnabled(false);

        this.jTreeMuster.updateUI();
    }//GEN-LAST:event_jButtonMusterNewActionPerformed

    private void jTreeMusterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeMusterMouseClicked
        DefaultMutableTreeNode lvSelection = (DefaultMutableTreeNode) this.jTreeMuster.getSelectionPath().getLastPathComponent();
        if (lvSelection.getUserObject() instanceof Ausloesemuster) {
            Ausloesemuster lvMuster = (Ausloesemuster) lvSelection.getUserObject();
            this.setMusterAufgaben(lvMuster);
            this.setMusterEreignisse(lvMuster);
            this.jTextFieldMusterBezeichnung.setText(lvMuster.getBezeichnung());
            this.ivSelectedAusloesemuster = lvMuster;
        } else {
            this.ivSelectedAusloesemuster = null;
        }
    }//GEN-LAST:event_jTreeMusterMouseClicked

    private void jButtonSaveReihenfolgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveReihenfolgeActionPerformed
        // Liste abarbeiten und jedem Element eine neue Sortiernummer vergeben.
        // Es wird dann keine zwei Elemente geben, die dieselbe Sortiernummer haben.
        int lvAnzahlElemente = defaultListModelAufgabenSortierung.getSize();
        for (int i = 0; i < lvAnzahlElemente; i++) {
            Aufgabe lvAufgabe = (Aufgabe) defaultListModelAufgabenSortierung.get(i);
            lvAufgabe.setSortNr(new Integer(i));
            lvAufgabe.save();
        }
        this.fillUnbedingteAufgabenList();
    }//GEN-LAST:event_jButtonSaveReihenfolgeActionPerformed

    private void jComboBoxAufgabenSortierungMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAufgabenSortierungMandantActionPerformed
        if(this.jComboBoxAufgabenSortierungMandant.getSelectedItem() != null){
            this.fillUnbedingteAufgabenList();
        }
    }//GEN-LAST:event_jComboBoxAufgabenSortierungMandantActionPerformed

    private void jButtonUACancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUACancelActionPerformed
        this.fillUnbedingteAufgabenList();
    }//GEN-LAST:event_jButtonUACancelActionPerformed

    private void jButtonAufgabeDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAufgabeDownActionPerformed
        // den selektierten einen noch unten verschieben.
        int lvIndex = this.jListUnbedingteAufgaben.getSelectedIndex();
        Aufgabe lvAufgabe = (Aufgabe) this.jListUnbedingteAufgaben.getSelectedValue();
        if (lvIndex > 0) {
            this.defaultListModelAufgabenSortierung.remove(lvIndex);
            this.defaultListModelAufgabenSortierung.insertElementAt(lvAufgabe, lvIndex + 1);
            this.jListUnbedingteAufgaben.setSelectedIndex(lvIndex + 1);
        }
    }//GEN-LAST:event_jButtonAufgabeDownActionPerformed

    private void jButtonAufgabeUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAufgabeUpActionPerformed
        // den selektierten einen noch oben verschieben.
        int lvIndex = this.jListUnbedingteAufgaben.getSelectedIndex();
        Aufgabe lvAufgabe = (Aufgabe) this.jListUnbedingteAufgaben.getSelectedValue();
        if (lvIndex > 0) {
            this.defaultListModelAufgabenSortierung.remove(lvIndex);
            this.defaultListModelAufgabenSortierung.insertElementAt(lvAufgabe, lvIndex - 1);
            this.jListUnbedingteAufgaben.setSelectedIndex(lvIndex - 1);
        }
    }//GEN-LAST:event_jButtonAufgabeUpActionPerformed

    private void jButtonCancelPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelPSActionPerformed
        this.ivSelectedProzessStarter = (ProzessStarter) this.jListProzessStarter.getSelectedValue();
        this.setProzessStarterFelder();
        this.enableProzessStarterPflegeItems(false);
    }//GEN-LAST:event_jButtonCancelPSActionPerformed

    private void jButtonSavePSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSavePSActionPerformed
        this.ivSelectedProzessStarter.setBezeichnung(this.jTextFieldPSBezeichnung.getText());
        this.ivSelectedProzessStarter.setProzessname(this.jTextFieldPSName.getText());
        this.ivSelectedProzessStarter.setPfad(this.jTextFieldPSPfad.getText());
        this.ivSelectedProzessStarter.setZeitfensterVon(this.jFormattedTextFieldVonZeitpunkt.getText());
        this.ivSelectedProzessStarter.setZeitfensterBis(this.jFormattedTextFieldBisZeitpunkt.getText());
        this.ivSelectedProzessStarter.setMandant((Mandant) this.jComboBoxPSMandant.getSelectedItem());
        this.ivSelectedProzessStarter.setUnbedingteAufgabe(this.jCheckBoxPsUndbedingteAufgabe.isSelected());
        this.ivSelectedProzessStarter.setFertigstellungsEreignis((Ereignis) this.jComboBoxPSFertigEreignis.getSelectedItem());
        this.ivSelectedProzessStarter.save();
        this.refreshProzessStarterList();
        this.jListProzessStarter.setSelectedValue(this.ivSelectedProzessStarter, true);
        this.enableProzessStarterPflegeItems(false);

        // andere Aufgabenlisten neu fuellen
        this.refreshAufgabenListen();
    }//GEN-LAST:event_jButtonSavePSActionPerformed

    private void jButtonEditPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditPSActionPerformed
        if(this.ivSelectedProzessStarter != null){
            this.enableProzessStarterPflegeItems(true);
        }
    }//GEN-LAST:event_jButtonEditPSActionPerformed

    private void jButtonDeletePSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeletePSActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            if(this.jListProzessStarter.getSelectedValue() != null){
                ((ProzessStarter) this.jListProzessStarter.getSelectedValue()).delete();
                this.refreshProzessStarterList();
                this.cleanProzessStarterFelder();
                // andere Aufgabenlisten neu fuellen
                this.refreshAufgabenListen();
            }
        }
    }//GEN-LAST:event_jButtonDeletePSActionPerformed

    private void jButtonNewPSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewPSActionPerformed
        this.ivSelectedProzessStarter = new ProzessStarter("", "", "", "", null, null, false, null, null, null, (Mandant) this.jComboBoxDateisucheMandant.getSelectedItem(), new Integer("9999"), false);
        this.setProzessStarterFelder();
        this.enableProzessStarterPflegeItems(true);
    }//GEN-LAST:event_jButtonNewPSActionPerformed

    private void jListProzessStarterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListProzessStarterMouseClicked
        if (this.jListProzessStarter.getSelectedValue() != null) {
            this.ivSelectedProzessStarter = (ProzessStarter) this.jListProzessStarter.getSelectedValue();
            this.setProzessStarterFelder();
            this.enableProzessStarterPflegeItems(false);
        }
    }//GEN-LAST:event_jListProzessStarterMouseClicked

    private void jComboBoxPSMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPSMandantActionPerformed
        if(this.jComboBoxPSMandant.getSelectedItem() != null){
            this.cleanProzessStarterFelder();
            this.fillProzessStarterFertigEreignisList();
            this.refreshProzessStarterList();
        }
    }//GEN-LAST:event_jComboBoxPSMandantActionPerformed

    private void jComboBoxDateisucheQualifierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDateisucheQualifierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDateisucheQualifierActionPerformed

    private void jButtonCancelDateisucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelDateisucheActionPerformed
        this.ivSelectedDateisuche = (Dateisuche) this.jListDateisuche.getSelectedValue();
        this.setDateisucheFelder();
        this.enableDateisuchePflegeItems(false);
    }//GEN-LAST:event_jButtonCancelDateisucheActionPerformed

    private void jButtonSaveDateisucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveDateisucheActionPerformed
        this.ivSelectedDateisuche.setBezeichnung(this.jTextFieldBezeichnungDateisuche.getText());
        this.ivSelectedDateisuche.setDateiname(this.jTextFieldNameDateisuche.getText());
        this.ivSelectedDateisuche.setDateiPfad(this.jTextFieldPfadDateisuche.getText());
        this.ivSelectedDateisuche.setMandant((Mandant) this.jComboBoxDateisucheMandant.getSelectedItem());
        this.ivSelectedDateisuche.setUnbedingteAufgabe(this.jCheckBoxDateisucheUnbedingteAufgabe.isSelected());
        this.ivSelectedDateisuche.setFertigstellungsEreignis((Ereignis) this.jComboBoxDateisucheFertigEreignis.getSelectedItem());
        this.ivSelectedDateisuche.setQualifier((String)this.jComboBoxDateisucheQualifier.getSelectedItem());
        this.ivSelectedDateisuche.save();
        this.refreshDateisucheList();
        this.jListDateisuche.setSelectedValue(this.ivSelectedDateisuche, true);
        this.enableDateisuchePflegeItems(false);
        // andere Aufgabenlisten neu fuellen
        this.refreshAufgabenListen();
    }//GEN-LAST:event_jButtonSaveDateisucheActionPerformed

    private void jButtonEditDateisucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditDateisucheActionPerformed
        if(this.ivSelectedDateisuche != null){
            this.enableDateisuchePflegeItems(true);
        }
    }//GEN-LAST:event_jButtonEditDateisucheActionPerformed

    private void jButtonDeleteDateisucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteDateisucheActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            if(this.jListDateisuche.getSelectedValue() != null){
                ((Dateisuche) this.jListDateisuche.getSelectedValue()).delete();
                this.refreshDateisucheList();
                this.cleanDateisucheFelder();
                // andere Aufgabenlisten neu fuellen
                this.refreshAufgabenListen();
            }
        }
    }//GEN-LAST:event_jButtonDeleteDateisucheActionPerformed

    private void jButtonNewDateisucheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewDateisucheActionPerformed
        this.ivSelectedDateisuche = new Dateisuche("", "", "", "", null, null, false, "", null, null, null, (Mandant) this.jComboBoxDateisucheMandant.getSelectedItem(), new Integer("9999"), false);
        this.setDateisucheFelder();
        this.enableDateisuchePflegeItems(true);
    }//GEN-LAST:event_jButtonNewDateisucheActionPerformed

    private void jListDateisucheMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListDateisucheMouseClicked
        this.ivSelectedDateisuche = (Dateisuche) this.jListDateisuche.getSelectedValue();
        this.setDateisucheFelder();
        this.enableDateisuchePflegeItems(false);
    }//GEN-LAST:event_jListDateisucheMouseClicked

    private void jComboBoxDateisucheMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDateisucheMandantActionPerformed
        if(jComboBoxDateisucheMandant.getSelectedItem() != null){
            this.cleanDateisucheFelder();
            this.fillDateisucheFertigEreignisList();
            this.refreshDateisucheList();
        }
    }//GEN-LAST:event_jComboBoxDateisucheMandantActionPerformed

    private void jButtonCancelEreignisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelEreignisActionPerformed
        if (this.jListEreignisseEreignisse.getSelectedValue() != null) {
            this.ivSelectedEreignis = (Ereignis) this.jListEreignisseEreignisse.getSelectedValue();
            this.setEreignisFelder();
            this.refreshEreignisList();
            this.enableEreignisPflegeItems(false);
        }
    }//GEN-LAST:event_jButtonCancelEreignisActionPerformed

    private void jButtonSaveEreignisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveEreignisActionPerformed
        this.ivSelectedEreignis.setName(this.jTextFieldEreignisBezeichnung.getText());
        this.ivSelectedEreignis.setMandant((Mandant) this.defaultComboBoxModelMandanten.getSelectedItem());
        this.ivSelectedEreignis.setEndeEreignis(this.jCheckBoxEndeEreignis.isSelected());
        this.ivSelectedEreignis.setStopEreignis(this.jCheckBoxStopEreignis.isSelected());
        this.ivSelectedEreignis.save();
        this.refreshEreignisList();
        this.jListEreignisseEreignisse.setSelectedValue(this.ivSelectedEreignis, true);
        // TODO selection des geaenderten Ereignisses in der Liste
        // andere Ereignislisten neu fuellen
        this.refreshEreignisListen();
    }//GEN-LAST:event_jButtonSaveEreignisActionPerformed

    private void jButtonEditEreignisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditEreignisActionPerformed
        if(this.ivSelectedEreignis != null){
            this.enableEreignisPflegeItems(true);
        }
    }//GEN-LAST:event_jButtonEditEreignisActionPerformed

    private void jButtonEreignisseDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEreignisseDeleteActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            if(this.jListEreignisseEreignisse.getSelectedValue() != null){
                ((Ereignis) this.jListEreignisseEreignisse.getSelectedValue()).delete();
                this.refreshEreignisList();
                this.cleanEreignisFelder();
                // andere Ereignislisten neu fuellen
                this.refreshEreignisListen();
            }
        }
    }//GEN-LAST:event_jButtonEreignisseDeleteActionPerformed

    private void jButtonEreignisseNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEreignisseNewActionPerformed
        this.ivSelectedEreignis = new Ereignis("", "", (Mandant) this.defaultComboBoxModelMandanten.getSelectedItem(), false, false);
        this.setEreignisFelder();
        this.enableEreignisPflegeItems(true);
    }//GEN-LAST:event_jButtonEreignisseNewActionPerformed

    private void jComboBoxEreignisMandantenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEreignisMandantenActionPerformed
        if(this.jComboBoxEreignisMandanten.getSelectedItem() != null){
            this.cleanEreignisFelder();
            this.refreshEreignisList();
        }
    }//GEN-LAST:event_jComboBoxEreignisMandantenActionPerformed

    private void jListEreignisseEreignisseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListEreignisseEreignisseMouseClicked
        if (this.jListEreignisseEreignisse.getSelectedValue() != null) {
            this.ivSelectedEreignis = (Ereignis) this.jListEreignisseEreignisse.getSelectedValue();
            this.setEreignisFelder();
            this.enableEreignisPflegeItems(false);
        }
    }//GEN-LAST:event_jListEreignisseEreignisseMouseClicked

    private void jButtonNewTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewTriggerActionPerformed
        this.ivSelectedTrigger = new Trigger("", "", "", false, null, null, null, (Mandant) this.jComboBoxTriggerMandant.getSelectedItem(), new Integer("9999"), false, null);
        this.setTriggerFelder();
        this.enableTriggerPflegeItems(true);
    }//GEN-LAST:event_jButtonNewTriggerActionPerformed

    private void jButtonDeleteTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteTriggerActionPerformed
        int i = okcancel("Are you sure ?");
        if( i == 0){
            if(this.jListTrigger.getSelectedValue() != null){
                ((Trigger) this.jListTrigger.getSelectedValue()).delete();
                this.refreshTriggerList();
                this.cleanTriggerFelder();
                // andere Aufgabenlisten neu fuellen
                this.refreshAufgabenListen();
            }
        }
    }//GEN-LAST:event_jButtonDeleteTriggerActionPerformed

    private void jCheckBoxTriggerUnbedingteAufgabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxTriggerUnbedingteAufgabeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBoxTriggerUnbedingteAufgabeActionPerformed

    private void jButtonCancelTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelTriggerActionPerformed
        this.ivSelectedTrigger = (Trigger) this.jListTrigger.getSelectedValue();
        this.setTriggerFelder();
        this.enableTriggerPflegeItems(false);
    }//GEN-LAST:event_jButtonCancelTriggerActionPerformed

    private void jButtonSaveTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveTriggerActionPerformed
        this.ivSelectedTrigger.setBezeichnung(this.jTextFieldBezeichnungTrigger.getText());
        this.ivSelectedTrigger.setAusloesezeitpunkt(this.jFormattedTextFieldTriggerAusloesezeitpunkt.getText());
        this.ivSelectedTrigger.setMandant((Mandant) this.jComboBoxTriggerMandant.getSelectedItem());
        this.ivSelectedTrigger.setUnbedingteAufgabe(this.jCheckBoxTriggerUnbedingteAufgabe.isSelected());
        this.ivSelectedTrigger.setFertigstellungsEreignis((Ereignis) this.jComboBoxTriggerFertigEreignis.getSelectedItem());
        this.ivSelectedTrigger.save();
        this.refreshTriggerList();
        this.jListTrigger.setSelectedValue(this.ivSelectedTrigger, true);
        this.enableTriggerPflegeItems(false);

        // andere Aufgabenlisten neu fuellen
        this.refreshAufgabenListen();
    }//GEN-LAST:event_jButtonSaveTriggerActionPerformed

    private void jButtonEditTriggerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditTriggerActionPerformed
        if(this.ivSelectedTrigger != null){
            this.enableTriggerPflegeItems(true);
        }
    }//GEN-LAST:event_jButtonEditTriggerActionPerformed

    private void jComboBoxTriggerMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTriggerMandantActionPerformed
        if(this.jComboBoxTriggerMandant.getSelectedItem() != null){
            this.cleanTriggerFelder();
            this.fillTriggerFertigEreignisList();
            this.refreshTriggerList();
        }
    }//GEN-LAST:event_jComboBoxTriggerMandantActionPerformed

    private void jListTriggerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListTriggerMouseClicked
        if (this.jListTrigger.getSelectedValue() != null) {
            this.ivSelectedTrigger = (Trigger) this.jListTrigger.getSelectedValue();
            this.setTriggerFelder();
            this.enableTriggerPflegeItems(false);
        }
    }//GEN-LAST:event_jListTriggerMouseClicked

    private void jComboBoxBuchungstageMandantenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBuchungstageMandantenActionPerformed
        this.initBuchungstagListe();
    }//GEN-LAST:event_jComboBoxBuchungstageMandantenActionPerformed

    private void jButtonLoadAenderungenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoadAenderungenActionPerformed

        Date lvDateVon = this.jXDatePickerProtokollVon.getDate();
        Date lvDateBis = this.jXDatePickerProtokollBis.getDate();
            
        SimpleDateFormat lvSdfVon = new SimpleDateFormat();
        lvSdfVon.applyPattern("yyyy-MM-dd 00:00:00");  
        
        SimpleDateFormat lvSdfBis = new SimpleDateFormat();
        lvSdfBis.applyPattern("yyyy-MM-dd 23:59:59"); 
        
        String lvAenderungVon = lvSdfVon.format(lvDateVon);
        String lvAenderungBis = lvSdfBis.format(lvDateBis);
        /*
        Timestamp lvAenderungenVonTS  = Timestamp.valueOf(lvAenderungVon);
        Timestamp lvAenderungenBisTS  = Timestamp.valueOf(lvAenderungBis);
        */
        Mandant lvMandant = (Mandant) this.defaultComboBoxModelMandanten.getSelectedItem();

        ProtokollDAO lvDAO = new ProtokollDAO(lvMandant);
        HashMap<String, ProtokollEintrag> lvProtokollEintragHashMap = lvDAO.findProtokollEintraege(lvAenderungVon, lvAenderungBis, lvMandant);
        
        
        int lvRow = 0;
        this.defaultTableModelProtokoll = new DefaultTableModel(lvProtokollEintragHashMap.size(), 6);
        String lvSpalten[] = {"Aktion", "Objekt-ID", "Objekt-Klasse", "Daten", "Zeitpunkt", "Benutzer"};
        this.defaultTableModelProtokoll.setColumnIdentifiers(lvSpalten);

        for (ProtokollEintrag pvProtokollEintrag : lvProtokollEintragHashMap.values()) {
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getAktion(), lvRow, 0);
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getObjectID(), lvRow, 1);
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getObjectClass(), lvRow, 2);
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getDaten(), lvRow, 3);
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getAenderungszeitpunkt(), lvRow, 4);
            this.defaultTableModelProtokoll.setValueAt(pvProtokollEintrag.getBenutzer(), lvRow, 5);           
            lvRow = lvRow + 1;
        }
        this.jTableProtokoll.setModel(defaultTableModelProtokoll);
        this.jTableProtokoll.getRowSorter().toggleSortOrder(4);
        
        
        
        
    }//GEN-LAST:event_jButtonLoadAenderungenActionPerformed

    private void jXMonthViewBuchungstageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXMonthViewBuchungstageActionPerformed
     DateFormat dfmt = new SimpleDateFormat("yyMMdd"); 
     DateFormat dfmt2 = new SimpleDateFormat("dd.MM.yyyy");
        SortedSet<Date> lvSelections = this.jXMonthViewBuchungstage.getSelection();
        
        for (Iterator<Date> it = lvSelections.iterator(); it.hasNext();) {
            Date lvDate = (Date)it.next();
            jFormattedTextFieldUltimo.setText(dfmt2.format(lvDate));
            Mandant lvMandant = (Mandant) this.jComboBoxBuchungstageMandanten.getSelectedItem();
            BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(lvMandant);
            jCheckBoxMonatsultimo.setSelected(lvBuTagVerw.isUltimo(dfmt.format(lvDate), lvMandant, true, false, false));
            jCheckBoxQuartalsultimo.setSelected(lvBuTagVerw.isUltimo(dfmt.format(lvDate), lvMandant, false, true, false));
            jCheckBoxJahresultimo.setSelected(lvBuTagVerw.isUltimo(dfmt.format(lvDate), lvMandant, false, false, true));
            // nur fuer das erste Datum aus der Selektion
            break;
        }
    }//GEN-LAST:event_jXMonthViewBuchungstageActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     DateFormat dfmt = new SimpleDateFormat("yyMMdd"); 
     DateFormat dfmt2 = new SimpleDateFormat("dd.MM.yyyy");

      SortedSet<Date> lvSelections = this.jXMonthViewBuchungstage.getSelection();
      if (lvSelections.size() == 1)
      {
         Iterator<Date> it = lvSelections.iterator();
         Date lvDate = (Date)it.next();
 
          if(this.jXMonthViewBuchungstage.isFlaggedDate(lvDate))
          {// Buchungstage sind als FlaggedDate markiert
             Mandant lvMandant = (Mandant) this.jComboBoxBuchungstageMandanten.getSelectedItem();
             BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(lvMandant);

             //System.out.println("Date: " + dfmt2.format(lvDate));
             if (!jFormattedTextFieldUltimo.getText().isEmpty())
             {
               lvBuTagVerw.updateUltimo(jFormattedTextFieldUltimo.getText().substring(8)+jFormattedTextFieldUltimo.getText().substring(3,5)+jFormattedTextFieldUltimo.getText().substring(0,2),
                                        lvMandant, false, false, false);
             }
             jFormattedTextFieldUltimo.setText(dfmt2.format(lvDate));
             lvBuTagVerw.updateUltimo(dfmt.format(lvDate), lvMandant, jCheckBoxMonatsultimo.isSelected(), jCheckBoxQuartalsultimo.isSelected(), jCheckBoxJahresultimo.isSelected());
           }
          else
          {
            JOptionPane.showMessageDialog((Component) null, "Bitte einen Buchungstag auswaehlen!"); 
          }
      }
      else
      {
          JOptionPane.showMessageDialog((Component) null, "Bitte ein Datum auswaehlen!");
      }
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    private void jCheckBoxMonatsultimoActionPerformed(java.awt.event.ActionEvent evt) {
        
    } 
    
    private void jCheckBoxQuartalsultimoActionPerformed(java.awt.event.ActionEvent evt) {
        
    } 
    
    private void jCheckBoxJahresultimoActionPerformed(java.awt.event.ActionEvent evt) {
        
    } 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProstView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProstView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProstView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProstView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        if(ProstView.checkPasswort()){

            final String lvFilenameINI = args[args.length - 1];
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ProstView(lvFilenameINI).setVisible(true);
                }
            });
            
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.DefaultComboBoxModel<Ereignis> defaultComboBoxModelDateisucheFertigEreignis;
    private javax.swing.DefaultComboBoxModel defaultComboBoxModelMandanten;
    private javax.swing.DefaultComboBoxModel<Ereignis> defaultComboBoxModelPSFertigEreignisse;
    private javax.swing.DefaultComboBoxModel<Ereignis> defaultComboBoxModelTriggerFertigEreignisse;
    private javax.swing.DefaultComboBoxModel<String> defaultComboBoxModelVgLebenszeichen;
    private javax.swing.DefaultListModel<Aufgabe> defaultListModelAufgabenSortierung;
    private javax.swing.DefaultListModel<Dateisuche> defaultListModelDateisuche;
    private javax.swing.DefaultListModel<Ereignis> defaultListModelEreignisse;
    private javax.swing.DefaultListModel<Aufgabe> defaultListModelMusterAuswahlAufgaben;
    private javax.swing.DefaultListModel<Ereignis> defaultListModelMusterAuswahlEreignisse;
    private javax.swing.DefaultListModel<ProzessStarter> defaultListModelProzessStarter;
    private javax.swing.DefaultListModel<Trigger> defaultListModelTrigger;
    private javax.swing.DefaultListModel<Vorgang> defaultListModelVorgaenge;
    private javax.swing.table.DefaultTableModel defaultTableModelProtokoll;
    private javax.swing.table.DefaultTableModel defaultTableModelVgAufgaben;
    private javax.swing.table.DefaultTableModel defaultTableModelVgEreignisse;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonAddBuchungstag;
    private javax.swing.JButton jButtonAufgabeDown;
    private javax.swing.JButton jButtonAufgabeUp;
    private javax.swing.JButton jButtonCancelDateisuche;
    private javax.swing.JButton jButtonCancelEreignis;
    private javax.swing.JButton jButtonCancelMuster;
    private javax.swing.JButton jButtonCancelPS;
    private javax.swing.JButton jButtonCancelTrigger;
    private javax.swing.JButton jButtonCloseVorgang;
    private javax.swing.JButton jButtonDeleteDateisuche;
    private javax.swing.JButton jButtonDeletePS;
    private javax.swing.JButton jButtonDeleteTrigger;
    private javax.swing.JButton jButtonEditDateisuche;
    private javax.swing.JButton jButtonEditEreignis;
    private javax.swing.JButton jButtonEditMuster;
    private javax.swing.JButton jButtonEditPS;
    private javax.swing.JButton jButtonEditTrigger;
    private javax.swing.JButton jButtonEreignisseDelete;
    private javax.swing.JButton jButtonEreignisseNew;
    private javax.swing.JButton jButtonLastMonth;
    private javax.swing.JButton jButtonLoadAenderungen;
    private javax.swing.JButton jButtonMusterDelete;
    private javax.swing.JButton jButtonMusterNew;
    private javax.swing.JButton jButtonNewDateisuche;
    private javax.swing.JButton jButtonNewPS;
    private javax.swing.JButton jButtonNewTrigger;
    private javax.swing.JButton jButtonNextMonth;
    private javax.swing.JButton jButtonRemoveBuchungstag;
    private javax.swing.JButton jButtonRepeatAufgabe;
    private javax.swing.JButton jButtonSaveDateisuche;
    private javax.swing.JButton jButtonSaveEreignis;
    private javax.swing.JButton jButtonSaveMuster;
    private javax.swing.JButton jButtonSavePS;
    private javax.swing.JButton jButtonSaveReihenfolge;
    private javax.swing.JButton jButtonSaveTrigger;
    private javax.swing.JButton jButtonUACancel;
    private javax.swing.JButton jButtonVorgangLoad;
    private javax.swing.JCheckBox jCheckBoxDateisucheUnbedingteAufgabe;
    private javax.swing.JCheckBox jCheckBoxEndeEreignis;
    private javax.swing.JCheckBox jCheckBoxJahresultimo;
    private javax.swing.JCheckBox jCheckBoxMonatsultimo;
    private javax.swing.JCheckBox jCheckBoxPsUndbedingteAufgabe;
    private javax.swing.JCheckBox jCheckBoxQuartalsultimo;
    private javax.swing.JCheckBox jCheckBoxRepeadLoad;
    private javax.swing.JCheckBox jCheckBoxStopEreignis;
    private javax.swing.JCheckBox jCheckBoxTriggerUnbedingteAufgabe;
    private javax.swing.JComboBox<Mandant> jComboBoxAufgabenSortierungMandant;
    private javax.swing.JComboBox<Mandant> jComboBoxBuchungstageMandanten;
    private javax.swing.JComboBox<Ereignis> jComboBoxDateisucheFertigEreignis;
    private javax.swing.JComboBox<Dateisuche> jComboBoxDateisucheMandant;
    private javax.swing.JComboBox jComboBoxDateisucheQualifier;
    private javax.swing.JComboBox jComboBoxEreignisMandanten;
    private javax.swing.JComboBox jComboBoxMusterMandant;
    private javax.swing.JComboBox jComboBoxPSFertigEreignis;
    private javax.swing.JComboBox jComboBoxPSMandant;
    private javax.swing.JComboBox jComboBoxProtokollMandant;
    private javax.swing.JComboBox jComboBoxTriggerFertigEreignis;
    private javax.swing.JComboBox jComboBoxTriggerMandant;
    private javax.swing.JComboBox jComboBoxUebersichtMandant;
    private javax.swing.JComboBox jComboBoxVgLebenszeichen;
    private javax.swing.JFormattedTextField jFormattedTextFieldBisZeitpunkt;
    private javax.swing.JFormattedTextField jFormattedTextFieldTriggerAusloesezeitpunkt;
    private javax.swing.JFormattedTextField jFormattedTextFieldUltimo;
    private javax.swing.JFormattedTextField jFormattedTextFieldVonZeitpunkt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<Dateisuche> jListDateisuche;
    private javax.swing.JList<Ereignis> jListEreignisseEreignisse;
    private javax.swing.JList<Aufgabe> jListMusterAufgaben;
    private javax.swing.JList<Ereignis> jListMusterEreignisse;
    private javax.swing.JList<ProzessStarter> jListProzessStarter;
    private javax.swing.JList<Trigger> jListTrigger;
    private javax.swing.JList<Aufgabe> jListUnbedingteAufgaben;
    private javax.swing.JList<Vorgang> jListVorgangsUebersicht;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuReload;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelAufgaben;
    private javax.swing.JPanel jPanelAusloesemuster;
    private javax.swing.JPanel jPanelBuchungstage;
    private javax.swing.JPanel jPanelEreignisse;
    private javax.swing.JPanel jPanelProtokoll;
    private javax.swing.JPanel jPanelVorgaenge;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTableProtokoll;
    private javax.swing.JTable jTableVgAufgaben;
    private javax.swing.JTable jTableVgEreignisse;
    private javax.swing.JTextField jTextFieldBezeichnungDateisuche;
    private javax.swing.JTextField jTextFieldBezeichnungTrigger;
    private javax.swing.JTextField jTextFieldEreignisBezeichnung;
    private javax.swing.JTextField jTextFieldMusterBezeichnung;
    private javax.swing.JTextField jTextFieldNameDateisuche;
    private javax.swing.JTextField jTextFieldPSBezeichnung;
    private javax.swing.JTextField jTextFieldPSName;
    private javax.swing.JTextField jTextFieldPSPfad;
    private javax.swing.JTextField jTextFieldPfadDateisuche;
    private javax.swing.JTree jTreeMuster;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerBuchungsdatum;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerProtokollBis;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerProtokollVon;
    private org.jdesktop.swingx.JXMonthView jXMonthViewBuchungstage;
    // End of variables declaration//GEN-END:variables
}
