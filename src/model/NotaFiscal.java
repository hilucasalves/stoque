package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class NotaFiscal {
    private static final Logger LOG = getLogger(NotaFiscal.class.getName());
    
    private Long COD_NOTFIS;
    //private int COD_SER;
    //private int COD_VEN;        
    //private int COD_HISSER;
    //private String CPF_CLI;
    //private int COD_PROD;
    //private int COD_PEDCOM;
    private String DTEMI_NOTFIS;
    private double VLR_NOTFIS;

    /**
     *
     */
    public NotaFiscal() {
    }

    /**
     *
     * @return
     */
    public Long getCOD_NOTFIS() {
        return COD_NOTFIS;
    }

    /**
     *
     * @param COD_NOTFIS
     */
    public void setCOD_NOTFIS(Long COD_NOTFIS) {
        this.COD_NOTFIS = COD_NOTFIS;
    }

    /**
     *
     * @return
     */
    public String getDTEMI_NOTFIS() {
        return DTEMI_NOTFIS;
    }

    /**
     *
     * @param DTEMI_NOTFIS
     */
    public void setDTEMI_NOTFIS(String DTEMI_NOTFIS) {
        this.DTEMI_NOTFIS = DTEMI_NOTFIS;
    }

    /**
     *
     * @return
     */
    public double getVLR_NOTFIS() {
        return VLR_NOTFIS;
    }

    /**
     *
     * @param VLR_NOTFIS
     */
    public void setVLR_NOTFIS(double VLR_NOTFIS) {
        this.VLR_NOTFIS = VLR_NOTFIS;
    }

}
