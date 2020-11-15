package kohdeluettelo;

import java.io.*;
import java.util.Comparator;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka Kohde -olioille.
 *
 * @author Pasi Hänninen
 * @version 30.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */

public class Kohde implements Cloneable {
    private int        kohdeNro;
    private String     nimi = "";
    private String     loytajat = "";
    private String     loytoaika = "";
    private String     keskietaisyys = "";
    private String     kiertoaika = "";
    private String     keskiratanopeus = "";
    private String     kuidenLKM;
    private String     kuidenNimet = "";
    private String     halkaisija = "";
    private String     pintaala = "";
    private String     massa = "";
    private String     keskitiheys;
    private String     pyorahdysaika = "";
    private String     lampotila = "";
    private String	   lisatietoja = "";

    private static int seuraavaNro    = 1;

    /**
     * Luokka joka vertaa kahta kohdetta keskenään.
     */
    public static class Vertailija implements Comparator<Kohde> {

        private final int kenttanro;


        /**
         * Alustetaan vertailija vertailemaan tietyn kentän perusteella
         * @param k vertailtavan kentän indeksi.
         */
        public Vertailija(int k) {
            this.kenttanro = k;
        }


        /**
         * Verrataana kahta kohdetta keskenään.
         * @param k1 1. verrattava kohde
         * @param k2 2. verrattava kohde
         * @return <0  jos j1 < j2, == 0 jos j1 == j2 ja muuten >0
         */
        @Override
		public int compare(Kohde k1, Kohde k2) {
            String s1 = k1.anna(kenttanro);
            String s2 = k2.anna(kenttanro);

            return s1.compareTo(s2);
        }
    }

    
    /**
     * Palauttaa kohteen kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 16;
    }


    /**
     * Eka kenttä joka on mielekäs kysyttäväksi
     * @return ekan kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monennenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0:
            return "" + kohdeNro;
        case 1:
            return "" + nimi;
        case 2:
            return "" + loytajat;
        case 3:
            return "" + loytoaika;
        case 4:
            return "" + keskietaisyys;
        case 5:
            return "" + kiertoaika;
        case 6:
            return "" + keskiratanopeus;
        case 7:
            return "" + kuidenLKM;
        case 8:
            return "" + kuidenNimet;
        case 9:
            return "" + halkaisija;
        case 10:
            return "" + pintaala;
        case 11:
            return "" + massa;
        case 12:
            return "" + keskitiheys;
        case 13:
            return "" + pyorahdysaika;
        case 14:
            return "" + lampotila;
        case 15:
            return "" + lisatietoja;
        default:
            return "";
        }
    }

    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Kohde kohde = new Kohde();
     *   kohde.aseta(1,"Merkurius") === null;
     * </pre>
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setKohdeNro(Mjonot.erota(sb, '§', getKohdeNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            loytoaika = tjono;
            return null;
        case 3:
            loytajat = tjono;
            return null;
        case 4:
            keskietaisyys = tjono;
            return null;
        case 5:
            kiertoaika = tjono;
            return null;
        case 6:
            keskiratanopeus = tjono;
            return null;
        case 7:
            kuidenLKM = tjono;
            return null;
        case 8:
            kuidenNimet = tjono;
            return null;
        case 9:
            halkaisija = tjono;
            return null;
        case 10:
            pintaala = tjono;
            return null;
        case 11:
            massa = tjono;
            return null;
        case 12:
            keskitiheys = tjono;
            return null;
        case 13:
            pyorahdysaika = tjono;
            return null;
        case 14:
            lampotila = tjono;
            return null;
        case 15:
            lisatietoja = tjono;
            return null;
        default:
            return "";
        }
    }


    /**
     * Palauttaa k:tta kohteen kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0:
            return "kohdenro";
        case 1:
            return "nimi";
        case 2:
            return "löytöaika";
        case 3:
            return "löytäjät";
        case 4:
            return "keskietäisyys Auringosta";
        case 5:
            return "kiertoaika Auringon ympäri";
        case 6:
            return "keskiratanopeus";
        case 7:
            return "kuiden lukumäärä";
        case 8:
            return "kuiden nimet";
        case 9:
            return "päiväntasaajan halkaisija ";
        case 10:
            return "pinta-ala";
        case 11:
            return "massa";
        case 12:
            return "keskitiheys";
        case 13:
            return "pyörähdysaika";
        case 14:
            return "pinnan lämpötila";
        case 15:
            return "lisätietoja";
        default:
            return "";
        }
    }
    
    /**
     * Antaa kohteelle seuraavan kohdenumeron.
     * @return Kohteen uusi kohdeNro.
     * @example
     * <pre name="test">
     *   Kohde merkurius = new Kohde();
     *   merkurius.getKohdeNro() === 0;
     *   merkurius.rekisteroi();
     *   Kohde venus = new Kohde();
     *   venus.rekisteroi();
     *   int n1 = merkurius.getKohdeNro();
     *   int n2 = venus.getKohdeNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        kohdeNro = seuraavaNro;
        seuraavaNro++;
        return kohdeNro;
    }

    /** 
     * Palauttaa kohdenumeron.
     * @return Palauttaa kohdeNron.
     */
    public int getKohdeNro() {
    	return kohdeNro;
    }
    
