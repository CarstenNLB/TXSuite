package de.nordlbit.prost.gui;

import de.nordlbit.prost.bd.VgAufgabe;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author frankew
 */
public class VgAufgabeTableModel extends AbstractTableModel 
{
    private static final String[] HEADER = {"Name", "in Arbeit", "erledigt", "i.A. seit", "erl. seit"};
    private List<VgAufgabe> data;
    
    /**
     * Konstruktor
     * @param data
     */
    public VgAufgabeTableModel(List<VgAufgabe> data) 
    {
        this.data = data;
    }
 
    /**
     * Liefert die Anzahl der Zeilen
     */
    public int getRowCount() 
    {
        return data.size();
    }
 
    /**
     * Liefert die Anzahl der Spalten
     */
    public int getColumnCount() 
    {
        return HEADER.length;
    }
 
    /**
     * Liefert den Wert an der entsprechenden Zeile und Spalte
     * @param row Zeile
     * @param column Spalte
     */
    public Object getValueAt(int row, int column) 
    {
        VgAufgabe lvVgAufgabe = data.get(row);
 
        switch(column)
        {
            case 0: return lvVgAufgabe.getAufgabe().getBezeichnung();
            case 1: return lvVgAufgabe.isInArbeit();
            case 2: return lvVgAufgabe.isErledigt();
            case 3: return lvVgAufgabe.getAnfangZeitpunkt();
            case 4: return lvVgAufgabe.getEndeZeitpunkt();        
            default: return "";
        }
    }
    
    /**
     * Ist die Zeile rot?
     * @param row Zeile
     * @return Wenn die Zeile rot ist, dann wird 'true' zurueckgeliefert. Ansonsten 'false'.
     */
    public boolean isRedRow(int row)
    {
        VgAufgabe lvVgAufgabe = data.get(row);
        if(lvVgAufgabe != null){
            return data.get(row).isZeitfensterAbgelaufen();
        } else {
            return false;
        }
    }
 
    /**
     * Liefert den Namen der Spalte
     */
    @Override
    public String getColumnName(int column) 
    {
        return HEADER[column];
    }
 
    /**
     * 
     */
    // Hier kann man die Klasse fuer eine Spalte aendern. 
    @Override
    public Class<?> getColumnClass(int column) 
    {
        if(column == 1 || column == 2){
            return Boolean.class;
        }
        return super.getColumnClass(column);
    }
    
    /**
     * Liefert die Vorgangsaufgabe der entsprechenden Zeile
     * @param row Zeile
     * @return Vorgangsaufgabe
     */
    public VgAufgabe getVgAufgabe(int row)
    {
        return data.get(row);
    }  
}
