/*******************************************************************************
 * Copyright (c) 2014 NORD/LB Norddeutsche Landesbank Girozentrale
 * Alle Rechte vorbehalten.
 *
 *******************************************************************************/

package nlb.txs.schnittstelle.Wertpapier.Gattungsdaten;

import java.util.List;

/**
 * @author tepperc
 *
 */
public class Gattung 
{
    /**
     * Ursprungsland (stat. BA)
     */
    private String ivGD160;
    
    /**
     *  Ursprungsland (ISO 3166)
     */
    private String ivGD161; 
    
    /**
     * BISTA-Laufzeitenschluessel
     */
    private String ivGD214;
    
    /**
     * Emittentengruppe
     */
    private String ivGD226;
    
    /**
     * Wertpapiertyp
     */
    private String ivGD230;
    
    /**
     * Urspr. Emittentennummer
     */
    private String ivGD240;
    
    /**
     * Aktuelle Emittentennummer
     */
    private String ivGD245;
    
    /**
     * Instrumentenkurzbezeichnung (INST_SHORT_NAME)
     */
    private String ivGD260;
    
    /**
     * Wertpapier-Langbezeichnung
     */
    private String ivGD270;
     
    /**
     * Wertpapiertyp spezifische Teile
     */
    private String ivGD280A;
    
    /**
     * erster Kupon per
     */
    private String ivGD290A;
    
    /**
     * Sonderheiten bei Zins- bzw. Dividendenzahlungen 
     */
    private String ivGD311A;
    
    /**
     * Zinszahlungsmodus
     */
    private String ivGD312;
    
    /**
     * Zinslauf/Dividendenart
     */
    private String ivGD321;
    
    /**
     * Datum Zinslaufbeginn/Dividendenberechtigung ab
     */
    private String ivGD322;

    /**
     * Verbriefung stueckeloser Wertpapiere
     */
    private String ivGD481A;
    
    /**
     * Kennzeichen Inhaber-/Namenspapiere
     */
    private String ivGD483;
    
    /**
     * Kennzeichen Deckungsstockfaehigkeit
     */
    private String ivGD572;
    
    /**
     * Emmissionsbetrag bzw. Anzahl emittierter Titel
     */
    private String ivGD630A;
    
    /**
     * Waehrung Kapitalbetraege
     */
    private String ivGD630B;
    
    /**
     * Emissionstag/Laufzeitbeginn
     */
    private String ivGD660;
    
    /**
     * Emissionskurs 
     */
    private String ivGD669;

    /**
     * Haircut EZB
     */
    private String ivGD776I;

    /**
     * Zinssatz %
     */
    private String ivGD801A;
    
    /**
     * Mindestzinssatz/-dividende
     */
    private String ivGD803E;
    
    /**
     * Maximalzinssatz/-dividende
     */
    private String ivGD804E;
    
    /**
     * Kennzeichen Zinssatz
     */
    private String ivGD805;
    
    /**
     * Zins-/Dividendenhoehe Abhaengigkeit
     */
    private String ivGD808;
  
    /**
     * Referenzzeitraum Art
     */
    private String ivGD808A;
   
    /**
     * Referenzzeitraum Anzahl
     */
    private String ivGD808B;
    
    /**
     * Prozentsatz Interbank-Offered-Rate
     */
    private String ivGD808C;
    
    /**
     * Tage Zinssatzfestlegung 
     */
    private String ivGD809;

    /**
     * Basis Tageberechnung
     */
    private String ivGD809C;
    
    /**
     * Zinstermin Periode
     */
    private String ivGD811;
    
    /**
     * Laufende Zinsperiode von  
     */
    private String ivGD815B;
    
    /**
     * Zinsberechnungsmethode
     */
    private String ivGD821B;
    
    /**
     * Tilgungsart
     */
    private String ivGD841;
    
    /**
     * Tilgungsrhythmus
     */
    private String ivGD842;
    
    /**
     * Einloesekurs
     */
    private String ivGD861A;
    
    /**
     * erster Tilgungstermin
     */
    private String ivGD900;
    
