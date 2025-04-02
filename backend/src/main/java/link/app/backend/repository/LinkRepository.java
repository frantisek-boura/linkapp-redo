package link.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import link.app.backend.entity.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {

}
