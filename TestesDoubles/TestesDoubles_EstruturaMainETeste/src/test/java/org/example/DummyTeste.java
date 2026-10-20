package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DummyTeste {
    @Test
    void deveExecutarOperacaoComDummy() {
        DummyExemplo exemplo = new DummyExemplo();
        DummyExemplo.DummyLogger logger = new DummyExemplo.DummyLogger();

        String resultado = exemplo.executarOperacao(logger);

        assertEquals("Operação executada", resultado);
    }
}
