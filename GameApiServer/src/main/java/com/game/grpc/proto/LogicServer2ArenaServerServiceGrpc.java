package com.game.grpc.proto;

/**
 * <pre>
 * 游戏逻辑服向竞技场服进行请求rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LogicServer2ArenaServerServiceGrpc {

  private LogicServer2ArenaServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "LogicServer2ArenaServerService";

  // Static method descriptors that strictly reflect the proto.
  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LogicServer2ArenaServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceStub>() {
        @java.lang.Override
        public LogicServer2ArenaServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ArenaServerServiceStub(channel, callOptions);
        }
      };
    return LogicServer2ArenaServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LogicServer2ArenaServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceBlockingStub>() {
        @java.lang.Override
        public LogicServer2ArenaServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ArenaServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LogicServer2ArenaServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LogicServer2ArenaServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LogicServer2ArenaServerServiceFutureStub>() {
        @java.lang.Override
        public LogicServer2ArenaServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LogicServer2ArenaServerServiceFutureStub(channel, callOptions);
        }
      };
    return LogicServer2ArenaServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 游戏逻辑服向竞技场服进行请求rpc service
   * </pre>
   */
  public interface AsyncService {
  }

  /**
   * Base class for the server implementation of the service LogicServer2ArenaServerService.
   * <pre>
   * 游戏逻辑服向竞技场服进行请求rpc service
   * </pre>
   */
  public static abstract class LogicServer2ArenaServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return LogicServer2ArenaServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service LogicServer2ArenaServerService.
   * <pre>
   * 游戏逻辑服向竞技场服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2ArenaServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<LogicServer2ArenaServerServiceStub> {
    private LogicServer2ArenaServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ArenaServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ArenaServerServiceStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service LogicServer2ArenaServerService.
   * <pre>
   * 游戏逻辑服向竞技场服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2ArenaServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<LogicServer2ArenaServerServiceBlockingStub> {
    private LogicServer2ArenaServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ArenaServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ArenaServerServiceBlockingStub(channel, callOptions);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service LogicServer2ArenaServerService.
   * <pre>
   * 游戏逻辑服向竞技场服进行请求rpc service
   * </pre>
   */
  public static final class LogicServer2ArenaServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<LogicServer2ArenaServerServiceFutureStub> {
    private LogicServer2ArenaServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LogicServer2ArenaServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LogicServer2ArenaServerServiceFutureStub(channel, callOptions);
    }
  }


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
        .build();
  }

  private static abstract class LogicServer2ArenaServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LogicServer2ArenaServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LogicServer2ArenaServerService");
    }
  }

  private static final class LogicServer2ArenaServerServiceFileDescriptorSupplier
      extends LogicServer2ArenaServerServiceBaseDescriptorSupplier {
    LogicServer2ArenaServerServiceFileDescriptorSupplier() {}
  }

  private static final class LogicServer2ArenaServerServiceMethodDescriptorSupplier
      extends LogicServer2ArenaServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    LogicServer2ArenaServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (LogicServer2ArenaServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LogicServer2ArenaServerServiceFileDescriptorSupplier())
              .build();
        }
      }
    }
    return result;
  }
}
