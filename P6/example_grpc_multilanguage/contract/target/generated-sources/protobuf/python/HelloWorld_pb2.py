# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: HelloWorld.proto
"""Generated protocol buffer code."""
from google.protobuf.internal import builder as _builder
from google.protobuf import descriptor as _descriptor
from google.protobuf import descriptor_pool as _descriptor_pool
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor_pool.Default().AddSerializedFile(b'\n\x10HelloWorld.proto\x12\x0fpt.tecnico.grpc\"-\n\x0cHelloRequest\x12\x0c\n\x04name\x18\x01 \x01(\t\x12\x0f\n\x07hobbies\x18\x02 \x03(\t\"!\n\rHelloResponse\x12\x10\n\x08greeting\x18\x01 \x01(\t2^\n\x11HelloWorldService\x12I\n\x08greeting\x12\x1d.pt.tecnico.grpc.HelloRequest\x1a\x1e.pt.tecnico.grpc.HelloResponseb\x06proto3')

_builder.BuildMessageAndEnumDescriptors(DESCRIPTOR, globals())
_builder.BuildTopDescriptorsAndMessages(DESCRIPTOR, 'HelloWorld_pb2', globals())
if _descriptor._USE_C_DESCRIPTORS == False:

  DESCRIPTOR._options = None
  _HELLOREQUEST._serialized_start=37
  _HELLOREQUEST._serialized_end=82
  _HELLORESPONSE._serialized_start=84
  _HELLORESPONSE._serialized_end=117
  _HELLOWORLDSERVICE._serialized_start=119
  _HELLOWORLDSERVICE._serialized_end=213
# @@protoc_insertion_point(module_scope)