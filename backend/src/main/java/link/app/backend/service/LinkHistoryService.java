package link.app.backend.service;

import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import link.app.backend.entity.Link;

@Service
public class LinkHistoryService implements ILinkHistoryService {
 
    private final EntityManager entityManager;

    public LinkHistoryService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Link> getLinkHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery()
            .forRevisionsOfEntity(Link.class, true, true)
            .add(AuditEntity.property("id").eq(id));
        return query.getResultList();
    }
    
}
