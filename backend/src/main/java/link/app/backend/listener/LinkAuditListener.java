package link.app.backend.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import link.app.backend.entity.Link;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class LinkAuditListener {

    private static Log log = LogFactory.getLog(LinkAuditListener.class);
    
    @PostPersist
    private void afterLinkCreated(Link link) {
        log.info("Link Created: " + link.getId());
        // TODO: Service to add this link to the history table
        // with action Created
    }

    @PostUpdate
    private void afterLinkUpdated(Link link) {
        log.info("Link Updated: " + link.getId());
        // TODO: Service to add this link to the history table
        // with action Updated 
    }

    @PostRemove
    private void afterLinkRemoved(Link link) {
        log.info("Link Removed: " + link.getId());
        // TODO: Service to add this link to the history table
        // with action Removed
    }

}
