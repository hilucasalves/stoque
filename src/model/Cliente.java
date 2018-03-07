package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Cliente {
    private static final Logger LOG = getLogger(Cliente.class.getName());
  
    private Long COD_CLI;
    private String CPF_CLI;
    private String NOME_CLI;
    private String SEX_CLI;
    private String DTNASC_CLI; 
    private String END_CLI;
    private String NUMEND_CLI;
    private String COMPEND_CLI;
    private String BAI_CLI;
    private String CEP_CLI;
    private String CID_CLI;
    private String UF_CLI;
    private String DDDTEL_CLI;
    private String TEL_CLI;
    private String EMAIL_CLI;
    private String STATUS_CLI;

    /**
     *
     */
    public Cliente() {
    }
    
    public Cliente(String CPF_CLI, String NOME_CLI) {
        this.CPF_CLI = CPF_CLI;
        this.NOME_CLI = NOME_CLI;
    }

    public Long getCOD_CLI() {
        return COD_CLI;
    }

    public void setCOD_CLI(Long COD_CLI) {
        this.COD_CLI = COD_CLI;
    }

    public String getCPF_CLI() {
        return CPF_CLI;
    }

    public void setCPF_CLI(String CPF_CLI) {
        this.CPF_CLI = CPF_CLI;
    }

    public String getNOME_CLI() {
        return NOME_CLI;
    }

    public void setNOME_CLI(String NOME_CLI) {
        this.NOME_CLI = NOME_CLI;
    }

    public String getSEX_CLI() {
        return SEX_CLI;
    }

    public void setSEX_CLI(String SEX_CLI) {
        this.SEX_CLI = SEX_CLI;
    }

    public String getDTNASC_CLI() {
        return DTNASC_CLI;
    }

    public void setDTNASC_CLI(String DTNASC_CLI) {
        this.DTNASC_CLI = DTNASC_CLI;
    }

    public String getEND_CLI() {
        return END_CLI;
    }

    public void setEND_CLI(String END_CLI) {
        this.END_CLI = END_CLI;
    }

    public String getNUMEND_CLI() {
        return NUMEND_CLI;
    }

    public void setNUMEND_CLI(String NUMEND_CLI) {
        this.NUMEND_CLI = NUMEND_CLI;
    }

    public String getCOMPEND_CLI() {
        return COMPEND_CLI;
    }

    public void setCOMPEND_CLI(String COMPEND_CLI) {
        this.COMPEND_CLI = COMPEND_CLI;
    }

    public String getBAI_CLI() {
        return BAI_CLI;
    }

    public void setBAI_CLI(String BAI_CLI) {
        this.BAI_CLI = BAI_CLI;
    }

    public String getCEP_CLI() {
        return CEP_CLI;
    }

    public void setCEP_CLI(String CEP_CLI) {
        this.CEP_CLI = CEP_CLI;
    }

    public String getCID_CLI() {
        return CID_CLI;
    }

    public void setCID_CLI(String CID_CLI) {
        this.CID_CLI = CID_CLI;
    }

    public String getUF_CLI() {
        return UF_CLI;
    }

    public void setUF_CLI(String UF_CLI) {
        this.UF_CLI = UF_CLI;
    }

    public String getDDDTEL_CLI() {
        return DDDTEL_CLI;
    }

    public void setDDDTEL_CLI(String DDDTEL_CLI) {
        this.DDDTEL_CLI = DDDTEL_CLI;
    }

    public String getTEL_CLI() {
        return TEL_CLI;
    }

    public void setTEL_CLI(String TEL_CLI) {
        this.TEL_CLI = TEL_CLI;
    }

    public String getEMAIL_CLI() {
        return EMAIL_CLI;
    }

    public void setEMAIL_CLI(String EMAIL_CLI) {
        this.EMAIL_CLI = EMAIL_CLI;
    }

    public String getSTATUS_CLI() {
        return STATUS_CLI;
    }

    public void setSTATUS_CLI(String STATUS_CLI) {
        this.STATUS_CLI = STATUS_CLI;
    }
    
    @Override
    public String toString(){
        return NOME_CLI;
    }
 
}