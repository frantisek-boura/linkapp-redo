package link.app.backend.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import link.app.backend.controller.LinkController;
import link.app.backend.response.LinkResponse;

@Component
public class LinkModelAssembler implements RepresentationModelAssembler<LinkResponse, EntityModel<LinkResponse>>{

    @Override
    public EntityModel<LinkResponse> toModel(LinkResponse response) {
        return EntityModel.of(response,
            linkTo(methodOn(LinkController.class).getLinkById(response.getId())).withSelfRel(),
            linkTo(methodOn(LinkController.class).getLinks()).withRel("links"),
            linkTo(methodOn(LinkController.class).getHistory(response.getId())).withRel("history")
        );
    }

}
