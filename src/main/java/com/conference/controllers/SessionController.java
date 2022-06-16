package com.conference.controllers;

import com.conference.models.Session;
import com.conference.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> list() {
        return sessionRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Session find(@PathVariable Long id) {
        return sessionRepository.findById(id).orElse(null);
    }

    // Post request
    @PostMapping
    // Type response code 201 - created
    @ResponseStatus(HttpStatus.CREATED)
    // request body content
    public Session create(@RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Session update(@PathVariable Long id, @RequestBody Session session) {
        //TODO: Validation for attributes are ok, return 400  status
        Session existSession = sessionRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(session, existSession, "session_id");
        return sessionRepository.saveAndFlush(existSession);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        sessionRepository.deleteById(id);
    }
}
