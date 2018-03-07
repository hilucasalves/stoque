
package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class ItemProduto {
    private static final Logger LOG = getLogger(ItemProduto.class.getName());
   
   private int QTD_ITEMPROD;
   private double VLR_ITEMPROD;

    /**
     *
     */
    public ItemProduto() {
   }

    /**
     *
     * @return
     */
    public int getQTD_ITEMPROD() {
        return QTD_ITEMPROD;
    }

    /**
     *
     * @param QTD_ITEMPROD
     */
    public void setQTD_ITEMPROD(int QTD_ITEMPROD) {
        this.QTD_ITEMPROD = QTD_ITEMPROD;
    }

    /**
     *
     * @return
     */
    public double getVLR_ITEMPROD() {
        return VLR_ITEMPROD;
    }

    /**
     *
     * @param VLR_ITEMPROD
     */
    public void setVLR_ITEMPROD(double VLR_ITEMPROD) {
        this.VLR_ITEMPROD = VLR_ITEMPROD;
    }
}
