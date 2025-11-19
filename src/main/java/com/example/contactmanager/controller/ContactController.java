package com.example.contactmanager.controller;

import com.example.contactmanager.dao.ContactDao;
import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactDao contactDao;

    @Autowired
    private ContactService contactService;

    @GetMapping
    public List<Contact> getAllContacts() {
        return contactDao.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable Long id) {
        Optional<Contact> contact = contactDao.getContactById(id);
        return contact.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Contact createContact(@RequestBody Contact contact) {
        return contactDao.addContact(contact);
    }

    @PutMapping("/{id}/phone")
    public ResponseEntity<Contact> updatePhone(@PathVariable Long id, @RequestBody String phone) {
        Contact updatedContact = contactDao.updatePhone(id, phone);
        if (updatedContact != null) {
            return ResponseEntity.ok(updatedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<Contact> updateEmail(@PathVariable Long id, @RequestBody String email) {
        Contact updatedContact = contactDao.updateEmail(id, email);
        if (updatedContact != null) {
            return ResponseEntity.ok(updatedContact);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        contactDao.deleteContact(id);
        return ResponseEntity.noContent().build();
    }

    // Новый эндпоинт для загрузки CSV
    @PostMapping("/upload-csv")
    public ResponseEntity<String> uploadContactsFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            contactService.uploadContactsFromCsv(file);
            return ResponseEntity.ok("Файл успешно загружен и данные сохранены в базу");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}


