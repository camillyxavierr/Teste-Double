package org.example;

public class MockExemplo {
    public interface Notificador {
        void enviar(String email, String mensagem);
    }

    public void cadastrarUsuario(String email, Notificador notificador) {
        notificador.enviar(email, "Cadastro realizado");
    }
}
