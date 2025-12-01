// src/main/java/com/br/aweb/sistema_vendas/controller/PedidoController.java
package com.br.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.aweb.sistema_vendas.model.Pedido;
import com.br.aweb.sistema_vendas.services.ClienteServices;
import com.br.aweb.sistema_vendas.services.PedidoServices;
import com.br.aweb.sistema_vendas.services.ProdutoServices;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoServices pedidoService;

    @Autowired
    private ClienteServices clienteService;

    @Autowired
    private ProdutoServices produtoService;

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("pedidos/list", Map.of("pedidos", pedidoService.findAll()));
    }

    @GetMapping("/novo")
    public ModelAndView create() {
        return new ModelAndView("pedidos/form", Map.of(
                "clientes", clienteService.findAll(),
                "produtos", produtoService.findAll()
        ));
    }

    @PostMapping("/novo")
    public String create(@RequestParam Long clienteId) {
        var cliente = clienteService.buscarPorID(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
        var pedido = new Pedido(cliente);
        pedidoService.salvar(pedido);
        return "redirect:/pedidos/" + pedido.getId();
    }

    @GetMapping("/{id}")
    public ModelAndView view(@PathVariable Long id) {
        var pedido = pedidoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
        return new ModelAndView("pedidos/view", Map.of(
                "pedido", pedido,
                "produtos", produtoService.findAll()
        ));
    }

    @PostMapping("/{id}/adicionar-item")
    public String adicionarItem(@PathVariable Long id, @RequestParam Long produtoId, @RequestParam Integer quantidade) {
        pedidoService.adicionarItem(id, produtoId, quantidade);
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/remover-item/{itemId}")
    public String removerItem(@PathVariable Long id, @PathVariable Long itemId) {
        pedidoService.removerItem(id, itemId);
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/{id}/cancelar")
    public String cancelar(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return "redirect:/pedidos";
    }
}