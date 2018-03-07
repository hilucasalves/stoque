package model;

import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;

/**
 *
 * @author INFOLUCK
 */
public class Login {
    private static final Logger LOG = getLogger(Login.class.getName());
    
    private String NOME_USU;
    private String LOGIN_USU;
    private String SENHA_USU;

    /**
     *
     */
    public Login() {
    }
    
    /**
     *
     * @return
     */
    public String getNOME_USU() {
        return NOME_USU;
    }

    /**
     *
     * @param NOME_USU
     */
    public void setNOME_USU(String NOME_USU) {
        this.NOME_USU = NOME_USU;
    }

    /**
     *
     * @return
     */
    public String getLOGIN_USU() {
        return LOGIN_USU;
    }

    /**
     *
     * @param LOGIN_USU
     */
    public void setLOGIN_USU(String LOGIN_USU) {
        this.LOGIN_USU = LOGIN_USU;
    }

    /**
     *
     * @return
     */
    public String getSENHA_USU() {
        return SENHA_USU;
    }

    /**
     *
     * @param SENHA_USU
     */
    public void setSENHA_USU(String SENHA_USU) {
        this.SENHA_USU = SENHA_USU;
    }

}
