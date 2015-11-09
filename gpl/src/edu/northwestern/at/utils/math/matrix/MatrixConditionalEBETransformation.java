package edu.northwestern.at.utils.math.matrix;

/*	Please see the license information at the end of this file. */

/**	MatrixConditionalEBETransformation.
 *
 * <p>
 * Encapsulates a conditional operation that can be performed on each element of a matrix
 * depending on their position
 * </p>
 */

public interface MatrixConditionalEBETransformation
{
	/**	Transforms an elements value into another value.
	 *
	 *	@param	row			Row to which the element belongs.
	 *	@param	column		Column to which the element belongs.
	 *	@param	element		Value of the element at row,column.
	 *
	 *	@return				Transformed value.
	 */

	double transform( int row , int column , double element );
}

/*
 * <p>
 * JMatrices is copyright &copy; 2001-2004 by Piyush Purang.
 * </p>
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * </p>
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * </p>
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * </p>
 * <p>
 * Modifications 2004-2006 by Northwestern University.
 * </p>
 */

