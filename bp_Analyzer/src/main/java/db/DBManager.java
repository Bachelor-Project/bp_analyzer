/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import dto.Analyze;
import java.util.List;

/**
 *
 * @author Dato
 */
public interface DBManager {
    
    public List<Analyze> getAnalysesOn(int taskId);
    public void saveAnalyse(Analyze analyze);
    public void updateAnalyze(int id, String text);
    public void deleteAnalyze(int id);
    
}
