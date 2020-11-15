package harjoitustyo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

import javax.swing.JScrollPane;

/**
 * Dialogi kohdetietojen tulostamiseen.
 * 
 * @author Pasi Hänninen
 * @version 23.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class TulostusDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextArea textArea  = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane(textArea);
    private final JButton buttonTulosta = new JButton("Tulosta");


    /**
     * Luodaan dialogi.
     */
    public TulostusDialog() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 510);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });
        buttonTulosta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    getTextArea().print();
                } catch (PrinterException e) {
                    // e.printStackTrace();
                }
            }
        });
        buttonTulosta.setActionCommand("OK");

        buttonPane.add(buttonTulosta);
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
    }

    
    /**
     * Palautetaan tulostusalue.
     * @return tulostusalue
     */
    public JTextArea getTextArea() {
        return textArea;
    }    
}