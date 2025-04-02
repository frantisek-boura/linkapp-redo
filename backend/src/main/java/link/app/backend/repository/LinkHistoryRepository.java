package link.app.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import link.app.backend.entity.LinkHistory;

@Repository
public interface LinkHistoryRepository extends JpaRepository<LinkHistory, Long> {

}
