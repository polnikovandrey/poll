package com.mcfly.poll.payload.polling;

import com.mcfly.poll.payload.user_role.UserProfile;

public class PollingUserProfile extends UserProfile {

    private Long pollCount;
    private Long voteCount;

    public Long getPollCount() {
        return pollCount;
    }

    public void setPollCount(Long pollCount) {
        this.pollCount = pollCount;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
