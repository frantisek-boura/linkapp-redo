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
import link.app.backend.request.LinkRequest;
import link.app.backend.response.LinkResponse;
import link.app.backend.entity.Link;
import link.app.backend.service.ILinkService;

@RestController
@RequestMapping("/links")
public class LinkController {
    
    private final LinkModelAssembler linkModelAssembler;
    private final ILinkService linkService;

    public LinkController(LinkModelAssembler assembler, ILinkService linkService) {
        this.linkModelAssembler = assembler;
        this.linkService = linkService;
    }

    @GetMapping("/{id}")
    public EntityModel<LinkResponse> getLinkById(@PathVariable Long id) {
        Link link = linkService.getById(id);
        LinkResponse response = linkService.mapToResponse(link);

        return linkModelAssembler.toModel(response);
    }

    @GetMapping("/{id}/history")
    public CollectionModel<EntityModel<LinkResponse>> getHistory(@PathVariable Long id) {
        List<EntityModel<LinkResponse>> linkHistory = linkService.getLinkHistory(id).stream()
            .map(linkService::mapToResponse)
            .map(linkModelAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(linkHistory,
            linkTo(methodOn(LinkController.class).getHistory(id)).withSelfRel());
    }

    @GetMapping("")
    public CollectionModel<EntityModel<LinkResponse>> getLinks() {
        List<EntityModel<LinkResponse>> links = linkService.getAll().stream()
            .map(linkService::mapToResponse)
            .map(linkModelAssembler::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(links,
            linkTo(methodOn(LinkController.class).getLinks()).withSelfRel());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<LinkResponse>> editLink(@PathVariable Long id, @RequestBody LinkRequest request) {
        LinkResponse response = linkService.mapToResponse(linkService.updateLink(id, request));
        EntityModel<LinkResponse> entityModel = linkModelAssembler.toModel(response);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PostMapping("")
    public ResponseEntity<EntityModel<LinkResponse>> createLink(@RequestBody LinkRequest request) {
        LinkResponse response = linkService.mapToResponse(linkService.createLink(request));

        return ResponseEntity
            .created(linkTo(methodOn(LinkController.class).getLinkById(response.getId())).toUri())
            .body(linkModelAssembler.toModel(response));
    }

}
