package com.example.crudapp.сontroller;

import com.example.crudapp.domain.Message;
import com.example.crudapp.repo.MessageRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainController {

    private final MessageRepo messageRepo;

    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public Iterable<Message> getAll(){
        return messageRepo.findAll() ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable(name = "id", required = true) Long id) {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent()) {
            return new ResponseEntity<>(message.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Message> add(@RequestBody(required = true) Message message) {
        messageRepo.save(message);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam(name = "id", required = true) Long id) {
        Optional<Message> message = messageRepo.findById(id);
        if (message.isPresent()) {
            messageRepo.delete(message.get());
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Message> update(@RequestParam(name = "id", required = true) Long id,
                                          @RequestBody(required = true) Message newMessage) {
        Optional<Message> messageOptional = messageRepo.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText(newMessage.getText());
            messageRepo.save(message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
