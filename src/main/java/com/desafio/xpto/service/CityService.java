package com.desafio.xpto.service;

import com.desafio.xpto.entity.City;
import com.desafio.xpto.repository.CityRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;

	public void loadCitiesFromCsv(String csvFilePath) throws Exception {
		CSVReader reader = new CSVReader(new FileReader(csvFilePath));
		List<String[]> citiesData = reader.readAll();
		boolean isFirstLine = true;
		for (String[] cityData : citiesData) {
			if (isFirstLine) {
				isFirstLine = false;
				continue;
			}
			City city = new City();
			city.setIbgeId(Integer.parseInt(cityData[0]));
			city.setState(cityData[1]);
			city.setName(cityData[2]);
			city.setIsCapital(!cityData[3].isEmpty());
			city.setLongitude(Double.parseDouble(cityData[4]));
			city.setLatitude(Double.parseDouble(cityData[5]));
			city.setNoAccents(cityData[6]);
			city.setAlternativeNames(cityData[7]);
			city.setMicroregion(cityData[8]);
			city.setMesoregion(cityData[9]);
			cityRepository.save(city);
		}
		reader.close();
	}

	public List<City> getCapitals() {
		return cityRepository.findByIsCapitalTrueOrderByName();
	}

	public Map<String, Object> getStateWithMinMaxCities() {
		Map<String, Long> countByState = getCityCountByState();
		String maxState = Collections.max(countByState.entrySet(), Map.Entry.comparingByValue()).getKey();
		String minState = Collections.min(countByState.entrySet(), Map.Entry.comparingByValue()).getKey();
		Map<String, Object> result = new HashMap<>();
		result.put("maxState", maxState);
		result.put("maxCount", countByState.get(maxState));
		result.put("minState", minState);
		result.put("minCount", countByState.get(minState));
		return result;
	}

	public Map<String, Long> getCityCountByState() {
		return cityRepository.findAll().stream().collect(Collectors.groupingBy(City::getState, Collectors.counting()));
	}

	public City getCityByIbgeId(int ibgeId) {
		return cityRepository.findByIbgeId(ibgeId).orElse(null);
	}

	public List<City> getCitiesByState(String state) {
		return cityRepository.findByState(state);
	}

	public City addCity(City city) {
		return cityRepository.save(city);
	}

	public void deleteCity(Long id) {
		cityRepository.deleteById(id);
	}

	public List<City> filterByColumn(String column, String value) {
		return cityRepository.findAll().stream().filter(city -> cityMatchesColumn(city, column, value)).collect(Collectors.toList());
	}

	private boolean cityMatchesColumn(City city, String column, String value) {
		switch (column.toLowerCase()) {
			case "name": return city.getName().contains(value);
			case "state": return city.getState().contains(value);
			case "ibgeid": return String.valueOf(city.getIbgeId()).contains(value);
			default: return false;
		}
	}

	public Long getDistinctCountByColumn(String column) {
		return cityRepository.findAll().stream().map(city -> getColumnValue(city, column)).distinct().count();
	}

	private String getColumnValue(City city, String column) {
		switch (column.toLowerCase()) {
			case "name": return city.getName();
			case "state": return city.getState();
			case "ibgeid": return String.valueOf(city.getIbgeId());
			default: return "";
		}
	}

	public Long getTotalCityCount() {
		return cityRepository.count();
	}

	public List<City> getFarthestCities() {
		List<City> cities = cityRepository.findAll();
		double maxDistance = 0;
		City cityA = null;
		City cityB = null;

		for (int i = 0; i < cities.size(); i++) {
			for (int j = i + 1; j < cities.size(); j++) {
				double distance = calculateDistance(
						cities.get(i).getLatitude(), cities.get(i).getLongitude(),
						cities.get(j).getLatitude(), cities.get(j).getLongitude()
				);

				if (distance > maxDistance) {
					maxDistance = distance;
					cityA = cities.get(i);
					cityB = cities.get(j);
				}
			}
		}

		return Arrays.asList(cityA, cityB);
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		final int EARTH_RADIUS = 6371; // Raio da Terra em km

		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLon / 2) * Math.sin(dLon / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}
}
