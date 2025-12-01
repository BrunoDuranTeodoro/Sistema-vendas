package com.br.aweb.sistema_vendas.controller;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.services.ClienteServices;
import jakarta.validation.Valid;
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

import java.util.Map;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteServices clienteService;

    @GetMapping("/novo")
    public ModelAndView create() {
        return new ModelAndView("cliente/form", Map.of("cliente", new Cliente()));
    }

    @PostMapping("/novo")
    public String create(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/form";
        }
       try {
         clienteService.salvar(cliente);
       } catch (IllegalArgumentException e) {
        result.rejectValue("email", "error.cliente",e.getMessage());
        result.rejectValue("cpf", "error.cliente",e.getMessage());

        return "cliente/form";
       }
        return "redirect:/clientes";
    }

    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("cliente/list", Map.of("clientes",
                clienteService.findAll()));
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable Long id) {
        var cliente = clienteService.buscarPorID(id);
        if (cliente.isPresent()) {
            return new ModelAndView("cliente/form", Map.of("cliente", cliente.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid Cliente cliente, BindingResult result) {
        if (result.hasErrors()) {
            return "cliente/form";
        }

        try {
         clienteService.atualizar(cliente.getId(), cliente);
       } catch (IllegalArgumentException e) {
        result.rejectValue("email", "error.cliente",e.getMessage());
        result.rejectValue("cpf", "error.cliente",e.getMessage());

        return "cliente/form";
       }

        
        return "redirect:/clientes";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id) {
        var cliente = clienteService.buscarPorID(id);
        if (cliente.isPresent()) {
            return new ModelAndView("cliente/delete", Map.of("cliente", cliente.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/delete/{id}")
    public String delete(Cliente cliente) {
        clienteService.delete(cliente);
        return "redirect:/clientes";
    }
}