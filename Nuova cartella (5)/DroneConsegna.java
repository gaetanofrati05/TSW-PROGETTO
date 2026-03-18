public class DroneConsegna extends Drone{
	private float caricoMassimo;
	private Batteria batt;
	public DroneConsegna(String codice, String modello, caricoMassimo) throws
	{
		super(codice, modello);
		this.caricoMassimo=caricoMassimo;
	}
	public setBatteria(float c)
	{
		batt= new Batteria(c);
	}
	public Batteria getBatteria()
	{
		return batt;
	}
	public float getCaricoMassimo()
	{
		return caricoMassimo;
	}
	public class Batteria{
		float capacita;
		int carica;
		public Batteria(float c)
		{
			capacita=c;
		}
		public void setPercentuale(int i)
		{
			carica=i;
		}
		public int getPercentuale()
		{
			return carica;
		}
		public float getCapacita()
		{
			return capacita;
		}
	}
}