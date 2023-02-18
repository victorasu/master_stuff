package com.example.glassfishproj.Models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "vehicles")
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_vehicle;
    private String make = "";
    private String model = "";
    private double price = 0;
    private String registration;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_customer", referencedColumnName = "id_customer")
    private Customer customer;

    public Vehicle() {
    }

    public Vehicle(long id_vehicle, String make, String model, double price, String registration, Customer customer) {
        this.id_vehicle = id_vehicle;
        this.make = make;
        this.model = model;
        this.price = price;
        this.registration = registration;
        this.customer = customer;
    }

    public long getId_vehicle() {
        return id_vehicle;
    }

    public void setId_vehicle(long id_vehicle) {
        this.id_vehicle = id_vehicle;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
