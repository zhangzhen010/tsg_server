package com.game.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * 充值服向逻辑服rpc service
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: grpc.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class PayServer2LogicServerServiceGrpc {

  private PayServer2LogicServerServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "PayServer2LogicServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ResPublicPayBack,
      com.google.protobuf.Empty> getResPayBackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "resPayBack",
      requestType = com.game.grpc.proto.GrpcProto.ResPublicPayBack.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ResPublicPayBack,
      com.google.protobuf.Empty> getResPayBackMethod() {
    io.grpc.MethodDescriptor<com.game.grpc.proto.GrpcProto.ResPublicPayBack, com.google.protobuf.Empty> getResPayBackMethod;
    if ((getResPayBackMethod = PayServer2LogicServerServiceGrpc.getResPayBackMethod) == null) {
      synchronized (PayServer2LogicServerServiceGrpc.class) {
        if ((getResPayBackMethod = PayServer2LogicServerServiceGrpc.getResPayBackMethod) == null) {
          PayServer2LogicServerServiceGrpc.getResPayBackMethod = getResPayBackMethod =
              io.grpc.MethodDescriptor.<com.game.grpc.proto.GrpcProto.ResPublicPayBack, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "resPayBack"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.game.grpc.proto.GrpcProto.ResPublicPayBack.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new PayServer2LogicServerServiceMethodDescriptorSupplier("resPayBack"))
              .build();
        }
      }
    }
    return getResPayBackMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PayServer2LogicServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceStub>() {
        @java.lang.Override
        public PayServer2LogicServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayServer2LogicServerServiceStub(channel, callOptions);
        }
      };
    return PayServer2LogicServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PayServer2LogicServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceBlockingStub>() {
        @java.lang.Override
        public PayServer2LogicServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayServer2LogicServerServiceBlockingStub(channel, callOptions);
        }
      };
    return PayServer2LogicServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PayServer2LogicServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PayServer2LogicServerServiceFutureStub>() {
        @java.lang.Override
        public PayServer2LogicServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PayServer2LogicServerServiceFutureStub(channel, callOptions);
        }
      };
    return PayServer2LogicServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * 充值服向逻辑服rpc service
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * 充值服务器返回游戏服务器充值状态
     * </pre>
     */
    default void resPayBack(com.game.grpc.proto.GrpcProto.ResPublicPayBack request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResPayBackMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service PayServer2LogicServerService.
   * <pre>
   * 充值服向逻辑服rpc service
   * </pre>
   */
  public static abstract class PayServer2LogicServerServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return PayServer2LogicServerServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service PayServer2LogicServerService.
   * <pre>
   * 充值服向逻辑服rpc service
   * </pre>
   */
  public static final class PayServer2LogicServerServiceStub
      extends io.grpc.stub.AbstractAsyncStub<PayServer2LogicServerServiceStub> {
    private PayServer2LogicServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayServer2LogicServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayServer2LogicServerServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * 充值服务器返回游戏服务器充值状态
     * </pre>
     */
    public void resPayBack(com.game.grpc.proto.GrpcProto.ResPublicPayBack request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResPayBackMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service PayServer2LogicServerService.
   * <pre>
   * 充值服向逻辑服rpc service
   * </pre>
   */
  public static final class PayServer2LogicServerServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<PayServer2LogicServerServiceBlockingStub> {
    private PayServer2LogicServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayServer2LogicServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayServer2LogicServerServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * 充值服务器返回游戏服务器充值状态
     * </pre>
     */
    public com.google.protobuf.Empty resPayBack(com.game.grpc.proto.GrpcProto.ResPublicPayBack request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResPayBackMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service PayServer2LogicServerService.
   * <pre>
   * 充值服向逻辑服rpc service
   * </pre>
   */
  public static final class PayServer2LogicServerServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<PayServer2LogicServerServiceFutureStub> {
    private PayServer2LogicServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PayServer2LogicServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PayServer2LogicServerServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * 充值服务器返回游戏服务器充值状态
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> resPayBack(
        com.game.grpc.proto.GrpcProto.ResPublicPayBack request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResPayBackMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RES_PAY_BACK = 0;

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
        case METHODID_RES_PAY_BACK:
          serviceImpl.resPayBack((com.game.grpc.proto.GrpcProto.ResPublicPayBack) request,
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
          getResPayBackMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.game.grpc.proto.GrpcProto.ResPublicPayBack,
              com.google.protobuf.Empty>(
                service, METHODID_RES_PAY_BACK)))
        .build();
  }

  private static abstract class PayServer2LogicServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PayServer2LogicServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.game.grpc.proto.GrpcProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PayServer2LogicServerService");
    }
  }

  private static final class PayServer2LogicServerServiceFileDescriptorSupplier
      extends PayServer2LogicServerServiceBaseDescriptorSupplier {
    PayServer2LogicServerServiceFileDescriptorSupplier() {}
  }

  private static final class PayServer2LogicServerServiceMethodDescriptorSupplier
      extends PayServer2LogicServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    PayServer2LogicServerServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (PayServer2LogicServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PayServer2LogicServerServiceFileDescriptorSupplier())
              .addMethod(getResPayBackMethod())
              .build();
        }
      }
    }
    return result;
  }
}
