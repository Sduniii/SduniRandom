package core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class SduniRandom {
	
	public enum Algorithm{RANDU,XOR,SDUNI}
	
	private Algorithm algo;
	private BigInteger seed, w;
	
	public SduniRandom(Algorithm algo, BigInteger seed){
		this.algo = algo;
		this.seed = BigInteger.valueOf(4101842887655102017L).xor(seed);
		this.w = BigInteger.valueOf(1);
	}
	
	public SduniRandom(Algorithm algo){
		this.algo = algo;
		this.seed = BigInteger.valueOf(4101842887655102017L).xor(BigInteger.valueOf(new Date().getTime()));
		this.w = BigInteger.valueOf(1);
	}
	
	public SduniRandom(){
		this.algo = null;
		this.seed = BigInteger.valueOf(4101842887655102017L).xor(BigInteger.valueOf(new Date().getTime()));
		this.w = BigInteger.valueOf(1);
	}
	
	private BigInteger bit64XORShift(){
		this.seed = this.seed.xor(this.seed.shiftRight(17));
		this.seed = this.seed.xor(this.seed.shiftLeft(31));
		this.seed = this.seed.xor(this.seed.shiftRight(8));
		return this.seed;
	}
	

	/**
	 * RANDU algorithm by IBM
	 * @return random BigInteger value
	 */
	private BigInteger randu(){
		BigInteger a = BigInteger.valueOf(65539);
		BigInteger c = BigInteger.valueOf(0);
		BigInteger m = (new BigInteger("2")).pow(31);
		this.seed =  (this.seed.multiply(a).add(c)).mod(m);
		return this.seed;
	}
	
	private BigInteger int64(){
		this.seed = bit64XORShift();
		this.w = BigInteger.valueOf(4294957665L).multiply(this.w.and(new BigInteger("ffffffff", 16))).add(this.w.shiftRight(32));
		return this.seed.xor(this.w);
	}
	
	public BigInteger next(BigInteger low, BigInteger high){
		switch(this.algo){
		case RANDU:
			return new BigDecimal(Math.sqrt(Math.pow((((randu().doubleValue()) % (high.doubleValue() + 1. - low.doubleValue())) + low.doubleValue()),2))).toBigInteger();
		case XOR:
			return new BigDecimal(Math.sqrt(Math.pow((((bit64XORShift().doubleValue()) % (high.doubleValue() + 1. - low.doubleValue())) + low.doubleValue()),2))).toBigInteger();
		case SDUNI:
			return new BigDecimal(Math.sqrt(Math.pow((((int64().doubleValue() * 0.0000000000000000000542101086242752217) % (high.doubleValue() + 1. - low.doubleValue())) + low.doubleValue()),2))).toBigInteger(); 
		default:
			return new BigDecimal(Math.sqrt(Math.pow((((int64().doubleValue() * 0.0000000000000000000542101086242752217) % (high.doubleValue() + 1. - low.doubleValue())) + low.doubleValue()),2))).toBigInteger();
		}
	}
	
	public double next(){
		switch(this.algo){
		case RANDU:
			return randu().doubleValue();
		case XOR:
			return bit64XORShift().doubleValue();
		case SDUNI:
			return int64().doubleValue() * 0.0000000000000000000542101086242752217; 
		default:
			return int64().doubleValue() * 0.0000000000000000000542101086242752217;
		}
	}
}
