package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SpyTeste {
    @Test
    void deveRegistrarChamadaDeNotificacao() {
        SpyExemplo exemplo = new SpyExemplo();
        SpyExemplo.SpyNotificador spy = new SpyExemplo.SpyNotificador();

        exemplo.cadastrarUsuario("carla@email.com", spy);

        assertEquals(1, spy.getChamadas().size());
        assertEquals(
                "carla@email.com - Cadastro realizado",
                spy.getChamadas().get(0)
        );
    }
}
