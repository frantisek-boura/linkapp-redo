package link.app.backend.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import link.app.backend.controller.LinkController;
import link.app.backend.entity.Link;

@Component
public class LinkModelAssembler implements RepresentationModelAssembler<Link, EntityModel<Link>>{

    @Override
    public EntityModel<Link> toModel(Link link) {
        return EntityModel.of(link,
            linkTo(methodOn(LinkController.class).getLinkById(link.getId())).withSelfRel(),
            linkTo(methodOn(LinkController.class).getLinks()).withRel("links"),
            linkTo(methodOn(LinkController.class).getHistory(link.getId())).withRel("history")
        );
    }

}
