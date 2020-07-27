/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

/**
 *
 * @author frankew
 */
public interface Protokoll {
    
    public static final int STRING = 0;
    public static final int XML = 1;
    public static final int HTML = 2;
    
    public static final String INSERT = "NEW";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    
    public String getProtokollEintrag(int pvProtokollEintragFormat, String pvProtokollEintragTyp);
}
