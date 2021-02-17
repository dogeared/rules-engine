package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.CompositeResponse;
import com.afitnerd.rulesengine.model.step.BasicStep;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static com.afitnerd.rulesengine.model.ghibli.step.FilmsResponseStep.FILMS_KEY;
import static com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep.PERSON_KEY;
import static com.afitnerd.rulesengine.model.ghibli.step.SpeciesResponseStep.SPECIES_KEY;
import static com.afitnerd.rulesengine.model.ServiceHttpResponse.Status;

public class CompositeResponseStep extends BasicStep {

    private CompositeResponse compositeResponse;

    @Override
    public Status evaluate(KeyValueFieldsRequest request) {
        Person person = fetchState(PERSON_KEY);
        List<Film> films = fetchState(FILMS_KEY);
        Species species = fetchState(SPECIES_KEY);
        compositeResponse = new CompositeResponse(
            Status.SUCCESS, HttpStatus.SC_OK, "success",
            person, films, species
        );
        return compositeResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return compositeResponse;
    }
}
