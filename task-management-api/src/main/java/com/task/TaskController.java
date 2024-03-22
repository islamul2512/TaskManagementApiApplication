package com.task.controller;

import com.task.entity.Task;
import com.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskService taskServiceImpl;

    public TaskController(TaskService taskServiceImpl) {
        this.taskServiceImpl = taskServiceImpl;
    }

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<Task> listTasks = taskServiceImpl.getAllTasks();
        model.addAttribute("listTasks", listTasks);

        return "index";
    }

    @RequestMapping("/new")
    public String showNewTaskForm(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);

        return "new_task";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("task") Task task) {
        taskServiceImpl.createTask(task);

        return "redirect:/";
    }


    @RequestMapping("/edit/{id}")
    public ModelAndView showEditTaskForm(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_task");

        Task task = taskServiceImpl.getTaskById(id);
        mav.addObject("task", task);

        return mav;
    }
    @RequestMapping("/delete/{id}")
    public String deleteTask(@PathVariable(name = "id") Long id) {
        taskServiceImpl.deleteTask(id);

        return "redirect:/";
    }
}
