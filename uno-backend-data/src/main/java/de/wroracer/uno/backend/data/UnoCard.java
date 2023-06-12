package de.wroracer.uno.backend.data;

public class UnoCard {
    private TYPE type;
    private COLOR color;

    public COLOR getColor() {
        return color;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return color.toString() + "-" + type.toString();
    }

    public enum TYPE {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        DRAW_TWO,
        WILDE_DRAW_FOUR,
        SKIP,
        REVERSE,
        WILD, ZERO;

        @Override
        public String toString() {
            switch (this) {
                case ONE:
                    return "1";
                case TWO:
                    return "2";
                case THREE:
                    return "3";
                case FOUR:
                    return "4";
                case FIVE:
                    return "5";
                case SIX:
                    return "6";
                case SEVEN:
                    return "7";
                case EIGHT:
                    return "8";
                case NINE:
                    return "9";
                case DRAW_TWO:
                    return "d2";
                case WILDE_DRAW_FOUR:
                    return "wd4";
                case SKIP:
                    return "s";
                case REVERSE:
                    return "r";
                case WILD:
                    return "w";
                default:
                    return "unknown";
            }
        }
    }

    public enum COLOR {
        GREEN,
        YELLOW,
        RED,
        BLUE,
        BLACK;

        @Override
        public String toString() {
            switch (this) {
                case GREEN:
                    return "g";
                case YELLOW:
                    return "y";
                case RED:
                    return "r";
                case BLUE:
                    return "b";
                case BLACK:
                    return "black";
                default:
                    return "unknown";
            }
        }
    }

}
