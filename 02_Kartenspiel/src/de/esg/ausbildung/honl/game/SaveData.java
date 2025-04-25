package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public record SaveData(int balance, int[] score, ArrayList<Card> cardStack) {
}
