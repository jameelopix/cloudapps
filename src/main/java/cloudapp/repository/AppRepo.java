package cloudapp.repository;

import org.springframework.stereotype.Repository;

import cloudapp.entity.App;
import cloudapp.jpa.BaseRepo;

@Repository
public interface AppRepo extends BaseRepo<App, Long> {
}