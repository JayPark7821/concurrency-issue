package com.example.stock.facade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

@SpringBootTest
class NamedLockStockFacadeTest {

	@Autowired
	private NamedLockStockFacade namedLockStockFacade;

	@Autowired
	private StockRepository stockRepository;

	@BeforeEach
	void before() {
		Stock stock = new Stock(1L, 100L);
		stockRepository.saveAndFlush(stock);
	}

	@AfterEach
	void after() {
		stockRepository.deleteAll();
	}

	@Test
	void 동시에_100개_요청 () throws Exception {
		//given
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		CountDownLatch latch = new CountDownLatch(threadCount);
		//when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try{
					namedLockStockFacade.decStock(1L, 1L);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();
		//then
		Stock stock = stockRepository.findById(1L).orElseThrow();
		assertEquals(0,stock.getQuantity());
	}
}