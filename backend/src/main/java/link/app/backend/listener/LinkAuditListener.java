package link.app.backend.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import link.app.backend.entity.Link;
import link.app.backend.service.ILinkHistoryService;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class LinkAuditListener {

    private static Log log = LogFactory.getLog(LinkAuditListener.class);

    private final ILinkHistoryService linkHistoryService;

    public LinkAuditListener(ILinkHistoryService linkHistoryService) {
        this.linkHistoryService = linkHistoryService;
    }
    
    @PostPersist
    private void afterLinkCreated(Link link) {
        log.info("Link Created: " + link.getId());
        linkHistoryService.logLinkCreated(link);
    }

    @PostUpdate
    private void afterLinkUpdated(Link link) {
        log.info("Link Updated: " + link.getId());
        linkHistoryService.logLinkUpdated(link);
    }

    @PostRemove
    private void afterLinkRemoved(Link link) {
        log.info("Link Removed: " + link.getId());
        linkHistoryService.logLinkRemoved(link);
    }

}
