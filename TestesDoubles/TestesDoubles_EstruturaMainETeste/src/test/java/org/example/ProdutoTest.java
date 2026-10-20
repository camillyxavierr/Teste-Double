package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Exemplos de Dummy, Stub, Fake, Spy e Mock usando o mesmo contexto: produtos.
 * Cada teste demonstra um estilo diferente de Test Double.
 */
class ProdutoTest {

    // =========================================================
    // 1) DUMMY
    // Objeto usado apenas para preencher uma dependência.
    // Não precisamos verificar nenhuma ação dele neste teste.
    // =========================================================
    static class DummyNotificador implements Produto.NotificadorEstoque {
        @Override
        public void notificar(String mensagem) {
            // Intencionalmente vazio: o Dummy não faz nada.
        }
    }

    @Test
    void dummy_deveCalcularDescontoSemPrecisarNotificar() {
        // ARRANGE: prepara o produto e um Dummy para a dependência.
        Produto produto = new Produto("Teclado", 200.00);
        DummyNotificador dummy = new DummyNotificador();

        // ACT: calcula o desconto.
        double precoComDesconto = produto.calcularDesconto(10);

        // ASSERT: verifica se R$ 200,00 com 10% de desconto resulta em R$ 180,00.
        assertEquals(180.00, precoComDesconto, 0.001);

        // Evita variável sem uso e demonstra que o objeto pode ser passado
        // como dependência sem participar do resultado do cálculo.
        assertNotNull(dummy);
    }

    // =========================================================
    // 2) STUB
    // Fornece uma resposta previamente definida para a consulta.
    // =========================================================
    @Test
    void stub_deveRetornarProdutoPredefinido() {
        // ARRANGE: o Stub sempre retorna o produto preparado abaixo.
        Produto produtoEsperado = new Produto("Mouse", 80.00);

        Produto.RepositorioProduto stub = new Produto.RepositorioProduto() {
            @Override
            public Produto buscarPorId(int id) {
                return produtoEsperado;
            }

            @Override
            public void salvar(Produto produto) {
                // Não é necessário neste cenário.
            }
        };

        Produto.ProdutoService service =
                new Produto.ProdutoService(stub, new DummyNotificador());

        // ACT
        Produto resultado = service.consultar(10);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Mouse", resultado.getNome());
        assertEquals(80.00, resultado.getPreco(), 0.001);
    }

    // =========================================================
    // 3) FAKE
    // Implementação simplificada que funciona de verdade, mas guarda
    // os produtos em memória em vez de usar um banco de dados.
    // =========================================================
    static class FakeRepositorioProduto implements Produto.RepositorioProduto {
        private final Map<Integer, Produto> produtos = new HashMap<>();
        private int proximoId = 1;

        @Override
        public Produto buscarPorId(int id) {
            return produtos.get(id);
        }

        @Override
        public void salvar(Produto produto) {
            produtos.put(proximoId++, produto);
        }
    }

    @Test
    void fake_deveSalvarProdutoNoRepositorioEmMemoria() {
        // ARRANGE
        FakeRepositorioProduto fake = new FakeRepositorioProduto();
        Produto produto = new Produto("Fone", 120.00);

        // ACT: o Fake armazena o produto em memória.
        fake.salvar(produto);
        Produto resultado = fake.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Fone", resultado.getNome());
        assertEquals(120.00, resultado.getPreco(), 0.001);
    }

    // =========================================================
    // 4) SPY
    // Registra as chamadas recebidas para que o teste possa inspecioná-las.
    // =========================================================
    static class SpyNotificador implements Produto.NotificadorEstoque {
        private final List<String> mensagens = new ArrayList<>();

        @Override
        public void notificar(String mensagem) {
            mensagens.add(mensagem);
        }

        List<String> getMensagens() {
            return mensagens;
        }
    }

    @Test
    void spy_deveRegistrarNotificacaoQuandoProdutoForCadastrado() {
        // ARRANGE
        FakeRepositorioProduto fake = new FakeRepositorioProduto();
        SpyNotificador spy = new SpyNotificador();
        Produto.ProdutoService service =
                new Produto.ProdutoService(fake, spy);
        Produto produto = new Produto("Monitor", 900.00);

        // ACT
        service.cadastrar(produto);

        // ASSERT: o Spy guardou a mensagem enviada pelo serviço.
        assertEquals(1, spy.getMensagens().size());
        assertEquals(
                "Produto cadastrado: Monitor",
                spy.getMensagens().get(0)
        );
    }

    // =========================================================
    // 5) MOCK
    // Usa Mockito para verificar se as dependências receberam chamadas
    // com os argumentos esperados.
    // =========================================================
    @Test
    void mock_deveVerificarChamadasAoCadastrarProduto() {
        // ARRANGE: cria os mocks com Mockito.
        Produto.RepositorioProduto repositorioMock =
                mock(Produto.RepositorioProduto.class);
        Produto.NotificadorEstoque notificadorMock =
                mock(Produto.NotificadorEstoque.class);

        Produto.ProdutoService service =
                new Produto.ProdutoService(repositorioMock, notificadorMock);
        Produto produto = new Produto("Webcam", 250.00);

        // ACT
        service.cadastrar(produto);

        // ASSERT: verifica as interações esperadas.
        verify(repositorioMock, times(1)).salvar(produto);
        verify(notificadorMock, times(1))
                .notificar("Produto cadastrado: Webcam");
    }
}
