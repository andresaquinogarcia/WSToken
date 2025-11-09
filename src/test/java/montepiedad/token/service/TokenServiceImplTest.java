package montepiedad.token.service;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import montepiedad.token.dto.TokenResponseDto;

class TokenServiceImplTest 
{

    private final TokenServiceImpl tokenService = new TokenServiceImpl();

    @Test
    void generarTokenValido() 
    {
        TokenResponseDto response = tokenService.generarToken();

        assertNotNull(response, "repsuesta no nula");
        assertNotNull(response.getToken(), "token no nulo");
        assertFalse(response.getToken().isBlank(), "token no vacio");
        assertNotNull(response.getexpires(), "fecha expiracion no nula");
        assertFalse(response.getexpires().isBlank(), "fecha expiracion no vacia");

        assertDoesNotThrow(() -> tokenService.validarToken(response.getToken()),"Token generado valido");
    }

    @Test
    void tokenInvalido() 
    {
        String tokenInvalido = "token-malo";
        assertThrows(IllegalArgumentException.class,() -> tokenService.validarToken(tokenInvalido),"Excepcion, token invalido");
    }
    
    @Test
    void excepcionTokenPartesIncompletas() 
    {
        TokenServiceImpl service = new TokenServiceImpl();

        String raw = "1234567890:987654321"; 
        String base64 = Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
        assertThrows(IllegalArgumentException.class,
                () -> service.validarToken(base64),"Excepcion por falta de partes");
    }

    @Test
    void hashIncorrecto() {
        TokenServiceImpl service = new TokenServiceImpl();

        TokenResponseDto response = service.generarToken();
        String tokenValido = response.getToken();

        String raw = new String(Base64.getDecoder().decode(tokenValido), StandardCharsets.UTF_8);
        String[] parts = raw.split(":");
        assertEquals(3, parts.length, "valido");

        parts[2] = "hash_incorrecto";
        String rawModificado = String.join(":", parts);
        String tokenModificado = Base64.getEncoder().encodeToString(rawModificado.getBytes(StandardCharsets.UTF_8));

        assertThrows(IllegalArgumentException.class,() -> service.validarToken(tokenModificado),"HAs no coincide");
    }

    
}
