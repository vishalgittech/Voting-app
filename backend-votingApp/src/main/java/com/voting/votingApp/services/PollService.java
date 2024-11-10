package com.voting.votingApp.services;


import com.voting.votingApp.Model.OptionVote;
import com.voting.votingApp.Model.Poll;
import com.voting.votingApp.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

    public List<Poll> getAllPoll() {
        return pollRepository.findAll();
    }

    public Optional<Poll> getPollById(Long id) {
        return pollRepository.findById(id);

    }

    public void vote(Long pollId, int optionIndex) {
        //Get Poll from DB
        Poll poll = pollRepository.findById(pollId).orElseThrow(()-> new RuntimeException("Poll not found"));

        //Get list of all options
        List <OptionVote >options = poll.getOptions();

        //If Index for Vote is not valid , throw exception
        if (optionIndex<0 || optionIndex>= options.size()) {
            throw new IllegalArgumentException("Invalid option index");
        }
        //Get selected option
        OptionVote selectedOption = options.get(optionIndex);

        //Increment vote for Selected option
         selectedOption.setVoteCount(selectedOption.getVoteCount() + 1);

         //Save increamented option into database
         pollRepository.save(poll);
    }

}

