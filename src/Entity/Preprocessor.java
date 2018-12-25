/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.List;


/**
 *
 * @author acer
 */
public class Preprocessor {
    
    public Preprocessor() {
        
    }
    
    public int mapGender(char gender) {
        return gender == 'P' ? 2 : 1;
    }
    
    public int mapAge(int age) {
        return age <= 10 ? 0 : age - 10;
    }
    
    public int mapHeight(int height) {
        int result = 0;
        if (height >= 110 && height <= 120) result = 1;
        else if (height >= 121 && height <= 130) result = 2;
        else if (height >= 131 && height <= 140) result = 3;
        else if (height >= 141 && height <= 150) result = 4;
        else if (height >= 151 && height <= 160) result = 5;
        else if (height >= 161 && height <= 170) result = 6;
        else if (height >= 171 && height <= 180) result = 7;
        else if (height >= 181 && height <= 190) result = 8;
        else if (height >= 191 && height <= 200) result = 9;
        else if (height >= 201 && height <= 210) result = 10;
        return result;
    }
    
    public int mapWeight(int weight) {
        int result = 0;
        if (weight >= 30 && weight <= 40) result = 1;
        else if (weight >= 41 && weight <= 50) result = 2;
        else if (weight >= 51 && weight <= 60) result = 3;
        else if (weight >= 61 && weight <= 70) result = 4;
        else if (weight >= 71 && weight <= 80) result = 5;
        else if (weight >= 81 && weight <= 90) result = 6;
        else if (weight >= 91 && weight <= 100) result = 7;
        else if (weight >= 101 && weight <= 110) result = 8;
        else if (weight >= 111 && weight <= 120) result = 9;
        else if (weight >= 121 && weight <= 130) result = 10;
        return result;
    }
    
    public int mapLILA(int LILA) {
        int result = 0;
        if (LILA >= 15 && LILA <= 20) result = 1;
        else if (LILA >= 21 && LILA <= 25) result = 2;
        else if (LILA >= 26 && LILA <= 30) result = 3;
        else if (LILA >= 31 && LILA <= 35) result = 4;
        else if (LILA >= 36 && LILA <= 40) result = 5;
        return result;
    }
    
    public int mapLP(int LP) {
        int result = 0;
        if (LP >= 60 && LP <= 70) result = 1;
        else if (LP >= 71 && LP <= 80) result = 2;
        else if (LP >= 81 && LP <= 90) result = 3;
        else if (LP >= 91 && LP <= 100) result = 4;
        else if (LP >= 101 && LP <= 110) result = 5;
        else if (LP >= 111 && LP <= 120) result = 6;
        else if (LP >= 121 && LP <= 130) result = 7;
        return result;
    }
    
    public int mapLK(int LK) {
        int result = 0;
        if (LK >= 40 && LK <= 45) result = 1;
        else if (LK >= 46 && LK <= 50) result = 2;
        else if (LK >= 51 && LK <= 55) result = 3;
        else if (LK >= 56 && LK <= 60) result = 4;
        return result;
    }
    
    public int mapNutritionalStatus(String nutritionalStatus) {
        int result = 0;
        if (nutritionalStatus.equals("sangat kurus")) result = 1;
        else if (nutritionalStatus.equals("kurus")) result = 2;
        else if (nutritionalStatus.equals("normal")) result = 3;
        else if (nutritionalStatus.equals("obesitas")) result = 4;
        return result;
    }
    
    public int[][] toMatrix(List<Patient> patients) {
        int size = patients.size();
        if (size <= 0) {
            throw new Error("You should not pass empty list");
        }
        
        int[][] matrix = new int[size][8];
        int i = 0;
        for (Patient patient : patients) {
            matrix[i][0] = this.mapGender(patient.getGender());
            matrix[i][1] = this.mapAge(patient.getAge());
            matrix[i][2] = this.mapWeight(patient.getWeight());
            matrix[i][3] = this.mapHeight(patient.getHeight());
            matrix[i][4] = this.mapLILA(patient.getLILA());
            matrix[i][5] = this.mapLP(patient.getLP());
            matrix[i][6] = this.mapLK(patient.getLK());
            matrix[i][7] = this.mapNutritionalStatus(patient.getNutritionalStatus());
            i++;
        }
        
        return matrix;
    }
    
}
