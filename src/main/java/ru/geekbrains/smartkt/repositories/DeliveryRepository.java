package ru.geekbrains.smartkt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

import ru.geekbrains.smartkt.dao.delivery.*;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    // список всех пунктов выдачи закзов
    List<DeliveryPoint> findAllPoints();

    // список всех постаматов
    List<DeliveryLocker> findAllLockers();
}