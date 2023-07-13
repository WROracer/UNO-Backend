package de.wroracer.uno.backend.util;

import de.wroracer.uno.backend.data.UnoCard;
import de.wroracer.uno.engine.card.Card;

import java.util.List;

public class CardConverter {

    public static List<UnoCard> convertAll(List<Card> cards) {
        return cards.stream().map(CardConverter::convert).toList();
    }

    public static UnoCard convert(Card card) {
        UnoCard c = new UnoCard();
        c.setColor(convertColor(card.getColor()));
        c.setType(convertType(card.getType()));
        return c;
    }

    private static UnoCard.COLOR convertColor(Card.Color color) {
        return switch (color) {
            case RED -> UnoCard.COLOR.RED;
            case BLUE -> UnoCard.COLOR.BLUE;
            case GREEN -> UnoCard.COLOR.GREEN;
            case YELLOW -> UnoCard.COLOR.YELLOW;
            default -> UnoCard.COLOR.BLACK;
        };
    }

    private static UnoCard.TYPE convertType(Card.Type type) {
        return switch (type) {
            case ZERO -> UnoCard.TYPE.ZERO;
            case ONE -> UnoCard.TYPE.ONE;
            case TWO -> UnoCard.TYPE.TWO;
            case THREE -> UnoCard.TYPE.THREE;
            case FOUR -> UnoCard.TYPE.FOUR;
            case FIVE -> UnoCard.TYPE.FIVE;
            case SIX -> UnoCard.TYPE.SIX;
            case SEVEN -> UnoCard.TYPE.SEVEN;
            case EIGHT -> UnoCard.TYPE.EIGHT;
            case NINE -> UnoCard.TYPE.NINE;
            case SKIP -> UnoCard.TYPE.SKIP;
            case REVERSE -> UnoCard.TYPE.REVERSE;
            case DRAW_TWO -> UnoCard.TYPE.DRAW_TWO;
            case WILDE -> UnoCard.TYPE.WILD;
            case WILDE_DRAW_FOUR -> UnoCard.TYPE.WILDE_DRAW_FOUR;
        };
    }

    public static Card convert(UnoCard card) {
        Card.Color color = switch (card.getColor()) {
            case RED -> Card.Color.RED;
            case BLUE -> Card.Color.BLUE;
            case GREEN -> Card.Color.GREEN;
            case YELLOW -> Card.Color.YELLOW;
            default -> Card.Color.BLACK;
        };
        Card.Type type = switch (card.getType()) {
            case REVERSE -> Card.Type.REVERSE;
            case SKIP -> Card.Type.SKIP;
            case DRAW_TWO -> Card.Type.DRAW_TWO;
            case WILD -> Card.Type.WILDE;
            case WILDE_DRAW_FOUR -> Card.Type.WILDE_DRAW_FOUR;
            case ZERO -> Card.Type.ZERO;
            case ONE -> Card.Type.ONE;
            case TWO -> Card.Type.TWO;
            case THREE -> Card.Type.THREE;
            case FOUR -> Card.Type.FOUR;
            case FIVE -> Card.Type.FIVE;
            case SIX -> Card.Type.SIX;
            case SEVEN -> Card.Type.SEVEN;
            case EIGHT -> Card.Type.EIGHT;
            case NINE -> Card.Type.NINE;
        };
        return Card.of(type, color);
    }
}
