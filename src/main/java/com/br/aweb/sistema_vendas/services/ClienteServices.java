package com.br.aweb.sistema_vendas.services;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorID(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public void delete(Cliente cliente) {
        clienteRepository.delete(cliente);
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        var optionalCliente = buscarPorID(id);
        if (optionalCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        var clienteExistente = optionalCliente.get();
        clienteExistente.setNomeCompleto(clienteAtualizado.getNomeCompleto());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setLogradouro(clienteAtualizado.getLogradouro());
        clienteExistente.setNumero(clienteAtualizado.getNumero());
        clienteExistente.setComplemento(clienteAtualizado.getComplemento());
        clienteExistente.setBairro(clienteAtualizado.getBairro());
        clienteExistente.setCidade(clienteAtualizado.getCidade());
        clienteExistente.setUf(clienteAtualizado.getUf());
        clienteExistente.setCep(clienteAtualizado.getCep());

        return clienteRepository.save(clienteExistente);
    }
}