package gamestore.ContactTest;

import gamestore.model.entity.Contact;
import gamestore.model.entity.User;
import gamestore.model.service.ContactServiceModel;
import gamestore.repository.ContactRepository;
import gamestore.repository.UserRepository;
import gamestore.service.ContactService;
import gamestore.service.ContactServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {
    private ContactService serviceToTest;
    private Contact contact;
    private User user;

    @Mock
    private ContactRepository mockContactRepository;

    @Mock
    private UserRepository mockUserRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("user");

        contact = new Contact();
        contact.setUser(user);
        contact.setQuestion("question");

        serviceToTest = new ContactServiceImpl(mockContactRepository, modelMapper, mockUserRepository);
    }

    @Test
    public void testAddContact() {
        ContactServiceModel contactServiceModel = modelMapper.map(contact, ContactServiceModel.class);
        when(mockUserRepository.findByUsername(contactServiceModel.getUserName()))
                .thenReturn(Optional.of(user));

        serviceToTest.addContact(contactServiceModel);

        Assertions.assertEquals(contact.getQuestion(), contactServiceModel.getQuestion());
    }


}
