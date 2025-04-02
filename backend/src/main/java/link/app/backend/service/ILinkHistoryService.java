package link.app.backend.service;

import link.app.backend.entity.Link;

public interface ILinkHistoryService {

    void logLinkCreated(Link link);
    void logLinkUpdated(Link link);
    void logLinkRemoved(Link link);
}
