package com.example.contactmanager.dao;

import com.example.contactmanager.entity.Contact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class ContactDaoImpl implements ContactDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Contact> getAllContacts() {
        TypedQuery<Contact> query = entityManager.createQuery("SELECT c FROM Contact c", Contact.class);
        return query.getResultList();
    }

    @Override
    public Optional<Contact> getContactById(Long id) {
        Contact contact = entityManager.find(Contact.class, id);
        return Optional.ofNullable(contact);
    }

    @Override
    public Contact addContact(Contact contact) {
        entityManager.persist(contact);
        return contact;
    }

    @Override
    public Contact updatePhone(Long id, String phone) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact != null) {
            contact.setPhone(phone);
            entityManager.merge(contact);
        }
        return contact;
    }

    @Override
    public Contact updateEmail(Long id, String email) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact != null) {
            contact.setEmail(email);
            entityManager.merge(contact);
        }
        return contact;
    }

    @Override
    public void deleteContact(Long id) {
        Contact contact = entityManager.find(Contact.class, id);
        if (contact != null) {
            entityManager.remove(contact);
        }
    }
}

