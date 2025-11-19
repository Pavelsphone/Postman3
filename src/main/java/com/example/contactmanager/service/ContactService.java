package com.example.contactmanager.service;

import com.example.contactmanager.dao.ContactDao;
import com.example.contactmanager.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactDao contactDao;

    public void uploadContactsFromCsv(MultipartFile file) throws Exception {
        List<Contact> contacts = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;

        // Пропускаем заголовок, если есть
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 3) {
                Contact contact = new Contact();
                contact.setFirstName(extractFirstName(data[0].trim()));
                contact.setLastName(extractLastName(data[0].trim()));
                contact.setPhone(data[1].trim());
                contact.setEmail(data[2].trim());
                contacts.add(contact);
            }
        }
        reader.close();

        // Batch insert
        saveContactsBatch(contacts);
    }

    private String extractFirstName(String fullName) {
        String[] parts = fullName.split(" ", 2);
        return parts.length > 0 ? parts[0] : "";
    }

    private String extractLastName(String fullName) {
        String[] parts = fullName.split(" ", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    private void saveContactsBatch(List<Contact> contacts) {
        // Batch size
        int batchSize = 50;

        for (int i = 0; i < contacts.size(); i += batchSize) {
            int end = Math.min(i + batchSize, contacts.size());
            List<Contact> batch = contacts.subList(i, end);

            for (Contact contact : batch) {
                contactDao.addContact(contact);
            }
        }
    }
}

