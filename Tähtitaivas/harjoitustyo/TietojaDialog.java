package harjoitustyo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.Component;

/**
 * Dialogi, joka kertoo tietoja ohjelmasta.
 * 
 * @author Pasi Hänninen
 * @version 30.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 **/
public class TietojaDialog extends JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
    private final JLabel lblPasiHanninen = new JLabel("Pasi Hänninen");


    /**
     * Käynnistetään dialogi.
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        try {
            TietojaDialog dialog = new TietojaDialog();
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Luodaan dialogi.
     */
    public TietojaDialog() {
        setTitle("Tietoja ohjelmasta");
        setBounds(100, 100, 240, 150);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        {
            JLabel lblTietoja = new JLabel("Tähtitaivaan kohdeluettelo");
            lblTietoja.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTietoja.setHorizontalAlignment(SwingConstants.CENTER);
            lblTietoja.setHorizontalTextPosition(SwingConstants.CENTER);
            lblTietoja.setBounds(new Rectangle(50, 10, 0, 0));
            contentPanel.add(lblTietoja);
        }
        {
            JLabel lblVersio = new JLabel("versio 0.9 (30.7.2015)  ");
            lblVersio.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(lblVersio);
        }
        lblPasiHanninen.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(lblPasiHanninen);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Sulje");
                okButton.addActionListener((e) -> suljeIkkuna());
                okButton.setActionCommand("Sulje");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
    }


    /**
      * Ikkunan sulkeminen.
      */
    private void suljeIkkuna() {
        dispose();
    }
}
