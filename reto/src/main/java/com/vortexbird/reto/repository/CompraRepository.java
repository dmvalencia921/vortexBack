package com.vortexbird.reto.repository;

import com.vortexbird.reto.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    Compra findOneByIdCompraNot(Integer idCompra);
}
