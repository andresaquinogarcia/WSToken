package montepiedad.token.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import montepiedad.token.dto.TokenResponseDto;
import montepiedad.token.service.TokenService;

/*
 * controller que expone los servicios disponibles para la generacion del token
*/
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TokenController 
{

	/*
	 * Variable de tipo TokenService usando inyeccion de dependenci
	*/
	@Autowired
    private TokenService tokenService;

    /*
     * Servicio para generar un token
    */
    @PostMapping("/token/generar")
    public ResponseEntity<TokenResponseDto> generarToken() 
    {
        TokenResponseDto response = tokenService.generarToken();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     * Servicio para validez la valides del token
     * @param token como cadena a validar.
    */
    @GetMapping("/token/validar")
    public ResponseEntity<String> validarToken(@RequestHeader("Authorization") String tokenHdr) 
    {
        String token = extraerToken(tokenHdr);
        tokenService.validarToken(token);
        return ResponseEntity.ok("Token valido");
    }
	/*
	 * metodo extraer el token del header
	 * */
    private String extraerToken(String authHeader) 
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) 
        {
            throw new IllegalArgumentException("Token invalido ");
        }
        return authHeader.substring(7);
    }

    

    /*
     * Servicio para probar el estado del api  
    */
    @GetMapping("/token/status")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
