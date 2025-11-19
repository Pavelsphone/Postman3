package com.example.contactmanager.dao;

import com.example.contactmanager.entity.Contact;
import java.util.List;
import java.util.Optional;

public interface ContactDao {
    List<Contact> getAllContacts();
    Optional<Contact> getContactById(Long id);
    Contact addContact(Contact contact);
    Contact updatePhone(Long id, String phone);
    Contact updateEmail(Long id, String email);
    void deleteContact(Long id);
}

