package link.app.backend.service;

import org.springframework.stereotype.Service;

import link.app.backend.dto.LinkDto;
import link.app.backend.entity.Link;

@Service
public class NewLinkService implements INewLinkService {

    private final IBrowserAvailabilityService browserAvailabilityService;

    public NewLinkService(IBrowserAvailabilityService browserAvailabilityService) {
        this.browserAvailabilityService = browserAvailabilityService;
    }

    @Override
    public Link createLink(LinkDto linkDto) {
        boolean isAvailableFirefox = browserAvailabilityService.checkFirefoxAvailability(linkDto.url());
        boolean isAvailableChrome = browserAvailabilityService.checkChromeAvailability(linkDto.url());
        Link link = new Link(linkDto.title(), linkDto.description(), linkDto.url(), linkDto.imageData(), linkDto.isActive());
        link.setAvailableFirefox(isAvailableFirefox);
        link.setAvailableChrome(isAvailableChrome);
        return link;
    }
    
}
