package montepiedad.token.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.stereotype.Service;

import montepiedad.token.dto.TokenResponseDto;

/*
 * implementacion de la interfaz TokenService
*/
@Service
public class TokenServiceImpl implements TokenService {

	/*
	 * declaraicon de constanstes
	*/
    private static final long TOKEN_VALIDITY_MINUTES = 30L;
    private static final String SECRET = "m0nt3P13d4d";

    /*
     * genera token firmado por SHA-256
     * @param request de tipo TokenRequestDto
    */
    @Override
    public TokenResponseDto generarToken() 
    {
        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(TOKEN_VALIDITY_MINUTES, ChronoUnit.MINUTES);
        String token = generarTokenSha256(issuedAt, expiresAt);

        ZonedDateTime zdt = ZonedDateTime.ofInstant(expiresAt, ZoneId.of("America/Mexico_City"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String expiresAtStr = zdt.format(formatter);

        return new TokenResponseDto(token, expiresAtStr);
    }

    /*
     * validar token
     * @param token como cadena a validar
    */
    @Override
    public void validarToken(String token) 
    {
        boolean valido = esTokenValido(token);
        if (!valido) 
        {
            throw new IllegalArgumentException("Token invalido o expirado");
        }
    }

    /*
	 * generart token usando sha256
	*/
    private String generarTokenSha256(Instant issuedAt, Instant expiresAt) 
    {
        long issuedAtMillis = issuedAt.toEpochMilli();
        long expiresAtMillis = expiresAt.toEpochMilli();
        String data = String.valueOf(issuedAtMillis);
        String hash = sha256(data + ":" + SECRET);
        String rawToken = issuedAtMillis + ":" + expiresAtMillis + ":" + hash;
        return Base64.getEncoder().encodeToString(rawToken.getBytes(StandardCharsets.UTF_8));
    }

    /*
	 * valida si es un token valido y vigente
	 * @param token como cadena a validar
	*/
    private boolean esTokenValido(String token) 
    {
        try 
        {
            String raw = new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = raw.split(":");
            if (parts.length != 3) 
            {
                return false;
            }
            long issuedAtMillis = Long.parseLong(parts[0]);
            long expiresAtMillis = Long.parseLong(parts[1]);
            String hash = parts[2];
            String expectedHash = sha256(issuedAtMillis + ":" + SECRET);
            
            if (!expectedHash.equals(hash)) 
            {
                return false;
            }

            Instant now = Instant.now();
            Instant expiresAt = Instant.ofEpochMilli(expiresAtMillis);
            return now.isBefore(expiresAt);
        }
        catch (Exception e) 
        {
            return false;
        }
    }

    /*
	 * generar hash SHA-256
	 * @param value como cadena a procesar
	*/
    private String sha256(String value) 
    {
        try 
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) 
            {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        }
        catch (Exception e) 
        {
            throw new RuntimeException("Error generando hash SHA-256", e);
        }
    }

}
