package com.exam.myntexam.controllers;

import com.exam.myntexam.storage.data.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author lorenzomalafo
 */
@RestController
@RequestMapping("/api")
public class AppStatusController {

    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> getStatus() {
        return new ResponseEntity<>(new Status("OK", "Application is running"),
                HttpStatus.OK);
    }
}
