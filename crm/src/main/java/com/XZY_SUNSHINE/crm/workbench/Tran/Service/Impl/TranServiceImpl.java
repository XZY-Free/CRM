package com.XZY_SUNSHINE.crm.workbench.Tran.Service.Impl;

import com.XZY_SUNSHINE.crm.commons.pojo.CreatePojo;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.workbench.Customer.mapper.CustomerMapper;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.Customer;
import com.XZY_SUNSHINE.crm.workbench.Tran.Service.TranService;
import com.XZY_SUNSHINE.crm.workbench.Tran.mapper.TranMapper;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.Tran;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Override
    public void createTran(Map map) {
        User user = (User) map.get(constants.SESSION_USER);
        String contactsId = (String) map.get("contactsId");
        String customerName = (String) map.get("customerName");
        String customer = customerMapper.selectCustomer(customerName);
        System.out.println("=================================================");
        System.out.println(customer);
        System.out.println("=================================================");
        if (customer==null){
            Customer newCustomer=new Customer();
            newCustomer.setId(uuid.getUUID());
            newCustomer.setName(customerName);
            customerMapper.insertSelective(newCustomer);
            customer=newCustomer.getId();
        }
        Tran tran= CreatePojo.newTran(map,user.getId(),contactsId,customer);
        tran.setDescription((String)map.get("description"));
        tran.setContactSummary((String)map.get("contactSummary"));
        tran.setNextContactTime((String)map.get("nextContactTime"));
        tran.setType((String)map.get("tranType"));
        tran.setSource((String)map.get("source"));
        tranMapper.insert(tran);
    }
}
