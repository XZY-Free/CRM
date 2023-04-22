package com.XZY_SUNSHINE.crm.workbench.Contacts.Service.Impl;

import com.XZY_SUNSHINE.crm.workbench.Contacts.Service.ContactsService;
import com.XZY_SUNSHINE.crm.workbench.Contacts.mapper.ContactsMapper;
import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.Contacts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public int saveContacts(Contacts contacts) {
        return contactsMapper.insert(contacts);
    }
}
