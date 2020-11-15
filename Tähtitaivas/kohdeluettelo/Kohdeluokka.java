package kohdeluettelo;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Luokka Kohdeluokka -olioille.
 * 
 * @author Pasi Hänninen
 * @version 30.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 * 
 * TODO: Tähän tekisin kohdeluokan lisäyksen ja muokkauksen JOptionPane.showOptionDialogin mukaisesti, mutta harkkatyöhön en ehdi
 * tekemään sellaista muutosta. Jatkan tämän kehittelyä kuitenkin, koska koko ohjelma tulee omien lasten käyttöön. Toimii 
 * nykyisenlaisenakin, mutta muutos toisi selkeyttä, yksinkertaisuutta, ja toki parempaa toimivuutta. Ongelmana olisi kuitenkin
 * silloin se, että kuinka lisätä kohdeluokkia nykyisestä kolmesta vaikka neljään tai viiteen. Lista voisi toimia parhaiten.
 */
public class Kohdeluokka implements Cloneable {

    private int kohdeluokanNro; 
    private int kohdeNro;
    private String kohdeluokanNimi;
    private static int seuraavaNro = 1;
    
    
    /**
     * Alustetaan kohdeluokka.
     */
    public Kohdeluokka() {
    }


    /**
     * Alustetaan tietyn kohteen kohdeluokka.  
     * @param kohdeNro kohteen viitenumero 
     */
    public Kohdeluokka(int kohdeNro) {
        this.kohdeNro = kohdeNro;
    }
    

    /**
     * @return kohdeluokan kenttien lukumäärä
     */
    public int getKenttia() {
        return 3;
    }


    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public int ekaKentta() {
        return 2;
    }
    

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     *
    public String getKysymys(int k) {
        switch (k) {
            case 0:
                return "kohdeluokannro";
            case 1:
                return "kohdenro";
            case 2:
                return "kohdeluokan nimi";
            default:
                return "???";
        }
    }
*/

    /**
     * @param k Minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     * @example
     * <pre name="test">
     *   Kohdeluokka koh = new Kohdeluokka();
     *   koh.parse("   4   |  1  |   Planeetta  | ");
     *   koh.anna(0) === "4";   
     *   koh.anna(1) === "1";   
     *   koh.anna(2) === "Planeetta";     
     * </pre>
     */
    public String anna(int k) {
        switch (k) {
        	case 0:
                return "" + kohdeluokanNro;
            case 1:
                return "" + kohdeNro;
            case 2:
            	return "" + kohdeluokanNimi;
            default:
                return "???";
        }
    }


    /**
     * Asetetaan valitun kentän sisältö.  Mikäli asettaminen onnistuu,
     * palautetaan null, muutoin virheteksti.
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     * @example
     * <pre name="test">
     *   Kohdeluokka koh = new Kohdeluokka();
     *   koh.aseta(0,"1") === null;
     *   koh.aseta(1,"1")  === null;
     *   koh.aseta(2,"Merkurius") === null;
     * </pre>
     */
    public String aseta(int k, String s) {
    	String st = s.trim();
        StringBuffer sb = new StringBuffer(st);
        switch (k) {
            case 0:
            	setKohdeluokanNro(Mjonot.erota(sb, '$', getKohdeluokanNro()));                
                return null;
            case 1:
            	kohdeNro = Mjonot.erota(sb, '$', kohdeNro);
                return null;
            case 2:
            	kohdeluokanNimi = st;
            	return null;
            default:
                return "Väärä kentän indeksi";
        }
    }
 

    /**
     * Tehdään identtinen klooni kohteesta.
     * @return Object kloonattu kohde
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Kohdeluokka koh = new Kohdeluokka();
     *   koh.parse("  10   |  9  |   Makemake   ");
     *   Kohdeluokka kopio = koh.clone();
     *   kopio.toString() === koh.toString();
     *   koh.parse("   1   |  11  |   Venus  ");
     *   kopio.toString().equals(koh.toString()) === false;
     * </pre>
     */
    @Override
    public Kohdeluokka clone() throws CloneNotSupportedException { // NOPMD
        return (Kohdeluokka)super.clone();
    }
    
    
    /**
     * Palautetaan mille kohteelle kohdeluokka kuuluu.
     * @return kohteen id
     */
    public int getKohdeNro() {
        return kohdeNro;
    }


    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot Kohdeluokalle.
     * @param nro Viite kohteeseen, jonka kohdeluokasta on kyse.
     */
    public void vastaaPlaneetat(int nro) {
    	kohdeNro = nro;
    	kohdeluokanNro = nro;
        kohdeluokanNimi = "Planeetat";
    }


    /**
     * Tulostetaan kohdeluokan tiedot.
     * @param out Tietovirta, johon tulostetaan.
     */
    public void tulosta(PrintStream out) {
        out.println(kohdeluokanNro + "|" + kohdeNro + "|" + kohdeluokanNimi + "|");
    }


    /**
     * Tulostetaan kohdeluokan tiedot.
     * @param os Tietovirta, johon tulostetaan.
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }


    /**
     * Antaa kohdeluokalle seuraavan kohdeluokan numeron.
     * @return Palauttaa kohdeluokan uusi numero.
     * @example
     * <pre name="test">
     *   Kohdeluokka planeetta1 = new Kohdeluokka();
     *   planeetta1.getKohdeNro() === 0;
     *   planeetta1.rekisteroi();
     *   Kohdeluokka planeetta2 = new Kohdeluokka();
     *   planeetta2.rekisteroi();
     *   int n1 = planeetta1.getKohdeNro();
     *   int n2 = planeetta2.getKohdeNro();
     *   n2-n1 === 0;
     * </pre>
     */
    public int rekisteroi() {
        kohdeluokanNro = seuraavaNro;
        seuraavaNro++;
        return kohdeluokanNro;
    }

    /**
     * Palautetaan kohdeluokan numero.
     * @return Palautetaan kohdeluokanNro.
     */
    public int getKohdeluokanNro() {
        return kohdeluokanNro;
    }
    
    /**
     * Asettaa numeron ja samalla varmistaa, että seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava numero
     */
    private void setKohdeluokanNro(int nr) {
        kohdeluokanNro = nr;
        if ( kohdeluokanNro >= seuraavaNro ) seuraavaNro = kohdeluokanNro + 1;
    } 

    /**
     * Palautetaan kohdeluokan nimi.
     * @return Palautetaan kohdeluokan nimi.
     */
    public String getKohdeluokanNimi() {
    	return kohdeluokanNimi;
    }
    
    /**
     * Palauttaa kohdeluokan tiedot merkkijonona, jonka voi tallentaa tiedostoon.
     * @return kohdeluokka tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Kohdeluokka kohdeluokka = new Kohdeluokka();
     *   kohdeluokka.parse("   10   |  9  |   Makemake    ");
     *   kohdeluokka.toString()    === "10|9|Makemake";
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
     * Selvitään kohteen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva kohdeluokanNro.
     * @param rivi josta kohdeluokan tiedot otetaan
     * @example
     * <pre name="test">
     *   Kohdeluokka kohdeluokka = new Kohdeluokka();
     *   kohdeluokka.parse("   11   |  10  |   Pluto    ");
     *   kohdeluokka.getKohdeluokanNro() === 11;
     *   kohdeluokka.toString()    === "11|10|Pluto";
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }


    /**
     * Testataan toimintaa.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Kohdeluokka planeetat = new Kohdeluokka();
        planeetat.vastaaPlaneetat(1);
        planeetat.tulosta(System.out);
    }

}