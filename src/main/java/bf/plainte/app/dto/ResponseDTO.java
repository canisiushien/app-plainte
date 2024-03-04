package bf.plainte.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private HttpHeaders headers;

    private String message;

    private Object data;

    private Integer code;
}
