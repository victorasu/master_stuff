package com.example.wildflyproj.Beans;

import com.example.wildflyproj.Interfaces.CustomerService;
import com.example.wildflyproj.Models.Vehicle;
import com.example.wildflyproj.Models.Customer;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
@Local(CustomerService.class)
@NamedQuery(name="findAllCustomers", query = "select p from Customer p")
public class CustomerBean implements CustomerService {

    @PersistenceContext(unitName = "ejb")
    private EntityManager manager;

    @Override
    public void addCustomer(String first_name, String last_name) {
        Customer customer = new Customer();
        customer.setFirst_name(first_name);
        customer.setLast_name(last_name);
        manager.persist(customer);
    }

    @Override
    public void deleteCustomer(long id) {
        Customer customer = find(id);
        if(customer != null){
            manager.remove(customer);
        }
    }

    @Override
    public Customer find(long id) {
        return manager.find(Customer.class, id);
    }

    @Override
    public Vehicle findVehicle(long id) {
        return manager.find(Vehicle.class, id);
    }

    public List<Customer> findAll() {
        TypedQuery<Customer> query = manager.createQuery("select p from Customer p", Customer.class);
        return query.getResultList();
    }

    @Override
    public void addVehicleToCustomer(long customer, long vehicle) {
        Vehicle veh = findVehicle(vehicle);
        Customer cust = find(customer);

        veh.setCustomer(cust);

        manager.persist(veh);
    }

    @Override
    public void removeVehicleFromCustomer(long vehicle) {
        Vehicle veh = findVehicle(vehicle);
        veh.setCustomer(null);

        manager.persist(veh);
    }
}
