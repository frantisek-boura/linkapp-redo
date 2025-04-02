package link.app.backend.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import link.app.backend.entity.Link;
import link.app.backend.service.IBrowserAvailabilityService;
import link.app.backend.service.ILinkHistoryService;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class LinkListener {

    private static Log log = LogFactory.getLog(LinkListener.class);

    private final ILinkHistoryService linkHistoryService;
    private final IBrowserAvailabilityService browserAvailabilityService;

    public LinkListener(ILinkHistoryService linkHistoryService, IBrowserAvailabilityService browserAvailabilityService) {
        this.linkHistoryService = linkHistoryService;
        this.browserAvailabilityService = browserAvailabilityService;
    }

    @PrePersist
    private void beforeLinkCreated(Link link) {
        link.setAvailableChrome(browserAvailabilityService.checkChromeAvailability(link.getUrl()));
        link.setAvailableFirefox(browserAvailabilityService.checkFirefoxAvailability(link.getUrl()));
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
