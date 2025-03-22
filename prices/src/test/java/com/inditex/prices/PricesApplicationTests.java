package com.inditex.prices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PricesApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testPriceAt10OnDay14() throws Exception {
		mockMvc.perform(get("/prices")
						.param("date", "2020-06-14T10:00:00")
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(35455))
				.andExpect(jsonPath("$.brandId").value(1))
				.andExpect(jsonPath("$.priceList").value(1))
				.andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
				.andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
				.andExpect(jsonPath("$.price").value(35.50));
	}

	@Test
	public void testPriceAt16OnDay14() throws Exception {
		mockMvc.perform(get("/prices")
						.param("date", "2020-06-14T16:00:00")
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(35455))
				.andExpect(jsonPath("$.brandId").value(1))
				.andExpect(jsonPath("$.priceList").value(2))
				.andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
				.andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
				.andExpect(jsonPath("$.price").value(25.45));
	}

	@Test
	public void testPriceAt21OnDay14() throws Exception {
		mockMvc.perform(get("/prices")
						.param("date", "2020-06-14T21:00:00")
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(35455))
				.andExpect(jsonPath("$.brandId").value(1))
				.andExpect(jsonPath("$.priceList").value(1))
				.andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
				.andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
				.andExpect(jsonPath("$.price").value(35.50));
	}

	@Test
	public void testPriceAt10OnDay15() throws Exception {
		mockMvc.perform(get("/prices")
						.param("date", "2020-06-15T10:00:00")
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(35455))
				.andExpect(jsonPath("$.brandId").value(1))
				.andExpect(jsonPath("$.priceList").value(3))
				.andExpect(jsonPath("$.startDate").value("2020-06-15T00:00:00"))
				.andExpect(jsonPath("$.endDate").value("2020-06-15T11:00:00"))
				.andExpect(jsonPath("$.price").value(30.50));
	}

	@Test
	public void testPriceAt21OnDay16() throws Exception {
		mockMvc.perform(get("/prices")
						.param("date", "2020-06-16T21:00:00")
						.param("productId", "35455")
						.param("brandId", "1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.productId").value(35455))
				.andExpect(jsonPath("$.brandId").value(1))
				.andExpect(jsonPath("$.priceList").value(4))
				.andExpect(jsonPath("$.startDate").value("2020-06-15T16:00:00"))
				.andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
				.andExpect(jsonPath("$.price").value(38.95));
	}
}
