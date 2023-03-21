package pl.coderslab.pokersessionmanager.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import pl.coderslab.pokersessionmanager.enums.RoleName
import spock.lang.Specification

@DataJpaTest
class RoleRepositoryTest extends Specification {

    @Autowired
    private RoleRepository roleRepository

    def """should check if repository method findByName(String roleName) is correctly
 returning role object when we pass valid argument"""() {
        given:
        def roleAdmin = RoleName.ROLE_ADMIN

        when:
        def optionalRole = roleRepository.findByName(roleAdmin)

        then:
        optionalRole.isPresent()
    }
}
