package pl.demo.creditsuissedemo.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogValuesRepository extends JpaRepository<LogValueEntity, Long> {
}
