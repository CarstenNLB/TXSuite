/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.nordlbit.prost.bd;

import java.util.HashMap;

/**
 *
 * @author frankew
 */
public class Mandant {

    private static HashMap<String, Mandant> ivMandanten;
    private String ivId;
    private String ivName;
    private String ivInstitutsId;

    public static HashMap<String, Mandant> getMandanten() {
        if (Mandant.ivMandanten != null) {
            return ivMandanten;
        }

        //Mandant.fillMandanten();
        /*
        MandantenDAO lvDAO = new MandantenDAO("009"); //Vorgang.getSvInstitut());

        ivMandanten = lvDAO.getMandanten();
        */
        
        return ivMandanten;
    }
    
    public static void setMandanten(HashMap<String, Mandant> pvMandanten)
    {
        //ivMandanten = new HashMap<String, Mandant>();
        //ivMandanten.put("2", new Mandant("2", "NORD/LB", "009"));
        //ivMandanten.put("1", new Mandant("1", "BLB", "004"));
        ivMandanten = pvMandanten;
    }
    
    
     /**
     * @param pvId
     * @param pvName
     * @param pvInstitutsId 
     */
    public Mandant(String pvId, String pvName, String pvInstitutsId) {
        super();
        this.ivId = pvId;
        this.ivName = pvName;
        this.ivInstitutsId = pvInstitutsId;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return this.ivName;
    }

    /**
     * @param pvName the name to set
     */
    public void setName(String pvName) {
        this.ivName = pvName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.ivId;
    }

    /**
     * @param pvId the id to set
     */
    public void setId(String pvId) {
        this.ivId = pvId;
    }
    
    public String getInstitutsId(){
        return this.ivInstitutsId;
    }
    
    public void setInstitutsId(String pvId){
        this.ivInstitutsId = pvId;
    }


    public String toString() {
        return this.getName();
    }
}
