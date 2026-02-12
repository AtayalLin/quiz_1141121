package com.example.quiz_1141121.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz_1141121.entity.Quiz;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into quiz (title, description, start_date, end_date, is_published) "
			+ " values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
	public void insertQuiz(
			@Param("title") String title, 
			@Param("description") String description,
			@Param("startDate") LocalDate startDate, 
			@Param("endDate") LocalDate endDate,
			@Param("isPublished") boolean isPublished);
	
	@Query(value = "select max(id) from quiz",nativeQuery = true)
	public int getQuizmax();
	
	@Query(value = "select * from quiz",nativeQuery = true)
	public List<Quiz> getAll();
	
	@Query(value = "select count(id) from quiz where id = ?1", nativeQuery = true)
	public int getQuizCount(int id);

//	@Modifying
//	@Transactional
//	@Query(value = "update quiz set "
//			+ " title = coalesce(:title, title), "
//			+ " description = coalesce(:description, description), "
//			+ " start_date = coalesce(:startDate, start_date), "
//			+ " end_date = coalesce(:endDate, end_date), "
//			+ " is_published = :isPublished "
//			+ " where id = :id", nativeQuery = true)
//	public int updateQuiz(@Param("id") int id, 
//			@Param("title") String title, 
//			@Param("description") String description,
//			@Param("startDate") LocalDate startDate, 
//			@Param("endDate") LocalDate endDate,
//			@Param("isPublished") boolean isPublished);
	
	/**
	 * 根據 ID 查詢問卷 (新增此項以供 Service 檢查狀態)
	 */
	@Query(value = "select * from quiz where id = ?1", nativeQuery = true)
	public Quiz getById(int id);

	@Modifying
	@Transactional
	@Query(value = "update quiz set title = ?2, description = ?3, start_date = ?4, " //
			+ " end_date = ?5, is_published = ?6 where id = ?1", nativeQuery = true)
	public void update(int id, String title, String description, LocalDate startDate,
			LocalDate endDate, boolean published);
	
	@Modifying
	@Transactional
	@Query(value = "delete from quiz where id in (?1)", nativeQuery = true)
	public void delete(List<Integer> idList);
	
	

}
