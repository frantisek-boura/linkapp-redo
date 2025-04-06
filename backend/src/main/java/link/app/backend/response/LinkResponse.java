package link.app.backend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class LinkResponse { 

    private Long id;
    private String title;
    private String description;
    private String url;
    private byte[] imageData;
    private boolean isAvailableFirefox;
    private boolean isAvailableChrome;
    private boolean isActive;

}