    /**
     * letzter Tilgungstermin
     */
    private String ivGD910;
    
    /**
     * Tilgungsate
     */
    private String ivGD921B;
    
    /**
     * Schuldnerkuendbarkeitsfrist
     */
    private String ivGD943;
    
    /**
     * Glaeubigerkuendbarkeit ab/am
     */
    private String ivGD952;
    
    /**
     * Art der Deckung
     */
    private String ivGD970G;

    /**
     * Adresse
     */
    private Adresse ivAdresse;
    
    /**
     * Liste mit den Kuendigungsterminen 
     */
    private List<KuendigungTermin> ivKuendigungstermine;
    
    /**
     * Zinslaufende
     */
    private String ivGD323;
    
    /** 
     * Notenbankfaehigkeit
     */
    private String ivGD776;
    
    /**
     * Notenbankfaehigkeit EZB
     */
    private String ivGD776B;
    
    /**
     * Liquiditaetsklasse
     */
    private String ivGD776K;
    
    /**
     * Schuldnerkuendbarkeit (FIRST_EXERCISE_DATE)
     */
    private String ivGD942;
    
    // GD944A -> Schuldnerkuendigungskurs (STRIKE_PRICE); GD945A -> Schuldnerkuendigungskurs n-fach (STRIKE_PRICE); GD945B -> Schuldnerkuendbarkeit per n-fach (FIRST_EXERCISE_DATE)
    
    /**
     * Kennzeichen Ue20a KUG
     */
    private String ivGD970i;
    
    /**
     * 
     */
    private String ivRefIndex;
    
    /**
     * 
     */
    private String ivRefIndexCurrency;
    
    /**
     * 
     */
    private String ivRefIndexTerm;
    
    /**
     * 
     */
    private String ivProduktTyp;
    
    /**
     * 
     */
    private String ivBoersennotiert;
    
    /**
     * Konstruktor
	 */
	public Gattung() 
	{
		this.ivGD160 = new String();
		this.ivGD161 = new String();
		this.ivGD214 = new String();
		this.ivGD226 = new String();
		this.ivGD230 = new String();
		this.ivGD240 = new String();
		this.ivGD245 = new String();
		this.ivGD260 = new String();
		this.ivGD270 = new String();
		this.ivGD280A = new String();
		this.ivGD290A = new String();
		this.ivGD311A = new String();
		this.ivGD312 = new String();
		this.ivGD321 = new String();
		this.ivGD322 = new String();
		this.ivGD481A = new String();
		this.ivGD483 = new String();
		this.ivGD572 = new String();
		this.ivGD630A = new String();
		this.ivGD630B = new String();
		this.ivGD660 = new String();
		this.ivGD669 = new String();
		this.ivGD776I = new String();
		this.ivGD801A = new String();
		this.ivGD803E = new String();
		this.ivGD804E = new String();
		this.ivGD805 = new String();
		this.ivGD808 = new String();
		this.ivGD808A = new String();
		this.ivGD808B = new String();
		this.ivGD808C = new String();
		this.ivGD809 = new String();
		this.ivGD809C = new String();
		this.ivGD811 = new String();
		this.ivGD815B = new String();
		this.ivGD821B = new String();
		this.ivGD841 = new String();
		this.ivGD842 = new String();
		this.ivGD861A = new String();
		this.ivGD900 = new String();
		this.ivGD910 = new String();
		this.ivGD921B = new String();
		this.ivGD943 = new String();
		this.ivGD952 = new String();
		this.ivGD970G = new String();
		this.ivAdresse = null; // new Adresse();
		this.ivKuendigungstermine = null; // new ArrayList<KuendigungTermin>();
		this.ivGD323 = new String();
		this.ivGD776 = new String();
		this.ivGD776B = new String();
		this.ivGD776K = new String();
		this.ivGD942 = new String();
		this.ivGD970i = new String();
		this.ivRefIndex = new String();
		this.ivRefIndexCurrency = new String();
		this.ivRefIndexTerm = new String();
		this.ivProduktTyp = new String();
		this.ivBoersennotiert = new String();
	}

