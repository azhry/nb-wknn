/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entity.Patient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author acer
 */
public class ConfusionMatrix {
    
    private final String[] LABELS = new String[] {
        "sangat kurus", "kurus", "normal", "obesitas"
    };
    
    private Map<String, Map<String, Integer>> matrix;
    private List<Patient> data;
    private Map<String, Integer> classDistribution;
    private Map<String, Double> precisions;
    private Map<String, Double> recalls;
    
    public ConfusionMatrix(List<Patient> data) {
        this.data = data;
        this.classDistribution = this.countClassDistribution(this.data);
        this.reset();
    }
    
    public void update(String actual, String predicted) {
        this.matrix.get(actual).computeIfPresent(predicted, (k, v) -> v + 1);
    }
    
    public void reset() {
        this.matrix = new HashMap<>();
        this.precisions = new HashMap<>();
        this.recalls = new HashMap<>();
        
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
    
    public double getAveragePrecision() {
        for (String label : LABELS) {
            int actualResults = 0;
            for (String l : LABELS) {
                actualResults += this.matrix.get(label).get(l);
            }
            if (actualResults == 0) {
                this.precisions.put(label, (double)actualResults);
            } else {
                this.precisions.put(label, 
                    (double)this.matrix.get(label).get(label) / 
                            (double)actualResults);
            }
        }
        double totalPrecision = 0.0;
        for (Map.Entry<String, Double> entry : this.precisions.entrySet()) {
            totalPrecision += entry.getValue();
        }
        return totalPrecision / this.precisions.size();
    }
    
    public double getAverageRecall() {
        for (String label : LABELS) {
            int predictedResults = 0;
            for (String l : LABELS) {
                predictedResults += this.matrix.get(l).get(label);
            }
            if (predictedResults == 0) {
                this.recalls.put(label, (double)predictedResults);
            } else {
                this.recalls.put(label, 
                    (double)this.matrix.get(label).get(label) / 
                            (double)predictedResults);
            }
            
        }
        double totalRecalls = 0.0;
        for (Map.Entry<String, Double> entry : this.recalls.entrySet()) {
            totalRecalls += entry.getValue();
        }
        return totalRecalls / this.recalls.size();
    }
    
    public double getFMeasure() {
        double precision = this.getAveragePrecision();
        double recall = this.getAverageRecall();
        return 2 * ((precision * recall) / (precision + recall));
    }
    
    private Map<String, Integer> countClassDistribution(
            List<Patient> patients) {
        Map<String, Integer> map = new HashMap<>();
        patients.stream().forEach(patient -> {
            String cls = patient.getNutritionalStatus();
            if (map.containsKey(cls)) {
                int c = map.get(cls);
                map.put(cls, ++c);
            } else {
                map.put(cls, 1);
            }
        });
        return map;
    }
}
