package pl.coderslab.pokersessionmanager.repository

import jakarta.annotation.security.PermitAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

//@DataJpaTest(properties = "spring.h2.console.enabled=true")
//@DataJpaTest()
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.h2.console.enabled=true")

class UserRepositoryTest extends Specification {

    @Autowired
    private UserRepository userRepository

    private static final USER_EMAIL = "testEmail@test.com"

    private static final FAKE_EMAIL = "fakeEmail@test.com"

    private static final USER_NEW_FIRST_NAME = "updatedFirstName"

    private static final USER_NEW_LAST_NAME = "updatedLastName"

    private static final USER_NEW_USERNAME = "updatedUserName"

    def "should check if method findByEmail is returning correct value based on '#emailParameter'"() {

        when:
        def user = userRepository.findByEmail(emailParameter)

        then:
        user.present == present

        where:

        emailParameter | present
        FAKE_EMAIL     | false
        USER_EMAIL     | true
        null           | false
    }

    def "should correctly update user details"() {
        given:
        def newName = "12345"
        def user = userRepository.findByEmail(USER_EMAIL).orElseThrow(() -> new RuntimeException("User for updating not found."))
        when:
        userRepository.updateUserDetails(user.id, newName, newName, newName)

        then:
        def userUpdated = userRepository.findByEmail(USER_EMAIL).orElseThrow(() -> new RuntimeException("User updating goes wrong."))
        userUpdated.username == "12345"
    }
}
