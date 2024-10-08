package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 游戏逻辑服向战斗服进行请求战斗rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2FightServerServiceGrpc {

  private LogicServer2FightServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2FightServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicFightFight,
      com.game.grpc.proto.GrpcProto.ResPublicFightFight> getReqFightMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqFight",
      requestType = com.game.grpc.proto.GrpcProto.ReqPublicFightFight.class,
      responseType = com.game.grpc.proto.GrpcProto.ResPublicFightFight.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicFightFight,
      com.game.grpc.proto.GrpcProto.ResPublicFightFight> getReqFightMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ReqPublicFightFight, com.game.grpc.proto.GrpcProto.ResPublicFightFight> getReqFightMethod;
    if ((getReqFightMethod = LogicServer2FightServerServiceGrpc.getReqFightMethod) == null) {
      synchronized (LogicServer2FightServerServiceGrpc.class) {
        if ((getReqFightMethod = LogicServer2FightServerServiceGrpc.getReqFightMethod) == null) {
          LogicServer2FightServerServiceGrpc.getReqFightMethod = getReqFightMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ReqPublicFightFight, com.game.grpc.proto.GrpcProto.ResPublicFightFight>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqFight"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ReqPublicFightFight.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ResPublicFightFight.getDefaultInstance()))
              .setSchemaDescriptor(new LogicServer2FightServerServiceMethodDescriptorSupplier("reqFight"))
              .build();
        }
      }
    }
    return getReqFightMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2FightServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceStub>() {
        @java.lang.Override
        public LogicServer2FightServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2FightServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2FightServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2FightServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2FightServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2FightServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2FightServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2FightServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2FightServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2FightServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2FightServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2FightServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 游戏逻辑服向战斗服进行请求战斗rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqFight(com.game.grpc.proto.GrpcProto.ReqPublicFightFight request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicFightFight> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqFightMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service LogicServer2FightServerService.
   * <pre>
   * 游戏逻辑服向战斗服进行请求战斗rpc service
   * </pre>
   */
  public static abstract class LogicServer2FightServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2FightServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2FightServerService.
   * <pre>
   * 游戏逻辑服向战斗服进行请求战斗rpc service
   * </pre>
   */
  public static final class LogicServer2FightServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2FightServerServiceStub> {
    private LogicServer2FightServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2FightServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2FightServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqFight(com.game.grpc.proto.GrpcProto.ReqPublicFightFight request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicFightFight> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqFightMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2FightServerService.
   * <pre>
   * 游戏逻辑服向战斗服进行请求战斗rpc service
   * </pre>
   */
  public static final class LogicServer2FightServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2FightServerServiceBlockingStub> {
    private LogicServer2FightServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2FightServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2FightServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.game.grpc.proto.GrpcProto.ResPublicFightFight reqFight(com.game.grpc.proto.GrpcProto.ReqPublicFightFight request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqFightMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2FightServerService.
   * <pre>
   * 游戏逻辑服向战斗服进行请求战斗rpc service
   * </pre>
   */
  public static final class LogicServer2FightServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2FightServerServiceFutureStub> {
    private LogicServer2FightServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2FightServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2FightServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.grpc.proto.GrpcProto.ResPublicFightFight> reqFight(
        com.game.grpc.proto.GrpcProto.ReqPublicFightFight request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqFightMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_FIGHT = 0;

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
        case METHODID_REQ_FIGHT:
          serviceImpl.reqFight((com.game.grpc.proto.GrpcProto.ReqPublicFightFight) request,
              (io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.ResPublicFightFight>) responseObserver);
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
          getReqFightMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ReqPublicFightFight,
              com.game.grpc.proto.GrpcProto.ResPublicFightFight>(
                service, METHODID_REQ_FIGHT)))
        .build();
  }

  private static abstract class LogicServer2FightServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2FightServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2FightServerService");
    }
  }

  private static final class LogicServer2FightServerServiceFileDescriptorSupplier
      extends LogicServer2FightServerServiceBaseDescriptorSupplier {
    LogicServer2FightServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2FightServerServiceMethodDescriptorSupplier
      extends LogicServer2FightServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2FightServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LogicServer2FightServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2FightServerServiceFileDescriptorSupplier())
              .addMethod(getReqFightMethod())
              .build();
        }
      }
    }
    return result;
  }
}
