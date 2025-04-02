package link.app.backend.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import link.app.backend.entity.Link;
import link.app.backend.entity.LinkHistory;
import link.app.backend.enums.ActionType;
import link.app.backend.repository.LinkHistoryRepository;


@Service
public class LinkHistoryService implements ILinkHistoryService {

    private final LinkHistoryRepository linkHistoryRepository;

    public LinkHistoryService(LinkHistoryRepository linkHistoryRepository) {
        this.linkHistoryRepository = linkHistoryRepository;
    }

    private void logLinkWithAction(Link link, ActionType action) {
        LinkHistory linkHistory = new LinkHistory(
            link,
            action,
            Timestamp.from(java.time.Instant.now())
        );
        linkHistoryRepository.save(linkHistory);
    }

    @Override
    public void logLinkCreated(Link link) {
        logLinkWithAction(link, ActionType.CREATED);
    }

    @Override
    public void logLinkUpdated(Link link) {
        logLinkWithAction(link, ActionType.UPDATED);
    }

    @Override
    public void logLinkRemoved(Link link) {
        logLinkWithAction(link, ActionType.REMOVED);
    }

}
