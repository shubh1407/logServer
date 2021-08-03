package com.ofbusiness.logServer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.ofbusiness.logServer.models.LogsDetails;
import com.ofbusiness.logServer.repository.LogsServerRepository;
import com.ofbusiness.logServer.service.impl.LogsDetailsServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LogsDetailsServiceTest {

	@InjectMocks
	private LogsDetailsServiceImpl logsDetailsService;
	
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
	public void saveMessageLogsSuccessTest()
	{
		Mockito.doReturn(logsDetails).when(logsRepository).save((logsDetails));
		String response = logsDetailsService.saveMessageLogs((logsDetails));
		assertEquals(messageId, response);
	}
	
	@Test
	public void getMessageLogsSuccessTest()
	{
		List<LogsDetails> logsDetailsList = new ArrayList<>();
		logsDetailsList.add(logsDetails);
		Mockito.doReturn(Optional.of(logsDetails)).when(logsRepository).findById(messageId);
		Mockito.doReturn(logsDetailsList).when(logsRepository).getAllChatLogs(userId, "1", 10);
		List<LogsDetails> response = logsDetailsService.getMessageLogs(userId,"1",10);
		assertEquals(logsDetailsList, response);
	}
	
	@Test
	public void deleteAllMessageLogsSuccessTest()
	{
		Mockito.doReturn(1L).when(logsRepository).deleteByUserId(userId);
		long response = logsDetailsService.deleteAllChatLogs(userId);
		assertEquals(1L, response);
	}
	
	@Test
	public void deleteMessageLogsSuccessTest()
	{
		Mockito.doReturn(1L).when(logsRepository).deleteByMessageIdAndUserId(messageId,userId);
		long response = logsDetailsService.deleteChatLogs(userId,messageId);
		assertEquals(1L, response);
	}
	
}
