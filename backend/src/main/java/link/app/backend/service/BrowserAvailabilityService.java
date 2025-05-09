package link.app.backend.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class BrowserAvailabilityService implements IBrowserAvailabilityService {


    private static final String FIREFOX_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
    private static final String CHROME_USER_AGENT = "Chrome/51.0.2704.103 Safari/537.36";

    private boolean checkBrowserAvailability(String link, String userAgent) {
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", userAgent);
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean checkFirefoxAvailability(String url) {
        return checkBrowserAvailability(url, FIREFOX_USER_AGENT);
    }

    @Override
    public boolean checkChromeAvailability(String url) {
        return checkBrowserAvailability(url, CHROME_USER_AGENT);
    }
    


}
