package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FakeTeste {
    @Test
    void deveSalvarEConsultarUsuario() {
        FakeExemplo.FakeRepositorioUsuario fake =
                new FakeExemplo.FakeRepositorioUsuario();

        int id = fake.salvar("Bruno");
        String nome = fake.buscar(id);

        assertNotNull(nome);
        assertEquals("Bruno", nome);
    }
}
