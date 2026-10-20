package org.example;

/**
 * Classe principal que representa um produto e operações relacionadas.
 */
public class Produto {
    private final String nome;
    private final double preco;

    public Produto(String nome, double preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public double calcularDesconto(double percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException(
                    "O desconto deve estar entre 0 e 100."
            );
        }
        return preco - (preco * percentual / 100.0);
    }

    public interface RepositorioProduto {
        Produto buscarPorId(int id);
        void salvar(Produto produto);
    }

    public interface NotificadorEstoque {
        void notificar(String mensagem);
    }

    /**
     * Serviço que cadastra produtos e envia uma notificação.
     */
    public static class ProdutoService {
        private final RepositorioProduto repositorio;
        private final NotificadorEstoque notificador;

        public ProdutoService(
                RepositorioProduto repositorio,
                NotificadorEstoque notificador
        ) {
            this.repositorio = repositorio;
            this.notificador = notificador;
        }

        public void cadastrar(Produto produto) {
            repositorio.salvar(produto);
            notificador.notificar("Produto cadastrado: " + produto.getNome());
        }

        public Produto consultar(int id) {
            return repositorio.buscarPorId(id);
        }
    }
}
