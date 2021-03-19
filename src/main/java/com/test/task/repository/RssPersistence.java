package com.test.task.repository;

import com.test.task.model.Rss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RssPersistence extends JpaRepository<Rss, Long> {

    Optional<Rss> findByTitle(String title);
}
