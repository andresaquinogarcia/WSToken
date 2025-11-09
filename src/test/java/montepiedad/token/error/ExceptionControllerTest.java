package montepiedad.token.error;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import montepiedad.token.dto.ErrorDto;

class ExceptionControllerTest 
{

    private final ExceptionController exceptionController = new ExceptionController();

    @Test
    void errorBAdRequest() 
    {
        IllegalArgumentException ex = new IllegalArgumentException("mensaje de prueba");

        ResponseEntity<ErrorDto> response = exceptionController.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Parametro invalido", response.getBody().getError());
        assertEquals("mensaje de prueba", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getDate());
    }

    @Test
    void errolGeneral() 
    {
        Exception ex = new Exception("error generico");

        ResponseEntity<ErrorDto> response = exceptionController.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error interno del servidor", response.getBody().getError());
        assertEquals("error generico", response.getBody().getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getDate());
    }
}
