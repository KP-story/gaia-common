// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: jwk.proto

package com.delight.gaia.auth.api.dto.response;

public interface JwkOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.delight.gaia.auth.api.dto.response.Jwk)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string kty = 1;</code>
   * @return The kty.
   */
  java.lang.String getKty();
  /**
   * <code>string kty = 1;</code>
   * @return The bytes for kty.
   */
  com.google.protobuf.ByteString
      getKtyBytes();

  /**
   * <code>string kid = 2;</code>
   * @return The kid.
   */
  java.lang.String getKid();
  /**
   * <code>string kid = 2;</code>
   * @return The bytes for kid.
   */
  com.google.protobuf.ByteString
      getKidBytes();

  /**
   * <code>optional string use = 3;</code>
   * @return Whether the use field is set.
   */
  boolean hasUse();
  /**
   * <code>optional string use = 3;</code>
   * @return The use.
   */
  java.lang.String getUse();
  /**
   * <code>optional string use = 3;</code>
   * @return The bytes for use.
   */
  com.google.protobuf.ByteString
      getUseBytes();

  /**
   * <code>optional string h = 4;</code>
   * @return Whether the h field is set.
   */
  boolean hasH();
  /**
   * <code>optional string h = 4;</code>
   * @return The h.
   */
  java.lang.String getH();
  /**
   * <code>optional string h = 4;</code>
   * @return The bytes for h.
   */
  com.google.protobuf.ByteString
      getHBytes();

  /**
   * <code>optional string n = 5;</code>
   * @return Whether the n field is set.
   */
  boolean hasN();
  /**
   * <code>optional string n = 5;</code>
   * @return The n.
   */
  java.lang.String getN();
  /**
   * <code>optional string n = 5;</code>
   * @return The bytes for n.
   */
  com.google.protobuf.ByteString
      getNBytes();

  /**
   * <code>optional string e = 6;</code>
   * @return Whether the e field is set.
   */
  boolean hasE();
  /**
   * <code>optional string e = 6;</code>
   * @return The e.
   */
  java.lang.String getE();
  /**
   * <code>optional string e = 6;</code>
   * @return The bytes for e.
   */
  com.google.protobuf.ByteString
      getEBytes();
}
