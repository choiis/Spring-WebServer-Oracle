package com.singer.redis;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import com.singer.common.DateUtil;
import com.singer.dao.ErrorDao;
import com.singer.vo.ErrorVo;

@Repository("redisDao")
public class RedisDao {

	private final Log log = LogFactory.getLog(RedisDao.class);

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Resource(name = "errorDao")
	private ErrorDao errorDao;

	public void zSet(String key, String value, double score) {
		try {
			ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
			zsetOp.add(key, value, score);
		} catch (RedisConnectionFailureException e) {
			log.debug("RedisConnectionFailureException");
		}
	}

	public void zSetIncre(String key, String value) {
		try {
			ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
			zsetOp.incrementScore(key, value, 1);
		} catch (RedisConnectionFailureException e) {
			log.debug("RedisConnectionFailureException");
		}
	}

	public void hmSetIncre(String key, String field) {
		try {
			HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();

			// Redis Insert
			// Hash구조에 Key는 날짜 field는 화면명
			hashOp.increment(key, field, 1);
		} catch (RedisConnectionFailureException e) {
			log.debug("RedisConnectionFailureException");
			ErrorVo errorVo = new ErrorVo(field, DateUtil.getTodayTime(), "RedisConnectionFailureException");
			errorDao.insertError(errorVo);
		}
	}

	public void hmSet(String key, String field, int number) {
		try {
			HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();

			// Redis Insert
			// Hash구조에 Key는 날짜 field는 화면명
			hashOp.put(key, field, number);
		} catch (RedisConnectionFailureException e) {
			log.debug("RedisConnectionFailureException");
			ErrorVo errorVo = new ErrorVo(field, DateUtil.getTodayTime(), "RedisConnectionFailureException");
			errorDao.insertError(errorVo);
		}
	}
}
