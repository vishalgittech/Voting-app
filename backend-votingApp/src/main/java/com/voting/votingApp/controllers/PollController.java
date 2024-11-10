package com.voting.votingApp.controllers;


import com.voting.votingApp.Model.Poll;
import com.voting.votingApp.request.Vote;
import com.voting.votingApp.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/polls")
@CrossOrigin(origins = "http://localhost:4200")
public class PollController {

    @Autowired
    private PollService pollService;

    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        // Code to create a poll goes here
        return pollService.createPoll(poll);
    }
    @GetMapping
    public List<Poll> getAllPoll()
    {
        return pollService.getAllPoll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Poll> getPollByID(@PathVariable Long id)
    {
        return pollService.getPollById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote){
        pollService.vote(vote.getPollId(),vote.getOptionIndex());
    }
}
