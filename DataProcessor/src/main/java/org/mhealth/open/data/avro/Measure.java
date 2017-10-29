/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.mhealth.open.data.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Measure extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 238273103559650170L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Measure\",\"namespace\":\"org.mhealth.open.data.avro\",\"fields\":[{\"name\":\"unit\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"value\",\"type\":\"float\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Measure> ENCODER =
      new BinaryMessageEncoder<Measure>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Measure> DECODER =
      new BinaryMessageDecoder<Measure>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Measure> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Measure> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Measure>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Measure to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Measure from a ByteBuffer. */
  public static Measure fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.String unit;
  @Deprecated public float value;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Measure() {}

  /**
   * All-args constructor.
   * @param unit The new value for unit
   * @param value The new value for value
   */
  public Measure(java.lang.String unit, java.lang.Float value) {
    this.unit = unit;
    this.value = value;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return unit;
    case 1: return value;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: unit = (java.lang.String)value$; break;
    case 1: value = (java.lang.Float)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'unit' field.
   * @return The value of the 'unit' field.
   */
  public java.lang.String getUnit() {
    return unit;
  }

  /**
   * Sets the value of the 'unit' field.
   * @param value the value to set.
   */
  public void setUnit(java.lang.String value) {
    this.unit = value;
  }

  /**
   * Gets the value of the 'value' field.
   * @return The value of the 'value' field.
   */
  public java.lang.Float getValue() {
    return value;
  }

  /**
   * Sets the value of the 'value' field.
   * @param value the value to set.
   */
  public void setValue(java.lang.Float value) {
    this.value = value;
  }

  /**
   * Creates a new Measure RecordBuilder.
   * @return A new Measure RecordBuilder
   */
  public static org.mhealth.open.data.avro.Measure.Builder newBuilder() {
    return new org.mhealth.open.data.avro.Measure.Builder();
  }

  /**
   * Creates a new Measure RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Measure RecordBuilder
   */
  public static org.mhealth.open.data.avro.Measure.Builder newBuilder(org.mhealth.open.data.avro.Measure.Builder other) {
    return new org.mhealth.open.data.avro.Measure.Builder(other);
  }

  /**
   * Creates a new Measure RecordBuilder by copying an existing Measure instance.
   * @param other The existing instance to copy.
   * @return A new Measure RecordBuilder
   */
  public static org.mhealth.open.data.avro.Measure.Builder newBuilder(org.mhealth.open.data.avro.Measure other) {
    return new org.mhealth.open.data.avro.Measure.Builder(other);
  }

  /**
   * RecordBuilder for Measure instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Measure>
    implements org.apache.avro.data.RecordBuilder<Measure> {

    private java.lang.String unit;
    private float value;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(org.mhealth.open.data.avro.Measure.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.unit)) {
        this.unit = data().deepCopy(fields()[0].schema(), other.unit);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.value)) {
        this.value = data().deepCopy(fields()[1].schema(), other.value);
        fieldSetFlags()[1] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Measure instance
     * @param other The existing instance to copy.
     */
    private Builder(org.mhealth.open.data.avro.Measure other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.unit)) {
        this.unit = data().deepCopy(fields()[0].schema(), other.unit);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.value)) {
        this.value = data().deepCopy(fields()[1].schema(), other.value);
        fieldSetFlags()[1] = true;
      }
    }

    /**
      * Gets the value of the 'unit' field.
      * @return The value.
      */
    public java.lang.String getUnit() {
      return unit;
    }

    /**
      * Sets the value of the 'unit' field.
      * @param value The value of 'unit'.
      * @return This builder.
      */
    public org.mhealth.open.data.avro.Measure.Builder setUnit(java.lang.String value) {
      validate(fields()[0], value);
      this.unit = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'unit' field has been set.
      * @return True if the 'unit' field has been set, false otherwise.
      */
    public boolean hasUnit() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'unit' field.
      * @return This builder.
      */
    public org.mhealth.open.data.avro.Measure.Builder clearUnit() {
      unit = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'value' field.
      * @return The value.
      */
    public java.lang.Float getValue() {
      return value;
    }

    /**
      * Sets the value of the 'value' field.
      * @param value The value of 'value'.
      * @return This builder.
      */
    public org.mhealth.open.data.avro.Measure.Builder setValue(float value) {
      validate(fields()[1], value);
      this.value = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'value' field has been set.
      * @return True if the 'value' field has been set, false otherwise.
      */
    public boolean hasValue() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'value' field.
      * @return This builder.
      */
    public org.mhealth.open.data.avro.Measure.Builder clearValue() {
      fieldSetFlags()[1] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Measure build() {
      try {
        Measure record = new Measure();
        record.unit = fieldSetFlags()[0] ? this.unit : (java.lang.String) defaultValue(fields()[0]);
        record.value = fieldSetFlags()[1] ? this.value : (java.lang.Float) defaultValue(fields()[1]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Measure>
    WRITER$ = (org.apache.avro.io.DatumWriter<Measure>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Measure>
    READER$ = (org.apache.avro.io.DatumReader<Measure>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}