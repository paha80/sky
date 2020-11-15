package harjoitustyo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import kohdeluettelo.Kohde;
import kohdeluettelo.SailoException;
import harjoitustyo.TahtitaivasSwing;
import harjoitustyo.PaivaysSwing;
import fi.jyu.mit.gui.EditPanel;
import fi.jyu.mit.gui.AbstractChooser;
import fi.jyu.mit.gui.ComboBoxChooser;
import fi.jyu.mit.gui.GenListChooser;
import fi.jyu.mit.gui.StringTable;

/**
 * Ohjelman käyttöliittymä tehtynä WindowBuilderillä. Varsinaiset toiminnot siirretty TahtitaivasSwing- sekä PaivaysSwing-luokkiin,
 * tästä luokasta tehdään vain kutsuja niihin. 
 * 
 * @author Pasi Hänninen
 * @version 30.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 * 
 * Virheilmoitus vääränlaisesta syötteestä kenttään toteuttamatta, tosin ei välttämättä tarvittava ominaisuus, koska kohteiden
 * tiedot toimivat myös ihan String -muodossakin. Aiemmassa vaiheessa virheilmoitus oli toiminnassa ilmoittamassa toimimattomasta
 * haku-ominaisuudesta.
 * 
 * TODO: Haun toteuttaminen fiksummin. Kohteille lisätään myös kohdekuvat. Kohdeluokkien esityksen muuttaminen taulukkomallista
 * ehkäpä listaksi, tätä mietittävä. Tässä kehityssuuntia jatkokehitystä varten.
 */
