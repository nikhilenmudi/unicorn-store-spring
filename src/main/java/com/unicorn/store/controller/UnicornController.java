package com.unicorn.store.controller;

import com.unicorn.store.exceptions.ResourceNotFoundException;
import com.unicorn.store.model.Unicorn;
import com.unicorn.store.service.UnicornService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class UnicornController {
    private final UnicornService unicornService;
    private static final Logger logger = LoggerFactory.getLogger(UnicornController.class);

    public UnicornController(UnicornService unicornService) {
        this.unicornService = unicornService;
    }

    @RequestMapping(value="/unicorns", method = RequestMethod.POST)
    public ResponseEntity<Unicorn> createUnicorn(@RequestBody Unicorn unicorn) {
        try {
            Unicorn savedUnicorn = unicornService.createUnicorn(unicorn);
            return ResponseEntity.ok(savedUnicorn);
        } catch (Exception e) {
            String errorMsg = "Error creating unicorn";
            logger.error(errorMsg, e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, errorMsg, e);
        } finally {
        }
    }

    @RequestMapping(value = "/unicorns", method = RequestMethod.GET)
    public ResponseEntity<List<Unicorn>> getAllUnicorns() {

        try {
            List<Unicorn> savedUnicorns = unicornService.getAllUnicorns();
            return ResponseEntity.ok(savedUnicorns);
        } catch (Exception e) {
            String errorMsg = "Error reading unicorns";
            logger.error(errorMsg, e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, errorMsg, e);
        } finally {
        }
    }

    @RequestMapping(value = "/unicorns/{unicornId}", method = RequestMethod.PUT)
    public ResponseEntity<Unicorn> updateUnicorn(@RequestBody Unicorn unicorn,
            @PathVariable String unicornId) {
        try {
            Unicorn savedUnicorn = unicornService.updateUnicorn(unicorn, unicornId);
            return ResponseEntity.ok(savedUnicorn);
        } catch (Exception e) {
            String errorMsg = "Error updating unicorn";
            logger.error(errorMsg, e);
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, errorMsg, e);
        } finally {
        }
    }

    @RequestMapping(value = "/unicorns/{unicornId}", method = RequestMethod.GET)
    public ResponseEntity<Unicorn> getUnicorn(@PathVariable String unicornId) {
        try {
            Unicorn unicorn = unicornService.getUnicorn(unicornId);
            return ResponseEntity.ok(unicorn);
        } catch (ResourceNotFoundException e) {
            String errorMsg = "Unicorn not found";
            logger.error(errorMsg, e);
            throw new ResponseStatusException(NOT_FOUND, errorMsg, e);
        } finally {
        }
    }

    @RequestMapping(value = "/unicorns/{unicornId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUnicorn(@PathVariable String unicornId) {
        try {
            unicornService.deleteUnicorn(unicornId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            String errorMsg = "Unicorn not found";
            logger.error(errorMsg, e);
            throw new ResponseStatusException(NOT_FOUND, errorMsg, e);
        } finally {
        }
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getWelcomeMessage() {
        return new ResponseEntity<>("Welcome to the Unicorn Store!", HttpStatus.OK);
    }
}