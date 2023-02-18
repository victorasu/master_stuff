package com.example.glassfishproj.Interfaces;

import com.example.glassfishproj.Models.Vehicle;

import java.util.List;

public interface VehicleService {
    public void addVehicle (String make, String model, double price, String registration);
    public List<Vehicle> findAll();
    public void deleteVehicle(long id);
    public Vehicle find(long id);
}
