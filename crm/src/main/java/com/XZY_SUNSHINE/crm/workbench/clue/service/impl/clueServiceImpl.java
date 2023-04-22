package com.XZY_SUNSHINE.crm.workbench.clue.service.impl;

import com.XZY_SUNSHINE.crm.commons.pojo.CreatePojo;
import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.constants;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.workbench.Contacts.mapper.ContactsActivityRelationMapper;
import com.XZY_SUNSHINE.crm.workbench.Contacts.mapper.ContactsMapper;
import com.XZY_SUNSHINE.crm.workbench.Contacts.mapper.ContactsRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.Contacts;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.ContactsActivityRelation;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.ContactsRemark;
import com.XZY_SUNSHINE.crm.workbench.Customer.mapper.CustomerMapper;
import com.XZY_SUNSHINE.crm.workbench.Customer.mapper.CustomerRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.Customer;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.CustomerRemark;
import com.XZY_SUNSHINE.crm.workbench.Tran.mapper.TranMapper;
import com.XZY_SUNSHINE.crm.workbench.Tran.mapper.TranRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.Tran;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranRemark;
import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueActivityRelationMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.mapper.ClueRemarkMapper;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueActivityRelation;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;
import com.XZY_SUNSHINE.crm.workbench.clue.service.clueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class clueServiceImpl implements clueService {
    @Autowired
    private TranRemarkMapper tranRemarkMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public void toConvert(Map map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(constants.SESSION_USER);
        Clue clue = clueMapper.selectByPrimaryKey(clueId);
        Customer customer = CreatePojo.newCustomer(clue,user);
        customerMapper.insert(customer);
        Contacts contacts = CreatePojo.newContacts(clue,user,customer.getId());
        contactsMapper.insert(contacts);
        System.out.println("正在处理备注表。。。。。。");
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueIdForConvert(clueId);
        CustomerRemark customerRemark=null;
        ContactsRemark contactsRemark =null;
        List<CustomerRemark> customerRemarkList = new ArrayList<>();
        List<ContactsRemark> contactsRemarkList = new ArrayList<>();
        for (int i1 = 0; i1 < clueRemarkList.size(); i1++) {
            customerRemark = CreatePojo.newCustomerRemark(customer.getId(), user.getId(), clueRemarkList.get(i1));
            contactsRemark =CreatePojo.newContactsRemark(contacts.getId(), user.getId(),clueRemarkList.get(i1));
            customerRemarkList.add(customerRemark);
            contactsRemarkList.add(contactsRemark);
        }
        customerRemarkMapper.insertByList(customerRemarkList);
        contactsRemarkMapper.insertByList(contactsRemarkList);
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectByClueId(clueId);
        List<ContactsActivityRelation> activityRelationList = new ArrayList<>();
        ContactsActivityRelation contactsActivityRelation=null;
        for (int i1 = 0; i1 < clueActivityRelationList.size(); i1++) {
            contactsActivityRelation = CreatePojo.newContactsActivityRelation(clueActivityRelationList.get(i1).getActivityId(),contacts.getId(),uuid.getUUID());
            activityRelationList.add(contactsActivityRelation);
        }
        contactsActivityRelationMapper.insertByList(activityRelationList);
        String isCreate = (String) map.get("isCreate");
        if (isCreate.equals("true")){
            System.out.println("创建交易中。。。。。。");
            Tran tran = CreatePojo.newTran(map,user.getId(),contacts.getId(), customer.getId());
            tranMapper.insert(tran);
            // 将线索下所有的备注转换到交易备注表下。
            List<TranRemark> tranRemarkList = new ArrayList<>();
            TranRemark remark = null;
            for (int i1 = 0; i1 < clueRemarkList.size(); i1++) {
                remark= CreatePojo.newTranRemark(clueRemarkList.get(i1), tran.getId());
                tranRemarkList.add(remark);
            }
            tranRemarkMapper.insertByList(tranRemarkList);
        }
        clueRemarkMapper.deleteByClueId(clueId);
        clueActivityRelationMapper.deleteClueActivityRelationByClueId(clueId);


    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int deleteClueByIds(String[] ids) {
        return clueMapper.deleteClueByIds(ids);
    }

    @Override
    public int updateClue(Clue clue) {
        return clueMapper.updateByPrimaryKey(clue);
    }

    @Override
    public Clue queryByPrimaryKey(String id) {
        return clueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertClue(Clue clue) {
        return clueMapper.insert(clue);
    }

    @Override
    public int queryCluesForPageCounts(Map map) {
        return clueMapper.selectCluesForPageCounts(map);
    }

    @Override
    public List<Clue> queryCluesForPage(Map map) {
        return clueMapper.selectCluesForPage(map);
    }
}
