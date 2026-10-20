package org.example;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

class MockTeste {
    @Test
    void deveVerificarChamadaDeNotificacao() {
        MockExemplo exemplo = new MockExemplo();
        MockExemplo.Notificador mockNotificador =
                mock(MockExemplo.Notificador.class);

        exemplo.cadastrarUsuario("diego@email.com", mockNotificador);

        verify(mockNotificador, times(1))
                .enviar("diego@email.com", "Cadastro realizado");
    }
}
