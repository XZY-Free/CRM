package com.XZY_SUNSHINE.crm.commons.pojo;

import com.XZY_SUNSHINE.crm.commons.utils.DateFormat;
import com.XZY_SUNSHINE.crm.commons.utils.uuid;
import com.XZY_SUNSHINE.crm.settings.pojo.User;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.Contacts;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.ContactsActivityRelation;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.ContactsRemark;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.Customer;
import com.XZY_SUNSHINE.crm.workbench.Customer.pojo.CustomerRemark;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.Tran;
import com.XZY_SUNSHINE.crm.workbench.Tran.pojo.TranRemark;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.Clue;
import com.XZY_SUNSHINE.crm.workbench.clue.pojo.ClueRemark;

import java.util.Date;
import java.util.Map;

public class CreatePojo {
    public static Customer newCustomer(Clue clue, User user){
        Customer customer = new Customer();
        customer.setId(uuid.getUUID());
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setCreateBy(user.getId());
        customer.setDescription(clue.getDescription());
        customer.setCreateTime(DateFormat.dateFormatTime(new Date()));
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setOwner(clue.getOwner());
        customer.setPhone(clue.getPhone());
        customer.setWebsite(clue.getWebsite());
        return customer;
    }
    public static Contacts newContacts(Clue clue, User user,String id){
        Contacts contacts = new Contacts();
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        contacts.setCreateBy(user.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setCreateTime(DateFormat.dateFormatTime(new Date()));
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(id);
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(uuid.getUUID());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        return contacts;
    }
    public static CustomerRemark newCustomerRemark(String customerId, String userId, ClueRemark clueRemark){
        CustomerRemark customerRemark = new CustomerRemark();
        customerRemark.setCustomerId(customerId);
        customerRemark.setCreateBy(userId);
        customerRemark.setCreateTime(DateFormat.dateFormatTime(new Date()));
        customerRemark.setId(uuid.getUUID());
        customerRemark.setNoteContent(clueRemark.getNoteContent());
        return customerRemark;
    }
    public static ContactsRemark newContactsRemark(String contactsId, String userId, ClueRemark clueRemark){
        ContactsRemark contactsRemark = new ContactsRemark();
        contactsRemark.setContactsId(contactsId);
        contactsRemark.setCreateBy(userId);
        contactsRemark.setCreateTime(DateFormat.dateFormatTime(new Date()));
        contactsRemark.setId(uuid.getUUID());
        contactsRemark.setNoteContent(clueRemark.getNoteContent());
        return contactsRemark;
    }
    public static ContactsActivityRelation newContactsActivityRelation(String activityId,String contactsId,String id){
        ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
        contactsActivityRelation.setActivityId(activityId);
        contactsActivityRelation.setContactsId(contactsId);
        contactsActivityRelation.setId(id);
        return contactsActivityRelation;
    }
    public static Tran newTran(Map map,String userId,String contactsId,String customerId){
        Tran tran = new Tran();
        tran.setActivityId((String)map.get("activityId"));
        tran.setContactsId(contactsId);
        tran.setCreateBy(userId);
        tran.setCreateTime(DateFormat.dateFormatTime(new Date()));
        tran.setCustomerId(customerId);
        tran.setExpectedDate((String)map.get("expectedTime"));
        tran.setId(uuid.getUUID());
        tran.setOwner(userId);
        tran.setMoney((String)map.get("money"));
        tran.setName((String)map.get("tradeName"));
        tran.setStage((String)map.get("stage"));
        return tran;
    }
    public static TranRemark newTranRemark(ClueRemark clueRemark,String id){
        TranRemark tranRemark = new TranRemark();
        tranRemark.setCreateBy(clueRemark.getCreateBy());
        tranRemark.setCreateTime(clueRemark.getCreateTime());
        tranRemark.setEditBy(clueRemark.getEditBy());
        tranRemark.setId(uuid.getUUID());
        tranRemark.setTranId(id);
        tranRemark.setEditFlag(clueRemark.getEditFlag());
        tranRemark.setNoteContent(clueRemark.getNoteContent());
        tranRemark.setEditTime(clueRemark.getEditTime());
        return tranRemark;
    }

}
