package org.example;

import java.util.HashMap;
import java.util.Map;

public class FakeExemplo {
    public static class FakeRepositorioUsuario {
        private final Map<Integer, String> usuarios = new HashMap<>();
        private int proximoId = 1;

        public int salvar(String nome) {
            int id = proximoId++;
            usuarios.put(id, nome);
            return id;
        }

        public String buscar(int id) {
            return usuarios.get(id);
        }
    }
}
