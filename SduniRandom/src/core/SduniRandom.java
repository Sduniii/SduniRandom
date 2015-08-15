package core;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SduniRandom extends Random {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9148407510341463493L;

	public enum Algorithm {
		RANDU, XOR, SDUNI, DEFAULT
	}

	private Lock l = new ReentrantLock();
	private Algorithm algo;
	private long v, w, u;

	public SduniRandom(Algorithm algo, long seed) {
		l.lock();
		this.w = 1;
		this.algo = algo;
		this.u = 4101842887655102017L ^ seed;
		int64();
		this.v = this.u;
		int64();
		this.w = v;
		int64();
		l.unlock();
	}

	public SduniRandom(Algorithm algo) {
		l.lock();
		this.w = 1;
		this.algo = algo;
		this.u = 4101842887655102017L ^ System.nanoTime();
		int64();
		this.v = this.u;
		int64();
		this.w = v;
		int64();
		l.unlock();
	}
	
	public SduniRandom(long seed) {
		l.lock();
		this.w = 1;
		this.algo = Algorithm.DEFAULT;
		this.u = 4101842887655102017L ^ seed;
		int64();
		this.v = this.u;
		int64();
		this.w = v;
		int64();
		l.unlock();
	}

	public SduniRandom() {
		l.lock();
		this.w = 1;
		this.algo = Algorithm.DEFAULT;
		this.u = 4101842887655102017L ^ System.nanoTime();
		int64();
		this.v = this.u;
		int64();
		this.w = v;
		int64();
		l.unlock();
	}

	private long bit64XORShift() {
		this.v = this.v ^ (this.v >> 21);
		this.v = this.v ^ (this.v << 35);
		this.v = this.v ^ (this.v >> 4);
		return this.v;
	}

	/**
	 * RANDU algorithm by IBM
	 * 
	 * @return random BigInteger value
	 */
	private long randu() {
		long a = 65539;
		long c = 0;
		long m = Math.round(Math.pow(2, 31));
		this.v = (this.v * a + c) % m;
		return this.v;
	}

	private long int64() {
		l.lock();
		try {
			u = u * 2862933555777941757L + 7046029254386353087L;
			v ^= v >>> 17;
			v ^= v << 31;
			v ^= v >>> 8;
			w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
			long x = u ^ (u << 21);
			x ^= x >>> 35;
			x ^= x << 4;
			return (x + v) ^ w;
		} finally {
			l.unlock();
		}
	}

	public long next(long low, long high) {
			return low + (long)(next() * ((high - low) + 1));
	}

	// TODO
	public double next() {
		switch (this.algo) {
		case RANDU:
			return 5.42101086242752217E-20 * randu() + 0.5;
		case XOR:
			return 5.42101086242752217E-20 * bit64XORShift() + 0.5;
		case SDUNI:
			return 5.42101086242752217E-20 * int64() + 0.5;
		default:
			return 5.42101086242752217E-20 * int64() + 0.5;
		}
	}
}
