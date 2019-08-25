package com.singer.redis;

import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import com.singer.common.DateUtil;
import com.singer.dao.ErrorDao;
import com.singer.vo.ErrorVo;
import com.singer.vo.SortedSetVo;

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
			ErrorVo errorVo = new ErrorVo(value, DateUtil.getTodayTime(), "zSet");
			errorDao.insertError(errorVo);
		}
	}

	public void zSetIncre(String key, String value) {
		try {
			ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
			zsetOp.incrementScore(key, value, 1);
		} catch (RedisConnectionFailureException e) {
			ErrorVo errorVo = new ErrorVo(value, DateUtil.getTodayTime(), "zSetIncre");
			errorDao.insertError(errorVo);
		}
	}

	public void hmSetIncre(String key, String field) {
		try {
			HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();

			// Redis Insert
			// Hash구조에 Key는 날짜 field는 화면명
			hashOp.increment(key, field, 1);
		} catch (RedisConnectionFailureException e) {
			ErrorVo errorVo = new ErrorVo(field, DateUtil.getTodayTime(), "hmSetIncre");
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
			ErrorVo errorVo = new ErrorVo(field, DateUtil.getTodayTime(), "hmSet");
			errorDao.insertError(errorVo);
		}
	}

	// hkeys key
	public Set<String> getHashValue(String key) {

		HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();
		try {
			Set<String> keys = hashOp.keys(key);
			log.debug("hkeys " + key);
			return keys;
		} catch (Exception e) {
			return null;
		}
	}

	// hmget key value
	public Integer getHashValue(String key, String hashKey) {

		HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();
		try {
			int result = Integer.parseInt((String) hashOp.get(key, hashKey));
			log.debug("hmget " + key + " " + hashKey);
			return result;
		} catch (Exception e) {
			return 0;
		}

	}

	// zrevrange key 0 -1 withscores
	public Vector<SortedSetVo> getSortedSet(String key) {

		ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
		Set<TypedTuple<Object>> sortedSet = zsetOp.reverseRangeWithScores(key, 0, -1);

		Iterator<TypedTuple<Object>> iter = sortedSet.iterator();
		Vector<SortedSetVo> vector = new Vector<SortedSetVo>();
		log.debug("zrange " + key + " 0 -1 withscores");
		while (iter.hasNext()) {
			TypedTuple<Object> typedTuple = iter.next();
			vector.add(new SortedSetVo((String) typedTuple.getValue(), typedTuple.getScore()));
		}
		return vector;
	}
}
