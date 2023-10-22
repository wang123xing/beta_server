package cn.beta.platform.config;

import cn.beta.platform.constant.CacheConstant;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import lombok.Data;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;

@Configuration
@EnableCaching // 开启缓存支持
@Data
@ConfigurationProperties(prefix = "beta.redis.pool")
public class RedisConfig extends CachingConfigurerSupport {

	private List<RedisPoolConfig> nodeList;

	@Value("${beta.redis.password}")
	private String password;
	@Value("${beta.redis.pool.maxIdle:8}")
	private Integer maxIdle;
	@Value("${beta.redis.pool.minIdle:1}")
	private Integer minIdle;
	@Value("${beta.redis.pool.maxTotal:8}")
	private Integer maxTotal;
	@Value("${beta.redis.pool.maxWaitMillis:5000L}")
	private Long maxWaitMillis;

	@Value("${beta.redis.port}")
	private Integer port;

	@Value("${beta.redis.host}")
	private String host;

	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		// 连接池配置
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxIdle( maxIdle);
		poolConfig.setMinIdle( minIdle);
		poolConfig.setMaxTotal( maxTotal);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
				.poolConfig(poolConfig)
				.build();
		// 集群redis
//        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
//        redisConfig.setClusterNodes(nodeList.stream().map(x->{
//			return new RedisNode(x.getHost(), x.getPort());
//		}).collect(Collectors.toSet()));
//        // 跨集群执行命令时要遵循的最大重定向数量
//        redisConfig.setMaxRedirects(3);
//		redisConfig.setPassword(password);
		RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
		redisConfig.setHostName(host);
		redisConfig.setPort(port);
		return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);
	}
		/**
         * RedisTemplate配置
         *
         * @param lettuceConnectionFactory
         * @return
         */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
		// 设置序列化
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		om.enableDefaultTyping(DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		// 配置redisTemplate
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
		redisTemplate.setConnectionFactory(lettuceConnectionFactory);
		RedisSerializer<?> stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);// key序列化
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);// value序列化
		redisTemplate.setHashKeySerializer(stringSerializer);// Hash key序列化
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);// Hash value序列化
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}

	/**
	 * 缓存配置管理器
	 * @param factory
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(LettuceConnectionFactory factory) {
        // 配置序列化（缓存默认有效期 6小时）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(6));
        RedisCacheConfiguration redisCacheConfiguration = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                												.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		// 以锁写入的方式创建RedisCacheWriter对象
		//RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
		// 创建默认缓存配置对象
		/* 默认配置，设置缓存有效期 1小时*/
		//RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));
		/* 自定义配置test:demo 的超时时间为 5分钟*/
		RedisCacheManager cacheManager = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter(factory)).cacheDefaults(redisCacheConfiguration)
				.withInitialCacheConfigurations(singletonMap(CacheConstant.TEST_DEMO_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(5)).disableCachingNullValues()))
				.transactionAware().build();
		return cacheManager;
	}
}
