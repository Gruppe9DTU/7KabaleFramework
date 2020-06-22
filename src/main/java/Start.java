public class Start {
    public static void main(String[] args) {
        GameControl gc = new GameControl();
        for(int i = 0; i < 10; i++){
            gc.makeMove();
        }
    }
}
