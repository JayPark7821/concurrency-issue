package com.example.stock.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.OptimisticLockStockService;
import com.example.stock.service.StockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NamedLockStockFacade {

	private final LockRepository lockRepository;
	private final StockService stockService;

	@Transactional
	public void decStock(Long id, Long quantity) {
		try{
			lockRepository.getLock(id.toString());
			stockService.decStock(id, quantity);
		}finally {
			lockRepository.releaseLock(id.toString());
		}
	}
}
