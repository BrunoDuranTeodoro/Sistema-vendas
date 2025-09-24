package com.br.aweb.sistema_vendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.aweb.sistema_vendas.model.Produto;
import com.br.aweb.sistema_vendas.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import lombok.var;

@Service
public class ProdutoServices {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorID(Long id) {
        return produtoRepository.findById(id);
    }

    @Transactional
    public void delete(Produto produto) {
        produtoRepository.delete(produto);
    }
    
    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado){
        var optinalProduto = buscarPorID(id);
        if (!optinalProduto.isPresent()){
            throw new IllegalArgumentException("Produto não enontrado");
        }

        var produtoExistente = optinalProduto.get();
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidadeEmEstoque(produtoAtualizado.getQuantidadeEmEstoque());

        return produtoRepository.save(produtoExistente);
    }
}
