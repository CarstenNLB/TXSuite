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

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import de.nordlbit.prost.Utilities.IniReader;
import de.nordlbit.prost.Utilities.StringCrypt;
import de.nordlbit.prost.bd.Mandant;
import de.nordlbit.prost.bd.VgAufgabe;
import de.nordlbit.prost.bd.VgAufgabenvektor;
import de.nordlbit.prost.bd.VgEreignis;
import de.nordlbit.prost.bd.VgEreignisvektor;
import de.nordlbit.prost.bd.Vorgang;
import de.nordlbit.prost.dao.BuchungstagVerwaltung;
import de.nordlbit.prost.dao.DatenbankParameter;
import de.nordlbit.prost.dao.DatenbankZugriff;
import de.nordlbit.prost.dao.ereignis.EreignisDAO;
import de.nordlbit.prost.dao.ereignisvektor.EreignisVektorDAO;

/**
 * Klasse 'VorgangView'
 * @author frankew
 */
public class VorgangView extends javax.swing.JFrame 
{

    private Set<VgEreignis> ivVgEreignisse;
    private Set<VgAufgabe> ivVgAufgaben;
    private javax.swing.Timer ivTimer  = null;
    private String ivSystem = "Produktion";
    private ArrayList<String> ivListeMandanten; 

