package com.ofbusiness.logServer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ofbusiness.logServer.models.LogsDetails;
import com.ofbusiness.logServer.repository.LogsServerRepository;
import com.ofbusiness.logServer.service.LogsDetailsService;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

	@InjectMocks
	private MainController mainController;
	
	@Mock
	private LogsDetailsService logsDetailsService;
	
	@Mock
	private LogsServerRepository logsRepository;
	
	private String messageId="1";
	private String userId="123456";
	private LogsDetails logsDetails;
	
	@Before
	public void initMocks()
	{
		logsDetails=logsDetails.builder().message("Demo Message")
		.isSent(true)
		.timeStamp(10001L)
		.messageId(messageId)
		.userId("123456")
		.build();
		System.out.println("");
	}
	
	@Test
	public void addMessageLogsSuccessTest()
	{
		Mockito.doReturn(messageId).when(logsDetailsService).saveMessageLogs((logsDetails));
		ResponseEntity<String> response = mainController.addChatLogs(userId, logsDetails);
		assertEquals(messageId, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void addMessageLogsFailureTest()
	{
		Mockito.doReturn(messageId).when(logsDetailsService).saveMessageLogs(logsDetails);
		ResponseEntity<String> response = mainController.addChatLogs(null, logsDetails);
		assertEquals("Please check your input!!!!", response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void getMessageLogsSuccessTest()
	{
		List<LogsDetails> logsDetailsList = new ArrayList<>();
		logsDetailsList.add(logsDetails);
		Mockito.doReturn(logsDetailsList).when(logsDetailsService).getMessageLogs(userId,null,10);
		ResponseEntity<List<LogsDetails>> response = mainController.getChatLogs(userId, null, 10);
		assertEquals(logsDetailsList, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void getMessageLogsFailureTest()
	{
		List<LogsDetails> logsDetailsList = new ArrayList<>();
		logsDetailsList.add(logsDetails);
		ResponseEntity<List<LogsDetails>> response = mainController.getChatLogs(null, null, 10);
		assertEquals(Collections.emptyList(), response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void deleteAllMessageLogsSuccessTest()
	{
		Mockito.doReturn(1L).when(logsDetailsService).deleteAllChatLogs(userId);
		ResponseEntity<String> response = mainController.deleteAllChatLogs(userId);
		assertEquals("Delete all logs successfully", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteAllMessageLogsFailureTest()
	{
		ResponseEntity<String> response = mainController.deleteAllChatLogs(null);
		assertEquals("Please check your input!!!", response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void deleteMessageLogsSuccessTest()
	{
		Mockito.doReturn(1L).when(logsDetailsService).deleteChatLogs(userId,messageId);
		Mockito.doReturn(Optional.of(logsDetails)).when(logsDetailsService).findLogsById(messageId);
		ResponseEntity<String> response = mainController.deleteChatLogs(userId,messageId);
		assertEquals("Delete logs successfully", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deleteMessageLogsFailureTest()
	{
		Mockito.doReturn(0L).when(logsDetailsService).deleteChatLogs(userId,messageId);
		ResponseEntity<String> response = mainController.deleteChatLogs(userId,messageId);
		assertEquals("Please check your input!!!!", response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void deleteMessageLogsFailureTest2()
	{
		ResponseEntity<String> response = mainController.deleteChatLogs(null,messageId);
		assertEquals("Please check your input!!!!", response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
}
