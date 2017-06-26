/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import dto.Analyze;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 *
 * @author Dato
 */
public class DBManagerReal implements DBManager {

    private final String connectionState = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState";
    private final String statementFinalizer = "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";
    private DataSource datasource;
    
    public static DBManagerReal instance = new DBManagerReal();
    
    private DBManagerReal(){
        initDB();
    }
    
    private void initDB(){
        PoolProperties pool = new PoolProperties();
        pool.setUrl(DBData.DB_PATH);
        pool.setDriverClassName(DBData.JDBC_DRIVER);
        pool.setUsername(DBData.USERNAME);
        pool.setPassword(DBData.PASSWORD);
        pool.setMaxActive(256);
        pool.setInitialSize(16);
        pool.setMaxWait(10000);
        pool.setJdbcInterceptors(connectionState + ";" + statementFinalizer);
        
        datasource = new DataSource();
        datasource.setPoolProperties(pool);
    }
    
    @Override
    public List<Analyze> getAnalysesOn(int taskId) {
        List<Analyze> result = new ArrayList<>();
        try {
            Connection con = datasource.getConnection();
            CallableStatement stmt = con.prepareCall("call select_comments_on(?)");
            stmt.setInt(1, taskId);
            stmt.execute();
            
            ResultSet rsSet =  stmt.getResultSet();
            while(rsSet.next()){
                Analyze a = new Analyze();
                a.setId(rsSet.getInt(1));
                a.setTaskId(rsSet.getInt(2));
                a.setUsername(rsSet.getString(3));
                a.setText(rsSet.getString(4));
                result.add(a);

            }
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerReal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public void saveAnalyse(Analyze analyze) {
        try {
            Connection con = datasource.getConnection();
            CallableStatement stmt = con.prepareCall("call save_comment(?, ?, ?)");
            stmt.setInt(1, analyze.getTaskId());
            stmt.setString(2, analyze.getUsername());
            stmt.setString(3, analyze.getText());
            stmt.execute();
            
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManagerReal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateAnalyze(int id, String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAnalyze(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
