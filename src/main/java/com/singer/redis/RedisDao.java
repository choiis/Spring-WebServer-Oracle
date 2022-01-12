package com.singer.redis;

import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Repository;

import com.singer.common.DateUtil;
import com.singer.util.InputQueryUtil;
import com.singer.vo.SortedSetVo;

@Repository
public class RedisDao {

	private final Log log = LogFactory.getLog(RedisDao.class);

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public DataType type(String key) {
		return redisTemplate.type(key);
	}

	public void zSet(String key, String value, double score) {
		try {
			ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
			zsetOp.add(key, value, score);
		} catch (RedisConnectionFailureException e) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(value);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add("zSet");

		}
	}

	public void zSetIncre(String key, String value) {
		try {
			ZSetOperations<String, Object> zsetOp = redisTemplate.opsForZSet();
			zsetOp.incrementScore(key, value, 1);
		} catch (RedisConnectionFailureException e) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(value);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add("zSetIncre");

		}
	}

	public void hmSetIncre(String key, String field) {
		try {
			HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();

			// Redis Insert
			// Hash구조에 Key는 날짜 field는 화면명
			hashOp.increment(key, field, 1);
		} catch (RedisConnectionFailureException e) {

			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(field);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add("hmSetIncre");

		}
	}

	public void hmSet(String key, String field, int number) {
		try {
			HashOperations<String, String, Object> hashOp = redisTemplate.opsForHash();

			// Redis Insert
			// Hash구조에 Key는 날짜 field는 화면명
			hashOp.put(key, field, number);
		} catch (RedisConnectionFailureException e) {
			InputQueryUtil queryUtil = new InputQueryUtil("log_error");
			queryUtil.add(field);
			queryUtil.add(DateUtil.getTodayTime());
			queryUtil.add("hmSet");

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

		Stream<TypedTuple<Object>> stream = sortedSet.stream();
		Vector<SortedSetVo> vector = new Vector<SortedSetVo>();
		log.debug("zrange " + key + " 0 -1 withscores");
		stream.forEachOrdered(s -> {
			vector.add(new SortedSetVo((String) s.getValue(), s.getScore()));
		});

		return vector;
	}
}
