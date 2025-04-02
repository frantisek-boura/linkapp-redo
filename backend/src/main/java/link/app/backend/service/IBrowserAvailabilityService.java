package link.app.backend.service;

public interface IBrowserAvailabilityService {

    boolean checkFirefoxAvailability(String url);
    boolean checkChromeAvailability(String url);

}
