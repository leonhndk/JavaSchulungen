package de.esg.ausbildung.honl.game;

import java.util.ArrayList;

public record SaveData(double balance, int[] score, ArrayList<Card> cardStack) { 
}
