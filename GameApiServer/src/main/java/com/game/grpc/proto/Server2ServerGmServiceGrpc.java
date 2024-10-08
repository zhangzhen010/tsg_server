package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerGmServiceGrpc {

  private Server2ServerGmServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerGmService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand,
      com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqGrpcGm",
      requestType = com.game.grpc.proto.GrpcProto.GMReqCommand.class,
      responseType = com.game.grpc.proto.GrpcProto.GMResCommand.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand,
      com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.GMReqCommand, com.game.grpc.proto.GrpcProto.GMResCommand> getReqGrpcGmMethod;
    if ((getReqGrpcGmMethod = Server2ServerGmServiceGrpc.getReqGrpcGmMethod) == null) {
      synchronized (Server2ServerGmServiceGrpc.class) {
        if ((getReqGrpcGmMethod = Server2ServerGmServiceGrpc.getReqGrpcGmMethod) == null) {
          Server2ServerGmServiceGrpc.getReqGrpcGmMethod = getReqGrpcGmMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.GMReqCommand, com.game.grpc.proto.GrpcProto.GMResCommand>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqGrpcGm"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.GMReqCommand.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.GMResCommand.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerGmServiceMethodDescriptorSupplier("reqGrpcGm"))
              .build();
        }
      }
    }
    return getReqGrpcGmMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerGmServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceStub>() {
        @java.lang.Override
        public Server2ServerGmServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmServiceStub(channel, callOptions);
        }
      };
    return Server2ServerGmServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerGmServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerGmServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerGmServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerGmServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerGmServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerGmServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerGmServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerGmServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqGrpcGm(com.game.grpc.proto.GrpcProto.GMReqCommand request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.GMResCommand> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqGrpcGmMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerGmService.
   * <pre>
   * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
   * </pre>
   */
  public static abstract class Server2ServerGmServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerGmServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerGmService.
   * <pre>
   * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
   * </pre>
   */
  public static final class Server2ServerGmServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerGmServiceStub> {
    private Server2ServerGmServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqGrpcGm(com.game.grpc.proto.GrpcProto.GMReqCommand request,
        io.grpc.stub.StreamObserver<com.game.grpc.proto.GrpcProto.GMResCommand> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqGrpcGmMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerGmService.
   * <pre>
   * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
   * </pre>
   */
  public static final class Server2ServerGmServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerGmServiceBlockingStub> {
    private Server2ServerGmServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.game.grpc.proto.GrpcProto.GMResCommand reqGrpcGm(com.game.grpc.proto.GrpcProto.GMReqCommand request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqGrpcGmMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerGmService.
   * <pre>
   * 后台-向其他服务器发送Gm命令（不同的服务器自己实现，因为有些服务器没有GmManager类）rpc service
   * </pre>
   */
  public static final class Server2ServerGmServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerGmServiceFutureStub> {
    private Server2ServerGmServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerGmServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerGmServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.game.grpc.proto.GrpcProto.GMResCommand> reqGrpcGm(
        com.game.grpc.proto.GrpcProto.GMReqCommand request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqGrpcGmMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_GRPC_GM = 0;

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
        case METHODID_REQ_GRPC_GM:
          serviceImpl.reqGrpcGm((com.game.grpc.proto.GrpcProto.GMReqCommand) request,
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
          getReqGrpcGmMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.GMReqCommand,
              com.game.grpc.proto.GrpcProto.GMResCommand>(
                service, METHODID_REQ_GRPC_GM)))
        .build();
  }

  private static abstract class Server2ServerGmServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerGmServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerGmService");
    }
  }

  private static final class Server2ServerGmServiceFileDescriptorSupplier
      extends Server2ServerGmServiceBaseDescriptorSupplier {
    Server2ServerGmServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerGmServiceMethodDescriptorSupplier
      extends Server2ServerGmServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerGmServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (Server2ServerGmServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerGmServiceFileDescriptorSupplier())
              .addMethod(getReqGrpcGmMethod())
              .build();
        }
      }
    }
    return result;
  }
}
