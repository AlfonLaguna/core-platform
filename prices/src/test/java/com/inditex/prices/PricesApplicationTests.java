package com.inditex.prices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricesApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper; // Para parsear el JSON de la respuesta

	@Test
	void testPriceAt10OnDay14() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> price = parseResponse(response.getBody());
		assertEquals(35455, price.get("productId"));
		assertEquals(1, price.get("brandId"));
		assertEquals(1, price.get("priceList"));
		assertEquals("2020-06-14T00:00:00", price.get("startDate"));
		assertEquals("2020-12-31T23:59:59", price.get("endDate"));
		assertEquals(35.50, (Double) price.get("price"), 0.01); // Tolerancia para doubles
	}

	@Test
	void testPriceAt16OnDay14() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T16:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> price = parseResponse(response.getBody());
		assertEquals(35455, price.get("productId"));
		assertEquals(1, price.get("brandId"));
		assertEquals(2, price.get("priceList"));
		assertEquals("2020-06-14T15:00:00", price.get("startDate"));
		assertEquals("2020-06-14T18:30:00", price.get("endDate"));
		assertEquals(25.45, (Double) price.get("price"), 0.01);
	}

	@Test
	void testPriceAt21OnDay14() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T21:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> price = parseResponse(response.getBody());
		assertEquals(35455, price.get("productId"));
		assertEquals(1, price.get("brandId"));
		assertEquals(1, price.get("priceList"));
		assertEquals("2020-06-14T00:00:00", price.get("startDate"));
		assertEquals("2020-12-31T23:59:59", price.get("endDate"));
		assertEquals(35.50, (Double) price.get("price"), 0.01);
	}

	@Test
	void testPriceAt10OnDay15() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-15T10:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> price = parseResponse(response.getBody());
		assertEquals(35455, price.get("productId"));
		assertEquals(1, price.get("brandId"));
		assertEquals(3, price.get("priceList"));
		assertEquals("2020-06-15T00:00:00", price.get("startDate"));
		assertEquals("2020-06-15T11:00:00", price.get("endDate"));
		assertEquals(30.50, (Double) price.get("price"), 0.01);
	}

	@Test
	void testPriceAt21OnDay16() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-16T21:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		Map<String, Object> price = parseResponse(response.getBody());
		assertEquals(35455, price.get("productId"));
		assertEquals(1, price.get("brandId"));
		assertEquals(4, price.get("priceList"));
		assertEquals("2020-06-15T16:00:00", price.get("startDate"));
		assertEquals("2020-12-31T23:59:59", price.get("endDate"));
		assertEquals(38.95, (Double) price.get("price"), 0.01);
	}

	@Test
	void getPrice_returns404_whenPriceNotFound() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-01-01T00:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertThat(response.getBody(), containsString("Price not found"));
		assertThat(response.getBody(), containsString("No applicable price found for productId: 35455, brandId: 1, date: 2020-01-01T00:00"));
		assertThat(response.getBody(), containsString("\"status\":404"));
	}

	@Test
	void getPrice_returns400_whenDateIsMissing() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody(), containsString("Missing parameter"));
		assertThat(response.getBody(), containsString("Parameter 'date' is required"));
		assertThat(response.getBody(), containsString("\"status\":400"));
	}

	@Test
	void getPrice_returns400_whenProductIdIsMissing() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T10:00:00&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody(), containsString("Missing parameter"));
		assertThat(response.getBody(), containsString("Parameter 'productId' is required"));
		assertThat(response.getBody(), containsString("\"status\":400"));
	}

	@Test
	void getPrice_returns400_whenBrandIdIsMissing() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T10:00:00&productId=35455",
				String.class
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody(), containsString("Missing parameter"));
		assertThat(response.getBody(), containsString("Parameter 'brandId' is required"));
		assertThat(response.getBody(), containsString("\"status\":400"));
	}

	@Test
	void getPrice_returns400_whenDateFormatIsInvalid() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=invalid-date&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody(), containsString("Invalid parameter"));
		assertThat(response.getBody(), containsString("Failed to convert 'date' with value 'invalid-date'"));
		assertThat(response.getBody(), containsString("\"status\":400"));
	}

	@Test
	@Sql(statements = "DROP TABLE PRICES", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD) // Elimina la tabla antes del test
	@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // Restaura la tabla después del test
	void getPrice_returns500_whenUnexpectedErrorOccurs() {
		ResponseEntity<String> response = restTemplate.getForEntity(
				"/prices?date=2020-06-14T10:00:00&productId=35455&brandId=1",
				String.class
		);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertThat(response.getBody(), containsString("Internal server error"));
		assertThat(response.getBody(), containsString("\"status\":500"));
	}

	// parsear el JSON de la respuesta
	private Map<String, Object> parseResponse(String json) {
		try {
			return objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON response: " + json, e);
		}
	}
}
