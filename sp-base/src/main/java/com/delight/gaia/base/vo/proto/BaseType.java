// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: base_type.proto

package com.delight.gaia.base.vo.proto;

public final class BaseType {
  private BaseType() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_delight_chat_model_I64_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_delight_chat_model_I64_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_delight_chat_model_Bool_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_delight_chat_model_Bool_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\017base_type.proto\022\026com.delight.chat.mode" +
      "l\"#\n\003I64\022\022\n\005value\030\001 \001(\004H\000\210\001\001B\010\n\006_value\"$" +
      "\n\004Bool\022\022\n\005value\030\001 \001(\010H\000\210\001\001B\010\n\006_valueB\004H\001" +
      "P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_delight_chat_model_I64_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_delight_chat_model_I64_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_delight_chat_model_I64_descriptor,
        new java.lang.String[] { "Value", "Value", });
    internal_static_com_delight_chat_model_Bool_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_delight_chat_model_Bool_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_delight_chat_model_Bool_descriptor,
        new java.lang.String[] { "Value", "Value", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