    /**
     * Creates new form ProstView
     * @param pvFilenameINI 
     */
    public VorgangView(String pvFilenameINI) 
    {
        System.out.println("FilenameINI: " + pvFilenameINI);
        
        initComponents();

        // Parameter einlesen
        ////ivListeMandanten = new ArrayList<String>();
        //ivListeDatenbankParameter = new HashMap<String, DatenbankParameter>();
        HashMap<String, Mandant> lvListeNeuerMandanten = new HashMap<String, Mandant>();
        ////int lvMandantenNr = 1;
        IniReader lvReader = null;  
        ////String lvDatenbankname = new String();
        String lvSystem = new String();
        try 
        {
            lvReader = new IniReader(pvFilenameINI);
            lvSystem = new String();
            ////do
            ////{
              lvSystem = lvReader.getPropertyString("Konfiguration", "System", "Fehler");
              if (!lvSystem.equals("Fehler"))
              {
                System.out.println("System: " + lvSystem);
                ////ivListeMandanten.add(lvMandant);
                ////lvMandantenNr++;
              }
            ////} while (!lvMandant.equals("Fehler"));
            ////System.out.println(ivListeMandanten.size() + " Mandanten eingelesen...");

            ////if (lvMandantenNr == 1)
            else
            {
                System.out.println("Kein System in '" + pvFilenameINI + "'");
                System.exit(1);
            }
            
            DatenbankParameter lvDatenbankParameter;
            Mandant lvNeuerMandant;
            ////for (int lvLauf = 0; lvLauf < ivListeMandanten.size(); lvLauf++)
            ////{
                ////lvMandant = ivListeMandanten.get(lvLauf);
                lvDatenbankParameter = new DatenbankParameter();
            
                String lvID = lvReader.getPropertyString(lvSystem, "ID", "Fehler");
                if (lvID.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Keine ID in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("ID: " + lvID);

                String lvInstitutID = lvReader.getPropertyString(lvSystem,"InstitutID", "Fehler");
                if (lvInstitutID.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Keine InstitutID in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("InstitutID: " + lvInstitutID);
                lvNeuerMandant = new Mandant(lvID, lvInstitutID, lvInstitutID);
                lvListeNeuerMandanten.put(lvID, lvNeuerMandant);

                String lvUsername = lvReader.getPropertyString(lvSystem, "Username", "Fehler");
                if (lvUsername.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Kein Username in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("Username: " + lvUsername);
                lvDatenbankParameter.setUsername(lvUsername);       
                
                String lvPassword = lvReader.getPropertyString(lvSystem, "Password", "Fehler");
                if (lvPassword.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Kein Password in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("Password: " + lvPassword);
                lvDatenbankParameter.setPassword(StringCrypt.decodeROT13(lvPassword));                
                
                String lvDatenbankname = lvReader.getPropertyString(lvSystem, "Datenbankname", "Fehler");
                if (lvDatenbankname.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Kein Datenbankname in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("Datenbank: " + lvDatenbankname);
                lvDatenbankParameter.setDatenbankname(lvDatenbankname);

                String lvIP =  lvReader.getPropertyString(lvSystem, "IP", "Fehler");
                if (lvIP.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Keine IP in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("IP: " + lvIP);
                lvDatenbankParameter.setIP(lvIP);
            
                String lvPort =  lvReader.getPropertyString(lvSystem, "Port", "Fehler");
                if (lvPort.equals("Fehler"))
                {
                    System.out.println("[" + lvSystem + "]Kein Port in '" + pvFilenameINI + "'");
                    System.exit(1);
                }
                System.out.println("Port: " + lvPort);
                lvDatenbankParameter.setPort(lvPort);
                
                DatenbankZugriff.getListeDatenbankParameter().put(lvInstitutID, lvDatenbankParameter);
            ////}
        }
        catch (Exception exp)
        {
            System.out.println("Fehler beim Laden der Datei '" + pvFilenameINI + "'");
            System.exit(1);
        }
        Mandant.setMandanten(lvListeNeuerMandanten);
        
        // MandantenList
        this.fillUebersichtMandantenAuswahl();
        this.jLabelSystem.setText(lvSystem);
        
        // DB-Verbindung aufmachen
        // TODO Muss ueberarbeitet werden...
        Mandant lvMandant = (Mandant)this.jComboBoxUebersichtMandant.getSelectedItem();
        //DatenbankZugriff.openConnection(lvMandant.getInstitutsId());
        //DatenbankZugriff.setDatenbankParameter(DatenbankZugriff.getListeDatenbankParameter().get(ivListeMandanten.get(0)));
        DatenbankZugriff.openConnection(lvMandant.getInstitutsId());
        this.initBuchungstagListe(lvMandant);
    }
    
    // Mandanten holen   
    private void fillUebersichtMandantenAuswahl() {
        this.defaultComboBoxModelUebersichtMandant.removeAllElements();
        Collection<Mandant> lvMandanten = Mandant.getMandanten().values();
        for (Mandant pvMandant : lvMandanten) {
            this.defaultComboBoxModelUebersichtMandant.addElement(pvMandant);
        }
    }

    protected void loadVgObjekte() {
        String lvDatum = "";
        Date lvDate = this.jXDatePickerBuchungsdatum.getDate();
        SimpleDateFormat lvSdf = new SimpleDateFormat();
        lvSdf.applyPattern("dd.MM.yyyy");
        if (lvDate != null)
        {
            lvDatum = lvSdf.format(lvDate);
        }
        
        // selektierten Vorgang merken
        int lvSelectedIndex = this.jComboBoxVorgaenge.getSelectedIndex();
        
        this.defaultComboBoxModelVorgaenge.removeAllElements();
        this.jTableVgAufgaben.setModel(new DefaultTableModel());
        this.jTableVgEreignisse.setModel(new DefaultTableModel());
        this.defaultComboBoxModelVgLebenszeichen.removeAllElements();
        
        this.jComboBoxVorgaenge.updateUI();
        this.jTableVgAufgaben.updateUI();
        this.jTableVgEreignisse.updateUI();

        if (!lvDatum.isEmpty()) {
            ArrayList<Vorgang> lvVorgaengeSorted = new ArrayList<Vorgang>(Vorgang.getVorgaenge((Mandant) this.jComboBoxUebersichtMandant.getSelectedItem(), lvDatum).values());
            Collections.sort(lvVorgaengeSorted);
            for (Vorgang pvVorgang : lvVorgaengeSorted) {
                this.defaultComboBoxModelVorgaenge.addElement(pvVorgang);
            }
        }
        
        if(this.defaultComboBoxModelVorgaenge.getSize() > 0 ){
            if(this.defaultComboBoxModelVorgaenge.getSize() > lvSelectedIndex && lvSelectedIndex >= 0){
                this.jComboBoxVorgaenge.setSelectedIndex(lvSelectedIndex);
            } else {
                this.jComboBoxVorgaenge.setSelectedIndex(0);
            }
            this.jComboBoxVorgaengeActionPerformed(null);
        } else {
            JOptionPane.showMessageDialog(this, "Es wurden keine Vorgaenge fuer das Buchungsdatum gefunden!");
        }
        
    }

    /**
     * Laedt die Objekte des Vorgangs
     * @param pvVorgang Vorgang
     */
    private void loadVgObjekte(Vorgang pvVorgang) 
    {
        if(pvVorgang != null){
            this.jTableVgAufgaben.setModel(new DefaultTableModel());
            this.jTableVgEreignisse.setModel(new DefaultTableModel());
            this.jTableVgAufgaben.updateUI();
            this.jTableVgEreignisse.updateUI();

            Collection<VgAufgabe> lvVgAufgaben = VgAufgabenvektor.getVgAufgaben(pvVorgang);
            this.ivVgAufgaben = new HashSet<VgAufgabe>();

            for (VgAufgabe pvAufgabe : lvVgAufgaben) {
                this.ivVgAufgaben.add(pvAufgabe);
            }


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
    
    /**
     * Fuellt die Ereignis-Tabelle
     */
    private void fillVgEreignisTable() 
    {
        int lvRow = 0;
        this.defaultTableModelVgEreignisse = new DefaultTableModel(this.ivVgEreignisse.size(), 3);
        String lvSpalten[] = {"Vorgang", "Ereignis", "Ausloesezeitpunkt"};
        this.defaultTableModelVgEreignisse.setColumnIdentifiers(lvSpalten);
        for (VgEreignis pvEreignis : this.ivVgEreignisse) 
        {
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getVorgangId(), lvRow, 0);
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getEreignis().getName(), lvRow, 1);
            this.defaultTableModelVgEreignisse.setValueAt(pvEreignis.getAusloesezeitpunkt(), lvRow, 2);
            lvRow = lvRow + 1;
        }
        this.jTableVgEreignisse.setModel(defaultTableModelVgEreignisse);
        this.jTableVgEreignisse.getRowSorter().toggleSortOrder(2);
    }

    /**
     * Fuellt die Aufgaben-Tabelle
     */
    private void fillVgAufgabenTable() 
    {
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
      columnModel.getColumn( 3 ).setPreferredWidth( 180);
      columnModel.getColumn( 4 ).setPreferredWidth( 173);  
    }

    /**
     * Zeigt einen Ok/Cancel-Dialog an  
     * @param theMessage Die anzuzeigende Nachricht
     * @return
     */
    public static int okcancel(String theMessage) 
    {
        int result = JOptionPane.showConfirmDialog((Component) null, theMessage,
            "alert", JOptionPane.OK_CANCEL_OPTION);
        return result;
    }
    
    /**
     * Setzt das System
     * @param pvSystem
     */
    public void setSystem(String pvSystem){
        this.ivSystem = pvSystem;
    }

    /**
     * Liefert das System
     * @return
     */
    public String getSystem()
    {
        return this.ivSystem;
   }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() 
    {
        defaultComboBoxModelUebersichtMandant = new javax.swing.DefaultComboBoxModel<Mandant>();
        defaultTableModelVgEreignisse = new javax.swing.table.DefaultTableModel();
        defaultTableModelVgAufgaben = new javax.swing.table.DefaultTableModel();
        defaultComboBoxModelVorgaenge = new javax.swing.DefaultComboBoxModel<Vorgang>();
        defaultComboBoxModelVgLebenszeichen = new javax.swing.DefaultComboBoxModel<String>();
        jPanelVorgangsuebersicht = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jComboBoxUebersichtMandant = new javax.swing.JComboBox<Mandant>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTableVgAufgaben = new javax.swing.JTable();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTableVgEreignisse = new javax.swing.JTable();
        jButtonVorgangLoad = new javax.swing.JButton();
        jCheckBoxRepeadLoad = new javax.swing.JCheckBox();
        jXDatePickerBuchungsdatum = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxVorgaenge = new javax.swing.JComboBox<Vorgang>();
        jLabelSystem = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxVgLebenszeichen = new javax.swing.JComboBox<String>();
        jButtonRepeatAufgabe = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PROST - Prozess-Steuerung-TXS - Vorgangsuebersicht");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel21.setText("System:");
        jLabel21.setVisible(false);

        jComboBoxUebersichtMandant.setModel(defaultComboBoxModelUebersichtMandant);
        jComboBoxUebersichtMandant.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxUebersichtMandantActionPerformed(evt);
            }
        });
        jComboBoxUebersichtMandant.setVisible(false);

        jLabel22.setText("Vorgaenge:");

        jLabel23.setText("Aufgaben:");

        jTableVgAufgaben.setAutoCreateRowSorter(true);
        jTableVgAufgaben.setModel(defaultTableModelVgAufgaben);
        jTableVgAufgaben.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane8.setViewportView(jTableVgAufgaben);

        jLabel24.setText("Ereignisse:");

        jTableVgEreignisse.setAutoCreateRowSorter(true);
        jTableVgEreignisse.setModel(defaultTableModelVgEreignisse);
        jScrollPane9.setViewportView(jTableVgEreignisse);

        jButtonVorgangLoad.setBackground(new java.awt.Color(0, 51, 255));
        jButtonVorgangLoad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonVorgangLoad.setText("Load");
        jButtonVorgangLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVorgangLoadActionPerformed(evt);
            }
        });

        jCheckBoxRepeadLoad.setText("Autom. Refresh");
        jCheckBoxRepeadLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxRepeadLoadActionPerformed(evt);
            }
        });

        jLabel7.setText("Buchungsdatum:");

        jLabel1.setText("System:");

        jComboBoxVorgaenge.setModel(defaultComboBoxModelVorgaenge);
        jComboBoxVorgaenge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxVorgaengeActionPerformed(evt);
            }
        });

        jLabelSystem.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabelSystem.setForeground(new java.awt.Color(255, 51, 51));
        jLabelSystem.setText(getSystem());

        jLabel2.setText("Letztes Lebenszeichen:");

        jComboBoxVgLebenszeichen.setModel(defaultComboBoxModelVgLebenszeichen);

        jButtonRepeatAufgabe.setText("Restart");
        jButtonRepeatAufgabe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRepeatAufgabeActionPerformed(evt);
            }
        });
        jButtonRepeatAufgabe.setVisible(false);

        jButton1.setText("Stop");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                 jButtonStopEreignisActionPerformed(evt);
            }
        });
        jButton1.setVisible(false);

        jButton2.setText("Start");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartEreignisActionPerformed(evt);
            }
        });
        jButton2.setVisible(false);
                
        javax.swing.GroupLayout jPanelVorgangsuebersichtLayout = new javax.swing.GroupLayout(jPanelVorgangsuebersicht);
        jPanelVorgangsuebersicht.setLayout(jPanelVorgangsuebersichtLayout);
        jPanelVorgangsuebersichtLayout.setHorizontalGroup(
            jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVorgangsuebersichtLayout.createSequentialGroup()
                                    .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel22)
                                        .addComponent(jLabel23))
                                    .addGap(46, 46, 46))
                                .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                                    .addComponent(jLabel24)
                                    .addGap(45, 45, 45)))
                            .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButtonRepeatAufgabe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(23, 23, 23)))
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                                .addComponent(jComboBoxVorgaenge, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBoxVgLebenszeichen, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE)
                            .addComponent(jScrollPane9)))
                    .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jXDatePickerBuchungsdatum, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                            .addComponent(jLabelSystem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                                .addComponent(jButtonVorgangLoad)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jCheckBoxRepeadLoad))
                            .addComponent(jComboBoxUebersichtMandant, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanelVorgangsuebersichtLayout.setVerticalGroup(
            jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxUebersichtMandant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelSystem, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jXDatePickerBuchungsdatum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonVorgangLoad)
                    .addComponent(jCheckBoxRepeadLoad))
                .addGap(55, 55, 55)
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jComboBoxVorgaenge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxVgLebenszeichen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRepeatAufgabe))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanelVorgangsuebersichtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelVorgangsuebersichtLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(45, 45, 45)
                        .addComponent(jButton1)
                        .addGap(28, 28, 28)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 3, Short.MAX_VALUE)
                .addComponent(jPanelVorgangsuebersicht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 30, Short.MAX_VALUE)
                .addComponent(jPanelVorgangsuebersicht, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        DatenbankZugriff.closeConnection();
    }//GEN-LAST:event_formWindowClosed

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

    private void jButtonVorgangLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonVorgangLoadActionPerformed
        this.loadVgObjekte();
        Vorgang lvVorgang = (Vorgang)this.jComboBoxVorgaenge.getSelectedItem();
        Mandant lvMandant = (Mandant)this.jComboBoxUebersichtMandant.getSelectedItem();
        EreignisVektorDAO lvEreignisVektorDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
        EreignisDAO lvEreignisDAO = new EreignisDAO(Vorgang.getSvMandant());
        if (lvVorgang != null && lvMandant != null)
        {
          if (lvEreignisVektorDAO.findStopEreignisVektor(lvVorgang.getId(), lvMandant.getId(), lvEreignisDAO.findStopEreignis(lvMandant.getId()).getId()))
          {
            jButton1.setEnabled(true);
            jButton2.setEnabled(false);
          }
          else
          {
            jButton1.setEnabled(false);
            jButton2.setEnabled(true);
          }
        }
    }//GEN-LAST:event_jButtonVorgangLoadActionPerformed

    private void jComboBoxVorgaengeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxVorgaengeActionPerformed
        Vorgang lvVorgang = (Vorgang)this.jComboBoxVorgaenge.getSelectedItem();
        this.loadVgObjekte(lvVorgang);
        this.fillVgAufgabenTable();
        this.fillVgEreignisTable();
    }//GEN-LAST:event_jComboBoxVorgaengeActionPerformed

    private void jComboBoxUebersichtMandantActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUebersichtMandantActionPerformed
        this.initBuchungstagListe(((Mandant)this.jComboBoxUebersichtMandant.getSelectedItem()));
    }//GEN-LAST:event_jComboBoxUebersichtMandantActionPerformed

    private void jButtonRepeatAufgabeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRepeatAufgabeActionPerformed
        int lvSelectedRow = this.jTableVgAufgaben.getSelectionModel().getLeadSelectionIndex();
        if(lvSelectedRow >= 0){
            lvSelectedRow = this.jTableVgAufgaben.convertRowIndexToModel(lvSelectedRow);
            VgAufgabe lvAufgabe = ((VgAufgabeTableModel) this.jTableVgAufgaben.getModel()).getVgAufgabe(lvSelectedRow);
            if(lvAufgabe != null){
                int i = okcancel("Are you sure ?"
                        + "\nSind evtl. anfallender Aufraeumarbeiten erledigt?"
                        + "\nEvtl. teilweise erzeugte Dateien entfernt?"
                        + "\nVerschobene Ausgangsdateien zurueckgestellt?"
                        + "\nBereits importierte Daten geloescht?"
                        + "\n..."
                        );
                if( i == 0){
                    lvAufgabe.enableRestart();
                }
            }
        }
    }//GEN-LAST:event_jButtonRepeatAufgabeActionPerformed

    private void jButtonStopEreignisActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Vorgang lvVorgang = (Vorgang)this.jComboBoxVorgaenge.getSelectedItem();
        Mandant lvMandant = (Mandant)this.jComboBoxUebersichtMandant.getSelectedItem();
        if (lvVorgang != null)
        {
          //System.out.println("Vorgang: " + lvVorgang.getId());
          EreignisVektorDAO lvDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
          EreignisDAO lvEreignisDAO = new EreignisDAO(Vorgang.getSvMandant());
          Calendar lvCal = Calendar.getInstance();          
          SimpleDateFormat lvSdf = new SimpleDateFormat(); 
          lvSdf.setTimeZone(lvCal.getTimeZone());
          lvSdf.applyPattern("yyyy-MM-dd HH:mm:ss"); 
          lvDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), lvVorgang.getId(), lvMandant.getId(), lvEreignisDAO.findStopEreignis(lvMandant.getId()).getId());
          this.loadVgObjekte();
          jButton1.setEnabled(false);
          jButton2.setEnabled(true);
        }
    }
    
    private void jButtonStartEreignisActionPerformed(java.awt.event.ActionEvent evt) 
    {
        Vorgang lvVorgang = (Vorgang)this.jComboBoxVorgaenge.getSelectedItem();
        Mandant lvMandant = (Mandant)this.jComboBoxUebersichtMandant.getSelectedItem();
        if (lvVorgang != null)
        {
          //System.out.println("Vorgang: " + lvVorgang.getId());
          EreignisVektorDAO lvDAO = new EreignisVektorDAO(Vorgang.getSvMandant());
          EreignisDAO lvEreignisDAO = new EreignisDAO(Vorgang.getSvMandant());
          Calendar lvCal = Calendar.getInstance();          
          SimpleDateFormat lvSdf = new SimpleDateFormat(); 
          lvSdf.setTimeZone(lvCal.getTimeZone());
          lvSdf.applyPattern("yyyy-MM-dd HH:mm:ss"); 
          lvDAO.insertEreignisVektor(lvSdf.format(lvCal.getTime()), lvVorgang.getId(), lvMandant.getId(), "79");//lvEreignisDAO.findStopEreignis(lvMandant.getId()).getId());
          lvDAO.deleteStopEreignisVektor(lvVorgang.getId(), lvMandant.getId(), lvEreignisDAO.findStopEreignis(lvMandant.getId()).getId());
          this.loadVgObjekte();
          jButton1.setEnabled(true);
          jButton2.setEnabled(false);
        }
    }

    private void initBuchungstagListe(Mandant pvMandant)
    {
        GregorianCalendar lvAktCal = (GregorianCalendar) this.jXDatePickerBuchungsdatum.getMonthView().getCalendar();
        DateFormat dfmt = new SimpleDateFormat("yy");
        String lvSearch = dfmt.format(lvAktCal.getTime()); 
        
        BuchungstagVerwaltung lvBuTagVerw = new BuchungstagVerwaltung(pvMandant);
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
            java.util.logging.Logger.getLogger(VorgangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VorgangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VorgangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VorgangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        final String lvFilenameINI = args[args.length - 1];
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VorgangView(lvFilenameINI).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.DefaultComboBoxModel<Mandant> defaultComboBoxModelUebersichtMandant;
    private javax.swing.DefaultComboBoxModel<String> defaultComboBoxModelVgLebenszeichen;
    private javax.swing.DefaultComboBoxModel<Vorgang> defaultComboBoxModelVorgaenge;
    private javax.swing.table.DefaultTableModel defaultTableModelVgAufgaben;
    private javax.swing.table.DefaultTableModel defaultTableModelVgEreignisse;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonRepeatAufgabe;
    private javax.swing.JButton jButtonVorgangLoad;
    private javax.swing.JCheckBox jCheckBoxRepeadLoad;
    private javax.swing.JComboBox<Mandant> jComboBoxUebersichtMandant;
    private javax.swing.JComboBox<String> jComboBoxVgLebenszeichen;
    private javax.swing.JComboBox<Vorgang> jComboBoxVorgaenge;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelSystem;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelVorgangsuebersicht;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTableVgAufgaben;
    private javax.swing.JTable jTableVgEreignisse;
    private org.jdesktop.swingx.JXDatePicker jXDatePickerBuchungsdatum;
    // End of variables declaration//GEN-END:variables
}
