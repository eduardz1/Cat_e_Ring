package main.businesslogic.service;

import main.businesslogic.menu.Menu;

/**
 * Service
 */
public record Service(Event linkedEvent, Menu referencedMenu) {
}