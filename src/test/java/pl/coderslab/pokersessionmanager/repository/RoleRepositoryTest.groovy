package pl.coderslab.pokersessionmanager.repository

import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import pl.coderslab.pokersessionmanager.entity.Role
import pl.coderslab.pokersessionmanager.enums.RoleName
import spock.lang.Shared
import spock.lang.Specification

@DataJpaTest
@EnableSharedInjection
class RoleRepositoryTest extends Specification {

    @Autowired
    private RoleRepository roleRepository


    def "should return Role by role name"() {
        given:
        def roleAdminName = RoleName.ROLE_ADMIN.toString()

        when:
        Role roleAdmin = roleRepository.findByName(roleAdminName)

        then:
        roleAdmin.name == RoleName.ROLE_ADMIN
    }
}
