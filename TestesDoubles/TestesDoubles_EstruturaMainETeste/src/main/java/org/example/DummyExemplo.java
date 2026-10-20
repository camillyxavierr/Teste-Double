package org.example;

public class DummyExemplo {
    public String executarOperacao(DummyLogger logger) {
        return "Operação executada";
    }

    // Objeto dummy: existe para preencher a dependência, mas não faz nada.
    public static class DummyLogger {
        public void registrar(String mensagem) {
            // Intencionalmente vazio.
        }
    }
}
