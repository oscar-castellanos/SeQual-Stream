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
package com.oscar.castellanos.sequal.sequalmodel.stream.filter.single;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.sql.Dataset;

import com.oscar.castellanos.sequal.sequalmodel.stream.common.SequenceWithTimestamp;
import com.roi.galegot.sequal.sequalmodel.common.Sequence;
import com.roi.galegot.sequal.sequalmodel.filter.FilterParametersNaming;
import com.roi.galegot.sequal.sequalmodel.util.ExecutionParametersManager;

public class NAmbP implements SingleFilter {

	private static final long serialVersionUID = -390276120695449143L;

	/**
	 * Validate.
	 *
	 * @param sequences the sequences
	 * @param isPaired  if the sequences are paired or not
	 * @return the Dataset
	 */
	@Override
	public Dataset<SequenceWithTimestamp> validate(Dataset<SequenceWithTimestamp> sequences, boolean isPaired){
		Double limMin;
		Double limMax;

		String limMinStr;
		String limMaxStr;

		Boolean limMinUse;
		Boolean limMaxUse;

		limMinStr = ExecutionParametersManager.getParameter(FilterParametersNaming.NAMBP_MIN_VAL);
		limMaxStr = ExecutionParametersManager.getParameter(FilterParametersNaming.NAMBP_MAX_VAL);

		limMin = (limMinUse = StringUtils.isNotBlank(limMinStr)) ? new Double(limMinStr) : null;
		limMax = (limMaxUse = StringUtils.isNotBlank(limMaxStr)) ? new Double(limMaxStr) : null;

		if (!limMinUse && !limMaxUse) {
			return sequences;
		}

		if (isPaired) {
			return sequences.filter((org.apache.spark.api.java.function.FilterFunction<SequenceWithTimestamp>)s -> this.filter(s.getSequence(), limMin, limMinUse, limMax, limMaxUse)
					&& this.filterPair(s.getSequence(), limMin, limMinUse, limMax, limMaxUse));
		}

		return sequences.filter((org.apache.spark.api.java.function.FilterFunction<SequenceWithTimestamp>)s -> this.filter(s.getSequence(), limMin, limMinUse, limMax, limMaxUse));
	}

	/**
	 * Filter.
	 *
	 * @param sequence  the sequence
	 * @param limMin    the lim min
	 * @param limMinUse the lim min use
	 * @param limMax    the lim max
	 * @param limMaxUse the lim max use
	 * @return the boolean
	 */
	private Boolean filter(Sequence sequence, Double limMin, Boolean limMinUse, Double limMax, Boolean limMaxUse) {
		return this.compare(sequence.getnAmbP(), limMin, limMinUse, limMax, limMaxUse);
	}

	/**
	 * Filter pair.
	 *
	 * @param sequence  the sequence
	 * @param limMin    the lim min
	 * @param limMinUse the lim min use
	 * @param limMax    the lim max
	 * @param limMaxUse the lim max use
	 * @return the boolean
	 */
	private Boolean filterPair(Sequence sequence, Double limMin, Boolean limMinUse, Double limMax, Boolean limMaxUse) {
		return this.compare(sequence.getnAmbPPair(), limMin, limMinUse, limMax, limMaxUse);
	}

	/**
	 * Compare.
	 *
	 * @param nAmbP     the n amb P
	 * @param limMin    the lim min
	 * @param limMinUse the lim min use
	 * @param limMax    the lim max
	 * @param limMaxUse the lim max use
	 * @return the boolean
	 */
	private Boolean compare(double nAmbP, Double limMin, Boolean limMinUse, Double limMax, Boolean limMaxUse) {

		if (limMinUse && limMaxUse) {
			return ((nAmbP >= limMin) && (nAmbP <= limMax));
		}
		if (limMinUse) {
			return (nAmbP >= limMin);
		}
		if (limMaxUse) {
			return (nAmbP <= limMax);
		}

		return true;
	}

}
