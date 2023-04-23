package com.XZY_SUNSHINE.crm.workbench.Customer.Service;

import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.Customer;

import java.util.List;

public interface CustomerService {
    List<String> queryCustomerByName(String name);
    int saveCustomer(Customer customer);
}
