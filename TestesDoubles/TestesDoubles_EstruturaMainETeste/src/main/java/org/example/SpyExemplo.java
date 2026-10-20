package org.example;

import java.util.ArrayList;
import java.util.List;

public class SpyExemplo {
    public interface Notificador {
        void enviar(String email, String mensagem);
    }

    public static class SpyNotificador implements Notificador {
        private final List<String> chamadas = new ArrayList<>();

        @Override
        public void enviar(String email, String mensagem) {
            chamadas.add(email + " - " + mensagem);
        }

        public List<String> getChamadas() {
            return chamadas;
        }
    }

    public void cadastrarUsuario(String email, Notificador notificador) {
        notificador.enviar(email, "Cadastro realizado");
    }
}
