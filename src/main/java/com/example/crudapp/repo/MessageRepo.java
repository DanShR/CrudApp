package com.example.crudapp.repo;

import com.example.crudapp.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepo extends CrudRepository<Message, Long> {
}
