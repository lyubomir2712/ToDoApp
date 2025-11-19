package com.ToDo.ToDo.controller;

import com.ToDo.ToDo.model.Task;
import com.ToDo.ToDo.model.User;
import com.ToDo.ToDo.service.TaskService;
import com.ToDo.ToDo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/tasks")
    public String showTasksPage(Authentication authentication, Model model,
                               @RequestParam(required = false) String filter) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Task> tasks;
        if ("completed".equals(filter)) {
            tasks = taskService.getCompletedTasks(user);
            model.addAttribute("filterType", "Completed");
        } else if ("incomplete".equals(filter)) {
            tasks = taskService.getIncompleteTasks(user);
            model.addAttribute("filterType", "Incomplete");
        } else {
            tasks = taskService.getAllTasksByUser(user);
            model.addAttribute("filterType", "All");
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("newTask", new Task());

        return "tasks";
    }

    @PostMapping("/tasks/create")
    public String createTask(@RequestParam String title,
                            @RequestParam(required = false) String description,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (title == null || title.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Task title is required");
            return "redirect:/tasks";
        }

        taskService.createTask(title, description, user);
        redirectAttributes.addFlashAttribute("successMessage", "Task created successfully");
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!taskService.isTaskOwnedByUser(id, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unauthorized access");
            return "redirect:/tasks";
        }

        taskService.toggleTaskCompletion(id);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/update")
    public String updateTask(@PathVariable Long id,
                            @RequestParam String title,
                            @RequestParam(required = false) String description,
                            @RequestParam(required = false, defaultValue = "false") boolean completed,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!taskService.isTaskOwnedByUser(id, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unauthorized access");
            return "redirect:/tasks";
        }

        taskService.updateTask(id, title, description, completed);
        redirectAttributes.addFlashAttribute("successMessage", "Task updated successfully");
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Long id,
                            Authentication authentication,
                            RedirectAttributes redirectAttributes) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!taskService.isTaskOwnedByUser(id, user)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Unauthorized access");
            return "redirect:/tasks";
        }

        taskService.deleteTask(id);
        redirectAttributes.addFlashAttribute("successMessage", "Task deleted successfully");
        return "redirect:/tasks";
    }
}
