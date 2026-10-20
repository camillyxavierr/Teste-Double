package org.example;

public class StubExemplo {
    public interface RepositorioUsuario {
        String buscarNome(int id);
    }

    public String obterNome(RepositorioUsuario repositorio, int id) {
        return repositorio.buscarNome(id);
    }
}
