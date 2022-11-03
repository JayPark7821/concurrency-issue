package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OptimisticLockStockService {

	private final StockRepository stockRepository;

	@Transactional
	public void decStock(Long id, Long quantity) {
		Stock stock = stockRepository.findByIdWithOptimisticLock(id);
		stock.decStock(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
