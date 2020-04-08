package ru.salnikov.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.salnikov.repositories.PersonDynamicArray;

@Endpoint
public class PersonEndpoint {
    private static final String NAMESPACE_URI = "http://soap.salnikov.ru";

    private PersonDynamicArray personDynamicArray;

    @Autowired
    public PersonEndpoint(PersonDynamicArray personDynamicArray) {
        this.personDynamicArray = personDynamicArray;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPersonByIdRequest")
    @ResponsePayload
    public GetPersonByIdResponse getPersonById(@RequestPayload GetPersonByIdRequest request) {
        GetPersonByIdResponse response = new GetPersonByIdResponse();
        PersonDynamicArray resultArray = (PersonDynamicArray) personDynamicArray.searchBy(x -> x.getId() == request.getId());
        if (personDynamicArray.getCount() != 0) {
            response.setPerson((Person)resultArray.get(0));
        }
        return response;
    }


}
