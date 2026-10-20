package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StubTeste {
    @Test
    void deveRetornarRespostaDefinidaPeloStub() {
        StubExemplo exemplo = new StubExemplo();

        StubExemplo.RepositorioUsuario stub = new StubExemplo.RepositorioUsuario() {
            @Override
            public String buscarNome(int id) {
                return "Ana";
            }
        };

        String resultado = exemplo.obterNome(stub, 7);

        assertEquals("Ana", resultado);
    }
}
