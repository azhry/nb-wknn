/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author acer
 */
public class NWKNN {
    
    private int K;
    private List<Patient> train;
    private int[][] trainMatrix;
    private Preprocessor p;
    
    public NWKNN() {
        this.p = new Preprocessor();
    }
    
    public void fit(List<Patient> patients) {
        this.train = patients;
        this.trainMatrix = this.p.toMatrix(patients);
    }
    
    private Double euclideanDistance(List<Integer> x1, List<Integer> x2) {
        Double result = 0.00;
        if (x1.size() != x2.size()) {
            throw new Error("You should pass two lists of same size");
        }
        
        Iterator x1Iterator = x1.iterator();
        Iterator x2Iterator = x2.iterator();
        
        while (x1Iterator.hasNext() && x2Iterator.hasNext()) {
            result += Math.pow((int)x1Iterator.next() -
                    (int)x2Iterator.next(), 2);
        }

        return Math.sqrt(result);
    }
    
    public List<Patient> getNearestNeighbors(Patient patient, Integer k) {
        k = k != null ? k : 1;
        final int SAMPLE_LENGTH = 8;
        
        int[] sample = new int[SAMPLE_LENGTH];
        sample[0] = this.p.mapGender(patient.getGender());
        sample[1] = this.p.mapAge(patient.getAge());
        sample[2] = this.p.mapWeight(patient.getWeight());
        sample[3] = this.p.mapHeight(patient.getHeight());
        sample[4] = this.p.mapLILA(patient.getLILA());
        sample[5] = this.p.mapLP(patient.getLP());
        sample[6] = this.p.mapLK(patient.getLK());
        sample[7] = this.p.mapNutritionalStatus(patient
                .getNutritionalStatus());
        
        Map<Integer, Double> results = new HashMap<>();
        List<Integer> sampleList = Arrays.stream(sample)
                .boxed()
                .collect(Collectors.toList());
        int i = 0;
        for (int[] row : this.trainMatrix) {
            List<Integer> rowList = Arrays.stream(row)
                    .boxed()
                    .collect(Collectors.toList());
            results.put(i, this.euclideanDistance(sampleList, rowList));
            i++;
        }
        
        List<Patient> neighbors = new ArrayList<>();
        List<Entry<Integer, Double>> sorted = this.sortMap(results);
        for (Map.Entry<Integer, Double> entry : sorted) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        
        return neighbors;
    }
    
    private List<Entry<Integer, Double>> sortMap(Map<Integer, Double> map) {
        Set<Entry<Integer, Double>> set = map.entrySet();
        List<Entry<Integer, Double>> list = new ArrayList<Entry<Integer, 
                Double>>(set);
        Collections.sort(list, new Comparator<Map.Entry<Integer, Double>>()
        {
            public int compare(Map.Entry<Integer, Double> o1, 
                    Map.Entry<Integer, Double> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        
        return list;
    }
    
}