    /**
     * Asettaa kohdenumeron ja samalla varmistaa, että seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava kohdenumero
     */
    private void setKohdeNro(int nr) {
        kohdeNro = nr;
        if (kohdeNro >= seuraavaNro) seuraavaNro = kohdeNro + 1;
    }
    
    /**
     * Palauttaa kohteen nimen.
     * @return Palauttaa kohteen nimen.
     * @example
     * <pre name="test">
     *   Kohde venus = new Kohde();
     *   venus.vastaaVenus();
     *   venus.getNimi() =R= "Venus";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }
    
    /**
     * Palauttaa tiedon kohteen löytäjistä.
     * @return Palauttaa löytäjät.
     */
    public String getLoytajat() {
    	return loytajat;
    }
    
    /** Palauttaa tiedon kohteen löytöajasta.
     * @return Palauttaa löytöajan.
     */
    public String getLoytoaika() {
    	return loytoaika;
    }
    
    /**
     * Palauttaa tiedon kohteen keskietäisyyden auringosta.
     * @return Palauttaa keskietäisyyden.
     */
    public String getKeskietaisyys() {
    	return keskietaisyys;
    }
    
    /**
     * Palauttaa tiedon kohteen kiertoajasta auringon ympäri.
     * @return Palauttaa kiertoajan.
     */
    public String getKiertoaika() {
    	return kiertoaika;
    }

    /**
     * Palauttaa tiedon kohteen keskiratanopeudesta auringon ympäri.
     * @return Palauttaa keskiratanopeuden.
     */
    public String getKeskiratanopeus() {
    	return keskiratanopeus;
    }
    
    /**
     * Palauttaa tiedon kohteen kuiden lukumäärästä.
     * @return Palauttaa kuiden lukumäärän.
     */
    public String getKuidenLKM() {
    	return kuidenLKM;
    }
    
    /**
     * Palauttaa tiedon kohteen kuiden nimistä.
     * @return Palauttaa kuiden nimet.
     */
    public String getKuidenNimet() {
    	return kuidenNimet;
    }
    
    /**
     * Palauttaa tiedon kohteen päiväntasaajan halkaisijasta.
     * @return Palauttaa halkaisijan.
     */
    public String getHalkaisija() {
    	return halkaisija;
    }
    
    /** 
     * Palauttaa tiedon kohteen pinta-alasta.
     * @return Palauttaa pinta-alan.
     */
    public String getPintaala() {
    	return pintaala;
    }
    
    /**
     * Palauttaa tiedon kohteen massasta.
     * @return Palauttaa massan.
     */
    public String getMassa() {
    	return massa;
    }
    
    /**
     * Palauttaa tiedon kohteen keskitiheydestä.
     * @return Palauttaa keskitiheyden.
     */
    public String getKeskitiheys() {
    	return keskitiheys;
    }
    
    /**
     * Palauttaa tiedon kohteen pyörähdysajasta.
     * @return Palauttaa pyörähdysajan.
     */
    public String getPyorahdysaika() {
    	return pyorahdysaika;
    }
    
