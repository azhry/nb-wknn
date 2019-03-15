/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author acer
 */
public class NWKNN {
    
    private final Preprocessor p = new Preprocessor();
    
    private int K;
    private List<Patient> train;
    private int[][] trainMatrix;
    private Map<String, Integer> classDistribution;
    
    public NWKNN() {
        this.classDistribution = new HashMap<>();
    }
    
    public void fit(List<Patient> patients) {
        this.train = patients;
        this.trainMatrix = this.p.toMatrix(patients);
        this.classDistribution = this.countClassDistribution(this.train);
    }
    
    public Map<String, Integer> getClassDistribution() {
        return this.classDistribution;
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
    
    private List<Patient> getNearestNeighbors(Patient patient, Integer k) {
        k = k != null ? k : 1;
        int[] sample = this.p.preprocess(patient);  
        Map<Integer, Double> results = new HashMap<>();
        List<Integer> sampleList = Arrays.stream(sample)
                .boxed()
                .collect(Collectors.toList());
        
        int i = 0;
        for (int[] row : this.trainMatrix) {
            List<Integer> rowList = Arrays.stream(row)
                    .boxed()
                    .collect(Collectors.toList());
            results.put(i, MathFx.euclideanDistance(sampleList, rowList));
            i++;
        }
        
        List<Patient> neighbors = new ArrayList<>();
        List<Entry<Integer, Double>> sorted = MathFx.sortMap(results, "ASC");
        
        int currentLimit = 0;
        for (Map.Entry<Integer, Double> entry : sorted) {
            if (currentLimit >= k) {
                break;
            }
            Patient pt = this.train.get(entry.getKey());
            pt.setDistance(entry.getValue());
            neighbors.add(pt);
            currentLimit++;
        }
        
        return neighbors;
    } 

    private Map<String, Double> calculateClassWeight(List<Patient> neighbors) {
        Map<String, Double> map = new HashMap<>();
        Map<String, Integer> dist = this.countClassDistribution(neighbors);
        int lowest = Collections.min(dist.values());
        this.classDistribution.forEach((key, value) -> {
            map.put(key, this.calculateWeight(value, lowest));
        });
        return map;
    }
    
    private double calculateWeight(int num, int lowest) {
        return 1 / Math.pow(
                (double)num / (double)lowest, 
                1 / Math.E
            );
    }
    
    public String predict(Patient patient, Integer k) {
        List<Patient> neighbors = this.getNearestNeighbors(patient, k);
        Map<String, Double> classWeights = this.calculateClassWeight(neighbors);
        Map<String, Integer> dist = this.countClassDistribution(neighbors);
        List<String> classes = new ArrayList<>(dist.keySet());
        if (classes.size() == 1) {
            return classes.get(0);
        }
        double temp = 0.0;
        for (Patient p : neighbors) {
            temp += Math.sqrt(p.getDistance());
        }
        final double sum = temp;
        Map<String, Double> scores = new HashMap<>();
        dist.forEach((key, value) -> {
            scores.put(key, value * sum);
        });
        List<Entry<String, Double>> sorted = MathFx.sortMapDouble(scores);
        return sorted.get(sorted.size() - 1).getKey();
    }
}
