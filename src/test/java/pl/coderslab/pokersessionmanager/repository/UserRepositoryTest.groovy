package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "spring.h2.console.enabled=true")

class UserRepositoryTest extends Specification {

    @Autowired
    private UserRepository userRepository

    def private static final USER_ID = 1L

    def """should check if repository method findByEmail(String email) is correctly
 returning user object when we pass valid argument"""() {
        given:
        def userEmail = "testEmail@test.com"

        when:
        def userByEmail = userRepository.findByEmail(userEmail)

        then:
        userByEmail.isPresent()
    }

    def """should check if repository method
 updateUserDetails(@Param("userId") Long userId,
                   @Param("firstName") String firstName,
                   @Param("lastName") String lastName,
                   @Param("username") String username)
 is correctly updating user details"""() {
        given:
        def userBeforeEditing = userRepository.findById(USER_ID).orElse(null)

        when:
        userRepository.updateUserDetails(USER_ID, "newFirstName", "newLastName", userBeforeEditing.username)

        then:
        def userAfterEditing = userRepository.findById(USER_ID).orElse(null)

        and: "id and username without changes"
        verifyAll {
            userBeforeEditing.id == userAfterEditing.id

            userBeforeEditing.firstName != userAfterEditing.firstName
            userBeforeEditing.lastName != userAfterEditing.lastName

            userBeforeEditing.username == userAfterEditing.username
        }
    }
}
