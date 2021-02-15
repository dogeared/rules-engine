package com.afitnerd.rulesengine.model.ghibli.step;

import com.afitnerd.rulesengine.model.KeyValueFieldsRequest;
import com.afitnerd.rulesengine.model.ServiceHttpResponse;
import com.afitnerd.rulesengine.model.ghibli.Film;
import com.afitnerd.rulesengine.model.ghibli.Person;
import com.afitnerd.rulesengine.model.ghibli.Species;
import com.afitnerd.rulesengine.model.ghibli.apiresponse.CompositeResponse;
import com.afitnerd.rulesengine.model.step.Step;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;

import static com.afitnerd.rulesengine.model.ghibli.step.FilmsResponseStep.FILMS_KEY;
import static com.afitnerd.rulesengine.model.ghibli.step.PersonResponseStep.PERSON_KEY;
import static com.afitnerd.rulesengine.model.ghibli.step.SpeciesResponseStep.SPECIES_KEY;

public class CompositeResponseStep implements Step {

    private Map<String, Object> stateContainer;
    private CompositeResponse compositeResponse;

    @Override
    public void setStateContainer(Map<String, Object> stateContainer) {
        this.stateContainer = stateContainer;
    }

    @Override
    public ServiceHttpResponse.Status evaluate(KeyValueFieldsRequest request) {
        Person person = (Person)stateContainer.get(PERSON_KEY);
        List<Film> films = (List<Film>)stateContainer.get(FILMS_KEY);
        Species species = (Species)stateContainer.get(SPECIES_KEY);
        compositeResponse = new CompositeResponse(
            ServiceHttpResponse.Status.SUCCESS, HttpStatus.SC_OK, "success",
            person, films, species
        );
        return compositeResponse.getStatus();
    }

    @Override
    public ServiceHttpResponse getResponse() {
        return compositeResponse;
    }
}
