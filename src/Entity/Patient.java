/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author acer
 */
public class Patient {
    
    private String name;
    private char gender;
    private int age;
    private int weight;
    private int height;
    private int LILA;
    private int LP;
    private int LK;
    private String nutritionalStatus;
    private Double distance;
    
    public Patient(String name, char gender, int age, int weight, int height, 
            int LILA, int LP, int LK, String nutritionalStatus) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.LILA = LILA;
        this.LP = LP;
        this.LK = LK;
        this.nutritionalStatus = nutritionalStatus;
        this.distance = 0.0;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the gender
     */
    public char getGender() {
        return gender;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the LILA
     */
    public int getLILA() {
        return LILA;
    }

    /**
     * @return the LP
     */
    public int getLP() {
        return LP;
    }

    /**
     * @return the LK
     */
    public int getLK() {
        return LK;
    }

    /**
     * @return the nutritionalStatus
     */
    public String getNutritionalStatus() {
        return nutritionalStatus;
    }
    
    /**
     * @return the distance
     */
    public Double getDistance() {
        return distance;
    }
    
    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
