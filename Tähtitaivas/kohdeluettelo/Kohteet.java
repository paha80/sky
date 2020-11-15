package kohdeluettelo;

import java.io.*;
import java.util.*;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Luokka Kohteet -oliolle.
 * 
 * @author Pasi Hänninen
 * @version 29.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class Kohteet implements Iterable<Kohde> {
    private boolean muutettu = false;
    private int MAX_KOHTEITA = Integer.MAX_VALUE;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "kohteet";
    private Kohde alkiot[] = new Kohde[30];

    /**
     * Oletusmuodostaja
     */
    public Kohteet() {
    }

    /** 
     * Muodostaja, jolla maxkoko voidaan asettaa.
     * @param koko kohteiden maxkoko  
     *  
     */ 
    public Kohteet(int koko) { 
        MAX_KOHTEITA = koko;  
        alkiot = new Kohde[koko]; 
    } 
    
    /**
     * Lisää uuden kohteen tietorakenteeseen.
     * @param kohde Lisättävän kohteen viite.
     * @throws SailoException Jos tietorakenne on jo täynnä.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Kohteet kohteet = new Kohteet();
     * Kohde venus = new Kohde();
     * kohteet.getLkm() === 0;
     * kohteet.lisaa(venus); 
     * kohteet.getLkm() === 1;
     * </pre>
     */
    public void lisaa(Kohde kohde) throws SailoException {
    	if ( lkm >= MAX_KOHTEITA ) throw new SailoException("Liikaa alkioita");
        if (lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = kohde;
        lkm++;
        muutettu = true;
    }
    
    /** 
     * Poistaa kohteen, jolla on valittu kohdenumero.
     * @param id poistettavan kohteen kohdenumero 
     * @return 1 jos poistettiin, 0 jos ei löydy 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kohteet Kohteet = new Kohteet(1); 
     * Kohde mars = new Kohde();
     * mars.rekisteroi();
     * int id = mars.getKohdeNro();
     * Kohteet.poista(id) === 0;  
     * </pre>  
     */ 
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    } 

    /**
     * Korvaa kohteen tietorakenteessa.  Ottaa kohteen omistukseensa.
     * Etsitään samalla kohdenumerolla oleva kohde.  Jos ei löydy, niin lisätään uutena kohteena.
     * @param kohde lisättävän kohteen viite.  Huom tietorakenne muuttuu omistajaksi.
     * @throws SailoException jos tietorakenne on jo täynnä
     * <pre name="test">
     * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Kohteet Kohteet = new Kohteet();
     * Kohde mars1 = new Kohde(); 
     * Kohde mars2 = new Kohde();
     * mars1.rekisteroi(); mars2.rekisteroi();
     * Kohteet.getLkm() === 0;
     * Kohteet.korvaaTaiLisaa(mars1); Kohteet.getLkm() === 1;
     * Kohteet.korvaaTaiLisaa(mars2); Kohteet.getLkm() === 2;
     * Kohde mars3 = mars1.clone();
     * mars3.aseta(3,"kkk");
     * Iterator<Kohde> it = Kohteet.iterator();
     * it.next() == mars1 === true;
     * Kohteet.korvaaTaiLisaa(mars3); Kohteet.getLkm() === 2;
     * it = Kohteet.iterator();
     * Kohde j0 = it.next();
     * j0 === mars3;
     * j0 == mars3 === true;
     * j0 == mars1 === false;
     * </pre>
     */
    public void korvaaTaiLisaa(Kohde kohde) throws SailoException {
        int id = kohde.getKohdeNro();
        for (int i = 0; i < lkm; i++) {
            if ( alkiot[i].getKohdeNro() == id ) {
                alkiot[i] = kohde;
                muutettu = true;
                return;
            }
        }
        lisaa(kohde);
    }


    /**
     * Palauttaa viitteen i:een kohteeseen.
     * @param i Monenteenko kohteeseen viite halutaan.
     * @return Viite kohteeseen, jonka indeksi on i.
     * @throws IndexOutOfBoundsException Jos indeksi ei ole sallitulla alueella.
     */
    public Kohde anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    

    /**
     * Lukee kohteet tiedostosta. 
     * @param tied tiedosto, josta luetaan
     * @throws SailoException jos tulee ongelmia
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     * 
     *  Kohteet Kohteet = new Kohteet();
     *  Kohde mars1 = new Kohde(); 
     *  Kohde mars2 = new Kohde();
     *  mars1.vastaaVenus();
     *  mars2.vastaaVenus();
     *  String hakemisto = "testikohteet";
     *  String tiedNimi = hakemisto+"/nimet";
     *  File ftied = new File(tiedNimi+".dat");
     *  File dir = new File(hakemisto);
     *  dir.mkdir();
     *  ftied.delete();
     *  Kohteet.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  Kohteet.lisaa(mars1);
     *  Kohteet.lisaa(mars2);
     *  Kohteet.talleta();
     * </pre>
     */
    public void lueTiedostosta(String tied) throws SailoException {
        setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
        	String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                Kohde kohde = new Kohde();
                kohde.parse(rivi); 
                lisaa(kohde);
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
     * Talletetaan kohteet tiedostoon.  
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak); 

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (int i=0; i<getLkm(); i++) {
                Kohde kohde = anna(i);
                fo.println(kohde.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
    
    /**
     * Palauttaa kohdeluettelon kohteen nimen.
     * @return kohteen nimi
     */
    public String getKokoNimi() {
        return kokoNimi;
    }

    /**
     * Palauttaa kohdeluettelon kohteiden lukumäärän.
     * @return palauttaa kohteiden lukumäärä.
     */
    public int getLkm() {
        return lkm;
    }

    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta.
     * @param nimi tallennustiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen.
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen.
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    /**
     * Luokka kohteen iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Kohteet Kohteet = new Kohteet();
     * Kohde mars1 = new Kohde();
     * Kohde mars2 = new Kohde();
     * mars1.rekisteroi(); 
     * mars2.rekisteroi();
     *
     * Kohteet.lisaa(mars1); 
     * Kohteet.lisaa(mars2); 
     * Kohteet.lisaa(mars1); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Kohde kohde:Kohteet)   // Kokeillaan for-silmukan toimintaa
     *   ids.append(" "+kohde.getKohdeNro());           
     * 
     * String tulos = " " + mars1.getKohdeNro() + " " + mars2.getKohdeNro() + " " + mars1.getKohdeNro();
     * 
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Kohde>  i=Kohteet.iterator(); i.hasNext(); ) { 
     *   Kohde kohde = i.next();
     *   ids.append(" "+kohde.getKohdeNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Kohde>  i=Kohteet.iterator();
     * i.next() == mars1  === true;
     * i.next() == mars2  === true;
     * i.next() == mars1  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class KohteetIterator implements Iterator<Kohde> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraavaa kohdetta.
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä kohteita
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava kohde.
         * @return seuraava kohde
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Kohde next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori kohteistaan.
     * @return kohde iteraattori
     */
    @Override
	public Iterator<Kohde> iterator() {
        return new KohteetIterator();
    }


    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien kohteiden viitteet. 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä kohdeistä 
     * @example 
     * <pre name="test"> 
     * #THROWS SailoException  
     *   Kohteet Kohteet = new Kohteet(); 
     *   Kohde kohde1 = new Kohde(); kohde1.parse("1|Merkurius|ei tiedossa|esihistoriallinen|57 909 227 km|87 d 23,3 h|47,87 km/s|0|-|4 879,4 km|74,797x10^6 km2|330,104x10^21 kg (0,055 Maan massaa)|5,427 g/cm3|58 d 15,6 h|alin: 100 K (-173 C), keski: 440 K (167 C), ylin: 700 K (427 C)|Aurinkokunnan pienin planeetta."); 
     *   Kohde kohde2 = new Kohde(); kohde2.parse("2|Venus|esihistoriallinen|ei tiedossa|108 208 926 km|224,70096 d|35,020|0|-|12 103,7 km|4,60x10^8 km2|4,86885x10^24 kg (0,815 Maan massaa)|5,204 g/cm3|243,0185 d|alin: 228 K (-45 C), keski: 735 K (462 C), ylin: 773 K (500 C)|Venus on kooltaan lähimpänä omaa planeettaamme."); 
     *   Kohteet.lisaa(kohde1); Kohteet.lisaa(kohde2);
     * </pre> 
     */ 
    public Collection<Kohde> etsi(String hakuehto, int k) {
        String ehto = "*";
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto;
        int hk = k;
        if ( k < 0 ) hk = 1;
        List<Kohde> loytyneet = new ArrayList<Kohde>(); 
        for (Kohde kohde : this) { 
            if (WildChars.onkoSamat(kohde.anna(hk), ehto)) loytyneet.add(kohde);  
        } 
        Collections.sort(loytyneet, new Kohde.Vertailija(k));  
        return loytyneet; 
    } 

    
    /** 
     * Etsii kohteen id:n perusteella.
     * @param id kohdenumero, jonka mukaan etsitään 
     * @return kohde jolla etsittävä id tai null 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kohteet Kohteet = new Kohteet(5); 
     * Kohde mars1 = new Kohde();
     * Kohde mars2 = new Kohde();
     * Kohde mars3 = new Kohde(); 
     * mars1.rekisteroi(); mars2.rekisteroi(); mars3.rekisteroi(); 
     * int id1 = mars1.getKohdeNro(); 
     * Kohteet.lisaa(mars1); Kohteet.lisaa(mars2); Kohteet.lisaa(mars3); 
     * Kohteet.annaId(id1  ) == mars1 === true; 
     * Kohteet.annaId(id1+1) == mars2 === true; 
     * Kohteet.annaId(id1+2) == mars3 === true; 
     * </pre> 
     */ 
    public Kohde annaId(int id) { 
        for (Kohde kohde : this) { 
            if (id == kohde.getKohdeNro()) return kohde; 
        } 
        return null; 
    } 


    /** 
     * Etsii kohteen id:n perusteella. 
     * @param id kohdenumero, jonka mukaan etsitään 
     * @return löytyneen kohdeen indeksi tai -1 jos ei löydy 
     * <pre name="test"> 
     * #THROWS SailoException  
     * Kohteet Kohteet = new Kohteet(5); 
     * Kohde mars1 = new Kohde();
     * Kohde mars2 = new Kohde();
     * Kohde mars3 = new Kohde(); 
     * mars1.rekisteroi(); mars2.rekisteroi(); mars3.rekisteroi(); 
     * int id1 = mars1.getKohdeNro(); 
     * Kohteet.lisaa(mars1); Kohteet.lisaa(mars2); Kohteet.lisaa(mars3); 
     * Kohteet.etsiId(id1+1) === 1; 
     * Kohteet.etsiId(id1+2) === 2; 
     * </pre> 
     */ 
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getKohdeNro()) return i; 
        return -1; 
    } 

    /**
     * Testataan toimintaa.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kohteet kohteet = new Kohteet();

        Kohde venus = new Kohde();
        venus.rekisteroi();
        venus.vastaaVenus();

        try {
            kohteet.lisaa(venus);

            System.out.println("============= Kohteet testi =================");

            for (int i = 0; i < kohteet.getLkm(); i++) {
                Kohde kohde = kohteet.anna(i);
                System.out.println("Kohde nro: " + i);
                kohde.tulosta(System.out);
            }

        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}