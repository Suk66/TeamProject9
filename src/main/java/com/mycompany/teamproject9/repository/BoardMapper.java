package com.mycompany.teamproject9.repository;

import com.mycompany.teamproject9.dto.Board;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BoardMapper {

    @Select("SELECT board_id, title, content, writer, created_at FROM board ORDER BY created_at DESC")
    @Results({
            @Result(column = "board_id", property = "boardId"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "writer", property = "writer"),
            @Result(column = "created_at", property = "createdAt", javaType = LocalDateTime.class) // ✅ 변환 명시
    })
    List<Board> findAll();



    // 2️⃣ 게시글 작성
    @Insert("INSERT INTO board (title, content, writer, password, created_at) VALUES (#{title}, #{content}, #{writer}, #{password}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "boardId")
    int insert(Board board);


    // 🔹 특정 게시글 조회
    @Select("SELECT board_id AS boardId, title, content, writer, password, created_at AS createdAt FROM board WHERE board_id = #{boardId}")
    Board findById(@Param("boardId") int boardId);


    // 4️⃣ 게시글 삭제
    @Delete("DELETE FROM board WHERE board_id = #{boardId}")
    int delete(@Param("boardId") int boardId);

    // 5️⃣ 게시글 수정
    @Update("UPDATE board SET title = #{title}, content = #{content} WHERE board_id = #{boardId}")
    int update(Board board);
}
