// Generated by proto2javame, Mon Nov 15 16:01:12 MSK 2021.

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import itelma.InvalidProtocolBufferException;
import itelma.Pair;
import itelma.Parser3;
import itelma.ProtoParserError;
import itelma.Serializer3;

public final class ClientParams {
	private final String ParameterName;
	private final boolean hasParameterName;
	private static final int FIELD_NUMBER_PARAMETER_NAME = 1;

	private final int ParameterId;
	private final boolean hasParameterId;
	private static final int FIELD_NUMBER_PARAMETER_ID = 2;

	private final String StringValue;
	private final boolean hasStringValue;
	private static final int FIELD_NUMBER_STRING_VALUE = 3;

	private final int IntValue;
	private final boolean hasIntValue;
	private static final int FIELD_NUMBER_INT_VALUE = 4;


	public static Builder newBuilder() {
		return new Builder();
	}

	private ClientParams(final Builder builder) {
			this.ParameterName = builder.ParameterName;
			this.hasParameterName = builder.hasParameterName;
			this.ParameterId = builder.ParameterId;
			this.hasParameterId = builder.hasParameterId;
			this.StringValue = builder.StringValue;
			this.hasStringValue = builder.hasStringValue;
			this.IntValue = builder.IntValue;
			this.hasIntValue = builder.hasIntValue;
	}

	public static class Builder {
		private String ParameterName;
		private boolean hasParameterName = false;

		private int ParameterId;
		private boolean hasParameterId = false;

		private String StringValue;
		private boolean hasStringValue = false;

		private int IntValue;
		private boolean hasIntValue = false;


		private Builder() {
		}

		public Builder setParameterName(final String ParameterName) {
			this.ParameterName = ParameterName;
			this.hasParameterName = true;
			return this;
		}

		public Builder setParameterId(final int ParameterId) {
			this.ParameterId = ParameterId;
			this.hasParameterId = true;
			return this;
		}

		public Builder setStringValue(final String StringValue) {
			this.StringValue = StringValue;
			this.hasStringValue = true;
			return this;
		}

		public Builder setIntValue(final int IntValue) {
			this.IntValue = IntValue;
			this.hasIntValue = true;
			return this;
		}

		public ClientParams build() {
			return new ClientParams(this);
		}
	}

	public String getParameterName() {
		return ParameterName;
	}

	public boolean hasParameterName() {
		return hasParameterName;
	}

	public int getParameterId() {
		return ParameterId;
	}

	public boolean hasParameterId() {
		return hasParameterId;
	}

	public String getStringValue() {
		return StringValue;
	}

	public boolean hasStringValue() {
		return hasStringValue;
	}

	public int getIntValue() {
		return IntValue;
	}

	public boolean hasIntValue() {
		return hasIntValue;
	}

	public String toString() {
		String retValue = "";
		retValue += this.getClass().getName() + "(";
		if(hasParameterName) retValue += "ParameterName = " + this.ParameterName + "\r\n";
		if(hasParameterId) retValue += "ParameterId = " + this.ParameterId + "\r\n";
		if(hasStringValue) retValue += "StringValue = " + this.StringValue + "\r\n";
		if(hasIntValue) retValue += "IntValue = " + this.IntValue + "\r\n";
		retValue += ")";

		return retValue;
	}

	// Override
	public int computeSize() {
		int totalSize = 0;
		if(hasParameterName) totalSize += ComputeSizeUtil.computeStringSize(FIELD_NUMBER_PARAMETER_NAME, ParameterName);
		if(hasParameterId) totalSize += ComputeSizeUtil.computeIntSize(FIELD_NUMBER_PARAMETER_ID, ParameterId);
		if(hasStringValue) totalSize += ComputeSizeUtil.computeStringSize(FIELD_NUMBER_STRING_VALUE, StringValue);
		if(hasIntValue) totalSize += ComputeSizeUtil.computeIntSize(FIELD_NUMBER_INT_VALUE, IntValue);
		totalSize += computeNestedMessageSize();

		return totalSize;
	}

	private int computeNestedMessageSize() {
		int messageSize = 0;

		return messageSize;
	}

	// Override
	public void writeFields(final OutputWriter writer) throws IOException {
		if(hasParameterName) writer.writeString(FIELD_NUMBER_PARAMETER_NAME, ParameterName);
		if(hasParameterId) writer.writeInt(FIELD_NUMBER_PARAMETER_ID, ParameterId);
		if(hasStringValue) writer.writeString(FIELD_NUMBER_STRING_VALUE, StringValue);
		if(hasIntValue) writer.writeInt(FIELD_NUMBER_INT_VALUE, IntValue);
	}

	static ClientParams parseFields(final InputReader reader) throws IOException {
		int nextFieldNumber = getNextFieldNumber(reader);
		final ClientParams.Builder builder = ClientParams.newBuilder();

		while (nextFieldNumber > 0) {
			if(!populateBuilderWithField(reader, builder, nextFieldNumber)) {
				reader.getPreviousTagDataTypeAndReadContent();
			}
			nextFieldNumber = getNextFieldNumber(reader);
		}

		return builder.build();
	}

	static int getNextFieldNumber(final InputReader reader) throws IOException {
		return reader.getNextFieldNumber();
	}

	public static ClientParams parsing(byte[] b) throws ProtoParserError {
		Builder builder = ClientParams.newBuilder();
		int start = 0;
		while(start < b.length) {
		Pair pair = Parser3.getPair(b, start);
		switch (pair.fieldNumber) {
			case FIELD_NUMBER_PARAMETER_NAME:
				String ParameterName = Parser3.getString(pair.content);
				builder.setParameterName(ParameterName); 
				break;
			case FIELD_NUMBER_PARAMETER_ID:
				int ParameterId = Parser3.getInt32(pair.content);
				builder.setParameterId(ParameterId); 
				break;
			case FIELD_NUMBER_STRING_VALUE:
				String StringValue = Parser3.getString(pair.content);
				builder.setStringValue(StringValue); 
				break;
			case FIELD_NUMBER_INT_VALUE:
				int IntValue = Parser3.getInt32(pair.content);
				builder.setIntValue(IntValue); 
				break;
			default:break;
		}
		if (start >= pair.endContent) throw new ProtoParserError("Err par");
		start = pair.endContent;
		}
		return builder.build();
	}

	public static ClientParams parseFrom(final byte[] data) throws IOException {
		return parseFields(new InputReader(data));
	}

	public static ClientParams parseFrom(final InputStream inputStream) throws IOException {
		return parseFields(new InputReader(inputStream));
	}

	public static ClientParams parseDelimitedFrom(final InputStream inputStream) throws IOException {
		final int limit = DelimitedSizeUtil.readDelimitedSize(inputStream);
		return parseFields(new InputReader(new DelimitedInputStream(inputStream, limit)));
	}
}