    /**
     * Palauttaa tiedon kohteen lämpötilasta.
     * @return Palauttaa lämpötilan.
     */
    public String getLampotila() {
    	return lampotila;
    }
    
    /**
     * Palauttaa tiedon lisätietoa -kentästä.
     * @return Palauttaa lisätiedon.
     */
    public String getLisatietoja() {
    	return lisatietoja;
    }

    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot kohteelle.
     */
    public void vastaaVenus() {
        nimi = "Venus";
        loytajat = "ei tiedossa";
        loytoaika = "esi-historiallinen";
        keskietaisyys = "108208926";
        kiertoaika = "224,70096 d";
        keskiratanopeus = "35.020";
        kuidenLKM = "0";
        kuidenNimet = "-";
        halkaisija = "121037";
        pintaala = "4,60×10^8";
        massa = "4,8685×1024";
        keskitiheys = "5.204";
        pyorahdysaika = "243,0185 d";
        lampotila = "Alin: 228 K, Keski: 735 K, Ylin: 775 K";
        lisatietoja = "Hieman Maapalloa pienempi";
    }


    /**
     * Tulostetaan kohteen tiedot.
     * @param out Tietovirta, johon tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", kohdeNro, 3) + "|Nimi: " + nimi);
        out.println("Löytäjät: " + loytajat + "|Löytöaika: " + loytoaika);
        out.println("Kiertoradan ominaisuudet");
        out.println("Keskietäisyys auringosta: " + keskietaisyys +
                " km|Kiertoaika auringon ympäri " + kiertoaika +
                "|Keskiratanopeus: " + keskiratanopeus + " km/s|Kuiden lukumäärä: " + kuidenLKM);
        out.print("Fyysiset ominaisuudet");
        out.println("Päiväntasaajan halkaisija: " + halkaisija + " km|Pinta-ala: " +
                " km2|Massa: " + massa + " kg");
        out.println("Keskitiheys: " + keskitiheys + " g/cm3|Pyörähdysaika: " + pyorahdysaika);
        out.println("Pinnan lämpötila");
        out.println(lampotila);
        out.println("Lisatietoa: " + lisatietoja);
    }


    /**
     * Tulostetaan kohteen tiedot.
     * @param os Tietovirta, johon tulostetaan.
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Palauttaa kohteen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return kohde tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kohde kohde = new Kohde();
     *   kohde.parse("   2  |  Venus   | ");
     *   kohde.toString().startsWith("2|Venus|") === true; // on enemmäkin kuin 2 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
    }


    /**
     * Selvitetään kohteen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva kohdeNro.
     * @param rivi josta kohteen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Kohde Kohde = new Kohde();
     *   Kohde.parse("   1  |  Merkurius   | ");
     *   Kohde.getKohdeNro() === 1;
     *   Kohde.toString().startsWith("1|Merkurius|") === true; // on enemmäkin kuin 2 kenttää, siksi loppu |
     *
     *   Kohde.rekisteroi();
     *   int n = Kohde.getKohdeNro();
     *   Kohde.parse(""+(n+20));       // Otetaan merkkijonosta vain kohdenumero
     *   Kohde.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   Kohde.getKohdeNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    /**
     * Tehdään identtinen klooni kohteesta.
     * @return Object kloonattu kohde
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Kohde kohde = new Kohde();
     *   kohde.parse("   4  |  Mars   | ");
     *   Kohde kopio = kohde.clone();
     *   kopio.toString() === kohde.toString();
     *   kohde.parse("   4  |  Mars Jupiter   | ");
     *   kopio.toString().equals(kohde.toString()) === false;
     * </pre>
     */
    @Override
    public Kohde clone() throws CloneNotSupportedException {
        Kohde uusi;
        uusi = (Kohde) super.clone();
        return uusi;
    }
    
    /**
     * Testataan toimintaa.
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Kohde venus = new Kohde(); 
        Kohde mars = new Kohde();
        venus.rekisteroi();
        mars.rekisteroi();
        venus.tulosta(System.out);
        venus.vastaaVenus();
        venus.tulosta(System.out);

        mars.vastaaVenus();
        mars.tulosta(System.out);

        mars.vastaaVenus();
        mars.tulosta(System.out);
    }

}