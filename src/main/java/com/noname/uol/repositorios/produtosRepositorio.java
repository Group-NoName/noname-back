package com.noname.uol.repositorios;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.noname.uol.entidades.Produtos;

@Repository
public interface produtosRepositorio extends MongoRepository<Produtos, String> {
	Page<Produtos> findAll(Pageable pageable);
}
