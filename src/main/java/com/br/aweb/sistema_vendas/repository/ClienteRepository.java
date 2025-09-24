package com.br.aweb.sistema_vendas.repository;

import com.br.aweb.sistema_vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}