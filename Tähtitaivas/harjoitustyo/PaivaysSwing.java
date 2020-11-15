package harjoitustyo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import harjoitustyo.TahtitaivasGUI;
import kohdeluettelo.SailoException;

/**
 * PaivaysSwing -luokka käsittelee päiväystä ohjelmassa. Ohjelma tallentaa jokaista käyttökertaa vastaavan päivämäärän
 * paivays.dat -tiedostoon, joka luetaan ja näytetään käyttäjälle ohjelmaa käynnistäessä.
 * 
 * @author Pasi Hänninen
 * @version 29.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class PaivaysSwing {
	
	final static String OIKEA_PAIVAYS = "dd. MMM yyyy";
	
	private TahtitaivasGUI tahtitaivasGUI;
	
	/**
	* @param tahtitaivasGUI tahtitaivasGUI
	*/
	public PaivaysSwing(TahtitaivasGUI tahtitaivasGUI) {
		this.tahtitaivasGUI = tahtitaivasGUI;
	}
	
	/**
	 * Otetaan "ylös" päivämäärä.
     * @param paivays päivämäärä
     * @return palautetaan päivämäärä merkkijonona
     */
    public static String Paivays(Date paivays) {
    	String paivaysJonona = null;
    	   SimpleDateFormat sdfr = new SimpleDateFormat(OIKEA_PAIVAYS);
    	   try{
    	paivaysJonona = sdfr.format( paivays );
    	   }catch (Exception ex ){
    		System.out.println(ex);
    	   }
    	   return paivaysJonona;
    }
    
    /**
     * Tarkistaja päiväyksen testaamiseen. Oikea muoto on "dd. MMM yyyy".
     * @param testattavaPaivays päiväys, jota testataan.
     * @return true, jos oikeassa muodossa, ja false, jos väärässä
     */
    public boolean onkoOikeaPaivays(String testattavaPaivays) {
        SimpleDateFormat sdfr = new SimpleDateFormat(OIKEA_PAIVAYS);
        try {
            sdfr.parse(testattavaPaivays);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
	
    /**
     * Luetaan tiedostosta sinne tallennettu päivämäärä ja tarkistetaan sen muoto.
     * Näytetään JOptionPanessa milloin ohjelmaa on käytetty viimeksi, tai jos ei ole,
     * toivotetaan käyttäjä tervetulleeksi.     
     * @throws SailoException jos ongelmia tulee
     */
    public void luePaivays() throws SailoException {
        String pvm = "";
        int apu = 1;
    	try ( BufferedReader fi = new BufferedReader(new FileReader("paivays.dat")) ) {
        	String rivi;
            while ( (rivi = fi.readLine()) != null ) {
            	if ( onkoOikeaPaivays(rivi) == true ) pvm = rivi;
            }
        } catch ( FileNotFoundException e ) {
        	JOptionPane.showMessageDialog(tahtitaivasGUI,
            	    "Taisit käynnistää ohjelman ensimmäistä kertaa. Tervetuloa käyttämään!",
            	    "Hei!", JOptionPane.PLAIN_MESSAGE);
        			apu = 0;
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    	if ( apu != 0) JOptionPane.showMessageDialog(tahtitaivasGUI,
        	    "Käytit ohjelmaa viimeksi " + pvm,
        	    "Hei taas!", JOptionPane.PLAIN_MESSAGE);
    }
    
	
	/**
	 * Tallennetaan päivämäärä tiedostoon.
	 * @throws SailoException jos ongelmia tulee
	 */
	public void talletaPaivays() throws SailoException {
        File ftied = new File("paivays.dat");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
        	Date paivays = new Date();
        	fo.println(Paivays(paivays));
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied + " kirjoittamisessa ongelmia");
        }
    }
	
}