	/**
     * @return the gD160
     */
    public String getGD160() {
        return this.ivGD160;
    }

    /**
     * @param pvGD160 the gD160 to set
     */
    public void setGD160(String pvGD160) {
        this.ivGD160 = pvGD160;
    }

    /**
     * @return the gD161
     */
    public String getGD161() {
        return this.ivGD161;
    }

    /**
     * @param pvGD161 the gD161 to set
     */
    public void setGD161(String pvGD161) {
        this.ivGD161 = pvGD161;
    }

    /**
     * @return the gD214
     */
    public String getGD214() {
        return this.ivGD214;
    }

    /**
     * @param pvGD214 the gD214 to set
     */
    public void setGD214(String pvGD214) {
        this.ivGD214 = pvGD214;
    }

    /**
     * @return the gD226
     */
    public String getGD226() {
        return this.ivGD226;
    }

    /**
     * @param pvGD226 the gD226 to set
     */
    public void setGD226(String pvGD226) {
        this.ivGD226 = pvGD226;
    }

    /**
     * @return the gD230
     */
    public String getGD230() {
        return this.ivGD230;
    }

    /**
     * @param pvGD230 the gD230 to set
     */
    public void setGD230(String pvGD230) {
        this.ivGD230 = pvGD230;
    }

    /**
     * @return the gD240
     */
    public String getGD240() {
        return this.ivGD240;
    }

    /**
     * @param pvGD240 the gD240 to set
     */
    public void setGD240(String pvGD240) {
        this.ivGD240 = pvGD240;
    }

    /**
     * @return the gD245
     */
    public String getGD245() {
        return this.ivGD245;
    }

    /**
     * @param pvGD245 the gD245 to set
     */
    public void setGD245(String pvGD245) {
        this.ivGD245 = pvGD245;
    }

    /**
     * @return the gD260
     */
    public String getGD260() {
        return this.ivGD260;
    }

    /**
     * @param pvGD260 the gD260 to set
     */
    public void setGD260(String pvGD260) {
        this.ivGD260 = pvGD260;
    }

    /**
     * @return the gD270
     */
    public String getGD270() {
        return this.ivGD270;
    }

    /**
     * @param pvGD270 the gD270 to set
     */
    public void setGD270(String pvGD270) {
        this.ivGD270 = pvGD270;
    }

    /**
     * @return the gD280A
     */
    public String getGD280A() {
        return this.ivGD280A;
    }

    /**
     * @param pvGD280A the gD280A to set
     */
    public void setGD280A(String pvGD280A) {
        this.ivGD280A = pvGD280A;
    }

    /**
     * @return the gD290A
     */
    public String getGD290A() {
        return this.ivGD290A;
    }

    /**
     * @param pvGD290A the gD290A to set
     */
    public void setGD290A(String pvGD290A) {
        this.ivGD290A = pvGD290A;
    }

    /**
     * @return the gD311A
     */
    public String getGD311A() {
        return this.ivGD311A;
    }

    /**
     * @param pvGD311A the gD311A to set
     */
    public void setGD311A(String pvGD311A) {
        this.ivGD311A = pvGD311A;
    }

    /**
     * @return the gD312
     */
    public String getGD312() {
        return this.ivGD312;
    }

    /**
     * @param pvGD312 the gD312 to set
     */
    public void setGD312(String pvGD312) {
        this.ivGD312 = pvGD312;
    }

    /**
     * @return the gD321
     */
    public String getGD321() {
        return this.ivGD321;
    }

    /**
     * @param pvGD321 the gD321 to set
     */
    public void setGD321(String pvGD321) {
        this.ivGD321 = pvGD321;
    }

    /**
     * @return the gD322
     */
    public String getGD322() {
        return this.ivGD322;
    }

    /**
     * @param pvGD322 the gD322 to set
     */
    public void setGD322(String pvGD322) {
        this.ivGD322 = pvGD322;
    }

