package link.app.backend.dto;

public record LinkDto(String title, String description, String url, byte[] imageData, boolean isActive) { }
