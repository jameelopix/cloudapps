package cloudapp.repository;

import org.springframework.stereotype.Repository;

import cloudapp.entity.TableMaster;
import cloudapp.jpa.BaseRepo;

@Repository
public interface TableRepo extends BaseRepo<TableMaster, Long> {
}