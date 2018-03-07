package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import static java.util.logging.Logger.getLogger;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author INFOLUCK
 */
public class FornecedorDAO {
    private static final Logger LOG = getLogger(FornecedorDAO.class.getName());
    
    /**
     *
     * @param vModuloAdministrativo
     */
    public void cleanFornecedor(view.ModuloAdministrativo vModuloAdministrativo){
    vModuloAdministrativo.CNPJ_FOR.setText(null);
    vModuloAdministrativo.NOME_FOR.setText(null);
    vModuloAdministrativo.END_FOR.setText(null);
    vModuloAdministrativo.NUMEND_FOR.setText(null);
    vModuloAdministrativo.COMPEND_FOR.setText(null);
    vModuloAdministrativo.BAI_FOR.setText(null);
    vModuloAdministrativo.CID_FOR.setText(null);
    vModuloAdministrativo.CEP_FOR.setText(null);
    vModuloAdministrativo.UF_FOR.setSelectedIndex(0);
    vModuloAdministrativo.DDDTEL_FOR.setText(null);
    vModuloAdministrativo.TEL_FOR.setText(null);
    vModuloAdministrativo.EMAIL_FOR.setText(null);
    vModuloAdministrativo.STATUS_FOR.setSelectedIndex(0);
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     */
    public void getFornecedor(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor){
    mFornecedor.setCNPJ_FOR(vModuloAdministrativo.CNPJ_FOR.getText().replace(".", "").replace("/", "").replace("-", ""));
    mFornecedor.setNOME_FOR(vModuloAdministrativo.NOME_FOR.getText());
    mFornecedor.setEND_FOR(vModuloAdministrativo.END_FOR.getText());
    mFornecedor.setNUMEND_FOR(vModuloAdministrativo.NUMEND_FOR.getText());
    mFornecedor.setCOMPEND_FOR(vModuloAdministrativo.COMPEND_FOR.getText());
    mFornecedor.setBAI_FOR(vModuloAdministrativo.BAI_FOR.getText());
    mFornecedor.setCEP_FOR(vModuloAdministrativo.CEP_FOR.getText().replace("-", ""));
    mFornecedor.setCID_FOR(vModuloAdministrativo.CID_FOR.getText());
    mFornecedor.setUF_FOR((String) vModuloAdministrativo.UF_FOR.getSelectedItem());
    mFornecedor.setDDDTEL_FOR(vModuloAdministrativo.DDDTEL_FOR.getText());
    mFornecedor.setTEL_FOR(vModuloAdministrativo.TEL_FOR.getText().replace("-", ""));
    mFornecedor.setEMAIL_FOR(vModuloAdministrativo.EMAIL_FOR.getText());
    mFornecedor.setSTATUS_FOR((String) vModuloAdministrativo.STATUS_FOR.getSelectedItem());
    }
 
    /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     */
    public void adicionarFornecedor(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor) {  
    if(mFornecedor.getNOME_FOR().equalsIgnoreCase("")||mFornecedor.getCNPJ_FOR().equalsIgnoreCase("")){
    showMessageDialog(vModuloAdministrativo, "Os principais campos estão vazios.", "Atenção", 2);
    } else{
    try{       
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.openConnection();
  
    String sql = ("INSERT INTO FORNECEDOR (CNPJ_FOR, NOME_FOR, END_FOR, NUMEND_FOR, COMPEND_FOR, BAI_FOR, CEP_FOR, CID_FOR, UF_FOR, DDDTEL_FOR, TEL_FOR, EMAIL_FOR, STATUS_FOR) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);

                preparedStatement.setString(1, mFornecedor.getCNPJ_FOR());
                preparedStatement.setString(2, mFornecedor.getNOME_FOR());
                preparedStatement.setString(3, mFornecedor.getEND_FOR());
                preparedStatement.setString(4, mFornecedor.getNUMEND_FOR());
                preparedStatement.setString(5, mFornecedor.getCOMPEND_FOR());
                preparedStatement.setString(6, mFornecedor.getBAI_FOR());
                preparedStatement.setString(7, mFornecedor.getCEP_FOR());
                preparedStatement.setString(8, mFornecedor.getCID_FOR());
                preparedStatement.setString(9, mFornecedor.getUF_FOR());
                preparedStatement.setString(10, mFornecedor.getDDDTEL_FOR());
                preparedStatement.setString(11, mFornecedor.getTEL_FOR());
                preparedStatement.setString(12, mFornecedor.getEMAIL_FOR());
                preparedStatement.setString(13, mFornecedor.getSTATUS_FOR().substring(0, 1));
                
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Fornecedor incluso com sucesso.");
    
    this.comboFornecedor(vModuloAdministrativo.PROD_NOME_FOR);
    this.cleanFornecedor(vModuloAdministrativo);
    this.pesquisarTabela_Fornecedor(vModuloAdministrativo, mFornecedor);
    }
    catch(SQLException | NumberFormatException sQLException){
    showMessageDialog(null, sQLException.getMessage());
    }}
    }

    /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     */
    
    public void excluirFornecedor(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor){
    try{
    mFornecedor.setCOD_FOR((long) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 0));
    
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("DELETE FROM FORNECEDOR WHERE COD_FOR = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
   
    preparedStatement.setLong(1, mFornecedor.getCOD_FOR());
    preparedStatement.executeUpdate();
    showMessageDialog(null, "Fornecedor excluído da Base de Dados com sucesso.");
    
    this.comboFornecedor(vModuloAdministrativo.PROD_NOME_FOR);
    this.cleanFornecedor(vModuloAdministrativo);
    this.pesquisarTabela_Fornecedor(vModuloAdministrativo, mFornecedor);
    }
    catch(SQLException sqlException){
    showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     */
    public void atualizarFornecedor(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor){
    try{
    mFornecedor.setCOD_FOR((long) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 0));
    
    getFornecedor(vModuloAdministrativo, mFornecedor);
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("UPDATE FORNECEDOR SET CNPJ_FOR = ?, NOME_FOR = ?, END_FOR = ?, NUMEND_FOR = ?, COMPEND_FOR = ?, BAI_FOR = ?, CEP_FOR = ?, CID_FOR = ?, UF_FOR = ?, DDDTEL_FOR = ?, TEL_FOR = ?, EMAIL_FOR = ?, STATUS_FOR = ? WHERE COD_FOR = ?");
    
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    
                preparedStatement.setString(1, mFornecedor.getCNPJ_FOR());
                preparedStatement.setString(2, mFornecedor.getNOME_FOR());
                preparedStatement.setString(3, mFornecedor.getEND_FOR());
                preparedStatement.setString(4, mFornecedor.getNUMEND_FOR());
                preparedStatement.setString(5, mFornecedor.getCOMPEND_FOR());
                preparedStatement.setString(6, mFornecedor.getBAI_FOR());
                preparedStatement.setString(7, mFornecedor.getCEP_FOR());
                preparedStatement.setString(8, mFornecedor.getCID_FOR());
                preparedStatement.setString(9, mFornecedor.getUF_FOR());
                preparedStatement.setString(10, mFornecedor.getDDDTEL_FOR());
                preparedStatement.setString(11, mFornecedor.getTEL_FOR());
                preparedStatement.setString(12, mFornecedor.getEMAIL_FOR());
                if(mFornecedor.getSTATUS_FOR().equalsIgnoreCase("Ativo")){
                    preparedStatement.setString(13, "A");
                } else {
                    preparedStatement.setString(13, "I");
                }
                
                preparedStatement.setLong(14, mFornecedor.getCOD_FOR());
                preparedStatement.executeUpdate();
                showMessageDialog(null, "Fornecedor atualizado com sucesso.");
    
    this.comboFornecedor(vModuloAdministrativo.PROD_NOME_FOR);
    this.cleanFornecedor(vModuloAdministrativo);
    this.pesquisarTabela_Fornecedor(vModuloAdministrativo, mFornecedor);
    }
    catch(SQLException sqlException){
            showMessageDialog(null, sqlException.getMessage());
    }
    }
    
    /**
     *
     * @param vModuloAdministrativo
     * @param mFornecedor
     * @return
     */
    public ArrayList pesquisarTabela_Fornecedor(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();   
    String sql = ("SELECT COD_FOR, CNPJ_FOR, NOME_FOR, END_FOR, NUMEND_FOR, "
            + "COMPEND_FOR, BAI_FOR, CEP_FOR, CID_FOR, UF_FOR, "
            + "DDDTEL_FOR, TEL_FOR, EMAIL_FOR, STATUS_FOR "
            + "FROM FORNECEDOR");
    ArrayList <model.Fornecedor> fornecedor = new ArrayList <> ();
  
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    while(resultSet.next()){
    model.Fornecedor f = new model.Fornecedor();
    f.setCOD_FOR(resultSet.getLong("COD_FOR"));
    f.setCNPJ_FOR(resultSet.getString("CNPJ_FOR"));
    f.setNOME_FOR(resultSet.getString("NOME_FOR"));
    f.setEND_FOR(resultSet.getString("END_FOR"));
    f.setNUMEND_FOR(resultSet.getString("NUMEND_FOR"));
    f.setCOMPEND_FOR(resultSet.getString("COMPEND_FOR"));
    f.setBAI_FOR(resultSet.getString("BAI_FOR"));
    f.setCEP_FOR(resultSet.getString("CEP_FOR"));
    f.setCID_FOR(resultSet.getString("CID_FOR"));
    f.setUF_FOR(resultSet.getString("UF_FOR"));
    f.setDDDTEL_FOR(resultSet.getString("DDDTEL_FOR"));
    f.setTEL_FOR(resultSet.getString("TEL_FOR"));
    f.setEMAIL_FOR(resultSet.getString("EMAIL_FOR"));
    f.setSTATUS_FOR(resultSet.getString("STATUS_FOR"));
    fornecedor.add(f);
    }
    Iterator it = fornecedor.iterator();
    DefaultTableModel m = (DefaultTableModel) vModuloAdministrativo.tableFornecedor.getModel();
    m.setNumRows(0);
    while(it.hasNext()){
    model.Fornecedor f = (model.Fornecedor)it.next();
    m.addRow(new Object[]{  f.getCOD_FOR(), f.getCNPJ_FOR(), f.getNOME_FOR(),
                            f.getEND_FOR(), f.getNUMEND_FOR(), f.getCOMPEND_FOR(),
                            f.getBAI_FOR(), f.getCEP_FOR(), f.getCID_FOR(),
                            f.getUF_FOR(), f.getDDDTEL_FOR(), f.getTEL_FOR(),
                            f.getEMAIL_FOR(), f.getSTATUS_FOR()});
    }
    return fornecedor;
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    return null;
    }
    
    public void comboFornecedor(javax.swing.JComboBox x){
    try{
    controller.ConnectionFactory connectionFactory = new controller.ConnectionFactory();
    connectionFactory.openConnection();
    
    String sql = ("SELECT COD_FOR, NOME_FOR FROM FORNECEDOR");
    PreparedStatement preparedStatement = connectionFactory.Connection.prepareStatement(sql);
    ResultSet resultSet = preparedStatement.executeQuery();
    x.removeAllItems();
    while(resultSet.next()){
    model.Fornecedor mFornecedor = new model.Fornecedor();
    mFornecedor.setCOD_FOR(resultSet.getLong("COD_FOR"));
    mFornecedor.setNOME_FOR(resultSet.getString("NOME_FOR"));
    x.addItem(new model.Fornecedor(mFornecedor.getCOD_FOR(), mFornecedor.getNOME_FOR()));
    }  
    }
    catch(SQLException sqlException){
    sqlException.getMessage();
    }
    }
    
    public void getFornecedorrow(view.ModuloAdministrativo vModuloAdministrativo, model.Fornecedor mFornecedor){
        mFornecedor.setCOD_FOR((long) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 0));
        mFornecedor.setCNPJ_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 1));
        mFornecedor.setNOME_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 2));
        mFornecedor.setEND_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 3));
        mFornecedor.setNUMEND_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 4));
        mFornecedor.setCOMPEND_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 5));
        mFornecedor.setBAI_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 6));
        mFornecedor.setCEP_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 7));
        mFornecedor.setCID_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 8));
        mFornecedor.setUF_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 9));
        mFornecedor.setDDDTEL_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 10));
        mFornecedor.setTEL_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 11));
        mFornecedor.setEMAIL_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 12));
        mFornecedor.setSTATUS_FOR((String) vModuloAdministrativo.tableFornecedor.getValueAt(vModuloAdministrativo.tableFornecedor.getSelectedRow(), 13));
        
        vModuloAdministrativo.CNPJ_FOR.setText(mFornecedor.getCNPJ_FOR());
        vModuloAdministrativo.NOME_FOR.setText(mFornecedor.getNOME_FOR());
        vModuloAdministrativo.END_FOR.setText(mFornecedor.getEND_FOR());
        vModuloAdministrativo.NUMEND_FOR.setText(mFornecedor.getNUMEND_FOR());
        vModuloAdministrativo.COMPEND_FOR.setText(mFornecedor.getCOMPEND_FOR());
        vModuloAdministrativo.BAI_FOR.setText(mFornecedor.getBAI_FOR());
        vModuloAdministrativo.CEP_FOR.setText(mFornecedor.getCEP_FOR());
        vModuloAdministrativo.CID_FOR.setText(mFornecedor.getCID_FOR());
        vModuloAdministrativo.UF_FOR.setSelectedItem(mFornecedor.getUF_FOR());
        vModuloAdministrativo.DDDTEL_FOR.setText(mFornecedor.getDDDTEL_FOR());
        vModuloAdministrativo.TEL_FOR.setText(mFornecedor.getTEL_FOR());
        vModuloAdministrativo.EMAIL_FOR.setText(mFornecedor.getEMAIL_FOR());
        if(mFornecedor.getSTATUS_FOR().equals("A")){
        vModuloAdministrativo.STATUS_FOR.setSelectedItem("Ativo");
        }else{
        vModuloAdministrativo.STATUS_FOR.setSelectedItem("Inativo");
        }
    }
}