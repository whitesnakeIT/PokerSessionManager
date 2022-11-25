package pl.coderslab.pokersessionmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.pokersessionmanager.entity.Session;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository <Session,Long> {

}
