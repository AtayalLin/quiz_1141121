package com.example.quiz_1141121.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
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
		@Query(value = "insert into questions (quiz_id, question_id, question, type, is_required, options, is_dependent, parent_id) "
				+ "values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
		public void insertQuestion(//
				int quizId,       // ?1
				int questionId,   // ?2
				String question,  // ?3
				String type,      // ?4
				boolean required, // ?5
				String options,    // ?6
				boolean isDependent, // ?7 
				Integer parentId     // ?8
				);

		// 4. 根據單一問卷 ID 刪除問題 (常用於更新問卷前先清空舊問題)
		@Modifying
		@Transactional
		@Query(value = "delete from questions where quiz_id = ?1", nativeQuery = true)
		public void deleteByQuizId(int quizId);

	    @Modifying
		@Transactional
		@Query(value = "update questions set question = ?3, type = ?4, is_required = ?5, options = ?6, "
				+ "is_dependent = ?7, parent_id = ?8 where quiz_id = ?1 and question_id = ?2", nativeQuery = true)
		public int updateQuestionInfo(//
				int quizId,       // ?1
				int questionId,   // ?2
				String question,  // ?3
				String type,      // ?4
				boolean required, // ?5
				String options,    // ?6
				boolean isDependent, // ?7
				Integer parentId     // ?8
				);
		
		@Query(value = "select * from questions where quiz_id = ?1", nativeQuery = true)
		public List<Questions> getByQuizId(int QuizId); 
		
		// 批次刪除多個問卷的問題 (Service deleteQuiz 使用)
		@Modifying
		@Transactional
		@Query(value = "delete from questions where quiz_id in (?1)", nativeQuery = true)
		public void delete(List<Integer> idList);
}
