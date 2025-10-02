package com.br.aweb.sistema_vendas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.model.Pedido;
import com.br.aweb.sistema_vendas.model.StatusPedido;
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
        //Passando parametro
        public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade){
        
        //Busca por id no produto repository se ele está presente, se não, ele gera uma exception
        var optinalProduto = produtoRepository.findById(produtoId);
        if (!optinalProduto.isPresent()){
            throw new IllegalArgumentException("Produto não enontrado");
        }

        var optinalPedido = pedidoRepository.findById(pedidoId);
        if (!optinalPedido.isPresent()){
            throw new IllegalArgumentException("Pedido não enontrado");
        }
  
        if(!pedido.getStatus().equals(StatusPedido.ATIVO)){
            throw new IllegalArgumentException("Pedido não está ativo no momento");
        }

        if(produto.getEstoque() < quantidade){
            throw new IllegalArgumentException("Quantidade insuficiente do produto" + produto.getNome());
        }


        var pedidoExistente = optinalPedido.get();



    }
        

}
