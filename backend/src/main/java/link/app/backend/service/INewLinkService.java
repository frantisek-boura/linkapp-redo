package link.app.backend.service;

import link.app.backend.dto.LinkDto;
import link.app.backend.entity.Link;

public interface INewLinkService {

    Link createLink(LinkDto linkDto);

}
