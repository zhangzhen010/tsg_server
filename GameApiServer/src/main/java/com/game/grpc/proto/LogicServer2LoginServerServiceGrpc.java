package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 游戏逻辑服向登录服进行请求rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2LoginServerServiceGrpc {

  private LogicServer2LoginServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2LoginServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo,
      com.google.protobuf.Empty> getReqUpUserInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqUpUserInfo",
      requestType = com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo,
      com.google.protobuf.Empty> getReqUpUserInfoMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo, com.google.protobuf.Empty> getReqUpUserInfoMethod;
    if ((getReqUpUserInfoMethod = LogicServer2LoginServerServiceGrpc.getReqUpUserInfoMethod) == null) {
      synchronized (LogicServer2LoginServerServiceGrpc.class) {
        if ((getReqUpUserInfoMethod = LogicServer2LoginServerServiceGrpc.getReqUpUserInfoMethod) == null) {
          LogicServer2LoginServerServiceGrpc.getReqUpUserInfoMethod = getReqUpUserInfoMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqUpUserInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2LoginServerServiceMethodDescriptorSupplier("reqUpUserInfo"))
              .build();
        }
      }
    }
    return getReqUpUserInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2LoginServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceStub>() {
        @java.lang.Override
        public LogicServer2LoginServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2LoginServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2LoginServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2LoginServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2LoginServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2LoginServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2LoginServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2LoginServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2LoginServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2LoginServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2LoginServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2LoginServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 游戏逻辑服向登录服进行请求rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqUpUserInfo(com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqUpUserInfoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogicServer2LoginServerService.
   * <pre>
   * 游戏逻辑服向登录服进行请求rpc service
   * </pre>
   */
  public static abstract class LogicServer2LoginServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2LoginServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2LoginServerService.
   * <pre>
   * 游戏逻辑服向登录服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2LoginServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2LoginServerServiceStub> {
    private LogicServer2LoginServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2LoginServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2LoginServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqUpUserInfo(com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqUpUserInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2LoginServerService.
   * <pre>
   * 游戏逻辑服向登录服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2LoginServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2LoginServerServiceBlockingStub> {
    private LogicServer2LoginServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2LoginServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2LoginServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty reqUpUserInfo(com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqUpUserInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2LoginServerService.
   * <pre>
   * 游戏逻辑服向登录服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2LoginServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2LoginServerServiceFutureStub> {
    private LogicServer2LoginServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2LoginServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2LoginServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reqUpUserInfo(
        com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqUpUserInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_UP_USER_INFO = 0;

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
        case METHODID_REQ_UP_USER_INFO:
          serviceImpl.reqUpUserInfo((com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getReqUpUserInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ReqPublicLoginUpUserInfo,
              com.google.protobuf.Empty>(
                service, METHODID_REQ_UP_USER_INFO)))
        .build();
  }

  private static abstract class LogicServer2LoginServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2LoginServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2LoginServerService");
    }
  }

  private static final class LogicServer2LoginServerServiceFileDescriptorSupplier
      extends LogicServer2LoginServerServiceBaseDescriptorSupplier {
    LogicServer2LoginServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2LoginServerServiceMethodDescriptorSupplier
      extends LogicServer2LoginServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2LoginServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LogicServer2LoginServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2LoginServerServiceFileDescriptorSupplier())
              .addMethod(getReqUpUserInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
