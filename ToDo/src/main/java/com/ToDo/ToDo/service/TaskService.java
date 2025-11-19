package com.ToDo.ToDo.service;

import com.ToDo.ToDo.model.Task;
import com.ToDo.ToDo.model.User;
import com.ToDo.ToDo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksByUser(User user) {
        return taskRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public List<Task> getCompletedTasks(User user) {
        return taskRepository.findByUserAndCompletedOrderByCreatedAtDesc(user, true);
    }

    public List<Task> getIncompleteTasks(User user) {
        return taskRepository.findByUserAndCompletedOrderByCreatedAtDesc(user, false);
    }

    public Task createTask(String title, String description, User user) {
        Task task = new Task(title, description, user);
        return taskRepository.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Long id, String title, String description, boolean completed) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);

        return taskRepository.save(task);
    }

    public void toggleTaskCompletion(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean isTaskOwnedByUser(Long taskId, User user) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.isPresent() && task.get().getUser().getId().equals(user.getId());
    }
}
