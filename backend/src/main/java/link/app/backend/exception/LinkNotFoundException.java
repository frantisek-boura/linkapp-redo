package link.app.backend.exception;

public class LinkNotFoundException extends RuntimeException {

    public LinkNotFoundException(Long id) {
        super(String.format("Link with id %s not found", id));
    }

}
