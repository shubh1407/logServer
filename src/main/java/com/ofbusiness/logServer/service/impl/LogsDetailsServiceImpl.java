package com.ofbusiness.logServer.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ofbusiness.logServer.models.LogsDetails;
import com.ofbusiness.logServer.repository.LogsServerRepository;
import com.ofbusiness.logServer.service.LogsDetailsService;

@Service
public class LogsDetailsServiceImpl implements LogsDetailsService {
	
	@Autowired
	private LogsServerRepository logsServerRepository;
	
	public String saveMessageLogs(LogsDetails logsDetails)
	{
		logsDetails = logsServerRepository.save(logsDetails);
		String result = logsDetails.getMessageId();
		return result;
	}
	
	public List<LogsDetails> getMessageLogs(String userId,String start,int limit)
	{
		List<LogsDetails> chatLogsList = Collections.emptyList();
		Optional<LogsDetails> startingLogs = null;
		String startId = null;
		if(Objects.nonNull(start) && !start.isEmpty()) 
			startingLogs = logsServerRepository.findById(start);
		else
			startingLogs = logsServerRepository.getLatestMessageLog();
		if(startingLogs.isPresent())
		{
			startId = startingLogs.get().getMessageId();
		}
		chatLogsList = logsServerRepository.getAllChatLogs(userId, startId, limit);
		return chatLogsList;
	}
	
	public long deleteAllChatLogs(String userId)
	{
		return logsServerRepository.deleteByUserId(userId);
	}
	
	public long deleteChatLogs(String userId,String messageId)
	{
		return logsServerRepository.deleteByMessageIdAndUserId(messageId,userId);
	}
	
	public Optional<LogsDetails> findLogsById(String messageId)
	{
		return logsServerRepository.findById(messageId);
	}

}
