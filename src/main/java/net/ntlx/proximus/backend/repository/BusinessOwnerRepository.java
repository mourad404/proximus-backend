package net.ntlx.proximus.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ntlx.proximus.backend.model.BusinessOwner;

public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {

	@Query("SELECT bo FROM BusinessOwner bo WHERE bo.entreprise.id = :id")
	public BusinessOwner getBoByEntreprise(@Param("id") Long id);
}
