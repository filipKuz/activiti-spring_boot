package com.sf12.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sf12.entity.TaskDto;
import com.sf12.entity.Zahtev;
import com.sf12.services.ZahtevServis;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Controller
@RequestMapping(value="/app")
public class ApplicationController {

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	RuntimeService runtimeService;
	
	@Autowired
	private ZahtevServis zahtevS;
	
	@Autowired
	private IdentityService identityService;
	
	
	
	@PostMapping(value="/start-process", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> startProcess() {
		
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		List<Task> taskList;
		try {
			identityService.setAuthenticatedUserId(userId);
			ProcessInstance pi = runtimeService.startProcessInstanceByKey("process");
			taskList = taskService.createTaskQuery()
	                .processInstanceId(pi.getProcessInstanceId()).list();
			taskService.claim(taskList.get(0).getId(), userId);
		} finally {
			identityService.setAuthenticatedUserId(null);
		}
		return new ResponseEntity<String>(taskList.get(0).getId(), HttpStatus.OK);
	}
	
	@GetMapping(value="/request-preview/{taskId}") 
	public ResponseEntity<?> reqPreview(@PathVariable("taskId") String taskId) {
		Zahtev z; 
		try {
			String procId = taskService.createTaskQuery().taskId(taskId).singleResult().getProcessInstanceId();
			z = zahtevS.findByIdProcesa(Long.valueOf(procId));
		} catch (Exception e) {
			return new ResponseEntity<String> ("Bad request ", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Zahtev> (z, HttpStatus.OK);
	}
	
	@GetMapping(value="/user-tasks")
	public ResponseEntity<?> showUserTasks(ModelMap model) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName()));
        }
		return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value="/all-tasks", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showTasks(ModelMap model) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).list();		
		List<TaskDto> dtos = new ArrayList<TaskDto>();
        for (Task task : tasks) {
            dtos.add(new TaskDto(task.getId(), task.getName()));
        }
		return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value="/all-tasks-gen/{type}", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> showAllTasksGen(@PathVariable("type") String type) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).list();		
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			if(task.getTaskDefinitionKey().equals(type)) {
				 dtos.add(new TaskDto(task.getId(), task.getName()));
			}
	    }
		
		return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value="/user-tasks-gen/{type}")
	public ResponseEntity<?> showUserTasksGen(@PathVariable("type") String type) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		List<TaskDto> dtos = new ArrayList<TaskDto>();
		for (Task task : tasks) {
			if(task.getTaskDefinitionKey().equals(type)) {
				 dtos.add(new TaskDto(task.getId(), task.getName()));
			}
	    }
		return new ResponseEntity<List<TaskDto>>(dtos, HttpStatus.OK);
	}
	
	@GetMapping(value="/claim/{taskId}")
	public ResponseEntity<?> claimTask(@PathVariable("taskId") String taskId) {
		
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String message;
		String userId = user.getUsername();
		if(!canClaim(taskId, userId)) {
			message = "You can't claim this task";
		}else {
			message = "Task is successfuly claimed";
			taskService.claim(taskId, userId);
		}
		Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
		return new ResponseEntity<TaskDto>(new TaskDto(t.getId(), t.getName()), HttpStatus.OK);
	}
	
	@GetMapping(value="/unclaim/{taskId}")
	public ResponseEntity<?> unclaimTask(@PathVariable("taskId") String taskId) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		taskService.unclaim(taskId);
		Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
		return new ResponseEntity<TaskDto>(new TaskDto(t.getId(), t.getName()), HttpStatus.OK);
	}
	
	
	@PostMapping(value="/execute/{taskId}")
	public ResponseEntity<String> executeTask(@PathVariable String taskId,@RequestParam Map<String, String> params) {
		User user;
		try{
			user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception ex){
			return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
		}
		String userId = user.getUsername();
		String message;
		
		if(canExecute(taskId, userId)) {
			formService.
			formService.submitTaskFormData(taskId, params);
			message = "Task is successfuly completed";
		}else {
			message = "You can't execute this task";
			return new ResponseEntity<String> (message, HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<String> (message, HttpStatus.OK);
	}
	
	@GetMapping(value = "logged-user")
	public ResponseEntity<?> getUsersGroups() {
		try {
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (user != null) {
				List<Group> g = identityService.createGroupQuery().groupMember(user.getUsername()).list();
				List<String> groups = new ArrayList<>();
				for (Group gr: g) {
					groups.add(gr.getName());
				}
				return new ResponseEntity<List<String>>(groups, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("login", HttpStatus.UNAUTHORIZED);
	}

	
	
	private boolean canClaim(String taskId, String userId) {
		for (Task t: taskService.createTaskQuery().taskCandidateUser(userId).list()) {
			if (t.getId().equals(taskId)) {
				return true;
			}
		}
		return false;
	}	
	
	private boolean canExecute(String taskId, String userId) {
		for(Task t: taskService.createTaskQuery().taskAssignee(userId).list()) {
			if (t.getId().equals(taskId)) {
				return true;
			}
		}
		return false;
	}
	
}
