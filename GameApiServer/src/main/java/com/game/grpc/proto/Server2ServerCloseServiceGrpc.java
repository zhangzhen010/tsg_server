package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class Server2ServerCloseServiceGrpc {

  private Server2ServerCloseServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "Server2ServerCloseService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getReqGrpcCloseServerMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reqGrpcCloseServer",
      requestType = com.google.protobuf.Empty.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      com.google.protobuf.Empty> getReqGrpcCloseServerMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, com.google.protobuf.Empty> getReqGrpcCloseServerMethod;
    if ((getReqGrpcCloseServerMethod = Server2ServerCloseServiceGrpc.getReqGrpcCloseServerMethod) == null) {
      synchronized (Server2ServerCloseServiceGrpc.class) {
        if ((getReqGrpcCloseServerMethod = Server2ServerCloseServiceGrpc.getReqGrpcCloseServerMethod) == null) {
          Server2ServerCloseServiceGrpc.getReqGrpcCloseServerMethod = getReqGrpcCloseServerMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reqGrpcCloseServer"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new Server2ServerCloseServiceMethodDescriptorSupplier("reqGrpcCloseServer"))
              .build();
        }
      }
    }
    return getReqGrpcCloseServerMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Server2ServerCloseServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceStub>() {
        @java.lang.Override
        public Server2ServerCloseServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerCloseServiceStub(channel, callOptions);
        }
      };
    return Server2ServerCloseServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Server2ServerCloseServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceBlockingStub>() {
        @java.lang.Override
        public Server2ServerCloseServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerCloseServiceBlockingStub(channel, callOptions);
        }
      };
    return Server2ServerCloseServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Server2ServerCloseServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<Server2ServerCloseServiceFutureStub>() {
        @java.lang.Override
        public Server2ServerCloseServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new Server2ServerCloseServiceFutureStub(channel, callOptions);
        }
      };
    return Server2ServerCloseServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void reqGrpcCloseServer(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReqGrpcCloseServerMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service Server2ServerCloseService.
   * <pre>
   * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
   * </pre>
   */
  public static abstract class Server2ServerCloseServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return Server2ServerCloseServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service Server2ServerCloseService.
   * <pre>
   * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
   * </pre>
   */
  public static final class Server2ServerCloseServiceStub
      extends io.grpc.stub.AbstractAsyncStub<Server2ServerCloseServiceStub> {
    private Server2ServerCloseServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerCloseServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerCloseServiceStub(channel, callOptions);
    }

    /**
     */
    public void reqGrpcCloseServer(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReqGrpcCloseServerMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service Server2ServerCloseService.
   * <pre>
   * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
   * </pre>
   */
  public static final class Server2ServerCloseServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<Server2ServerCloseServiceBlockingStub> {
    private Server2ServerCloseServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerCloseServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerCloseServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty reqGrpcCloseServer(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReqGrpcCloseServerMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service Server2ServerCloseService.
   * <pre>
   * 通知关闭服务器进程service（这个消息所有服务器都需要，统一放入API项目，钩子不好用，自己实现关闭服务器）
   * </pre>
   */
  public static final class Server2ServerCloseServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<Server2ServerCloseServiceFutureStub> {
    private Server2ServerCloseServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Server2ServerCloseServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new Server2ServerCloseServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> reqGrpcCloseServer(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReqGrpcCloseServerMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQ_GRPC_CLOSE_SERVER = 0;

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
        case METHODID_REQ_GRPC_CLOSE_SERVER:
          serviceImpl.reqGrpcCloseServer((com.google.protobuf.Empty) request,
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
          getReqGrpcCloseServerMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.google.protobuf.Empty,
              com.google.protobuf.Empty>(
                service, METHODID_REQ_GRPC_CLOSE_SERVER)))
        .build();
  }

  private static abstract class Server2ServerCloseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Server2ServerCloseServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server2ServerCloseService");
    }
  }

  private static final class Server2ServerCloseServiceFileDescriptorSupplier
      extends Server2ServerCloseServiceBaseDescriptorSupplier {
    Server2ServerCloseServiceFileDescriptorSupplier() {}
  }

  private static final class Server2ServerCloseServiceMethodDescriptorSupplier
      extends Server2ServerCloseServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    Server2ServerCloseServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (Server2ServerCloseServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Server2ServerCloseServiceFileDescriptorSupplier())
              .addMethod(getReqGrpcCloseServerMethod())
              .build();
        }
      }
    }
    return result;
  }
}
