/*

(C) Copyright 2015-2017 Alberto Fernández <infjaf@gmail.com>

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 3.0 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library.  If not, see <http://www.gnu.org/licenses/>.

*/

package com.itsz.netty.binary.dbf.core;


/**
 * Indicates aligment of data.
 */
public enum DBFAlignment {
	/**
	 * Data is aligned to the left (so padding data is appended to the string)
	 */
	LEFT,
	/**
	 * Data is aligned to the right(so padding data is preppended to the string)
	 */
	RIGHT;
}
