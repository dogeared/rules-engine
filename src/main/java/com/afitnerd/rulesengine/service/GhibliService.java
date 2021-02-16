package com.afitnerd.rulesengine.service;

import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PeopleResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.SpeciesResponse;

import java.util.List;

public interface GhibliService {

    String BASE_URL = "https://ghibliapi.herokuapp.com";
    String PEOPLE_ENDPOINT = BASE_URL + "/people";
    String SPECIES_ENDPOINT = BASE_URL + "/species";
    String FILMS_ENDPOINT = BASE_URL + "/films";

    PeopleResponse listPeople();
    PersonResponse findPersonByName(String name);
    FilmsResponse listFilmsByUrls(List<String> filmUrls);
    SpeciesResponse findSpeciesByUrl(String speciesUrl);
}
