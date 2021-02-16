package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.SpeciesResponse;
import com.afitnerd.rulesengine.model.step.BasicStep;
import com.afitnerd.rulesengine.service.GhibliService;

import java.util.Map;

import static com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep.PERSON_KEY;

public class SpeciesResponseStep extends BasicStep {

    public static final String SPECIES_KEY = "species";

    private final GhibliService ghibliService;
    private SpeciesResponse speciesResponse;

    public SpeciesResponseStep(GhibliService ghibliService) {
        this.ghibliService = ghibliService;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        Person person = fetchState(PERSON_KEY);
        speciesResponse = ghibliService.findSpeciesByUrl(person.getSpeciesUrl());
        saveState(SPECIES_KEY, speciesResponse.getSpecies());
        return speciesResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return speciesResponse;
    }
}
