package com.ofbusiness.logServer.service;

import java.util.List;
import java.util.Optional;

import com.ofbusiness.logServer.models.LogsDetails;


public interface LogsDetailsService {
	
	
	public String saveMessageLogs(LogsDetails logsDetails);
	
	public List<LogsDetails> getMessageLogs(String userId,String start,int limit);
	
	public long deleteAllChatLogs(String userId);
	
	public long deleteChatLogs(String userId,String messageId);
	
	public Optional<LogsDetails> findLogsById(String messageId);

}
