/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


/**
 *
 * @author frankew
 */
public class ColoredTableCellRenderer extends  DefaultTableCellRenderer  {
    @Override
    public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int col) {
 
            final Component comp;
            VgAufgabeTableModel model = (VgAufgabeTableModel) table.getModel( );
 
            comp = super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, col);
            
 
            if (model.isRedRow(row)) {
                comp.setBackground(Color.RED);
            }
            /*
            else {
                if(table.getSelectedRow() == row){
                 comp.setBackground(Color.);
                } else {
                  comp.setBackground(Color.WHITE);
                }
            }
            */
            return comp;
        }
}
