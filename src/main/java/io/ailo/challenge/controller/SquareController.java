package io.ailo.challenge.controller;

import io.ailo.challenge.service.SquareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class SquareController {

    @Resource
    private SquareService squareService;

    @GetMapping("/")
    public String info() {
        return "Ailo Zombie Apocalypse by Xin Huang";
    }

    @PostMapping("/run")
    public ResponseEntity<String> run(@RequestParam("input") MultipartFile inputFile) throws IOException {
        String content = new String(inputFile.getBytes(), StandardCharsets.UTF_8);
        String output = squareService.getFinalOutput(content);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
}
