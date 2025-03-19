package com.desafio.xpto.repository;

import com.desafio.xpto.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	List<City> findByIsCapitalTrueOrderByName();

	@Query("SELECT c.state, COUNT(c) FROM City c GROUP BY c.state")
	List<Object[]> countCitiesByState();

	Optional<City> findByIbgeId(int ibgeId);

	List<City> findByState(String state);

	@Query("SELECT c FROM City c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :value, '%')) " +
			"OR LOWER(c.state) LIKE LOWER(CONCAT('%', :value, '%')) " +
			"OR LOWER(c.noAccents) LIKE LOWER(CONCAT('%', :value, '%'))")
	List<City> filterByColumn(String value);

	@Query("SELECT COUNT(DISTINCT c.name) FROM City c")
	Long countDistinctByName();

	@Query("SELECT COUNT(DISTINCT c.state) FROM City c")
	Long countDistinctByState();

	@Query("SELECT COUNT(DISTINCT c.ibgeId) FROM City c")
	Long countDistinctByIbgeId();
}
