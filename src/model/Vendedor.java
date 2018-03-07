package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Vendedor {
    private static final Logger LOG = getLogger(Vendedor.class.getName());
    
    private Long COD_VEN;
    private String CPF_VEN;
    private String NOME_VEN;
    private String SEX_VEN;
    private String DATNASC_VEN;
    private String END_VEN;
    private String NUMEND_VEN;
    private String COMPEND_VEN;
    private String BAI_VEN;
    private String CEP_VEN;
    private String CID_VEN;
    private String UF_VEN;
    private String DDDTEL_VEN;
    private String TEL_VEN;
    private String EMAIL_VEN;
    private String STATUS_VEN;

    /**
     *
     */
    public Vendedor() {
    }

    /**
     *
     * @return
     */
    public Long getCOD_VEN() {
        return COD_VEN;
    }

    /**
     *
     * @param COD_VEN
     */
    public void setCOD_VEN(Long COD_VEN) {
        this.COD_VEN = COD_VEN;
    }

    /**
     *
     * @return
     */
    public String getCPF_VEN() {
        return CPF_VEN;
    }

    /**
     *
     * @param CPF_VEN
     */
    public void setCPF_VEN(String CPF_VEN) {
        this.CPF_VEN = CPF_VEN;
    }

    /**
     *
     * @return
     */
    public String getNOME_VEN() {
        return NOME_VEN;
    }

    /**
     *
     * @param NOME_VEN
     */
    public void setNOME_VEN(String NOME_VEN) {
        this.NOME_VEN = NOME_VEN;
    }

    /**
     *
     * @return
     */
    public String getSEX_VEN() {
        return SEX_VEN;
    }

    /**
     *
     * @param SEX_VEN
     */
    public void setSEX_VEN(String SEX_VEN) {
        this.SEX_VEN = SEX_VEN;
    }

    /**
     *
     * @return
     */
    public String getDATNASC_VEN() {
        return DATNASC_VEN;
    }

    /**
     *
     * @param DATNASC_VEN
     */
    public void setDATNASC_VEN(String DATNASC_VEN) {
        this.DATNASC_VEN = DATNASC_VEN;
    }

    /**
     *
     * @return
     */
    public String getEND_VEN() {
        return END_VEN;
    }

    /**
     *
     * @param END_VEN
     */
    public void setEND_VEN(String END_VEN) {
        this.END_VEN = END_VEN;
    }

    /**
     *
     * @return
     */
    public String getNUMEND_VEN() {
        return NUMEND_VEN;
    }

    /**
     *
     * @param NUMEND_VEN
     */
    public void setNUMEND_VEN(String NUMEND_VEN) {
        this.NUMEND_VEN = NUMEND_VEN;
    }

    /**
     *
     * @return
     */
    public String getCOMPEND_VEN() {
        return COMPEND_VEN;
    }

    /**
     *
     * @param COMPEND_VEN
     */
    public void setCOMPEND_VEN(String COMPEND_VEN) {
        this.COMPEND_VEN = COMPEND_VEN;
    }

    /**
     *
     * @return
     */
    public String getBAI_VEN() {
        return BAI_VEN;
    }

    /**
     *
     * @param BAI_VEN
     */
    public void setBAI_VEN(String BAI_VEN) {
        this.BAI_VEN = BAI_VEN;
    }

    /**
     *
     * @return
     */
    public String getCEP_VEN() {
        return CEP_VEN;
    }

    /**
     *
     * @param CEP_VEN
     */
    public void setCEP_VEN(String CEP_VEN) {
        this.CEP_VEN = CEP_VEN;
    }

    /**
     *
     * @return
     */
    public String getCID_VEN() {
        return CID_VEN;
    }

    /**
     *
     * @param CID_VEN
     */
    public void setCID_VEN(String CID_VEN) {
        this.CID_VEN = CID_VEN;
    }

    /**
     *
     * @return
     */
    public String getUF_VEN() {
        return UF_VEN;
    }

    /**
     *
     * @param UF_VEN
     */
    public void setUF_VEN(String UF_VEN) {
        this.UF_VEN = UF_VEN;
    }

    /**
     *
     * @return
     */
    public String getDDDTEL_VEN() {
        return DDDTEL_VEN;
    }

    /**
     *
     * @param DDDTEL_VEN
     */
    public void setDDDTEL_VEN(String DDDTEL_VEN) {
        this.DDDTEL_VEN = DDDTEL_VEN;
    }

    /**
     *
     * @return
     */
    public String getTEL_VEN() {
        return TEL_VEN;
    }

    /**
     *
     * @param TEL_VEN
     */
    public void setTEL_VEN(String TEL_VEN) {
        this.TEL_VEN = TEL_VEN;
    }

    /**
     *
     * @return
     */
    public String getEMAIL_VEN() {
        return EMAIL_VEN;
    }

    /**
     *
     * @param EMAIL_VEN
     */
    public void setEMAIL_VEN(String EMAIL_VEN) {
        this.EMAIL_VEN = EMAIL_VEN;
    }
    
    public String getSTATUS_VEN(){
        return STATUS_VEN;
    }
    
    public void setSTATUS_VEN(String STATUS_VEN){
        this.STATUS_VEN = STATUS_VEN;
    }
}
