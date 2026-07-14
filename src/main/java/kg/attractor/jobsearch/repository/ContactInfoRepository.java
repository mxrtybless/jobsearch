package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.ContactInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContactInfoRepository {
    private final Map<Integer, ContactInfo> contacts = new LinkedHashMap<>();
    private int nextId = 1;

    public List<ContactInfo> findByResumeId(Integer resumeId) {
        return contacts.values()
                .stream()
                .filter(contact -> contact.getResumeId().equals(resumeId))
                .toList();
    }

    public ContactInfo save(ContactInfo contactInfo) {
        if (contactInfo.getId() == null) {
            contactInfo.setId(nextId++);
        }

        contacts.put(contactInfo.getId(), contactInfo);
        return contactInfo;
    }

    public List<ContactInfo> saveAll(List<ContactInfo> contactInfos) {
        List<ContactInfo> savedContacts = new ArrayList<>();

        for (ContactInfo contactInfo : contactInfos) {
            savedContacts.add(save(contactInfo));
        }

        return savedContacts;
    }

    public void deleteByResumeId(Integer resumeId) {
        contacts.values().removeIf(contact -> contact.getResumeId().equals(resumeId));
    }
}