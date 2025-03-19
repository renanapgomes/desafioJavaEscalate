package com.desafio.xpto.controller;

import com.desafio.xpto.entity.City;
import com.desafio.xpto.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cities")
@Tag(name = "City Controller", description = "Gerenciamento de Cidades")
public class CityController {

	@Autowired
	private CityService cityService;

	@Operation(summary = "Carregar arquivo", description = "Ler o arquivo CSV das cidades para a base de dados.")
	@PostMapping("/load")
	public ResponseEntity<String> loadCities(@RequestParam("file") MultipartFile csvFile) {
		try {
			Path tempFile = Files.createTempFile("cities-", ".csv");
			Files.write(tempFile, csvFile.getBytes());

			cityService.loadCitiesFromCsv(tempFile.toString());

			Files.deleteIfExists(tempFile);

			return ResponseEntity.ok("Cidades carregadas com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao carregar CSV: " + e.getMessage());
		}
	}

	@Operation(summary = "Retornar capitais", description = "Retorna todas as cidades que são capitais, ordenadas por nome.")
	@GetMapping("/capitals")
	public ResponseEntity<List<City>> getCapitals() {
		return ResponseEntity.ok(cityService.getCapitals());
	}

	@Operation(summary = "Obter estado com maior e menor número de cidades", description = "Retorna o estado com maior e menor quantidade de cidades e a contagem.")
	@GetMapping("/states/min-max")
	public ResponseEntity<Map<String, Object>> getStateWithMinMaxCities() {
		return ResponseEntity.ok(cityService.getStateWithMinMaxCities());
	}

	@Operation(summary = "Obter quantidade de cidades por estado", description = "Retorna a quantidade de cidades agrupadas por estado.")
	@GetMapping("/count-by-state")
	public ResponseEntity<Map<String, Long>> getCityCountByState() {
		return ResponseEntity.ok(cityService.getCityCountByState());
	}

	@Operation(summary = "Buscar cidade por IBGE ID", description = "Retorna as informações da cidade informando o ID do IBGE.")
	@GetMapping("/id/{ibgeId}")
	public ResponseEntity<City> getCityByIbgeId(@PathVariable int ibgeId) {
		return ResponseEntity.ok(cityService.getCityByIbgeId(ibgeId));
	}

	@Operation(summary = "Listar cidades por estado", description = "Retorna o nome das cidades baseado no estado informado.")
	@GetMapping("/state/{state}")
	public ResponseEntity<List<City>> getCitiesByState(@PathVariable String state) {
		return ResponseEntity.ok(cityService.getCitiesByState(state));
	}

	@Operation(summary = "Adicionar nova cidade", description = "Permite adicionar uma nova cidade ao sistema.")
	@PostMapping
	public ResponseEntity<City> addCity(@RequestBody City city) {
		return ResponseEntity.ok(cityService.addCity(city));
	}

	@Operation(summary = "Deletar cidade", description = "Remove uma cidade do sistema com base no ID.")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCity(@PathVariable Long id) {
		cityService.deleteCity(id);
		return ResponseEntity.ok("Cidade removida com sucesso.");
	}

	@Operation(summary = "Filtrar cidades", description = "Filtrar cidades com base em uma coluna e string.")
	@GetMapping("/filter/{column}/{value}")
	public ResponseEntity<List<City>> filterByColumn(@PathVariable String column, @PathVariable String value) {
		return ResponseEntity.ok(cityService.filterByColumn(column, value));
	}


	@Operation(summary = "Distinct Count", description = "Retornar a quantidade de registros distintos baseado em uma coluna.")
	@GetMapping("/distinct-count/{column}")
	public ResponseEntity<Long> getDistinctCountByColumn(@PathVariable String column) {
		return ResponseEntity.ok(cityService.getDistinctCountByColumn(column));
	}

	@Operation(summary = "Contagem", description = "Retornar a quantidade de registros total.")
	@GetMapping("/total-count")
	public ResponseEntity<Long> getTotalCityCount() {
		return ResponseEntity.ok(cityService.getTotalCityCount());
	}

	@Operation(summary = "Calculo de Distancia", description ="Dentre todas as cidades, obter as duas cidades mais distantes uma da outra com base + " +
	"na localização (distância em KM em linha reta)")
	@GetMapping("/farthest")
	public ResponseEntity<List<City>> getFarthestCities() {
		return ResponseEntity.ok(cityService.getFarthestCities());
	}
}
