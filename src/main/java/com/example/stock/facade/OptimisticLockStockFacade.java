package com.example.stock.facade;

import org.springframework.stereotype.Component;

import com.example.stock.service.OptimisticLockStockService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OptimisticLockStockFacade {

	private final OptimisticLockStockService optimisticLockStockService;

	public void decStock(Long id, Long quantity) throws InterruptedException {

		while (true){
			try {
				optimisticLockStockService.decStock(id, quantity);
				break;
			} catch (Exception e) {
				Thread.sleep(50);
			}
		}
	}
}
