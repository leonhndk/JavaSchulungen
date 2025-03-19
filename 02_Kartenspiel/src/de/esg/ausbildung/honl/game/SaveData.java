package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public record SaveData(int[] score, ArrayList<Card> cardStack) { }
