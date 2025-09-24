package com.br.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.br.aweb.sistema_vendas.model.Produto;
import com.br.aweb.sistema_vendas.services.ProdutoServices;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoServices produtoService;

    @GetMapping("/novo")
    public ModelAndView create() {
        return new ModelAndView("produto/form", Map.of("produto", new Produto()));
    }

    @PostMapping("/novo")
    public String create(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return "produto/form";
        }
        produtoService.salvar(produto);
        return "redirect:/produtos";
    }

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("produto/list", Map.of("produtos",
                produtoService.findAll()));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var produto = produtoService.buscarPorID(id);
        if (produto.isPresent()) {
            return new ModelAndView("produto/form", Map.of("produto", produto.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors())
            return "produto/form";
        produtoService.atualizar(produto.getId(), produto);
        return "redirect:/produtos";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        var produto = produtoService.buscarPorID(id);
        if (produto.isPresent())
            return new ModelAndView("produto/delete", Map.of("produto", produto.get()));
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{id}")
    public String delete(Produto produto) {
        produtoService.delete(produto);
        return "redirect:/produtos";
    }

}
