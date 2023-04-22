package com.XZY_SUNSHINE.crm.workbench.Customer.Service.Impl;

import com.XZY_SUNSHINE.crm.workbench.Customer.Service.CustomerService;
import com.XZY_SUNSHINE.crm.workbench.Customer.mapper.CustomerMapper;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
        @Autowired
        private CustomerMapper customerMapper;
        @Override
        public int saveCustomer(Customer customer) {
                return customerMapper.insertSelective(customer);
        }
}
