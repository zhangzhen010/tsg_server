package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 逻辑服通知房间服更新玩家信息service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2RoomServerServiceGrpc {

  private LogicServer2RoomServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2RoomServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.PlayerInfo,
      com.google.protobuf.Empty> getReqPlayerUpMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqPlayerUp",
      requestType = com.game.bean.proto.BeanProto.PlayerInfo.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.PlayerInfo,
      com.google.protobuf.Empty> getReqPlayerUpMethod() {
    io.grpc.MethodDescriptor<com.game.bean.proto.BeanProto.PlayerInfo, com.google.protobuf.Empty> getReqPlayerUpMethod;
    if ((getReqPlayerUpMethod = LogicServer2RoomServerServiceGrpc.getReqPlayerUpMethod) == null) {
      synchronized (LogicServer2RoomServerServiceGrpc.class) {
        if ((getReqPlayerUpMethod = LogicServer2RoomServerServiceGrpc.getReqPlayerUpMethod) == null) {
          LogicServer2RoomServerServiceGrpc.getReqPlayerUpMethod = getReqPlayerUpMethod =
              io.grpc.MethodDescriptor.<com.game.bean.proto.BeanProto.PlayerInfo, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqPlayerUp"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.bean.proto.BeanProto.PlayerInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2RoomServerServiceMethodDescriptorSupplier("reqPlayerUp"))
              .build();
        }
      }
    }
    return getReqPlayerUpMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2RoomServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceStub>() {
        @java.lang.Override
        public LogicServer2RoomServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2RoomServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2RoomServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2RoomServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2RoomServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2RoomServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2RoomServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2RoomServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2RoomServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2RoomServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2RoomServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2RoomServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 逻辑服通知房间服更新玩家信息service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqPlayerUp(com.game.bean.proto.BeanProto.PlayerInfo request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqPlayerUpMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogicServer2RoomServerService.
   * <pre>
   * 逻辑服通知房间服更新玩家信息service
   * </pre>
   */
  public static abstract class LogicServer2RoomServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2RoomServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2RoomServerService.
   * <pre>
   * 逻辑服通知房间服更新玩家信息service
   * </pre>
   */
  public static final class LogicServer2RoomServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2RoomServerServiceStub> {
    private LogicServer2RoomServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2RoomServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2RoomServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqPlayerUp(com.game.bean.proto.BeanProto.PlayerInfo request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqPlayerUpMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2RoomServerService.
   * <pre>
   * 逻辑服通知房间服更新玩家信息service
   * </pre>
   */
  public static final class LogicServer2RoomServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2RoomServerServiceBlockingStub> {
    private LogicServer2RoomServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2RoomServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2RoomServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty reqPlayerUp(com.game.bean.proto.BeanProto.PlayerInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqPlayerUpMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2RoomServerService.
   * <pre>
   * 逻辑服通知房间服更新玩家信息service
   * </pre>
   */
  public static final class LogicServer2RoomServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2RoomServerServiceFutureStub> {
    private LogicServer2RoomServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2RoomServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2RoomServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reqPlayerUp(
        com.game.bean.proto.BeanProto.PlayerInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqPlayerUpMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_PLAYER_UP = 0;

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
        case METHODID_REQ_PLAYER_UP:
          serviceImpl.reqPlayerUp((com.game.bean.proto.BeanProto.PlayerInfo) request,
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
          getReqPlayerUpMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.bean.proto.BeanProto.PlayerInfo,
              com.google.protobuf.Empty>(
                service, METHODID_REQ_PLAYER_UP)))
        .build();
  }

  private static abstract class LogicServer2RoomServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2RoomServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2RoomServerService");
    }
  }

  private static final class LogicServer2RoomServerServiceFileDescriptorSupplier
      extends LogicServer2RoomServerServiceBaseDescriptorSupplier {
    LogicServer2RoomServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2RoomServerServiceMethodDescriptorSupplier
      extends LogicServer2RoomServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2RoomServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LogicServer2RoomServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2RoomServerServiceFileDescriptorSupplier())
              .addMethod(getReqPlayerUpMethod())
              .build();
        }
      }
    }
    return result;
  }
}
