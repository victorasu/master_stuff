package com.example.wildflyproj.Interfaces;

import com.example.wildflyproj.Models.Vehicle;

import java.util.List;

public interface VehicleService {
    public void addVehicle (String make, String model, double price, String registration);
    public void deleteVehicle(long id);
    public Vehicle find(long id);
    public List<Vehicle> findAll();
}
