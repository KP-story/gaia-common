// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: gaia_message.proto

package com.delight.gaia.base.vo.proto;

/**
 * Protobuf type {@code com.delight.gaia.base.vo.proto.GaiaMessageProto}
 */
public final class GaiaMessageProto extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.delight.gaia.base.vo.proto.GaiaMessageProto)
    GaiaMessageProtoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GaiaMessageProto.newBuilder() to construct.
  private GaiaMessageProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GaiaMessageProto() {
    payload_ = com.google.protobuf.ByteString.EMPTY;
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new GaiaMessageProto();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return GaiaMessage.internal_static_com_delight_gaia_base_vo_proto_GaiaMessageProto_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return GaiaMessage.internal_static_com_delight_gaia_base_vo_proto_GaiaMessageProto_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            GaiaMessageProto.class, Builder.class);
  }

  private int bitField0_;
  public static final int METADATA_FIELD_NUMBER = 1;
  private Metadata metadata_;
  /**
   * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
   * @return Whether the metadata field is set.
   */
  @Override
  public boolean hasMetadata() {
    return metadata_ != null;
  }
  /**
   * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
   * @return The metadata.
   */
  @Override
  public Metadata getMetadata() {
    return metadata_ == null ? Metadata.getDefaultInstance() : metadata_;
  }
  /**
   * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
   */
  @Override
  public MetadataOrBuilder getMetadataOrBuilder() {
    return metadata_ == null ? Metadata.getDefaultInstance() : metadata_;
  }

  public static final int PAYLOAD_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <code>optional bytes payload = 2;</code>
   * @return Whether the payload field is set.
   */
  @Override
  public boolean hasPayload() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional bytes payload = 2;</code>
   * @return The payload.
   */
  @Override
  public com.google.protobuf.ByteString getPayload() {
    return payload_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (metadata_ != null) {
      output.writeMessage(1, getMetadata());
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeBytes(2, payload_);
    }
    getUnknownFields().writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (metadata_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getMetadata());
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, payload_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof GaiaMessageProto)) {
      return super.equals(obj);
    }
    GaiaMessageProto other = (GaiaMessageProto) obj;

    if (hasMetadata() != other.hasMetadata()) return false;
    if (hasMetadata()) {
      if (!getMetadata()
          .equals(other.getMetadata())) return false;
    }
    if (hasPayload() != other.hasPayload()) return false;
    if (hasPayload()) {
      if (!getPayload()
          .equals(other.getPayload())) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasMetadata()) {
      hash = (37 * hash) + METADATA_FIELD_NUMBER;
      hash = (53 * hash) + getMetadata().hashCode();
    }
    if (hasPayload()) {
      hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
      hash = (53 * hash) + getPayload().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static GaiaMessageProto parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GaiaMessageProto parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GaiaMessageProto parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GaiaMessageProto parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GaiaMessageProto parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static GaiaMessageProto parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static GaiaMessageProto parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GaiaMessageProto parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static GaiaMessageProto parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static GaiaMessageProto parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static GaiaMessageProto parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static GaiaMessageProto parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(GaiaMessageProto prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.delight.gaia.base.vo.proto.GaiaMessageProto}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.delight.gaia.base.vo.proto.GaiaMessageProto)
      GaiaMessageProtoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return GaiaMessage.internal_static_com_delight_gaia_base_vo_proto_GaiaMessageProto_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return GaiaMessage.internal_static_com_delight_gaia_base_vo_proto_GaiaMessageProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              GaiaMessageProto.class, Builder.class);
    }

    // Construct using com.delight.gaia.base.vo.proto.GaiaMessageProto.newBuilder()
    private Builder() {

    }

    private Builder(
        BuilderParent parent) {
      super(parent);

    }
    @Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      metadata_ = null;
      if (metadataBuilder_ != null) {
        metadataBuilder_.dispose();
        metadataBuilder_ = null;
      }
      payload_ = com.google.protobuf.ByteString.EMPTY;
      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return GaiaMessage.internal_static_com_delight_gaia_base_vo_proto_GaiaMessageProto_descriptor;
    }

    @Override
    public GaiaMessageProto getDefaultInstanceForType() {
      return GaiaMessageProto.getDefaultInstance();
    }

    @Override
    public GaiaMessageProto build() {
      GaiaMessageProto result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public GaiaMessageProto buildPartial() {
      GaiaMessageProto result = new GaiaMessageProto(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(GaiaMessageProto result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.metadata_ = metadataBuilder_ == null
            ? metadata_
            : metadataBuilder_.build();
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.payload_ = payload_;
        to_bitField0_ |= 0x00000001;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof GaiaMessageProto) {
        return mergeFrom((GaiaMessageProto)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(GaiaMessageProto other) {
      if (other == GaiaMessageProto.getDefaultInstance()) return this;
      if (other.hasMetadata()) {
        mergeMetadata(other.getMetadata());
      }
      if (other.hasPayload()) {
        setPayload(other.getPayload());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              input.readMessage(
                  getMetadataFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              payload_ = input.readBytes();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private Metadata metadata_;
    private com.google.protobuf.SingleFieldBuilderV3<
        Metadata, Metadata.Builder, MetadataOrBuilder> metadataBuilder_;
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     * @return Whether the metadata field is set.
     */
    public boolean hasMetadata() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     * @return The metadata.
     */
    public Metadata getMetadata() {
      if (metadataBuilder_ == null) {
        return metadata_ == null ? Metadata.getDefaultInstance() : metadata_;
      } else {
        return metadataBuilder_.getMessage();
      }
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public Builder setMetadata(Metadata value) {
      if (metadataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        metadata_ = value;
      } else {
        metadataBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public Builder setMetadata(
        Metadata.Builder builderForValue) {
      if (metadataBuilder_ == null) {
        metadata_ = builderForValue.build();
      } else {
        metadataBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public Builder mergeMetadata(Metadata value) {
      if (metadataBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          metadata_ != null &&
          metadata_ != Metadata.getDefaultInstance()) {
          getMetadataBuilder().mergeFrom(value);
        } else {
          metadata_ = value;
        }
      } else {
        metadataBuilder_.mergeFrom(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public Builder clearMetadata() {
      bitField0_ = (bitField0_ & ~0x00000001);
      metadata_ = null;
      if (metadataBuilder_ != null) {
        metadataBuilder_.dispose();
        metadataBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public Metadata.Builder getMetadataBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getMetadataFieldBuilder().getBuilder();
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    public MetadataOrBuilder getMetadataOrBuilder() {
      if (metadataBuilder_ != null) {
        return metadataBuilder_.getMessageOrBuilder();
      } else {
        return metadata_ == null ?
            Metadata.getDefaultInstance() : metadata_;
      }
    }
    /**
     * <code>.com.delight.gaia.base.vo.proto.Metadata metadata = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        Metadata, Metadata.Builder, MetadataOrBuilder>
        getMetadataFieldBuilder() {
      if (metadataBuilder_ == null) {
        metadataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            Metadata, Metadata.Builder, MetadataOrBuilder>(
                getMetadata(),
                getParentForChildren(),
                isClean());
        metadata_ = null;
      }
      return metadataBuilder_;
    }

    private com.google.protobuf.ByteString payload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>optional bytes payload = 2;</code>
     * @return Whether the payload field is set.
     */
    @Override
    public boolean hasPayload() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional bytes payload = 2;</code>
     * @return The payload.
     */
    @Override
    public com.google.protobuf.ByteString getPayload() {
      return payload_;
    }
    /**
     * <code>optional bytes payload = 2;</code>
     * @param value The payload to set.
     * @return This builder for chaining.
     */
    public Builder setPayload(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      payload_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>optional bytes payload = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearPayload() {
      bitField0_ = (bitField0_ & ~0x00000002);
      payload_ = getDefaultInstance().getPayload();
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.delight.gaia.base.vo.proto.GaiaMessageProto)
  }

  // @@protoc_insertion_point(class_scope:com.delight.gaia.base.vo.proto.GaiaMessageProto)
  private static final GaiaMessageProto DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new GaiaMessageProto();
  }

  public static GaiaMessageProto getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GaiaMessageProto>
      PARSER = new com.google.protobuf.AbstractParser<GaiaMessageProto>() {
    @Override
    public GaiaMessageProto parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<GaiaMessageProto> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<GaiaMessageProto> getParserForType() {
    return PARSER;
  }

  @Override
  public GaiaMessageProto getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

