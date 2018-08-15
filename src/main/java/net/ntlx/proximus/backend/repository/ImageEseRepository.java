package net.ntlx.proximus.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.ImageEse;

public interface ImageEseRepository extends JpaRepository<ImageEse, Long> {

	@Query("SELECT i FROM ImageEse i WHERE i.entreprise.id = :id")
	public List<ImageEse> getImagesByEse(@Param(value="id") Long id);
}
