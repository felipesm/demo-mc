package com.ifdeveloper.demomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifdeveloper.demomc.domain.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
	
	@Transactional(readOnly = true)
	@Query("SELECT cid FROM Cidade cid WHERE cid.estado.id = :id_estado ORDER BY cid.nome ASC")
	public List<Cidade> findCidades(@Param ("id_estado") Integer idEstado);

}