public class TahtitaivasGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static String kohdeluettelonNimi = "kohteet";

    /** Tahtitaivasswing */
    protected final TahtitaivasSwing tahtitaivasswing;
    /** PaivaysSwing */
    protected static PaivaysSwing paivaysswing;
    /** Kanttä, johon kirjoitetaan hakuehto */
    public JTextField editHaku = new JTextField();
    /** Paneli, johon kohteen tiedot luodaan */
    public final JPanel panelKohde = new JPanel();
    /** Paneli, johon kohteen kentät on tarkoitus laittaa */
    public final Box boxKohde = Box.createVerticalBox(); 
    /** teksti, johon virheilmoitukset tulevat */
    public final JLabel labelVirhe = new JLabel(" ");
    /** Lista, jossa on kohteiden nimet */
    public final AbstractChooser<Kohde> listKohteet = new GenListChooser<Kohde>();
    /** Lista, josta voidaan valita etsittävä kenttä */
    public final ComboBoxChooser cbKentat = new ComboBoxChooser();
    /** taulukko, jossa kohteen kohdeluokka */
    public final StringTable tableKohdeluokat = new StringTable();
	
    private JPanel contentPane;	
	private JPanel panelLista = new JPanel();
	private JPanel panelHaku = new JPanel();
	private JLabel labelHakuehto = new JLabel("Hakuehto");
	    
    private JPanel panelNapit = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu mnTiedosto = new JMenu("Tiedosto");
    private JMenuItem mntmTallenna = new JMenuItem("Tallenna");
    private JMenuItem mntmAvaa = new JMenuItem("Avaa");
    private JMenuItem mntmTulosta = new JMenuItem("Tulosta");
    private JMenuItem mntmLopeta = new JMenuItem("Lopeta");
    private JButton btnTallenna = new JButton("Tallenna");
    private JButton btnPoistaKohde = new JButton("Poista kohde");
    
    private JButton btnLisaaKohde = new JButton("Lisää kohde");
 
    private JMenu mnMuokkaa = new JMenu("Muokkaa");
    private JMenu mnApua = new JMenu("Apua");
    private JMenuItem mntmLisaakohde = new JMenuItem("Lisää kohde");
    private JMenuItem mntmLisaakohdeluokka = new JMenuItem("Lisää kohdeluokka");
    private JMenuItem mntmPoistakohde = new JMenuItem("Poista kohde");
    private JMenuItem mntmPoistakohdeluokka = new JMenuItem("Poista kohdeluokka");
    private JMenuItem mntmApua = new JMenuItem("Apua");
    private JMenuItem mntmTietoja = new JMenuItem("Tietoja");
    
    private final JScrollPane scrollKohde = new JScrollPane(panelKohde);
    private final JPanel panelKohdeTiedot = new JPanel();
    private final JLabel labelKohdeTiedot = new JLabel("Kohteen tiedot");
    private final JPanel panelKohdeluokat = new JPanel();
    private final JLabel labelKohdeluokat = new JLabel("Kohteen luokitus");
    
    private final JSplitPane splitPaneListaKohdetiedot = new JSplitPane();
    private final JSplitPane splitPaneKohdeKohdeluokat = new JSplitPane();

    private EditPanel editPanelNimi = new EditPanel();
    private EditPanel editPanelLoytajat = new EditPanel();
    private EditPanel editPanelLoytoaika = new EditPanel();
    private EditPanel editPanelKeskietaisyys = new EditPanel();
    private EditPanel editPanelKiertoaika = new EditPanel();
    private EditPanel editPanelKiertoratanopeus = new EditPanel();
    private EditPanel editPanelKuidenLKM = new EditPanel();
    private EditPanel editPanelKuidenNimet = new EditPanel();
    private EditPanel editPanelHalkaisija = new EditPanel();
    private EditPanel editPanelPintaAla = new EditPanel();
    private EditPanel editPanelMassa = new EditPanel();
    private EditPanel editPanelKeskitiheys = new EditPanel();
    private EditPanel editPanelPyorahdysaika = new EditPanel();
    private EditPanel editPanelLampotila = new EditPanel();
    private EditPanel editPanelLisatietoa = new EditPanel();
    
    private final Action actionUusiKohde = new ActionUusiKohde();
    private final Action actionPoistaKohde = new ActionPoistaKohde();
    private final Action actionTalleta = new ActionTalleta();
    private final Action actionUusiKohdeluokka = new ActionUusiKohdeluokka();
    private final Action actionPoistaKohdeluokka = new ActionPoistaKohdeluokka();
    private final JMenuItem mntmLisaaKohdeluokka = new JMenuItem("Lisää kohdeluokka");
    private final JButton btnLisaaKohdeluokka = new JButton("Lisää kohdeluokka");
    private final JButton btnPoistaKohdeluokka = new JButton("Poista kohdeluokka");

    
    /**
     * Käynnistetään ohjelma.
     * @param args ei käytössä
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    TahtitaivasGUI frame = new TahtitaivasGUI();
                    frame.setVisible(true);
                    if (args.length == 0) {
                        if (!frame.avaa()) System.exit(ABORT);
                    } else
                        frame.lueTiedosto(args[0]);
                    		luePaivays();
                    	frame.talletaPaivays();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


	/**
     * Create the frame.
     */
    public TahtitaivasGUI() {
    	tahtitaivasswing = new TahtitaivasSwing(this);
    	paivaysswing = new PaivaysSwing(this);
    	
    	addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                tahtitaivasswing.talleta();
            }
        });
    	
        setTitle("Tähtitaivaan kohdeluettelo v0.9");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 537);
        
        setJMenuBar(menuBar);

        menuBar.add(mnTiedosto);
        
        mntmTallenna.setAction(actionTalleta);
        mntmTallenna.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mnTiedosto.add(mntmTallenna);

        mntmAvaa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                avaa();
            }
        });
        mntmAvaa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        mnTiedosto.add(mntmAvaa);
        
        mntmTulosta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tulosta();
            }
        });
        mntmTulosta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        mnTiedosto.add(mntmTulosta);
        
        mntmLopeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                lopeta();
            }
        });
        mntmLopeta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        mnTiedosto.add(mntmLopeta);
        
        menuBar.add(mnMuokkaa);

        mntmLisaakohde.setAction(actionUusiKohde);
        mntmLisaakohde.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        mnMuokkaa.add(mntmLisaakohde);

        mntmPoistakohde.setAction(actionPoistaKohde);
        mntmPoistakohde.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        mnMuokkaa.add(mntmPoistakohde);
        
        mntmLisaaKohdeluokka.setAction(actionUusiKohdeluokka);
        mntmLisaakohdeluokka.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
        mnMuokkaa.add(mntmLisaaKohdeluokka);
        
        mntmPoistakohdeluokka.setAction(actionPoistaKohdeluokka);
        mnMuokkaa.add(mntmPoistakohdeluokka);

        menuBar.add(mnApua);
        
        mnApua.add(mntmApua);
        mntmApua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tahtitaivasswing.avustus();
            }
        });

        mnApua.add(mntmTietoja);
        mntmTietoja.addActionListener((e) -> tahtitaivasswing.avaaTietoja());
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        contentPane.add(panelNapit, BorderLayout.SOUTH);
        
        btnTallenna.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnTallenna.setAction(actionTalleta);
        panelNapit.add(btnTallenna);
        
        btnLisaaKohde.setAction(actionUusiKohde);
        panelNapit.add(btnLisaaKohde);
        
        btnPoistaKohde.setAction(actionPoistaKohde);
        panelNapit.add(btnPoistaKohde);
        
        panelNapit.add(btnLisaaKohdeluokka);
        btnLisaaKohdeluokka.setAction(actionUusiKohdeluokka);
        
        panelNapit.add(btnPoistaKohdeluokka);
        btnPoistaKohdeluokka.setAction(actionPoistaKohdeluokka);
        
        contentPane.add(splitPaneListaKohdetiedot, BorderLayout.CENTER);
        labelHakuehto.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelHakuehto.setLabelFor(editHaku);
        splitPaneListaKohdetiedot.setLeftComponent(panelLista);
        panelLista.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelLista.setLayout(new BorderLayout(0, 0));
        panelHaku.setBorder(new LineBorder(new Color(0, 0, 0)));

        panelLista.add(panelHaku, BorderLayout.NORTH);
        panelHaku.setLayout(new BoxLayout(panelHaku, BoxLayout.Y_AXIS));
        panelHaku.add(labelHakuehto);
        cbKentat.getCaptionLabel().setPreferredSize(new Dimension(31, 0));
        cbKentat.setSelectedIndex(0);
        cbKentat.setKohteet(new String[] { "nimi", "halkaisija", "keskietäisyys" });
        panelHaku.add(cbKentat);
        editHaku.setToolTipText("Kirjoita hakuehto");

        panelHaku.add(editHaku);
        editHaku.setColumns(10);
        listKohteet.getCaptionLabel().setText("Kohteet");
        listKohteet.getCaptionLabel().setHorizontalTextPosition(SwingConstants.CENTER);
        listKohteet.getCaptionLabel().setHorizontalAlignment(SwingConstants.CENTER);
        listKohteet.setPreferredSize(new Dimension(150, 148));
        
        panelLista.add(listKohteet, BorderLayout.CENTER);

        editHaku.requestFocus();
        splitPaneListaKohdetiedot.setRightComponent(splitPaneKohdeKohdeluokat);
        splitPaneKohdeKohdeluokat.setResizeWeight(1.0);
        splitPaneKohdeKohdeluokat.setRightComponent(panelKohdeluokat);
        panelKohdeluokat.setMaximumSize(new Dimension(200, 32767));
        panelKohdeluokat.setLayout(new BorderLayout(0, 0));
        panelKohdeluokat.add(labelKohdeluokat, BorderLayout.NORTH);
        labelKohdeluokat.setHorizontalAlignment(SwingConstants.CENTER);
        tableKohdeluokat.getTable().setModel(
        		new DefaultTableModel(new Object[][] { { "planeetta"  }, }, new String[] { "kohdeluokka" }));
        tableKohdeluokat.getTable().setPreferredScrollableViewportSize(new Dimension(150, 200));
        tableKohdeluokat.setMinimumSize(new Dimension(120, 24));

        panelKohdeluokat.add(tableKohdeluokat, BorderLayout.CENTER);
        splitPaneKohdeKohdeluokat.setLeftComponent(panelKohdeTiedot);
        panelKohdeTiedot.setLayout(new BorderLayout(0, 0));
        panelKohdeTiedot.add(scrollKohde);
        labelKohdeTiedot.setHorizontalAlignment(SwingConstants.CENTER);

        panelKohdeTiedot.add(labelKohdeTiedot, BorderLayout.NORTH);
        labelVirhe.setOpaque(true);
        labelVirhe.setHorizontalAlignment(SwingConstants.CENTER);

        panelKohdeTiedot.add(labelVirhe, BorderLayout.SOUTH);
        panelKohde.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelKohde.setLayout(new BorderLayout(0, 0));
        
        panelKohde.add(boxKohde, BorderLayout.NORTH);        
 
        boxKohde.add(editPanelNimi);
        
        editPanelLoytajat.setCaption("löytäjät");
        boxKohde.add(editPanelLoytajat);
        
        editPanelLoytoaika.setCaption("löytäaika");
        boxKohde.add(editPanelLoytoaika);
        
        editPanelKeskietaisyys.setCaption("keskietäisyys Auringosta");
        boxKohde.add(editPanelKeskietaisyys);
        
        editPanelKiertoaika.setCaption("kiertoaika Auringon ympäri");
        boxKohde.add(editPanelKiertoaika);
        
        editPanelKiertoratanopeus.setCaption("keskiratanopeus");
        boxKohde.add(editPanelKiertoratanopeus);
        
        editPanelKuidenLKM.setCaption("kuiden lukumäärä");
        boxKohde.add(editPanelKuidenLKM);
        
        editPanelKuidenNimet.setCaption("kuiden nimet");
        boxKohde.add(editPanelKuidenNimet);
        
        editPanelHalkaisija.setCaption("päiväntasaajan halkaisija");
        boxKohde.add(editPanelHalkaisija);
        
        editPanelPintaAla.setCaption("pinta-ala");
        boxKohde.add(editPanelPintaAla);
        
        editPanelMassa.setCaption("massa");
        boxKohde.add(editPanelMassa);
        
        editPanelKeskitiheys.setCaption("keskitiheys");
        boxKohde.add(editPanelKeskitiheys);
               
        editPanelPyorahdysaika.setCaption("pyörähdysaika");
        boxKohde.add(editPanelPyorahdysaika);
        
        editPanelLampotila.setCaption("pinnan lämpötila");
        boxKohde.add(editPanelLampotila);
        
        editPanelLisatietoa.setCaption("lisätietoja");
        boxKohde.add(editPanelLisatietoa);         
    }
    
    /**
     * Alustetaan kohdeluettelon lukemalla sen valitun nimisestä tiedostosta.
     * @param nimi tiedosto, josta luetaan
     */
    protected void lueTiedosto(String nimi) {
        kohdeluettelonNimi = nimi;
        setTitle("Kohdeluettelo - " + kohdeluettelonNimi);
        String virhe = tahtitaivasswing.lueTiedosto(nimi);
        if (virhe != null) JOptionPane.showMessageDialog(this, virhe);
    }
    
    /**
     * Luetaan päivämäärä tiedostosta, ja avataan JOptionPane toivottamaan käyttäjä tervetulleeksi.
     * @throws SailoException jos ongelmia tulee
     */
    protected static void luePaivays() throws SailoException {
    	paivaysswing.luePaivays();
    }

    /**
     * Tallennetaan päivämäärä tiedostoon.
     * @throws SailoException jos ongelmia tulee
     */
    protected void talletaPaivays() throws SailoException {
    	paivaysswing.talletaPaivays();
    }

    /**
     * Lähettää lopettamisviestin.
     */
    protected void lopeta() {
        processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Tulostetaan kohdeluettelon tiedot TulostusDialogiin.
     */
    protected void tulosta() {
        TulostusDialog dialog = new TulostusDialog();
        tahtitaivasswing.tulostaValitut(dialog.getTextArea());
        dialog.setVisible(true);
    }


    /**
     * Talletetaan vanhat tiedot ja kysytään uuden kohdeluettelon nimeä 
     * ja avataan tämä tiedoston.
     * @return false jos nimeä ei anneta
     */
    protected boolean avaa() {
        String uusinimi = AvaaDialog.askName(kohdeluettelonNimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }


    private class ActionUusiKohde extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


        public ActionUusiKohde() {
            putValue(NAME, "Uusi kohde");
            putValue(SHORT_DESCRIPTION, "Lisää uuden kohteen kohdeluetteloon");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            tahtitaivasswing.uusiKohde();
        }
    }
    
    private class ActionPoistaKohde extends AbstractAction {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;


    public ActionPoistaKohde() {
        putValue(NAME, "Poista kohde");
        putValue(SHORT_DESCRIPTION, "Poistaa kohteen kohdeluettelosta");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        tahtitaivasswing.poistaKohde();
    }
}


    private class ActionTalleta extends AbstractAction {
        private static final long serialVersionUID = 1L;


        public ActionTalleta() {
            putValue(NAME, "Tallenna");
            putValue(SHORT_DESCRIPTION, "Tallentaa kohdeluettelon tiedot");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            tahtitaivasswing.talleta();
        }

    }


    private class ActionUusiKohdeluokka extends AbstractAction {
        private static final long serialVersionUID = 1L;


        public ActionUusiKohdeluokka() {
            putValue(NAME, "Lisää kohdeluokka");
            putValue(SHORT_DESCRIPTION, "Lisää kohteelle uuden kohdeluokan");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            tahtitaivasswing.uusiKohdeluokka();
        }
    }


    private class ActionPoistaKohdeluokka extends AbstractAction {
        private static final long serialVersionUID = 1L;


        public ActionPoistaKohdeluokka() {
            putValue(NAME, "Poista kohdeluokka");
            putValue(SHORT_DESCRIPTION, "Poistetaan kohdalla oleva kohdeluokka");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            tahtitaivasswing.poistaKohdeluokka();
        }
    }
    
    
}