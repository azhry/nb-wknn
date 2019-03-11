/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author acer
 */
public class NaiveBayes {
    
    private final Preprocessor p = new Preprocessor();
    
    private Map<String, Integer> classDistribution;
    private Map<String, Map<String, Integer>> sampleDistribution;
    private Map<String, Double> priorProbability;
    private Map<String, Map<String, Double>> conditionalProbability;
    private List<Patient> train;
    private int[][] trainMatrix;
    private int totalSamples;
    
    public NaiveBayes() {
        this.priorProbability = new HashMap<>();
        this.classDistribution = new HashMap<>();
    }
    
    public void fit(List<Patient> patients) {
        this.train = patients;
        this.totalSamples = this.train.size();
        this.trainMatrix = this.p.toMatrix(patients);
        this.classDistribution = this.countClassDistribution(this.train);
        this.sampleDistribution = this.countSampleDistribution(
                this.trainMatrix);
        this.computePriorProbability();
    }
    
    private void computePriorProbability() {
        for (Map.Entry<String, Integer> entry :
                this.classDistribution.entrySet()) {
            this.priorProbability.put(entry.getKey(),
                    (double)(entry.getValue() / this.totalSamples));
        }
    }
    
    private void computeConditionalProbability() {
        for (int i = 0; i < this.totalSamples; i++) {
            
        }
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
    
    private Map<String, Map<String, Integer>> countSampleDistribution(
            int[][] trainMatrix) {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        return map;
    }
}
