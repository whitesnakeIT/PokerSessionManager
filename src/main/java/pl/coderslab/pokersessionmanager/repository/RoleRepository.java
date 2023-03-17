package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Role;


@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(nativeQuery = true, value = "select * from roles  where name = :roleName")
    Role findByName(@Param("roleName") String roleName);
}