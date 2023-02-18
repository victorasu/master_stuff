package com.example.glassfishproj.Models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_customer;
    String first_name;
    String last_name;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy="customer")
    private Vehicle vehicle;

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        setVehicle(null);
    }

}
