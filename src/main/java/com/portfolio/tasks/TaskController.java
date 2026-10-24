package com.portfolio.tasks;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong ids = new AtomicLong();

    @PostMapping
    public ResponseEntity<Task> create(
            @Valid @RequestBody CreateTaskRequest request) {

        Task task = new Task(
                ids.incrementAndGet(),
                request.title().trim(),
                false
        );

        tasks.put(task.id(), task);

        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @GetMapping
    public List<Task> list() {
        return tasks.values()
                .stream()
                .sorted(Comparator.comparingLong(Task::id))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> get(@PathVariable("id") long id) {
        Task task = tasks.get(id);

        return task == null
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(task);
    }
}