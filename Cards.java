import java.util.ArrayList;
import java.util.Random;

class Cards {
    ArrayList<Card> cards = new ArrayList<>();
    private Random random = new Random();

    public Cards() {
        setElements(cards);
        mix(cards);
    }

    public void setElements(ArrayList<Card> cards) {
        for (int i = 0; i < 2; i++) {
            cards.add(new Card("Back to the Future", "img/backToTheFutureImage.png", "img/back.png"));
            cards.add(new Card("Titanic", "img/titanicImage.png", "img/back.png"));
            cards.add(new Card("Harry Potter", "img/harryPotterImage.png", "img/back.png"));
            cards.add(new Card("The Godfather", "img/theGodFatherImage.png", "img/back.png"));
            cards.add(new Card("Forrest Gump", "img/forrestGumpImage.png", "img/back.png"));
            cards.add(new Card("Rocky", "img/rockyImage.png", "img/back.png"));
            cards.add(new Card("Pirates of the Caribbean", "img/piratesOfTheCaribbeanImage.png", "img/back.png"));
            cards.add(new Card("Oppenheimer", "img/oppenheimerImage.png", "img/back.png"));
            cards.add(new Card("Barbie", "img/barbieImage.png", "img/back.png"));
        }
    }

    public void mix(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            int j = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public void reshuffle() {
        mix(cards);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}