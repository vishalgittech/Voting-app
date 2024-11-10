import { Component, OnInit } from '@angular/core';
import { PollService } from '../poll.service';
import { Poll } from '../poll.models';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-poll',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './poll.component.html',
  styleUrls: ['./poll.component.css']
})
export class PollComponent implements OnInit {

  newPoll: Poll = {
    id: 0,
    question: '',
    options: [{ optionText: ' ', voteCount: 0 },
    { optionText: ' ', voteCount: 0 }
    ]
  }

  polls: Poll[] = [];
  constructor(private _pollService: PollService) { }

  ngOnInit(): void {
    this.loadPolls();
  }

  loadPolls() {
    this._pollService.getPolls().subscribe({
      next: (data) => {
        this.polls = data;
      },
      error: (error) => {
        console.error("Error loading polls", error);
      }
    });
  }

  addOption() {
    this.newPoll.options.push({ optionText: ' ', voteCount: 0 });
  }

  createPoll() {
    // Validation: Check if question and options are not empty
    if (!this.newPoll.question.trim() || this.newPoll.options.some(opt => !opt.optionText.trim())) {
      console.error("Poll question or options cannot be empty.");
      return;
    }

    this._pollService.createPoll(this.newPoll).subscribe({
      next: (createdPoll) => {
        this.polls.push(createdPoll);
        this.resetPoll();
      },
      error: (error) => {
        console.error("Error creating poll", error);
      }
    });
  }

  resetPoll() {
    this.newPoll = {
      id: 0,
      question: '',
      options: [{ optionText: ' ', voteCount: 0 },
      { optionText: ' ', voteCount: 0 }
      ]
    };
  }

  vote(pollId: number, optionIndex: number) {
    const poll = this.polls.find(p => p.id === pollId);
    if (!poll) return; // If poll doesn't exist

    // Prevent multiple votes on the same option by the same user (this is just a simulation)
    if (poll.options[optionIndex].voteCount >= 1) {
      console.error("You have already voted on this option.");
      return;
    }

    this._pollService.vote(pollId, optionIndex).subscribe({
      next: () => {
        poll.options[optionIndex].voteCount++;
      },
      error: (error) => {
        console.error("Error voting on poll", error);
      }
    });
  }

  trackByIndex(index: number): number {
    return index;
  }
}
