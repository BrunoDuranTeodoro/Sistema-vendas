package com.br.aweb.sistema_vendas.services;

import com.br.aweb.sistema_vendas.model.Cliente;
import com.br.aweb.sistema_vendas.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importante
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeção do encoder

    @Transactional
    public Cliente salvar(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf()) || clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Cliente já cadastrado");
        }
        
        // Criptografar a senha antes de salvar
        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        
        return clienteRepository.save(cliente);
    }
    
    // ... (Mantenha os métodos findAll, buscarPorID, delete iguais) ...

    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        var optionalCliente = buscarPorID(id);
        if (optionalCliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }

        var clienteExistente = optionalCliente.get();
        // ... validações de email e CPF existentes ...

        clienteExistente.setNomeCompleto(clienteAtualizado.getNomeCompleto());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        // ... atualizar outros campos de endereço ...

        // ATENÇÃO: Se a senha vier preenchida na atualização, deve ser criptografada.
        // Se estiver vazia, mantém a antiga (lógica opcional, depende da sua regra de negócio)
        if (clienteAtualizado.getSenha() != null && !clienteAtualizado.getSenha().isEmpty()) {
             clienteExistente.setSenha(passwordEncoder.encode(clienteAtualizado.getSenha()));
        }

        return clienteRepository.save(clienteExistente);
    }
    
    // Adicione métodos findAll, buscarPorID, delete conforme original...
    public List<Cliente> findAll() { return clienteRepository.findAll(); }
    public Optional<Cliente> buscarPorID(Long id) { return clienteRepository.findById(id); }
    public void delete(Cliente cliente) { clienteRepository.delete(cliente); }
}