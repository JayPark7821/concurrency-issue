package com.example.stock.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.repository.LockRepository;
import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;

import lombok.RequiredArgsConstructor;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LettuceLockStockFacade {

	private final RedisLockRepository redisLockRepository;

	private final StockService stockService;


	public void decStock(Long key, Long quantity) throws InterruptedException {
		while (!redisLockRepository.lock(key)) {
			Thread.sleep(100);
		}

		try {
			stockService.decStock(key, quantity);
		} finally {
			redisLockRepository.unlock(key);
		}
	}
}