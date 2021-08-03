package com.ofbusiness.logServer.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ofbusiness.logServer.models.LogsDetails;

@Repository
public interface LogsServerRepository extends CrudRepository<LogsDetails, String> {
	
	@Query(value="Select l.message_id,l.user_id,l.message,l.time_stamp,l.is_sent from logs_details l where l.message_id>= :start and l.user_id = :userId order by l.time_stamp desc limit :limit",nativeQuery = true)
	List<LogsDetails> getAllChatLogs(@Param("userId") String userId,@Param("start") String startId,
			@Param("limit") int limit);
	
	@Query(value="Select * from logs_details order by time_stamp desc limit 1",nativeQuery = true)
	Optional<LogsDetails> getLatestMessageLog();

	@Transactional
	long deleteByUserId(String userId);
	
	@Transactional
	long deleteByMessageIdAndUserId(String messageId,String userId);
	
}
