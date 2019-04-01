/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

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
        row.put("sangat kurus", 0);
        row.put("kurus", 0);
        row.put("normal", 0);
        row.put("obesitas", 0);
        
        Map<String, Integer> row2 = new HashMap<>();
        row2.put("sangat kurus", 0);
        row2.put("kurus", 0);
        row2.put("normal", 0);
        row2.put("obesitas", 0);
        
        Map<String, Integer> row3 = new HashMap<>();
        row3.put("sangat kurus", 0);
        row3.put("kurus", 0);
        row3.put("normal", 0);
        row3.put("obesitas", 0);
        
        Map<String, Integer> row4 = new HashMap<>();
        row4.put("sangat kurus", 0);
        row4.put("kurus", 0);
        row4.put("normal", 0);
        row4.put("obesitas", 0);
        
        this.matrix.put("sangat kurus", row);
        this.matrix.put("kurus", row2);
        this.matrix.put("normal", row3);
        this.matrix.put("obesitas", row4);
    }
    
    public Map<String, Map<String, Integer>> getMatrix() {
        return this.matrix;
    }
    
    public double getAccuracy() {
       int tp = this.matrix.get("sangat kurus").get("sangat kurus") + 
               this.matrix.get("kurus").get("kurus") + 
               this.matrix.get("normal").get("normal") + 
               this.matrix.get("obesitas").get("obesitas");
       int f = MathFx.sumMap(this.matrix.get("sangat kurus")) + 
               MathFx.sumMap(this.matrix.get("kurus")) + 
               MathFx.sumMap(this.matrix.get("normal")) + 
               MathFx.sumMap(this.matrix.get("obesitas"));
       return (double)tp / (double)f;
    }
}
