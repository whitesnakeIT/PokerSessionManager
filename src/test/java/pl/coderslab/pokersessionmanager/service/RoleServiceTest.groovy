package pl.coderslab.pokersessionmanager.service

import org.springframework.boot.test.context.SpringBootTest
import pl.coderslab.pokersessionmanager.entity.Role
import pl.coderslab.pokersessionmanager.enums.RoleName
import pl.coderslab.pokersessionmanager.repository.RoleRepository
import spock.lang.Specification

@SpringBootTest
class RoleServiceTest extends Specification {

    private static final ROLE_USER = RoleName.ROLE_USER

    def private final roleRepository = Mock(RoleRepository)

    def private final roleService = new RoleService(roleRepository)

    def private final mockedRole() {
        def role = new Role()
        role.id = 1
        role.name = ROLE_USER

        role
    }

    def """should check if service method findByName(RoleName roleName) is returning
 Role entity object based on roleName"""() {
        roleRepository.findByName(_ as RoleName) >> Optional.of(mockedRole())

        when:
        def roleByName = roleService.findByName(ROLE_USER)

        then:
        verifyAll (roleByName) {
            id != null
            name != null
        }
    }

    def """should check if service method findByName(RoleName roleName) is throwing
 an exception when roleName is null"""() {
        when:
        roleService.findByName(null)

        then:
        def exception = thrown(RuntimeException)
        exception.message == "Searching for role failed. Role name is null."
    }
}
