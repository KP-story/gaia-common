// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: jwk.proto

package com.delight.gaia.auth.api.dto.response;

/**
 * Protobuf type {@code com.delight.gaia.auth.api.dto.response.Jwk}
 */
public final class Jwk extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:com.delight.gaia.auth.api.dto.response.Jwk)
    JwkOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Jwk.newBuilder() to construct.
  private Jwk(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Jwk() {
    kty_ = "";
    kid_ = "";
    use_ = "";
    h_ = "";
    n_ = "";
    e_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new Jwk();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.delight.gaia.auth.api.dto.response.JwkOuterClass.internal_static_com_delight_gaia_auth_api_dto_response_Jwk_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.delight.gaia.auth.api.dto.response.JwkOuterClass.internal_static_com_delight_gaia_auth_api_dto_response_Jwk_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.delight.gaia.auth.api.dto.response.Jwk.class, com.delight.gaia.auth.api.dto.response.Jwk.Builder.class);
  }

  private int bitField0_;
  public static final int KTY_FIELD_NUMBER = 1;
  @SuppressWarnings("serial")
  private volatile java.lang.Object kty_ = "";
  /**
   * <code>string kty = 1;</code>
   * @return The kty.
   */
  @java.lang.Override
  public java.lang.String getKty() {
    java.lang.Object ref = kty_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      kty_ = s;
      return s;
    }
  }
  /**
   * <code>string kty = 1;</code>
   * @return The bytes for kty.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getKtyBytes() {
    java.lang.Object ref = kty_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      kty_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int KID_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object kid_ = "";
  /**
   * <code>string kid = 2;</code>
   * @return The kid.
   */
  @java.lang.Override
  public java.lang.String getKid() {
    java.lang.Object ref = kid_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      kid_ = s;
      return s;
    }
  }
  /**
   * <code>string kid = 2;</code>
   * @return The bytes for kid.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getKidBytes() {
    java.lang.Object ref = kid_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      kid_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int USE_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object use_ = "";
  /**
   * <code>optional string use = 3;</code>
   * @return Whether the use field is set.
   */
  @java.lang.Override
  public boolean hasUse() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional string use = 3;</code>
   * @return The use.
   */
  @java.lang.Override
  public java.lang.String getUse() {
    java.lang.Object ref = use_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      use_ = s;
      return s;
    }
  }
  /**
   * <code>optional string use = 3;</code>
   * @return The bytes for use.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getUseBytes() {
    java.lang.Object ref = use_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      use_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int H_FIELD_NUMBER = 4;
  @SuppressWarnings("serial")
  private volatile java.lang.Object h_ = "";
  /**
   * <code>optional string h = 4;</code>
   * @return Whether the h field is set.
   */
  @java.lang.Override
  public boolean hasH() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>optional string h = 4;</code>
   * @return The h.
   */
  @java.lang.Override
  public java.lang.String getH() {
    java.lang.Object ref = h_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      h_ = s;
      return s;
    }
  }
  /**
   * <code>optional string h = 4;</code>
   * @return The bytes for h.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getHBytes() {
    java.lang.Object ref = h_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      h_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int N_FIELD_NUMBER = 5;
  @SuppressWarnings("serial")
  private volatile java.lang.Object n_ = "";
  /**
   * <code>optional string n = 5;</code>
   * @return Whether the n field is set.
   */
  @java.lang.Override
  public boolean hasN() {
    return ((bitField0_ & 0x00000004) != 0);
  }
  /**
   * <code>optional string n = 5;</code>
   * @return The n.
   */
  @java.lang.Override
  public java.lang.String getN() {
    java.lang.Object ref = n_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      n_ = s;
      return s;
    }
  }
  /**
   * <code>optional string n = 5;</code>
   * @return The bytes for n.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getNBytes() {
    java.lang.Object ref = n_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      n_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int E_FIELD_NUMBER = 6;
  @SuppressWarnings("serial")
  private volatile java.lang.Object e_ = "";
  /**
   * <code>optional string e = 6;</code>
   * @return Whether the e field is set.
   */
  @java.lang.Override
  public boolean hasE() {
    return ((bitField0_ & 0x00000008) != 0);
  }
  /**
   * <code>optional string e = 6;</code>
   * @return The e.
   */
  @java.lang.Override
  public java.lang.String getE() {
    java.lang.Object ref = e_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      e_ = s;
      return s;
    }
  }
  /**
   * <code>optional string e = 6;</code>
   * @return The bytes for e.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getEBytes() {
    java.lang.Object ref = e_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      e_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(kty_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, kty_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(kid_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, kid_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, use_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, h_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, n_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, e_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(kty_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, kty_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(kid_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, kid_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, use_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, h_);
    }
    if (((bitField0_ & 0x00000004) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, n_);
    }
    if (((bitField0_ & 0x00000008) != 0)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, e_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.delight.gaia.auth.api.dto.response.Jwk)) {
      return super.equals(obj);
    }
    com.delight.gaia.auth.api.dto.response.Jwk other = (com.delight.gaia.auth.api.dto.response.Jwk) obj;

    if (!getKty()
        .equals(other.getKty())) return false;
    if (!getKid()
        .equals(other.getKid())) return false;
    if (hasUse() != other.hasUse()) return false;
    if (hasUse()) {
      if (!getUse()
          .equals(other.getUse())) return false;
    }
    if (hasH() != other.hasH()) return false;
    if (hasH()) {
      if (!getH()
          .equals(other.getH())) return false;
    }
    if (hasN() != other.hasN()) return false;
    if (hasN()) {
      if (!getN()
          .equals(other.getN())) return false;
    }
    if (hasE() != other.hasE()) return false;
    if (hasE()) {
      if (!getE()
          .equals(other.getE())) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + KTY_FIELD_NUMBER;
    hash = (53 * hash) + getKty().hashCode();
    hash = (37 * hash) + KID_FIELD_NUMBER;
    hash = (53 * hash) + getKid().hashCode();
    if (hasUse()) {
      hash = (37 * hash) + USE_FIELD_NUMBER;
      hash = (53 * hash) + getUse().hashCode();
    }
    if (hasH()) {
      hash = (37 * hash) + H_FIELD_NUMBER;
      hash = (53 * hash) + getH().hashCode();
    }
    if (hasN()) {
      hash = (37 * hash) + N_FIELD_NUMBER;
      hash = (53 * hash) + getN().hashCode();
    }
    if (hasE()) {
      hash = (37 * hash) + E_FIELD_NUMBER;
      hash = (53 * hash) + getE().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.delight.gaia.auth.api.dto.response.Jwk parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.delight.gaia.auth.api.dto.response.Jwk prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code com.delight.gaia.auth.api.dto.response.Jwk}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:com.delight.gaia.auth.api.dto.response.Jwk)
      com.delight.gaia.auth.api.dto.response.JwkOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.delight.gaia.auth.api.dto.response.JwkOuterClass.internal_static_com_delight_gaia_auth_api_dto_response_Jwk_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.delight.gaia.auth.api.dto.response.JwkOuterClass.internal_static_com_delight_gaia_auth_api_dto_response_Jwk_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.delight.gaia.auth.api.dto.response.Jwk.class, com.delight.gaia.auth.api.dto.response.Jwk.Builder.class);
    }

    // Construct using com.delight.gaia.auth.api.dto.response.Jwk.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      kty_ = "";
      kid_ = "";
      use_ = "";
      h_ = "";
      n_ = "";
      e_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.delight.gaia.auth.api.dto.response.JwkOuterClass.internal_static_com_delight_gaia_auth_api_dto_response_Jwk_descriptor;
    }

    @java.lang.Override
    public com.delight.gaia.auth.api.dto.response.Jwk getDefaultInstanceForType() {
      return com.delight.gaia.auth.api.dto.response.Jwk.getDefaultInstance();
    }

    @java.lang.Override
    public com.delight.gaia.auth.api.dto.response.Jwk build() {
      com.delight.gaia.auth.api.dto.response.Jwk result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.delight.gaia.auth.api.dto.response.Jwk buildPartial() {
      com.delight.gaia.auth.api.dto.response.Jwk result = new com.delight.gaia.auth.api.dto.response.Jwk(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(com.delight.gaia.auth.api.dto.response.Jwk result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.kty_ = kty_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.kid_ = kid_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.use_ = use_;
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.h_ = h_;
        to_bitField0_ |= 0x00000002;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.n_ = n_;
        to_bitField0_ |= 0x00000004;
      }
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.e_ = e_;
        to_bitField0_ |= 0x00000008;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.delight.gaia.auth.api.dto.response.Jwk) {
        return mergeFrom((com.delight.gaia.auth.api.dto.response.Jwk)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.delight.gaia.auth.api.dto.response.Jwk other) {
      if (other == com.delight.gaia.auth.api.dto.response.Jwk.getDefaultInstance()) return this;
      if (!other.getKty().isEmpty()) {
        kty_ = other.kty_;
        bitField0_ |= 0x00000001;
        onChanged();
      }
      if (!other.getKid().isEmpty()) {
        kid_ = other.kid_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (other.hasUse()) {
        use_ = other.use_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      if (other.hasH()) {
        h_ = other.h_;
        bitField0_ |= 0x00000008;
        onChanged();
      }
      if (other.hasN()) {
        n_ = other.n_;
        bitField0_ |= 0x00000010;
        onChanged();
      }
      if (other.hasE()) {
        e_ = other.e_;
        bitField0_ |= 0x00000020;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
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
              kty_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              kid_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            case 26: {
              use_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            case 34: {
              h_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000008;
              break;
            } // case 34
            case 42: {
              n_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000010;
              break;
            } // case 42
            case 50: {
              e_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000020;
              break;
            } // case 50
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

    private java.lang.Object kty_ = "";
    /**
     * <code>string kty = 1;</code>
     * @return The kty.
     */
    public java.lang.String getKty() {
      java.lang.Object ref = kty_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        kty_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string kty = 1;</code>
     * @return The bytes for kty.
     */
    public com.google.protobuf.ByteString
        getKtyBytes() {
      java.lang.Object ref = kty_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        kty_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string kty = 1;</code>
     * @param value The kty to set.
     * @return This builder for chaining.
     */
    public Builder setKty(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      kty_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>string kty = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearKty() {
      kty_ = getDefaultInstance().getKty();
      bitField0_ = (bitField0_ & ~0x00000001);
      onChanged();
      return this;
    }
    /**
     * <code>string kty = 1;</code>
     * @param value The bytes for kty to set.
     * @return This builder for chaining.
     */
    public Builder setKtyBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      kty_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }

    private java.lang.Object kid_ = "";
    /**
     * <code>string kid = 2;</code>
     * @return The kid.
     */
    public java.lang.String getKid() {
      java.lang.Object ref = kid_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        kid_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string kid = 2;</code>
     * @return The bytes for kid.
     */
    public com.google.protobuf.ByteString
        getKidBytes() {
      java.lang.Object ref = kid_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        kid_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string kid = 2;</code>
     * @param value The kid to set.
     * @return This builder for chaining.
     */
    public Builder setKid(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      kid_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string kid = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearKid() {
      kid_ = getDefaultInstance().getKid();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string kid = 2;</code>
     * @param value The bytes for kid to set.
     * @return This builder for chaining.
     */
    public Builder setKidBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      kid_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private java.lang.Object use_ = "";
    /**
     * <code>optional string use = 3;</code>
     * @return Whether the use field is set.
     */
    public boolean hasUse() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>optional string use = 3;</code>
     * @return The use.
     */
    public java.lang.String getUse() {
      java.lang.Object ref = use_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        use_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string use = 3;</code>
     * @return The bytes for use.
     */
    public com.google.protobuf.ByteString
        getUseBytes() {
      java.lang.Object ref = use_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        use_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string use = 3;</code>
     * @param value The use to set.
     * @return This builder for chaining.
     */
    public Builder setUse(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      use_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>optional string use = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearUse() {
      use_ = getDefaultInstance().getUse();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>optional string use = 3;</code>
     * @param value The bytes for use to set.
     * @return This builder for chaining.
     */
    public Builder setUseBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      use_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    private java.lang.Object h_ = "";
    /**
     * <code>optional string h = 4;</code>
     * @return Whether the h field is set.
     */
    public boolean hasH() {
      return ((bitField0_ & 0x00000008) != 0);
    }
    /**
     * <code>optional string h = 4;</code>
     * @return The h.
     */
    public java.lang.String getH() {
      java.lang.Object ref = h_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        h_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string h = 4;</code>
     * @return The bytes for h.
     */
    public com.google.protobuf.ByteString
        getHBytes() {
      java.lang.Object ref = h_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        h_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string h = 4;</code>
     * @param value The h to set.
     * @return This builder for chaining.
     */
    public Builder setH(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      h_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>optional string h = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearH() {
      h_ = getDefaultInstance().getH();
      bitField0_ = (bitField0_ & ~0x00000008);
      onChanged();
      return this;
    }
    /**
     * <code>optional string h = 4;</code>
     * @param value The bytes for h to set.
     * @return This builder for chaining.
     */
    public Builder setHBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      h_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }

    private java.lang.Object n_ = "";
    /**
     * <code>optional string n = 5;</code>
     * @return Whether the n field is set.
     */
    public boolean hasN() {
      return ((bitField0_ & 0x00000010) != 0);
    }
    /**
     * <code>optional string n = 5;</code>
     * @return The n.
     */
    public java.lang.String getN() {
      java.lang.Object ref = n_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        n_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string n = 5;</code>
     * @return The bytes for n.
     */
    public com.google.protobuf.ByteString
        getNBytes() {
      java.lang.Object ref = n_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        n_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string n = 5;</code>
     * @param value The n to set.
     * @return This builder for chaining.
     */
    public Builder setN(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      n_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <code>optional string n = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearN() {
      n_ = getDefaultInstance().getN();
      bitField0_ = (bitField0_ & ~0x00000010);
      onChanged();
      return this;
    }
    /**
     * <code>optional string n = 5;</code>
     * @param value The bytes for n to set.
     * @return This builder for chaining.
     */
    public Builder setNBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      n_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }

    private java.lang.Object e_ = "";
    /**
     * <code>optional string e = 6;</code>
     * @return Whether the e field is set.
     */
    public boolean hasE() {
      return ((bitField0_ & 0x00000020) != 0);
    }
    /**
     * <code>optional string e = 6;</code>
     * @return The e.
     */
    public java.lang.String getE() {
      java.lang.Object ref = e_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        e_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string e = 6;</code>
     * @return The bytes for e.
     */
    public com.google.protobuf.ByteString
        getEBytes() {
      java.lang.Object ref = e_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        e_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string e = 6;</code>
     * @param value The e to set.
     * @return This builder for chaining.
     */
    public Builder setE(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      e_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    /**
     * <code>optional string e = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearE() {
      e_ = getDefaultInstance().getE();
      bitField0_ = (bitField0_ & ~0x00000020);
      onChanged();
      return this;
    }
    /**
     * <code>optional string e = 6;</code>
     * @param value The bytes for e to set.
     * @return This builder for chaining.
     */
    public Builder setEBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      e_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:com.delight.gaia.auth.api.dto.response.Jwk)
  }

  // @@protoc_insertion_point(class_scope:com.delight.gaia.auth.api.dto.response.Jwk)
  private static final com.delight.gaia.auth.api.dto.response.Jwk DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.delight.gaia.auth.api.dto.response.Jwk();
  }

  public static com.delight.gaia.auth.api.dto.response.Jwk getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Jwk>
      PARSER = new com.google.protobuf.AbstractParser<Jwk>() {
    @java.lang.Override
    public Jwk parsePartialFrom(
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

  public static com.google.protobuf.Parser<Jwk> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<Jwk> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.delight.gaia.auth.api.dto.response.Jwk getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
