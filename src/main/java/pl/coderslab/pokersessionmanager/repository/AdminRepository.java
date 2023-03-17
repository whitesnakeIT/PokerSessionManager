package pl.coderslab.pokersessionmanager.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.user.Admin;


@Repository
@Transactional
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
