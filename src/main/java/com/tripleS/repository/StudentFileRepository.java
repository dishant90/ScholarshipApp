package com.tripleS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.tripleS.model.StudentFile;

public interface StudentFileRepository extends JpaRepository<StudentFile, Integer>, QueryDslPredicateExecutor {
	
	StudentFile findByFileNo(String fileNo);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StudentFile s WHERE s.fileNo = :fileNo")
	boolean existsByFileNo(@Param("fileNo") String fileNo);
}
