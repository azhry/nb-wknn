/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Entity.Patient;
import java.util.ArrayList;
import java.util.Arrays;
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
    private Map<String, Map<String, Map<String, Integer>>> sampleDistribution;
    private Map<String, Double> priorProbability;
    private Map<String, 
            Map<String, Map<String, Double>>> conditionalProbability;
    private List<Patient> train;
    private int[][] trainMatrix;
    private int totalSamples;
    private Map<String, Map<String, Double>> attributesMean;
    private Map<String, Map<String, Double>> attributesStd;
    
    public NaiveBayes() {
        this.priorProbability = new HashMap<>();
        this.classDistribution = new HashMap<>();
        this.conditionalProbability = new HashMap<>();
        this.attributesMean = new HashMap<>();
        this.attributesStd = new HashMap<>();
    }
    
    public void fit(List<Patient> patients) {
        this.train = patients;
        this.totalSamples = this.train.size();
        this.trainMatrix = this.p.toMatrix(patients);
        this.classDistribution = this.countClassDistribution(this.train);
        this.countSampleDistribution(this.trainMatrix);
        this.computePriorProbability();
        this.computeConditionalProbability();
    }
    
    private void computePriorProbability() {
        for (Map.Entry<String, Integer> entry :
                this.classDistribution.entrySet()) {
            this.priorProbability.put(entry.getKey(),
                    ((double)entry.getValue() / (double)this.totalSamples));
        }
    }
    
    private void computeConditionalProbability() {
        final Map<String, Integer> clsDist = this.classDistribution;
        this.sampleDistribution.forEach((label, data) -> {
            String cls = "";
            switch (label) {
                case "1":
                    cls = "sangat kurus";
                    break;
                
                case "2":
                    cls = "kurus";
                    break;
                
                case "3":
                    cls = "normal";
                    break;
                    
                case "4":
                    cls = "obesitas";
                    break;
            }
            final String lbl = cls;
            Map<String, Map<String, Double>> dist = new HashMap<>();
            data.forEach((attr, values) -> {
                Map<String, Double> attrDist = new HashMap<>();
                values.forEach((val, c) -> {
                    attrDist.put(val, (double)c / (double)clsDist.get(lbl));
                });
                dist.put(attr, attrDist);
            });
            this.conditionalProbability.put(label, dist);
        });
    }
    
    private Map<String, Integer> countClassDistribution(
            List<Patient> patients) {
        String[] continuousAttributes = new String[]{
            "Age", "Weight", "Height", "LILA", "LP", "LK"
        };
        Map<String, Map<String, List<Integer>>> 
                attributeValues = new HashMap<>();
        Map<String, Integer> map = new HashMap<>();
        patients.stream().forEach(patient -> {
            String cls = patient.getNutritionalStatus();
            if (map.containsKey(cls)) {
                int c = map.get(cls);
                map.put(cls, ++c);
            } else {
                map.put(cls, 1);
            }
            
            Map<String, List<Integer>> val;
            if (attributeValues.containsKey(cls)) {
                val = attributeValues.get(cls);
            } else {
                val = new HashMap<>();
            }
            
            for (String cAttr : continuousAttributes) {
                List<Integer> cVal;
                if (val.containsKey(cAttr.toLowerCase())) {
                    cVal = val.get(cAttr.toLowerCase());
                } else {
                    cVal = new ArrayList<>();
                }
                cVal.add((Integer)Reflector.callUserFunc(
                                Patient.class, patient, "get" + cAttr));
                val.put(cAttr.toLowerCase(), cVal);
            }
            attributeValues.put(cls, val);
        });
        
        attributeValues.forEach((k, v) -> {
            final Map<String, Double> attrMean = new HashMap<>();
            final Map<String, Double> attrStd = new HashMap<>();
            v.forEach((kk, vv) -> {
                Integer[] cv = vv.toArray(new Integer[vv.size()]);
                attrMean.put(kk, MathFx.mean(cv));
                attrStd.put(kk, MathFx.standardDeviation(cv));
            });
            this.attributesMean.put(k, attrMean);
            this.attributesStd.put(k, attrStd);
        });
        
        return map;
    }
    
    private void countSampleDistribution(int[][] trainMatrix) {
        this.sampleDistribution = new HashMap<>();
        this.sampleDistribution.put(Integer.toString(1), new HashMap<>());
        this.sampleDistribution.put(Integer.toString(2), new HashMap<>());
        this.sampleDistribution.put(Integer.toString(3), new HashMap<>());
        this.sampleDistribution.put(Integer.toString(4), new HashMap<>());
        
        final int ROW_LENGTH = trainMatrix[0].length;
        for (int i = 0; i < trainMatrix.length; i++) {
            String label = Integer.toString(trainMatrix[i][ROW_LENGTH - 1]);
            this.incrementDistribution(label, "gender", trainMatrix[i][0]);
            this.incrementDistribution(label, "age", trainMatrix[i][1]);
            this.incrementDistribution(label, "weight", trainMatrix[i][2]);
            this.incrementDistribution(label, "height", trainMatrix[i][3]);
            this.incrementDistribution(label, "lila", trainMatrix[i][4]);
            this.incrementDistribution(label, "lp", trainMatrix[i][5]);
            this.incrementDistribution(label, "lk", trainMatrix[i][6]);
        }
    }
        
    private void incrementDistribution(String label, String attr, int value) {
        Map<String, Map<String, Integer>> map = 
                this.sampleDistribution.get(label);
        String strVal = Integer.toString(value);
        Map <String, Integer> dist = new HashMap<>();
        if (map.containsKey(attr)) {
            dist = map.get(attr);
            if (dist.containsKey(strVal)) {
                dist.computeIfPresent(strVal, (k, v) -> v + 1);
            } else {
                dist.put(strVal, 1);
            }
        } else {
            dist.put(strVal, 1);
        }
        map.put(attr, dist);
        this.sampleDistribution.put(label, map);
    }
    
    private Double getConditionalProbability(String label, String attr,
            String value) {
        Map<String, Double> map = this.conditionalProbability.get(label)
                .get(attr);
        return map.containsKey(value) ? map.get(value) : 1.0;
    }
    
    public String predict(Patient patient) {
        int[] sample = this.p.preprocess(patient);
        Map<String, Double> probabilities = new HashMap<>();
        String[] attributes = new String[] {
            "weight", "height", "lila", "lp", "lk"
        };
        List<String> continuousAttributes = Arrays.asList(new String[] {
            "age", "weight", "height", "lila", "lp", "lk"
        });
        
        this.priorProbability.forEach((key, value) -> {
            String keyConvert = "";
            switch (key) {
                case "sangat kurus":
                    keyConvert = "1";
                    break;
                
                case "kurus":
                    keyConvert = "2";
                    break;
                
                case "normal":
                    keyConvert = "3";
                    break;
                    
                case "obesitas":
                    keyConvert = "4";
                    break;
            }
            double posteriorProbability = this.priorProbability.get(key);
            for (int i = 0; i < attributes.length; i++) {
                
                double prob = 0.0;
//                if (continuousAttributes.contains(attributes[i])) {
//                    System.out.println(Arrays.toString(this.attributesStd.get(key).keySet().toArray()));
//                    double std = 
//                            this.attributesStd.get(key).get(attributes[i]);
//                    double mean = 
//                            this.attributesMean.get(key).get(attributes[i]);
//                    prob = (1 / (Math.sqrt(2 * Math.PI) * std)) * 
//                            Math.exp(-(Math.pow(sample[i] - mean, 2)) / 
//                                    (2 * std));
//                    
//                    System.out.println(prob);
//                } else {
                    prob = this.getConditionalProbability(
                        keyConvert, attributes[i], Integer.toString(sample[i]));
//                }
                
                posteriorProbability *= prob;
            }
            probabilities.put(key, posteriorProbability);
        });
        
        List<Map.Entry<String, Double>> result = MathFx
                .sortMapDouble(probabilities);
        return result.get(0).getKey();
    }
}
