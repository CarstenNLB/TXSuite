/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.util.Comparator;

/**
 *
 * @author frankew
 */
public class AufgabeComparator implements Comparator<Aufgabe> {

    @Override
    public int compare(Aufgabe o1, Aufgabe o2) {
        return o1.getBezeichnung().compareTo(o2.getBezeichnung());
    }
    
}
