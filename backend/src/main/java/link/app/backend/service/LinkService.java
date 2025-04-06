package link.app.backend.service;

import java.util.List;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import link.app.backend.entity.Link;
import link.app.backend.exception.LinkNotFoundException;
import link.app.backend.repository.LinkRepository;
import link.app.backend.request.LinkRequest;
import link.app.backend.response.LinkResponse;

@Service
public class LinkService implements ILinkService {

    private final LinkRepository linkRepository;
    private final IBrowserAvailabilityService browserAvailabilityService;
    private final IImageCompressionService imageCompressionService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public LinkService(IBrowserAvailabilityService browserAvailabilityService, IImageCompressionService imageCompressionService, LinkRepository linkRepository, EntityManager entityManager, ModelMapper modelMapper) {
        this.browserAvailabilityService = browserAvailabilityService;
        this.imageCompressionService = imageCompressionService;
        this.linkRepository = linkRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public Link createLink(LinkRequest request) {
        boolean isAvailableFirefox = browserAvailabilityService.checkFirefoxAvailability(request.getUrl());
        boolean isAvailableChrome = browserAvailabilityService.checkChromeAvailability(request.getUrl());
        Link newLink = Link.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .url(request.getUrl())
            .imageData(imageCompressionService.compressImage(request.getImageData()))
            .isAvailableFirefox(isAvailableFirefox)
            .isAvailableChrome(isAvailableChrome)
            .isActive(request.isActive())
            .build();
        return linkRepository.save(newLink);
    }

    @Override
    public Link updateLink(Long id, LinkRequest request) {
        Link updatedLink = linkRepository.findById(id)
            .map(link -> {
                link.setTitle(request.getTitle());
                link.setDescription(request.getDescription());
                link.setUrl(request.getUrl());
                link.setImageData(imageCompressionService.compressImage(request.getImageData()));
                return link;
            })
            .orElseThrow(() -> new LinkNotFoundException(id));

        return linkRepository.save(updatedLink);
    }

    @Override
    public List<Link> getLinkHistory(Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery()
            .forRevisionsOfEntity(Link.class, true, true)
            .add(AuditEntity.property("id").eq(id));
        return query.getResultList();
    }

    @Override
    public LinkResponse mapToResponse(Link link) {
        LinkResponse response = modelMapper.map(link, LinkResponse.class);
        return response;
    }

    @Override
    public Link getById(Long id) {
        return linkRepository.findById(id)
            .orElseThrow(() -> new LinkNotFoundException(id));
    }

    @Override
    public List<Link> getAll() {
        return linkRepository.findAll();
    }
    
}
