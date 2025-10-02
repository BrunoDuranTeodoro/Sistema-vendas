package com.br.aweb.sistema_vendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.model.Pedido;
import com.br.aweb.sistema_vendas.repository.PedidoRepository;
import com.br.aweb.sistema_vendas.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

@Service
public class PedidoServices {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    

    @Transactional
        public Pedido salvar(Cliente cliente) {
        return pedidoRepository.save(new Pedido(cliente));
    }


    @Transactional
        public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade){

        var optinalProduto = produtoRepository.findById(produtoId);
        if (!optinalProduto.isPresent()){
            throw new IllegalArgumentException("Produto não enontrado");
        }

        var optinalPedido = pedidoRepository.findById(pedidoId);
        if (!optinalPedido.isPresent()){
            throw new IllegalArgumentException("Pedido não enontrado");
        }

        
    }
        

}
