package montepiedad.token.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import montepiedad.token.dto.TokenResponseDto;
import montepiedad.token.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TokenController.class)
class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TokenService tokenService;

    @Test
    void generarToken() throws Exception {
        TokenResponseDto resp = new TokenResponseDto("token", "2025-11-09 01:00:00");
        when(tokenService.generarToken()).thenReturn(resp);

        mockMvc.perform(post("/api/token/generar"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"))
                .andExpect(jsonPath("$.expires").value("2025-11-09 01:00:00"));
    }

    @Test
    void validartoken() throws Exception {
        doNothing().when(tokenService).validarToken(anyString());

        mockMvc.perform(get("/api/token/validar")
                        .header("Authorization", "Bearer ABC123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Token valido"));
    }

    @Test
    void validarTokenincorrecto() throws Exception {
        mockMvc.perform(get("/api/token/validar")
                        .header("Authorization", "MALFORMATO"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void erroralgenerarToken() throws Exception {
        mockMvc.perform(get("/api/token/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Servicio disponible"));
    }
}
