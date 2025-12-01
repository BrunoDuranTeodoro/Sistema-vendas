package com.br.aweb.sistema_vendas.repository;

import com.br.aweb.sistema_vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    // Novo método necessário para o login
    Optional<Cliente> findByEmail(String email);
}