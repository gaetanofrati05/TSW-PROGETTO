public abstract Drone extends DispositivoVolo{
	private final String codice;
	private String modello;
	private int quota;
	private static int nDroni=0;
	private static List<String> lista= new ArrayList<> ();
	
	public Drone(String codice, String modello) throws Exception
	{
		if(lista.contains(codice))//ho utilizzato questo metodo perchè non avevo ancora letto quella parte della traccia
		//implemento cmq anche l'altra versione cos' vedi se è giusta
		
			throw new Exception("codice già usato");
		else
		{
			lista.add(codice);
			this.codice=codice;
			quota=0;
			this.modello=modello;
			nDroni++;//codice="DRONE_"+nDroni;
			
		}
	}
	public String getCodice()
	{
		return codice;
	}
	public void decolla(int quota)
	{
		if(quota>100)
			throw new Exception("troppo alto");
		else
			this.quota=quota;
	}
	public int getQuota()
	{
		return quota;
	}
	public void atterra()
	{
		quota=0;
	}
	public abstract String mostraStato()
	{
		return codice+";"+modello+";"+quota+";";
	}
	
}