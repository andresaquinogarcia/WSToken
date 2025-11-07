package montepiedad.token.service;

import montepiedad.token.dto.TokenResponseDto;

/*
* interfaz que controla las operaciones del servicio
* este expone los metodos generarToken y validarToken 
*/
public interface TokenService 
{
	/*
	 * metodo para generar un token
	*/
	TokenResponseDto generarToken();

	/*
	 * Metodo para validar estatus de un token
	 * @param token como cadena a validar.
	*/
	void validarToken(String token);

}
