// src/main/java/com/br/aweb/sistema_vendas/services/PedidoServices.java
package com.br.aweb.sistema_vendas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.model.ItemPedido;
import com.br.aweb.sistema_vendas.model.Pedido;
import com.br.aweb.sistema_vendas.model.Produto;
import com.br.aweb.sistema_vendas.model.StatusPedido;
import com.br.aweb.sistema_vendas.repository.ItemPedidoRepository;
import com.br.aweb.sistema_vendas.repository.PedidoRepository;
import com.br.aweb.sistema_vendas.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServices {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Transactional
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPedido.ATIVO) {
            throw new IllegalArgumentException("Pedido não está ativo");
        }

        produto.baixarEstoque(quantidade);
        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.adicionarItem(item);

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void removerItem(Long pedidoId, Long itemId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        if (pedido.getStatus() != StatusPedido.ATIVO) {
            throw new IllegalArgumentException("Pedido não está ativo");
        }

        item.getProduto().estornarEstoque(item.getQuantidade());
        pedido.removerItem(item);

        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));

        if (pedido.getStatus() != StatusPedido.ATIVO) {
            throw new IllegalArgumentException("Pedido já está finalizado ou cancelado");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        for (ItemPedido item : pedido.getItens()) {
            item.getProduto().estornarEstoque(item.getQuantidade());
        }

        pedidoRepository.save(pedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }
}