    /**
     * @return the gD481A
     */
    public String getGD481A() {
        return this.ivGD481A;
    }

    /**
     * @param pvGD481A the gD481A to set
     */
    public void setGD481A(String pvGD481A) {
        this.ivGD481A = pvGD481A;
    }

    /**
     * @return the gD483
     */
    public String getGD483() {
        return this.ivGD483;
    }

    /**
     * @param pvGD483 the gD483 to set
     */
    public void setGD483(String pvGD483) {
        this.ivGD483 = pvGD483;
    }

    /**
     * @return the gD572
     */
    public String getGD572() {
        return this.ivGD572;
    }

    /**
     * @param pvGD572 the gD572 to set
     */
    public void setGD572(String pvGD572) {
        this.ivGD572 = pvGD572;
    }

    /**
     * @return the gD630A
     */
    public String getGD630A() {
        return this.ivGD630A;
    }

    /**
     * @param pvGD630A the gD630A to set
     */
    public void setGD630A(String pvGD630A) {
        this.ivGD630A = pvGD630A;
    }

    /**
     * @return the gD630B
     */
    public String getGD630B() {
        return this.ivGD630B;
    }

    /**
     * @param pvGD630B the gD630B to set
     */
    public void setGD630B(String pvGD630B) {
        this.ivGD630B = pvGD630B;
    }

    /**
     * @return the gD660
     */
    public String getGD660() {
        return this.ivGD660;
    }

    /**
     * @param pvGD660 the gD660 to set
     */
    public void setGD660(String pvGD660) {
        this.ivGD660 = pvGD660;
    }

    /**
     * @return the gD669
     */
    public String getGD669() {
        return this.ivGD669;
    }

    /**
     * @param pvGD669 the gD669 to set
     */
    public void setGD669(String pvGD669) {
        this.ivGD669 = pvGD669;
    }

    /**
     * @return the gD776I
     */
    public String getGD776I() {
        return this.ivGD776I;
    }

    /**
     * @param pvGD776I the gD776I to set
     */
    public void setGD776I(String pvGD776I) {
        this.ivGD776I = pvGD776I;
    }

    /**
     * @return the gD801A
     */
    public String getGD801A() {
        return this.ivGD801A;
    }

    /**
     * @param pvGD801A the gD801A to set
     */
    public void setGD801A(String pvGD801A) {
        this.ivGD801A = pvGD801A;
    }

    /**
     * @return the gD803E
     */
    public String getGD803E() {
        return this.ivGD803E;
    }

    /**
     * @param pvGD803E the gD803E to set
     */
    public void setGD803E(String pvGD803E) {
        this.ivGD803E = pvGD803E;
    }

    /**
     * @return the gD804E
     */
    public String getGD804E() {
        return this.ivGD804E;
    }

    /**
     * @param pvGD804E the gD804E to set
     */
    public void setGD804E(String pvGD804E) {
        this.ivGD804E = pvGD804E;
    }

    /**
     * @return the gD805
     */
    public String getGD805() {
        return this.ivGD805;
    }

    /**
     * @param pvGD805 the gD805 to set
     */
    public void setGD805(String pvGD805) {
        this.ivGD805 = pvGD805;
    }

    /**
     * @return the gD808
     */
    public String getGD808() {
        return this.ivGD808;
    }

    /**
     * @param pvGD808 the gD808 to set
     */
    public void setGD808(String pvGD808) {
        this.ivGD808 = pvGD808;
    }

    /**
     * @return the gD808A
     */
    public String getGD808A() {
        return this.ivGD808A;
    }

    /**
     * @param pvGD808A the gD808A to set
     */
    public void setGD808A(String pvGD808A) {
        this.ivGD808A = pvGD808A;
    }

    /**
     * @return the gD808B
     */
    public String getGD808B() {
        return this.ivGD808B;
    }

    /**
     * @param pvGD808B the gD808B to set
     */
    public void setGD808B(String pvGD808B) {
        this.ivGD808B = pvGD808B;
    }

    /**
     * @return the gD808C
     */
    public String getGD808C() {
        return this.ivGD808C;
    }

