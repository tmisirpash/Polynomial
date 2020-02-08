package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException 
	{
		Node poly = null;
		while (sc.hasNextLine()) 
		{
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) 
	{
		if (poly1 == null && poly2 == null)
		{
			return null;
		}
		if (poly1 == null)
		{
			return poly2;
		}
		if (poly2 == null)
		{
			return poly1;
		}
		Node temp1 = poly1;
		Node temp2 = poly2;
		Node poly = null;
		int end = 0;
		while (end == 0)
		{
			if (temp1 == null)
			{
				while (temp2 != null)
				{
					poly = new Node(temp2.term.coeff, temp2.term.degree, poly);
					temp2 = temp2.next;
				}
				end = 1;
			}
			else if (temp2 == null)
			{
				while (temp1 != null)
				{
					poly = new Node(temp1.term.coeff, temp1.term.degree, poly);
					temp1 = temp1.next;
				}
				end = 1;
			}
			else if (temp1.term.degree == temp2.term.degree)
			{
				if (temp1.term.coeff + temp2.term.coeff == 0)
				{
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
				else
				{
					poly = new Node(temp1.term.coeff + temp2.term.coeff, temp1.term.degree, poly);
					temp1 = temp1.next;
					temp2 = temp2.next;
				}
			}
			else if (temp1.term.degree < temp2.term.degree)
			{
				poly = new Node(temp1.term.coeff, temp1.term.degree, poly);
				temp1 = temp1.next;
			}
			else if (temp1.term.degree > temp2.term.degree)
			{
				poly = new Node(temp2.term.coeff, temp2.term.degree, poly);
				temp2 = temp2.next;
			}
		}
		return flip(poly);
	}
	private static Node flip(Node n)
	{
		Node next = null;
		Node prev = null;
		Node temp = n;
		Node curr = temp;
		
		while (curr != null)
		{
			next = curr.next;
			curr.next = prev;
			prev = curr;
			curr = next;
		}
		temp = prev;
		return temp;
	}
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) 
	{
		if (poly1 == null || poly2 == null)
		{
			return null;
		}
		Node temp1 = poly1;
		Node temp2 = poly2;
		Node polynomial1 = null;
		for (Node pointer = temp1; pointer != null; pointer = pointer.next)
		{
			Node polynomial2 = null;
			for (Node pointer2 = temp2; pointer2 != null; pointer2 = pointer2.next)
			{
				if (pointer.term.coeff * pointer2.term.coeff != 0)
				{
					polynomial2 = new Node(pointer.term.coeff*pointer2.term.coeff, pointer.term.degree+pointer2.term.degree, polynomial2);
				}
			}
			polynomial2 = flip(polynomial2);
			polynomial1 = add(polynomial1, polynomial2);
		}
		return polynomial1;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) 
	{
		// running total for the polynomial
		float total = 0; 
		// pointer hops through each node of the polynomial
		for (Node ptr = poly; ptr != null; ptr = ptr.next) 
		{
			// evaluates each term and adds to total
			total += ptr.term.coeff*Math.pow(x, ptr.term.degree);
		}
		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
