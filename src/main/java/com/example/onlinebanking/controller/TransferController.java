package com.example.onlinebanking.controller;

import com.example.onlinebanking.dto.TransferRequest;
import com.example.onlinebanking.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@CrossOrigin
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<String> transfer(
            @Valid @RequestBody TransferRequest request,
            Authentication auth) {
        transferService.transfer(request, auth);
        return ResponseEntity.ok("Transfer successful");
    }
}