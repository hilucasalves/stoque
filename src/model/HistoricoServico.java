package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class HistoricoServico {
    private static final Logger LOG = getLogger(HistoricoServico.class.getName());
    
    private String COD_HISSER;
    //private int COD_SER;
    private String DES_HISSER;
    //private String CPF_CLI;
    //private int COD_VEN;
    
    /**
     *
     */
    public HistoricoServico() {
        
    }

    /**
     *
     * @return
     */
    public String getCOD_HISSER() {
        return COD_HISSER;
    }

    /**
     *
     * @param COD_HISSER
     */
    public void setCOD_HISSER(String COD_HISSER) {
        this.COD_HISSER = COD_HISSER;
    }

    /**
     *
     * @return
     */
    public String getDES_HISSER() {
        return DES_HISSER;
    }

    /**
     *
     * @param DES_HISSER
     */
    public void setDES_HISSER(String DES_HISSER) {
        this.DES_HISSER = DES_HISSER;
    }

}
