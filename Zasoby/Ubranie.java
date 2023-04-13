package Zasoby;

public class Ubranie {
    private int x;
    private int wytrzymalosc;

    public Ubranie(int x) {
        this.x = x;
        wytrzymalosc = (int) Math.pow(x, 2);
    }

    public int getWytrzymalosc() {
        return wytrzymalosc;
    }

    public void setWytrzymalosc(int wytrzymalosc) {
        this.wytrzymalosc = wytrzymalosc;
    }
}
