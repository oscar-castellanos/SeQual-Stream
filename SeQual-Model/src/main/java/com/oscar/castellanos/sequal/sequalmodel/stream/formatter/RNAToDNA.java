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
package com.oscar.castellanos.sequal.sequalmodel.stream.formatter;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;

import com.oscar.castellanos.sequal.sequalmodel.stream.common.SequenceWithTimestamp;
import com.roi.galegot.sequal.sequalmodel.common.Sequence;

/**
 * The Class RNAToDNA.
 */
public class RNAToDNA implements Formatter {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3160351422137802821L;

	@Override
	public Dataset<SequenceWithTimestamp> format(Dataset<SequenceWithTimestamp> sequences, boolean isPaired) {

		if (isPaired) {
			return sequences.map((org.apache.spark.api.java.function.MapFunction<SequenceWithTimestamp,SequenceWithTimestamp>)sequence -> this.doFormatPair(sequence),Encoders.bean(SequenceWithTimestamp.class));
		}

		return sequences.map((org.apache.spark.api.java.function.MapFunction<SequenceWithTimestamp,SequenceWithTimestamp>)sequence -> this.doFormat(sequence),Encoders.bean(SequenceWithTimestamp.class));
	}

	/**
	 * Do format.
	 *
	 * @param sequence the sequence
	 * @return the sequence
	 */
	private SequenceWithTimestamp doFormat(SequenceWithTimestamp sequenceWithTimestamp) {
		Sequence sequence = sequenceWithTimestamp.getSequence();
		sequence.setSequenceString(sequence.getSequenceString().replace("U", "T"));
		return sequenceWithTimestamp;
	}

	/**
	 * Do format pair.
	 *
	 * @param sequence the sequence
	 * @return the sequence
	 */
	private SequenceWithTimestamp doFormatPair(SequenceWithTimestamp sequenceWithTimestamp) {

		this.doFormat(sequenceWithTimestamp);

		Sequence sequence = sequenceWithTimestamp.getSequence();
		sequence.setSequenceStringPair(sequence.getSequenceStringPair().replace("U", "T"));

		return sequenceWithTimestamp;
	}	
}
