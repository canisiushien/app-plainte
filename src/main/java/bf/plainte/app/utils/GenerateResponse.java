package bf.plainte.app.utils;

import bf.plainte.app.dto.ResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public class GenerateResponse {

    public static ResponseEntity<?> success(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<String>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 200)), HttpStatus.OK);
    }

    public static ResponseEntity<?> created(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 201)), HttpStatus.CREATED);
    }

    public static ResponseEntity<?> error(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 500)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> notFound(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 404)), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> unauthorized(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 401)), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> badRequest(HttpHeaders httpHeaders, String message, Object data) throws JsonProcessingException {
        return new ResponseEntity<>(new ObjectMapper().setSerializationInclusion(Include.ALWAYS).writeValueAsString(new ResponseDTO(httpHeaders, message, data, 400)), HttpStatus.BAD_REQUEST);
    }

}
