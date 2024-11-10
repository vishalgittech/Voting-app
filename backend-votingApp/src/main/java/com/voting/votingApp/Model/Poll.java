package com.voting.votingApp.Model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Poll {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private String question;

   @ElementCollection
   private List<OptionVote> options = new ArrayList<>();

//   @ElementCollection
//   private List<Long> votes = new ArrayList<>();
}
