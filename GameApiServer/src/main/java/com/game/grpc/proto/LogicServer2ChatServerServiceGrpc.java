package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 游戏逻辑服向聊天服进行正则验证service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2ChatServerServiceGrpc {

  private LogicServer2ChatServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2ChatServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqChatPublicRegex,
      com.game.grpc.proto.GrpcProto.ResChatPublicRegex> getReqChatPublicRegexMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqChatPublicRegex",
      requestType = com.game.grpc.proto.GrpcProto.ReqChatPublicRegex.class,
      responseType = com.game.grpc.proto.GrpcProto.ResChatPublicRegex.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqChatPublicRegex,
      com.game.grpc.proto.GrpcProto.ResChatPublicRegex> getReqChatPublicRegexMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqChatPublicRegex, com.game.grpc.proto.GrpcProto.ResChatPublicRegex> getReqChatPublicRegexMethod;
    if ((getReqChatPublicRegexMethod = LogicServer2ChatServerServiceGrpc.getReqChatPublicRegexMethod) == null) {
      synchronized (LogicServer2ChatServerServiceGrpc.class) {
        if ((getReqChatPublicRegexMethod = LogicServer2ChatServerServiceGrpc.getReqChatPublicRegexMethod) == null) {
          LogicServer2ChatServerServiceGrpc.getReqChatPublicRegexMethod = getReqChatPublicRegexMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ReqChatPublicRegex, com.game.grpc.proto.GrpcProto.ResChatPublicRegex>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqChatPublicRegex"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ReqChatPublicRegex.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ResChatPublicRegex.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2ChatServerServiceMethodDescriptorSupplier("reqChatPublicRegex"))
              .build();
        }
      }
    }
    return getReqChatPublicRegexMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2ChatServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceStub>() {
        @java.lang.Override
        public LogicServer2ChatServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ChatServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2ChatServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2ChatServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2ChatServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ChatServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2ChatServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2ChatServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ChatServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2ChatServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ChatServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2ChatServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 游戏逻辑服向聊天服进行正则验证service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqChatPublicRegex(com.game.grpc.proto.GrpcProto.ReqChatPublicRegex request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResChatPublicRegex> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqChatPublicRegexMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogicServer2ChatServerService.
   * <pre>
   * 游戏逻辑服向聊天服进行正则验证service
   * </pre>
   */
  public static abstract class LogicServer2ChatServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2ChatServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2ChatServerService.
   * <pre>
   * 游戏逻辑服向聊天服进行正则验证service
   * </pre>
   */
  public static final class LogicServer2ChatServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2ChatServerServiceStub> {
    private LogicServer2ChatServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ChatServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ChatServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqChatPublicRegex(com.game.grpc.proto.GrpcProto.ReqChatPublicRegex request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResChatPublicRegex> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqChatPublicRegexMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2ChatServerService.
   * <pre>
   * 游戏逻辑服向聊天服进行正则验证service
   * </pre>
   */
  public static final class LogicServer2ChatServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2ChatServerServiceBlockingStub> {
    private LogicServer2ChatServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ChatServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ChatServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.game.grpc.proto.GrpcProto.ResChatPublicRegex reqChatPublicRegex(com.game.grpc.proto.GrpcProto.ReqChatPublicRegex request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqChatPublicRegexMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2ChatServerService.
   * <pre>
   * 游戏逻辑服向聊天服进行正则验证service
   * </pre>
   */
  public static final class LogicServer2ChatServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2ChatServerServiceFutureStub> {
    private LogicServer2ChatServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ChatServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ChatServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.grpc.proto.GrpcProto.ResChatPublicRegex> reqChatPublicRegex(
        com.game.grpc.proto.GrpcProto.ReqChatPublicRegex request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqChatPublicRegexMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_CHAT_PUBLIC_REGEX = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQ_CHAT_PUBLIC_REGEX:
          serviceImpl.reqChatPublicRegex((com.game.grpc.proto.GrpcProto.ReqChatPublicRegex) request,
              (io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResChatPublicRegex>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getReqChatPublicRegexMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ReqChatPublicRegex,
              com.game.grpc.proto.GrpcProto.ResChatPublicRegex>(
                service, METHODID_REQ_CHAT_PUBLIC_REGEX)))
        .build();
  }

  private static abstract class LogicServer2ChatServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2ChatServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2ChatServerService");
    }
  }

  private static final class LogicServer2ChatServerServiceFileDescriptorSupplier
      extends LogicServer2ChatServerServiceBaseDescriptorSupplier {
    LogicServer2ChatServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2ChatServerServiceMethodDescriptorSupplier
      extends LogicServer2ChatServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2ChatServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LogicServer2ChatServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2ChatServerServiceFileDescriptorSupplier())
              .addMethod(getReqChatPublicRegexMethod())
              .build();
        }
      }
    }
    return result;
  }
}
