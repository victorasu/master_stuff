package com.example.glassfishproj.Beans;

import com.example.glassfishproj.Interfaces.CustomerService;
import com.example.glassfishproj.Models.Vehicle;
import com.example.glassfishproj.Models.Customer;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
@Remote(CustomerService.class)
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

    public List<Customer> findAll() {
        TypedQuery<Customer> query = manager.createQuery("select p from Customer p", Customer.class);
        return query.getResultList();
    }

    @Override
    public void deleteCustomer(long id) {
        Customer customer = find(id);
        if(customer != null){

            Vehicle vehicle = customer.getVehicle();
            if(vehicle != null){
                vehicle.setCustomer(null);
                manager.persist(vehicle);
            }

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

    @Override
    public void addVehicleToCustomer(long customer, long vehicle) {
        Vehicle veh = findVehicle(vehicle);
        Customer cust = find(customer);

        cust.setVehicle(veh);
        veh.setCustomer(cust);

        manager.persist(veh);
    }

    @Override
    public void removeVehicleFromCustomer(long vehicle) {
        Vehicle veh = findVehicle(vehicle);
        veh.getCustomer().removeVehicle();
        veh.setCustomer(null);

        manager.persist(veh);
    }
}
