package com.ofbusiness.logServer.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ofbusiness.logServer.models.LogsDetails;
import com.ofbusiness.logServer.service.LogsDetailsService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MainController {

	@Autowired
	private LogsDetailsService logsDetailsService;
	
	@PostMapping("/chatlogs/{user}")
	public ResponseEntity<String> addChatLogs(@PathVariable("user") String userId,@RequestBody LogsDetails logsDetails)
	{
		String result = null;
		System.out.println(userId);
		if(Objects.nonNull(userId) && !userId.trim().isEmpty() && Objects.nonNull(logsDetails) && Objects.nonNull(logsDetails.getMessage()) 
				&& Objects.nonNull(logsDetails.getTimeStamp()))
		{
			logsDetails.setUserId(userId);
			result = logsDetailsService.saveMessageLogs(logsDetails);
			System.out.println("message id "+result);
		}
		else
		{
			return new ResponseEntity<>("Please check your input!!!!",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
	
	@GetMapping("/chatlogs/{user}")
	public ResponseEntity<List<LogsDetails>> getChatLogs(@PathVariable("user") String userId,@RequestParam(required = false) String start, 
			@RequestParam(defaultValue="10",required = false) int limit)
	{
		List<LogsDetails> chatLogsList = Collections.emptyList();
		if(Objects.nonNull(userId) && !userId.trim().isEmpty())
		{
			chatLogsList=logsDetailsService.getMessageLogs(userId, start, limit);
		}
		else
		{
			return new ResponseEntity<>(Collections.emptyList(),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(chatLogsList,HttpStatus.OK);
	}
	
	@DeleteMapping("/chatlogs/{user}")
	public ResponseEntity<String> deleteAllChatLogs(@PathVariable("user") String userId)
	{
		if(Objects.nonNull(userId) && !userId.trim().isEmpty())
		{
			logsDetailsService.deleteAllChatLogs(userId);
		}
		else
		{
			return new ResponseEntity<>("Please check your input!!!",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Delete all logs successfully",HttpStatus.OK);
	}
	
	@DeleteMapping("/chatlogs/{user}/{msgId}")
	public ResponseEntity<String> deleteChatLogs(@PathVariable("user") String userId,@PathVariable("msgId") String messageId)
	{
		if(Objects.nonNull(userId)  && !userId.trim().isEmpty())
		{
			Optional<LogsDetails> optionalLogsDetails = logsDetailsService.findLogsById(messageId);
			if(optionalLogsDetails.isPresent() && optionalLogsDetails.get().getUserId().equals(userId))
			{
				long deletedRows = logsDetailsService.deleteChatLogs(userId,messageId);
				if(deletedRows>0)
					return new ResponseEntity<>("Delete logs successfully",HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("Please check your input!!!!",HttpStatus.BAD_REQUEST);
	}
}