    /**
     * @param pvGD808C the gD808C to set
     */
    public void setGD808C(String pvGD808C) {
        this.ivGD808C = pvGD808C;
    }

    /**
     * @return the gD809
     */
    public String getGD809() {
        return this.ivGD809;
    }

    /**
     * @param pvGD809 the gD809 to set
     */
    public void setGD809(String pvGD809) {
        this.ivGD809 = pvGD809;
    }

    /**
     * @return the gD809C
     */
    public String getGD809C() {
        return this.ivGD809C;
    }

    /**
     * @param pvGD809C the gD809C to set
     */
    public void setGD809C(String pvGD809C) {
        this.ivGD809C = pvGD809C;
    }

    /**
     * @return the gD811
     */
    public String getGD811() {
        return this.ivGD811;
    }

    /**
     * @param pvGD811 the gD811 to set
     */
    public void setGD811(String pvGD811) {
        this.ivGD811 = pvGD811;
    }

    /**
     * @return the gD815B
     */
    public String getGD815B() {
        return this.ivGD815B;
    }

    /**
     * @param pvGD815B the gD815B to set
     */
    public void setGD815B(String pvGD815B) {
        this.ivGD815B = pvGD815B;
    }

    /**
     * @return the gD821B
     */
    public String getGD821B() {
        return this.ivGD821B;
    }

    /**
     * @param pvGD821B the gD821B to set
     */
    public void setGD821B(String pvGD821B) {
        this.ivGD821B = pvGD821B;
    }

    /**
     * @return the gD841
     */
    public String getGD841() {
        return this.ivGD841;
    }

    /**
     * @param pvGD841 the gD841 to set
     */
    public void setGD841(String pvGD841) {
        this.ivGD841 = pvGD841;
    }

    /**
     * @return the gD842
     */
    public String getGD842() {
        return this.ivGD842;
    }

    /**
     * @param pvGD842 the gD842 to set
     */
    public void setGD842(String pvGD842) {
        this.ivGD842 = pvGD842;
    }

    /**
     * @return the gD861A
     */
    public String getGD861A() {
        return this.ivGD861A;
    }

    /**
     * @param pvGD861A the gD861A to set
     */
    public void setGD861A(String pvGD861A) {
        this.ivGD861A = pvGD861A;
    }

    /**
     * @return the gD900
     */
    public String getGD900() {
        return this.ivGD900;
    }

    /**
     * @param pvGD900 the gD900 to set
     */
    public void setGD900(String pvGD900) {
        this.ivGD900 = pvGD900;
    }

    /**
     * @return the gD910
     */
    public String getGD910() {
        return this.ivGD910;
    }

    /**
     * @param pvGD910 the gD910 to set
     */
    public void setGD910(String pvGD910) {
        this.ivGD910 = pvGD910;
    }

    /**
     * @return the gD921B
     */
    public String getGD921B() {
        return this.ivGD921B;
    }

    /**
     * @param pvGD921B the gD921B to set
     */
    public void setGD921B(String pvGD921B) {
        this.ivGD921B = pvGD921B;
    }

    /**
     * @return the gD943
     */
    public String getGD943() {
        return this.ivGD943;
    }

    /**
     * @param pvGD943 the gD943 to set
     */
    public void setGD943(String pvGD943) {
        this.ivGD943 = pvGD943;
    }

    /**
     * @return the gD952
     */
    public String getGD952() {
        return this.ivGD952;
    }

    /**
     * @param pvGD952 the gD952 to set
     */
    public void setGD952(String pvGD952) {
        this.ivGD952 = pvGD952;
    }

    /**
     * @return the gD970G
     */
    public String getGD970G() {
        return this.ivGD970G;
    }

    /**
     * @param pvGD970G the gD970G to set
     */
    public void setGD970G(String pvGD970G) {
        this.ivGD970G = pvGD970G;
    }

    /**
     * @return the adresse
     */
    public Adresse getAdresse() {
        return this.ivAdresse;
    }

