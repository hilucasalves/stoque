package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Servico {
    private static final Logger LOG = getLogger(Servico.class.getName());
   
    private Long COD_SER;
    private String DES_SER;
    private double VLR_SER;

    /**
     *
     */
    public Servico() {
    
    }

    /**
     *
     * @return
     */
    public Long getCOD_SER() {
        return COD_SER;
    }

    /**
     *
     * @param COD_SER
     */
    public void setCOD_SER(Long COD_SER) {
        this.COD_SER = COD_SER;
    }

    /**
     *
     * @return
     */
    public String getDES_SER() {
        return DES_SER;
    }

    /**
     *
     * @param DES_SER
     */
    public void setDES_SER(String DES_SER) {
        this.DES_SER = DES_SER;
    }

    /**
     *
     * @return
     */
    public double getVLR_SER() {
        return VLR_SER;
    }

    /**
     *
     * @param VLR_SER
     */
    public void setVLR_SER(double VLR_SER) {
        this.VLR_SER = VLR_SER;
    }

}
