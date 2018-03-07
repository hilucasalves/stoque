package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Produto{
    private static final Logger LOG = getLogger(Produto.class.getName());
    
    private long COD_PROD;
    //private int COD_CAT;
    //private int COD_FOR;
    private String NOME_PROD;
    private String MARCA_PROD;
    private int QTD_PROD;
    private double VLRUNID_PROD;
    
    /**
     *
     */
    public Produto() {
    }
    
    public Produto(long COD_PROD, String NOME_PROD) {
        this.COD_PROD = COD_PROD;
        this.NOME_PROD = NOME_PROD;
    }

    /**
     *
     * @return
     */
    public long getCOD_PROD() {
        return COD_PROD;
    }

    /**
     *
     * @param COD_PROD
     */
    public void setCOD_PROD(long COD_PROD) {
        this.COD_PROD = COD_PROD;
    }

    /**
     *
     * @return
     */
    public String getNOME_PROD() {
        return NOME_PROD;
    }

    /**
     *
     * @param NOME_PROD
     */
    public void setNOME_PROD(String NOME_PROD) {
        this.NOME_PROD = NOME_PROD;
    }

    /**
     *
     * @return
     */
    public String getMARCA_PROD() {
        return MARCA_PROD;
    }

    /**
     *
     * @param MARCA_PROD
     */
    public void setMARCA_PROD(String MARCA_PROD) {
        this.MARCA_PROD = MARCA_PROD;
    }

    /**
     *
     * @return
     */
    public int getQTD_PROD() {
        return QTD_PROD;
    }

    /**
     *
     * @param QTD_PROD
     */
    public void setQTD_PROD(int QTD_PROD) {
        this.QTD_PROD = QTD_PROD;
    }

    /**
     *
     * @return
     */
    public double getVLRUNID_PROD() {
        return VLRUNID_PROD;
    }

    /**
     *
     * @param VLRUNID_PROD
     */
    public void setVLRUNID_PROD(double VLRUNID_PROD) {
        this.VLRUNID_PROD = VLRUNID_PROD;
    }
    
    @Override
    public String toString(){
        return NOME_PROD;
    }
}