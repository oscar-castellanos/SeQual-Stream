/*
 * This file is part of SeQual.
 * 
 * SeQual is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SeQual is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SeQual.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.oscar.castellanos.sequal.sequalmodel.stream.trimmer;

/**
 * The Enum Trimmers.
 */
public enum TrimmersStream {

	/** The trimleft. */
	TRIMLEFT(0, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimLeft"),

	/** The trimright. */
	TRIMRIGHT(1, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimRight"),

	/** The trimleftp. */
	TRIMLEFTP(2, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimLeftP"),

	/** The trimrightp. */
	TRIMRIGHTP(3, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimRightP"),

	/** The trimqualleft. */
	TRIMQUALLEFT(4, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimQualLeft"),

	/** The trimqualright. */
	TRIMQUALRIGHT(5, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimQualRight"),

	/** The trimleft. */
	TRIMNLEFT(6, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimNLeft"),

	/** The trimright. */
	TRIMNRIGHT(7, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimNRight"),

	/** The trimlefttolength. */
	TRIMLEFTTOLENGTH(8, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimLeftToLength"),

	/** The trimrighttolength. */
	TRIMRIGHTTOLENGTH(9, "com.oscar.castellanos.sequal.sequalmodel.stream.trimmer.TrimRightToLength");

	/** The Constant MAX_FILTER_PRIORITY. */
	public static final int MAX_TRIMMER_PRIORITY = 9;

	/** The trimmer class name. */
	private String trimmerClassName;

	/** The priority. */
	private int priority;

	/**
	 * Instantiates a new trimmers.
	 *
	 * @param priority         the priority
	 * @param trimmerClassName the trimmer class name
	 */
	private TrimmersStream(int priority, String trimmerClassName) {
		this.priority = priority;
		this.trimmerClassName = trimmerClassName;
	}

	/**
	 * Gets the trimmer class.
	 *
	 * @return the trimmer class
	 */
	public String getTrimmerClass() {
		return this.trimmerClassName;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return this.priority;
	}
}