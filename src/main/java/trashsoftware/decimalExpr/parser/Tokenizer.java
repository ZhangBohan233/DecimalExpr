package trashsoftware.decimalExpr.parser;

import trashsoftware.numbers.Rational;

import java.util.ArrayList;
import java.util.List;

class Tokenizer {

    private final static String NUMBERS = "0123456789";
    private final static String ALPHA = "abcdefghijklmnopqrstuvexyzABCDEFGHIJKLMNOPQRSTUVEXYZ";
    private final static String SYMBOLS = "=+-*/%^\\!@#$&<>?`~";
    private final static int[] SELF_CONCAT_TYPES = {0, 1};
    private final static int[][] CROSS_CONCAT_TYPES = {
            {1, 0},  // text and number
            {0, 10},  // number and dot
            {10, 0},  // dot and number
            {0, 14},  // number and '{'
            {14, 0},  // '{' and number
            {0, 15},  // number and '}'
            {15, 0},  // '}' and number
            {10, 14}  // dot and '{'
    };

    private String expression;
    private List<Token> tokens = new ArrayList<>();

    Tokenizer(String expression) {
        this.expression = expression;
    }

    List<Token> tokenize() {
        tokens.clear();
        StringBuilder stringBuilder = new StringBuilder();
        int curr = -1;
        int prev;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            prev = curr;
            curr = charType(c);
            if (!concatenateAble(prev, curr)) {
                addToken(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            stringBuilder.append(c);
        }
        addToken(stringBuilder.toString());
        return tokens;
    }

    private void addToken(String s) {
        String trimmed = s.trim();
        if (trimmed.length() > 0) {
            if (isNumber(trimmed)) {
                tokens.add(new NumberToken(trimmed));
            } else {
                tokens.add(new IdToken(s));
            }
        }
    }

    private static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (!(isDigit(c) || c == '.' || c == Rational.FRONT_REPEAT_CHAR || c == Rational.BACK_REPEAT_CHAR))
                return false;
        }
        return true;
    }

    private static int charType(char c) {
        if (isDigit(c)) return 0;
        else if (isAlpha(c)) return 1;
        else if (isSymbol(c)) return 2;
        else switch (c) {
                case '.':
                    return 10;
                case '(':
                    return 11;
                case ')':
                    return 12;
                case ',':
                    return 13;
                case Rational.FRONT_REPEAT_CHAR:
                    return 14;
                case Rational.BACK_REPEAT_CHAR:
                    return 15;
                default:
                    return -1;
            }
    }

    private static boolean isDigit(char c) {
        return NUMBERS.indexOf(c) != -1;
    }

    private static boolean isAlpha(char c) {
        return ALPHA.indexOf(c) != -1;
    }

    private static boolean isSymbol(char c) {
        return SYMBOLS.indexOf(c) != -1;
    }

    private static boolean concatenateAble(int prev, int curr) {
        if (prev == curr) {
            return arrayContainsInt(prev);
        } else {
            return crossConcatenate(prev, curr);
        }
    }

    private static boolean arrayContainsInt(int value) {
        for (int i : Tokenizer.SELF_CONCAT_TYPES) {
            if (i == value) return true;
        }
        return false;
    }

    private static boolean crossConcatenate(int prev, int curr) {
        for (int[] pair : CROSS_CONCAT_TYPES) {
            if (prev == pair[0] && curr == pair[1]) return true;
        }
        return false;
    }
}
