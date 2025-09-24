package br.com.caixaverso.service;

import br.com.caixaverso.model.Produto;
import br.com.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    @Transactional
    public Produto salvar(Produto produto) {
        repository.persist(produto);
        return produto;
    }

    public List<Produto> listarTodos() {
        return repository.listAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Transactional
    public Optional<Produto> atualizar(Long id, Produto dadosAtualizados) {
        Produto produto = repository.findById(id);
        if (produto == null) return Optional.empty();

        produto.nome = dadosAtualizados.nome;
        produto.descricao = dadosAtualizados.descricao;
        produto.preco = dadosAtualizados.preco;
        return Optional.of(produto);
    }

    @Transactional
    public boolean excluir(Long id) {
        return repository.deleteById(id);
    }
}