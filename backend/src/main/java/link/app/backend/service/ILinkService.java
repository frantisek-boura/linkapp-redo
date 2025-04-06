package link.app.backend.service;

import java.util.List;

import link.app.backend.entity.Link;
import link.app.backend.request.LinkRequest;
import link.app.backend.response.LinkResponse;

public interface ILinkService {

    List<Link> getAll();
    Link getById(Long id);
    Link createLink(LinkRequest request);
    Link updateLink(Long id, LinkRequest request);
    List<Link> getLinkHistory(Long id);
    LinkResponse mapToResponse(Link link);

}
