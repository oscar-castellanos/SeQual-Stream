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
import com.oscar.castellanos.sequal.sequalmodel.stream.filter.FQFilter;
import com.roi.galegot.sequal.sequalmodel.common.Sequence;
import com.roi.galegot.sequal.sequalmodel.filter.FilterParametersNaming;
import com.roi.galegot.sequal.sequalmodel.util.ExecutionParametersManager;

public class Quality extends FQFilter implements SingleFilter {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6867361108805219701L;
	
	/**
	 * Validate.
	 *
	 * @param sequences the sequences
	 * @param isPaired  if the sequences are paired or not
	 * @return the Dataset
	 */
	@Override
	public Dataset<SequenceWithTimestamp> validate(Dataset<SequenceWithTimestamp> sequences, boolean isPaired) {
		Double limMin;
		Double limMax;

		String limMinStr;
		String limMaxStr;

		Boolean limMinUse;
		Boolean limMaxUse;

		limMinStr = ExecutionParametersManager.getParameter(FilterParametersNaming.QUALITY_MIN_VAL);
		limMaxStr = ExecutionParametersManager.getParameter(FilterParametersNaming.QUALITY_MAX_VAL);
		limMinUse = StringUtils.isNotBlank(limMinStr);
		limMaxUse = StringUtils.isNotBlank(limMaxStr);

		limMin = (limMinUse) ? new Double(limMinStr) : null;
		limMax = (limMaxUse) ? new Double(limMaxStr) : null;

		if ((!limMinUse && !limMaxUse) || !this.hasQuality) {
			return sequences;
		}

		if (isPaired) {
			return sequences.filter((org.apache.spark.api.java.function.FilterFunction<SequenceWithTimestamp>)sequence -> this.filter(sequence.getSequence(), limMin, limMinUse, limMax, limMaxUse)
					&& this.filterPair(sequence.getSequence(), limMin, limMinUse, limMax, limMaxUse));
		}

		return sequences.filter((org.apache.spark.api.java.function.FilterFunction<SequenceWithTimestamp>)sequence -> this.filter(sequence.getSequence(), limMin, limMinUse, limMax, limMaxUse));
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
		if (sequence.getHasQuality()) {
			return this.compare(sequence.getQuality(), limMin, limMinUse, limMax, limMaxUse);
		}

		return false;
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
		if (sequence.getHasQuality()) {
			return this.compare(sequence.getQualityPair(), limMin, limMinUse, limMax, limMaxUse);
		}

		return false;
	}

	/**
	 * Compare.
	 *
	 * @param quality   the quality
	 * @param limMin    the lim min
	 * @param limMinUse the lim min use
	 * @param limMax    the lim max
	 * @param limMaxUse the lim max use
	 * @return the boolean
	 */
	private Boolean compare(double quality, Double limMin, Boolean limMinUse, Double limMax, Boolean limMaxUse) {
		if (limMinUse && limMaxUse) {
			return ((quality >= limMin) && (quality <= limMax));
		}
		if (limMinUse) {
			return (quality >= limMin);
		}
		if (limMaxUse) {
			return (quality <= limMax);
		}
		return true;
	}

}
