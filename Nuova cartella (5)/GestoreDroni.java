public class GestoreDroni{
	public List<Drone> droni = new ArrayList<>();
	public static void gui()
	{
		JFrame frame= new JFrame("finesta");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280,720);
		
		JPanel pannello= new JPanel();
		frame.setContentPane(pannello);
		panello.setLayout(new FlowLayout());
		JTextField codice= new JTextField("codice"); 
		JTextField modello= new JTextField("modello"); 
		JTextField carico= new JTextField("carico massimo"); 
		JTextField capacita= new JTextField("capacita betteria"); 
		
		JButton aggiungi= new JButton("aggiungi drone");
		
		pannello.add(codice);
		pannello.add(modello);
		pannello.add(carico);
		pannello.add(capacita);
		
		ActionListener aggiunta= new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try{
					Drone d= new(codice.getText(), modello.getText(),Float.parseFloat( carico.getText()));
					d.setBatteria(Integer.parseInt(capacita.getText()));
					droni.add(d);
				catch(Exception ec)
					System.err.println(e.getMessage());
			}
		}
		bottone.addActionListener(aggiunta);

		
		//manca salva flotta per questioni di tempo 
		//manca l'eccezione che dovevo creare per questioni fi tempo
		frame.setVisible(true);
	}
	public static void main(String args[])
	{
		//non istanzio i droni per questioni di tempo
		droni.stream().flter(d-> d instanceof DroneConsegna)
		.filter(d-> d.getBatteria().getPercentuale()>50).forEach(System.out::println);
		Double somma=
		droni.stream().flter(d-> d instanceof DroneConsegna)
		.filter(d-> d.getQuota()==0).mapToDouble(d-> d.getCaricoMassimo()).sum();
		
		droni.stream().flter(d-> d instanceof DroneConsegna)
		.filter(d-> d.getBatteria().getCapacita()>5000).filter(d-> d.getCodice()).forEach(System.out::println);
	}
}