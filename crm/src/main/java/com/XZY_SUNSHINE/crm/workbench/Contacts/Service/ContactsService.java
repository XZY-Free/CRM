package com.XZY_SUNSHINE.crm.workbench.Contacts.Service;

import com.XZY_SUNSHINE.crm.workbench.Contacts.pojo.Contacts;

import java.util.List;

public interface ContactsService {
    List<Contacts> queryContactsByName(String name);
}