    /**
     * @param pvAdresse the adresse to set
     */
    public void setAdresse(Adresse pvAdresse) {
        this.ivAdresse = pvAdresse;
    }

    /**
     * @return the kuendigungstermine
     */
    public List<KuendigungTermin> getKuendigungstermine() {
        return this.ivKuendigungstermine;
    }

    /**
     * @param pvKuendigungstermine the kuendigungstermine to set
     */
    public void setKuendigungstermine(List<KuendigungTermin> pvKuendigungstermine) {
        this.ivKuendigungstermine = pvKuendigungstermine;
    }

	/**
	 * @return the ivGD323
	 */
	public String getGD323() {
		return ivGD323;
	}

	/**
	 * @param pvGD323 the ivGD323 to set
	 */
	public void setGD323(String pvGD323) {
		this.ivGD323 = pvGD323;
	}

	/**
	 * @return the ivGD776
	 */
	public String getGD776() {
		return ivGD776;
	}

	/**
	 * @param ivGD776 the ivGD776 to set
	 */
	public void setGD776(String pvGD776) {
		this.ivGD776 = pvGD776;
	}

	/**
	 * @return the ivGD776B
	 */
	public String getGD776B() {
		return ivGD776B;
	}

	/**
	 * @param ivGD776B the ivGD776B to set
	 */
	public void setGD776B(String pvGD776B) {
		this.ivGD776B = pvGD776B;
	}

	/**
	 * @return the ivGD776K
	 */
	public String getGD776K() {
		return ivGD776K;
	}

	/**
	 * @param ivGD776K the ivGD776K to set
	 */
	public void setGD776K(String pvGD776K) {
		this.ivGD776K = pvGD776K;
	}

	/**
	 * @return the ivGD942
	 */
	public String getGD942() {
		return ivGD942;
	}

	/**
	 * @param ivGD942 the ivGD942 to set
	 */
	public void setGD942(String pvGD942) {
		this.ivGD942 = pvGD942;
	}

	/**
	 * @return the ivGD970i
	 */
	public String getGD970i() {
		return ivGD970i;
	}

	/**
	 * @param ivGD970i the ivGD970i to set
	 */
	public void setGD970i(String pvGD970i) {
		this.ivGD970i = pvGD970i;
	}

	/**
	 * @return the ivRefIndex
	 */
	public String getRefIndex() {
		return ivRefIndex;
	}

	/**
	 * @param ivRefIndex the ivRefIndex to set
	 */
	public void setRefIndex(String pvRefIndex) {
		this.ivRefIndex = pvRefIndex;
	}

	/**
	 * @return the ivRefIndexCurrency
	 */
	public String getRefIndexCurrency() {
		return ivRefIndexCurrency;
	}

	/**
	 * @param ivRefIndexCurrency the ivRefIndexCurrency to set
	 */
	public void setRefIndexCurrency(String pvRefIndexCurrency) {
		this.ivRefIndexCurrency = pvRefIndexCurrency;
	}

	/**
	 * @return the ivRefIndexTerm
	 */
	public String getRefIndexTerm() {
		return ivRefIndexTerm;
	}

	/**
	 * @param ivRefIndexTerm the ivRefIndexTerm to set
	 */
	public void setRefIndexTerm(String pvRefIndexTerm) {
		this.ivRefIndexTerm = pvRefIndexTerm;
	}

	/**
	 * @return the ivProduktTyp
	 */
	public String getProduktTyp() {
		return ivProduktTyp;
	}

	/**
	 * @param ivProduktTyp the ivProduktTyp to set
	 */
	public void setProduktTyp(String pvProduktTyp) {
		this.ivProduktTyp = pvProduktTyp;
	}

	/**
	 * @return the ivBoersennotiert
	 */
	public String getBoersennotiert() {
		return ivBoersennotiert;
	}

	/**
	 * @param ivBoersennotiert the ivBoersennotiert to set
	 */
	public void setBoersennotiert(String pvBoersennotiert) {
		this.ivBoersennotiert = pvBoersennotiert;
	}
}
