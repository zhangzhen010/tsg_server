package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerGmReloadServiceGrpc {

  private Server2ServerGmReloadServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerGmReloadService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand,
      com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmReloadMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqGrpcGmReload",
      requestType = com.game.grpc.proto.GrpcProto.GMReqCommand.class,
      responseType = com.game.grpc.proto.GrpcProto.GMResCommand.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand,
      com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmReloadMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand, com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmReloadMethod;
    if ((getReqGrpcGmReloadMethod = Server2ServerGmReloadServiceGrpc.getReqGrpcGmReloadMethod) == null) {
      synchronized (Server2ServerGmReloadServiceGrpc.class) {
        if ((getReqGrpcGmReloadMethod = Server2ServerGmReloadServiceGrpc.getReqGrpcGmReloadMethod) == null) {
          Server2ServerGmReloadServiceGrpc.getReqGrpcGmReloadMethod = getReqGrpcGmReloadMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.GMReqCommand, com.game.grpc.proto.GrpcProto.GMResCommand>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqGrpcGmReload"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.GMReqCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.GMResCommand.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerGmReloadServiceMethodDescriptorSupplier("reqGrpcGmReload"))
              .build();
        }
      }
    }
    return getReqGrpcGmReloadMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerGmReloadServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceStub>() {
        @java.lang.Override
        public Server2ServerGmReloadServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmReloadServiceStub(channel, callOptions);
        }
      };
    return Server2ServerGmReloadServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerGmReloadServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerGmReloadServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmReloadServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerGmReloadServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerGmReloadServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmReloadServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerGmReloadServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmReloadServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerGmReloadServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqGrpcGmReload(com.game.grpc.proto.GrpcProto.GMReqCommand request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.GMResCommand> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqGrpcGmReloadMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerGmReloadService.
   * <pre>
   * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
   * </pre>
   */
  public static abstract class Server2ServerGmReloadServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerGmReloadServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerGmReloadService.
   * <pre>
   * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
   * </pre>
   */
  public static final class Server2ServerGmReloadServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerGmReloadServiceStub> {
    private Server2ServerGmReloadServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmReloadServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmReloadServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqGrpcGmReload(com.game.grpc.proto.GrpcProto.GMReqCommand request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.GMResCommand> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqGrpcGmReloadMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerGmReloadService.
   * <pre>
   * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
   * </pre>
   */
  public static final class Server2ServerGmReloadServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerGmReloadServiceBlockingStub> {
    private Server2ServerGmReloadServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmReloadServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmReloadServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.game.grpc.proto.GrpcProto.GMResCommand reqGrpcGmReload(com.game.grpc.proto.GrpcProto.GMReqCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqGrpcGmReloadMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerGmReloadService.
   * <pre>
   * gm重新加载配置表消息service（这个从gm消息Server2ServerGmService独立出来是因为所有服务器都需要，统一放入API项目）
   * </pre>
   */
  public static final class Server2ServerGmReloadServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerGmReloadServiceFutureStub> {
    private Server2ServerGmReloadServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmReloadServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmReloadServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.grpc.proto.GrpcProto.GMResCommand> reqGrpcGmReload(
        com.game.grpc.proto.GrpcProto.GMReqCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqGrpcGmReloadMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_GRPC_GM_RELOAD = 0;

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
        case METHODID_REQ_GRPC_GM_RELOAD:
          serviceImpl.reqGrpcGmReload((com.game.grpc.proto.GrpcProto.GMReqCommand) request,
              (io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.GMResCommand>) responseObserver);
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
          getReqGrpcGmReloadMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.GMReqCommand,
              com.game.grpc.proto.GrpcProto.GMResCommand>(
                service, METHODID_REQ_GRPC_GM_RELOAD)))
        .build();
  }

  private static abstract class Server2ServerGmReloadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerGmReloadServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerGmReloadService");
    }
  }

  private static final class Server2ServerGmReloadServiceFileDescriptorSupplier
      extends Server2ServerGmReloadServiceBaseDescriptorSupplier {
    Server2ServerGmReloadServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerGmReloadServiceMethodDescriptorSupplier
      extends Server2ServerGmReloadServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerGmReloadServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (Server2ServerGmReloadServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerGmReloadServiceFileDescriptorSupplier())
              .addMethod(getReqGrpcGmReloadMethod())
              .build();
        }
      }
    }
    return result;
  }
}
