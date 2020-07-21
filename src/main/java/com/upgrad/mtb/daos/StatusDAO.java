package com.upgrad.mtb.daos;

import com.upgrad.mtb.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("statusDAO")
public interface StatusDAO extends JpaRepository<Status, Integer> {
    Status findDistinctByStatus(String status);
}
