package com.example.langchallenge2.bot.repository;

import com.example.langchallenge2.bot.model.Theory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TheoryRepository extends JpaRepository<Theory, Integer> {

  @Modifying
  @Transactional
  @Query(nativeQuery = true, value =
      "INSERT INTO theory (dayNumber, theory, msgNumber) values ( :dayNumber, :theory, :msgNumber)")
  void setTheory(@Param("dayNumber") int dayNumber, @Param("theory") String theory,
      @Param("msgNumber") int msgNumber);

  @Query(nativeQuery = true, value =
      "SELECT CASE WHEN EXISTS (SELECT 1 FROM theory WHERE dayNumber = :dayNumber AND msgNumber = :msgNumber) THEN 1 ELSE 0 END")
  int checkData(@Param("dayNumber") int dayNumber, @Param("msgNumber") int msgNumber);
}
