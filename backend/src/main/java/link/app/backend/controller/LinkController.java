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

import link.app.backend.assembler.LinkModelAssembler;
import link.app.backend.dto.LinkDto;
import link.app.backend.entity.Link;
import link.app.backend.exception.LinkNotFoundException;
import link.app.backend.repository.LinkRepository;
import link.app.backend.service.ILinkHistoryService;
import link.app.backend.service.INewLinkService;

@RestController
@RequestMapping("/links")
public class LinkController {
    
    private final LinkRepository linkRepository;
    private final LinkModelAssembler linkModelAssembler;
    private final ILinkHistoryService linkHistoryService;
    private final INewLinkService newLinkService;

    public LinkController(LinkRepository repository, LinkModelAssembler assembler, ILinkHistoryService history, INewLinkService newLinkService) {
        this.linkRepository = repository;
        this.linkModelAssembler = assembler;
        this.linkHistoryService = history;
        this.newLinkService = newLinkService;
    }

    @GetMapping("/{id}")
    public EntityModel<Link> getLinkById(@PathVariable Long id) {
        Link link = linkRepository.findById(id)
            .orElseThrow(() -> new LinkNotFoundException(id));

        return linkModelAssembler.toModel(link);
    }

    @GetMapping("/{id}/history")
    public CollectionModel<EntityModel<Link>> getHistory(@PathVariable Long id) {
        List<EntityModel<Link>> linkHistory = linkHistoryService.getLinkHistory(id).stream()
            .map(linkModelAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(linkHistory,
            linkTo(methodOn(LinkController.class).getHistory(id)).withSelfRel());
    }

    @GetMapping("")
    public CollectionModel<EntityModel<Link>> getLinks() {
        List<EntityModel<Link>> links = linkRepository.findAll().stream()
            .map(linkModelAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(links,
            linkTo(methodOn(LinkController.class).getLinks()).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Link>> editLink(@PathVariable Long id, @RequestBody LinkDto newLink) {
        Link updatedLink = linkRepository.findById(id)
            .map(link -> {
                link.setTitle(newLink.title());
                link.setDescription(newLink.description());
                link.setUrl(newLink.url());
                link.setImageData(newLink.imageData());
                return linkRepository.save(link);
            })
            .orElseGet(() -> {
                return linkRepository.save(newLinkService.createLink(newLink));
            });
        EntityModel<Link> entityModel = linkModelAssembler.toModel(updatedLink);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<Link>> createLink(@RequestBody LinkDto newLink) {
        Link link = linkRepository.save(newLinkService.createLink(newLink));
        return ResponseEntity
            .created(linkTo(methodOn(LinkController.class).getLinkById(link.getId())).toUri())
            .body(linkModelAssembler.toModel(link));
    }

}
