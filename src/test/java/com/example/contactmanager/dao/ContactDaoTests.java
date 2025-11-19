package com.example.contactmanager.dao;

import com.example.contactmanager.entity.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class ContactDaoTests {

    @Autowired
    private ContactDao contactDao;

    @Test
    public void testAddAndGetContact() {
        Contact contact = new Contact("Иван", "Иванов", "+79991234567", "ivan@example.com");
        Contact savedContact = contactDao.addContact(contact);

        assertNotNull(savedContact.getId());
        assertEquals("Иван", savedContact.getFirstName());

        Optional<Contact> foundContact = contactDao.getContactById(savedContact.getId());
        assertTrue(foundContact.isPresent());
        assertEquals("Иванов", foundContact.get().getLastName());
    }

    @Test
    public void testGetAllContacts() {
        Contact contact1 = new Contact("Иван", "Иванов", "+79991234567", "ivan@example.com");
        Contact contact2 = new Contact("Петр", "Петров", "+79997654321", "petr@example.com");

        contactDao.addContact(contact1);
        contactDao.addContact(contact2);

        List<Contact> contacts = contactDao.getAllContacts();
        assertTrue(contacts.size() >= 2);
    }

    @Test
    public void testUpdatePhone() {
        Contact contact = new Contact("Иван", "Иванов", "+79991234567", "ivan@example.com");
        Contact savedContact = contactDao.addContact(contact);

        Contact updatedContact = contactDao.updatePhone(savedContact.getId(), "+79999999999");
        assertNotNull(updatedContact);
        assertEquals("+79999999999", updatedContact.getPhone());
    }

    @Test
    public void testUpdateEmail() {
        Contact contact = new Contact("Иван", "Иванов", "+79991234567", "ivan@example.com");
        Contact savedContact = contactDao.addContact(contact);

        Contact updatedContact = contactDao.updateEmail(savedContact.getId(), "newemail@example.com");
        assertNotNull(updatedContact);
        assertEquals("newemail@example.com", updatedContact.getEmail());
    }

    @Test
    public void testDeleteContact() {
        Contact contact = new Contact("Иван", "Иванов", "+79991234567", "ivan@example.com");
        Contact savedContact = contactDao.addContact(contact);

        Long contactId = savedContact.getId();
        contactDao.deleteContact(contactId);

        Optional<Contact> foundContact = contactDao.getContactById(contactId);
        assertFalse(foundContact.isPresent());
    }
}

