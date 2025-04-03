package link.app.backend.service;

import java.util.List;
import link.app.backend.entity.Link;

public interface ILinkHistoryService {

    List<Link> getLinkHistory(Long id);

}
