/*
 * <copyright>
 *  
 *  Copyright 1997-2007 BBNT Solutions, LLC
 *  under sponsorship of the Defense Advanced Research Projects
 *  Agency (DARPA).
 * 
 *  You can redistribute this software and/or modify it under the
 *  terms of the Cougaar Open Source License as published on the
 *  Cougaar Open Source Website (www.cougaar.org).
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 * </copyright>
 */
package be.abeel.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A sorting comparator to sort strings numerically, ie [1, 2, 10], as opposed
 * to [1, 10, 2].
 */
public final class NaturalOrderComparator implements Comparator<String> {

	public static final Comparator<String> NUMERICAL_ORDER = new NaturalOrderComparator(false);
	public static final Comparator<String> NUMERICAL_ORDER_IGNORE_CASE = new NaturalOrderComparator(true);
	private boolean ignoreCase;
	

	private NaturalOrderComparator(boolean ignoreCase) {
		this.ignoreCase=ignoreCase;
	}

	private int compareRight(String a, String b) {
		int bias = 0;
		int ia = 0;
		int ib = 0;

		// The longest run of digits wins. That aside, the greatest
		// value wins, but we can't know that it will until we've scanned
		// both numbers to know that they have the same magnitude, so we
		// remember it in BIAS.
		for (;; ia++, ib++) {
			char ca = charAt(a, ia);
			char cb = charAt(b, ib);

			if (!Character.isDigit(ca) && !Character.isDigit(cb)) {
				return bias;
			} else if (!Character.isDigit(ca)) {
				return -1;
			} else if (!Character.isDigit(cb)) {
				return +1;
			} else if (ca < cb) {
				if (bias == 0) {
					bias = -1;
				}
			} else if (ca > cb) {
				if (bias == 0)
					bias = +1;
			} else if (ca == 0 && cb == 0) {
				return bias;
			}
		}
	}

	public int compare(String a, String b) {
		if(ignoreCase){
			a=a.toLowerCase();
			b=b.toLowerCase();
		}
		
		int ia = 0, ib = 0;
		int nza = 0, nzb = 0;
		char ca, cb;
		int result;

		while (true) {
			// only count the number of zeroes leading the last number compared
			nza = nzb = 0;

			ca = charAt(a, ia);
			cb = charAt(b, ib);

			// skip over leading spaces or zeros
			while (Character.isSpaceChar(ca) || ca == '0') {
				if (ca == '0') {
					nza++;
				} else {
					// only count consecutive zeroes
					nza = 0;
				}

				ca = charAt(a, ++ia);
			}

			while (Character.isSpaceChar(cb) || cb == '0') {
				if (cb == '0') {
					nzb++;
				} else {
					// only count consecutive zeroes
					nzb = 0;
				}

				cb = charAt(b, ++ib);
			}

			// process run of digits
			if (Character.isDigit(ca) && Character.isDigit(cb)) {
				if ((result = compareRight(a.substring(ia), b.substring(ib))) != 0) {
					return result;
				}
			}

			if (ca == 0 && cb == 0) {
				// The strings compare the same. Perhaps the caller
				// will want to call strcmp to break the tie.
				return nza - nzb;
			}

			if (ca < cb) {
				return -1;
			} else if (ca > cb) {
				return +1;
			}

			++ia;
			++ib;
		}
	}

	private static char charAt(String s, int i) {
		if (i >= s.length()) {
			return 0;
		} else {
			return s.charAt(i);
		}
	}

	public static void main(String[] args) {
		String[] strings;
		if (args.length > 0) {
			String filename = args[0];
			try {
				List<String> data = new ArrayList<String>();
				BufferedReader br = new BufferedReader(new FileReader(filename));
				while (true) {
					String line = br.readLine();
					if (line == null)
						break;
					int sep = line.indexOf("#");
					if (sep >= 0) {
						line = line.substring(0, sep);
					}
					line = line.trim();
					if (line.length() == 0)
						continue;
					data.add(line);
				}
				br.close();
				strings = data.toArray(new String[data.size()]);
			} catch (Exception e) {
				System.err.println("Unable to read " + filename);
				e.printStackTrace();
				System.exit(-1);
				return;
			}
		} else {
			strings = new String[] { "1-2", "1-02", "1-20", "10-20", "fred", "jane", "pic01", "pic2", "pic02",
					"pic02a", "pic3", "pic4", "pic 4 else", "pic 5", "pic05", "pic 5", "pic 5 something", "pic 6",
					"pic   7", "pic100", "pic100a", "pic120", "pic121", "pic02000", "tom", "x2-g8", "x2-y7", "x2-y08",
					"x8-y8" };
		}

		List<String> orig = Arrays.asList(strings);

		System.out.println("Original: " + orig);

		List<String> scrambled = Arrays.asList(strings);
		Collections.shuffle(scrambled);

		System.out.println("Scrambled: " + scrambled);

		Collections.sort(scrambled, NaturalOrderComparator.NUMERICAL_ORDER);

		System.out.println("Sorted: " + scrambled);
	}
}