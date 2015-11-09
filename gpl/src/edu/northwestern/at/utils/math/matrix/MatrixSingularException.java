package edu.northwestern.at.utils.math.matrix;

/*	Please see the license information at the end of this file. */

/** Error for reporting matrix singularity.
 */

public class MatrixSingularException
	extends java.lang.RuntimeException
{
	/**	Constructs MatrixSingular exception.
	 */

	public MatrixSingularException()
	{
		super();
	}

	/**	Constructs MatrixSingular exception.
	 *
	 *	@param	errorMessage	Error message.
	 */

	public MatrixSingularException( String errorMessage )
	{
		super( errorMessage );
	}
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

