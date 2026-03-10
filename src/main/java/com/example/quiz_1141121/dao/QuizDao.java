package com.example.quiz_1141121.dao;

import java.util.*;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;	
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.quiz_1141121.entity.Quiz;

import jakarta.transaction.Transactional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{
	
	@Modifying
	@Transactional
	@Query(value = "insert into quiz (title, type, intro, start_date, end_date, is_published, " //
			+ " collect_name, collect_phone, collect_email, require_age) "
			+ " values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10)", nativeQuery = true)
	public void insertQuiz(
			String title,       // ?1
			String type,        // ?2
			String intro,       // ?3
			LocalDate startDate,// ?4
			LocalDate endDate,  // ?5
			boolean isPublished, // ?6
			boolean collectName,// ?7
			boolean collectPhone,// ?8
			boolean collectEmail, // ?9
			boolean requireAge    // ?10
			);
	
	@Query(value = "select max(id) from quiz",nativeQuery = true)
	public int getQuizmax();
	
	@Query(value = "select * from quiz",nativeQuery = true)
	public List<Quiz> getAll();
	
	@Query(value = "select count(id) from quiz where id = ?1", nativeQuery = true)
	public int getQuizCount(int id);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE quiz SET is_published = ?2 WHERE id = ?1", nativeQuery = true)
	public void updatePublishStatus(int id, boolean isPublished);

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
	@Query(value = "UPDATE quiz SET title = ?2, type = ?3, intro = ?4, start_date = ?5, " //
			+ " end_date = ?6, is_published = ?7, collect_name = ?8, collect_phone = ?9, "//
			+ " collect_email = ?10, require_age=?11 WHERE id=?1", nativeQuery = true)
	public void update(
			int id,             // ?1
			String title,       // ?2
			String type,        // ?3
			String intro,       // ?4
			LocalDate startDate,// ?5
			LocalDate endDate,  // ?6
			boolean published,   // ?7
			boolean collectName,// ?8
			boolean collectPhone,// ?9
			boolean collectEmail, // ?10
			boolean requireAge    // ?11
			
			);
	
	@Modifying
	@Transactional
	@Query(value = "delete from quiz where id in (?1)", nativeQuery = true)
	public void delete(List<Integer> idList);
	
	

}
