package com.example.stock.facade;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class RedissonLockStockFacade {

	private final RedissonClient redissonClient;

	private final StockService stockService;


	public void decStock(Long key, Long quantity) {
		RLock lock = redissonClient.getLock(key.toString());
		try{
			boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
			if (!available) {
				System.out.println("lock 획득 실패");
				return;
			}
			stockService.decStock(key,quantity);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}finally {
			lock.unlock();
		}
	}
}