package com.delight.gaia.base.converter;

import java.math.BigInteger;

public class BigIntegerUtils {



	public static byte[] toBytesUnsigned(final BigInteger bigInt) {

		// Copied from Apache Commons Codec 1.8

		int bitlen = bigInt.bitLength();

		// round bitlen
		bitlen = ((bitlen + 7) >> 3) << 3;
		final byte[] bigBytes = bigInt.toByteArray();

		if (((bigInt.bitLength() % 8) != 0) && (((bigInt.bitLength() / 8) + 1) == (bitlen / 8))) {
		
			return bigBytes;
		
		}

		// set up params for copying everything but sign bit
		int startSrc = 0;
		int len = bigBytes.length;

		// if bigInt is exactly byte-aligned, just skip signbit in copy
		if ((bigInt.bitLength() % 8) == 0) {
			
			startSrc = 1;
			len--;
		}
		
		final int startDst = bitlen / 8 - len; // to pad w/ nulls as per spec
		final byte[] resizedBytes = new byte[bitlen / 8];
		System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
		return resizedBytes;
	}


	/**
	 * Prevents public instantiation.
	 */
	private BigIntegerUtils() {

	}
}