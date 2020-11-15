package kohdeluettelo;

import java.io.*;
import java.util.*;

/**
 * Kohdeluettelon kohdeluokat, joka osaa mm. lisätä uuden kohdeluokan.
 *
 * @author Pasi Hänninen
 * @version 29.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class Kohdeluokat implements Iterable<Kohdeluokka> {

    private boolean muutettu = false;
    private String tiedostonPerusNimi = "";

    /** Taulukko kohdeluokista */
    private final Collection<Kohdeluokka> alkiot = new ArrayList<Kohdeluokka>();


    /**
     * Alustetaan Kohdeluokat.
     */
    public Kohdeluokat() {
    }


    /**
     * Lisää uuden kohdeluokan tietorakenteeseen.
     * @param koh Lisättävä kohdeluokka.
     */
    public void lisaa(Kohdeluokka koh) {
        alkiot.add(koh);
        muutettu = true;
    }
    
    /** 
     * Poistetaan valittu kohdeluokan.
     * @param kohdeluokka kohdeluokka
     * @return tosi jos löytyi poistettava kohdeluokka  
     * <pre name="test"> 
     * #THROWS SailoException  
     * #import java.io.File; 
     *  Kohdeluokat luokat = new Kohdeluokat(); 
     *  Kohdeluokka venus2 = new Kohdeluokka(); venus2.vastaaPlaneetat(2); 
     *  Kohdeluokka venus1 = new Kohdeluokka(); venus1.vastaaPlaneetat(1); 
     *  Kohdeluokka venus4 = new Kohdeluokka(); venus4.vastaaPlaneetat(2);  
     *  Kohdeluokka venus3 = new Kohdeluokka(); venus3.vastaaPlaneetat(1);  
     *  Kohdeluokka venus5 = new Kohdeluokka(); venus5.vastaaPlaneetat(2);  
     *  luokat.lisaa(venus2); 
     *  luokat.lisaa(venus1); 
     *  luokat.lisaa(venus4); 
     *  luokat.lisaa(venus3); 
     *  luokat.poista(venus5) === false ; luokat.getLkm() === 4; 
     *  luokat.poista(venus1) === true;   luokat.getLkm() === 3; 
     *  List<Kohdeluokka> h = luokat.annaKohdeluokat(1); 
     *  h.size() === 1;  
     *  h = luokat.annaKohdeluokat(1); 
     *  h.get(0) === venus3; 
     * </pre> 
     */ 
    public boolean poista(Kohdeluokka kohdeluokka) { 
        boolean ret = alkiot.remove(kohdeluokka); 
        if (ret) muutettu = true; 
        return ret; 
    } 

    /** 
     * Poistetaan kaikki tietyn kohteen kohdeluokat.
     * @param kohdeNro viite siihen, minkä kohteen Kohdeluokat poistetaan 
     * @return montako poistettiin  
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * #import java.io.File; 
     *  Kohdeluokat luokat = new Kohdeluokat(); 
     *  Kohdeluokka venus2 = new Kohdeluokka(); venus2.vastaaPlaneetat(2); 
     *  Kohdeluokka venus1 = new Kohdeluokka(); venus1.vastaaPlaneetat(1); 
     *  Kohdeluokka venus4 = new Kohdeluokka(); venus4.vastaaPlaneetat(2);  
     *  Kohdeluokka venus3 = new Kohdeluokka(); venus3.vastaaPlaneetat(1);  
     *  Kohdeluokka venus5 = new Kohdeluokka(); venus5.vastaaPlaneetat(2);  
     *  luokat.lisaa(venus2); 
     *  luokat.lisaa(venus1); 
     *  luokat.lisaa(venus4); 
     *  luokat.lisaa(venus3); 
     *  luokat.lisaa(venus5); 
     *  luokat.poista(2) === 3;  luokat.getLkm() === 2; 
     *  luokat.poista(3) === 0;  luokat.getLkm() === 2; 
     *  List<Kohdeluokka> h = luokat.annaKohdeluokat(2); 
     *  h.size() === 0;  
     *  h = luokat.annaKohdeluokat(1); 
     *  h.get(0) === venus1; 
     *  h.get(1) === venus3; 
     * </pre> 
     */
    public int poista(int kohdeNro) { 
        int n = 0; 
        for (Iterator<Kohdeluokka> it = alkiot.iterator(); it.hasNext();) { 
            Kohdeluokka koh = it.next(); 
            if (koh.getKohdeNro() == kohdeNro) { 
                it.remove(); 
                n++; 
            } 
        } 
        if (n > 0) muutettu = true; 
        return n; 
    } 

    /**
     * Luetaan kohdeluokat tiedostosta.
     * @param tied tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Kohdeluokat luokat = new Kohdeluokat();
     *  Kohdeluokka venus2 = new Kohdeluokka(); venus2.vastaaPlaneetat(2);
     *  Kohdeluokka venus1 = new Kohdeluokka(); venus1.vastaaPlaneetat(1);
     *  Kohdeluokka venus4 = new Kohdeluokka(); venus4.vastaaPlaneetat(2); 
     *  Kohdeluokka venus3 = new Kohdeluokka(); venus3.vastaaPlaneetat(1); 
     *  Kohdeluokka venus5 = new Kohdeluokka(); venus5.vastaaPlaneetat(2); 
     *  String tiedNimi = "testikohteet";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  luokat.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  luokat.lisaa(venus2);
     *  luokat.lisaa(venus1);
     *  luokat.lisaa(venus4);
     *  luokat.lisaa(venus3);
     *  luokat.lisaa(venus5);
     *  luokat.talleta();
     *  luokat = new Kohdeluokat();
     *  luokat.lueTiedostosta(tiedNimi);
     *  Iterator<Kohdeluokka> i = luokat.iterator();
     *  i.next().toString() === venus2.toString();
     *  i.next().toString() === venus1.toString();
     *  i.next().toString() === venus4.toString();
     *  i.next().toString() === venus3.toString();
     *  i.next().toString() === venus5.toString();
     *  i.hasNext() === false;
     *  luokat.lisaa(venus5);
     *  luokat.talleta();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                Kohdeluokka koh = new Kohdeluokka();
                koh.parse(rivi);
                lisaa(koh);
            }
            muutettu = false;

        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta.
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }
    
    
    /**
     * Tallentaa Kohdeluokat tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Kohdeluokka koh : this) {
                fo.println(koh.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }

    /**
     * Palautetaan kohdeluettelon kohdeluokkien lukumäärä.
     * @return Palautetaan kohdeluokkien lukumäärä.
     */
    public int getLkm() {
        return alkiot.size();
    }

    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta.
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen.
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen.
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen.
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    /**
     * Iteraattori kaikkien kohdeluokkien läpikäymiseen.
     * @return Palauttaa kohdeluokkieniteraattorin
     * 
     * @example
     * <pre name="test">
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     *  Kohdeluokat kohdeluokat = new Kohdeluokat();
     *  Kohdeluokka testi21 = new Kohdeluokka(2); kohdeluokat.lisaa(testi21);
     *  Kohdeluokka testi11 = new Kohdeluokka(1); kohdeluokat.lisaa(testi11);
     *  Kohdeluokka testi22 = new Kohdeluokka(2); kohdeluokat.lisaa(testi22);
     *  Kohdeluokka testi12 = new Kohdeluokka(1); kohdeluokat.lisaa(testi12);
     *  Kohdeluokka testi23 = new Kohdeluokka(2); kohdeluokat.lisaa(testi23);
     * 
     *  Iterator<Kohdeluokka> i2=kohdeluokat.iterator();
     *  i2.next() === testi21;
     *  i2.next() === testi11;
     *  i2.next() === testi22;
     *  i2.next() === testi12;
     *  i2.next() === testi23;
     *  i2.next() === testi12;  #THROWS NoSuchElementException  
     *  
     *  int n = 0;
     *  int nrot[] = {2,1,2,1,2};
     *  
     *  for ( Kohdeluokka koh:kohdeluokat ) { 
     *    koh.getKohdeNro() === nrot[n]; n++;  
     *  }
     *  
     *  n === 5;
     *  
     * </pre>
     */
    @Override
    public Iterator<Kohdeluokka> iterator() {
        return alkiot.iterator();
    }


    /**
     * Haetaan kohteen kohdeluokka.
     * @param kohdeNro Kohteen numero jolle kohdeluokka haetaan.
     * @return Palauttaa tietorakenteen, jossa viiteet löydettyyn kohdeluokkaan.
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Kohdeluokat kohdeluokat = new Kohdeluokat();
     *  Kohdeluokka testi21 = new Kohdeluokka(2); kohdeluokat.lisaa(testi21);
     *  Kohdeluokka testi11 = new Kohdeluokka(1); kohdeluokat.lisaa(testi11);
     *  Kohdeluokka testi22 = new Kohdeluokka(2); kohdeluokat.lisaa(testi22);
     *  Kohdeluokka testi12 = new Kohdeluokka(1); kohdeluokat.lisaa(testi12);
     *  Kohdeluokka testi23 = new Kohdeluokka(2); kohdeluokat.lisaa(testi23);
     *  Kohdeluokka testi51 = new Kohdeluokka(5); kohdeluokat.lisaa(testi51);
     *  
     *  List<Kohdeluokka> loytyneet;
     *  loytyneet = kohdeluokat.annaKohdeluokat(3);
     *  loytyneet.size() === 0; 
     *  loytyneet = kohdeluokat.annaKohdeluokat(1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == testi11 === true;
     *  loytyneet.get(1) == testi12 === true;
     *  loytyneet = kohdeluokat.annaKohdeluokat(5);
     *  loytyneet.size() === 1; 
     *  loytyneet.get(0) == testi51 === true;
     * </pre> 
     */
    public List<Kohdeluokka> annaKohdeluokat(int kohdeNro) {
        List<Kohdeluokka> loydetyt = new ArrayList<Kohdeluokka>();
        for (Kohdeluokka koh : alkiot)
            if (koh.getKohdeNro() == kohdeNro) loydetyt.add(koh);
        return loydetyt;
    }

    /** 
     * Laitetaan muutos, jolloin pakotetaan tallentamaan.   
     */ 
    public void setMuutos() { 
        muutettu = true; 
    }

    /**
     * Testataan toimintaa.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kohdeluokat kohteita = new Kohdeluokat();
        Kohdeluokka planeetat = new Kohdeluokka();
        planeetat.vastaaPlaneetat(1);
        Kohdeluokka kaapioplaneetat = new Kohdeluokka();
        kaapioplaneetat.vastaaPlaneetat(2);
        Kohdeluokka asteroidit = new Kohdeluokka();
        asteroidit.vastaaPlaneetat(3);

        kohteita.lisaa(planeetat);
        kohteita.lisaa(kaapioplaneetat);
        kohteita.lisaa(asteroidit);
        kohteita.lisaa(kaapioplaneetat);

        System.out.println("============= Kohdeluokat testi =================");

        List<Kohdeluokka> lisaakohteita = kohteita.annaKohdeluokat(1);

        for (Kohdeluokka kohde : lisaakohteita) {
            System.out.print(kohde.getKohdeNro() + "|");
            kohde.tulosta(System.out);
        }

    }

}