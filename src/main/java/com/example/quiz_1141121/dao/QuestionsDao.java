package com.example.quiz_1141121.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz_1141121.entity.QuestionId;
import com.example.quiz_1141121.entity.Questions;

import jakarta.transaction.Transactional;

@Repository
public interface QuestionsDao extends JpaRepository<Questions, QuestionId>{

	// 1. 取得特定問卷的所有問題
		@Query(value = "select * from questions where quiz_id = ?1", nativeQuery = true)
		public List<Questions> findAllByQuizId(int quizId);

		// 2. 檢查特定的問題是否存在 (計算數量)
		@Query(value = "select count(*) from questions where quiz_id = ?1 and question_id = ?2", nativeQuery = true)
		public int getQuestionCount(int quizId, int questionId);

		// 3. 手動新增問題 (Native Insert)
		@Modifying
		@Transactional
		@Query(value = "insert into questions (quiz_id, question_id, question, type, is_required, options) "
				+ "values (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
		public void insertQuestion(//
				@Param("quizId") int quizId, //
				@Param("questionId") int questionId, //
				@Param("qText") String question, //
				@Param("qType") String type, //
				@Param("isRequired") boolean required, //
				@Param("qOptions") String options);

		// 4. 根據問卷 ID 刪除問題 (常用於更新問卷前先清空舊問題)
		@Modifying
		@Transactional
		@Query(value = "delete from questions where quiz_id = ?1", nativeQuery = true)
		public void deleteByQuizId(int quizId);

	    @Modifying
		@Transactional
		@Query(value = "update questions set " //
				+ " question = coalesce(:qText, question), " //
				+ " type = coalesce(:qType, type), " //
				+ " is_required = :isRequired, " // boolean 通常直接更新，或在 Service 判斷
				+ " options = coalesce(:qOptions, options) " //
				+ " where quiz_id = :quizId and question_id = :qId", //
				nativeQuery = true)
		public int updateQuestionInfo(//
				@Param("quizId") int quizId, //
				@Param("qId") int questionId, //
				@Param("qText") String question, //
				@Param("qType") String type, //
				@Param("isRequired") boolean required, //
				@Param("qOptions") String options);
		
		@Query(value = "select * from questions where quiz_id = ?1", nativeQuery = true)
		public List<Questions> getByQuizId(int QuizId); 
		

		
		@Modifying
		@Transactional
		@Query(value = "delete from questions where quiz_id in (?1)", nativeQuery = true)
		public void delete(List<Integer> idList);
}
