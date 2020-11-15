package harjoitustyo;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import kohdeluettelo.Kohdeluettelo;
import kohdeluettelo.Kohdeluokka;
import kohdeluettelo.Kohde;
import kohdeluettelo.SailoException;
import fi.jyu.mit.gui.AbstractChooser;
import fi.jyu.mit.gui.EditPanel;
import fi.jyu.mit.gui.IStringTable;
import fi.jyu.mit.gui.StringTable;
import fi.jyu.mit.gui.TextAreaOutputStream;

/**
 * TahtitaivasSwing -luokka, joka käsittelee kohdeluetteloa Swing -komponenteilla.
 * 
 * @author Pasi Hänninen
 * @version 29.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class TahtitaivasSwing {

	private static Color virheVari = new Color(255, 0, 0);
	private static Color normaaliVari = new Color(255, 255, 255);
    private final static Kohde apukohde = new Kohde(); 
	private TahtitaivasGUI tahtitaivasGUI;

	private final Kohdeluettelo kohdeluettelo;

	private Kohde kohdeKohdalla;
	private Kohde editKohde;      
	private EditPanel editKohdeKentta[]; 


	/**
	* @param tahtitaivasGUI tahtitaivasGUI
	*/
	public TahtitaivasSwing(TahtitaivasGUI tahtitaivasGUI) {
		this.tahtitaivasGUI = tahtitaivasGUI;
		kohdeluettelo = new Kohdeluettelo();
	}

	private JLabel getLabelVirhe() {
		return tahtitaivasGUI.labelVirhe;
	}

	private AbstractChooser<String> getCbKentat() {
		return tahtitaivasGUI.cbKentat;
	}

	private JTextField getEditHaku() {
		return tahtitaivasGUI.editHaku;
	}

	private AbstractChooser<Kohde> getListKohteet() {
		return tahtitaivasGUI.listKohteet;
	}

	private StringTable getTableKohdeluokat() {
		return tahtitaivasGUI.tableKohdeluokat;
	}

	/** @return alue johon lisätään kohteen yksittäiset kentät.  */
	private JComponent getPanelKohde() {
		return tahtitaivasGUI.boxKohde;
	}


	/**
	*  Avaa TietojaDialog -ikkunan.
	*/
	public void avaaTietoja() {
		TietojaDialog dialog = new TietojaDialog();
		dialog.setModal(true);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
     * Alustetaan valitut alueet käyttökuntoon.
     */
    private void alusta() {
        getCbKentat().clear();
        for (int k = apukohde.ekaKentta(); k < apukohde.getKenttia(); k++) {
            getCbKentat().add(apukohde.getKysymys(k));
        }
        getCbKentat().setSelectedIndex(0);
        
        getEditHaku().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                hae(0);
            }
        });

        getCbKentat().addSelectionChangeListener( (e) -> hae(-1) );
        getListKohteet().addSelectionChangeListener( (e) -> naytaKohde() );
        
        getTableKohdeluokat().addTableEditListener((sender, row, column, s)  -> setKohdeluokka(sender,row,column,s));        
        
        luoNaytto();
    }

	
	/**
     * Asetetaan editoitava kohde.
     * @param k uusi viite editKohteelle
     */
    private void setEditKohde(Kohde k) {
        editKohde = k;
    }

    
    /**
     * Asetetaan editoitava kohde.
     * @param k uusi viite editKohteelle
     */
    private void setKohdeKohdalla(Kohde k) {
        int kohdenro = -1;
        if ( k != null ) kohdenro = k.getKohdeNro();
        tarkistaMuutos(kohdenro);
        kohdeKohdalla = k;
        setEditKohde(null);
    }

    
    /**
     * Tutkitaan onko editoitavaan kohteeseen tehty muutoksia, jotka kannattaa tallentaa.
     * @return false jos ei muutoksia
     */
    private boolean muuttunut() {
        if (kohdeKohdalla == null) return false;
        if (editKohde == null) return false;
        return !kohdeKohdalla.equals(editKohde);
    }
	
	/**
     * Tarkistetaan onko kohteen tiedot muuttuneet ja jos on, kysytään halutaanko tallentaa.
     * @param kohdenro mikä kohde aktiiviseksi muutoksen jälkeen
     */
    public void tarkistaMuutos(int kohdenro) {
        if (muuttunut()) {
            int vastaus = JOptionPane.showConfirmDialog(tahtitaivasGUI, "Tallennetaanko?", "Kohde muuttunut!", JOptionPane.YES_NO_OPTION);
            if (vastaus == JOptionPane.YES_OPTION) talleta(kohdenro);
        }
    }
    
    
    /**
     * Luodaan uusi kohde, jota aletaan editoimaan.
     */
    private void luoUusiKohde() {
        Kohde uusi = new Kohde();
        uusi.rekisteroi();
        setKohdeKohdalla(uusi);
    }



	/**
	 * Tekee uuden tyhjän kohteen editointia varten.
	 */
	public void uusiKohde() {
		luoUusiKohde();
        laitaKohde();
        getListKohteet().clearSelection();
    }

	/**
     * Tekee uuden tyhjän kohdeluokan editointia varten.
     */
    public void uusiKohdeluokka() {
        if ( kohdeKohdalla == null ) return;
        tarkistaMuutos(kohdeKohdalla.getKohdeNro());
        Kohdeluokka kohdeluokka = new Kohdeluokka(kohdeKohdalla.getKohdeNro());
        kohdeluokka.rekisteroi();
        try {
            kohdeluettelo.lisaa(kohdeluokka);
        } catch (SailoException e) {
            JOptionPane.showMessageDialog(tahtitaivasGUI, e.getMessage()); 
        }
        naytaKohdeluokat();
    }

    
    /**
     * Näytetään kohdeluokat taulukkoon.
     */
    protected void naytaKohdeluokat() {
        if ( kohdeKohdalla == null ) return;
        getTableKohdeluokat().clear();
        getTableKohdeluokat().getTable().getColumnModel().getColumn(0).setMinWidth(100);
        try {        
            List<Kohdeluokka> kohdeluokat = kohdeluettelo.annaKohdeluokat(kohdeKohdalla);
            for (Kohdeluokka koh : kohdeluokat) {
                int r = getTableKohdeluokat().addRow();
                getTableKohdeluokat().setObjectAt(koh, r);
                for (int k = koh.ekaKentta(); k < koh.getKenttia(); k++) {
                    String jono = koh.anna(k);
                    getTableKohdeluokat().setValueAtModel(jono, r, k - koh.ekaKentta());
                }
            }
        } catch (SailoException ex) {
            JOptionPane.showMessageDialog(tahtitaivasGUI, "Kohdeluokkien hakemisessa ongelmia! " + ex.getMessage());
        }  
    }


	/**
	 * Luetaan kohteen tiedot tiedostosta. 
	 * @param s tiedoston hakemiston nimi
	 * @return null jos onnistuu, muuten virheilmoitus
	 */
	public String lueTiedosto(String s) {
		try {
			alusta();
			kohdeluettelo.lueTiedostosta(s);
			hae(0);
			return null;
		} catch (SailoException e) {
			hae(0);
			// uusiKohde();
			return e.getMessage();
		}
	}
	
	/**
     * Asetetaan kohdeluokkaan uusi arvo.
     * @param sender mistä taulukosta pyyntö tuli
     * @param row miltä riviltä
     * @param column mistä sarakkeesta
     * @param s mitä haluttiin laittaa
     * @return null jos ok, muuten virhe
     */
    public String setKohdeluokka(IStringTable sender, int row, int column, Object s) {
        Kohdeluokka koh = (Kohdeluokka)sender.getObjectAt(row);
        if ( koh == null ) return "Ei kohdeluokkaa";
        String virhe = koh.aseta(column+koh.ekaKentta(), s.toString());
        if ( virhe == null ) kohdeluettelo.setKohdeluokkaMuutos();
        return virhe;
    }    

	/**
     * Tallettaa nykyisen mahdollisesti muutetun kohteen ja sitten koko tiedoston.
     * @param kohdenro Mikä kohde aktiiviseksi tallennuksen jälkeen. -1 = nykyinen valittu kohde.
     * @return null jos menee hyvin, muuten virheteksti
     */
    public String talleta(int kohdenro) {
        int kn = kohdenro;
        try {
            if (muuttunut()) {
                kohdeluettelo.korvaaTaiLisaa(editKohde);
                kohdeKohdalla = editKohde;
                if (kohdeKohdalla != null && kohdenro < 0) {
                    kn = kohdeKohdalla.getKohdeNro();
                }
                hae(kn);
            }
            setEditKohde(null);
            kohdeluettelo.talleta();
            return null;
        } catch (SailoException ex) {
            JOptionPane.showMessageDialog(tahtitaivasGUI, "Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }
    
    
    /**
     * Tallettaa nykyisen mahdollisesti muutetun kohteen ja sitten koko tiedoston.
     * @return null jos menee hyvin, muuten virheteksti
     */
    public String talleta() {
        return talleta(-1);
    }
    

    /**
     * Suorittaa niiden kohteiden hakemisen, joiden valittu kenttä täyttää hakuehdon.
     * @param knro kohteen numero, joka aktivoidaan haun jälkeen, -1 = aktivoidaan nykyinen kohde
     */
    protected void hae(int knro) {
        int jn = knro;
        if ( jn < 0 && kohdeKohdalla != null ) jn = kohdeKohdalla.getKohdeNro();
        
        int k = getCbKentat().getSelectedIndex() + apukohde.ekaKentta(); 
        String ehto = getEditHaku().getText();
        if (ehto.indexOf('*') < 0) {
            ehto = "*" + ehto + "*";
        }
       
        getListKohteet().clear();
        int index = 0;
        Collection<Kohde> Kohteet;
        try {
            Kohteet = kohdeluettelo.etsi(ehto, k);
            int i = 0;
            for (Kohde Kohde:Kohteet) {
                if (Kohde.getKohdeNro() == jn) index = i;
                getListKohteet().add(Kohde.getNimi(),Kohde);
                i++;
            }
        } catch (SailoException ex) {
            JOptionPane.showMessageDialog(tahtitaivasGUI, "Kohteen hakemisessa ongelmia! " + ex.getMessage());
        }

        getListKohteet().setSelectedIndex(index); 
    }


	/**
	 * Laittaa kohteen tiedot kohteen alueeseen.
	 */
	private void laitaKohde() {
	        if (kohdeKohdalla == null) return;
	        for (int i = 0, k = kohdeKohdalla.ekaKentta(); k < kohdeKohdalla.getKenttia(); k++, i++) {
	        	editKohdeKentta[i].setLabelWidth(175);
	            editKohdeKentta[i].setText(kohdeKohdalla.anna(k));
	            editKohdeKentta[i].getEdit().setBackground(normaaliVari);
	            editKohdeKentta[i].setToolTipText("");
	        }
	        naytaKohdeluokat();
	}
    

	/**
	 * Näyttää listasta valitun kohteen tiedot.
	 */
	protected void naytaKohde() {
		int ind = getListKohteet().getSelectedIndex();
		if (ind < 0)
			return;
		kohdeKohdalla = getListKohteet().getSelectedObject();
		laitaKohde();
	}
	
	/**
     * Käsittelee edit-kenttään tulleen muutoksen kohteeseen.
     * Mikäli kohdetta ei vielä ole editoitu, luodaan kohteesta
     * kopio editKohde-olioon ja muutokset kohdistetaan tähän
     * kohteeseen.  Näin voidaan muutoksia verrata kohde-olioon
     * ja tunnistetaan muutostarpeet.  Jos muutosta ei voida
     * sijoittaa kohteeseen, muutetaan taustaväri punaiseksi.
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosKohteeseen(JTextField edit) {
        if (kohdeKohdalla == null) luoUusiKohde();
        if (editKohde == null)
            try {
                setEditKohde(kohdeKohdalla.clone());
            } catch (CloneNotSupportedException e) { // virhettä ei tule
            }
        String s = edit.getText();
        int k = Integer.parseInt(edit.getName().substring(2));
        String virhe = editKohde.aseta(k, s);
        setVirhe(virhe);
        if (virhe == null) {
            edit.setToolTipText("");
            edit.setBackground(normaaliVari);
        } else {
            edit.setToolTipText(virhe);
            edit.setBackground(virheVari);
        }
    }
    

	/**
	 * Laitetaan virheilmoitus näkyville jos labelVirhe on alustettu.
	 * Mikäli virhettä ei ole, niin piilotetaan virheilmoitus.    
	 * @param virhe virheteksti
	 */
	private void setVirhe(String virhe) {
		if (getLabelVirhe() == null)
			return;
		if (virhe == null) {
			getLabelVirhe().setVisible(false);
			return;
		}
		getLabelVirhe().setVisible(true);
		getLabelVirhe().setText(virhe);
		getLabelVirhe().setBackground(virheVari);
	}

	/**
     * Luo panelKohteeseen paikan, johon kohteen tiedot voi tulostaa.
     */
    private void luoNaytto() {
        Container panelJasen = getPanelKohde();
        panelJasen.removeAll();
        int n = apukohde.getKenttia() - apukohde.ekaKentta();
        editKohdeKentta = new EditPanel[n];

        for (int i = 0, k = apukohde.ekaKentta(); k < apukohde.getKenttia(); k++, i++) {
            EditPanel edit = new EditPanel(); // NOPMD
            edit.setCaption(apukohde.getKysymys(k));
            editKohdeKentta[i] = edit;
            edit.setName("ej" + k);
            edit.getEdit().setName("tj" + k);
            panelJasen.add(edit);
            edit.addKeyListener(new KeyAdapter() { // NOPMD
                @Override
                public void keyReleased(KeyEvent e) {
                    kasitteleMuutosKohteeseen((JTextField)e.getComponent());
                }
            });

        }
        
    }

	/**
	* Poistetaan kohdeluokkataulukosta valitulla kohdalla oleva kohdeluokka.
	*/
	public void poistaKohdeluokka() {
		int rivi = getTableKohdeluokat().getSelectedRow();
        if ( rivi < 0 ) return;
        Kohdeluokka kohdeluokka = (Kohdeluokka)getTableKohdeluokat().getSelectedObject();
        if ( kohdeluokka == null ) return;
        kohdeluettelo.poistaKohdeluokka(kohdeluokka);
        naytaKohdeluokat();
        if ( rivi >= getTableKohdeluokat().getRowCount() ) rivi = getTableKohdeluokat().getRowCount() - 1;
        getTableKohdeluokat().selectCell(rivi, 0);
    }


	/**
     * Poistetaan listasta valittu kohde.
     */
    public void poistaKohde() {
        if ( kohdeKohdalla == null ) return;
        int vastaus = JOptionPane.showConfirmDialog(tahtitaivasGUI, "Poistetaanko kohde: " + kohdeKohdalla.getNimi(), "Poisto?", JOptionPane.YES_NO_OPTION);
        if ( vastaus == JOptionPane.NO_OPTION ) return;
        kohdeluettelo.poista(kohdeKohdalla.getKohdeNro());
        int index = getListKohteet().getSelectedIndex();
        hae(0);
        getListKohteet().setSelectedIndex(index);
    }

    /**
     * Avataan ulkoinen selain näyttämään avustustekstiä.
     */
    public void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://trac.cc.jyu.fi/projects/ohj2ht/wiki/k2015/suunnitelmat/papehann");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }
	
	/**
	 * Tulostetaan kohteen tiedot.
	 * @param os tietovirta johon tulostetaan
	 * @param kohde tulostettava kohde
	 */
	public void tulosta(PrintStream os, final Kohde kohde) {
		os.println("----------------------------------------------");
		kohde.tulosta(os);
		os.println("----------------------------------------------");
		try {
			List<Kohdeluokka> kohdeluokat = kohdeluettelo
					.annaKohdeluokat(kohde);
			for (Kohdeluokka koh : kohdeluokat)
				koh.tulosta(os);
		} catch (SailoException ex) {
			JOptionPane.showMessageDialog(tahtitaivasGUI,
					"Hakemisessa ongelmia! " + ex.getMessage());
		}
	}

	/**
	 * Tulostetaan listassa olevat kohteet tekstialueeseen.
	 * @param text alue, johon tulostetaan
	 */
	public void tulostaValitut(JTextArea text) {
		try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
			os.println("Tulostetaan kaikki kohteet");
			Collection<Kohde> kohteet = kohdeluettelo.etsi("", -1);
			for (Kohde kohde : kohteet) {
				tulosta(os, kohde);
				os.println("\n\n");
			}
		} catch (SailoException ex) {
			JOptionPane.showMessageDialog(tahtitaivasGUI,
					"Kohteen hakemisessa ongelmia! " + ex.getMessage());
		}
	}
}
