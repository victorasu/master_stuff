package com.example.glassfishproj.Beans;

import com.example.glassfishproj.Interfaces.VehicleService;
import com.example.glassfishproj.Models.Vehicle;

import jakarta.ejb.Local;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;


@Stateless
@Local(VehicleService.class)
public class VehicleBean implements VehicleService {

    @PersistenceContext(unitName = "ejb")
    private EntityManager manager;

    @Override
    public void addVehicle(String make, String model, double price, String registration) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setPrice(price);
        vehicle.setRegistration(registration);


        manager.persist(vehicle);
    }

    @Override
    public void deleteVehicle(long id) {
        Vehicle vehicle = find(id);
        manager.remove(vehicle);
    }

    public Vehicle find(long id) {
        return manager.find(Vehicle.class, id);
    }

    public List<Vehicle> findAll() {
        TypedQuery<Vehicle> query = manager.createQuery("select s from Vehicle s", Vehicle.class);
        return query.getResultList();
    }

}
