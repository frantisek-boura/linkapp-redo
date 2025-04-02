package link.app.backend.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

import java.util.Arrays;

public class Link  {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @Lob
    private String description;

    private String url;

    @Lob
    private byte[] imageData;

    private boolean isAvailableFirefox;

    private boolean isAvailableChrome;

    private boolean isActive;

    public Link() {}

    public Link(String title, String description, String url, byte[] imageData, boolean isAvailableFirefox, boolean isAvailableChrome, boolean isActive) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageData = imageData;
        this.isAvailableFirefox = isAvailableFirefox;
        this.isAvailableChrome = isAvailableChrome;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public boolean isAvailableFirefox() {
        return isAvailableFirefox;
    }

    public void setAvailableFirefox(boolean isAvailableFirefox) {
        this.isAvailableFirefox = isAvailableFirefox;
    }

    public boolean isAvailableChrome() {
        return isAvailableChrome;
    }

    public void setAvailableChrome(boolean isAvailableChrome) {
        this.isAvailableChrome = isAvailableChrome;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + Arrays.hashCode(imageData);
        result = prime * result + (isAvailableFirefox ? 1231 : 1237);
        result = prime * result + (isAvailableChrome ? 1231 : 1237);
        result = prime * result + (isActive ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Link other = (Link) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        if (!Arrays.equals(imageData, other.imageData))
            return false;
        if (isAvailableFirefox != other.isAvailableFirefox)
            return false;
        if (isAvailableChrome != other.isAvailableChrome)
            return false;
        if (isActive != other.isActive)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Link [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url + ", imageData="
                + Arrays.toString(imageData) + ", isAvailableFirefox=" + isAvailableFirefox + ", isAvailableChrome="
                + isAvailableChrome + ", isActive=" + isActive + "]";
    }
    
}