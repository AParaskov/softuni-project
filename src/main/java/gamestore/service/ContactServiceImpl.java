package gamestore.service;

import gamestore.model.entity.Contact;
import gamestore.model.entity.User;
import gamestore.model.service.ContactServiceModel;
import gamestore.repository.ContactRepository;
import gamestore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void addContact(ContactServiceModel contactServiceModel) {
        User user = this.userRepository
                .findByUsername(contactServiceModel.getUserName())
                .orElseThrow();

        Contact contact = this.modelMapper
                .map(contactServiceModel, Contact.class);

        contact.setUser(user);

        this.contactRepository.saveAndFlush(contact);

    }
}
