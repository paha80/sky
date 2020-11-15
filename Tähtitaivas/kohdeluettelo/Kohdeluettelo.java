package kohdeluettelo;

import java.io.File;
import java.util.Collection;
import java.util.List;
import kohdeluettelo.Kohde;

/**
 * Kohdeluettelo -luokka, joka huolehtii kohteista.
 *
 * @author Pasi Hänninen
 * @version 30.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class Kohdeluettelo {
    private Kohteet kohteet = new Kohteet();
    private Kohdeluokat kohdeluokat = new Kohdeluokat();

    /**
     * Palauttetaan kohteiden lukumäärä.
     * @return Palauttaa kohteiden lukumäärän.
     */
    public int getKohteita() {
        return kohteet.getLkm();
    }
    
    /**
     * Poistetaan kohteista ja kohdeluokista ne, joilla on nro.
     * @param id viitenumero, jonka mukaan poistetaan
     * @return montako kohdetta poistettiin
     */
    public int poista(int id) {
        int ret = kohteet.poista(id); 
        kohdeluokat.poista(id); 
        return ret; 
    }


    /** 
     * Poistetaan tämä kohdeluokka. 
     * @param kohdeluokka poistettava kohdeluokka 
     */ 
    public void poistaKohdeluokka(Kohdeluokka kohdeluokka) { 
        kohdeluokat.poista(kohdeluokka); 
    } 

    /**
     * Lisätään uusi kohde.
     * @param kohde Lisättävä kohde.
     * @throws SailoException Jos lisäystä ei voida tehdä.
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Kohdeluettelo kohdeluettelo = new Kohdeluettelo();
     * Kohde venus1 = new Kohde(); 
     * Kohde venus2 = new Kohde();
     * venus1.rekisteroi(); 
     * venus2.rekisteroi();
     * kohdeluettelo.getKohteita() === 0;
     * kohdeluettelo.lisaa(venus1); kohdeluettelo.getKohteita() === 1;
     * kohdeluettelo.lisaa(venus2); kohdeluettelo.getKohteita() === 2;
     * kohdeluettelo.lisaa(venus1); kohdeluettelo.getKohteita() === 3;
     * kohdeluettelo.getKohteita() === 3;
     * kohdeluettelo.annaKohde(0) === venus1;
     * kohdeluettelo.annaKohde(1) === venus2;
     * kohdeluettelo.annaKohde(2) === venus1;
     * kohdeluettelo.annaKohde(3) === venus1; #THROWS IndexOutOfBoundsException 
     * </pre>
     */
    public void lisaa(Kohde kohde) throws SailoException {
        kohteet.lisaa(kohde);
    }

    /**
     * Lisätään uusi kohdeluokka kohdeluetteloon.
     * @param koh lisättävä kohdeluokka 
     * @throws SailoException jos tulee ongelmia
     */
    public void lisaa(Kohdeluokka koh) throws SailoException {
        kohdeluokat.lisaa(koh);
    }
    
    /** 
     * Korvaa kohteen tietorakenteessa.  Ottaa kohteen omistukseensa. 
     * Etsitään samalla kohdenumerolla oleva kohde. Jos ei löydy, 
     * niin lisätään uutena kohteena. 
     * @param kohde lisätäävän kohteen viite.
     * @throws SailoException jos tietorakenne on jo täynnä 
     */ 
    public void korvaaTaiLisaa(Kohde kohde) throws SailoException { 
        kohteet.korvaaTaiLisaa(kohde); 
    } 


    /** 
     * Palauttaa "taulukossa" hvenusehtoon vastaavien kohteiden viitteet 
     * @param hvenusehto hvenusehto  
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kohteissa 
     * @throws SailoException Jos jotakin menee väärin
     */ 
    public Collection<Kohde> etsi(String hvenusehto, int k) throws SailoException { 
        return kohteet.etsi(hvenusehto, k); 
    } 
    
    /**
     * Haetaan kaikki kohteen kohdeluokat.
     * @param kohde kohde, jolle kohdeluokkaa haetaan
     * @return tietorakenne jossa viiteet löydettyihin kohdeluokat
     * @throws SailoException jos tulee ongelmia
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.util.*;
     * 
     *  Kohdeluettelo kohdeluettelo = new Kohdeluettelo();
     *  Kohde venus1 = new Kohde(); 
     *  Kohde venus2 = new Kohde(); 
     *  Kohde venus3 = new Kohde();
     *  venus1.rekisteroi(); venus2.rekisteroi(); venus3.rekisteroi();
     *  int id1 = venus1.getKohdeNro();
     *  int id2 = venus2.getKohdeNro();
     *  Kohdeluokka pitsi11 = new Kohdeluokka(id1); kohdeluettelo.lisaa(pitsi11);
     *  Kohdeluokka pitsi12 = new Kohdeluokka(id1); kohdeluettelo.lisaa(pitsi12);
     *  Kohdeluokka pitsi21 = new Kohdeluokka(id2); kohdeluettelo.lisaa(pitsi21);
     *  Kohdeluokka pitsi22 = new Kohdeluokka(id2); kohdeluettelo.lisaa(pitsi22);
     *  Kohdeluokka pitsi23 = new Kohdeluokka(id2); kohdeluettelo.lisaa(pitsi23);
     *  
     *  List<Kohdeluokka> loytyneet;
     *  loytyneet = kohdeluettelo.annaKohdeluokat(venus3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kohdeluettelo.annaKohdeluokat(venus1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == pitsi11 === true;
     *  loytyneet.get(1) == pitsi12 === true;
     *  loytyneet = kohdeluettelo.annaKohdeluokat(venus2);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == pitsi21 === true;
     * </pre> 
     */
    public List<Kohdeluokka> annaKohdeluokat(Kohde kohde) throws SailoException {
        return kohdeluokat.annaKohdeluokat(kohde.getKohdeNro());
    }

    /** 
     * Laitetaan kohdeluokat muuttuneeksi, niin pakotetaan tallentamaan. 
     */ 
    public void setKohdeluokkaMuutos() { 
        kohdeluokat.setMuutos(); 
    } 

    /**
     * Asettaa tiedostojen perusnimet.
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        kohteet.setTiedostonPerusNimi(hakemistonNimi + "kohteet");
        kohdeluokat.setTiedostonPerusNimi(hakemistonNimi + "kohdeluokat");
    }
    
    
    /**
     * Lukee kohdeluettelon tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        
    	kohteet = new Kohteet();
        kohdeluokat = new Kohdeluokat();

        setTiedosto(nimi);
        kohteet.lueTiedostosta();
        kohdeluokat.lueTiedostosta();
        }

    /**
     * Tallettaa kohdeluettelon tiedot tiedostoon.
     * @throws SailoException jos tallettamisessa ongelmia
     */
    public void talleta() throws SailoException {
        String virhe = "";
        try {
            kohteet.talleta();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            kohdeluokat.talleta();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }
    
    /**
     * Palauttaa i:n kohteen.
     * @param i Monesko kohde palautetaan.
     * @return Palautetaan viite i:teen kohteeseen.
     * @throws IndexOutOfBoundsException Jos i väärin.
     */
    public Kohde annaKohde(int i) throws IndexOutOfBoundsException {
        return kohteet.anna(i);
    }
    
    /**
     * Testataan toimintaa.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kohdeluettelo kohdeluettelo = new Kohdeluettelo();

        try {          
            Kohde venus = new Kohde(); 
            Kohde mars = new Kohde();
            venus.rekisteroi();
            venus.vastaaVenus();
            mars.rekisteroi();
            mars.vastaaVenus();

            kohdeluettelo.lisaa(venus);
            kohdeluettelo.lisaa(mars);
            
            kohdeluettelo.poista(2);

            System.out.println("============= Kohdeluettelon testi =================");
            Collection<Kohde> kohteet = kohdeluettelo.etsi("", -1);
            int i = 0;
            for (Kohde kohde: kohteet) {
                System.out.println("Kohde paikassa: " + i);
                kohde.tulosta(System.out);
                List<Kohdeluokka> loytyneet = kohdeluettelo.annaKohdeluokat(kohde);
                for (Kohdeluokka kohdeluokka : loytyneet)
                    kohdeluokka.tulosta(System.out);
                i++;
            }

        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
        
    }

}