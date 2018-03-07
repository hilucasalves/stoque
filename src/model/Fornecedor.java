package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Fornecedor {
    private static final Logger LOG = getLogger(Fornecedor.class.getName());
  
    private long    COD_FOR;
    private String  CNPJ_FOR;
    private String  NOME_FOR;
    private String  END_FOR;
    private String  NUMEND_FOR;
    private String  COMPEND_FOR;
    private String  BAI_FOR;
    private String  CEP_FOR;
    private String  CID_FOR;
    private String  UF_FOR;
    private String  DDDTEL_FOR;
    private String  TEL_FOR;
    private String  EMAIL_FOR;
    private String  STATUS_FOR;
    
    /**
     *
     */
    public Fornecedor() {
    }
    
    public Fornecedor(long COD_FOR, String NOME_FOR) {
        this.COD_FOR = COD_FOR;
        this.NOME_FOR = NOME_FOR;
    }

    /**
     *
     * @return
     */
    public long getCOD_FOR() {
        return COD_FOR;
    }

    /**
     *
     * @param COD_FOR
     */
    public void setCOD_FOR(long COD_FOR) {
        this.COD_FOR = COD_FOR;
    }

    /**
     *
     * @return
     */
    public String getCNPJ_FOR() {
        return CNPJ_FOR;
    }

    /**
     *
     * @param CNPJ_FOR
     */
    public void setCNPJ_FOR(String CNPJ_FOR) {
        this.CNPJ_FOR = CNPJ_FOR;
    }

    /**
     *
     * @return
     */
    public String getNOME_FOR() {
        return NOME_FOR;
    }

    /**
     *
     * @param NOME_FOR
     */
    public void setNOME_FOR(String NOME_FOR) {
        this.NOME_FOR = NOME_FOR;
    }

    /**
     *
     * @return
     */
    public String getEND_FOR() {
        return END_FOR;
    }

    /**
     *
     * @param END_FOR
     */
    public void setEND_FOR(String END_FOR) {
        this.END_FOR = END_FOR;
    }

    /**
     *
     * @return
     */
    public String getNUMEND_FOR() {
        return NUMEND_FOR;
    }

    /**
     *
     * @param NUMEND_FOR
     */
    public void setNUMEND_FOR(String NUMEND_FOR) {
        this.NUMEND_FOR = NUMEND_FOR;
    }

    /**
     *
     * @return
     */
    public String getCOMPEND_FOR() {
        return COMPEND_FOR;
    }

    /**
     *
     * @param COMPEND_FOR
     */
    public void setCOMPEND_FOR(String COMPEND_FOR) {
        this.COMPEND_FOR = COMPEND_FOR;
    }

    /**
     *
     * @return
     */
    public String getBAI_FOR() {
        return BAI_FOR;
    }

    /**
     *
     * @param BAI_FOR
     */
    public void setBAI_FOR(String BAI_FOR) {
        this.BAI_FOR = BAI_FOR;
    }

    /**
     *
     * @return
     */
    public String getCEP_FOR() {
        return CEP_FOR;
    }

    /**
     *
     * @param CEP_FOR
     */
    public void setCEP_FOR(String CEP_FOR) {
        this.CEP_FOR = CEP_FOR;
    }

    /**
     *
     * @return
     */
    public String getCID_FOR() {
        return CID_FOR;
    }

    /**
     *
     * @param CID_FOR
     */
    public void setCID_FOR(String CID_FOR) {
        this.CID_FOR = CID_FOR;
    }

    /**
     *
     * @return
     */
    public String getUF_FOR() {
        return UF_FOR;
    }

    /**
     *
     * @param UF_FOR
     */
    public void setUF_FOR(String UF_FOR) {
        this.UF_FOR = UF_FOR;
    }

    /**
     *
     * @return
     */
    public String getDDDTEL_FOR() {
        return DDDTEL_FOR;
    }

    /**
     *
     * @param DDDTEL_FOR
     */
    public void setDDDTEL_FOR(String DDDTEL_FOR) {
        this.DDDTEL_FOR = DDDTEL_FOR;
    }

    /**
     *
     * @return
     */
    public String getTEL_FOR() {
        return TEL_FOR;
    }

    /**
     *
     * @param TEL_FOR
     */
    public void setTEL_FOR(String TEL_FOR) {
        this.TEL_FOR = TEL_FOR;
    }

    /**
     *
     * @return
     */
    public String getEMAIL_FOR() {
        return EMAIL_FOR;
    }

    /**
     *
     * @param EMAIL_FOR
     */
    public void setEMAIL_FOR(String EMAIL_FOR) {
        this.EMAIL_FOR = EMAIL_FOR;
    }
    
    public String getSTATUS_FOR(){
        return STATUS_FOR;
    }
    
    public void setSTATUS_FOR(String STATUS_FOR){
        this.STATUS_FOR = STATUS_FOR;
    }
    
    @Override
    public String toString(){
        return NOME_FOR;
    }
}