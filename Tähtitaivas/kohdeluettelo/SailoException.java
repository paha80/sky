package kohdeluettelo;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * 
 * @author Pasi Hänninen
 * @version 8.7.2015
 * @email pasi.p.hanninen@student.jyu.fi
 */
public class SailoException extends Exception {

    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja, jolle tuodaan poikkeuksessa käytettävä viesti.
     * 
     * @param viesti Poikkeuksen viesti.
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}