package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StockService {

	private final StockRepository stockRepository;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decStock(Long id, Long quantity) {
		Stock stock = stockRepository.findById(id).orElseThrow();
		stock.decStock(quantity);
		stockRepository.saveAndFlush(stock);
	}
}
