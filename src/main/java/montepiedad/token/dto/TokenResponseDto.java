package montepiedad.token.dto;

/*
 * DTO de respuesta con el token generado
*/
public class TokenResponseDto 
{
	/*
	 * Variables como resultado de la generaicon del tokenm
	 * token - Token generado
	 * expires - Expiracion del token.
	*/
    private String token;
    private String expires;

    /*
     * Declaración de constructores.
    */
    public TokenResponseDto() {
    }

    public TokenResponseDto(String token, String expires) {
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getexpires() {
        return expires;
    }

    public void setexpires(String expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "TokenResponseDto [token=" + token + ", expires=" + expires + "]";
    }
}
