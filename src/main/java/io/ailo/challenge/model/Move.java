package io.ailo.challenge.model;

/**
 * U = Up
 * D = Down
 * L = Left
 * R = Right
 */
public enum Move implements IAction {
    U {
        @Override
        public void apply(Square s) {
            s.setY(s.getY() == 0 ? s.getGrid().getYMax() : s.getY() - 1);
        }
    },
    D {
        @Override
        public void apply(Square s) {
            s.setY(s.getY() == s.getGrid().getYMax() ? 0 : s.getY() + 1);
        }
    },
    L {
        @Override
        public void apply(Square s) {
            s.setX(s.getX() == 0 ? s.getGrid().getXMax() : s.getX() - 1);
        }
    },
    R {
        @Override
        public void apply(Square s) {
            s.setX(s.getX() == s.getGrid().getXMax() ? 0 : s.getX() + 1);
        }
    }
}
