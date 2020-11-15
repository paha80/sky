package harjoitustyo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;

import fi.jyu.mit.gui.EditPanel;

/**
 * Dialogi, jossa kysytään avattavan kohdeluettelon nimeä.
 * 
 * @author Pasi Hänninen
 * @version 29.7.2013
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class AvaaDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JPanel contentPanel = new JPanel();
    private EditPanel editPanel = new EditPanel();

    /**
     * Käynnistetään dialogi.
     * @param defName ehdotettava nimi
     * @return käyttäjän antama nimi
     */
    public static String askName(String defName) {
        String nimi = null;
        try {
            AvaaDialog dialog = new AvaaDialog(null);
            dialog.editPanel.setText(defName);
            dialog.setVisible(true);
            nimi = dialog.getResult();
            dialog.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nimi;
    }


    private String result = null;
    private final JLabel lblVersio = new JLabel("v0.9");
    private final JLabel lblTekija = new JLabel("Pasi Hänninen");


    /**
     * Palautetaan dialogista saatu tulos, null jos poistuttiin.
     * @return dialogiin kirjoitettu jono, null jos lopetettiin
     */
    public String getResult() {
        return result;
    }


    /**
     * Apumetodi tuloksen asettamiseksi.
     * @param value tulokselle laitettava arvo
     */
    protected void setResult(String value) {
        result = value;
    }

    
    /**
     * Palautetaan editPanel sisäluokille.
     * @return editPanel
     */
    protected EditPanel getEditPanel() {
        return editPanel;
    }

    
    /**
     * Kohdeluettelon nimen kysyvä dialogi.
     * @param parent kenelle modaalinen
     */
    public AvaaDialog(Frame parent) {
        super(parent,"Nimi",true);
        setTitle("Tähtitaivaan kohdeluettelo v0.9");
        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setBounds(100, 100, 371, 268);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        getContentPane().add(contentPanel, BorderLayout.SOUTH);
        contentPanel.setLayout(new BorderLayout(0, 0));
        editPanel.setToolTipText("esim. kohteet.dat");

        editPanel.setColumns(20);
        editPanel.setCaption("Anna kohdeluettelo");
        contentPanel.add(editPanel, BorderLayout.NORTH);
        JPanel buttonPane = new JPanel();
        contentPanel.add(buttonPane, BorderLayout.SOUTH);
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton okButton = new JButton("Lataa");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setResult(getEditPanel().getText());
                setVisible(false); 
            }
        });
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Poistu");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setResult(null);
                setVisible(false);
            }
        });
        cancelButton.setActionCommand("Poistu");
        buttonPane.add(cancelButton);
        
        JPanel panelTiedot = new JPanel();
        getContentPane().add(panelTiedot, BorderLayout.CENTER);
        panelTiedot.setLayout(null);
        
        JLabel lblOhjelmanNimi = new JLabel("Tähtitaivaan kohdeluettelo");
        lblOhjelmanNimi.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblOhjelmanNimi.setBounds(22, 20, 324, 23);
        lblOhjelmanNimi.setFont(new Font("Courier New", Font.BOLD, 20));
        panelTiedot.add(lblOhjelmanNimi);
        lblVersio.setFont(new Font("Courier New", Font.PLAIN, 16));
        lblVersio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblVersio.setBounds(148, 41, 61, 16);
        
        panelTiedot.add(lblVersio);
        lblTekija.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTekija.setFont(new Font("Courier New", Font.BOLD, 16));
        lblTekija.setBounds(114, 93, 140, 16);
        
        panelTiedot.add(lblTekija);
    }
}