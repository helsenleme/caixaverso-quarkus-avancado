package br.com.caixaverso.service;

import br.com.caixaverso.model.Produto;
import br.com.caixaverso.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ProdutoService {

    @Inject
    ProdutoRepository repository;

    public void salvar(Produto produto) {
        repository.persist(produto);
    }

    public List<Produto> listarTodos() {
        return repository.listAll();
    }
}