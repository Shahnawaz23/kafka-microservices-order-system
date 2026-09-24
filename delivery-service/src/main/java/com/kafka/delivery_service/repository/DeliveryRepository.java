package com.kafka.delivery_service.repository;

import com.kafka.delivery_service.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Integer> {

}
