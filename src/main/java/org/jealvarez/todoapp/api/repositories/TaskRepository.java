package org.jealvarez.todoapp.api.repositories;

import org.apache.ibatis.session.SqlSession;
import org.jealvarez.todoapp.api.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {

    private final SqlSession sqlSession;

    public TaskRepository(final SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Task> findAll() {
        return sqlSession.selectList("org.jealvarez.todoapp.api.repositories.TaskRepository.findAll");
    }

    public Optional<Task> findById(final long id) {
        return Optional.ofNullable(sqlSession.selectOne("org.jealvarez.todoapp.api.repositories.TaskRepository.findById", id));
    }

    public Optional<Task> save(final Task task) {
        sqlSession.insert("org.jealvarez.todoapp.api.repositories.TaskRepository.save", task);

        return Optional.ofNullable(task);
    }

    public void delete(final long id) {
        sqlSession.delete("org.jealvarez.todoapp.api.repositories.TaskRepository.delete", id);
    }

    public Optional<Task> update(final Task task) {
        sqlSession.update("org.jealvarez.todoapp.api.repositories.TaskRepository.update", task);

        return Optional.ofNullable(task);
    }

}
