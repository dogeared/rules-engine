package com.afitnerd.rulesengine.service;

import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.FilmsResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PeopleResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.PersonResponse;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.SpeciesResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.afitnerd.rulesengine.model.ServiceHttpResponse.Status;

@Service
public class GhibliServiceImpl implements GhibliService {

    private final ObjectMapper mapper;

    private final TypeReference<List<Person>> typeRef = new TypeReference<>() {};

    public GhibliServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PeopleResponse listPeople() {
        try {
            HttpResponse httpResponse = Request.Get(PEOPLE_ENDPOINT)
                .execute()
                .returnResponse();
            return new PeopleResponse(
                Status.SUCCESS, httpResponse.getStatusLine().getStatusCode(),
            "got list of people", mapper.readValue(httpResponse.getEntity().getContent(), typeRef)
            );
        } catch (IOException e) {
            return new PeopleResponse(
                Status.FAILURE, HttpStatus.SC_BAD_REQUEST, "failed to get a list of people"
            );
        }
    }

    @Override
    public PersonResponse findPersonByName(String name) {
        // this is unnecessary - we could use the api directly
        PeopleResponse peopleResponse = listPeople();
        if (peopleResponse.getStatus() == Status.FAILURE) {
            return new PersonResponse(
                Status.FAILURE, HttpStatus.SC_NOT_FOUND, "list people failed"
            );
        }
        Optional<Person> person = peopleResponse.getPeople().stream()
            .filter(p -> p.getName().equalsIgnoreCase(name.toLowerCase()))
            .findFirst();
        return person
            .map(p -> new PersonResponse(
                Status.SUCCESS, HttpStatus.SC_OK, "found person", p
            ))
            .orElseGet(() -> new PersonResponse(
                Status.FAILURE, HttpStatus.SC_NOT_FOUND, "person not found"
            ));
    }

    @Override
    public FilmsResponse listFilmsByUrls(List<String> filmUrls) {
        try {
            List<Film> films = new ArrayList<>();
            for (String url : filmUrls) {
                films.add(
                    mapper.readValue(Request.Get(url).execute().returnResponse().getEntity().getContent(), Film.class)
                );
            }
            return new FilmsResponse(ServiceHttpResponse.Status.SUCCESS, HttpStatus.SC_OK, "got films", films);
        } catch (IOException e) {
            return new FilmsResponse(
                Status.SUCCESS, HttpStatus.SC_BAD_REQUEST, "got films"
            );
        }
    }

    @Override
    public SpeciesResponse findSpeciesByUrl(String speciesUrl) {
        try {
            HttpResponse httpResponse = Request.Get(speciesUrl)
                .execute()
                .returnResponse();
            return new SpeciesResponse(
                Status.SUCCESS, httpResponse.getStatusLine().getStatusCode(),
            "found species", mapper.readValue(httpResponse.getEntity().getContent(), Species.class)
            );
        } catch (IOException e) {
            return new SpeciesResponse(
                Status.FAILURE, HttpStatus.SC_BAD_REQUEST, "couldn't find species"
            );
        }
    }
}
