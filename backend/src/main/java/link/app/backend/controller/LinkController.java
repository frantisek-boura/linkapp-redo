package link.app.backend.controller;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import link.app.backend.assembler.LinkModelAssembler;
import link.app.backend.dto.LinkDto;
import link.app.backend.entity.Link;
import link.app.backend.exception.LinkNotFoundException;
import link.app.backend.repository.LinkRepository;
import link.app.backend.service.IBrowserAvailabilityService;
import link.app.backend.service.ILinkHistoryService;

@RestController
public class LinkController {
    
    private final LinkRepository repository;
    private final LinkModelAssembler assembler;
    private final ILinkHistoryService history;
    private final IBrowserAvailabilityService browserAvailabilityService;

    public LinkController(LinkRepository repository, LinkModelAssembler assembler, ILinkHistoryService history, IBrowserAvailabilityService browserAvailabilityService) {
        this.repository = repository;
        this.assembler = assembler;
        this.history = history;
        this.browserAvailabilityService = browserAvailabilityService;
    }

    @GetMapping("/{id}")
    public EntityModel<Link> getLinkById(@PathVariable Long id) {
        Link link = repository.findById(id)
            .orElseThrow(() -> new LinkNotFoundException(id));
        return assembler.toModel(link);
    }

    @GetMapping("/{id}/history")
    public CollectionModel<EntityModel<Link>> getHistory(@PathVariable Long id) {
        List<EntityModel<Link>> linkHistory = history.getLinkHistory(id).stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(linkHistory,
            linkTo(methodOn(LinkController.class).getHistory(id)).withSelfRel());
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Link>> getLinks() {
        List<EntityModel<Link>> links = repository.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(links,
            linkTo(methodOn(LinkController.class).getLinks()).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Link>> editLink(@PathVariable Long id, @RequestBody LinkDto newLink) {
        Link updatedLink = repository.findById(id)
            .map(link -> {
                link.setTitle(newLink.title());
                link.setDescription(newLink.description());
                link.setUrl(newLink.url());
                link.setImageData(newLink.imageData());
                return repository.save(link);
            })
            .orElseGet(() -> {
                boolean isAvailableFirefox = browserAvailabilityService.checkFirefoxAvailability(newLink.url());
                boolean isAvailableChrome = browserAvailabilityService.checkChromeAvailability(newLink.url());
                Link link = new Link(newLink.title(), newLink.description(), newLink.url(), newLink.imageData(), newLink.isActive());
                link.setAvailableFirefox(isAvailableFirefox);
                link.setAvailableChrome(isAvailableChrome);
                return repository.save(link);
            });
        EntityModel<Link> entityModel = assembler.toModel(updatedLink);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Link>> createLink(@RequestBody LinkDto newLink) {
        boolean isAvailableFirefox = browserAvailabilityService.checkFirefoxAvailability(newLink.url());
        boolean isAvailableChrome = browserAvailabilityService.checkChromeAvailability(newLink.url());
        Link link = new Link(newLink.title(), newLink.description(), newLink.url(), newLink.imageData(), newLink.isActive());
        link.setAvailableFirefox(isAvailableFirefox);
        link.setAvailableChrome(isAvailableChrome);
        repository.save(link);

        return ResponseEntity
            .created(linkTo(methodOn(LinkController.class).getLinkById(link.getId())).toUri())
            .body(assembler.toModel(link));
    }

}
