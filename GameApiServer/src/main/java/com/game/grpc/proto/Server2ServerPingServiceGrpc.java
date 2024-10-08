package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 判断服务器之间rpc连接状态ping rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerPingServiceGrpc {

  private Server2ServerPingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerPingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getReqPingMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqPing",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getReqPingMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.google.protobuf.Empty> getReqPingMethod;
    if ((getReqPingMethod = Server2ServerPingServiceGrpc.getReqPingMethod) == null) {
      synchronized (Server2ServerPingServiceGrpc.class) {
        if ((getReqPingMethod = Server2ServerPingServiceGrpc.getReqPingMethod) == null) {
          Server2ServerPingServiceGrpc.getReqPingMethod = getReqPingMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqPing"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerPingServiceMethodDescriptorSupplier("reqPing"))
              .build();
        }
      }
    }
    return getReqPingMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerPingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceStub>() {
        @java.lang.Override
        public Server2ServerPingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerPingServiceStub(channel, callOptions);
        }
      };
    return Server2ServerPingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerPingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerPingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerPingServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerPingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerPingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerPingServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerPingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerPingServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerPingServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 判断服务器之间rpc连接状态ping rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqPing(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqPingMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerPingService.
   * <pre>
   * 判断服务器之间rpc连接状态ping rpc service
   * </pre>
   */
  public static abstract class Server2ServerPingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerPingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerPingService.
   * <pre>
   * 判断服务器之间rpc连接状态ping rpc service
   * </pre>
   */
  public static final class Server2ServerPingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerPingServiceStub> {
    private Server2ServerPingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerPingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerPingServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqPing(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqPingMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerPingService.
   * <pre>
   * 判断服务器之间rpc连接状态ping rpc service
   * </pre>
   */
  public static final class Server2ServerPingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerPingServiceBlockingStub> {
    private Server2ServerPingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerPingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerPingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty reqPing(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqPingMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerPingService.
   * <pre>
   * 判断服务器之间rpc连接状态ping rpc service
   * </pre>
   */
  public static final class Server2ServerPingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerPingServiceFutureStub> {
    private Server2ServerPingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerPingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerPingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reqPing(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqPingMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_PING = 0;

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
        case METHODID_REQ_PING:
          serviceImpl.reqPing((com.google.protobuf.Empty) request,
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
          getReqPingMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.google.protobuf.Empty>(
                service, METHODID_REQ_PING)))
        .build();
  }

  private static abstract class Server2ServerPingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerPingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerPingService");
    }
  }

  private static final class Server2ServerPingServiceFileDescriptorSupplier
      extends Server2ServerPingServiceBaseDescriptorSupplier {
    Server2ServerPingServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerPingServiceMethodDescriptorSupplier
      extends Server2ServerPingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerPingServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (Server2ServerPingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerPingServiceFileDescriptorSupplier())
              .addMethod(getReqPingMethod())
              .build();
        }
      }
    }
    return result;
  }
}
