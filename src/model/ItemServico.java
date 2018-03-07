
package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class ItemServico {
    private static final Logger LOG = getLogger(ItemServico.class.getName());
    
    private int QTD_ITEMSER;
    private double VLR_ITEMSER;

    /**
     *
     */
    public ItemServico() {
    }

    /**
     *
     * @return
     */
    public int getQTD_ITEMSER() {
        return QTD_ITEMSER;
    }

    /**
     *
     * @param QTD_ITEMSER
     */
    public void setQTD_ITEMSER(int QTD_ITEMSER) {
        this.QTD_ITEMSER = QTD_ITEMSER;
    }

    /**
     *
     * @return
     */
    public double getVLR_ITEMSER() {
        return VLR_ITEMSER;
    }

    /**
     *
     * @param VLR_ITEMSER
     */
    public void setVLR_ITEMSER(double VLR_ITEMSER) {
        this.VLR_ITEMSER = VLR_ITEMSER;
    }

}
