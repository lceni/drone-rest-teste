package br.com.lceni.drone.resource;

/**
 * Define o rob�, seu status atual e a dimens�o do campo de atua��o.
 * @author Vostro
 *
 */
public class Drone {

	/**
	 * Define os movimentos permitidos.
	 */
	public static final String ALLOWED_MOVEMENT = "LRM"; // esquerda, direita, mover
	/**
	 * Define as dire��es permitidas.
	 */
	public static final String ALLOWED_FACING = "NESW"; // norte, leste, sul, oeste
	
	/**
	 * a posi��o atual no plano x.
	 */
	private int x;
	/**
	 * a posi��o atual no plano y.
	 */
	private int y;
	/**
	 * o tamanho m�ximo de x.
	 */
	private int maxX;
	/**
	 * o tamanho m�ximo de y.
	 */
	private int maxY;
	/**
	 * dire��o para qual est� apontando.
	 */
	private char facing;
	
	/**
	 * Inicializa o rob�.
	 * Sendo o canto superior esquerdo = 0 x 0.
	 * Sendo o canto inferior direito = maxX x maxY.
	 * @param x
	 * @param y
	 * @param maxX
	 * @param maxY
	 * @param facing
	 */
	public Drone(int x, int y, int maxX, int maxY, char facing) {
		this.x = x;
		this.y = y;
		this.maxX = maxX;
		this.maxY = maxY;
		this.facing = facing;
	}
	
	/**
	 * Gira o robo para a esquerda.
	 */
	public void left() {
		switch (facing) {
		case 'N': this.facing = 'W'; break;
		case 'E': this.facing = 'N'; break;
		case 'S': this.facing = 'E'; break;
		case 'W': this.facing = 'S'; break;
		default: break;
		}
	}
	
	/**
	 * Gira o robo para a direita.
	 */
	public void right() {
		switch (facing) {
		case 'N': this.facing = 'E'; break;
		case 'E': this.facing = 'S'; break;
		case 'S': this.facing = 'W'; break;
		case 'W': this.facing = 'N'; break;
		default: break;
		}
	}
	
	/**
	 * Move o robo em 1 metro no sentido em que estiver apontando pelo atributo facing.
	 * @return false se estiver nos limites do campo definidos entre 0-maxX e 0-maxY
	 */
	public boolean move() {
		if (facing == 'N' && y == 0) return false;
		if (facing == 'E' && x == maxX) return false;
		if (facing == 'S' && y == maxY) return false;
		if (facing == 'W' && x == 0) return false;
		
		switch (facing) {
		case 'N': this.y--; break;
		case 'E': this.x++; break;
		case 'S': this.y++; break;
		case 'W': this.x--; break;
		default: return false;
		}
		
		return true;
	}
	
	/**
	 * Retorna o estado atual do rob�.
	 * @return o estado no formato JSON.
	 */
	public String status() {
		return "{'x':"+x+",'y':"+y+",'facing':'"+facing+"'}";
	}
}
