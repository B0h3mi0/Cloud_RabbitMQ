package com.transp_national.consumer_queue_1.infrastructure.adapters.out.database.repository;

import com.transp_national.consumer_queue_1.infrastructure.adapters.out.database.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLocationRepository extends JpaRepository<LocationEntity, Long> {
}
