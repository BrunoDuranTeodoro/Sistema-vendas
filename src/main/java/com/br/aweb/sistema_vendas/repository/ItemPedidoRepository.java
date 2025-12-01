// src/main/java/com/br/aweb/sistema_vendas/repository/ItemPedidoRepository.java
package com.br.aweb.sistema_vendas.repository;

import com.br.aweb.sistema_vendas.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}