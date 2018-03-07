package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Categoria {
    private static final Logger LOG = getLogger(Categoria.class.getName());

    private long COD_CAT;
    private String DES_CAT;

    public Categoria() {
    }
    
    /**
     *
     * @param COD_CAT
     * @param DES_CAT
     */
    public Categoria(long COD_CAT, String DES_CAT) {
        this.COD_CAT = COD_CAT;
        this.DES_CAT = DES_CAT;
    }
    
    /**
     *
     * @return
     */
    public long getCOD_CAT() {
        return COD_CAT;
    }

    /**
     *
     * @param COD_CAT
     */
    public void setCOD_CAT(long COD_CAT) {
        this.COD_CAT = COD_CAT;
    }

    /**
     *
     * @return
     */
    public String getDES_CAT() {
        return DES_CAT;
    }

    /**
     *
     * @param DES_CAT
     */
    public void setDES_CAT(String DES_CAT) {
        this.DES_CAT = DES_CAT;
    }
    
    @Override
    public String toString(){
        return DES_CAT;
    }
}