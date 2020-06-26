/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.nordlbit.prost.dao.aufgabenvektor.AufgabenVektorDAO;

/**
 *
 * @author frankew
 */
public class VgAufgabenvektor 
{
    /**
     * 
     */
    private Set<VgAufgabe> ivErkannteAufgaben;
    
    /**
     * 
     */
    public VgAufgabenvektor(){
        super();
        this.ivErkannteAufgaben = new HashSet<VgAufgabe>();
    }

   /**
    * 
    * @param pvVg
    * @return
    */
    public static Collection<VgAufgabe> getVgAufgaben(Vorgang pvVg){
        AufgabenVektorDAO lvDAO = new AufgabenVektorDAO(Vorgang.getSvMandant());
        ArrayList<VgAufgabe> lvCol = new ArrayList<VgAufgabe>();
        
        for(VgAufgabe pvAufgabe : lvDAO.findAufgabenByVorgangID(pvVg).values()){
                lvCol.add(pvAufgabe);
        }
        
        return lvCol;
    }    
}
