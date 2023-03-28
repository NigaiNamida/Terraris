public class BossPanel {
    private JLabel nextLabel;
    private int gridCols;
    private int gridRows;

    private int blockSize;

    private TetrisPiece block;

    public NextPanel(){
        nextLabel = new JLabel("NEXT");
        nextLabel.setForeground(new Color(193,221,196,255));
        nextLabel.setFont(new Font("Futura",Font.BOLD,20));

        //setting panel
        this.setBounds(545, 50, 100, 100);
        this.setBackground(Color.black);
        this.setBorder(new LineBorder(Color.WHITE,3));

        //add component
        this.add(nextLabel);
        block = PlayZone.getNextPiece();

        //set value of var for grid
        gridCols = 5;
        blockSize = this.getWidth()/ gridCols;
        gridRows = this.getHeight() / blockSize;

    }
}
