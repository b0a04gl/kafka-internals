/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.personal.gallery.kafkainternals.avro.pojo;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class MatchStats extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 2904749624394925113L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MatchStats\",\"namespace\":\"com.personal.gallery.kafkainternals.avro.pojo\",\"fields\":[{\"name\":\"Innings\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Inning\",\"fields\":[{\"name\":\"battingTeam\",\"type\":\"string\"},{\"name\":\"bowlingTeam\",\"type\":\"string\"},{\"name\":\"totalRuns\",\"type\":\"long\"},{\"name\":\"totalWickets\",\"type\":\"long\"},{\"name\":\"totalOvers\",\"type\":\"double\"}]}},\"namespace\":\"com.personal.gallery.kafkainternals.avro.pojo\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<MatchStats> ENCODER =
      new BinaryMessageEncoder<MatchStats>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<MatchStats> DECODER =
      new BinaryMessageDecoder<MatchStats>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<MatchStats> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<MatchStats> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<MatchStats>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this MatchStats to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a MatchStats from a ByteBuffer. */
  public static MatchStats fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> Innings;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public MatchStats() {}

  /**
   * All-args constructor.
   * @param Innings The new value for Innings
   */
  public MatchStats(java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> Innings) {
    this.Innings = Innings;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return Innings;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: Innings = (java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'Innings' field.
   * @return The value of the 'Innings' field.
   */
  public java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> getInnings() {
    return Innings;
  }

  /**
   * Sets the value of the 'Innings' field.
   * @param value the value to set.
   */
  public void setInnings(java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> value) {
    this.Innings = value;
  }

  /**
   * Creates a new MatchStats RecordBuilder.
   * @return A new MatchStats RecordBuilder
   */
  public static com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder newBuilder() {
    return new com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder();
  }

  /**
   * Creates a new MatchStats RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new MatchStats RecordBuilder
   */
  public static com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder newBuilder(com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder other) {
    return new com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder(other);
  }

  /**
   * Creates a new MatchStats RecordBuilder by copying an existing MatchStats instance.
   * @param other The existing instance to copy.
   * @return A new MatchStats RecordBuilder
   */
  public static com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder newBuilder(com.personal.gallery.kafkainternals.avro.pojo.MatchStats other) {
    return new com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder(other);
  }

  /**
   * RecordBuilder for MatchStats instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<MatchStats>
    implements org.apache.avro.data.RecordBuilder<MatchStats> {

    private java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> Innings;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.Innings)) {
        this.Innings = data().deepCopy(fields()[0].schema(), other.Innings);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing MatchStats instance
     * @param other The existing instance to copy.
     */
    private Builder(com.personal.gallery.kafkainternals.avro.pojo.MatchStats other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.Innings)) {
        this.Innings = data().deepCopy(fields()[0].schema(), other.Innings);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'Innings' field.
      * @return The value.
      */
    public java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> getInnings() {
      return Innings;
    }

    /**
      * Sets the value of the 'Innings' field.
      * @param value The value of 'Innings'.
      * @return This builder.
      */
    public com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder setInnings(java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning> value) {
      validate(fields()[0], value);
      this.Innings = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'Innings' field has been set.
      * @return True if the 'Innings' field has been set, false otherwise.
      */
    public boolean hasInnings() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'Innings' field.
      * @return This builder.
      */
    public com.personal.gallery.kafkainternals.avro.pojo.MatchStats.Builder clearInnings() {
      Innings = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MatchStats build() {
      try {
        MatchStats record = new MatchStats();
        record.Innings = fieldSetFlags()[0] ? this.Innings : (java.util.List<com.personal.gallery.kafkainternals.avro.pojo.Inning>) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<MatchStats>
    WRITER$ = (org.apache.avro.io.DatumWriter<MatchStats>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<MatchStats>
    READER$ = (org.apache.avro.io.DatumReader<MatchStats>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
