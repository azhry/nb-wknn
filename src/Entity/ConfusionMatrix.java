/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author acer
 */
public class ConfusionMatrix {
    
    private Map<String, Map<String, Integer>> matrix;
    
    public ConfusionMatrix() {
        this.reset();
    }
    
    public void update(String actual, String predicted) {
        this.matrix.get(actual).computeIfPresent(predicted, (k, v) -> v + 1);
    }
    
    public void reset() {
        this.matrix = new HashMap<>();
        
        Map<String, Integer> row = new HashMap<>();
        row.put("Sangat Kurus", 0);
        row.put("Kurus", 0);
        row.put("Normal", 0);
        row.put("Obesitas", 0);
        
        this.matrix.put("Sangat Kurus", row);
        this.matrix.put("Kurus", row);
        this.matrix.put("Normal", row);
        this.matrix.put("Obesitas", row);
    }
    
    public Map<String, Map<String, Integer>> getMatrix() {
        return this.matrix;
    }
}
