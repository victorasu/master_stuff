package com.example.wildflyproj.Interfaces;

import com.example.wildflyproj.Models.Vehicle;
import com.example.wildflyproj.Models.Customer;

import java.util.List;

public interface CustomerService {
    public void addCustomer (String first_name, String last_name);
    public void deleteCustomer(long id);
    public Customer find(long id);
    public Vehicle findVehicle(long id);
    public void addVehicleToCustomer(long customer, long vehicle);
    public void removeVehicleFromCustomer(long vehicle);
    public List<Customer> findAll();
}
