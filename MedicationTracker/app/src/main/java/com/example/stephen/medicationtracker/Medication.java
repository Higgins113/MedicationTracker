package com.example.stephen.medicationtracker;

/**
 * Created by Stephen on 09-03-2017.
 */
public class Medication {
    String username,medName,medType,ingestion,medQuantity;
    String medStartDate;
    int dosage;
    String duration;

    public Medication(String username,String medName,String medType,String ingestion,String medQuantity, String medStartDate, int dosage, String duration){
        this.username = username;
        this.medName=medName;
        this.medType=medType;
        this.ingestion=ingestion;
        this.medQuantity=medQuantity;
        this.medStartDate = medStartDate;
        this.dosage = dosage;
        this.duration = duration;
    }

    public Medication(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getIngestion() {
        return ingestion;
    }

    public void setIngestion(String ingestion) {
        this.ingestion = ingestion;
    }

    public String getMedQuantity() {
        return medQuantity;
    }

    public void setMedQuantity(String medQuantity) {
        this.medQuantity = medQuantity;
    }

    public String getMedStartDate() {
        return medStartDate;
    }

    public void setMedStartDate(String medStartDate) {
        this.medStartDate = medStartDate;
    }

    public int getDosage() {
        return dosage;
    }

    public void setDosage(int dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return this.medName + ". " + this.medType + " "  + this.ingestion + " " + medQuantity;
    }
}
