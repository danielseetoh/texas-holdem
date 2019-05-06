package com.luckbox.holdem.models.game;

import com.luckbox.holdem.models.User;

import java.util.ArrayList;

/**
 * Created by danielseetoh on 5/7/19.
 */
public class Player {
    public Card[] hand;
    public User user;

    public Player(User user) {
        this.user = user;
    }
}
