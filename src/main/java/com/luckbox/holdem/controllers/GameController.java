package com.luckbox.holdem.controllers;


import com.luckbox.holdem.models.game.Card;
import com.luckbox.holdem.models.game.Deck;
import com.luckbox.holdem.models.game.GameEvaluator;
import com.luckbox.holdem.models.game.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luckbox.holdem.models.User;
import com.luckbox.holdem.repositories.UserRepository;
/**
 * Created by danielseetoh on 5/22/19.
 */

@Controller
@RequestMapping(path="/game")
public class GameController {
    @GetMapping(path="/cards")
    public @ResponseBody String getCards() {
        // TODO: log generated cards

        Deck deck = new Deck();
        for (Card card : deck.deck) {
            System.out.println(card.toString());
        }

        return "Cards";
    }

    @GetMapping(path="/test")
    public @ResponseBody String getTest() {
        GameEvaluator.getBestCombo(null);

        return "Cards";
    }
}
